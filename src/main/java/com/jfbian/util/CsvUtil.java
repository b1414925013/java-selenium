/**
 * @Title:  CsvUtil.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月8日 下午8:59:57
 * @version V1.0
 */
package com.jfbian.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * @ClassName:  CsvUtil
 * @Description:TODO(描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2019年12月8日 下午8:59:57
 */
public class CsvUtil {
    /**
     * @return
     *
     * @Title: readCsv
     * @Description:读取csv全部文件
     * @param path
     * @return: void
     * @throws
     */
    public static List<String[]> readCsv(String path) {
        final List<String[]> list = new ArrayList<>();
        try {
            final DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
            final CSVReader csvReader = new CSVReader(new InputStreamReader(in, "UTF-8"));
            String[] strs;
            while ((strs = csvReader.readNext()) != null) {
                list.add(strs);
            }
            csvReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @return
     *
     * @Title: readCsv
     * @Description: 对csv文件 其中一列进行读取，列是以0为计数开始
     * @param path
     * @param columnNum
     * @return: void
     * @throws
     */
    public static List<String> readCsv(String path, int columnNum) {
        final List<String> list = new ArrayList<>();
        try {
            final DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
            final CSVReader csvReader = new CSVReader(new InputStreamReader(in, "UTF-8"));
            String[] strs;

            while ((strs = csvReader.readNext()) != null) {
                list.add(strs[columnNum]);
            }

            csvReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @Title: readCsvForSkip
     * @Description: 读取csv文件 ,跳过skipNum行
     * @param path
     * @param skipNum
     * @return
     * @return: List<String[]>
     * @throws
     */
    public static List<String[]> readCsvForSkip(String path, int skipNum) {
        final List<String[]> list = new ArrayList<>();
        try {
            final DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
            final CSVReader csvReader = new CSVReader(new InputStreamReader(in, "UTF-8"));
            String[] strs;
            csvReader.skip(skipNum);
            while ((strs = csvReader.readNext()) != null) {
                list.add(strs);
            }

            csvReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
