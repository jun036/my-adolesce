package com.adolesce.server.javabasic.basic;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class NumberTest {
    /**
     * Math中四舍五入的方法
     * <p>
     * Math.ceil(double a)向上舍入，将数值向上舍入为最为接近的整数，返回值是double类型
     * <p>
     * Math.floor(double a)向下舍入，将数值向下舍入为最为接近的整数，返回值是double类型
     * <p>
     * Math.round(float a)标准舍入，将数值四舍五入为最为接近的整数，返回值是int类型
     * <p>
     * Math.round(double a)标准舍入，将数值四舍五入为最为接近的整数，返回值是long类型
     */
    @Test
    public void mathTest() {
        //ceil返回大的值
        System.out.println("Math.ceil测试开始.......");
        System.out.println(Math.ceil(-10.1));   //-10.0
        System.out.println(Math.ceil(10.7));    //11.0
        System.out.println(Math.ceil(-0.7));    //-0.0
        System.out.println(Math.ceil(0.0));     //0.0
        System.out.println(Math.ceil(-0.0));    //-0.0
        System.out.println(Math.ceil(-1.7));    //-1.0
        System.out.println("Math.ceil测试结束.......");

        //floor返回小的值
        System.out.println("Math.floor测试开始.......");
        System.out.println(Math.floor(-10.1));   //-11.0
        System.out.println(Math.floor(10.7));    //10.0
        System.out.println(Math.floor(-0.7));    //-1.0
        System.out.println(Math.floor(0.0));     //0.0
        System.out.println(Math.floor(-0.0));    //-0.0
        System.out.println(Math.floor(-1.7));    //-2.0
        System.out.println("Math.floor测试结束.......");

        //round四舍五入，float返回int，double返回long
        System.out.println("Math.round测试开始.......");
        System.out.println(Math.round(10.5));   //11
        System.out.println(Math.round(-10.5));  //-10
        System.out.println("Math.round测试结束.......");
    }

    /**
     * https://blog.51cto.com/u_13626762/3088440
     * 一、Math中random生成随机数：生成大于等于0，小于1的随机数（包头不包尾）
     */
    @Test
    public void testRandomNum1(){
        int min = 10;
        int max = 100;
        //一、Math生成随机数（Math 类中的 random 方法返回一个 [0.0, 1.0) 区间的 double 值。）
        for (int i = 0; i < 10000; i++) {
            System.out.println("随机数：" + (int)((Math.random() * (max - min + 1)) + min));
            //System.out.println("随机数：" + (int)(Math.random()*100 + 1));
        }
    }

    /**
     * 二、Random类生成随机数
     * Java 1.7 之前，最流行的随机数生成方法是 Random.nextInt
     * 两种构造方式：
     *      第一种使用默认的种子（当前时间作为种子）
     *      另一个使用long型整数为种子，Random类可以生成布尔型、浮点类型、整数等类型的随机数，还可以指定生成随机数的范围
     */
    @Test
    public void testRandomNum2(){
        int min = 10;
        int max = 100;
        //调用 netxInt 时带上 bound 参数，将得到预期区间内的随机数：
        Random rand = new Random();
        /*for (int i = 0; i < 10000; i++) {
            System.out.println("随机数：" + (rand.nextInt(max - min + 1) + min));
        }*/

        //Random使用当前时间作为Random的种子
        Random rand2 = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            System.out.println("随机数：" + (rand2.nextInt(max - min + 1) + min));
        }

        /*System.out.println(rand.nextInt());
        System.out.println(rand.nextBoolean());
        System.out.println(rand.nextDouble());*/
    }

    /**
     * 三、ThreadLocalRandom
     * Java 1.7 中ThreadLocalRandom类提供了一种新的更高效的随机数生成方法。与 Random 类相比有三个重要区别：
     * 1、无需显式初始化 ThreadLocalRandom 实例。这样可以避免创建大量无用的实例，浪费垃圾收集器回收时间。
     * 2、不能为 ThreadLocalRandom 设置随机种子（seed），这可能会导致问题。如果需要设置随机种子，应该避免采用这种方式生成随机数。
     * 3、Random 类在多线程时表现不佳。
     */
    @Test
    public void testRandomNum3(){
        int min = 10;
        int max = 100;
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            System.out.println(rand.nextInt(min, max + 1));
        }
    }

    /**
     * 四、ints：Java 8 引入了新的 ints 方法，返回一个java.util.stream.IntStream
     */
    @Test
    public void testRandomNum4(){
        int min = 10;
        int max = 100;
        //四、Java 8 引入了新的 ints 方法，返回一个java.util.stream.IntStream
        //不带参数的 ints方法将返回一个无限 int 流：
        Random random = new Random();
        IntStream unlimitedIntStream = random.ints();
        //调用时还可以指定参数来限制流大小：
        IntStream limitedIntStream = random.ints(100);
        //当然，也可以为生成数值设置最大值和最小值
        IntStream limitedIntStreamWithinARange = random.ints(1000, min, max + 1);
        limitedIntStreamWithinARange.forEach(System.out::println);
    }

    /**
     * 4、BigDecimal处理小数
     * <p>
     * 两种构造方式：第一种直接value写数字的值，第二种用String
     */
    public void testRandomNum5(){
        //BigDecimal
        System.out.println(0.8 - 0.7);   //0.10000000000000009
        BigDecimal a1 = new BigDecimal(0.1);
        BigDecimal b1 = new BigDecimal(0.9);
        BigDecimal c1 = a1.add(b1);
        System.out.println("a1.add(b1)=" + c1);  //a1.add(b1)=1.0000000000000000277555756156289135105907917022705078125

        BigDecimal a2 = new BigDecimal("0.1");
        a2 = BigDecimal.valueOf(0.1);

        BigDecimal b2 = new BigDecimal("0.9");
        b2 = BigDecimal.valueOf(0.9);

        BigDecimal c2 = a2.add(b2);
        System.out.println("a2=" + a2);  //a2=0.1
        System.out.println("a2.add(b2)=" + c2);  //a2.add(b2)=1.0
    }

    /**
     * apache工具类随机数获取
     */
    @Test
    public void testRandomUtil() {
        System.out.println(RandomStringUtils.randomNumeric(6));
    }


    @Test
    public void test1() {
        Object a = new Integer(3);
        Long b = Long.valueOf(a.toString());
        System.out.println(b);
    }

    @Test
    public void testBigDecimal() {
        BigDecimal a = new BigDecimal(10.897);
        BigDecimal b = new BigDecimal("10.897");
        BigDecimal c = BigDecimal.valueOf(10.897);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    @Test
    public void test2() {
        Integer i01 = 59;
        int i02 = 59;
        Integer i03 = Integer.valueOf(59);
        Integer i04 = new Integer(59);

        System.out.println(i02 == i04);
        System.out.println(i03 == i04);
        System.out.println(i01 == i03);
        System.out.println(i01 == i02);
    }

    //算法一：剩余金额随机法（）
    public void redPackage1(BigDecimal amount, BigDecimal min, BigDecimal num) {
        BigDecimal remain = amount.subtract(min.multiply(num));
        final Random random = new Random();
        final BigDecimal hundred = new BigDecimal("100");
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal redpack;
        for (int i = 0; i < num.intValue(); i++) {
            final int nextInt = random.nextInt(100);
            if (i == num.intValue() - 1) {
                redpack = remain;
            } else {
                redpack = new BigDecimal(nextInt).multiply(remain).divide(hundred, 2, RoundingMode.FLOOR);
            }
            if (remain.compareTo(redpack) > 0) {
                remain = remain.subtract(redpack);
            } else {
                remain = BigDecimal.ZERO;
            }
            sum = sum.add(min.add(redpack));
            System.out.println("第" + (i + 1) + "个人抢到红包金额为：" + min.add(redpack).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        System.out.println("红包总额：" + sum.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testRedPackage1() {
        BigDecimal amount = new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal min = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal num = new BigDecimal(10).setScale(2, BigDecimal.ROUND_HALF_UP);
        redPackage1(amount, min, num);
    }

    //算法二：整体金额随机法
    public void redPackage2(BigDecimal amount, BigDecimal min, BigDecimal num) {
        final Random random = new Random();
        final int[] rand = new int[num.intValue()];
        BigDecimal sum1 = BigDecimal.ZERO;
        BigDecimal redpack;
        int sum = 0;
        for (int i = 0; i < num.intValue(); i++) {
            rand[i] = random.nextInt(100);
            sum += rand[i];
        }
        final BigDecimal bigDecimal = new BigDecimal(sum);
        BigDecimal remain = amount.subtract(min.multiply(num));
        for (int i = 0; i < rand.length; i++) {
            if (i == num.intValue() - 1) {
                redpack = remain;
            } else {
                redpack = remain.multiply(new BigDecimal(rand[i])).divide(bigDecimal, 2, RoundingMode.FLOOR);
            }
            if (remain.compareTo(redpack) > 0) {
                remain = remain.subtract(redpack);
            } else {
                remain = BigDecimal.ZERO;
            }
            sum1 = sum1.add(min.add(redpack)).setScale(2, BigDecimal.ROUND_HALF_UP);
            System.out.println("第" + (i + 1) + "个人抢到红包金额为：" + min.add(redpack).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        System.out.println("红包总额：" + sum1);
    }

    @Test
    public void testRedPackage2() {
        BigDecimal amount = new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal min = new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal num = new BigDecimal(10).setScale(2, BigDecimal.ROUND_HALF_UP);
        redPackage2(amount, min, num);
    }

    //算法三：二倍均值法
    public void redPackage3(BigDecimal amount, BigDecimal min, int num) {
        //参与计算的红包金额（刨除最小红包金额，其余才参与随机红包的计算，比如100，发10个红包，最小红包7，参与计算金额 = 100 - 7 * 10 = 30）
        BigDecimal redmain = amount.subtract(min.multiply(new BigDecimal(num)));
        //100
        final BigDecimal hundred = new BigDecimal("100");
        //2
        final BigDecimal two = new BigDecimal("2");
        //已发红包总金额
        BigDecimal sum = BigDecimal.ZERO;
        //当前循环产生的随机金额
        BigDecimal redpack;

        for (int i = 0; i < num; i++) {
            if (i != num - 1) {
                //当前循环产生的金额 = 剩余可随机金额 / 剩余红包数 * 2 * 当前随机系数  (每个人可抢的最大金额为平均金额的2倍)
                redpack = redmain.divide(new BigDecimal(num-i), 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(two)
                        .multiply(new BigDecimal(Math.random() + 0.005).setScale(2,BigDecimal.ROUND_HALF_UP));
                        //.multiply(new BigDecimal(new Random().nextInt(100)).divide(hundred, 2, BigDecimal.ROUND_HALF_UP));
            } else {
                redpack = redmain;
                if(hundred.compareTo(sum.add(min.add(redpack)).setScale(2, BigDecimal.ROUND_HALF_UP)) != 0){
                    redpack = redpack.subtract(sum.add(min.add(redpack)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(hundred));
                }
            }
            redmain = redmain.subtract(redpack).setScale(2, BigDecimal.ROUND_HALF_UP);
            sum = sum.add(min.add(redpack)).setScale(2, BigDecimal.ROUND_HALF_UP);
            System.out.println("第" + (i + 1) + "个人抢到红包金额为：" + min.add(redpack).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        System.out.println("红包总额：" + sum);
    }

    @Test
    public void testRedPackage3() {
        BigDecimal amount = new BigDecimal(100);
        BigDecimal min = new BigDecimal(6);
        int num = 10;
        redPackage3(amount, min, num);
    }

    @Test
    public void test11() {
        for (int i = 0; i < 10000; i++) {
            testRedPackage3();
            //System.out.println(new BigDecimal(Math.random() + 0.005).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
    }
}