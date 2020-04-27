package com.jfbian.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @version V1.0
 * @ClassName FileUtil
 * @Description
 * @Author zhangyue
 * @Date 2018/1/9 20:58
 */
public class FileUtil {
    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {
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
            if (!dir.exists()) {// 判断文件目录是否存在
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
     * byte[] 转InputStream
     */
    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    /**
     * InputStream 转 byte[]
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream) throws IOException {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        final byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        final byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * byte[] 转 InputStreamReader
     */
    public static final InputStreamReader byte2Reader(byte[] buf) {
        final InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(buf));
        return isr;
    }

    /**
     * 删除文件
     *
     * @param pathname
     *            文件名（包括路径）
     */
    public static void deleteFile(String pathname) {
        final File file = new File(pathname);
        if (file.isFile() && file.exists()) {
            file.delete();
        } else {
            System.out.println("File[" + pathname + "] not exists!");
        }

    }

    /**
     * 删除文件树
     *
     * @param dirpath
     *            文件夹路径
     */
    public static void deleteFileTree(String dirpath) throws IOException {
        deleteEveryThing(dirpath);
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     *            文件名
     * @return
     */
    public static String getExtention(String fileName) {
        final int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }

    /**
     * 获取文件分隔符
     *
     * @return
     */
    public static String getFileSeparator() {
        return File.separator;
    }

    /**
     * 获取相对路径
     *
     * @param params
     *            按参数先后位置得到相对路径
     * @return
     */
    public static String getRelativePath(String... params) {

        if (null != params) {
            String path = "";
            for (final String str : params) {
                path = path + getFileSeparator() + str;
            }

            return path;
        }

        return null;
    }

    /**
     * 把一个字符串写到指定文件中
     *
     * @param str
     *            要写入文件中的字符串内容
     * @param path
     *            文件夹路径
     * @param fileName
     *            文件名称
     */
    public static void writeStringToFile(String str, String path, String fileName) throws IOException {
        final File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        final File file = new File(path + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        final FileWriter fw = new FileWriter(file, true);
        fw.write(str);
        fw.flush();
        fw.close();
    }

    /**
     * 在某个文件中追加内容
     *
     * @param fileName
     * @param content
     */
    public static void appendStringToFile(String fileName, String content) {
        try {
            // 判断文件是否存在
            final File file = new File(fileName);
            judeFileExists(file);
            // 打开一个随机访问文件流，按读写方式
            final RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            final long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write((content + "\r\n").getBytes());
            randomFile.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    // 判断文件是否存在,如果不存在则创建
    public static void judeFileExists(File file) {
        if (file.exists()) {
        } else {
            try {
                file.createNewFile();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 判断文件夹是否存在，如果不存在则创建
    public static void judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }
    }

    /**
     * 获取windows/linux的项目根目录
     *
     * @return
     */
    public static String getConTextPath() {
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if ("usr".equals(fileUrl.substring(1, 4))) {
            fileUrl = fileUrl.substring(0, fileUrl.length() - 16);// linux
        } else {
            fileUrl = fileUrl.substring(1, fileUrl.length() - 16);// windows
        }
        return fileUrl;
    }

    /**
     * 字符串转数组
     *
     * @param str
     *            字符串
     * @param splitStr
     *            分隔符
     * @return
     */
    public static String[] StringToArray(String str, String splitStr) {
        String[] arrayStr = null;
        if (!"".equals(str) && str != null) {
            if (str.indexOf(splitStr) != -1) {
                arrayStr = str.split(splitStr);
            } else {
                arrayStr = new String[1];
                arrayStr[0] = str;
            }
        }
        return arrayStr;
    }

    /**
     * 读取文件
     *
     * @param Path
     * @return
     */
    public static String ReadFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            final FileInputStream fileInputStream = new FileInputStream(Path);
            final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 获取文件夹下所有文件的名称 + 模糊查询（当不需要模糊查询时，queryStr传空或null即可） 1.当路径不存在时，map返回retType值为1
     * 2.当路径为文件路径时，map返回retType值为2，文件名fileName值为文件名 3.当路径下有文件夹时，map返回retType值为3，文件名列表fileNameList，文件夹名列表folderNameList
     *
     * @param folderPath
     *            路径
     * @param queryStr
     *            模糊查询字符串
     * @return
     */
    public static HashMap<String, Object> getFilesName(String folderPath, String queryStr) {
        final HashMap<String, Object> map = new HashMap<>();
        final List<String> fileNameList = new ArrayList<>();// 文件名列表
        final List<String> folderNameList = new ArrayList<>();// 文件夹名列表
        final File f = new File(folderPath);
        if (!f.exists()) { // 路径不存在
            map.put("retType", "1");
        } else {
            final boolean flag = f.isDirectory();
            if (flag == false) { // 路径为文件
                map.put("retType", "2");
                map.put("fileName", f.getName());
            } else { // 路径为文件夹
                map.put("retType", "3");
                final File fa[] = f.listFiles();
                queryStr = queryStr == null ? "" : queryStr;// 若queryStr传入为null,则替换为空（indexOf匹配值不能为null）
                for (final File fs : fa) {
                    if (fs.getName().indexOf(queryStr) != -1) {
                        if (fs.isDirectory()) {
                            folderNameList.add(fs.getName());
                        } else {
                            fileNameList.add(fs.getName());
                        }
                    }
                }
                map.put("fileNameList", fileNameList);
                map.put("folderNameList", folderNameList);
            }
        }
        return map;
    }

    /**
     * 以行为单位读取文件，读取到最后一行
     *
     * @param filePath
     * @return
     */
    public static List<String> readFileContent(String filePath) {
        BufferedReader reader = null;
        final List<String> listContent = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                listContent.add(tempString);
                line++;
            }
            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e1) {
                }
            }
        }
        return listContent;
    }

    /**
     * 读取指定行数据 ，注意：0为开始行
     *
     * @param filePath
     * @param lineNumber
     * @return
     */
    public static String readLineContent(String filePath, int lineNumber) {
        BufferedReader reader = null;
        String lineContent = "";
        try {
            reader = new BufferedReader(new FileReader(filePath));
            int line = 0;
            while (line <= lineNumber) {
                lineContent = reader.readLine();
                line++;
            }
            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e1) {
                }
            }
        }
        return lineContent;
    }

