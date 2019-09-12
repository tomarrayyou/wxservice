package com.pingan.common.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @description: 实体基类
 * @author: shouwangqingzhong
 * @date: 2019/9/9 18:52
 */
@Api("实体基类")
@Data
public class DataEntity {

    @ApiModelProperty("有效标志")
    @Column(name = "del_ind")
    private String delInd;

    @ApiModelProperty("创建人")
    @Column(name = "create_id")
    private Long createId;

    @ApiModelProperty("创建时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @ApiModelProperty("最后修改人")
    @Column(name = "last_update_id")
    private Long lastUpdateId;

    @ApiModelProperty("最后修改时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
}

