package com.jfbian.utils;

import java.util.regex.Pattern;

/**
 * @ClassName:  RegexUtils
 * @Description:正则工具类
 * @author: bianjianfeng
 * @date:   2020-04-12 21:05:26
 */
public class RegexUtil {
    /**
    *
    * @Title: CheckContainChinese
    * @Description: 检查字符串中有无中文字符
    * @param str
    * @return: boolean
    */
    public static boolean CheckContainChinese(String str) {
        for (String splitStr : str.split("")) {
            if (Pattern.matches("[\\u4E00-\\u9FA5]*?", splitStr)) {
                return true;
            }
        }
        return false;
    }
}
