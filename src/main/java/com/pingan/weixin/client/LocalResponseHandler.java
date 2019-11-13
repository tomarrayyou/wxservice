package com.pingan.weixin.client;

import lombok.Data;

/**
 * @description:
 * @author: shouwangqingzhong
 * @date: 2019/11/13 14:54
 */
@Data
public abstract class LocalResponseHandler {

    protected String uriId;

    protected long startTime = System.currentTimeMillis();
}
