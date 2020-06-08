package com.jfbian;

import org.openqa.selenium.WebElement;

import com.jfbian.utils.WebUtil;

/**
 * selenium-java版本为4.0.0-alpha-2 谷歌版本为83.0.4103.97 ChromeDriver版本为 83.0.4103.39
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

        WebElement webElement = WebUtil.getWebElement("form#form");
        WebElement getprevElement = WebUtil.getprevElement(webElement);
        String attribute = getprevElement.getAttribute("id");
        String attribute2 = getprevElement.getAttribute("class");
        String attribute3 = getprevElement.getAttribute("href");
        System.out.println(attribute);
        System.out.println(attribute2);
        System.out.println(attribute3);

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
