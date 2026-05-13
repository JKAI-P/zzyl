package com.zzyl.nursing.dto;

import lombok.Data;

@Data
public class ElderQueryDto {
    private Integer pageNum;
    private Integer pageSize;
    private String name;
    private String idCardNo;
    private Integer status;
}
