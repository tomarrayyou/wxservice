package com.pingan.common.entity;

import com.pingan.enums.GenderEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Api("用户表")
@Entity
@Table(name = "sys_user")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "USER_ID")
    @TableGenerator(name = "USER_ID",table = "SYS_PK_GENERATOR",pkColumnName = "PK_NAME",valueColumnName = "PK_VALUE",pkColumnValue = "USER_ID",allocationSize = 2)
    private Long id;

    @ApiModelProperty("用户姓名")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty("用户手机号")
    @Column(name = "mobile")
    private String mobile;

    @ApiModelProperty("用户性别")
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @ApiModelProperty("表记录创建日期")
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public static User of(){
        return new User();
    }
}
