package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 入住申请老人信息DTO
 */
@Data
@ApiModel("入住申请老人信息DTO")
public class CheckInElderDto {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "性别（0女 1男）")
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "身份证人像面图片")
    private String idCardPortraitImg;

    @ApiModelProperty(value = "身份证国徽面图片")
    private String idCardNationalEmblemImg;

    @ApiModelProperty(value = "老人头像")
    private String image;
}
