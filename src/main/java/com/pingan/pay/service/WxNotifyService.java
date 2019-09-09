package com.pingan.pay.service;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @description: 微信通知服务
 * @author: shouwangqingzhong
 * @date: 2019/8/28 16:18
 */
@Api("微信通知服务")
@Service
public class WxNotifyService {

    private static Logger logger = LoggerFactory.getLogger(WxNotifyService.class);

    public Object refundNotify(HttpServletRequest request) throws IOException {
        //获取通知报文
        String notifyStr = getRequestStr(request);
        logger.info("WxNotifyService refundNotify notifyStr is"+notifyStr);

        return null;
    }

    private String getRequestStr(HttpServletRequest request) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = request.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        while ((str = bufferedReader.readLine())!= null){
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        //释放资源
        inputStream.close();
        return buffer.toString();
    }
}
