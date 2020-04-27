/**
 * @Title:  CsvUtil.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2019年12月8日 下午8:59:57
 * @version V1.0
 */
package com.jfbian.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * @ClassName:  CsvUtil
 * @Description:csv操作类工具类
 * @author: bianjianfeng
 * @date:   2019年12月8日 下午8:59:57
 */
public class CsvUtil {
    /**
     * @Title: readCsv
     * @Description:读取csv全部文件
     * @param path
     * @return: void
     */
    public static List<String[]> readAllCsv(String path) {
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
     *
     * @Title: readCsv
     * @Description: 对csv文件 其中一列进行读取，列是以0为计数开始
     * @param path
     * @param columnNum
     * @return: void
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
     * @return: List<String[]>
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

    public static File writeCsv(List<Object> head, List<List<Object>> dataList, String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     *
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        for (int i = 0; i < row.size(); i++) {
            StringBuffer sb = new StringBuffer();

            //带双引号
            // String rowStr = sb.append("\"").append(row.get(i)).append("\",").toString();

            //不带双引号
            String rowStr = sb.append(row.get(i)).append(",").toString();
            if (i == row.size() - 1) {
                rowStr = sb.substring(0, sb.lastIndexOf(","));
            }
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    /**
     *
     * @Title: readCsv
     * @Description: 读取csv文件
     * @param filePath 文件完成路径
     * @return: List<List<String>>
     */
    public static List<List<String>> readCsv(String filePath) {
        List<List<String>> resultList = new ArrayList<>();
        try (BufferedReader reader =
            new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));) {
            //(文件完整路径),编码格式//GBK
            //           reader.readLine();//显示标题行,没有则注释掉
            //           System.out.println(reader.readLine());
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split(",");//CSV格式文件时候的分割符,默认使用的是,号
                resultList.add(Arrays.asList(item));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
