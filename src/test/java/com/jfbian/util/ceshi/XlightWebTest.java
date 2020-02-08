package com.jfbian.util.ceshi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.xlightweb.BodyDataSource;
import org.xlightweb.GetRequest;
import org.xlightweb.IFutureResponse;
import org.xlightweb.IHttpResponse;
import org.xlightweb.IPart;
import org.xlightweb.MultipartRequest;
import org.xlightweb.Part;
import org.xlightweb.PostRequest;
import org.xlightweb.client.HttpClient;
import org.xlightweb.client.HttpClient.FollowsRedirectMode;

import junit.framework.TestCase;

/**@ClassName:  XlightWebTest
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: bianjianfeng
 * @date:   2019年11月9日 下午9:52:28
 */
public class XlightWebTest extends TestCase
{

    private static HttpClient client = new HttpClient(createIgnoreVerifySSL());

    static
    {
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
    public static SSLContext createIgnoreVerifySSL()
    {
        SSLContext sc = null;
        try
        {
            sc = SSLContext.getInstance("SSLv3");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager trustManager = new X509TrustManager()
            {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException
                {}

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException
                {}

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
            };
            sc.init(null, new TrustManager[] {trustManager}, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sc;
    }


    /**
     * <p>Title: setUp</p>
     * <p>Description: </p>
     * @throws java.lang.Exception
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * <p>Title: tearDown</p>
     * <p>Description: </p>
     * @throws java.lang.Exception
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }


    public void testMain() throws SocketTimeoutException, IOException
    {
        //        Map<String, String> heads = new HashMap<>();
        //        heads.put("ceshi001", "ceshi001");
        //        heads.put("ceshi002", "ceshi002");
        //        heads.put("ceshi003", "ceshi003");
        //        Map<String, String> parameters = new HashMap<>();
        //        parameters.put("ceshi001", "ceshi001");
        //        parameters.put("ceshi002", "ceshi002");
        //        parameters.put("ceshi003", "ceshi003");
        String url = "https://www.baidu.com/";
        //        get(url, heads, parameters);
        get(url, new HashMap<>(), new HashMap<>());

        //        System.out.println("=========================================");
        //        post(url, heads, parameters);
        //
        //        System.out.println("=========================================");
        //        final String jsoData = "{jsondata}";
        //        postforjson(url, heads, parameters, jsoData);
        //
        //        System.out.println("=========================================");
        //        final String filePath = "D:\\360极速浏览器下载\\selenium自动化.java";
        //        List<LinkedHashMap<String, String>> arrayList = new ArrayList<>();
        //        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //        map.put("ceshikey", "ceshivlue");
        //        map.put("ceshikey001", "ceshivlue001");
        //        LinkedHashMap<String, String> map2 = new LinkedHashMap<>();
        //        map2.put("filePath", filePath);
        //        map2.put("Content-Type", "Content-TypeContent-Type");
        //        map2.put("ceshikey001", "ceshivlue001");
        //        arrayList.add(map);
        //        arrayList.add(map2);
        //        upFile(url, heads, parameters, "aaaaaaaamultipart/mixed", arrayList);
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
    public void get(String url, Map<String, String> heads, Map<String, String> parameters)
        throws SocketTimeoutException, IOException
    {
        GetRequest request = new GetRequest(url);
        for (String key : heads.keySet())
        {
            request.addHeader(key, heads.get(key));
        }
        for (String key : parameters.keySet())
        {
            request.addParameter(key, heads.get(key));
        }
        System.out.println(request);
        IHttpResponse call = client.call(request);
        System.out.println(call);
    }

    /**
     * @Method_Name: post
     * @Description: post请求
     * @param url
     * @param heads
     * @param parameters
     * @throws MalformedURLException void
     * @author bianjianfeng
     * @date 2019年11月10日上午11:28:29
     */
    public void post(String url, Map<String, String> heads, Map<String, String> parameters) throws MalformedURLException
    {
        PostRequest request = new PostRequest(url);
        for (String key : heads.keySet())
        {
            request.addHeader(key, heads.get(key));
        }
        for (String key : parameters.keySet())
        {
            request.addParameter(key, heads.get(key));
        }
        request.removeHeader("Content-Length");
        System.out.println(request);
    }

    /**
     * @Method_Name: postforjson
     * @Description: post请求json
     * @param url
     * @param heads
     * @param parameters
     * @param jsoData
     * @throws IOException void
     * @author bianjianfeng
     * @date 2019年11月10日上午11:28:45
     */
    public void postforjson(String url, Map<String, String> heads, Map<String, String> parameters, String jsoData)
        throws IOException
    {
        PostRequest request = new PostRequest(url, "application/json;charset=UTF-8", jsoData);
        for (String key : heads.keySet())
        {
            request.addHeader(key, heads.get(key));
        }
        for (String key : parameters.keySet())
        {
            request.addParameter(key, heads.get(key));
        }
        System.out.println(request);
    }

    /**
     * @Method_Name: upFile
     * @Description: 上传文件
     * @param url
     * @param heads
     * @param parameters
     * @param headContentType
     * @param listMaps
     * @throws IOException void
     * @author bianjianfeng
     * @date 2019年11月10日上午11:30:39
     */
    public void upFile(String url, Map<String, String> heads, Map<String, String> parameters, String headContentType,
        List<LinkedHashMap<String, String>> listMaps) throws IOException
    {
        MultipartRequest request = new MultipartRequest("POST", url, headContentType);
        for (LinkedHashMap<String, String> linkedHashMap : listMaps)
        {
            part(request, linkedHashMap);
        }

        for (String key : heads.keySet())
        {
            request.addHeader(key, heads.get(key));
        }
        for (String key : parameters.keySet())
        {
            request.addParameter(key, heads.get(key));
        }

        request.removeHeader("Transfer-Encoding");
        System.out.println(request);
    }

    /**@Method_Name: part
     * @Description: 填充part
     * @param request
     * @param map TODO
     * @throws IOException void
     * @author bianjianfeng
     * @return
     * @date 2019年11月10日上午10:13:52
     */
    public IPart part(MultipartRequest request, LinkedHashMap<String, String> map) throws IOException
    {
        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        int count = 0;
        IPart part = null;
        while (iterator.hasNext())
        {
            Entry<String, String> entry = iterator.next();
            if (count == 0)
            {
                if (entry.getKey().equals("filePath"))
                {
                    part = new Part(new File(entry.getValue()));
                }
                else
                {
                    part = new Part(entry.getKey(), entry.getValue());
                }
            }
            else
            {
                part.addHeader(entry.getKey(), entry.getValue());
            }
            count++;
        }
        request.addPart(part);
        return part;
    }

    public void downLoadFile(String url, Map<String, String> heads, Map<String, String> parameters, String destination)
        throws SocketTimeoutException, IOException, InterruptedException, ExecutionException
    {
        GetRequest request = new GetRequest(url);
        for (String key : heads.keySet())
        {
            request.addHeader(key, heads.get(key));
        }
        for (String key : parameters.keySet())
        {
            request.addParameter(key, heads.get(key));
        }
        System.out.println(request);
        IFutureResponse send = client.send(request);
        Thread.sleep(30);
        IHttpResponse response = send.get();
        BodyDataSource body = response.getBody();

        InputStream inputStream = body.toInputStream();

        writeToLocal(destination, inputStream);
    }


    public void writeToLocal(String destination, InputStream input) throws IOException
    {
        int index;
        byte[] bytes = new byte[1024];
        final File parentFile = new File(destination).getParentFile();
        if (!parentFile.exists())
        {
            parentFile.mkdirs();
        }
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1)
        {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();
    }

}
