package com.jfbian.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @ClassName: WebUtil
 * @Description:web操作类
 * @author: bianjianfeng
 * @date: Oct 21, 2019 9:07:15 PM
 */
public class WebUtil {
    public static WebDriver driver = WebDriverUtil.driver;
    public static JavascriptExecutor js = getJavascriptExecutor();

    /**
     * @param jsStr void
     * @Method_Name: clickElementsByJquery
     * @Description: 通过jquery代码点击
     * @author bianjianfeng
     * @date 2019年10月21日下午10:36:05
     */
    public static void clickElementsByJquery(String jsStr) {
        js.executeScript(jsStr + ".click()");
    }

    /**
     * @param element void
     * @Method_Name: clickElementsByJquery
     * @Description: 通过jquery代码点击
     * @author bianjianfeng
     * @date 2019年10月21日下午10:45:35
     */
    public static void clickElementsByJquery(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * @param script
     * @param args   void
     * @Method_Name: executeJS
     * @Description: 执行jquery代码
     * @author bianjianfeng
     * @date 2019年10月21日下午10:25:41
     */
    public static void executeJs(String script, Object... args) {
        js.executeScript(script, args);
    }

    /**
     * @return String
     * @Method_Name: getCookies
     * @Description: 获取cookies
     * @author bianjianfeng
     * @date 2019年11月14日上午12:26:58
     */
    public static String getCookies() {
        final Set<Cookie> cookies = driver.manage().getCookies();
        final StringBuilder cookieStr = new StringBuilder();
        for (final Cookie cookie : cookies) {
            final String name = cookie.getName();
            final String value = cookie.getValue();
            cookieStr.append(name + "=" + value + ";");
        }
        final String str = cookieStr.toString();
        return str.substring(0, str.length() - 1);
    }

    /**
     * @param jsStr
     * @return WebElement
     * @Method_Name: getElementByJquery
     * @Description: 通过jquery代码获取webElement
     * @author bianjianfeng
     * @date 2019年10月21日下午10:32:33
     */
    public static WebElement getElementByJquery(String jsStr) {
        return (WebElement) js.executeScript(jsStr + "[0]");
    }

    /**
     * @param jsStr
     * @return WebElement
     * @Method_Name: getElementByJS
     * @Description: 通过js代码获取webElement
     * @author bianjianfeng
     * @date 2019年10月21日下午10:29:29
     */
    public static WebElement getElementByJs(String jsStr) {
        return (WebElement) js.executeScript(jsStr);
    }

    /**
     * @param jsStr
     * @return List<WebElement>
     * @Method_Name: getElementsByJquery
     * @Description: 通过jquery代码获取webElement集合
     * @author bianjianfeng
     * @date 2019年10月21日下午10:34:02
     */
    public static List<WebElement> getElementsByJquery(String jsStr) {
        return (List<WebElement>) js.executeScript(jsStr + ".get()");
    }

    /**
     * @return JavascriptExecutor
     * @Method_Name: get
     * @Description: 将WebElement类型的driver强制转换为js类型的
     * @author bianjianfeng
     * @date 2019年10月21日下午10:20:58
     */
    public static JavascriptExecutor getJavascriptExecutor() {
        final JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    /**
     * @return String
     * @Method_Name: getURL
     * @Description: 获取当前window的url
     * @author bianjianfeng
     * @date 2019年10月25日下午8:59:57
     */
    public static String getUrl() {
        return (String) js.executeScript("return window.location.href");
    }

    /**
     * @return List<Object>
     * @Method_Name: getURLs
     * @Description: 获取urls
     * @author bianjianfeng
     * @date 2019年11月13日下午11:59:38
     */
    public static List<Object> getUrls() {
        return Arrays
                .asList(js.executeScript("return window.location.href"),
                        js.executeScript("return window.parent.location.href"),
                        js.executeScript("return window.parent.parent.location.href"),
                        js.executeScript("return window.parent.parent.parent.location.href"),
                        js.executeScript("return window.parent.parent.parent.parent.location.href"))
                .stream().distinct().collect(Collectors.toList());
    }

    /**
     * @param cssSelector
     * @return WebElement
     * @Method_Name: getWebElement
     * @Description: 获取元素
     * @author bianjianfeng
     * @date Oct 21, 20199:12:41 PM
     */
    public static WebElement getWebElement(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    /**
     * @param cssSelector
     * @return WebElement
     * @Method_Name: getWebElement
     * @Description: 获取元素集合
     * @author bianjianfeng
     * @date Oct 21, 20199:12:41 PM
     */
    public static List<WebElement> getWebElements(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }

    /**
     * @param cssSelector
     * @return
     * @Method_Name: getElementsText
     * @Description: 获取元素集合中每个元素的text集合
     * @author bianjianfeng
     * @date 2019年10月21日下午9:59:23
     */
    public static List<String> getWebElementsText(String cssSelector) {
        final List<WebElement> webElements = getWebElements(cssSelector);
        final List<String> list = new ArrayList<>();
        for (final WebElement ele : webElements) {
            list.add(ele.getText());
        }
        return list;
    }

    /**
     * @param cssSelector
     * @return String
     * @Method_Name: getWebElementText
     * @Description: 获取元素text值
     * @author bianjianfeng
     * @date Oct 21, 20199:16:17 PM
     */
    public static String getWebElementText(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector)).getText();
    }

    /**
     * @param cssSelector
     * @return String
     * @Method_Name: getWebElementValue
     * @Description: 获取元素value值
     * @author bianjianfeng
     * @date Oct 21, 20199:16:31 PM
     */
    public static String getWebElementValue(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector)).getAttribute("value");
    }

    /**
     * @return String
     * @Method_Name: getWebTitle
     * @Description: 获得网站的标题
     * @author bianjianfeng
     * @date Oct 21, 20199:08:54 PM
     */
    public static String getWebTitle() {
        return driver.getTitle();
    }

    /**
     * 此为封装好的高亮方法
     *
     * @param element
     */
    public static void hightlight(WebElement element) {
        // 使用 js 将传入的参数背景颜色和边框设置为蓝色和红色
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
                "backgroud: blue; border: 3px solid red;");
    }

    /**
     * @param urlStr
     * @Method_Name: openWindows
     * @Description: 打开一个url网页
     * @author bianjianfeng
     * @date 2019年10月21日下午9:51:04
     */
    public static void openWindows(String urlStr) {
        driver.get(urlStr);
    }

    public static void out(Object object) {
        System.out.println(object);
    }

    /**
     * @Method_Name: refresh
     * @Description: 刷新浏览器
     * @author bianjianfeng
     * @date 2019年10月21日下午11:03:49
     */
    public static void refresh() {
        driver.navigate().refresh();
    }

    /**
     * @param cssSelector
     * @param value
     * @Method_Name: sendKey
     * @Description: TODO(这里用一句话描述这个方法的作用) void
     * @author bianjianfeng
     * @date 2019年10月21日下午10:48:05
     */
    public static void sendKey(String cssSelector, String value) {
        getWebElement(cssSelector).sendKeys(value);
    }

    /**
     * @param i
     * @Method_Name: setimplicitlyWait
     * @Description: 设置隐性等待
     * @author bianjianfeng
     * @date 2019年10月21日下午10:13:19
     */
    public static void setimplicitlyWait(int i) {
        driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);
    }

    /**
     * @Method_Name: setWindowsSize
     * @Description: 设置窗口最大化
     * @author bianjianfeng
     * @date 2019年10月21日下午10:14:55
     */
    public static void setWindowsSize() {
        driver.manage().window().maximize();
    }

    /**
     * @param i
     * @Method_Name: sleep
     * @Description: 线程停止（i）秒
     * @author bianjianfeng
     * @date Oct 21, 20199:21:44 PM
     */
    public static void sleep(int i) {
        try {
            Thread.sleep(i * 1000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Method_Name: switchToDefaultFrame
     * @Description: 切换到默认的Frame
     * @author bianjianfeng
     * @date 2019年11月14日上午12:30:50
     */
    public static void switchToDefaultFrame() {
        driver.switchTo().defaultContent();
    }

    public static void switchToFrame(String srcStr) {
        out("切换iframe开始");
        final List<WebElement> webElements = getWebElements("iframe");
        for (final WebElement webElement : webElements) {
            final String src = webElement.getAttribute("src");
            if (src.equals(srcStr)) {
                out("成功切换到ifram==>" + src);
                driver.switchTo().frame(webElement);
            }
        }
        out("切换iframe结束");
    }

    /**
     * @Method_Name: switchToParentFrame
     * @Description: 切换到父Frame
     * @author bianjianfeng
     * @date 2019年11月14日上午12:28:26
     */
    public static void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    /**
     * @Method_Name: SystemClose
     * @Description: 退出系统
     * @author bianjianfeng
     * @date Oct 21, 20199:19:07 PM
     */
    public static void systemClose() {
        System.exit(0);
    }

    /**
     * @param cssSelector TODO
     * @Method_Name: WebClick
     * @Description: TODO(这里用一句话描述这个方法的作用) void
     * @author bianjianfeng
     * @date 2019年10月21日下午10:04:31
     */
    public static void webClick(String cssSelector) {
        getWebElement(cssSelector).click();
    }

    /**
     * @Method_Name: WebClose
     * @Description: 关闭driver
     * @author bianjianfeng
     * @date Oct 21, 20199:17:20 PM
     */
    public static void webClose() {
        driver.close();
        driver.quit();
    }

    /**
     * 移动到页面顶部
     */
    public void scrollToTop() {
        js.executeScript("window.scrollTo(document.documentElement.clientHeight,0);");
    }

    /**
     * 移动到页面底部
     */
    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0,document.documentElement.clientHeight);");
    }

    public WebUtil() {
        super();
    }

    public WebUtil(WebDriver driver) {
        WebUtil.driver = driver;
    }
}
