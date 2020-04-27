/**
 * @Title:  ShellUtilsTest.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月8日 上午9:34:31
 * @version V1.0
 */
package com.jfbian.util;


import org.junit.Test;

import com.jfbian.utils.ShellUtil;

/**
 * @ClassName:  ShellUtilsTest
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2019年12月8日 上午9:34:31
 */
class ShellUtilsTest {

    /**
     * {@link com.jfbian.utils.ShellUtil#execute(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)} 的测试方法。
     */
    @Test
    public void testExecute() {
        //ShellUtils.execute("root", "192.168.17.129", 22, "root", "pwd");
        final String[] aa = {"cd /etc/", "ll", "cd /root/", "ll"};
        ShellUtil.execute("root", "192.168.17.129", 22, "root", "cd /etc/&& ls&& cd ..&& ls");
        // ShellUtil.executeMore("root", "192.168.17.129", 22, "root", aa);
    }

}
