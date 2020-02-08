/**
 * @Title: CsvUtilTest.java
 * @Package com.jfbian.util
 * @Description: 描述
 * @author: bianjianfneg
 * @date: 2019年12月8日 下午9:17:17
 * @version V1.0
 */
package com.jfbian.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: CsvUtilTest
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date: 2019年12月8日 下午9:17:17
 */
class CsvUtilTest {

    /**
     * {@link com.jfbian.util.CsvUtil#readCsv(java.lang.String)} 的测试方法。
     */
    @Test
    public void testReadCsvString() {
        final List<String[]> readCsv = CsvUtil.readCsv("C:\\Users\\Administrator\\Desktop\\新建文本文档.csv");
        readCsv.forEach(eles -> System.out.println(Arrays.toString(eles)));
    }

    /**
     * {@link com.jfbian.util.CsvUtil#readCsv(java.lang.String, int)} 的测试方法。
     */
    @Test
    public void testReadCsvStringInt() {
        final List<String> readCsv = CsvUtil.readCsv("C:\\Users\\Administrator\\Desktop\\新建文本文档.csv", 2);
        readCsv.forEach(ele -> System.out.println(ele));
    }

    /**
     * {@link com.jfbian.util.CsvUtil#readCsvForSkip(java.lang.String, int)} 的测试方法。
     */
    @Test
    public void testReadCsvForSkip() {
        final List<String[]> readCsv = CsvUtil.readCsvForSkip("C:\\Users\\Administrator\\Desktop\\新建文本文档.csv", 2);
        readCsv.forEach(eles -> System.out.println(Arrays.toString(eles)));
    }
}
