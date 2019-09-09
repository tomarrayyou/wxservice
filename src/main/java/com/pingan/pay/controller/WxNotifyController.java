package com.pingan.pay.controller;

import com.pingan.http.service.HttpServiceRequest;
import com.pingan.pay.service.WxNotifyService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description: 微信退费回调通知地址
 * @author: shouwangqingzhong
 * @date: 2019/8/28 16:12
 */
@Api("微信退费回调通知地址")
@RestController
@RequestMapping("/wx/notify")
public class WxNotifyController {

    private static Logger logger = LoggerFactory.getLogger(WxNotifyController.class);
    @Autowired
    private WxNotifyService wxNotifyService;

    @RequestMapping(value = "/refund")
    @ResponseBody
    public Object refundNotify(HttpServletRequest request) throws IOException {
        logger.info("微信退款回调： request ={}",request);
        return wxNotifyService.refundNotify(request);
    }
}
