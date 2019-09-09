package com.pingan.http.service;

import com.pingan.http.model.WxReqModel;
import com.pingan.http.model.WxRespModel;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * @description: 具体的http请求，微信请求
 * @author: shouwangqingzhong
 * @date: 2019/8/22 17:59
 */
public class WxHttpRequest extends BaseService{

    private static Logger logger = LoggerFactory.getLogger(WxHttpRequest.class);

    public WxHttpRequest(String url) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(url);
    }

    public WxRespModel createRequest(WxReqModel model) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        String result = sendPost(model);
        logger.info("create Request response:"+result);
        //将返回的xml转为对象
        XStream xStream = new XStream(new DomDriver("UTF-8",new XmlFriendlyNameCoder("-_","_")));
        xStream.alias("xml",WxRespModel.class);
        WxRespModel respModel = (WxRespModel)xStream.fromXML(result);
        return respModel;
    }
}
