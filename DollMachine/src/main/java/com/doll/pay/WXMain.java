package com.doll.pay;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;



public class WXMain {
	private static WXPay wxpay = null;
	static{
		try {
			WXPayConfigImpl config = WXPayConfigImpl.getInstance();
			wxpay = new WXPay(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static Map<String, String> doOrder(HashMap<String, String> map) throws Exception{
		Map<String, String> returnMap = wxpay.unifiedOrder(map);
		return returnMap;
	}
	public static Map<String, String> doOrderQuery(HashMap<String, String> map) throws Exception{
		Map<String, String> returnMap = wxpay.orderQuery(map);
		return returnMap;
	}
	public static Map<String, String> doOrderClose(HashMap<String, String> map) throws Exception{
		Map<String, String> returnMap = wxpay.closeOrder(map);
		return returnMap;
	}
	public static String getUnifiedOrderJSAPI(String prepay_id) throws Exception {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("package", "prepay_id="+prepay_id);
		return JSONObject.toJSONString(wxpay.jsapiSign(reqData));
	}
	
	public static String getUnifiedOrderApp(String prepay_id) throws Exception {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("package", "Sign=WXPay");
		reqData.put("prepayid", prepay_id);
		return JSONObject.toJSONString(wxpay.jsapiSignApp(reqData));
	}
	public static boolean isPayResultNotifySignatureValid(Map<String, String> reqData) throws Exception {
		return wxpay.isPayResultNotifySignatureValid(reqData);
	}
}
