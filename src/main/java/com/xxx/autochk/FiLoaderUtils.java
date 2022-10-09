package com.xxx.autochk;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public final class FiLoaderUtils {
    private static final String username = "cypc_ds1";
    private static final String password = "muTFqh6XFckjwBQBAY2K=4WM1BXjaHSmldG1HFX9AckFJxjydZ#r1IGSQ@lDLr!7wr=#xv@354H7biiWNNWGKvDPCWLhKh$RFE96tn2w2hQI1@qlxtA7SF23PApQ3lwY0AyMDIxMDUxNA==8bCCMVa5cHwXfNQvtyt=RB0mQFX4wH8u3b@3*xGJ$fxfa7BELvIg7UTJRepgOhBVQ8M8QmK4CV3N*nkCIA*s7BB4ntG=mEpFtZgTOUr7g9*@A2icHSUal*Ou6Gt5o";


    public static void main(String[] args) throws Exception {

        getLoaderOptList();
    }



    public static JSONArray getLoaderOptList() throws Exception{
//        String auth_url = "https://10.191.179.39:20009/cas/login?service=https://10.191.179.39:20026/Loader/LoaderServer/240/loader/v1/job/all?timeStamp="+System.currentTimeMillis();

        String auth_url="https://10.191.179.39:28443/web/#!/app/cluster/service/detail/Kafka/Kafka/kafkaTopicTable";


        HttpGet getRequest = new HttpGet(auth_url);
        getRequest.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.114 Safari/537.36");
        HttpClient client = getHttpClient();
        HttpResponse response = client.execute(getRequest);
        String collect = getContent(response);
        Document doc = Jsoup.parse(collect);
        String ticket = doc.body().getElementsByTag("input").stream()
                .filter(e->e.attr("name").equals("lt")).findFirst().get().attr("value");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username",username));
        params.add(new BasicNameValuePair("password",password));
        params.add(new BasicNameValuePair("_eventId","submit"));
        params.add(new BasicNameValuePair("submit","Login"));
        params.add(new BasicNameValuePair("lt",ticket));
        UrlEncodedFormEntity entity =new UrlEncodedFormEntity(params,"utf-8");

        HttpPost postRequest = new HttpPost(auth_url);
        postRequest.setEntity(entity);
        response = client.execute(postRequest);
        final int status = response.getStatusLine().getStatusCode();
        final URI uri = getLocationURI(postRequest, response);

//        Header[] headers = response.getHeaders("Set-Cookie");
        if (status == HttpStatus.SC_TEMPORARY_REDIRECT) {
//            Arrays.stream(headers).forEach(e->postRequest.addHeader(e));
            response = client.execute(RequestBuilder.copy(postRequest).setUri(uri).build());
        } else {
            HttpGet get = new HttpGet(uri);
//            get.addHeader("Authorization", constructAuthentication(username,password1));
//            Arrays.stream(headers).forEach(e->postRequest.addHeader(e));
            get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.114 Safari/537.36");
            response = client.execute(get);
        }
        collect = getContent(response);
        JSONObject entries = JSONUtil.parseObj(collect);
        JSONArray all = entries.getJSONArray("all");
        JSONArray dbcenterGroup = new JSONArray();
        for(int i=0;i<all.size();i++){
            JSONObject group = (JSONObject) all.get(i);
            if (group.get("gname").equals("dbcenter")) {
                dbcenterGroup.add(group);
            }
        }
        if (dbcenterGroup == null) {
            return null;
        }


//        File file = new File("loader.json");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//        out.write(dbcenterGroup.toStringPretty());
//        out.close();
        return dbcenterGroup;
    }



    private static URI getLocationURI(final HttpRequest request, final HttpResponse response) throws ProtocolException {
        final Header locationHeader = response.getFirstHeader("location");
        if (locationHeader == null) {
            throw new ProtocolException("未返回重定向location首部");
        }
        final String location = locationHeader.getValue();
        final URI requestURI = URI.create(request.getRequestLine().getUri());
        return requestURI.resolve(location);
    }

    private static HttpClient getHttpClient(){
        return sslClient();
//        return getHttpClient("TLS");
    }

    private static HttpClient getHttpClient(String userTLSVersion) {
        ThreadSafeClientConnManager ccm = new ThreadSafeClientConnManager();
        ccm.setMaxTotal(100);
        HttpClient httpclient = WebClientDevWrapper.wrapClient(new DefaultHttpClient(ccm), userTLSVersion);
        return httpclient;
    }

    private static HttpClient sslClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, new DefaultHostnameVerifier());
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
            return closeableHttpClient;
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getContent(HttpResponse response) throws Exception{
        InputStream content = response.getEntity().getContent();
        InputStreamReader isr = new InputStreamReader(content, Charset.forName("utf-8"));
        BufferedReader br = new BufferedReader(isr);
        String collect = br.lines().collect(Collectors.joining("\n"));
        content.close();
        return collect;
    }



}
