/**
 * @Title:  FileBlockUtil.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月5日 下午10:59:54
 * @version V1.0
 */
package com.jfbian.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName:  FileBlockUtil
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2019年12月5日 下午10:59:54
 */
public class FileBlockUtil {
    /**
     * 获得指定文件的byte数组
     */
    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            final File file = new File(filePath);
            final FileInputStream fis = new FileInputStream(file);
            final ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            final byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            final File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @Title: splitFile
     * @Description: 分割文件
     * @param sourceFile 源文件（带文件名）
     * @param targetFile 目标文件目录
     * @param targetFileName 目标文件名
     * @param blockSize 文件分割大小
     * @return: void
     * @throws
     */
    public void splitFile(String sourceFile, String targetFile, String targetFileName, int blockSize) {
        final byte[] sourceFileBytes = getBytes(sourceFile);
        final int length = sourceFileBytes.length;
        final int blockNum = length % blockSize == 0 ? length / blockSize : length / blockSize + 1;
        for (int i = 0; i < blockNum; i++) {
            if (i + 1 == blockNum) {
                getFile(Arrays.copyOfRange(sourceFileBytes, i * blockSize, length - i * blockSize), targetFile,
                    targetFileName);
                return;
            }
            getFile(Arrays.copyOfRange(sourceFileBytes, i * blockSize, (i + 1) * blockSize), targetFile,
                targetFileName + i);
        }
    }
}
