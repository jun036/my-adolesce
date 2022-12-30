package com.adolesce.server.javabasic;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.adolesce.common.entity.User;
import com.adolesce.common.entity.course.Lesson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2021/7/13 15:32
 */
public class SomeTest {
    @Test
    public void testInt1() {
        Integer i = new Integer(100);
        Integer j = new Integer(100);
        System.out.println(i == j);//false
    }

    @Test
    public void testInt2() {
        Integer i = new Integer(100);
        int j = 100;
        System.out.println(i == j);//true
    }

    @Test
    public void testInt3() {
        Integer i = new Integer(100);
        Integer j = 100;
        System.out.println(i == j); //false
    }

    @Test
    public void testInt4() {
        Integer i = 100;
        Integer j = 100;
        System.out.println(i == j);  //true

        Integer x = 128;
        Integer y = 128;
        System.out.println(x == y); //false
    }

    @Test
    public void testString1() {
        String s = "hello";
        String ss = s;
        String sss = "hello";

        System.out.println(s == sss);
        System.out.println(s == ss);
        s = s + " world";
        System.out.println(s == ss);
        System.out.println(s);
    }

    @Test
    public void testString2() {
        String s1 = "hello";
        String s2 = new String("hello");
        System.out.println(s1 == s2);
    }

    @Test
    public void testString3() {
        String s1 = new String("hello ") + new String("world");
        s1.intern();
        String s2 = "hello world";
        System.out.println(s1 == s2);
    }

    @Test
    public void testString4() {
        String s1 = new String("hello ") + new String("world");  //新new对象
        String s2 = "hello world";  //常量池
        System.out.println("s1 == s2: " + (s1 == s2)); //false

        String s3 = "hello " + "world";  //常量池

        String h = "hello ";
        String w = "world";
        String s4 = h + w;  //新new对象
        String s7 = h + w;  //新new对象

        String s5 = "hello";
        s5 = s5 + " world";         //新new对象
        String s6 = s5 + " world";  //新new对象

        System.out.println("s1 == s3: " + (s1 == s3));  //false
        System.out.println("s1 == s4: " + (s1 == s4)); //false

        System.out.println("s2 == s3: " + (s2 == s3)); //true
        System.out.println("s2 == s4: " + (s2 == s4.intern())); //true
        System.out.println("s3 == s4: " + (s3 == s4.intern())); //true

        System.out.println("s1 == s5: " + (s1 == s5)); //false
        System.out.println("s2 == s5: " + (s2 == s5)); //false
        System.out.println("s3 == s5: " + (s3 == s5)); //false
        System.out.println("s4 == s5: " + (s4 == s5)); //false
        System.out.println("s5 == s6: " + (s5 == s6)); //false
        System.out.println("s4 == s7: " + (s4 == s7.intern())); //false
    }

    @Test
    public void test(){
        String v1 = "aaaaaaaaaaaa";

        String v2 = "aaaaaaaaaaaa";

        Integer v3 = 128;

        Integer v4 = 128;

        Long v5 = 2L;
        Long v6 = 2L;

        Double v7 = 0.8;
        Double v8 = 0.8;
        System.out.println(v1 == v2);
        System.out.println(v3 == v4);
        System.out.println("bb" == "bb");
        System.out.println(v5 == v6);
        System.out.println(v7 == v8);
    }

    @Test
    public void testTryFinally1() {
       System.out.println("return :" + testReturn1());
    }

    private int testReturn5() {
        int i = 1;
        try {
            i++;
            System.out.println("try:" + i);
            int x = i / 0;
            return i;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
            return i;
        } finally {
            i++;
            System.out.println("finally:" + i);
            return i;
        }
    }

    private int testReturn4() {
        int i = 1;
        try {
            i++;
            System.out.println("try:" + i);
            return i;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
            return i;
        } finally {
            i++;
            System.out.println("finally:" + i);
            return i;
        }
    }

    private int testReturn3() {
        int i = -2;
        try {
            i++;
            System.out.println("try:" + i);
            return i++ / --i;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
            return i;
        } finally {
            i++;
            System.out.println("finally:" + i);
        }
    }

    private int testReturn1() {
        int i = 1;
        try {
            i++;
            System.out.println("try:" + i);
            return i;
        } catch (Exception e) {
            i++;
            System.out.println("catch:" + i);
        } finally {
            i++;
            System.out.println("finally:" + i);
        }
        return i;
    }

    private List<Integer> testReturn2() {
        List<Integer> list = new ArrayList<>();
        try {
            list.add(1);
            System.out.println("try:" + list);
            return list;
        } catch (Exception e) {
            list.add(2);
            System.out.println("catch:" + list);
        } finally {
            list.add(3);
            System.out.println("finally:" + list);
        }
        return list;
    }

