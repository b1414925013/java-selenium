/**
 * @Title: XMLUtilTest.java
 * @Package com.jfbian.util
 * @Description: 描述
 * @author: bianjianfneg
 * @date: 2019年12月12日 下午11:39:22
 * @version V1.0
 */
package com.jfbian.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

import com.jfbian.utils.XMLUtil;

import java.io.File;
import java.util.List;


/**
 * @ClassName: XMLUtilTest
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date: 2019年12月12日 下午11:39:22
 */
class XMLUtilTest {

    /**
     * {@link com.jfbian.utils.XMLUtil#getDocument(java.lang.String)} 的测试方法。
     *
     * @throws Exception
     */
    @Test
    public void testGetDocument() throws Exception {
        final File file = new File("./");
        System.out.println(file.getAbsolutePath());
        final Document document = XMLUtil.getDocument("./files/person.xml");
        final Element root = document.getRootElement();
        final Element beQuery = (Element) root.selectSingleNode("//person[@age='30']");
        if (beQuery == null) {
            System.out.println("Not exist!");
        }
        System.out.println(beQuery.elementText("name"));
        beQuery.element("name").setText("da大明");
        System.out.println(beQuery.attributeValue("weight"));
        System.out.println(beQuery.attributeValue("age"));
        System.out.println(beQuery.elementText("gender"));

        final List selectNodes = root.selectNodes("//person");
        System.out.println(selectNodes);
        XMLUtil.writeToXML(document, "./files/newperson.xml");
    }

    /**
     * {@link com.jfbian.utils.XMLUtil#writeToXML(org.dom4j.Document, java.lang.String)} 的测试方法。
     */
    @Test
    public void testWriteToXML() {
        final String[] array = new String[]{"002", "003"};
        final String[] arr = new String[array.length + 1];
        System.arraycopy(array, 0, arr, 0, array.length);
        arr[array.length] = "001";
        System.out.println(arr);
    }
}
