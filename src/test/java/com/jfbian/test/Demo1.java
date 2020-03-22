/**
 * @Title:  Demo1.java
 * @Package com.jfbian.test
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月23日 下午7:44:48
 * @version V1.0
 */
package com.jfbian.test;

/**
* @ClassName:  Demo1
* @Description:TODO(描述这个类的作用)
* @author: bianjianfeng
* @date:   2019年12月23日 下午7:44:48
*/
public class Demo1 {
    public static void main(String[] args) {
        final Demo1 demo1 = new Demo1();
        demo1.test();
    }

    public void test() {
        final Demo demo = new Demo();
        final Demo demo2 = new Demo();
        demo.extracted(new ExtractedParameter(demo, demo));
    }
}
