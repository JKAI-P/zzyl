package com.zzyl;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.auth.Auth;
import com.baidubce.qianfan.model.chat.ChatResponse;

public class AIModelTest {

    private static final String prompt = "帮我讲2个笑话";

    public static void main(String[] args) {
        /**
         * 第一个参数：认证类型，固定选择 Auth.TYPE_OAUTH
         * 第二个参数：accessKeyId，从百度云控制台创建的应用里可以找到
         * 第三个参数：accessKeySecret，从百度云控制台创建的应用里可以找到
         */
        Qianfan qianfan = new Qianfan(Auth.TYPE_OAUTH, "KbayjMBvRwQWMCGN0TCtIRIt", "SH6zWo3RYpfIW3pjrurTh9CqE7MP57dq");
        ChatResponse response = qianfan.chatCompletion()
                .model("ERNIE-4.0-8K-Preview") // 模型名称，要选择自己开通付费的模型
                .addMessage("user", prompt) // 聊天内容，可以设置多个，每个消息包含role（角色，user表示用户，assistant表示模型），content（消息内容）
//                .temperature(0.7) // 采样参数，取值范围(0,1]， 值越小，生成的内容越模糊，范围更大。精准度降低。
//                .maxOutputTokens(2000) // 模型输出最大长度，取值范围[2, 2048]
//                .responseFormat("json_object")  // 模型输出格式，取值范围：text（文本）、json_object（JSON对象）
                .execute();
        String result = response.getResult();
        System.out.println(result);
    }
}