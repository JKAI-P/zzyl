package com.zzyl.nursing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 入住配置DTO
 */
@Data
@ApiModel("入住配置DTO")
public class CheckInConfigDto {

    @ApiModelProperty(value = "床位ID")
    private Long bedId;

    @ApiModelProperty(value = "楼层ID")
    private Long floorId;

    @ApiModelProperty(value = "楼层名称")
    private String floorName;

    @ApiModelProperty(value = "房间ID")
    private Long roomId;

    @ApiModelProperty(value = "房间编号")
    private String code;

    @ApiModelProperty(value = "护理等级ID")
    private Long nursingLevelId;

    @ApiModelProperty(value = "护理等级名称")
    private String nursingLevelName;

    @ApiModelProperty(value = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "费用开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feeStartDate;

    @ApiModelProperty(value = "费用结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime feeEndDate;

    @ApiModelProperty(value = "床位费")
    private BigDecimal bedFee;

    @ApiModelProperty(value = "护理费")
    private BigDecimal nursingFee;

    @ApiModelProperty(value = "押金")
    private BigDecimal deposit;

    @ApiModelProperty(value = "政府补贴")
    private BigDecimal governmentSubsidy;

    @ApiModelProperty(value = "保险支付")
    private BigDecimal insurancePayment;

    @ApiModelProperty(value = "其他费用")
    private BigDecimal otherFees;
}
