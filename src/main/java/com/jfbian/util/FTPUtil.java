package com.jfbian.util;



import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/*四、Jsch中sftp提供的一些api

put()：      文件上传
get()：      文件下载
cd()：       进入指定目录
ls()：       得到指定目录下的文件列表
rename()：   重命名指定文件或目录
rm()：       删除指定文件
mkdir()：    创建目录
rmdir()：    删除目录*/
public class FTPUtil {

    /**
     * 参考实例
     *
     * @param session
     */
    // public static void main(String[] args) {
    // File file = new File("D://36.txt");
    // FTPUtil ft = new FTPUtil();
    // Session s = ft.getSession(SFTPInfo.SFTP_REQ_HOST,
    // SFTPInfo.SFTP_DEFAULT_PORT, SFTPInfo.SFTP_REQ_USERNAME,
    // SFTPInfo.SFTP_REQ_PASSWORD);
    // Channel channel = ft.getChannel(s);
    // ChannelSftp sftp = (ChannelSftp)channel;
    // String upload = ft.uploadFile(sftp,"hot_Imgs",file);
    // System.out.println(upload);
    // ft.closeAll(sftp, channel, s); //关闭连接
    // }
    public Channel getChannel(Session session) {
        Channel channel = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("get Channel success!");
            System.out.println();
        } catch (final JSchException e) {
            System.out.println("get Channel fail!");
        }
        return channel;
    }

    public Session getSession(String host, int port, String username, final String password) {
        Session session = null;
        try {
            final JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            final Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect();
            System.out.println("Session connected!");
        } catch (final JSchException e) {
            System.out.println("get Channel failed!");
        }
        return session;
    }

    /**
     * 创建文件夹
     *
     * @param sftp
     * @param dir
     *            文件夹名称
     */
    public void mkdir(ChannelSftp sftp, String dir) {
        try {
            sftp.mkdir(dir);
            System.out.println("创建文件夹成功！");
        } catch (final SftpException e) {
            System.out.println("创建文件夹失败！");
            e.printStackTrace();
        }
    }

    /**
     * @param sftp
     * @param dir
     *            上传目录
     * @param file
     *            上传文件
     * @return
     */
    public String uploadFile(ChannelSftp sftp, String dir, File file) {
        String result = "";
        try {
            sftp.cd(dir);
            // File file = new File("D://34.txt"); //要上传的本地文件
            if (file != null) {
                sftp.put(new FileInputStream(file), file.getName());
                result = "上传成功！";
            } else {
                result = "文件为空！不能上传！";
            }
        } catch (final Exception e) {
            System.out.println("上传失败！");
            result = "上传失败！";
        }
        return result;
    }

    /**
     * 下载文件
     *
     * @param directory
     *            下载目录
     * @param downloadFile
     *            下载的文件
     * @param saveFile
     *            存在本地的路径
     * @param sftp
     */
    public String download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        String result = "";
        try {
            sftp.cd(directory);
            sftp.get(downloadFile, saveFile);
            result = "下载成功！";
        } catch (final Exception e) {
            result = "下载失败！";
            System.out.println("下载失败！");

        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param directory
     *            要删除文件所在目录
     * @param deleteFile
     *            要删除的文件
     * @param sftp
     */
    public String delete(String directory, String deleteFile, ChannelSftp sftp) {
        String result = "";
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
            result = "删除成功！";
        } catch (final Exception e) {
            result = "删除失败！";
            System.out.println("删除失败！");
        }
        return result;
    }

    private void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    private void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public void closeAll(ChannelSftp sftp, Channel channel, Session session) {
        try {
            closeChannel(sftp);
            closeChannel(channel);
            closeSession(session);
        } catch (final Exception e) {
            System.out.println("closeAll");
        }
    }
}