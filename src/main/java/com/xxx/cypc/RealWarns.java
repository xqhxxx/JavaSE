package com.xxx.cypc;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

class RealWarns {


//    public static void main(String[] args) throws IOException {
//
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("测试1")//数据
//                .append("\n")//换行
//                .append("[吐]");//表情
//
//        sendDingDing(stringBuffer);
//    }

    /**
     * 发送钉钉
     * @param stringBuffer 钉钉机器人消息内容
     */
    public static void sendDingDing(StringBuffer stringBuffer) {

        try {
            //钉钉机器人地址（配置机器人的webhook）
            String baseUrl = "https://oapi.dingtalk.com/robot/send?access_token=";
            String token = "1c89276b6228fd2fdf9e3af896bd355aeb429e03faf65f906c9b50e4b932d34f";
            String secret = "SECae67eb3f667373ddc19e1453d5dbdbbec116cbb5c515add1e3db693e65a189d7";
            long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String dingUrl = baseUrl + token + "&timestamp=" + timestamp + "&sign=" + URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");

            //是否通知所有人
            boolean isAtAll = true;
            //通知具体人的手机号码列表
            List<String> mobileList = Lists.newArrayList();
//            mobileList.add("+86-18173145870");
            mobileList.add("+86-17671261492");

            String content = stringBuffer.toString();

            //组装请求内容
            String reqStr = packageBuildReqData(content, isAtAll, mobileList);
            //推送消息（http请求）
            String result = HttpUtil.doPosthttp(dingUrl, reqStr);
            System.out.println("result == " + result);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 组装请求报文
     * @param content
     * @return
     */
    private static String packageBuildReqData(String content, boolean isAtAll, List<String> mobileList) {
        //消息内容
        Map<String, String> contentMap = Maps.newHashMap();
        contentMap.put("content", content);
        //通知人
        Map<String, Object> atMap = Maps.newHashMap();
        //1.是否通知所有人
        atMap.put("isAtAll", isAtAll);
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", mobileList);

        Map<String, Object> reqMap = Maps.newHashMap();
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);

        return JSON.toJSONString(reqMap);
    }

}

