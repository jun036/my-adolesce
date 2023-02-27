package com.adolesce.server.autoconfig;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.adolesce.autoconfig.template.MinioTemplate;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/9/1 11:52
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MinioTest {
    @Autowired
    private MinioTemplate minioTemplate;

     @Before
    public void before() {
        if(ObjectUtil.isEmpty(minioTemplate)){
            MinioClient minioClient = MinioClient
                    .builder()
                    .credentials("minioadmin", "minioadmin")
                    .endpoint("http://localhost:9000")
                    .build();
            minioTemplate = new MinioTemplate(minioClient);
        }
    }

    //测试创建桶
    @Test
    public void testCreateBucket() {
        String bucketName = "mytest-bucket111";
        boolean result = minioTemplate.createBucket(bucketName);
        System.out.println(result ? "创建桶成功！" : "创建桶失败");
    }

    //测试查询所有桶名称
    @Test
    public void testListBucketNames() {
        List<String> buckets = minioTemplate.listBucketNames();
        buckets.forEach(System.out::println);
    }

    //测试删除指定桶
    @Test
    public void testRemoveBucket() {
        String bucketName = "ly-bucket";
        boolean result = minioTemplate.removeBucket(bucketName);
        System.out.println(result ? "删除桶成功！" : "删除桶失败");
    }

    //测试在桶中创建文件夹
    @Test
    public void testMkDir() {
        String bucketName = "lwd-bucket";
        String dir = "school/class/";
        minioTemplate.createFile(bucketName, dir);
    }

    //测试查询桶中所有对象
    @Test
    public void testQueryObjects() {
        String bucketName = "lwd-bucket";
        List<String> objectNames = minioTemplate.listObjectNames(bucketName);
        objectNames.forEach(System.out::println);
    }

    //测试上传文件
    @Test
    public void testUploadFile1() throws FileNotFoundException {
        //1、桶
        String bucketName = "mytest-bucket111";
        //2、上传上去的路径文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        String objectName = todayStr + "/myhourse.jpg";
        //3、文件流
        String filePath = "D://some-test-file/pic/hourse2.jpg";
        FileInputStream in = new FileInputStream(filePath);
        //4、上传
        ObjectWriteResponse objectWriteResponse = minioTemplate.putObject(bucketName, objectName, in, "image/jpg");  // text/html
        //5、打印文件路径
        String path = "http://localhost:9000/" + bucketName + "/" + objectName;
        System.out.println(path);
    }

    //测试上传文件
    @Test
    public void testUploadFile2(){
        //1、桶
        String bucketName = "mytest-bucket";
        //2、上传上去的路径文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        String objectName = todayStr + "/16259756545903123.jpg";
        //3、文件流
        String filePath = "D:\\some-test-file\\pic\\16259756545903123.jpg";
        //4、上传
        ObjectWriteResponse objectWriteResponse = minioTemplate.putObject(bucketName, objectName,filePath);  // text/html
        //5、打印文件路径
        String path = "http://localhost:9000/" + bucketName + "/" + objectName;
        System.out.println(path);
    }

    //测试下载文件至本地
    @Test
    public void testDownFile1() {
        //1、桶
        String bucketName = "mytest-bucket";
        //2、上传上去的路径文件名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        //String objectName = todayStr + "/myhourse.jpg";
        String objectName = "2022/09/01/myhourse.jpg";
        //3、下载
        String filePath = "D://1.jpg";
        InputStream in = minioTemplate.getObjectForInputStream(bucketName,objectName);
        FileUtil.writeFromStream(in,new File(filePath));
    }

    //测试下载文件至字节数组
    @Test
    public void testDownFile2() throws IOException {
        //1、桶
        String bucketName = "mytest-bucket";
        //2、上传上去的路径文件名
        String objectName = "2022/09/01/myhourse.jpg";
        //3、下载
        byte[] bytes = minioTemplate.getObjectForBytes(bucketName,objectName);
        //byte[] 转换为bufferedImage
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(in);
        System.out.println(bufferedImage);
    }

    //测试获取文件外链
    @Test
    public void testGetObjectURL() {
        //1、桶
        String bucketName = "mytest-bucket";
        //2、上传上去的路径文件名
        String objectName = "2022/09/01/myhourse.jpg";
        //3、设置有效期
        String objectURL = minioTemplate.getObjectURL(bucketName, objectName,0);
        System.out.println(objectURL);
    }
}
