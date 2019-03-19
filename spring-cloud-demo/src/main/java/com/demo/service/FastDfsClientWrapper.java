package com.demo.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/1115:26
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FastDfsClientWrapper {

    private final FastFileStorageClient fastFileStorageClient;
    private final AppendFileStorageClient appendFileStorageClient;


    /**
     * 批量保存文件
     *
     * @param files 文件集
     * @return 上传成功文件路径
     */
    public List<String> uploadFile(List<MultipartFile> files) {
        if (files.isEmpty()) {
            throw new IllegalArgumentException("上传文件为空");
        }
        List<String> filePaths = new ArrayList<>();
        files.forEach((MultipartFile file) -> {
            try {
                StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
                filePaths.add(getResAccessUrl(storePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return filePaths;
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException 异常
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension 文件备注
     * @return 文件访问地址
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    private String getResAccessUrl(StorePath storePath) {
        log.info("文件资源信息：{}", storePath.toString());
        return storePath.getFullPath();
    }


    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     */
    public void deleteFile(String fileUrl) {
        Assert.hasText(fileUrl, "文件路径不可为空");
        int pathStartPos = fileUrl.indexOf("/");
        fastFileStorageClient.deleteFile(fileUrl.substring(0, pathStartPos), fileUrl.substring(pathStartPos + 1));
    }


}
