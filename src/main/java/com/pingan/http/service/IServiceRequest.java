package com.pingan.http.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * @Author: shouwangqingzhong
 * @Description:  请求器标准接口
 * @Date: 2019/8/22 17:49
 * @Param:
 * @return:
 * @version: 3.0.0
 **/
public interface IServiceRequest {

    public String sendPost(String url,Object object) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;
}
