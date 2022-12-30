package com.adolesce.autoconfig.template;

import com.adolesce.autoconfig.config.MinioProperties;
import com.google.common.collect.Lists;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@EnableConfigurationProperties({MinioProperties.class})
public class MinioTemplate {
    @Autowired
    private MinioClient minioClient;

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    @SneakyThrows
    public boolean createBucket(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            return true;
        }
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        return true;
    }

    /**
     * 列出所有存储桶名称
     *
     * @return
     */
    @SneakyThrows
    public List<String> listBucketNames() {
        List<Bucket> list = listBuckets();
        return list.stream().filter(Objects::nonNull).map(o -> o.name()).collect(Collectors.toList());
    }

    /**
     * 列出所有存储桶
     *
     * @return
     */
    @SneakyThrows
    public List<Bucket> listBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 删除存储桶
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public boolean removeBucket(String bucketName) {
        Iterable<Result<Item>> myObjects = listObjects(bucketName);
        for (Result<Item> result : myObjects) {
            Item item = result.get();
            // 有对象文件，则删除失败
            if (item.size() > 0) {
                return false;
            }
        }
        // 删除存储桶，注意，只有存储桶为空时才能删除成功。
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        return !bucketExists(bucketName);
    }


    /**
     * 在桶里创建文件夹对象
     *
     * @param fileName
     */
    @SneakyThrows
    public void createFile(String bucketName, String fileName) {
        if (!bucketExists(bucketName)) {
            //minio服务器创建桶
            createBucket(bucketName);
        }
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                new ByteArrayInputStream(new byte[]{}), 0, -1)
                .build());
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketName) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 列出存储桶中的所有对象名称
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public List<String> listObjectNames(String bucketName) {
        List<String> ret = Lists.newArrayList();
        Iterable<Result<Item>> myObjects = listObjects(bucketName);
        for (Result<Item> result : myObjects) {
            Item item = result.get();
            ret.add(item.objectName());
        }
        return ret;
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param multipartFile
     */
    @SneakyThrows
    public ObjectWriteResponse putObject(String bucketName, MultipartFile multipartFile) {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(multipartFile.getName())
                .contentType(multipartFile.getContentType())
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .build();

        return minioClient.putObject(args);
    }

    /**
     * 通过InputStream上传对象
     *
     * @param bucketName  存储桶名称
     * @param objectName  存储桶里的对象名称
     * @param in          要上传的流
     * @param contentType 要上传的文件类型 MimeTypeUtils.IMAGE_JPEG_VALUE
     * @return
     */
    @SneakyThrows
    public ObjectWriteResponse putObject(String bucketName, String objectName, InputStream in, String contentType) {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .contentType(contentType)
                .stream(in, in.available(), -1)
                .build();
        return minioClient.putObject(args);
    }

    /**
     * 本地文件上传
     *
     * @param bucketName  存储桶名称
     * @param objectName  存储桶里的对象名称
     * @param filePath  本地文件路径
     * @return
     */
    @SneakyThrows
    public ObjectWriteResponse putObject(String bucketName, String objectName, String filePath) {
        ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName).object(objectName).filename(filePath).build());
        return objectWriteResponse;
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public InputStream getObjectForInputStream(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (!flag) {
            return null;
        }
        StatObjectResponse resp = statObject(bucketName, objectName);
        return resp == null ? null : minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 以字节数组的形式获取一个文件对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public byte[] getObjectForBytes(String bucketName, String objectName) {
        InputStream inputStream = getObjectForInputStream(bucketName, objectName);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while (true) {
            try {
                if (!((rc = inputStream.read(buff, 0, 100)) > 0)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public StatObjectResponse statObject(String bucketName, String objectName) {
        return !bucketExists(bucketName)
                ? null : minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     * @throws Exception
     */
    @SneakyThrows
    public void removeMinio(String bucketName,String objectName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expireTime 有效期，秒（1秒到7天之间）
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, int expireTime) {
        GetPresignedObjectUrlArgs.Builder builderArgs = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(objectName);
        if(expireTime != 0){
            builderArgs.expiry(expireTime);
        }
        GetPresignedObjectUrlArgs build = builderArgs.build();
        return minioClient.getPresignedObjectUrl(build);
    }
}

