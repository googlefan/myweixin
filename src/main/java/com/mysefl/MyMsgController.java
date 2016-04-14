package com.mysefl;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutCustomMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

/**
 * Created by zhuhui on 16-4-14.
 */
public class MyMsgController extends MsgControllerAdapter {
    static Log logger = Log.getLog(MyMsgController.class);
    private static final String helpStr = "\t发送 help 可获得帮助，发送\"视频\" 可获取视频教程，发送 \"美女\" 可看美女，发送 music 可听音乐 ，发送新闻可看JFinal新版本消息。公众号功能持续完善中";

    public MyMsgController() {
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

    protected void processInTextMsg(InTextMsg inTextMsg) {
        OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
        this.render(outCustomMsg);
    }

    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
        OutCustomMsg outCustomMsg = new OutCustomMsg(inVoiceMsg);
        this.render(outCustomMsg);
    }

    protected void processInVideoMsg(InVideoMsg inVideoMsg) {
        OutCustomMsg outCustomMsg = new OutCustomMsg(inVideoMsg);
        this.render(outCustomMsg);
    }

    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
        OutCustomMsg outCustomMsg = new OutCustomMsg(inShortVideoMsg);
        this.render(outCustomMsg);
    }

    protected void processInLocationMsg(InLocationMsg inLocationMsg) {
        OutCustomMsg outCustomMsg = new OutCustomMsg(inLocationMsg);
        this.render(outCustomMsg);
    }

    protected void processInLinkMsg(InLinkMsg inLinkMsg) {
        OutCustomMsg outCustomMsg = new OutCustomMsg(inLinkMsg);
        this.render(outCustomMsg);
    }

    protected void processInCustomEvent(InCustomEvent inCustomEvent) {
        logger.debug("测试方法：processInCustomEvent()");
        this.renderNull();
    }

    protected void processInImageMsg(InImageMsg inImageMsg) {
        OutCustomMsg outCustomMsg = new OutCustomMsg(inImageMsg);
        this.render(outCustomMsg);
    }

    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        if("subscribe".equals(inFollowEvent.getEvent())) {
            logger.debug("关注：" + inFollowEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
            outMsg.setContent("这是Jfinal-weixin测试服务</br>\r\n感谢您的关注");
            this.render(outMsg);
        }

        if("unsubscribe".equals(inFollowEvent.getEvent())) {
            logger.debug("取消关注：" + inFollowEvent.getFromUserName());
        }

    }

    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
        if("subscribe".equals(inQrCodeEvent.getEvent())) {
            logger.debug("扫码未关注：" + inQrCodeEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
            outMsg.setContent("感谢您的关注，二维码内容：" + inQrCodeEvent.getEventKey());
            this.render(outMsg);
        }

        if("SCAN".equals(inQrCodeEvent.getEvent())) {
            logger.debug("扫码已关注：" + inQrCodeEvent.getFromUserName());
        }

    }

    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
        logger.debug("发送地理位置事件：" + inLocationEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
        outMsg.setContent("地理位置是：" + inLocationEvent.getLatitude());
        this.render(outMsg);
    }

    protected void processInMassEvent(InMassEvent inMassEvent) {
        logger.debug("测试方法：processInMassEvent()");
        this.renderNull();
    }

    protected void processInMenuEvent(InMenuEvent inMenuEvent) {
        logger.debug("菜单事件：" + inMenuEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
        outMsg.setContent("菜单事件内容是：" + inMenuEvent.getEventKey());
        this.render(outMsg);
    }

    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
        logger.debug("语音识别事件：" + inSpeechRecognitionResults.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inSpeechRecognitionResults);
        outMsg.setContent("语音识别内容是：" + inSpeechRecognitionResults.getRecognition());
        this.render(outMsg);
    }

    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
        logger.debug("测试方法：processInTemplateMsgEvent()");
        this.renderNull();
    }
}
