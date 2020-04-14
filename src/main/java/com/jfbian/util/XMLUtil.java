/**
 * @Title:  UtilsForXML.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月12日 下午11:24:00
 * @version V1.0
 */
package com.jfbian.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
* @ClassName:  XMLUtil
* @Description:xml文件操作类
* @author: bianjianfeng
* @date:   2019年12月12日 下午11:24:00
*/
public class XMLUtil {
    /**
     * 需要一个方法来创建DOM4j德 XML解析器并返回一个Document对象
     */
    public static Document getDocument(String xmlPath) throws Exception {
        final SAXReader reader = new SAXReader();
        //将XML文件路径传给Document对象并返回其实例dom
        final Document dom = reader.read(new File(xmlPath));
        return dom;
    }

    /**
     * 需要一个方法来将更新后的document对象写入到XML文件中去
     * @throws Exception
     */
    public static void writeToXML(Document dom, String xmlPath) throws Exception {

        //首先创建样式和输出流
        final OutputFormat format = OutputFormat.createPrettyPrint();
        final OutputStream out = new FileOutputStream(xmlPath);
        final XMLWriter writer = new XMLWriter(out, format);

        //写入之后关闭流
        writer.write(dom);
        writer.close();
    }
}
