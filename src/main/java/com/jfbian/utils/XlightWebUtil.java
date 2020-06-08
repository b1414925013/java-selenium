package com.jfbian.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.xlightweb.BodyDataSource;
import org.xlightweb.GetRequest;
import org.xlightweb.IHttpResponse;
import org.xlightweb.IPart;
import org.xlightweb.MultipartRequest;
import org.xlightweb.Part;
import org.xlightweb.PostRequest;
import org.xlightweb.PutRequest;
import org.xlightweb.client.HttpClient;
import org.xlightweb.client.HttpClient.FollowsRedirectMode;

/**
 * @ClassName: XlightWebUtil
 * @Description:XlightWeb工具类
 * @author: bianjianfeng
 * @date: 2019年11月12日 下午11:40:59
 */
public class XlightWebUtil {
    private static HttpClient client = new HttpClient(createIgnoreVerifySSL());

    static {
        client.setMaxIdle(3);
        client.setConnectTimeoutMillis(300 * 1000);
        client.setResponseTimeoutMillis(30 * 1000L);
        client.setBodyDataReceiveTimeoutMillis(30 * 1000L);
        client.setAutoHandleCookies(true);
        // 有些接口不能使用重定向功能
        client.setFollowsRedirectMode(FollowsRedirectMode.ALL);
    }

