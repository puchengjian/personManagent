package com.person.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: pzy
 * @create: 2019/8/22 16:57
 */
@Slf4j
@Component
public class FtpUtils {

    private FTPClient ftpClient = null;

    @Value("${ftp.address}")
    private String host;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;


    /**
     * 上传文件
     *
     * @param path        路径
     * @param fileName    文件名称
     * @param inputStream
     */
    public boolean uploadFile(String path, String fileName, InputStream inputStream) {
        try {
            boolean flag = connect();
            if (!flag)
                return false;

            if (!ftpClient.changeWorkingDirectory(path))
                ftpClient.makeDirectory(path);

            //被动模式 服务端开端口，客户端连接
            //主动模式 客户端开端口，服务器连接
            //设置被动模式 默认主动模式 客户端不可控，防火墙等原因，需要服务端开启端口
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftpClient.storeFile(fileName, inputStream)) {
                log.error("storeFile失败！");
                return false;
            }
            log.info("ftp上传成功");
            inputStream.close();
            ftpClient.logout();
        } catch (Exception e) {
            log.error("ftp上传异常:{}", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    /**
     * 删除文件
     *
     * @param path     路径
     * @param fileName 文件名称
     */
    public boolean deleteFile(String path, String fileName) {
        boolean flag = false;
        try {
            flag = connect();
            if (flag)
                ftpClient.deleteFile(path + "/" + fileName);
        } catch (Exception e) {
            log.error("ftp删除异常:{}", e);
        }

        return flag;
    }


    /**
     * ftp连接登录
     */
    private boolean connect() {
        boolean flag = false;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(host, port);// 连接FTP服务器
            log.info("ftp连接成功");
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftpClient.login(username, password);// 登录
            log.info("ftp登录成功");
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                log.error("登录失败，状态码{}", reply);
                ftpClient.disconnect();
            }
            flag = true;
        } catch (Exception e) {
            log.error("连接失败:{}", e);
        }

        return flag;
    }

}
