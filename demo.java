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

	public static final String APP_ID = "";
	public static final String APP_KEY = "";

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
		// 		id.getUid() 获取 或者
		//      new SeckenReqId("uid")
		event = api.realtimeAuth(
				AuthType.CLICK,
				new SeckenReqId(""));
		System.out.println(event.toString());
		id = waitResult(event.getEvent());
		
		SeckenSignature resp = api.queryAuthToken("query_token");
	}
}

