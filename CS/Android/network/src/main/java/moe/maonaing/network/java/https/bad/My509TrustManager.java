package moe.maonaing.network.java.https.bad;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 不推荐做法: 只实现 X509TrustManager 接口,
 * 但什么都不做. 所以是默认信任所有的证书,其实这种做法使用 Https 和没使用
 * Https 是一样的.
 */
public class My509TrustManager implements X509TrustManager {

    // 校验客户端
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    // 校验服务端,一般只需要校验服务端
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}