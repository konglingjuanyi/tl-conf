package com.tiaoling.cloud.conf.utils;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by yhl on 2016/10/9.
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(HttpUtils.class);
    private static final HttpClient httpClient;
    static {
        // 构造HttpClient的实例
        httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(50000); // 连接5秒超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(70000);// 读取30秒超时
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url
     *            请求的URL地址
     * @param json
     *            请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    @SuppressWarnings("deprecation")
    public static String doPost(String url, String json) {
        LOGGER.debug("doPost url is {},parajson is {}", new Object[] { url,
                json });
        String response = null;
        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                "UTF-8");
        // 请求超时
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 20000);
        // 设置Http Post数据
        try {
            method.setRequestBody(json);
            method.setRequestHeader("Connection", "close");
            method.setRequestHeader("Content-type", "application/json");

            String charSet = method.getResponseCharSet();
            httpClient.executeMethod(method);
            response = method.getResponseBodyAsString();
        } catch (Exception e) {
            LOGGER.error("url is {},parajson is {},Exception is {}",
                    new Object[] { url, json, e.getMessage() });
        } finally {
            method.releaseConnection();
        }
        LOGGER.debug("url is {},parajsonjson is {},response is {}",
                new Object[] { url, json, response });
        return response;
    }
    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url
     *            请求的URL地址
     * @param json
     *            请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     */
    @SuppressWarnings("deprecation")
    public static String doPost(String url, String json,Integer timeout) {
        LOGGER.debug("doPost url is {},parajson is {}", new Object[] { url,
                json });
        String response = null;
        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                "UTF-8");
        // 请求超时
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
        //method.getParams().setParameter(HttpMethodParams.t, value);
        // 设置Http Post数据
        try {
            method.setRequestBody(json);
            method.setRequestHeader("Connection", "close");
            method.setRequestHeader("Content-type", "application/json");

            String charSet = method.getResponseCharSet();
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeout);
            httpClient.executeMethod(method);
            response = method.getResponseBodyAsString();
        } catch (Exception e) {
            LOGGER.error("url is {},parajson is {},Exception is {}",
                    new Object[] { url, json, e.getMessage() });
        } finally {
            method.releaseConnection();
        }
        LOGGER.debug("url is {},parajsonjson is {},response is {}",
                new Object[] { url, json, response });
        return response;
    }

    public static String GetUrlContent(String url, String paramStr) {
        String encoding = "UTF-8";
        StringBuffer sBuffer = new StringBuffer();
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建GET方法的实例
        GetMethod getMethod = new GetMethod(url + "?" + paramStr);
        getMethod.addRequestHeader("Content-Type", "text/html;charset="
                + encoding);
        // 使用系统提供的默认的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                        + getMethod.getStatusLine());
            }
            // 读取内容
            byte[] responseBody = getMethod.getResponseBody();
            // 处理内容
            sBuffer.append(new String(responseBody, encoding));
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            getMethod.releaseConnection();
        }
        return sBuffer.toString();
    }

    public static String HttpPost(String url, String method, Map paramMap) {
        String encoding = "UTF-8";
        String webUrl = url + "/" + method;
        if (encoding == null || "".equals(encoding))
            encoding = "UTF-8";
        StringBuffer sBuffer = new StringBuffer();
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建POS方法的实例
        NameValuePair[] pairs = null;
        PostMethod postMethod = new PostMethod(webUrl);
        if (paramMap != null) {
            pairs = new NameValuePair[paramMap.size()];
            Set set = paramMap.keySet();
            Iterator it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                Object key = it.next();
                Object value = paramMap.get(key);
                pairs[i] = new NameValuePair(key.toString(), value.toString());
                i++;
            }
            postMethod.setRequestBody(pairs);
        }
        postMethod.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(50000); // 连接50秒超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);// 读取30秒超时
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                        + postMethod.getStatusLine());
                sBuffer = new StringBuffer();
            } else {
                InputStream resStream = postMethod.getResponseBodyAsStream();
                sBuffer = new StringBuffer(postMethod.getResponseBodyAsString()
                        + "");
            }
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return sBuffer.toString();
    }

    public static String HttpPostForJson(String url, String method, Map paramMap) {
        String encoding = "UTF-8";
        String webUrl = url + "/" + method + ".json";
        if (encoding == null || "".equals(encoding))
            encoding = "UTF-8";
        StringBuffer sBuffer = new StringBuffer();
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建POS方法的实例
        NameValuePair[] pairs = null;
        PostMethod postMethod = new PostMethod(webUrl);
        if (paramMap != null) {
            pairs = new NameValuePair[paramMap.size()];
            Set set = paramMap.keySet();
            Iterator it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                Object key = it.next();
                Object value = paramMap.get(key);
                pairs[i] = new NameValuePair(key.toString(), value.toString());
                i++;
            }
            postMethod.setRequestBody(pairs);
        }
        postMethod.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                        + postMethod.getStatusLine());
            }
            InputStream resStream = postMethod.getResponseBodyAsStream();
            sBuffer = new StringBuffer(postMethod.getResponseBodyAsString()
                    + "");
            // modify by guanshiqiang at 2012-06-01 for 处理接收字符乱码bug end
            // 处理内容
            // sBuffer.append(new String(responseBody,encoding));
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return sBuffer.toString();
    }

    public static String HttpPostForRest(String url, String method, Map paramMap) {
        String encoding = "UTF-8";
        String webUrl = url + "/" + method + ".rest";
        if (encoding == null || "".equals(encoding))
            encoding = "UTF-8";
        StringBuffer sBuffer = new StringBuffer();
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        // 创建POS方法的实例
        NameValuePair[] pairs = null;
        PostMethod postMethod = new PostMethod(webUrl);
        if (paramMap != null) {
            pairs = new NameValuePair[paramMap.size()];
            Set set = paramMap.keySet();
            Iterator it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                Object key = it.next();
                Object value = paramMap.get(key);
                pairs[i] = new NameValuePair(key.toString(), value.toString());
                i++;
            }
            postMethod.setRequestBody(pairs);
        }
        postMethod.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
        try {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                        + postMethod.getStatusLine());
            }
            InputStream resStream = postMethod.getResponseBodyAsStream();
            sBuffer = new StringBuffer(postMethod.getResponseBodyAsString()
                    + "");
            // modify by guanshiqiang at 2012-06-01 for 处理接收字符乱码bug end
            // 处理内容
            // sBuffer.append(new String(responseBody,encoding));
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return sBuffer.toString();
    }

    public static void print(HttpServletResponse response, String json) {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }

    public static String sendPost(String url, String param)
            throws UnsupportedEncodingException {
        param = java.net.URLEncoder.encode(param, "UTF-8");
        DataOutputStream out = null;
        // / PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new DataOutputStream(conn.getOutputStream());
            // 发送请求参数
            out.writeBytes(param);
            // flush输出流的缓冲
            out.flush();
            System.out.println("conn.getInputStream()::=="
                    + conn.getInputStream());
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            if (conn != null) {
                System.out.println("链接未关闭");
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return result;
    }

    public static JSONObject getPostRespone(String url, String inputstr)
            throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        try {
            client.getHttpConnectionManager().getParams()
                    .setSoTimeout(60 * 1000);
            post.getParams().setParameter(
                    HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            post.addRequestHeader("Content-Type",
                    "application/json;charset=UTF-8");
            post.setRequestEntity(new StringRequestEntity(inputstr,
                    "application/json", "UTF-8"));
            client.executeMethod(post);
            InputStream inputStream = post.getResponseBodyAsStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);

            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;

            JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
            return jsonObject;
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return null;
        }

    }
}
