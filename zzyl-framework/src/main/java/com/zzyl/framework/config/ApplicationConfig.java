package com.zzyl.framework.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序注解配置
 *
 * @author ruoyi
 */
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.zzyl.**.mapper")
public class ApplicationConfig
{
    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization()
    {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
            // 配置 LocalDateTime 的序列化和反序列化格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, 
                new com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer(formatter));
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, 
                new com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer(formatter));
        };
    }
}
