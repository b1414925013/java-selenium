package com.jfbian.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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
@SuppressWarnings("all")
public class WebUtil {

    private static Logger logger = Logger.getLogger(WebUtil.class);
    public static WebDriver driver = WebDriverUtil.driver;
    public static JavascriptExecutor js = getJavascriptExecutor();

    public WebUtil() {
        super();
    }

    public WebUtil(WebDriver driver) {
        WebUtil.driver = driver;
    }

    /**
     * @Method_Name: clickElementsByJquery
     * @Description: 通过jquery代码点击
     * @param jsStr
     *            void
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
     * @param element
     *            void
     * @author bianjianfeng
     * @date 2019年10月21日下午10:45:35
     */
    public static void clickElementsByJquery(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * @Method_Name: executeJS
     * @Description: 执行jquery代码
     * @param script
     * @param args
     *            void
     * @author bianjianfeng
     * @date 2019年10月21日下午10:25:41
     */
    public static void executeJs(String script, Object... args) {
        js.executeScript(script, args);
    }

    /**
     * @Method_Name: getCookies
     * @Description: 获取cookies
     * @return String
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
     * @Description: 通过jquery代码获取webElement
     * @param jsStr
     * @return WebElement
     * @Method_Name: getElementByJquery
     * @author bianjianfeng
     * @date 2019年10月21日下午10:32:33
     */
    public static WebElement getElementByJquery(String jsStr) {
        return (WebElement)js.executeScript(jsStr + "[0]");
    }

    /**
     * @Method_Name: getElementByJS
     * @Description: 通过js代码获取webElement
     * @param jsStr
     * @return WebElement
     * @author bianjianfeng
     * @date 2019年10月21日下午10:29:29
     */
    public static WebElement getElementByJs(String jsStr) {
        return (WebElement)js.executeScript(jsStr);
    }

    /**
     * @Method_Name: getElementsByJquery
     * @Description: 通过jquery代码获取webElement集合
     * @param jsStr
     * @return List<WebElement>
     * @author bianjianfeng
     * @date 2019年10月21日下午10:34:02
     */
    public static List<WebElement> getElementsByJquery(String jsStr) {
        return (List<WebElement>)js.executeScript(jsStr + ".get()");
    }

    /**
     * @Method_Name: get
     * @Description: 将WebElement类型的driver强制转换为js类型的
     * @return JavascriptExecutor
     * @author bianjianfeng
     * @date 2019年10月21日下午10:20:58
     */
    public static JavascriptExecutor getJavascriptExecutor() {
        final JavascriptExecutor js = (JavascriptExecutor)driver;
        return js;
    }

    /**
     * @Method_Name: getURL
     * @Description: 获取当前window的url
     * @return String
     * @author bianjianfeng
     * @date 2019年10月25日下午8:59:57
     */
    public static String getUrl() {
        return (String)js.executeScript("return window.location.href");
    }

    /**
     * @Method_Name: getURLs
     * @Description: 获取urls
     * @return List<Object>
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
     * @Method_Name: getWebElement
     * @Description: 获取元素
     * @param cssSelector
     * @return WebElement
     * @author bianjianfeng
     * @date Oct 21, 20199:12:41 PM
     */
    public static WebElement getWebElement(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    /**
     * @Method_Name: getWebElement
     * @Description: 获取元素集合
     * @param cssSelector
     * @return WebElement
     * @author bianjianfeng
     * @date Oct 21, 20199:12:41 PM
     */
    public static List<WebElement> getWebElements(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }

    /**
     * @Method_Name: getElementsText
     * @Description: 获取元素集合中每个元素的text集合
     * @author bianjianfeng
     * @date 2019年10月21日下午9:59:23
     * @param cssSelector
     * @return
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
     * @Method_Name: getWebElementText
     * @Description: 获取元素text值
     * @param cssSelector
     * @return String
     * @author bianjianfeng
     * @date Oct 21, 20199:16:17 PM
     */
    public static String getWebElementText(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector)).getText();
    }

