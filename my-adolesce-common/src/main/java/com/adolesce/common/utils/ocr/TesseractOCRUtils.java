package com.adolesce.common.utils.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/9/26 20:22
 */
public class TesseractOCRUtils {
    public String doOCR(BufferedImage image) throws TesseractException {
        //创建Tesseract对象
        ITesseract tesseract = new Tesseract();
        //设置字体库路径
        tesseract.setDatapath("D:\\some-test-file\\tessdata");
        //设置语言 -->简体中文
        tesseract.setLanguage("chi_sim");
        //执行ocr识别
        String result = tesseract.doOCR(image);
        //替换回车和tal键  使结果为一行
        result = result.replaceAll("\\r|\\n", "-").replaceAll(" ", "");
        return result;
    }
}
