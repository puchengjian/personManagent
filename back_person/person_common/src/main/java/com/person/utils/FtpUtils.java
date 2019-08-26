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
            log.info("ftp连接成功");
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            log.info("ftp登录成功");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.error("登录失败，状态码{}", reply);
                ftp.disconnect();
                return flag;
            }

            if (!ftp.changeWorkingDirectory(imagePath)) {
                ftp.makeDirectory(imagePath);
            }
            ftp.changeWorkingDirectory(imagePath);
            //被动模式 服务端开端口，客户端连接
            //主动模式 客户端开端口，服务器连接
            //设置被动模式 默认主动模式 客户端不可控，防火墙等原因，需要服务端开启端口
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(fileName, inputStream)) {
                log.error("storeFile失败！");
                return flag;
            }
            log.info("ftp上传成功");
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
