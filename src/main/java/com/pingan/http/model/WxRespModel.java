package com.pingan.http.model;

import lombok.Data;

/**
 * @description: 微信请求返回xml对象 (事例)
 * @author: shouwangqingzhong
 * @date: 2019/8/22 18:00
 */
@Data
public class WxRespModel {

    private String message;

    private Integer status;

}
