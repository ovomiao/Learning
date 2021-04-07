package moe.maonaing.network.java.retrofit;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import moe.maonaing.network.java.retrofit.api.FileDownloadService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * * 【下载进入监听说明】
 *      * 可以自定义 OkHttp 拦截器实现
 *      * 也可以在读取流那里实现（参考 Kotlin 版）
 *
 * 【Java 版这里使用自定义 OkHttp 拦截器实现】
 *      * 1. 先自定义 ProgressResponseBody 响应体，获取每次读取的字节数
 *      * 2. 自定义拦截器
 *      * 3. 在 OkHttp 客户端添加拦截器
 */
public final class MyDownloadManager {

    private static final String BASE_URL = "https://dl.hdslb.com/mobile/latest/android_b/";

    private volatile static MyDownloadManager mInstance = null;

    private static final int CONNECT_TIMEOUT = 1;
    private static final int READ_TIMEOUT = 1;
    private static final int WRITE_TIMEOUT = 1;


    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    private OkHttpClient mClient;
    private Retrofit mRetrofit;
    private ProgressInterceptor mInterceptor;

    private ProgressListener mListener;

    private void initOkHttp() {

        mInterceptor = new ProgressInterceptor((url, bytesRead, contentLength, isDone) -> {
            if (mListener != null) {
                mListener.onProgress(url, bytesRead, contentLength, isDone);
            }
        });

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
                .readTimeout(READ_TIMEOUT, TIME_UNIT)
                .writeTimeout(WRITE_TIMEOUT, TIME_UNIT)
                .addInterceptor(mInterceptor);

        mClient = builder.build();

    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mClient)
                .build();
    }

    public <T> T create(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

    @NotNull
    public static MyDownloadManager get() {
        return Holder.INSTANCE;
    }

    public MyDownloadManager addProgressListener(ProgressListener listener) {
        mListener = listener;
        return this;
    }

    public MyDownloadManager setUrl(String url) {
        FileDownloadService fileDownloadService = mRetrofit.create(FileDownloadService.class);
        return this;
    }

    private static final class Holder {
        private static final MyDownloadManager INSTANCE = new MyDownloadManager();
    }

    private MyDownloadManager() {
        initOkHttp();
        initRetrofit();
    }

} 