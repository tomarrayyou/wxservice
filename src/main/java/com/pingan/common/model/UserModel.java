package com.pingan.common.model;

import com.pingan.enums.GenderEnum;
import com.pingan.utils.EnumValid;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @description: 用户模型
 * @author: shouwangqingzhong
 * @date: 2019/9/5 17:06
 */
@Data
public class UserModel {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户姓名不能为空")
    private String userName;

    @ApiModelProperty("用户手机号")
    @NotBlank(message = "用户手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$",message = "手机格式不正确")
    private String mobile;

    @ApiModelProperty("用户性别")
    @Enumerated(EnumType.STRING)
    @EnumValid(target = GenderEnum.class, message = "gender取值必须为FEMALE, MALE, UNKNOWN")
    private GenderEnum gender;

    @ApiModelProperty("表记录创建日期")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public static UserModel of(){
        return new UserModel();
    }
}
