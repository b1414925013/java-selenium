/**
 * @Title:  JsoupParserUtilTest.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月7日 上午9:13:46
 * @version V1.0
 */
package com.jfbian.util;

import junit.framework.TestCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.jfbian.utils.JsoupParserUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:  JsoupParserUtilTest
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @param
 * @date:   2019年12月7日 上午9:13:46
 */
public class JsoupParserUtilTest extends TestCase {
    @Test
    public void name() throws IOException {
        final Document parse = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\新建文本文档.html"), "UTF-8");
        final Elements moreElementsBySelectStr =
            JsoupParserUtil.getMoreElementsBySelectStr(parse, "body > table:nth-child(1) > thead > tr th");
        final String singElementText = JsoupParserUtil.getSingElementText(moreElementsBySelectStr);
        final List<String> list = new ArrayList<>();
        for (final Element element : moreElementsBySelectStr) {
            final String htmlStr = element.text();
            list.add(htmlStr);
        }
        System.out.println(list);
    }

    @Test
    public void name2() throws IOException {
        final Document parse = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\新建文本文档.xml"), "UTF-8");
        final Elements moreElementsBySelectStr =
            JsoupParserUtil.getMoreElementsBySelectStr(parse, "root > part > name");
        final List<String> list = new ArrayList<>();
        for (final Element element : moreElementsBySelectStr) {
            final String htmlStr = element.text();
            list.add(htmlStr);
        }
        System.out.println(list);
    }
}
