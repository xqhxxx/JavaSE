package com.xxx.autochk;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class WebClientDevWrapper
{
    private static final String PROTOCOL_NAME = "https";
    
    private static final int PORT = 443;
    
    private static final String DEFAULT_SSL_VERSION = "TLS";
    
    public static HttpClient wrapClient(HttpClient base, String userTLSVersion)
    {
        SSLContext sslContext = null;
        try
        {
            sslContext = SSLContext.getInstance(userTLSVersion);
        }
        catch (NoSuchAlgorithmException e1)
        {
            e1.printStackTrace();
            return null;
        }
        
        if (sslContext == null)
        {
            return null;
        }
        
        X509TrustManager trustManager = new X509TrustManager()
        {
            public X509Certificate[] getAcceptedIssuers()
            {
                return null;
            }
            
            public void checkClientTrusted(X509Certificate[] ax509certificate, String s)
                throws CertificateException
            {
            }
            
            public void checkServerTrusted(X509Certificate[] ax509certificate, String s)
                throws CertificateException
            {
            }
        };
        try
        {
            sslContext.init(null, new TrustManager[] {trustManager}, new SecureRandom());
        }
        catch (KeyManagementException e)
        {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = null;
        if ((userTLSVersion == null) || (userTLSVersion.isEmpty()) || (userTLSVersion.equals(DEFAULT_SSL_VERSION)))
        {
            sslSocketFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        }
        else
        {
            sslSocketFactory = new BigdataSslSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER, userTLSVersion);
        }
        ClientConnectionManager ccm = base.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme(PROTOCOL_NAME, PORT, sslSocketFactory));
        return new DefaultHttpClient(ccm, base.getParams());
    }
}
