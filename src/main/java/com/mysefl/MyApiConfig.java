package com.mysefl;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.demo.WeixinApiController;
import com.jfinal.weixin.demo.WeixinMsgController;
import com.jfinal.weixin.demo.WeixinPayController;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

/**
 * Created by zhuhui on 16-4-14.
 */
public class MyApiConfig extends JFinalConfig{
    public MyApiConfig() {
    }

    public void loadProp(String pro, String dev) {
        try {
            PropKit.use(pro);
        } catch (Exception var4) {
            PropKit.use(dev);
        }

    }

    public void configConstant(Constants me) {
        this.loadProp("a_little_config_pro.properties", "a_little_config.properties");
        me.setDevMode(PropKit.getBoolean("devMode", Boolean.valueOf(false)).booleanValue());
        ApiConfigKit.setDevMode(me.getDevMode());
    }

    public void configRoute(Routes me) {
        me.add("/msg", WeixinMsgController.class);
        me.add("/api", WeixinApiController.class, "/api");
        me.add("/pay", WeixinPayController.class);
    }

    public void configPlugin(Plugins me) {
    }

    public void configInterceptor(Interceptors me) {
    }

    public void configHandler(Handlers me) {
    }

    public void afterJFinalStart() {
    }

    public static void main(String[] args) {
        JFinal.start("webapp", 80, "/", 5);
    }
}
