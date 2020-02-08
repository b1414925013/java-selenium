package com.jfbian.util;

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
import java.util.Set;

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
        client.setResponseTimeoutMillis(30L * 1000L);
        client.setBodyDataReceiveTimeoutMillis(30L * 1000L);
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
     * @Method_Name: downLoadFile
     * @Description: 下载文件
     * @param url
     * @param heads
     * @param parameters
     * @param destination
     * @throws Exception
     *             void
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
            request.addParameter(key, heads.get(key));
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
     * @return
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
     *            TODO
     * @throws IOException
     *             void
     * @author bianjianfeng
     * @return
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
     * @param jsoData
     * @throws IOException
     *             void
     * @author bianjianfeng
     * @return
     * @throws MalformedURLException
     * @date 2019年11月10日上午11:28:45
     */
    public IHttpResponse postForJson(String url, Map<String, String> heads, Map<String, String> parameters,
        String jsoData) throws MalformedURLException, IOException {
        final PostRequest request = new PostRequest(url, "application/json;charset=UTF-8", jsoData);
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
     * @return
     * @throws MalformedURLException
     * @throws SocketTimeoutException
     * @throws IOException
     */
    public IHttpResponse put(String url, Map<String, String> heads, Map<String, String> parameters, String jsonData,
        String headContentType) throws MalformedURLException, IOException {
        final PutRequest putRequest = new PutRequest(url, headContentType, jsonData);
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
     * @return
     * @date 2019年11月10日上午11:30:39
     */
    public IHttpResponse upFile(String url, Map<String, String> heads, Map<String, String> parameters,
        String headContentType, List<LinkedHashMap<String, String>> listMaps) throws IOException {
        final MultipartRequest request = new MultipartRequest("POST", url, headContentType);
        final String contentType = request.getContentType();
        final int contentTypeLength = contentType.length() - contentType.indexOf("boundary=") - 9 + 2;
        int contentLength = contentTypeLength;
        for (final LinkedHashMap<String, String> linkedHashMap : listMaps) {
            final IPart part = part(linkedHashMap);
            final int partLength = part.getNonBlockingBody().toString().getBytes().length;
            int partHeaderLength = 0;
            final Set<String> headerNameSet = part.getHeaderNameSet();
            for (final String head : headerNameSet) {
                partHeaderLength += head.length() + 2 + part.getHeader(head).length() + 2;
            }
            contentLength += partLength + contentTypeLength + partHeaderLength + 2;
            request.addPart(part);
        }

        for (final String key : heads.keySet()) {
            request.addHeader(key, heads.get(key));
        }

        for (final String key : parameters.keySet()) {
            request.addParameter(key, parameters.get(key));
        }

        request.removeHeader("Transfer-Encoding");
        return client.call(request);
    }

    /**
     * @Method_Name: writeToLocal
     * @Description: 输出文件流到指定地址
     * @param destination
     * @param input
     * @throws IOException
     *             void
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
     * @param: @return
     * @param: @throws MalformedURLException
     * @param: @throws IOException
     * @return: IHttpResponse
     * @throws
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
}
