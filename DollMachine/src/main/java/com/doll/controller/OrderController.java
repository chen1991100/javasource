package com.doll.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.doll.entity.PayOrder;
import com.doll.entity.Price;
import com.doll.pagebean.ResponseBean;
import com.doll.pagebean.ResultState;
import com.doll.pay.WXMain;
import com.doll.pay.WXPayUtil;
import com.doll.service.OrderService;
import com.doll.service.PriceService;
import com.doll.utils.AlipayConfig;
import com.doll.utils.Hash;
import com.doll.utils.PayUtils;
import com.doll.utils.RequestUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private PriceService priceService;

	@RequestMapping("/status")
	@ResponseBody
	public ResponseBean status(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseBean responseBean = new ResponseBean();
		String outTradeNo = RequestUtil.getStringXss(request, "outTradeNo");
		if (!StringUtils.isEmpty(outTradeNo)) {
			PayOrder payOrder = orderService.getOne(outTradeNo);
			if (payOrder != null && payOrder.getOrderStatus() == 1) {
				responseBean.setStatus(ResultState.OK);
				responseBean.setDataObject(JSONObject.toJSONString(payOrder));
			} else {
				responseBean.setMsg("订单不存在或者未支付");
			}
		} else {
			responseBean.setMsg("订单参数不正确");
		}
		return responseBean;

	}

	@RequestMapping("/product")
	@ResponseBody
	public ResponseBean product(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ResponseBean responseBean = new ResponseBean();
		PrintWriter out = response.getWriter();
		String orderinfo = "";
		int price_id = RequestUtil.getInt(request, "price_id");
		int user_id = RequestUtil.getInt(request, "user_id");
		String channel = RequestUtil.getStringXss(request, "channel");
		String outTradeNo = RequestUtil.getStringXss(request, "outTradeNo");
		Price price = priceService.getOne(price_id);
		int reallyTotalAmount = price.getRmb();
		DecimalFormat df = new DecimalFormat("0.00");
		String priceValue_float = df.format((reallyTotalAmount)
				/ (float) 100);
		if ("alipay".equals(channel)) {
			AlipayTradeAppPayRequest aplAlipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody("娃娃粉");
			model.setSubject("娃娃粉星币购买");
			model.setOutTradeNo(outTradeNo);
			model.setTimeoutExpress("30m");
			model.setTotalAmount(priceValue_float);
			model.setProductCode("QUICK_MSECURITY_PAY");
			aplAlipayTradeAppPayRequest.setBizModel(model);
			aplAlipayTradeAppPayRequest.setNotifyUrl(AlipayConfig.notify_url_alipay);
			try {
				// 这里和普通的接口调用不同，使用的是sdkExecute
				AlipayTradeAppPayResponse alipayTradeAppPayResponse = PayUtils
						.getAlipayClientInstance().sdkExecute(
								aplAlipayTradeAppPayRequest);
				if (alipayTradeAppPayResponse.isSuccess()) {
					System.out.println(alipayTradeAppPayResponse.getBody());
					orderinfo = alipayTradeAppPayResponse.getBody();
					responseBean.setStatus(ResultState.OK);
					responseBean.setDataObject(orderinfo);
				}

			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
		} else {
			String client_ip = request.getRemoteAddr();
			if (client_ip == null) {
				client_ip = "127.0.0.1";
			}
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("body", "娃娃粉-星币购买");
			data.put("out_trade_no", outTradeNo);
			data.put("device_info", "WEB");
			data.put("fee_type", "CNY");
			data.put("total_fee", "" + reallyTotalAmount);
			data.put("spbill_create_ip", client_ip);
			data.put("notify_url", AlipayConfig.notify_url_wx);
			data.put("trade_type", "APP");
			data.put("product_id", outTradeNo);
			
			try {
				Map<String, String> returnData = WXMain.doOrder(data);
				if(!returnData.isEmpty()){
					if("SUCCESS".equals(returnData.get("return_code"))&&"SUCCESS".equals(returnData.get("result_code"))){
						String prepay_id = returnData.get("prepay_id");
						System.out.println(prepay_id);
						responseBean.setStatus(ResultState.OK);
						responseBean.setDataObject(WXMain.getUnifiedOrderApp(prepay_id));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if(responseBean.getStatus()==ResultState.OK){
			PayOrder payOrder = new PayOrder();
			payOrder.setPriceId(price_id);
			payOrder.setChannel(channel);
			payOrder.setUserId(user_id);
			payOrder.setOuttradeno(outTradeNo);
			payOrder.setTotalAmount(reallyTotalAmount);
			payOrder.setPayCoin(price.getCoin());
			payOrder.setInsertTime(new Timestamp(System.currentTimeMillis()));
			String pk = orderService.insertPayInfo(payOrder);
			if(!StringUtils.isEmpty(pk)){
				responseBean.setStatus(ResultState.OK);
			}else{
				responseBean.setStatus(ResultState.Error);
			}
		}else{
			responseBean.setStatus(ResultState.Error);
		}
		return responseBean;
	}
	
	@RequestMapping("/notifywx")
	public void notifywx(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		Map<String, String> backMap = new HashMap<String, String>();
		backMap.put("return_code", "FAIL");
		
		StringBuffer xmlStr = new StringBuffer();
		BufferedReader reader = request.getReader();
        String line = null;
        while ((line = reader.readLine()) != null) {
             xmlStr.append(line);
        }
		try {
			Map<String, String> respData = WXPayUtil.xmlToMap(xmlStr.toString());
			if(!respData.isEmpty()&&WXMain.isPayResultNotifySignatureValid(respData)&&"SUCCESS".equals(respData.get("return_code"))&&"SUCCESS".equals(respData.get("result_code"))){
				String out_trade_no = respData.get("out_trade_no");
				String trade_no = respData.get("transaction_id");
				int total_amount = Hash.toInt(respData.get("total_fee"));
				int ret = orderService.updatePayOrder(out_trade_no,trade_no,total_amount);
				if(ret>0){
					backMap.put("return_code", "SUCCESS");
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(WXPayUtil.mapToXml(backMap));
		out.flush();
        out.close();
        
	}

	@RequestMapping("/notifyalipay")
	public void notifyalipay(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String return_flag="fail";
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status")
				.getBytes("ISO-8859-1"), "UTF-8");

		String total_amount = new String(request.getParameter("total_amount")
				.getBytes("ISO-8859-1"), "UTF-8");

		try {
			boolean flag = AlipaySignature.rsaCheckV1(params,
					AlipayConfig.alipay_public_key, AlipayConfig.charset,
					"RSA2");
			if (flag) {
				if (("TRADE_SUCCESS").equals(trade_status)) {
					BigDecimal a = new BigDecimal(total_amount);
		    		BigDecimal b = new BigDecimal(100);
		        	int total_amount_int = a.multiply(b).intValue();
					int ret = orderService.updatePayOrder(out_trade_no,trade_no,total_amount_int);
					if(ret>0){
						return_flag="success";
					}
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		out.print(return_flag);
		out.flush();
        out.close();
	}
	
}
