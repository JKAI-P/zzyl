package com.zzyl.nursing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 护理员老人关联对象 nursing_elder
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("护理员老人关联实体")
public class NursingElder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 护理员id
     */
    @Excel(name = "护理员id")
    @ApiModelProperty(value = "护理员id")
    private Long nursingId;

    /**
     * 老人id
     */
    @Excel(name = "老人id")
    @ApiModelProperty(value = "老人id")
    private Long elderId;

}
