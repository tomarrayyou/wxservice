package com.pingan.weixin.client;

import com.alibaba.fastjson.JSON;
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
 * @date: 2019/11/13 14:52
 */
public class JsonResponseHandler {

    private static Logger logger = LoggerFactory.getLogger(JsonResponseHandler.class);


    public static class JsonResponseHandlerImpl<T> extends LocalResponseHandler implements ResponseHandler<T> {

        private Class<T> clazz;

        public JsonResponseHandlerImpl(String uriId,Class<T> clazz){
            this.uriId = uriId;
            this.clazz = clazz;
        }

        @Override
        public T handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status >= 200 && status <300){
                HttpEntity entity = httpResponse.getEntity();
                String str = EntityUtils.toString(entity, "utf-8");
                logger.info("URI[{}] elapsed time:{} ms RESPONSE DATA :{}",super.uriId,System.currentTimeMillis()-super.startTime,str);
                return JSON.parseObject(str,clazz);
            }else {
                throw new RuntimeException("Unexpected response status: "+status);
            }
        }
    }
}
