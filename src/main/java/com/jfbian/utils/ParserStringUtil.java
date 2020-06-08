/**
 * @Title:  asdf.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月14日 下午10:31:12
 * @version V1.0
 */
package com.jfbian.utils;

public class ParserStringUtil {
    /**
     * 字符串格式化
     *
     * @param postData 未添加参数的postData(${num} 为参数 )
     * @param args 参数
     * @return String
     */
    public static String formatPostData(String postData, String... args) {
        StringBuffer result = new StringBuffer(postData);
        int countStr = countStr(postData, "${");
        for (int i = 1; i <= countStr; i++) {
            int start = result.indexOf("${");
            int end = start + 3 + String.valueOf(i).length();
            result.replace(start, end, args[i - 1]);
        }
        return result.toString();
    }

    public static int countStr(String str, String sToFind) {
        int num = 0;
        while (str.contains(sToFind)) {
            str = str.substring(str.indexOf(sToFind) + sToFind.length());
            num++;
        }
        return num;
    }
}