    /**
     * @Method_Name: createIgnoreVerifySSL
     * @Description: 绕过验证
     * @return SSLContext
     * @author bianjianfeng
     * @date 2019年11月12日下午11:35:06
     */
    public static SSLContext createIgnoreVerifySSL() {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSLv3");
            //sc = SSLContext.getInstance("TLSv1.3");
            //sc = SSLContext.getInstance("TLSv1.2");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            final X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {}

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {}

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sc.init(null, new TrustManager[] {trustManager}, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return sc;
    }

    /**
     *
     * @Title: sleepAllThread
     * @Description: 睡眠所有线程millis毫秒
     * @param millis
     * @return: void
     */
    @SuppressWarnings("static-access")
    public static void sleepAllThread(int millis) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        for (Thread thread : list) {
            System.out.println(thread.getName());
            try {
                thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Method_Name: downLoadFile
     * @Description: 下载文件
     * @param url
     * @param heads
     * @param parameters
     * @param destination  目标文件(全路径名)
     * @throws Exception
     * @author bianjianfeng
     * @date 2019年11月14日上午12:47:00
     */
    public void downLoadFile(String url, Map<String, String> heads, Map<String, String> parameters, String destination)
        throws Exception {
        final GetRequest request = new GetRequest(url);
        for (final String key : heads.keySet()) {
            request.addHeader(key, heads.get(key));
        }
        for (final String key : parameters.keySet()) {
            request.addParameter(key, parameters.get(key));
        }
        final IHttpResponse response = client.call(request);
        final BodyDataSource body = response.getBody();

        final InputStream inputStream = body.toInputStream();

        writeToLocal(destination, inputStream);
    }

    /**
     * @Method_Name: get
     * @Description: get请求
     * @param url
     * @param heads
     * @param parameters
     * @author bianjianfeng
     * @throws IOException
     * @throws SocketTimeoutException
     * @date 2019年11月9日下午10:40:03
     */
    public IHttpResponse get(String url, Map<String, String> heads, Map<String, String> parameters)
        throws SocketTimeoutException, IOException

    {
        final GetRequest request = new GetRequest(url);
        for (final String key : heads.keySet()) {
            request.addHeader(key, heads.get(key));
        }
        for (final String key : parameters.keySet()) {
            request.addParameter(key, parameters.get(key));
        }

        return client.call(request);
    }

    /**
     * @Method_Name: part
     * @Description: 填充part
     * @param map
     * @throws IOException
     * @author bianjianfeng
     * @date 2019年11月10日上午10:13:52
     */
    public IPart part(LinkedHashMap<String, String> map) throws IOException {
        final Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        int count = 0;
        IPart part = null;
        while (iterator.hasNext()) {
            final Entry<String, String> entry = iterator.next();
            if (count == 0) {
                if (entry.getKey().equals("filePath")) {
                    part = new Part(new File(entry.getValue()));
                } else {
                    part = new Part(entry.getKey(), entry.getValue());
                    part.removeHeader("Content-Length");
                }
            } else {
                part.addHeader(entry.getKey(), entry.getValue());
            }
            count++;
        }
        return part;
    }

    /**
     * @Method_Name: postforjson
     * @Description: post请求json
     * @param url
     * @param heads
     * @param parameters
     * @param jsonData
     * @throws IOException
     * @author bianjianfeng
     * @throws MalformedURLException
     * @date 2019年11月10日上午11:28:45
     */
    public IHttpResponse postForJson(String url, Map<String, String> heads, Map<String, String> parameters,
        String jsonData) throws MalformedURLException, IOException {
        final PostRequest request = new PostRequest(url, "application/json;charset=UTF-8", jsonData);
        for (final String key : heads.keySet()) {
            request.addHeader(key, heads.get(key));
        }
        for (final String key : parameters.keySet()) {
            request.addParameter(key, parameters.get(key));
        }
        return client.call(request);
    }

    /**
     * put 请求
     *
     * @param url
     * @param heads
     * @param parameters
     * @throws MalformedURLException
     * @throws SocketTimeoutException
     * @throws IOException
     */
    public IHttpResponse put(String url, Map<String, String> heads, Map<String, String> parameters, String postData,
        String headContentType) throws MalformedURLException, IOException {
        final PutRequest putRequest = new PutRequest(url, headContentType, postData);
        for (final String key : heads.keySet()) {
            putRequest.addHeader(key, heads.get(key));
        }
        for (final String key : parameters.keySet()) {
            putRequest.addParameter(key, parameters.get(key));
        }
        putRequest.removeHeader("Content-Length");
        return client.call(putRequest);
    }

    /**
     * @Method_Name: upFile
     * @Description: 上传文件
     * @param url
     * @param heads
     * @param parameters
     * @param headContentType
     * @param listMaps
     * @throws IOException
     * @author bianjianfeng
     * @date 2019年11月10日上午11:30:39
     */
    public IHttpResponse upFile(String url, Map<String, String> heads, Map<String, String> parameters,
        String headContentType, List<LinkedHashMap<String, String>> listMaps) throws IOException {
        final MultipartRequest request = new MultipartRequest("POST", url, headContentType);
        for (final LinkedHashMap<String, String> linkedHashMap : listMaps) {
            final IPart part = part(linkedHashMap);
            request.addPart(part);
        }

        for (final String key : heads.keySet()) {
            request.addHeader(key, heads.get(key));
        }

        for (final String key : parameters.keySet()) {
            request.addParameter(key, parameters.get(key));
        }

        return client.call(request);
    }

    /**
     * @Method_Name: writeToLocal
     * @Description: 输出文件流到指定地址
     * @param destination
     * @param input
     * @throws IOException
     * @author bianjianfeng
     * @date 2019年11月14日上午12:46:29
     */
    public void writeToLocal(String destination, InputStream input) throws IOException {
        int index;
        final byte[] bytes = new byte[1024];
        final File parentFile = new File(destination).getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        final FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();
    }

    /**
     *
     * @Title: postForFromData
     * @Description: post提交表单
     * @param: @param url
     * @param: @param heads
     * @param: @param parameters
     * @param: @param fromData
     * @param: @param headContentType
     * @param: @throws MalformedURLException
     * @param: @throws IOException
     * @return: IHttpResponse
     */
    public IHttpResponse postForFromData(String url, Map<String, String> heads, Map<String, String> parameters,
        String fromData, String headContentType) throws MalformedURLException, IOException {
        final PostRequest request = new PostRequest(url, headContentType, fromData);
        for (final String key : heads.keySet()) {
            request.addHeader(key, heads.get(key));
        }
        for (final String key : parameters.keySet()) {
            request.addParameter(key, parameters.get(key));
        }
        return client.call(request);
    }

    /**
     * 带head,part的文上传请求
     *
     * @param postUrl url
     * @param headMap head元素
     * @param partMap part元素
     * @param fileFullPath 文件
     * @param name 文件上传name
     * @param filePartName
     * @return BodyDataSource
     */
    public static BodyDataSource multiPartRequest(String postUrl, Map<String, Object> headMap,
        Map<String, Object> partMap, String fileFullPath, String name, String filePartName) {
        MultipartRequest request = null;
        BodyDataSource bodydataSource = null;
        long contentLen = 0;
        try {
            request = new MultipartRequest("POST", postUrl, "multipart/form-data");

            for (Entry<String, Object> entry : headMap.entrySet()) {
                request.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }

            for (Entry<String, Object> entry : partMap.entrySet()) {
                Part part = new Part("", String.valueOf(entry.getValue()));
                part.removeHeader("Content-Type");
                String partHeaderLine = "Content-Disposition: form-data; name=\"" + entry.getKey() + "\"";
                part.addHeaderlines(partHeaderLine);
                request.addPart(part);
                contentLen += partHeaderLine.length();
                contentLen += String.valueOf(entry.getValue()).length();
            }

            ////////////////////////////////////////////////////////////////
            File file = new File(fileFullPath);
            Part filePart = new Part(file);
            String partHeaderLine1 =
                "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filePartName + "\"";
            String partHeaderLine2 = "Content-Type: application/octet-stream";

            if (fileFullPath.endsWith(".xml")) {
                partHeaderLine2 = "Content-Type: text/xml";
            }
            if (fileFullPath.endsWith(".csv")) {
                partHeaderLine2 = "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            }
            if (fileFullPath.endsWith(".xls")) {
                partHeaderLine2 = "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            }
            if (fileFullPath.endsWith(".xlsx")) {
                partHeaderLine2 = "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            }
            if (fileFullPath.endsWith(".msi") || fileFullPath.endsWith(".MSI")) {
                partHeaderLine2 = "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            }
            if (fileFullPath.endsWith(".zip")) {
                partHeaderLine2 = "Content-Type: application/zip";
            }

            String[] partheaderLines = new String[] {partHeaderLine1, partHeaderLine2};
            filePart.addHeaderlines(partheaderLines);
            request.addPart(filePart);
            ////////////////////////////////////////////////////////////////

            String contentType = request.getContentType();
            int boundaryLen = contentType.length() - contentType.indexOf("boundary=") - 9;

            // 换行符长度\r\n
            int lenOfCRLF = 2;
            // 每个Part用“--分隔符”分隔，最后一个“--分隔符--”表示结束,每一行都必须以\r\n结束，包括最后一行
            int preBoundaryLen = 2;
            int lastBoundaryLen = 2;

            contentLen +=
                (preBoundaryLen + boundaryLen + lenOfCRLF + lenOfCRLF + lenOfCRLF + lenOfCRLF) * partMap.size();
            contentLen += preBoundaryLen + boundaryLen + lenOfCRLF + partHeaderLine1.length() + lenOfCRLF
                + partHeaderLine2.length() + lenOfCRLF + lenOfCRLF + file.length() + lenOfCRLF + preBoundaryLen
                + boundaryLen + lastBoundaryLen + lenOfCRLF;

            // Content-Length 的长度是post 数据的长度，不包含请求头域长度
            request.setHeader("Content-Length", String.valueOf(contentLen));
            request.removeHeader("Transfer-Encoding");

            IHttpResponse response = client.call(request);
            bodydataSource = response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bodydataSource;
    }
}
