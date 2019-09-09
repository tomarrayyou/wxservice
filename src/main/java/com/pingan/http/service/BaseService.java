package com.pingan.http.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * @description: http请求服务基类
 * @author: shouwangqingzhong
 * @date: 2019/8/22 17:46
 */
public class BaseService {

    private static Logger logger = LoggerFactory.getLogger(BaseService.class);
    //请求地址
    private String url;

    //发送请求的http请求器
    private IServiceRequest iServiceRequest;

    public BaseService(String url) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.url = url;
        Class c = Class.forName("com.pingan.http.service.HttpServiceRequest");
        iServiceRequest = (IServiceRequest) c.newInstance();
    }

    protected String sendPost(Object object) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return iServiceRequest.sendPost(url,object);
    }

    /**
     * @Author: shouwangqingzhong
     * @Description:  供用户定义自己的请求器
     * @Date: 2019/8/22 17:57
     * @Param: [iServiceRequest]
     * @return: void
     * @version: 3.0.0
     **/
    public void setiServiceRequest(IServiceRequest iServiceRequest){
        this.iServiceRequest = iServiceRequest;
    }
}
