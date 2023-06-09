package com.adolesce.common.utils.excel.imports;

import com.adolesce.common.utils.XmlReadHelper;
import com.adolesce.common.utils.excel.ExcelReaderHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 类名称：ExcelProcessService
 * 类描述 ：解析excel并生成Vo，如果有错误信息，显示错误信息
 *
 * @author : 刘威东
 * 创建时间：2015年6月26日 下午5:07:01
 */
@Slf4j
public class ExcelImportHelper {

    /**
     * 解析excel中选择的sheet
     *
     * @param configName 配置名称(excelImportConfig.xml)
     * @param file       上传文件对象
     * @param sheet      工作表的页码
     * @return 返回读取的List
     * @throws Exception
     */
    public static List<ExcelImportBaseBo> importExcel(String configName, MultipartFile file, Integer sheet) throws Exception {
        List<ExcelImportBaseBo> boList = new ArrayList<>();
        //校验是否有此配置
        ExcelImportConfig importConfig = XmlReadHelper.getExcelImportConfigByName(configName);
        if (Objects.isNull(importConfig)) {
            throw new Exception("导入配置未设置,config:" + configName);
        }
        //校验是否继承BaseBo
        Class boClass = importConfig.getBoClazz();
        Object instance = boClass.newInstance();
        if (!(instance instanceof ExcelImportBaseBo)) {
            throw new Exception("生成实例出错:请继承ExcelImportBaseBo");
        }
        //开始解析sheet页
        ExcelImportBaseBo bo;
        ExcelReaderHelper reader = new ExcelReaderHelper(file);
        int rowNum = reader.getRowsOfSheet(sheet);
        Integer startLine = importConfig.getStartLine();
        List<ExcelImportConfig.Field> configFieldList = importConfig.getFieldList();
        List<Integer> needReadColumns = configFieldList.stream().map(field -> field.getColumnIndex()).collect(Collectors.toList());
        for (int i = startLine - 1; i <= rowNum; i++) {
            bo = (ExcelImportBaseBo) boClass.newInstance();
            Row excelRow = reader.getExcelRow(sheet, i);
            //校验行是否为空，是则跳过不处理(都为空字符串也算)
            boolean rowIsNull = ExcelReaderHelper.validRowIsNull(excelRow, needReadColumns);
            if (rowIsNull) {
                continue;
            }
            //填充baseBo
            fillBaseBo(bo, configFieldList, excelRow);
            bo.setDataLine(i + 1);
            System.out.println("当前读取至第" + bo.getDataLine() + "行");
            boList.add(bo);
        }
        return boList;
    }

    /**
     * 填充baseBo
     *
     * @param baseBo          待填充对象
     * @param configFieldList 字段配置集合
     * @param row             数据行
     */
    private static void fillBaseBo(ExcelImportBaseBo baseBo, List<ExcelImportConfig.Field> configFieldList, Row row) {
        for (ExcelImportConfig.Field configField : configFieldList) {
            Cell cell = row.getCell(configField.getColumnIndex());
            setBaseObjProperty(baseBo, configField, cell);
        }
    }

    /**
     * 填充字段
     *
     * @param baseBo      待填充对象
     * @param configField 字段配置
     * @param cell        数据列
     */
    private static void setBaseObjProperty(ExcelImportBaseBo baseBo, ExcelImportConfig.Field configField, Cell cell) {
        Object cellValue = ExcelReaderHelper.getCellValue(cell);
        String fieldName = configField.getFieldName();
        String fieldDesc = configField.getFieldDesc();
        //根据配置校验单元格数据是否可为空
        if (Objects.isNull(cellValue)) {
            String validateResult = validateCellValue(null, configField);
            if (StringUtils.isNotBlank(validateResult)) {
                baseBo.addFieldError(fieldName, validateResult);
            }
            return;
        }
        String requiredType = "";
        try {
            Field field = baseBo.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Class fieldType = field.getType();
            if (BigDecimal.class.isAssignableFrom(fieldType)) {
                requiredType = "整数或小数";
                if (cellValue instanceof Date) {
                    addFiledError(baseBo, fieldName, requiredType, fieldDesc, "时间/日期");
                } else if (cellValue instanceof Double) {
                    field.set(baseBo, new BigDecimal(((Double) cellValue).doubleValue()));
                } else if (cellValue instanceof String) {
                    field.set(baseBo, new BigDecimal((String) cellValue));
                }
            } else if (String.class.isAssignableFrom(fieldType)) {
                requiredType = "文本";
                if (cellValue instanceof Date) {
                    addFiledError(baseBo, fieldName, requiredType, fieldDesc, "时间/日期");
                } else if (cellValue instanceof Double) {
                    addFiledError(baseBo, fieldName, requiredType, fieldDesc, "数值");
                } else if (cellValue instanceof String) {
                    field.set(baseBo, cellValue);
                }
            } else if (Date.class.isAssignableFrom(fieldType)) {
                requiredType = "时间";
                if (cellValue instanceof Date) {
                    field.set(baseBo, cellValue);
                } else if (cellValue instanceof Double) {
                    addFiledError(baseBo, fieldName, requiredType, fieldDesc, "数值");
                } else if (cellValue instanceof String) {
                    String formartStr;
                    if (((String) cellValue).contains("-")) {
                        formartStr = "yyyy-MM-dd";
                    } else {
                        formartStr = "yyyy/MM/dd";
                    }
                    field.set(baseBo, cn.hutool.core.date.DateUtil.parse((String) cellValue, formartStr));
                }
            } else if (Integer.class.isAssignableFrom(fieldType) || int.class.isAssignableFrom(fieldType)) {
                requiredType = "整数";
                if (cellValue instanceof Date) {
                    addFiledError(baseBo, fieldName, requiredType, fieldDesc, "时间/日期");
                } else if (cellValue instanceof Double) {
                    field.set(baseBo, ((Double) cellValue).intValue());
                } else if (cellValue instanceof String) {
                    field.set(baseBo, new Integer((String) cellValue));
                }
            } else if (Long.class.isAssignableFrom(fieldType) || long.class.isAssignableFrom(fieldType)) {
                requiredType = "整数";
                if (cellValue instanceof Date) {
                    addFiledError(baseBo, fieldName, requiredType, fieldDesc, "时间/日期");
                } else if (cellValue instanceof Double) {
                    field.set(baseBo, ((Double) cellValue).longValue());
                } else if (cellValue instanceof String) {
                    field.set(baseBo, new Long((String) cellValue));
                }
            } else if (Double.class.isAssignableFrom(fieldType) || double.class.isAssignableFrom(fieldType)) {
                requiredType = "整数或小数";
                if (cellValue instanceof Date) {
                    addFiledError(baseBo, fieldName, requiredType, fieldDesc, "时间/日期");
                } else if (cellValue instanceof Double) {
                    field.set(baseBo, cellValue);
                } else if (cellValue instanceof String) {
                    field.set(baseBo, new Double((String) cellValue));
                }
            } else {
                field.set(baseBo, cellValue);
            }
            String validateResult = validateCellValue(field.get(baseBo), configField);
            if (StringUtils.isNotBlank(validateResult)) {
                baseBo.addFieldError(fieldName, validateResult);
                return;
            }
        } catch (Exception e) {
            baseBo.addFieldError(fieldName, fieldDesc + "需要：" + requiredType + "类型，当前值为：" + cellValue);
        }
    }

