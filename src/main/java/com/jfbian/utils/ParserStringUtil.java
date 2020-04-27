/**
 * @Title:  asdf.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月14日 下午10:31:12
 * @version V1.0
 */
package com.jfbian.utils;

import java.text.MessageFormat;

public class ParserStringUtil {
    /**
     * 将字符串text中由openToken和closeToken组成的占位符依次替换为args数组中的值
     * @param openToken
     * @param closeToken
     * @param text
     * @param args
     * @return
     */
    public static String parse(String openToken, String closeToken, String text, Object... args) {
        if (args == null || args.length <= 0) {
            return text;
        }
        int argsIndex = 0;
        if (text == null || text.isEmpty()) {
            return "";
        }
        final char[] src = text.toCharArray();
        int offset = 0;
        // search open token
        int start = text.indexOf(openToken, offset);
        if (start == -1) {
            return text;
        }
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    ///////////////////////////////////////仅仅修改了该else分支下的个别行代码////////////////////////
                    final String value = argsIndex <= args.length - 1
                        ? args[argsIndex] == null ? "" : args[argsIndex].toString() : expression.toString();
                    builder.append(value);
                    offset = end + closeToken.length();
                    argsIndex++;
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                }
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    public static String parse0(String text, Object... args) {
        return ParserStringUtil.parse("${", "}", text, args);
    }

    public static String parse1(String text, Object... args) {
        return ParserStringUtil.parse("{", "}", text, args);
    }

    /**
     * 使用示例
     * @param args
     */
    public static void main(String... args) {
        //{}被转义，不会被替换
        System.out.println(ParserStringUtil.parse("{", "}", "我的名字是\\{},结果是{}，可信度是%{}", "雷锋", true, 100));
        System.out.println(ParserStringUtil.parse0("我的名字是${},结果是${}，可信度是%${}", "雷锋", true, 100));
        System.out.println(ParserStringUtil.parse1("我的名字是{},结果是{}，可信度是%{}", "雷锋", true, 100));
        System.out.println(MessageFormat.format("我是{0},我来自{1},今年{2}岁", "中国人", "北京", "22"));
        System.out.println(ParserStringUtil.parse0("{\"id\":\"${}\",\"name\":\"${}\"}", "编号", "姓名"));
        System.out.println(ParserStringUtil.parse0("{'id':'${}','name':'${}'}", "编号", "姓名"));
    }
}
