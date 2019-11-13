package com.pingan.weixin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @description: 微信请求状态数据
 * @author: shouwangqingzhong
 * @date: 2019/11/13 11:25
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResult {

    private static final String SUCCESS_CODE = "0";

    private String errcode;

    private String errmsg;

    public Boolean isSuccess(){
        return errcode == null || errcode.isEmpty() || errcode.equals(SUCCESS_CODE);
    }
}