    /**
     * 添加校验错误信息
     *
     * @param baseBo
     * @param fieldName    字段名称
     * @param requiredType 所需类型
     * @param fieldDesc    字段描述
     * @param nowType      目前类型
     */
    private static void addFiledError(ExcelImportBaseBo baseBo, String fieldName, String requiredType,
                               String fieldDesc, String nowType) {
        StringBuilder builder = new StringBuilder(fieldDesc);
        builder.append("需要：").append(requiredType).append("类型，当前值为").append(nowType).append("类型");
        baseBo.addFieldError(fieldName, builder.toString());
    }


    /**
     * 校验单元格是否必填以及正则
     *
     * @param cellValue   单元格值
     * @param configField 单元格配置信息
     * @return 错误信息
     */
    public static String validateCellValue(Object cellValue, ExcelImportConfig.Field configField) {
        if (Objects.isNull(cellValue)) {
            if (!configField.isAlowBlank()) {
                return configField.getFieldDesc() + "为必填字段";
            } else {
                return null;
            }
        }
        String regexStr = configField.getRegexStr();
        if (StringUtils.isBlank(regexStr)) {
            return null;
        }
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(cellValue.toString());
        while (!matcher.find()) {
            return configField.getErrorMsg();
        }
        return null;
    }

    public static void importMaxExcel(String path, int sheetNo, XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler) throws Exception {
        importMaxExcel(path,null,sheetNo,sheetContentsHandler);
    }

    public static void importMaxExcel(InputStream in,int sheetNo, XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler) throws Exception {
        importMaxExcel(null,in,sheetNo,sheetContentsHandler);
    }

    /**
     * 读取数据
     * @param path 文件路径，和文件流二者传其一
     * @param in 文件流，和文件路径二者传其一
     * @param sheetNo 读取第几个sheet（从1开始）
     * @param sheetContentsHandler sheet处理类
     * @throws Exception
     */
    public static void importMaxExcel(String path, InputStream in, int sheetNo,
                          XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler) throws Exception {
        //============设置POI的事件模式，指定使用事件驱动去解析EXCEL来做============
        //1.根据Excel获取OPCPackage对象
        OPCPackage pkg;
        if(org.springframework.util.StringUtils.isEmpty(path)){
            pkg = OPCPackage.open(in);
        }else{
            pkg = OPCPackage.open(path, PackageAccess.READ);
        }
        //2.创建XSSFReader对象
        XSSFReader reader = new XSSFReader(pkg);

        //3.获取String类型表格SharedStringsTable对象
        //SharedStringsTable sst = reader.getSharedStringsTable();
        ReadOnlySharedStringsTable sst = new ReadOnlySharedStringsTable(pkg);

        //4.获取样式表格StylesTable对象
        StylesTable styles = reader.getStylesTable();

        //============使用Sax进行解析============
        //5.创建Sax的XmlReader对象
        XMLReader parser = XMLReaderFactory.createXMLReader();

        //6.设置Sheet的事件处理器
        parser.setContentHandler(new XSSFSheetXMLHandler(styles, sst, sheetContentsHandler, false));

        //7.逐行读取(因为有多个sheet所以需要迭代读取)
        XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) reader.getSheetsData();
        int currentSheet = 0;
        while (sheets.hasNext()) {
            currentSheet++;
            InputStream sheetstream = sheets.next();//每一个sheet的数据流
            if(currentSheet == sheetNo){
                InputSource sheetSource = new InputSource(sheetstream);
                try {
                    parser.parse(sheetSource);
                } finally {
                    sheetstream.close();
                }
            }
        }
    }

}
