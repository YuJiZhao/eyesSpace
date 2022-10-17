package com.eyes.eyesspace.common.service.service;

import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.utils.LogUtils;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadByUrlDto;
import com.eyes.eyesspace.common.feign.module.dto.FileUploadDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.eyes.eyesspace.common.service.utils.QiniuUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {
    private final HttpServletRequest request;

    public FileService(HttpServletRequest request) {
        this.request = request;
    }

    private final String FILE_BASE_URL = ConfigContext.getString("QINIU_FILE_BASEURL");
    private final String FOLDER_URL = ConfigContext.getString("FOLDER_URL");

    public FileUploadDto sUpload(MultipartFile multipartFile, String category) throws CustomException {
        String fileName = getRandomFileName(multipartFile, category);
        QiniuUtils.sUpload2Qiniu(multipartFile, fileName);
        return new FileUploadDto(FILE_BASE_URL + fileName);
    }

    public FileUploadDto bUpload(MultipartFile multipartFile, String category) throws CustomException {
        String fileName = getRandomFileName(multipartFile, category);

        File folder = new File(FOLDER_URL);
        File file = new File(folder, fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new CustomException("文件保存失败！", e.getMessage());
        }

        QiniuUtils.bUpload2Qiniu(FOLDER_URL + "\\" + fileName, fileName);

        if (!file.exists() || !file.isFile()) {
            LogUtils.warn(request, "无法删除本地文件!文件不存在或其为文件夹！");
        } else {
            boolean delete = file.delete();
            if(!delete) LogUtils.warn(request, "本地文件删除失败！");
        }

        return new FileUploadDto(FILE_BASE_URL + fileName);
    }

    public FileUploadByUrlDto uploadByUrl(String netAddress, String category) throws CustomException {
        int lastIndexOf = netAddress.lastIndexOf(".");
        String suffix = netAddress.substring(lastIndexOf - 1);
        String fileName = UUID.randomUUID() + suffix;
        String path = FOLDER_URL + "\\"+ fileName;

        // 下载到本地
        try {
            URL url = new URL(netAddress);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();

            FileOutputStream fileOutputStream = new FileOutputStream(path);

            int byteread;
            byte[] buffer = new byte[1024];
            while ((byteread = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteread);
            }
            fileOutputStream.close();
        } catch (Exception e) {
            throw new CustomException("文件下载本地错误！", e.getMessage());
        }

        return new FileUploadByUrlDto(FILE_BASE_URL + fileName);
    }

    /**
     * 计算文件名称
     * @param multipartFile
     * @param category
     * @return
     * @throws CustomException
     */
    private String getRandomFileName(MultipartFile multipartFile, String category) throws CustomException {
        String originalFilename = multipartFile.getOriginalFilename();
        if (Objects.isNull(originalFilename)) throw new CustomException("文件名为空！");
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(lastIndexOf - 1);
        return category + "/" + UUID.randomUUID() + suffix;
    }
}