    /**
     * @Method_Name: getWebElementValue
     * @Description: 获取元素value值
     * @param cssSelector
     * @return String
     * @author bianjianfeng
     * @date Oct 21, 20199:16:31 PM
     */
    public static String getWebElementValue(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector)).getAttribute("value");
    }

    /**
     * @Method_Name: getWebTitle
     * @Description: 获得网站的标题
     * @return String
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
     * @Method_Name: openWindows
     * @Description: 打开一个url网页
     * @author bianjianfeng
     * @date 2019年10月21日下午9:51:04
     * @param urlStr
     */
    public static void openWindows(String urlStr) {
        driver.get(urlStr);

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
     * @Method_Name: sendKey
     * @Description: 输入内容
     * @author bianjianfeng
     * @date 2019年10月21日下午10:48:05
     * @param cssSelector
     * @param value
     *
     */
    public static void sendKey(String cssSelector, String value) {
        getWebElement(cssSelector).sendKeys(value);
    }

    /**
     * @Method_Name: setimplicitlyWait
     * @Description: 设置隐性等待(全局等待)
     * @param i 等待时间(秒)
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
     * @Method_Name: sleep
     * @Description: 线程停止（i）秒
     * @param i 等待时间(秒)
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

    /**
     *
     * @Title: switchToFrame
     * @Description: 切换Frame
     * @param srcStr 要切换的Frame的src
     * @return: void
     */
    public static void switchToFrame(String srcStr) {
        logger.debug("切换iframe开始");
        int count = 0;
        final List<WebElement> webElements = getWebElements("iframe");
        for (final WebElement webElement : webElements) {
            final String src = webElement.getAttribute("src");
            if (src.contains(srcStr)) {
                driver.switchTo().frame(webElement);
                count++;
                logger.info("成功切换到ifram==>" + src);
                break;
            }
        }
        if (count == 0) {
            logger.info("切换到ifram失败==>" + srcStr);
        }
        logger.debug("切换iframe结束");
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
     * @Method_Name: WebClick
     * @Description: 点击元素
     * @author bianjianfeng
     * @date 2019年10月21日下午10:04:31
     * @param cssSelector
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

    /**
     * 检查元素是否存在
     */
    public boolean isExist(String cssSelector) {
        String pattern = "return document.querySelectorAll(\"" + cssSelector + "\")";
        List<WebElement> eles = (List<WebElement>)js.executeScript(pattern);

        if (eles.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 鼠标点击元素
     */
    public void mouseClick(WebElement element) {
        String code = "var fireOnThis = arguments[0];" + "var evObj = document.createEvent('MouseEvents');"
            + "evObj.initEvent( 'click', true, true );" + "fireOnThis.dispatchEvent(evObj);";
        js.executeScript(code, element);
    }

    /**
     * 鼠标移动到元素
     */
    public static void mouseEnter(WebElement element) {
        String code = "var fireOnThis = arguments[0];" + "var evObj = document.createEvent('MouseEvents');"
            + "evObj.initEvent( 'mouseenter', false, false );" + "fireOnThis.dispatchEvent(evObj);";
        js.executeScript(code, element);
    }

    /**
     * 鼠标移动到元素
     */
    public void mouseOver(WebElement element) {
        String code = "var fireOnThis = arguments[0];" + "var evObj = document.createEvent('MouseEvents');"
            + "evObj.initEvent( 'mouseover', true, true );" + "fireOnThis.dispatchEvent(evObj);";
        js.executeScript(code, element);
    }

    /**
    *
    * @Title: getParentWebElement
    * @Description: 获取父元素
    * @param: element 基础元素
    * @return: WebElement
    */
    public static WebElement getParentWebElement(WebElement element) {
        return element.findElement(By.xpath("./parent::*"));
    }

    /**
    *
    * @Title: getprevElement
    * @Description: 获取前一个元素
    * @param: element 基础元素
    * @return: WebElement
    */
    public static WebElement getprevElement(WebElement element) {
        return element.findElement(By.xpath("./preceding-sibling::*[1]"));
    }

    /**
    *
    * @Title: getNextElement
    * @Description: 获取后一个元素
    * @param: element 基础元素
    * @return: WebElement
    */
    public static WebElement getNextElement(WebElement element) {
        return element.findElement(By.xpath("./following-sibling::*[1]"));
    }

    /**
     *
     * @Title: switchToWindow
     * @Description: 切换window
     * @param windowTitle 窗口名
     * @return: void
     */
    public static void switchToWindow(String windowTitle) {
        logger.debug("切换窗口开始");
        int count = 0;
        Set<String> windowHandles = driver.getWindowHandles();
        for (String str : windowHandles) {
            driver.switchTo().window(str);
            if (Objects.equals(getWebTitle(), windowTitle)) {
                count++;
                logger.info("切换窗口成功==> " + windowTitle);
                break;
            }
        }
        if (count == 0) {
            logger.info("切换窗口失败==> " + windowTitle);
        }
        logger.debug("切换窗口结束");
    }
}
