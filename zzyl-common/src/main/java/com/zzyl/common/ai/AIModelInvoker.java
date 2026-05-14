package com.zzyl.common.ai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ResponseFormatJsonObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class AIModelInvoker {

    private final AliBaiLianProperties aliBaiLianProperties;

    public String invoke(String prompt) {
        log.info("调用模型开始");
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(aliBaiLianProperties.getApiKey())
                .baseUrl(aliBaiLianProperties.getBaseUrl())
                .build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)//设置发送的内容
                .responseFormat(ResponseFormatJsonObject.builder().build())//指定返回的数据格式
                .model(aliBaiLianProperties.getModel())//设置调用的模型名称
                .build();
        ChatCompletion chatCompletion = client.chat().completions().create(params);//调用大模型
        String result = chatCompletion.choices().get(0).message().content().get();//获取模型结果
        log.info("调用模型结束: {}", result);
        return result;
    }
}