package com.zzyl.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzyl.common.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

@Slf4j
public class HttpTest {

    @Test
    public void testHttp() {
        String content = HttpUtil.get("https://www.itheima.com");
        System.out.println("content = " + content);
    }

    @Test
    public void testGetProjectList() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", 1);
        params.put("pageSize", 5);
        //String content = HttpUtil.get("http://localhost:9000/serve/project/list", params);
        HttpResponse response = HttpRequest.get("http://localhost:9000/serve/project/list")
                .form("pageNum",1)
                .form("pageSize",5)
                //.form(params)
                .header("authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjczZDZiYzFkLTdiMTMtNGRlNS1iYmU5LTE1NTczM2VmNTYyNyJ9.QzUPv3XsHhEwC6Vw66Ux6RhyIxi6oZBRQcW7KdINkPFfxarufRQ9StiDWp-4I11H5N4xKGG5gWOgGyvjxJZWzQ")
                // 调用第三接口时，一定要设置超时时间
                .timeout(5000)
                .execute();
        if (response.isOk()) {
            // 响应了200状态
            String content = response.body();//响应的内容
            System.out.println("content = " + content);
        }
    }
    @Test
    public void testPost() {
        String url = "http://localhost:9000/nursing/project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);
        // 添加护理项目，传递json数据格式
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));
        System.out.println(result);
    }

    @Test
    public void testCreatePost() {
        String url = "http://localhost:9000/serve/project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试999");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);

        // 发送请求
        HttpResponse response = //HttpUtil.createPost(url).body(JSONUtil.toJsonStr(paramMap))
        HttpRequest.post(url).body(JSONUtil.toJsonStr(paramMap))
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjczZDZiYzFkLTdiMTMtNGRlNS1iYmU5LTE1NTczM2VmNTYyNyJ9.QzUPv3XsHhEwC6Vw66Ux6RhyIxi6oZBRQcW7KdINkPFfxarufRQ9StiDWp-4I11H5N4xKGG5gWOgGyvjxJZWzQ")
                .execute();
        if(response.isOk()) {
            System.out.println(response.body());
        }else{
            System.out.println(response);
        }

    }

    /**
     * 获取openId
     */
    @Test
    public void testGetOpenId(){
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", "wx1353e9f7ea1773cd");
        paramMap.put("secret", "0dab0527b9bf519a3b76aff62fdd9524");
        paramMap.put("js_code", "0b3UzIkl2NNfFf4gftml2HHwJE2UzIka");
        paramMap.put("grant_type", "authorization_code");

        HttpResponse res = HttpRequest.get(url).form(paramMap).timeout(5000).execute();
        if(res.isOk()) {
            String result = res.body();
            log.info("获取openId: {}", result);
            //解析json对象
            JSONObject jsonObject = JSONUtil.parseObj(result);
            String openid = jsonObject.getStr("openid");
            System.out.println("openid = " + openid);
        }
    }

    @Test
    public void testGetPhone(){
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token={}";
        String accessToken = getAccessToken();
        url = StrUtil.format(url, accessToken);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("code", "d4f51615484feb4cc04d1bff21515374e9f26d5e674385e02c0dd718b28fddf6");
        HttpResponse res = HttpRequest.post(url).body(JSONUtil.toJsonStr(paramMap)).timeout(5000).execute();
        if(res.isOk()) {
            String result = res.body();
            log.info("获取手机号: {}", result);
            //解析json对象
            JSONObject jsonObject = JSONUtil.parseObj(result);
            String phoneNumber = jsonObject.getJSONObject("phone_info").getStr("phoneNumber");
            Object phone = jsonObject.getByPath("phone_info.phoneNumber");
            System.out.println("phone = " + phone);
            System.out.println("phoneNumber = " + phoneNumber);
        }
    }

    private String getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", "wx1353e9f7ea1773cd");
        paramMap.put("secret", "0dab0527b9bf519a3b76aff62fdd9524");
        paramMap.put("grant_type", "client_credential");
        HttpResponse res = HttpRequest.get(url).form(paramMap).timeout(5000).execute();
        if(res.isOk()) {
            String result = res.body();
            log.info("获取access_token: {}", result);
            //解析json对象
            JSONObject jsonObject = JSONUtil.parseObj(result);
            String access_token = jsonObject.getStr("access_token");
            System.out.println("access_token = " + access_token);
            return access_token;
        }
        return null;
    }
}
