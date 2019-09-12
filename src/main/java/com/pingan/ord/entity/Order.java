package com.pingan.ord.entity;

import com.pingan.common.entity.DataEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @description: 订单详情表
 * @author: shouwangqingzhong
 * @date: 2019/9/9 18:42
 */
@Api("订单详情表")
@Entity
@Table(name = "order_detail")
@Data
public class Order extends DataEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ORDER_DETAIL_ID")
    @TableGenerator(name = "ORDER_DETAIL_ID",table = "SYS_PK_GENERATOR",pkColumnName = "PK_NAME",valueColumnName = "PK_VALUE",pkColumnValue = "ORDER_DETAIL_ID",allocationSize = 2)
    private Long id;

    @ApiModelProperty("订单类型")
    @Column(name = "order_type")
    private String orderType;

    @ApiModelProperty("订单来源")
    @Column(name = "order_from")
    private String orderFrom;

    @ApiModelProperty("订单费用")
    @Column(name = "order_fee")
    private Double orderFee;

    @ApiModelProperty("订单状态")
    @Column(name = "order_status")
    private String orderStatus;

    @ApiModelProperty("支付状态")
    @Column(name = "pay_status")
    private String payStatus;

    @ApiModelProperty("患者id")
    @Column(name = "patient_id")
    private Long patientId;

    @ApiModelProperty("检查附件")
    @Column(name = "inspection_annex")
    private String inspectionAnnex;

    @ApiModelProperty("检查机构id")
    @Column(name = "office_id")
    private Long officeId;

    @ApiModelProperty("检查科室id")
    @Column(name = "dept_id")
    private String deptId;

}
