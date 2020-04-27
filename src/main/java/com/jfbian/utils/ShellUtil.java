/**
 * @Title:  阿什顿飞.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月8日 上午9:25:57
 * @version V1.0
 */
package com.jfbian.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * SSH登录Linux调用shell工具类
 */
public class ShellUtil {

    public static int execute(String username, String ip, int port, String password, String command) {
        System.out.println("进入ShellUtils工具类的execute方法");
        int returnCode = 0;
        final JSch jsch = new JSch();
        final MyUserInfo userInfo = new ShellUtil().new MyUserInfo();
        final List<String> stdout = new ArrayList<>();

        try {
            //创建session并且打开连接，因为创建session之后要主动打开连接
            System.out.println("创建session并且打开连接");
            final Session session = jsch.getSession(username, ip, port);
            session.setPassword(password);
            //此处必须设置userInfo
            session.setUserInfo(userInfo);
            session.connect();
            final Channel channel = session.openChannel("exec");
            final ChannelExec channelExec = (ChannelExec)channel;
            System.out.println("打开通道、执行的命令，command = " + command);
            channelExec.setCommand(command);
            channelExec.setInputStream(null);

            final BufferedReader input = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
            channelExec.connect();

            //接收远程服务器执行命令的结果
            input.lines().forEach(ele -> stdout.add(ele));

            input.close();

            //关闭通道
            channelExec.disconnect();
            //关闭session
            session.disconnect();

            System.out.println("执行命令返回 ：" + stdout.toString());
        } catch (final Exception e) {
            System.out.println("ShellUtils工具类的execute方法执行异常");
            returnCode = 1;
            e.printStackTrace();
        }

        return returnCode;
    }

    public static int executeMore(String username, String ip, int port, String password, String[] commands) {
        System.out.println("进入ShellUtils工具类的execute方法");
        int returnCode = 0;
        final JSch jsch = new JSch();
        final MyUserInfo userInfo = new ShellUtil().new MyUserInfo();

        try {
            //创建session并且打开连接，因为创建session之后要主动打开连接
            System.out.println("创建session并且打开连接");
            final Session session = jsch.getSession(username, ip, port);
            session.setPassword(password);
            //此处必须设置userInfo
            session.setUserInfo(userInfo);
            session.connect();

            final ChannelShell channel = (ChannelShell)session.openChannel("shell");
            channel.setPtyType("dumb");
            channel.connect();
            final InputStream inputStream = channel.getInputStream();
            final OutputStream outputStream = channel.getOutputStream();
            for (final String command : commands) {
                final String cmd = command + " \r\n";
                outputStream.write(cmd.getBytes());
            }
            final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            //退出linux系统
            final String cmd2 = "exit \r\n";
            outputStream.write(cmd2.getBytes());
            outputStream.flush();

            //接收远程服务器执行命令的结果
            final StringBuilder msgs = new StringBuilder();
            String msg = null;
            while ((msg = in.readLine()) != null) {
                msgs.append(msg + "\r\n");
            }
            in.close();

            //关闭通道
            channel.disconnect();
            //关闭session
            session.disconnect();
            System.out.println("=======================");
            System.out.println("执行命令返回 ：" + msgs.toString());
            System.out.println("=======================");
        } catch (final Exception e) {
            System.out.println("ShellUtils工具类的execute方法执行异常");
            returnCode = 1;
            e.printStackTrace();
        }

        return returnCode;
    }

    public class MyUserInfo implements UserInfo {

        @Override
        public String getPassphrase() {
            System.out.println("MyUserInfo.getPassphrase()");
            return null;
        }

        @Override
        public String getPassword() {
            System.out.println("MyUserInfo.getPassword()");
            return null;
        }

        @Override
        public boolean promptPassphrase(String arg0) {
            System.out.println("MyUserInfo.promptPassphrase()");
            System.out.println(arg0);
            return false;
        }

        @Override
        public boolean promptPassword(String arg0) {
            System.out.println("MyUserInfo.promptPassword()");
            System.out.println(arg0);
            return false;
        }

        @Override
        public boolean promptYesNo(String arg0) {
            System.out.println("MyUserInfo.promptYesNo()");
            System.out.println(arg0);
            if (arg0.contains("The authenticity of host")) {
                return true;
            }
            return true;
        }

        @Override
        public void showMessage(String arg0) {
            System.out.println("MyUserInfo.showMessage()");
        }

    }
}
