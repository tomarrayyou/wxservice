package com.pingan.weixin.client;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description:
 * @author: shouwangqingzhong
 * @date: 2019/11/13 15:06
 */
public class XmlResponseHandler {

    private static Logger logger = LoggerFactory.getLogger(XmlResponseHandler.class);


    public static class XmlResponseHandlerImpl<T> extends LocalResponseHandler implements ResponseHandler<T>{

        private Class<T> clazz;

        private String sign_type;
        //签名校验key
        private String key;

        public XmlResponseHandlerImpl(String uriId,Class<T> clazz, String sign_type, String key) {
            this.uriId = uriId;
            this.clazz = clazz;
            this.sign_type = sign_type;
            this.key = key;
        }

        @Override
        public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status <300){
                HttpEntity entity = response.getEntity();
                String str = EntityUtils.toString(entity);
                Header contentType = response.getEntity().getContentType();
                if (null != contentType && !contentType.toString().matches(".*[uU][tT][fF]-8$")){
                    str = new String(str.getBytes("iso-8859-1"),"utf-8");
                }
                logger.info("URI[{}] elapsed time:{} ms RESPONSE DATA :{}",super.uriId,System.currentTimeMillis()-super.startTime,str);
                //未完待续
            }
            return null;
        }
    }
}