    /**
     * 读取从beginLine到endLine数据（包含beginLine和endLine），注意：0为开始行
     *
     * @param filePath
     * @param beginLineNumber
     *            开始行
     * @param endLineNumber
     *            结束行
     * @return
     */
    public static List<String> readLinesContent(String filePath, int beginLineNumber, int endLineNumber) {
        final List<String> listContent = new ArrayList<>();
        try {
            int count = 0;
            final BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String content = reader.readLine();
            while (content != null) {
                if (count >= beginLineNumber && count <= endLineNumber) {
                    listContent.add(content);
                }
                content = reader.readLine();
                count++;
            }
        } catch (final Exception e) {
        }
        return listContent;
    }

    /**
     * 读取若干文件中所有数据
     *
     * @param listFilePath
     * @return
     */
    public static List<String> readFileContent_list(List<String> listFilePath) {
        final List<String> listContent = new ArrayList<>();
        for (final String filePath : listFilePath) {
            final File file = new File(filePath);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    listContent.add(tempString);
                    line++;
                }
                reader.close();
            } catch (final IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e1) {
                    }
                }
            }
        }
        return listContent;
    }

    /**
     * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
     *
     * @param filePath
     * @param content
     * @param flag
     *            true:如果文件存在且存在内容，则内容换行追加；false:如果文件存在且存在内容，则内容替换
     */
    public static String fileLinesWrite(String filePath, String content, boolean flag) {
        String filedo = "write";
        FileWriter fw = null;
        try {
            final File file = new File(filePath);
            // 如果文件夹不存在，则创建文件夹
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {// 如果文件不存在，则创建文件,写入第一行内容
                file.createNewFile();
                fw = new FileWriter(file);
                filedo = "create";
            } else {// 如果文件存在,则追加或替换内容
                fw = new FileWriter(file, flag);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return filedo;
    }

    /**
     * 写文件
     *
     * @param ins
     * @param out
     */
    public static void writeIntoOut(InputStream ins, OutputStream out) {
        final byte[] bb = new byte[10 * 1024];
        try {
            int cnt = ins.read(bb);
            while (cnt > 0) {
                out.write(bb, 0, cnt);
                cnt = ins.read(bb);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                ins.close();
                out.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断list中元素是否完全相同（完全相同返回true,否则返回false）
     *
     * @param list
     * @return
     */
    private static boolean hasSame(List<? extends Object> list) {
        if (null == list) {
            return false;
        }
        return 1 == new HashSet<Object>(list).size();
    }

    /**
     * 判断list中是否有重复元素（无重复返回true,否则返回false）
     *
     * @param list
     * @return
     */
    private static boolean hasSame2(List<? extends Object> list) {
        if (null == list) {
            return false;
        }
        return list.size() == new HashSet<Object>(list).size();
    }

    /**
     * 增加/减少天数
     *
     * @param date
     * @param num
     * @return
     */
    public static Date DateAddOrSub(Date date, int num) {
        final Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    // https://www.cnblogs.com/chenhuan001/p/6575053.html
    /**
     * 递归删除文件或者目录
     *
     * @param file_path
     */
    public static void deleteEveryThing(String file_path) {
        try {
            final File file = new File(file_path);
            if (!file.exists()) {
                return;
            }
            if (file.isFile()) {
                file.delete();
            } else {
                final File[] files = file.listFiles();
                for (final File file2 : files) {
                    final String root = file2.getAbsolutePath();// 得到子文件或文件夹的绝对路径
                    deleteEveryThing(root);
                }
                file.delete();
            }
        } catch (final Exception e) {
            System.out.println("删除文件失败");
        }
    }

    /**
     * 创建目录
     *
     * @param dir_path
     */
    public static void mkDir(String dir_path) {
        final File myFolderPath = new File(dir_path);
        try {
            if (!myFolderPath.exists()) {
                myFolderPath.mkdir();
            }
        } catch (final Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    // https://blog.csdn.net/lovoo/article/details/77899627
    /**
     * 判断指定的文件是否存在。
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /* 得到文件后缀名
    *
    * @param fileName
    * @return
    */
    public static String getFileExt(String fileName) {
        final int point = fileName.lastIndexOf('.');
        final int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 删除文件夹及其下面的子文件夹
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteDir(File dir) throws IOException {
        if (dir.isFile()) {
            throw new IOException("IOException -> BadInputException: not a directory.");
        }
        final File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDir(file);
                }
            }
        }
        dir.delete();
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copy(File src, File dst) throws Exception {
        final int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            final byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (final Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (null != out) {
                try {
                    out.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }
        }
    }
}
