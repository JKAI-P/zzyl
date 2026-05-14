package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 入住申请DTO
 */
@Data
@ApiModel("入住申请DTO")
public class CheckInApplyDto {

    @ApiModelProperty(value = "老人信息")
    private CheckInElderDto checkInElderDto;

    @ApiModelProperty(value = "家属信息列表")
    private List<ElderFamilyDto> elderFamilyDtoList;

    @ApiModelProperty(value = "入住配置")
    private CheckInConfigDto checkInConfigDto;

    @ApiModelProperty(value = "签约办理")
    private CheckInContractDto checkInContractDto;
}
