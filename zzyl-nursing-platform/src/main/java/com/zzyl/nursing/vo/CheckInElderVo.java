package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("入住老人信息VO")
public class CheckInElderVo {

    @ApiModelProperty(value = "老人姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别（0女 1男）")
    private Integer sex;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "一寸照片")
    private String image;

    @ApiModelProperty(value = "身份证人像面")
    private String idCardPortraitImg;

    @ApiModelProperty(value = "身份证国徽面")
    private String idCardNationalEmblemImg;
}
