package com.eyes.eyesspace.common.service.utils;

import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.utils.LogUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class QiniuUtils {
    private static final String accessKey = ConfigContext.getString("QINIU_ACCESS_KEY");
    private static final String secretKey = ConfigContext.getString("QINIU_SECRET_KEY");
    private static final String bucket = ConfigContext.getString("QINIU_BUCKET");
    private static final Auth auth = Auth.create(accessKey, secretKey);

    // 小文件上传(数据流)
    public static void sUpload2Qiniu(MultipartFile multipartFile, String fileName) throws CustomException {
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(multipartFile.getBytes());
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            uploadManager.put(byteInputStream, fileName, upToken,null, null);
        } catch (Exception ex) {
            LogUtils.error(LogUtils.logMap(ex.getMessage()));
            throw new CustomException("文件上传失败");
        }
    }

    // 大上传文件(分片上传)
    public static void bUpload2Qiniu(String localFilePath, String fileName) throws CustomException {
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        cfg.resumableUploadMaxConcurrentTaskCount = 10;
        String upToken = auth.uploadToken(bucket);
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        try {
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            uploadManager.put(localFilePath, fileName, upToken);
        } catch (IOException ex) {
            LogUtils.error(LogUtils.logMap(ex.getMessage()));
            throw new CustomException("文件上传失败！");
        }
    }

    // 删除文件
    public static void deleteFileFromQiniu(String fileName) throws CustomException {
        Configuration cfg = new Configuration(Region.huanan());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            throw new CustomException("文件删除失败！");
        }
    }
}
