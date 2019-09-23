package com.pingan.common.model;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: shouwangqingzhong
 * @date: 2019/9/19 17:53
 */
@Data
public class Page<T> {

    private int page;

    private int size;

    private int totalElements;

    private int totalPage;

    private List<T> elements;
}
