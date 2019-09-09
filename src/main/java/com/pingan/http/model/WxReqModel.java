package com.pingan.http.model;

import lombok.Data;

/**
 * @description: 微信服务请求对象 （举例）
 * @author: shouwangqingzhong
 * @date: 2019/8/22 18:02
 */
@Data
public class WxReqModel {

    private String orderId;

    private String price;
}
