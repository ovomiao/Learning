package moe.maonaing.network.java.https.bad;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class JavaHttpsUtils {
    private static final String URL = "";

    public void get() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(URL);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        ///////////////////////////////////////////
        // 处理 Https 问题
        ///////////////////////////////////////////
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] managers = {
                new My509TrustManager()
        };
        sslContext.init(null, managers, new SecureRandom());
        SSLSocketFactory factory = sslContext.getSocketFactory();
        connection.setSSLSocketFactory(factory);
        // 处理完成

        connection.setReadTimeout(1000 * 10);
        connection.setConnectTimeout(1000 * 10);
        connection.setRequestMethod("GET");
        connection.connect();
    }

} 