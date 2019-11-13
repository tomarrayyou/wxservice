package com.pingan.weixin.model;

import lombok.Data;

/**
 * @description: 微信返回token实体
 * @author: shouwangqingzhong
 * @date: 2019/11/13 11:24
 */
@Data
public class Token extends BaseResult{

    private String access_token;

    private int expires_in;
}
