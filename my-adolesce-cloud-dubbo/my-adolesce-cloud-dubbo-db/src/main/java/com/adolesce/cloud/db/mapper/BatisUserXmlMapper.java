package com.adolesce.cloud.db.mapper;


import com.adolesce.cloud.dubbo.domain.db.BatisAddress;
import com.adolesce.cloud.dubbo.domain.db.BatisUser;

import java.util.List;
import java.util.Map;

public interface BatisUserXmlMapper {
    /**
     * mapper接口传参问题
     *  1、mapper接口中形参只有一个时
     *      1）、如果形参是非对象和非map类型，在xml的#{}中填写任意变量值都可以取到值，mybatis可以获取到唯一的参数
     *      2）、如果形参是对象或map类型，在在xml的#{}中直接填写对象中的属性或者map中的key可以取到值
     *
     *  2、mapper接口中形参有多个参数时：
     *      1）、JDK8 xml中可以用 param1, param2,....进行取值
     *          1、如果形参是非对象和非map类型，比如第一个形参是字符串，在xml的#{}中直接用#{param1}取值
     *          2、如果形参是对象或map类型，比如第一个形参是对象，在xml的#{}中用#{param1.属性名}取值
     *
     *      2）、编辑器如果是IDEA，可以用Settings | Build, Execution, Deployment | Compiler | Java Compiler 下方添加-parameters参数后
     *           注意：如果把这个 -parameters去掉，就不能直接用形参去取值，只能用arg0、arg1|param1、param2或者通过@Param方式了
     *           1、如果形参是非对象和非map类型，比如第一个形参是字符串，在xml的#{}中直接用   #{形参名}取值
     *           2、如果形参是对象或map类型，比如第一个形参是对象，      在xml的#{}中用      #{形参名.属性名}取值
     *
     *      3）、如果形参中使用了@Param("参数名")
     *           1、如果形参是非对象和非map类型，比如第一个形参是字符串，在xml的#{}中直接用   #{参数名}取值
     *           2、如果形参是对象或map类型，比如第一个形参是对象，      在xml的#{}中用      #{参数名.属性名}取值
     *
     *  3、其他：
     *      1）、@Param使用场景
     *           第一种：方法有多个参数，需要@Param（最常见）
     *           第二种：方法参数要取别名，需要@Param
     *           第三种：xml中的SQL使用了$，需要@Param
     *
     *      2）、默认形参名
     *          1、使用jdk1.7得到的是: [1, 0, param1, param2]
     *          2、使用1.8得到的则是: [arg1, arg0, param1, param2]
     *          3、但这种方法不建议使用，sql层表达不直观，且一旦顺序调整容易出错。
     *          select * from t_device where device_id = #{arg0} and device_name != #{arg1}
     *
     *      3）、 idea有时可以不加@Param,那么它 对我的代码做了什么？
     *          IDEA编译时采取了强制保持方法参数变量名，但需要满足如下
     *          1. 必须是jdk8或以上
     *          2. 编译器参数-parameters
     *          原文链接：https://blog.csdn.net/neusoft2016/article/details/110818507
     */
    List<BatisUser> queryByNameAndAge(Map<String,Object> paramMapper, int age);

    Integer insert(BatisUser paramUsers);

    void batchInsert(List<BatisUser> usersList);

    void deleteById(Long paramLong);

    void deleteByIdsWithList(List<Long> ids);

    void deleteByIdsWithMap(Map<String, Object> ids);

    void deleteByIdsStr1(String ids, String password);

    void deleteByIdsStr2(Map<String, Object> params);

    void deleteByIdsStr3(BatisUser batisUser);

    void update(BatisUser paramUsers);

    BatisUser getById(Long paramLong);

    List<BatisUser> queryByParam(BatisUser user);

    List<BatisUser> queryPageByName(String name);

    List<BatisUser> selectBatisUserByParams(Map<String,Object> params);

    List<BatisAddress> selectBatisAddressByParams(Map<String,Object> params);

    List<Map<String,Object>> queryResltWithMap();
}