package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("入住配置VO")
public class CheckInConfigVo {

    @ApiModelProperty(value = "入住开始日期")
    private String startDate;

    @ApiModelProperty(value = "入住结束日期")
    private String endDate;

    @ApiModelProperty(value = "费用开始日期")
    private String feeStartDate;

    @ApiModelProperty(value = "费用结束日期")
    private String feeEndDate;

    @ApiModelProperty(value = "护理等级ID")
    private Long nursingLevelId;

    @ApiModelProperty(value = "护理等级名称")
    private String nursingLevelName;

    @ApiModelProperty(value = "床位费用")
    private BigDecimal bedFee;

    @ApiModelProperty(value = "护理费用")
    private BigDecimal nursingFee;

    @ApiModelProperty(value = "押金")
    private BigDecimal deposit;

    @ApiModelProperty(value = "政府补贴")
    private BigDecimal governmentSubsidy;

    @ApiModelProperty(value = "医保支付")
    private BigDecimal insurancePayment;

    @ApiModelProperty(value = "其他费用")
    private BigDecimal otherFees;

    @ApiModelProperty(value = "床位ID")
    private Long bedId;

    @ApiModelProperty(value = "床位编号")
    private String bedNumber;

    @ApiModelProperty(value = "房间编号")
    private String code;

    @ApiModelProperty(value = "楼层ID")
    private Long floorId;

    @ApiModelProperty(value = "楼层名称")
    private String floorName;

    @ApiModelProperty(value = "房间ID")
    private Long roomId;
}
