package com.jfbian;

import com.jfbian.util.WebUtil;

/**
 * selenium-java版本为4.0.0-alpha-2 谷歌版本为78.0.3904.97 ChromeDriver版本为 78.0.3904.70
 *
 */
public class App {
    public static void main(String[] args) {

        // 访问百度首页
        WebUtil.openWindows("https://www.baidu.com/");
        WebUtil.setWindowsSize();
        // 获得网站的标题打印
        System.out.println(WebUtil.getWebTitle());
        System.out.println(WebUtil.getUrls());
        System.out.println(WebUtil.getCookies());
        WebUtil.sendKey("input#kw", "hahaha");
        WebUtil.sleep(10);
        WebUtil.clickElementsByJquery(WebUtil.getWebElement("input#su"));
        System.out.println(WebUtil.getUrls());
        System.out.println("cpu数量" + Runtime.getRuntime().availableProcessors());
        WebUtil.sleep(10);
        WebUtil.webClose();
        WebUtil.systemClose();
    }
}
