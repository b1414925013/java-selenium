package com.jfbian.utils;

import java.io.File;

/**
 * @ClassName:  pathUtil
 * @Description:路径工具类
 * @author: bianjianfeng
 * @date:   2020-04-12 08:57:14
 */
public class PathUtil {

    /**
    *
    * @Title: getRootPath
    * @Description: 获取项目根目录
    * @return: String
    */
    public static String getRootPath() {
        return System.getProperty("user.dir") + File.separator;
    }

    /**
     *
     * @Title: getTemporaryFolder
     * @Description: 获取临时文件夹
     * @return: String
     */
    public static String getTemporaryFolder() {
        return System.getProperty("java.io.tmpdir");
    }
}
