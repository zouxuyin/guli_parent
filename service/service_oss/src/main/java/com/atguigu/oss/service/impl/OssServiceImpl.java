package com.atguigu.oss.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;


@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile multipartFile) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String buckeName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;

        try {
            //获取文件名称的数据流
            inputStream = multipartFile.getInputStream();

            //获取文件名称
            String originalFilename = multipartFile.getOriginalFilename();

            //1在文件名称里加随机唯一的值
//            String uuid = UUID.randomUUID().toString().replace("-","");
//            originalFilename=uuid+originalFilename;
            //2把文件按照日期进行分类
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            originalFilename=datePath+"/"+originalFilename;



            //调用oss方法上传
            //第一个参数 bucket名称,二个上传oss文件路径，和文件名称

            ossClient.putObject(buckeName, originalFilename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把文件上传到阿里云oss路径手动拼接
            String url="https://"+buckeName+"."+endpoint+"/"+originalFilename;
            //URL
            //https://zou1010.oss-cn-beijing.aliyuncs.com/u%3D2344451607%2C2404623174%26fm%3D111%26gp%3D0.jpg
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
