package com.xxx.autochk;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;


import java.io.*;

public class BasicAuthAccess {

    public HttpClient loginAndAccess(String webUrl, String userName, String password, String userTLSVersion)
            throws Exception {
        HttpClient httpClient = getHttpClient(userTLSVersion);
        String authentication = constructAuthentication(userName, password);

        return httpClient;
    }

    private HttpClient getHttpClient(String userTLSVersion) {

        ThreadSafeClientConnManager ccm = new ThreadSafeClientConnManager();
        ccm.setMaxTotal(100);

        HttpClient httpclient = WebClientDevWrapper.wrapClient(new DefaultHttpClient(ccm), userTLSVersion);
        return httpclient;
    }

    private String constructAuthentication(String userName, String password)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("Basic");
        sb.append(" ");

        String userNamePasswordToken = userName + ":" + password;
        try {
            byte[] token64 = Base64.encode(userNamePasswordToken.getBytes("UTF-8"));

            String token = new String(token64);
            sb.append(token);
        } catch (UnsupportedEncodingException e) {
        }
        return sb.toString();
    }

    private HttpResponse firstAccessResp(String webUrl, String userName, String password, String authentication,
                                         HttpClient httpClient)
            throws Exception {
        HttpGet httpGet = new HttpGet(webUrl + "api/v2/session/status");
        httpGet.addHeader("Authorization", authentication);
        BufferedReader bufferedReader = null;

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            String stateLine = response.getStatusLine().toString();

            InputStream inputStream = response.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String lineContent = "";
            lineContent = bufferedReader.readLine();
//            log.info("Response content is {} ", lineContent);

            if (!(stateLine.equals("HTTP/1.1 200 "))) {
            }


            while (lineContent != null) {

                lineContent = bufferedReader.readLine();
            }
        } catch (UnsupportedEncodingException e) {
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                }
            }
        }
        return response;
    }
}
