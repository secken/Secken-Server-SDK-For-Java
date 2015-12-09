# Secken Private Cloud Server SDK For Java

## 简介（Description）
Secken Private Cloud Server SDK For Java是Secken官方提供了一套用于和洋葱验证服务交互的SDK组件，通过使用它，您可以简化集成Secken服务的流程并降低开发成本。

密码就要大声说出来，开启无密时代，让密码下岗

洋葱是一个基于云和用户生物特征的身份验证服务。网站通过集成洋葱，可以快速实现二维码登录，或在支付、授权等关键业务环节使用指纹、声纹或人脸识别功能，从而彻底抛弃传统的账号密码体系。对个人用户而言，访问集成洋葱服务的网站将无需注册和记住账号密码，直接使用生物特征验证提高了交易安全性，无需担心账号被盗。洋葱还兼容Google验证体系，支持国内外多家网站的登录令牌统一管理。

【联系我们】

官网：https://www.yangcong.com

微信：yangcongAPP

微信群：http://t.cn/RLGDwMJ

QQ群：475510094

微博：http://weibo.com/secken

帮助：https://www.yangcong.com/help

合作：010-64772882 / market@secken.com

支持：support@secken.com

帮助文档：https://www.yangcong.com/help

项目地址：https://github.com/secken/Secken-Server-SDK-For-Java

## 更新发布（Update & Release Notes）

```
【1.0.0】更新内容：
1、完成了接口封装。
```

## Demo:

```java
import java.io.UnsupportedEncodingException;

import secken.sdk.SeckenApi;
import secken.sdk.entry.request.SeckenReqEvent;
import secken.sdk.entry.request.SeckenReqId;
import secken.sdk.entry.response.SeckenEvent;
import secken.sdk.entry.response.SeckenId;
import secken.sdk.entry.response.SeckenQr;
import secken.sdk.entry.response.SeckenSignature;
import secken.sdk.enums.AuthType;
import secken.sdk.exceptions.SeckenParamException;
import secken.sdk.exceptions.SeckenSignatureVerifyException;


public class SeckenSDKTest {

    public static SeckenApi api = null;
    
    // app_id app_key 可到洋葱官网申请sdk应用
    public static final String APP_ID = "";
    public static final String APP_KEY = "";

    // 轮询等待事件结果
    public static SeckenId waitResult(SeckenReqEvent event)
            throws InterruptedException, SeckenSignatureVerifyException {
        while (true) {
            SeckenId resp = (SeckenId) api.getResult(event);
            System.out.println(resp.toString());
            Thread.sleep(5000);
            if (resp.getStatus() == 200) {
                return resp;
            }
        }
    }

    public static void main(String[] args) throws SeckenParamException,
            SeckenSignatureVerifyException, InterruptedException,
            UnsupportedEncodingException {
        
        api = new SeckenApi(APP_ID, APP_KEY);

        SeckenQr qr = null;
        SeckenId id = null;
        SeckenEvent event = null;

        // 获得验证二维码地址及数据
        qr = api.getAuth();
        System.out.println(qr.toString());
        // 等待成功返回结果
        id = waitResult(qr.getEvent());

        // 一键推送验证
        // SeckenReqId 可以从
        //      id.getUid() 获取 或者
        //      new SeckenReqId("uid")
        event = api.realtimeAuth(
                AuthType.CLICK,
                new SeckenReqId(""));
        System.out.println(event.toString());
        id = waitResult(event.getEvent());
        
        // 查询app端sdk token状态
        SeckenSignature resp = api.queryAuthToken("query_token");
    }
}
```


