/**
 * @Title:  FTPUtilTest.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月8日 下午3:58:14
 * @version V1.0
 */
package com.jfbian.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import org.junit.Test;

import java.io.File;

/**
 * @ClassName:  FTPUtilTest
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2019年12月8日 下午3:58:14
 */
class FTPUtilTest {

    @Test
    public void uploadFile() {
        final File file = new File("D://36.txt");
        final FTPUtil ft = new FTPUtil();
        final Session s = ft.getSession("192.168.17.129", 22, "root", "root");
        final Channel channel = ft.getChannel(s);
        final ChannelSftp sftp = (ChannelSftp)channel;
        final String upload = ft.uploadFile(sftp, "/root/", file);
        System.out.println(upload);
        ft.closeAll(sftp, channel, s); //关闭连接
    }

    @Test
    public void deleteFile() {
        final FTPUtil ft = new FTPUtil();
        final Session s = ft.getSession("192.168.17.129", 22, "root", "root");
        final Channel channel = ft.getChannel(s);
        final ChannelSftp sftp = (ChannelSftp)channel;
        final String delete = ft.delete("/root/", "36.txt", sftp);
        System.out.println(delete);
        ft.closeAll(sftp, channel, s); //关闭连接
    }

    @Test
    public void getFile() {
        final FTPUtil ft = new FTPUtil();
        final Session s = ft.getSession("192.168.17.129", 22, "root", "root");
        final Channel channel = ft.getChannel(s);
        final ChannelSftp sftp = (ChannelSftp)channel;
        final String download = ft.download("/root", "666.txt", "D:\\", sftp);
        System.out.println(download);
        ft.closeAll(sftp, channel, s); //关闭连接
    }

}
