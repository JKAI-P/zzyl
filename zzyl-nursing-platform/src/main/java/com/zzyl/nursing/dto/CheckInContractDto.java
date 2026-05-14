package com.zzyl.nursing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 入住合同DTO
 */
@Data
@ApiModel("入住合同DTO")
public class CheckInContractDto {

    @ApiModelProperty(value = "合同名称")
    private String contractName;

    @ApiModelProperty(value = "第三方联系人姓名")
    private String thirdPartyName;

    @ApiModelProperty(value = "第三方联系人电话")
    private String thirdPartyPhone;

    @ApiModelProperty(value = "协议路径")
    private String agreementPath;

    @ApiModelProperty(value = "签约日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signDate;
}
