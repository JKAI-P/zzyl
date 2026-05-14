package com.zzyl.common.ai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.dashscope")
public class AliBaiLianProperties {
    private String apiKey;
    private String baseUrl;
    private String model;
}
