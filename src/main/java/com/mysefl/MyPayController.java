package com.mysefl;

import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.PaymentApi;
import com.jfinal.weixin.sdk.kit.IpKit;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import com.jfinal.weixin.sdk.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuhui on 16-4-14.
 */
public class MyPayController  extends Controller {
    private static String appid = "";
    private static String partner = "";
    private static String paternerKey = "";
    private static String notify_url = "http://www.xxx.com/pay/pay_notify";

    public MyPayController() {
    }

    public void index() {
        String openId = "";
        HashMap params = new HashMap();
        params.put("appid", appid);
        params.put("mch_id", partner);
        params.put("body", "JFinal2.0极速开发");
        params.put("out_trade_no", "977773682111");
        params.put("total_fee", "1");
        String ip = IpKit.getRealIp(this.getRequest());
        if(StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }

        params.put("spbill_create_ip", ip);
        params.put("trade_type", PaymentApi.TradeType.JSAPI.name());
        params.put("nonce_str", System.currentTimeMillis() / 1000L + "");
        params.put("notify_url", notify_url);
        params.put("openid", openId);
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        String xmlResult = PaymentApi.pushOrder(params);
        System.out.println(xmlResult);
        Map result = PaymentKit.xmlToMap(xmlResult);
        String return_code = (String)result.get("return_code");
        String return_msg = (String)result.get("return_msg");
        if(!StrKit.isBlank(return_code) && "SUCCESS".equals(return_code)) {
            String result_code = (String)result.get("result_code");
            if(!StrKit.isBlank(result_code) && "SUCCESS".equals(result_code)) {
                String prepay_id = (String)result.get("prepay_id");
                HashMap packageParams = new HashMap();
                packageParams.put("appId", appid);
                packageParams.put("timeStamp", System.currentTimeMillis() / 1000L + "");
                packageParams.put("nonceStr", System.currentTimeMillis() + "");
                packageParams.put("package", "prepay_id=" + prepay_id);
                packageParams.put("signType", "MD5");
                String packageSign = PaymentKit.createSign(packageParams, paternerKey);
                packageParams.put("paySign", packageSign);
                String jsonStr = JsonUtils.toJson(packageParams);
                this.setAttr("json", jsonStr);
                System.out.println(jsonStr);
                this.render("/jsp/pay.jsp");
            } else {
                this.renderText(return_msg);
            }
        } else {
            this.renderText(return_msg);
        }
    }

    public void pay_notify() {
        String xmlMsg = HttpKit.readData(this.getRequest());
        System.out.println("支付通知=" + xmlMsg);
        Map params = PaymentKit.xmlToMap(xmlMsg);
        String result_code = (String)params.get("result_code");
        String totalFee = (String)params.get("total_fee");
        String orderId = (String)params.get("out_trade_no");
        String transId = (String)params.get("transaction_id");
        String timeEnd = (String)params.get("time_end");
        if(PaymentKit.verifyNotify(params, paternerKey) && "SUCCESS".equals(result_code)) {
            System.out.println("更新订单信息");
            HashMap xml = new HashMap();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            this.renderText(PaymentKit.toXml(xml));
        } else {
            this.renderText("");
        }
    }
}
