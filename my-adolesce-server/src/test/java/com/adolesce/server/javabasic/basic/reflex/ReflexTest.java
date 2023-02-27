package com.adolesce.server.javabasic.basic.reflex;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2021/12/8 11:54
 * <p>
 * 反射就是可以在代码运行过程中
 * 1、动态的获取类信息
 * 2、动态的创建对象，调用对象中的方法。
 * <p>
 * 需要注意的规律
 * 1、getConstructors()、getFields() ：这一组是获取公共的，包括父类的也可以获取（如果获取私有的会报：NoSuch...Exception）
 * 2、getDeclaredConstructor()、getDeclaredFields()  ： 这一组是获取当前类所有的（公共的、私有的）
 * 3、私有方式获取的Constructor、Field、Method，在使用的时候需要设置暴力反射：setAccessible(true)
 */
public class ReflexTest {
    /**
     * 1、获取.class的三种方式
     */
    @Test
    public void test1() throws ClassNotFoundException {
        //1、Class.forName方式
        Class aClass = Class.forName("com.adolesce.server.javabasic.basic.reflex.GoodsEntity");
        //2、类名.class方式
        Class bClass = GoodsEntity.class;
        //3、对象.getClass()方式
        GoodsEntity entity = new GoodsEntity();
        Class cClass = entity.getClass();
        System.out.println(aClass);
        System.out.println(aClass == bClass);
        System.out.println(bClass == cClass);
    }

    /**
     * 2、反射获取构造方法对象
     */
    @Test
    public void test2() throws ClassNotFoundException, NoSuchMethodException {
        Class aClass = Class.forName("com.adolesce.server.javabasic.basic.reflex.GoodsEntity");
        //1、获取所有公共构造器集合
        Constructor[] constructors = aClass.getConstructors();
        System.out.println("公共构造器集合");
        Arrays.stream(constructors).forEach(System.out::println);

        //2、获取所有构造器集合（包括私有构造器）
        Constructor[] declaredConstructors = aClass.getDeclaredConstructors();
        System.out.println("所有构造器集合（包括私有构造器）");
        Arrays.stream(declaredConstructors).forEach(System.out::println);

        //3、获取单个公共构造器
        Constructor constructor = aClass.getConstructor(String.class, Double.class, Integer.class);
        System.out.println("单个公共构造器");
        System.out.println(constructor);

        //4、获取单个私有构造器
        Constructor constructor2 = aClass.getDeclaredConstructor(String.class, Integer.class, String.class, Double.class);
        System.out.println("单个私有构造器");
        System.out.println(constructor2);
    }

    /**
     * 3、利用构造器Constructor对象创建对象
     */
    @Test
    public void test3() throws Exception {
        Class aClass = Class.forName("com.adolesce.server.javabasic.basic.reflex.GoodsEntity");
        //1、利用无参构造器创建对象
        Constructor constructor = aClass.getConstructor();
        Object o1 = constructor.newInstance();
        System.out.println("利用无参构造器创建对象1");
        System.out.println(o1);

        //2、利用无参构造器创建对象（简写方式）
        Object o2 = aClass.newInstance();
        System.out.println("利用无参构造器创建对象2");
        System.out.println(o2);

        //3、利用私有有参构造器创建对象（调用私有构造器要使用暴力反射）
        Constructor constructor2 = aClass.getDeclaredConstructor(String.class, Integer.class, String.class, Double.class);
        constructor2.setAccessible(true);
        Object o3 = constructor2.newInstance("图片路径1", 3, "商品1", 12.41);
        System.out.println("利用私有有参构造器创建对象3");
        System.out.println(o3);
    }

