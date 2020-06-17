package com.server.tools.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Http工具类
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String strResult = EntityUtils.toString(httpEntity);
            logger.debug("url:{},response:{}", url, strResult);
            return strResult;
        } catch (Exception ex) {
            logger.warn("excption:{}", ex);
        } finally {
            httpget.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        return null;
    }

    /**
     * 带参数的get请求
     *
     * @param url
     * @param parMap
     * @return
     */
    public static String get(String url, Map<String, String> parMap) {
        String body = null;
        HttpClientBuilder httpclientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpclient = httpclientBuilder.build();
        HttpGet httpget = new HttpGet(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> paramsEntry = parMap.entrySet();
        for (Map.Entry<String, String> paramEntry : paramsEntry) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        try {
            String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps, "UTF-8"));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                logger.debug("http get status is : {}", response.getStatusLine());//状态码，一般状态码为200时，为正常状态
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.info("Http Get Method Failure : {}", e);
        } finally {
            httpget.releaseConnection();
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        logger.info("response body : {}", body);
        return body;
    }

    /**
     * 带参数post请求
     *
     * @param url
     * @return
     */
    public static String post(String url, Map<String, String> map) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        if (map != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (String key : map.keySet()) {
                nvps.add(new BasicNameValuePair(key, map.get(key)));
            }
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        }
        try {
            CloseableHttpResponse response = httpClient.execute(httpost);
            String strResult = EntityUtils.toString(response.getEntity());
            logger.debug("url:{} ,map:{},response:{}", url, map, strResult);
            return strResult;
        } catch (Exception ex) {
            logger.warn("excption:{}", ex);
        } finally {
            httpost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        return null;
    }


    /**
     * RAW Post请求
     *
     * @param url
     * @param Body
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, String Body) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        httpost.setEntity(new StringEntity(Body, Consts.UTF_8));
        try {
            CloseableHttpResponse response = httpClient.execute(httpost);
            String strResult = EntityUtils.toString(response.getEntity());
            logger.debug("url:{} ,Body:{},response:{}", url, Body, strResult);
            return strResult;
        } catch (Exception ex) {
            logger.warn("excption:{}", ex);
        } finally {
            httpost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        return null;
    }


    /**
     * 微信API请求  （带证书）
     *
     * @param url
     * @param Body
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String weixinApipost(String url, String Body) {
        HttpPost httpost = null;
        CloseableHttpClient httpClient = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File("C:/Users/Administrator/Desktop/apiclient_cert.p12"));
            try {
                keyStore.load(instream, "1370249302".toCharArray());
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, "1370249302".toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            httpost = new HttpPost(url);
            httpost.setEntity(new StringEntity(Body, Consts.UTF_8));
            CloseableHttpResponse response = httpClient.execute(httpost);
            String strResult = EntityUtils.toString(response.getEntity());
            logger.debug("url:{} ,Body:{},response:{}", url, Body, strResult);
            return strResult;
        } catch (Exception ex) {
            logger.warn("excption:{}", ex);
        } finally {
            httpost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpclient.close() Failure :{} " + e);
            }
        }
        return null;
    }


    //微信消息发送
    public static String doHttpPostJson(String Url, String json) {
        String message = "";
        System.out.println(json);
        try {
            URL url = new URL(Url);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(json.getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            message = new String(jsonBytes, "UTF-8");
            System.out.println(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}