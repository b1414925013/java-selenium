/**
 * @Title:  XLSX2CSVTest.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2020年3月22日 下午11:58:42
 * @version V1.0
 */
package com.jfbian.util;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @ClassName:  XLSX2CSVTest
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2020年3月22日 下午11:58:42
 */
class XLSX2CSVTest {

    @Test
    void test() {
        int lastRowIndex = XLSX2CSV.getLastRowIndex("D:\\Desktop\\test.xlsx", "test");
        System.out.println(lastRowIndex);
        Map<String, List<List<String>>> read = XLSX2CSV.read("D:\\Desktop\\test.xlsx", 0, 1, "test");
        System.out.println(read);
    }
}