    /**
     * 4、反射获取属性字段Field对象
     */
    @Test
    public void test4() throws ClassNotFoundException, NoSuchFieldException {
        Class aClass = Class.forName("com.adolesce.server.javabasic.basic.reflex.GoodsEntity");
        //1、获取所有公共字段集合（包括父类public修饰字段）
        Field[] fields = aClass.getFields();
        System.out.println("公共字段集合");
        Arrays.stream(fields).forEach(System.out::println);

        //2、获取所有字段集合（包括私有字段）
        Field[] declaredFields = aClass.getDeclaredFields();
        System.out.println("所有字段集合（包括私有字段）");
        Arrays.stream(declaredFields).forEach(System.out::println);

        //3、获取单个公共字段
        Field nameField = aClass.getField("name");
        System.out.println("单个公共字段");
        System.out.println(nameField);

        //4、获取单个私有字段
        Field priceField = aClass.getDeclaredField("price");
        System.out.println("单个私有字段");
        System.out.println(priceField);

    }

    /**
     * 5、利用Field对象获取值和赋值
     */
    @Test
    public void test5() throws Exception {
        Class<?> aClass = Class.forName("com.adolesce.server.javabasic.basic.reflex.GoodsEntity");
        Object object = aClass.newInstance();
        //1、为公共Field字段设置值
        Field nameField = aClass.getField("name");
        nameField.set(object, "华为手机");

        Field stockField = aClass.getField("stock");
        stockField.set(object, 99);

        System.out.println("设置了商品名称和库存：" + object);

        //2、获取公共Field字段值
        Object name = nameField.get(object);
        Object stock = stockField.get(object);
        System.out.println("获取商品名称和库存：" + name + "  " + stock);

        //3、为私有Field字段设置值
        Field priceField = aClass.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(object, 12.99);
        System.out.println("设置了商品价格：" + object);

        //4、获取私有Field字段值
        Object price = priceField.get(object);
        System.out.println(price);
        System.out.println("获取商品价格：" + price);
    }

    /**
     * 6、反射获取Method对象
     */
    @Test
    public void test6() throws ClassNotFoundException, NoSuchMethodException {
        Class aClass = Class.forName("com.adolesce.server.javabasic.basic.reflex.GoodsEntity");
        //1、获取所有公共方法集合（包括父类public修饰方法）
        Method[] methods = aClass.getMethods();
        System.out.println("公共方法集合");
        Arrays.stream(methods).forEach(System.out::println);

        //2、获取所有方法集合（包括私有方法）
        Method[] declaredMethods = aClass.getDeclaredMethods();
        System.out.println("所有方法集合（包括私有方法）");
        Arrays.stream(declaredMethods).forEach(System.out::println);

        //3、获取单个公共方法
        Method printStockMethod = aClass.getMethod("printStock", String.class);
        System.out.println("单个公共方法");
        System.out.println(printStockMethod);

        //4、获取单个私有字段
        Method printPirceMethod = aClass.getDeclaredMethod("printPirce", String.class);
        System.out.println("单个私有方法");
        System.out.println(printPirceMethod);
    }

    /**
     * 7、利用Method对象运行方法
     */
    @Test
    public void test7() throws Exception {
        Class aClass = Class.forName("com.adolesce.server.javabasic.basic.reflex.GoodsEntity");
        GoodsEntity object = (GoodsEntity)aClass.newInstance();
        //调用父类公共方法
        Method setStockMethod = aClass.getMethod("setStock", Integer.class);
        setStockMethod.invoke(object,99);

        Method printStockMethod = aClass.getMethod("printStock", String.class);
        printStockMethod.invoke(object, "aaa");

        //调用子类私有方法
        Method setPriceMethod = aClass.getDeclaredMethod("setPrice", Double.class);
        setPriceMethod.invoke(object,99.98);

        /*Field priceField = aClass.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(object,99.99);*/

        Method printPirceMethod = aClass.getDeclaredMethod("printPirce", String.class);
        printPirceMethod.setAccessible(true);
        printPirceMethod.invoke(object,"bbb");
    }

