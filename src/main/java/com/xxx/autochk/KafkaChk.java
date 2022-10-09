package com.xxx.autochk;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KafkaChk {
    public static void main(String[] args) {
        KafkaChk kafkaChk = new KafkaChk();
        KafkaChk.GsKafkaState gsKafkaState = kafkaChk.gsChk();
    }

    private static final String userName = "cypc_ds1";
    private static final String password = "Cypc@20210514";

    public GsKafkaState gsChk() {

        String[] topics = "gzb_magus_points,sx_magus_points,xjb_magus_points,xld_magus_points".split(",");
        GsKafkaState state = new GsKafkaState();
        JSONArray topicList = chk("https://10.191.179.39:28443/web");

//        topicList.stream().forEach(System.out::println);

//        for(String topic:topics){
//
//            Stream<JSONObject> stream = topicList.stream();
//            JSONObject topicObj = stream.filter(obj -> obj.get("name").equals(topic)).findFirst().get();
//            if(null == topicObj){
//                continue;
//            }
//
//            int kcV = Double.valueOf((String) topicObj.get("topicOutBytes")) > 0 ? 1:0;
//            int kpV = Double.valueOf((String) topicObj.get("topicInBytes")) > 0 ? 1:0;
//
//            KafkaState ks = chk(prop.getGsFiUrl(),topic,prop.getKcName(),prop.getKpName());
//            if (topic.startsWith(prop.getGzbTopicPrefix())){
//                state.setGzbKcValue(kcV);
//                state.setGzbKpValue(kpV);
//            } else if(topic.startsWith(prop.getSxTopicPrefix())){
//                state.setSxKcValue(kcV);
//                state.setSxKpValue(kpV);
//            } else if(topic.startsWith(prop.getXjbTopicPrefix())){
//                state.setXjbKcValue(kcV);
//                state.setXjbKpValue(kpV);
//            } else if(topic.startsWith(prop.getXldTopicPrefix())){
//                state.setXldKcValue(kcV);
//                state.setXldKpValue(kpV);
//            }
//        }

        state.setChkTime(System.currentTimeMillis());
        return state;
    }

 /*   public KafkaState chk(HealthChkProp prop) {
        return chk(prop.getFiUrl(),prop.getTopicName(),prop.getKcName(),prop.getKpName());
    }*/

    @Data
    public class KafkaState {
        int kcValue;
        int kpValue;
        long chkTime;
    }

    @Data
    public class GsKafkaState {
        int gzbKcValue;
        int gzbKpValue;
        int sxKpValue;
        int sxKcValue;
        int xjbKpValue;
        int xjbKcValue;
        int xldKpValue;
        int xldKcValue;
        long chkTime;
    }

    private KafkaState chk(final String fiUrl, final String topicName, final String kcName, final String kpName) {
        BasicAuthAccess authAccess = new BasicAuthAccess();
        KafkaState state = new KafkaState();
        try {
            String url = String.format("%s/api/v2/clusters/1/services/Kafka/private/topics?topicName=&_=%d", fiUrl, System.currentTimeMillis());
            HttpClient httpClient = authAccess.loginAndAccess(url, userName, password, "");
            HttpGet get = new HttpGet(url);
            get.addHeader("Content-Type", "application/json;charset=UTF-8");
            HttpResponse response = httpClient.execute(get);
            System.out.println(response);
            InputStream content = response.getEntity().getContent();
            InputStreamReader isr = new InputStreamReader(content);
            BufferedReader br = new BufferedReader(isr);
            String collect = br.lines().collect(Collectors.joining("\n"));

            JSONObject jsonObject = JSON.parseObject(collect);
            JSONArray topicList = jsonObject.getJSONObject("resObj").getJSONArray("topicList");
            Stream<JSONObject> stream = topicList.stream().map(x -> (JSONObject) x);
            JSONObject topic = stream.filter(obj -> obj.get("name").equals(topicName)).findFirst().get();
            double kcV = Double.valueOf((String) topic.get(kcName));
            double kpV = Double.valueOf((String) topic.get(kpName));

            state.setKcValue(kcV > 0 ? 1 : 0);
            state.setKpValue(kpV > 0 ? 1 : 0);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
        }
        state.setChkTime(System.currentTimeMillis());
        return state;
    }

    private JSONArray chk(final String fiUrl) {
        BasicAuthAccess authAccess = new BasicAuthAccess();
        try {
            String url = String.format("%s/api/v2/clusters/1/services/Kafka/private/topics?topicName=&_=%d", fiUrl, System.currentTimeMillis());
            HttpClient httpClient = authAccess.loginAndAccess(url, userName, password, "TLSv1.1");//TLSv1.1
            HttpGet get = new HttpGet(url);
            get.addHeader("Content-Type", "application/json;charset=UTF-8");
            HttpResponse response = httpClient.execute(get);
            InputStream content = response.getEntity().getContent();
            InputStreamReader isr = new InputStreamReader(content);
            BufferedReader br = new BufferedReader(isr);
            String collect = br.lines().collect(Collectors.joining("\n"));

            JSONObject jsonObject = JSON.parseObject(collect);
            JSONArray topicList = jsonObject.getJSONObject("resObj").getJSONArray("topicList");
            return topicList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
