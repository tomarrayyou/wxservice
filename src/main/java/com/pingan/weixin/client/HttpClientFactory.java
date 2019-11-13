package com.pingan.weixin.client;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.*;

/**
 * @description: httpclient 4.3.x
 * @author: shouwangqingzhong
 * @date: 2019/11/13 11:23
 */
public class HttpClientFactory {

    private static final String[] supportedProtocols = new String[]{"TLSv1"};

    public static CloseableHttpClient createHttpClient(){
        return createHttpClient(100,10,5000,2);
    }


    /*
     * @Author: shouwangqingzhong
     * @Description: 获取客户端
     * @Date: 2019/11/13 14:18
     * @Param: [maxTotal, maxPerRoute, timeout, retryExecutionCount]
     * @return: org.apache.http.impl.client.CloseableHttpClient
     * @version: 3.0.0
     **/
    public static CloseableHttpClient createHttpClient(int maxTotal,int maxPerRoute,int timeout,int retryExecutionCount){
        try {
            SSLContext sslContext = SSLContexts.custom().useSSL().build();
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
            poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(timeout).build();
            poolingHttpClientConnectionManager.setDefaultSocketConfig(socketConfig);
            return HttpClientBuilder.create()
                                    .setConnectionManager(poolingHttpClientConnectionManager)
                                    .setSSLSocketFactory(sf)
                                    .setRetryHandler(new HttpRequestRetryHandlerImpl(retryExecutionCount))
                                    .build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * @Author: shouwangqingzhong
     * @Description: key store 类型HttpClient
     * @Date: 2019/11/13 14:37
     * @Param: [keyStore, keyPassword, timeout, retryExecutionCount]
     * @return: org.apache.http.impl.client.CloseableHttpClient
     * @version: 3.0.0
     **/
    public static CloseableHttpClient createKeyMaterialHttpClient(KeyStore keyStore,String keyPassword,int timeout,int retryExecutionCount){
        return createKeyMaterialHttpClient(keyStore,keyPassword,supportedProtocols,timeout,retryExecutionCount);
    }


    /*
     * @Author: shouwangqingzhong
     * @Description: key store 类型HttpClient
     * @Date: 2019/11/13 14:45
     * @Param: [keyStore, keyPassword, supportedProtocols, timeout, retryExecutionCount]
     * @return: org.apache.http.impl.client.CloseableHttpClient
     * @version: 3.0.0
     **/
    private static CloseableHttpClient createKeyMaterialHttpClient(KeyStore keyStore, String keyPassword, String[] supportedProtocols, int timeout, int retryExecutionCount) {
        try {
            SSLContext sslContext = SSLContexts.custom().useSSL().loadKeyMaterial(keyStore, keyPassword.toCharArray()).build();
            SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext, supportedProtocols, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(timeout).build();
            return HttpClientBuilder.create()
                                    .setDefaultSocketConfig(socketConfig)
                                    .setSSLSocketFactory(sf)
                                    .setRetryHandler(new HttpRequestRetryHandlerImpl(retryExecutionCount))
                                    .build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * @Author: shouwangqingzhong
     * @Description: 请求超时
     * @Date: 2019/11/13 14:09
     * @Param:
     * @return:
     * @version: 3.0.0
     **/
    private static class HttpRequestRetryHandlerImpl implements HttpRequestRetryHandler{

        private int retryExcutionCount;

        public HttpRequestRetryHandlerImpl(int retryExcutionCount) {
            this.retryExcutionCount = retryExcutionCount;
        }

        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount > retryExcutionCount){
                return false;
            }
            if (exception instanceof InterruptedIOException){
                return false;
            }
            if (exception instanceof UnknownHostException){
                return false;
            }
            if (exception instanceof ConnectTimeoutException){
                return true;
            }
            if (exception instanceof SSLException){
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (!idempotent){
                return true;
            }
            return false;
        }
    }
}