    @Test
    public void testMap() throws Exception {
        HashMap<String,Integer> hashMap = HashMap.class.newInstance();
        Method putMethod = HashMap.class.getMethod("put", Object.class, Object.class);
        putMethod.invoke(hashMap,"age",12);

        Method getMethod = HashMap.class.getMethod("get", Object.class);
        Integer age = (Integer)getMethod.invoke(hashMap, "age");
        System.out.println(age);

        Method entrySetMethod = HashMap.class.getMethod("entrySet");
        Set<Map.Entry<Object,Object>> entrySet = (Set<Map.Entry<Object, Object>>) entrySetMethod.invoke(hashMap);
        entrySet.forEach(entry -> System.err.println(entry.getKey() + ":" +entry.getValue()));

    }

    @Test
    public void testMapDeclared() {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("like_name", "zhangsan");
        paramMap.put("ge_price", 10);
        paramMap.put("le_price", 20);
        paramMap.put("in_code", Arrays.asList(new Integer[]{2, 5, 6, 8}));

        //获取实体类所有属性名
        Field[] goodsEntityFields = GoodsEntity.class.getDeclaredFields();
        Set<String> goodsEntityFieldNames = Arrays.stream(goodsEntityFields)
                .map(field -> field.getName()).collect(Collectors.toSet());

        //获取Compare类的方法对象Map（方法名称 -> 方法对象） 弊端：同名方法存在覆盖
        /*Method[] mybatisPlusMethods = Compare.class.getDeclaredMethods();
        Map<String, Method> mybatisPlusMethodMap = Arrays.stream(mybatisPlusMethods).collect(Collectors.toMap(
                method -> method.getName(), Function.identity(), (o1, o2) ->
                         o1.getParameterCount() < o2.getParameterCount()? o1:o2
        ));*/

        //循环前端传参列表，反射设置查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        paramMap.forEach((k, v) -> {
            String[] karr = k.split("_");
            if (karr.length > 1) {
                String operator = karr[0];
                String fieldName = karr[1];
                if (goodsEntityFieldNames.contains(fieldName)) {
                    try {
                        //Method method = mybatisPlusMethodMap.get(operator);
                        Method method;
                        if (v instanceof List) {
                            method = Func.class.getDeclaredMethod(operator, Object.class, Collection.class);
                        } else {
                            method = Compare.class.getDeclaredMethod(operator, Object.class, Object.class);
                        }
                        if (ObjectUtil.isNotEmpty(method)) {
                            method.invoke(queryWrapper, fieldName, v);
                            System.out.println(operator + ":" + fieldName);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println(queryWrapper);
    }

    /**
     * 需求：
     * 前端灵活传参，参数封装在paramMap中
     * 现在需要实现动态查询：
     * 需要把paramMap中的参数动态设置进 QueryWrapper中
     * 发起查询，最后的结果会封装至GoodsEntity对象中
     */
    @Test
    public void testMapDeclared2() throws Exception {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("like_name", "zhangsan");
        paramMap.put("ge_price", 10);
        paramMap.put("le_price", 20);
        paramMap.put("in_code", Arrays.asList(new Integer[]{2, 5, 6, 8}));

        //1、根据反射拿到GoodsEntity中的字段名称，用于后续判断paramMap中的参数是否存在
        Field[] declaredFields = GoodsEntity.class.getDeclaredFields();
        Set<String> declaredFieldsSet = Arrays.stream(declaredFields).map(field -> field.getName()).collect(Collectors.toSet());

        //2、新建QueryWrapper对象
        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.like("name","zhangsan")''

        //3、循环遍历参数Map，将其中的参数根据反射设置进QueryWrapper中
        paramMap.forEach((k, v) -> {
            String methodName = k.split("_")[0];
            String fieldName = k.split("_")[1];
            if (declaredFieldsSet.contains(fieldName)) {
                try {
                    Method declaredMethod;
                    if (v instanceof List) {
                        declaredMethod = AbstractWrapper.class.getMethod(methodName, Object.class, Collection.class);
                    } else {
                        declaredMethod = AbstractWrapper.class.getMethod(methodName, Object.class, Object.class);
                    }
                    declaredMethod.invoke(queryWrapper, fieldName, v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(queryWrapper);
    }
}