    @Test
    public void testTryFinally2() {
        List<Integer> list = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6});
        Integer taskNo = 0;
        try {
            while (true) {
                taskNo++;
                if (taskNo == 3) {
                    int a = 3 / 0;
                }
                System.out.println("正在处理任务：" + taskNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("任务" + taskNo + "处理失败，进行补偿");
        }
    }

    @Test
    public void testStringNull() {
        String s1 = null + "";
        String s2 = "null";
        System.out.println(s1 + "");
        System.out.println(s2);
        System.out.println(s1.equals(s2));
        System.out.println((Boolean) null);
    }

    @Test
    public void testDateBetweenSecond() throws ParseException {
        String str = DateUtil.tomorrow().toDateStr() + " 00:00:00";
        long between = DateUtil.between(new Date(), DateUtil.parse(str), DateUnit.SECOND);
        System.out.println(between);
    }

    @Test
    public void testPageBySelf() {
        Integer page = 7;
        Integer pageSize = 3;
        String pids = "25,13,67,90,26,63,73,88,54,18,90,16,17,52,99";
        //List<String> list = Arrays.asList(pids.split(","));
        String[] list = pids.split(",");

       /* int startIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize - 1;
        System.out.println(startIndex + " -> "+endIndex);

        List<Long> result = new ArrayList<>(pageSize);
        for(int i = startIndex; i <= endIndex; i++ ){
            result.add(Long.valueOf(list[i]));
        }
        result.forEach(System.out::println);*/


        Arrays.stream(list).skip((page - 1) * pageSize).limit(pageSize)
                .map(e -> Long.valueOf(e))
                .forEach(System.out::println);
        // int totalPageSize = list.size() % pageSize ==0 ? list.size() / pageSize : list.size() / pageSize + 1;
    }


    @Test
    public void testRandom() {
        for (int i = 0; i < 20; i++) {
            //Integer num = RandomUtils.nextInt(2,13);
            //Integer num = RandomUtils.nextInt(11) + 2 ;
            Integer num = RandomUtil.randomInt(2, 13);
            System.out.println(num);
        }
    }

    @Test
    public void testToString() {
        User user = new User();
        System.out.println(user.toString());
        List list = new ArrayList();
        list = new LinkedList();
        Map map = new HashMap();
    }

    @Test
    public void testDate() {
        Date endOfDay = DateUtil.endOfDay(new Date());
        //一天后过期   获取当前凌晨时间  减去当前时间  得到毫秒值
        long betweenDay = DateUtil.between(new Date(System.currentTimeMillis()), endOfDay, DateUnit.SECOND);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endOfDay));
    }

    @Test
    public void testJsonParse() {
        String str = "[\"aaa\",\"bbb\",\"ccc\"]";
        //方式一
        List<String> strings = JSON.parseObject(str, List.class);
        strings.forEach(System.out::println);
        System.out.println("==============");
        //方式二
        strings = (List) JSON.parseArray(str);
        strings.forEach(System.err::println);
    }


    @Test
    public void test3() {
        String json = "{'a': '\\\\\\\\11111111\\\\aa\\\\bb\\\\cc\\\\dd\\\\cexe.docx'}";
        System.out.println(json);
        Map map = JSONObject.parseObject(json, Map.class);

        System.err.println(map);
        System.err.println(map.get("a"));
    }

    @Test
    public void testJinZhi() {
        int num = 1;
        if (num > 0) {
            if (num < 10) {
                System.out.println(String.format("%02x", Integer.valueOf(num)));
            } else {
                System.out.println(Integer.toHexString(num));
            }
        }
    }

    @Test
    public void test4() {
        String a = "abc";
        String b = "abc";
        if (a == b) {
            String c = a.concat(b);
            String d = a + b + c;
            System.out.print(d);
        }
    }

    @Test
    public void test5() throws ParseException {
        List<Lesson> basicLessons = Lesson.getBasicLessons();
        List<Lesson> seniorLessons = Lesson.getSeniorLessons();
        Map<String, String> holidays = Lesson.getHolidays("2022-08-24");
        System.out.println(holidays);
    }

    @Test
    public void test6() {
        Long i = 1L;
        System.out.println(String.format("%04d", 23222));
    }

    @Test
    public void test7() throws ParseException {
        //格式化类
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        //初始化四个时间
        Date date1 = dateFormat.parse("20:00:00");
        Date date2 = dateFormat.parse("20:00:00");
        Date date3 = dateFormat.parse("20:00:01");
        Date date4 = dateFormat.parse("20:30:00");

        //打印格式化好的时间
        System.out.println(dateFormat.format(date1));
        System.out.println(dateFormat.format(date2));
        System.out.println(dateFormat.format(date3));
        System.out.println(dateFormat.format(date4));

        //对四个时间进行比较
        System.out.println(date1.compareTo(date2));  //0 ： 说明date1和date2一样大
        System.out.println(date2.compareTo(date3));  // -1 ： 说明date2比date3小
        System.out.println(date4.compareTo(date3));  // 1 ： 说明date4比date3 大
    }

    /**
     * 分解一个数字，得到该数字的“状态位”
     */
    @Test
    public void parseNum() {
        int num = 10;
        List<Integer> parseResult = new ArrayList<>();

        for (int i = 0; num != 0; num = num >> 1, i++) {
            if ((num & 1) == 1) {
                parseResult.add((int) Math.pow(2, i));
            }
        }
        parseResult.forEach(System.err::println);
    }

    @Test
    public void testTryCatchWithFor() {
        try {
            while (true) {
                for (int i = 1; i < 50; i++) {
                    System.out.println(i);
                    if(i%5 == 0){
                        int y = i/0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("记录错误信息：" + e);
        } finally {
            try {
                System.out.println("我是finally，需要执行");
            }finally {

            }
        }
    }

    /**
     * 69期唐豪公司需求
     * @throws ParseException
     */
    @Test
    public void test8() throws ParseException {
        String beginDateStr = "2022-11-09";
        String endDateStr = "2022-12-20";
        //1、获取时间区间集合
        List<String> dateList = getDateList(beginDateStr,endDateStr);

        //2、准备数据：构建日期Map集合（也可以用实体封装）
        List<Map> dateMapList = new ArrayList<>();
        Random random = new Random();
        dateList.forEach(dateStr -> {
            HashMap dateMap = new HashMap();
            dateMap.put("date",dateStr);
            dateMap.put("weekOfMonth",dateStr.substring(0,dateStr.lastIndexOf("-")+1) +
                    DateUtil.weekOfMonth(DateUtil.parseDate(dateStr))+"周");
            //此处的条数应该从数据库查询，目前用随机数代替
            dateMap.put("count",random.nextInt(20));
            dateMapList.add(dateMap);
        });
        System.err.println("数据如下：");
        System.err.println("==================================");
        dateMapList.stream().forEach(System.out::println);

        //3、统计数据
        Map<String,Integer> resultMap = new TreeMap<>();
        String weekOfMonth = "";
        for (Map map : dateMapList) {
            weekOfMonth = (String)map.get("weekOfMonth");
            Integer count = resultMap.get(weekOfMonth);
            if(count != null){
                resultMap.put(weekOfMonth,count + (Integer) map.get("count"));
            }else{
                resultMap.put(weekOfMonth,(Integer) map.get("count"));
            }
        }
        System.err.println("统计结果如下：");
        System.err.println("==================================");
        resultMap.forEach((k,v) -> System.out.println(k + ":" +v));
    }

    /**
     * 根据开始时间和结束时间获取范围内的日期集合
     * @param beginDateStr 开始日期
     * @param endDateStr 结束日期
     * @return
     * @throws ParseException
     */
    private List<String> getDateList(String beginDateStr, String endDateStr) throws ParseException {
        List<String> dateList = new ArrayList<>();
        Date beginDate = DateUtil.parseDate(beginDateStr);
        Date endDate = DateUtil.parseDate(endDateStr);
        while (!beginDate.after(endDate)) {
            dateList.add(DateUtil.formatDate(beginDate));
            beginDate = DateUtils.addDays(beginDate, 1);
        }
        return dateList;
    }

    @Test
    public void test9(){
        Map<String, Integer> hashMap = Maps.newHashMap();
        Map<String, Integer> treeMap = Maps.newTreeMap();
        Map<String, Integer> linkedHashMap = Maps.newLinkedHashMap();
        System.out.println("--------------test hashMap");
        testMap(hashMap);
        System.out.println("--------------test treeMap");
        testMap(treeMap);
        System.out.println("--------------test linkedHashMap");
        testMap(linkedHashMap);
    }

    private void testMap(Map<String, Integer> map) {
        map.put("2022-11-2周:34", 1);
        map.put("2022-11-3周:60", 2);
        map.put("2022-11-4周:36", 3);
        map.put("2022-12-1周:20", 4);
        map.put("2022-12-2周:54", 4);
        map.put("2022-12-3周:69", 4);
        map.put("2022-12-4周:24", 4);
        map.forEach((k,v) -> System.out.println(k + ":" +v));
    }

    @Test
    public void test10(){
        int a = 2;
        Integer b = 2;
        User user = new User();
        user.setUserName("张三");
        user.setAge(18);
        
        test10_1(2,a,user);
        System.out.println(a);
        System.out.println(b);
        System.out.println(user);
        
    }

    private void test10_1(int a,Integer b,User user) {
        a = 3;
        b = new Integer(3);
        User user1 = new User();
        user1.setUserName("李四");
        user1.setAge(20);
        user = user1;
    }

    @Test
    public void test11(){
        System.out.println(2.0 - 1.1);
        System.out.println((20.0-11.0)/10.0);
        System.out.println(new BigDecimal(2.0).subtract(new BigDecimal(1.1)));
    }

    @Test
    public void test12(){
        Short num1 = Short.valueOf("1");
        Map map = new HashMap();
        map.put("num1",num1);

        Short num2 = (Short) map.get("num1");
        System.out.println(num2.equals(1));
        System.out.println(num2 == 1);
    }
}
