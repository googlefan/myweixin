package com.mysefl;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;

/**
 * Created by zhuhui on 16-4-14.
 */
public class MyController  extends ApiController {
    public MyController() {
    }

    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", Boolean.valueOf(false)).booleanValue());
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

    public void getMenu() {
        ApiResult apiResult = MenuApi.getMenu();
        if(apiResult.isSucceed()) {
            this.renderText(apiResult.getJson());
        } else {
            this.renderText(apiResult.getErrorMsg());
        }

    }

    public void createMenu() {
        String str = "{\n    \"button\": [\n        {\n            \"name\": \"进入理财\",\n            \"url\": \"http://m.bajie8.com/bajie/enter\",\n            \"type\": \"view\"\n        },\n        {\n            \"name\": \"安全保障\",\n            \"key\": \"112\",\n\t    \"type\": \"click\"\n        },\n        {\n\t    \"name\": \"使用帮助\",\n\t    \"url\": \"http://m.bajie8.com/footer/cjwt\",\n\t    \"type\": \"view\"\n        }\n    ]\n}";
        ApiResult apiResult = MenuApi.createMenu(str);
        if(apiResult.isSucceed()) {
            this.renderText(apiResult.getJson());
        } else {
            this.renderText(apiResult.getErrorMsg());
        }

    }

    public void getFollowers() {
        ApiResult apiResult = UserApi.getFollows();
        this.renderText(apiResult.getJson());
    }

    public void getUserInfo() {
        ApiResult apiResult = UserApi.getUserInfo("ohbweuNYB_heu_buiBWZtwgi4xzU");
        this.renderText(apiResult.getJson());
    }

    public void sendMsg() {
        String str = " {\n           \"touser\":\"ohbweuNYB_heu_buiBWZtwgi4xzU\",\n           \"template_id\":\"9SIa8ph1403NEM3qk3z9-go-p4kBMeh-HGepQZVdA7w\",\n           \"url\":\"http://www.sina.com\",\n           \"topcolor\":\"#FF0000\",\n           \"data\":{\n                   \"first\": {\n                       \"value\":\"恭喜你购买成功！\",\n                       \"color\":\"#173177\"\n                   },\n                   \"keyword1\":{\n                       \"value\":\"去哪儿网发的酒店红包（1个）\",\n                       \"color\":\"#173177\"\n                   },\n                   \"keyword2\":{\n                       \"value\":\"1元\",\n                       \"color\":\"#173177\"\n                   },\n                   \"remark\":{\n                       \"value\":\"欢迎再次购买！\",\n                       \"color\":\"#173177\"\n                   }\n           }\n       }";
        ApiResult apiResult = TemplateMsgApi.send(str);
        this.renderText(apiResult.getJson());
    }

    public void getQrcode() {
        String str = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
        ApiResult apiResult = QrcodeApi.create(str);
        this.renderText(apiResult.getJson());
    }

    public void getShorturl() {
        String str = "{\"action\":\"long2short\",\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}";
        ApiResult apiResult = ShorturlApi.getShorturl(str);
        this.renderText(apiResult.getJson());
    }

    public void getRecord() {
        String str = "{\n    \"endtime\" : 987654321,\n    \"pageindex\" : 1,\n    \"pagesize\" : 10,\n    \"starttime\" : 123456789\n }";
        ApiResult apiResult = CustomServiceApi.getRecord(str);
        this.renderText(apiResult.getJson());
    }

    public void getCallbackIp() {
        ApiResult apiResult = CallbackIpApi.getCallbackIp();
        this.renderText(apiResult.getJson());
    }
}
