package com.person.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: pzy
 * @create: 2019/8/22 16:57
 */
@Slf4j
public class FtpUtils {


    public static boolean uploadFile(String host, int port, String username, String password, String imagePath, String fileName, InputStream inputStream) {
        boolean flag = false;
        FTPClient ftp = new FTPClient();

        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return flag;
            }
            //部署到线上配置
            ftp.enterLocalPassiveMode();

            if (!ftp.changeWorkingDirectory(imagePath)) {
                ftp.makeDirectory(imagePath);
            }
            ftp.changeWorkingDirectory(imagePath);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(fileName, inputStream)) {
                log.error("storeFile失败！");
                return flag;
            }
            inputStream.close();
            ftp.logout();
            flag = true;
        } catch (Exception e) {
            log.error("上传图片异常:{}", e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }

}
