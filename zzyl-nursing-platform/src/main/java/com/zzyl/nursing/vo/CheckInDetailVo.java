package com.zzyl.nursing.vo;

import com.zzyl.nursing.domain.Contract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("入住VO")
public class CheckInDetailVo {

    @ApiModelProperty(value = "入住ID")
    private Long id;

    @ApiModelProperty(value = "老人姓名")
    private String elderName;

    @ApiModelProperty(value = "老人ID")
    private Long elderId;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别（0女 1男）")
    private Integer sex;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "老人头像")
    private String image;

    @ApiModelProperty(value = "床位编号")
    private String bedNumber;

    @ApiModelProperty(value = "楼层名称")
    private String floorName;

    @ApiModelProperty(value = "房间编号")
    private String roomCode;

    @ApiModelProperty(value = "护理等级名称")
    private String nursingLevelName;

    @ApiModelProperty(value = "开始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "费用开始日期")
    private String feeStartDate;

    @ApiModelProperty(value = "费用结束日期")
    private String feeEndDate;

    @ApiModelProperty(value = "床位费")
    private String bedFee;

    @ApiModelProperty(value = "护理费")
    private String nursingFee;

    @ApiModelProperty(value = "押金")
    private String deposit;

    @ApiModelProperty(value = "政府补贴")
    private String governmentSubsidy;

    @ApiModelProperty(value = "保险支付")
    private String insurancePayment;

    @ApiModelProperty(value = "其他费用")
    private String otherFees;

    @ApiModelProperty(value = "合同状态 (0: 未生效, 1: 已生效, 2: 已过期, 3: 已失效)")
    private Integer contractStatus;

    @ApiModelProperty(value = "状态 (0: 已入住, 1: 已退住)")
    private Integer status;

    @ApiModelProperty(value = "合同信息")
    private Contract contract;

    @ApiModelProperty(value = "家属信息列表")
    private List<ElderFamilyVo> elderFamilyVoList;

    @ApiModelProperty(value = "老人信息（嵌套对象）")
    private CheckInElderVo checkInElderVo;

    @ApiModelProperty(value = "入住配置（嵌套对象）")
    private CheckInConfigVo checkInConfigVo;
}
