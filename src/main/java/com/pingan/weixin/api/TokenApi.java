package com.pingan.weixin.api;

import com.pingan.weixin.model.Token;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 * @description: 获取access_token
 * @author: shouwangqingzhong
 * @date: 2019/11/13 11:23
 */
public class TokenApi extends BaseApi{

    public static Token token(String appid,String secret){
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri(WX_URI + "/cgi-bin/token")
                .addParameter("grant_type", "client_credential")
                .addParameter("appid", appid)
                .addParameter("secret", secret)
                .build();
        return null;
    }
}
