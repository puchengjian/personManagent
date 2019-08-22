package com.person.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author: pzy
 * @create: 2019/8/22 9:05
 */
@Slf4j
public class FileUtils {

    /**
     * 上传文件
     *
     * @param file     文件
     * @param path     路径
     * @param fileName 文件名称
     */
    public static boolean uploadFile(MultipartFile file, String path, String fileName) {
        boolean flag = true;
        //生成新的文件名
        String realPath = path + fileName;
        File dest = new File(realPath);

        File parentFile = dest.getParentFile();
        while (parentFile.exists()) {
            parentFile.mkdir();
            parentFile = parentFile.getParentFile();
        }

        try {
            //保存文件
            file.transferTo(dest);
        } catch (Exception e) {
            log.error("上传文件异常:{}", e);
            flag = false;
        }

        return flag;
    }


    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     *
     * @param fileOriginName 源文件名
     */
    public static String getFileName(String fileOriginName) {
        return UUIDUtils.getUUID() + getSuffix(fileOriginName);
    }

}
