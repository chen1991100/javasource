package com.doll.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;


public class PayUtils {
	private static AlipayClient alipayClient = null; 
	
	public static synchronized AlipayClient getAlipayClientInstance() {
		if (alipayClient == null) {
			alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		}
		return alipayClient;
    }
}
