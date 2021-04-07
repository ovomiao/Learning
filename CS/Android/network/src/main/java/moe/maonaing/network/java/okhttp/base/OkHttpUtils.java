package moe.maonaing.network.java.okhttp.base;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 一个请求需要提供的内容
 * 一个完整 request 的组成
 *      * url
 *      * method
 *      * headers 请求头
 *      * body  请求体
 *
 * 一个完成的 Response 的组成
 *      * code -> Http 状态码,例如: 200,404
 *      * headers -> 响应头
 *          * 例如: content-length 可以返回响应体的大小
 *      * body -> 响应体
 *
 * OkHttp 相关的对象创建都是基于构建者模式的
 */
public class OkHttpUtils {
    private static final String TAG = "OkHttpUtils";
    
    public static final String URL = "https://www.bilibili.com/";

    /**
     * 获取最基本的 OkHttpClient 对象
     */
    private OkHttpClient getBaseOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        return client;
    }

    private Request create() {
        // 使用 Builder 构建请求体
        Request.Builder builder = new Request.Builder();
        builder.url(URL);
        builder.get();  // 使用 GET 方法

        // 构建 request 对象
        return builder.build();
    }

    private Call getCall() {
        Call call = getBaseOkHttpClient().newCall(create());
        return call;
    }

    // 发起请求
    // execute() 方法是同步方法，当前调用者线程会阻塞在这里
    private void sendRequest1() throws IOException {
        // 返回响应体
        Response execute = getCall().execute();
    }

    // 发起一步请求
    //      即将请求加入请求队列,当前代码继续向下执行,不会被阻塞在这里
    private void sendRequest2() {
        Log.i(TAG, "sendRequest2: 将异步请求放入队列");
        getCall().enqueue(new Callback() {
            // 请求失败时回调
            @Override
            public void onFailure(Call call, IOException e) {
                
            }

            // 请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求是否成功
                boolean successful = response.isSuccessful();
                int code = response.code(); // 响应码
                Headers headers = response.headers(); // 响应头
                ResponseBody body = response.body(); // 响应体
                // 例如获取 响应头
                headers.get("Content-Type");
            }
        });
        Log.i(TAG, "sendRequest2: 我没有阻塞哦");
    }

}
