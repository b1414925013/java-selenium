package com.jfbian.utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @ClassName: WebDriverUtil
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: bianjianfeng
 * @date: Oct 21, 2019 9:03:46 PM
 */
public class WebDriverUtil {
    public static WebDriver driver = getChromeDriver();

    /**
     * @Method_Name: getChromeDriver
     * @Description: selenium-java版本为4.0.0-alpha-2 谷歌版本为78.0.3904.97 ChromeDriver版本为 78.0.3904.70
     * @return WebDriver
     * @author bianjianfeng
     * @date Oct 21, 2019:02:08 PM
     */
    public static WebDriver getChromeDriver() {
        // 设置chrome浏览器驱动的所在位置
        final String value = System.getProperty("user.dir") + "\\tools\\chromedriver78_0_3904_11.exe";

        final ChromeDriverService service =
            new ChromeDriverService.Builder().usingDriverExecutable(new File(value)).usingAnyFreePort().build();
        try {
            service.start();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        // 创建一个谷歌浏览器对象
        final WebDriver driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        return driver;
    }

    public static WebDriver getPhantomjsDriver() {
        // 设置必要参数
        final DesiredCapabilities dcaps = new DesiredCapabilities();
        // ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        // 截屏支持
        dcaps.setCapability("takesScreenshot", true);
        // css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        // js支持
        dcaps.setJavascriptEnabled(true);
        // 驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            System.getProperty("user.dir") + "\\tools\\phantomjs.exe");
        // 屏蔽日志
        final String[] phantomArgs = new String[] {"--webdriver-loglevel=NONE"};
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
        // 创建无界面浏览器对象
        final PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        return driver;
    }
}
