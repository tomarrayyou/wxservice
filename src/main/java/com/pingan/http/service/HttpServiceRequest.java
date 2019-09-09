package com.pingan.http.service;

import com.alibaba.fastjson.JSON;
import com.pingan.config.HttpConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * @description: http请求服务类
 * @author: shouwangqingzhong
 * @date: 2019/8/22 16:26
 */
public class HttpServiceRequest implements IServiceRequest{
    @Autowired
    private HttpConfig httpConfig;

    private static Logger logger = LoggerFactory.getLogger(HttpServiceRequest.class);
    //表示请求器是否已做了初始化工作
    private Boolean hasInit = false;
    //连接超时时间，默认10秒
    private int scokerTimeout = 10000;
    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    public HttpServiceRequest() throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        init();
    }


    private void init() throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        /*//需要证书的协议的请求协议
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //加载本地的证书进行https加密传输，证书可放在项目的resource目下下
        InputStream instream = HttpServiceRequest.class.getResourceAsStream("/apiclient_cert.p12");
        try {
            //设置证书密码
            keyStore.load(instream,httpConfig.getSecret().toCharArray());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }finally {
            instream.close();
        }

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore,httpConfig.getSecret().toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER
        );

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();*/


        //不需要证书的请求协议
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https",SSLConnectionSocketFactory.getSocketFactory())
                .build(),
                null,
                null,
                null
        );

        httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        requestConfig = RequestConfig.custom().setSocketTimeout(scokerTimeout).setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

    @Override
    public String sendPost(String url,Object object) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        if (!hasInit){
            init();
        }

        String result = null;
        HttpPost httpPost = new HttpPost(url);

        /*//如果以xml传输
        XStream xStream = new XStream(new DomDriver("UTF-8",new XmlFriendlyNameCoder("-_","_")));
        //将请求的参数对象，转为xml
        String postDataXML = xStream.toXML(object);
        logger.info("API,POST Data ="+postDataXML);

        //得指明使用UTF-8编码，否则到API服务器的xml中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML,"UTF-8");
        //设置请求头
        httpPost.addHeader("Content-type","text/xml");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        httpPost.setConfig(requestConfig);*/

        //以json作为传输格式
        String postDataJson = JSON.toJSONString(object);
        logger.info("API,POST Data ="+postDataJson);
        //得指明使用UTF-8编码，否则到API服务器的xml中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataJson,"UTF-8");
        //设置请求头
        httpPost.addHeader("Content-type","application/json");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        httpPost.setConfig(requestConfig);


        logger.info("executing request"+httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return result;
    }


    private Object analzying(String data){
        /*//xml转对象
        XStream xStream = new XStream(new DomDriver("UTF-8",new XmlFriendlyNameCoder("-_","_")));
        xStream.alias("xml",Object.class);
        Object o = xStream.fromXML(data);*/

        //json转对象
        Object o = JSON.parseObject(data, Object.class);
        return o;
    }
}
