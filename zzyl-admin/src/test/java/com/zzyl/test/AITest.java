package com.zzyl.test;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class AITest{
    public static void main(String[] args) {
        // 启动 Spring Boot 应用以加载配置
        ConfigurableApplicationContext context = SpringApplication.run(com.zzyl.RuoYiApplication.class, args);
        
        // 从配置文件读取配置
        String apiKey = context.getEnvironment().getProperty("aliyun.dashscope.api-key");
        String baseUrl = context.getEnvironment().getProperty("aliyun.dashscope.base-url");
        String model = context.getEnvironment().getProperty("aliyun.dashscope.model");
        
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("我是谁")
                .model(model)
                .build();

        try {
            ChatCompletion chatCompletion = client.chat().completions().create(params);
            System.out.println(chatCompletion.choices().get(0).message().content().get());
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}