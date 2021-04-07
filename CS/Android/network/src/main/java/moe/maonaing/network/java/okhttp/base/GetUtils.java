package moe.maonaing.network.java.okhttp.base;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 简单 GET 请求的演示
 */
public class GetUtils {
    private static final String URL = "https://www.bilibili.com/";
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void get(@NotNull Callback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(() -> {
                    callback.onFailure(call, e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 将响应体转化为 string
                ResponseBody body = response.body();
                if (body == null) {
                    mHandler.post(() -> {
                        callback.onFailure(call, null);
                    });
                    return;
                }
                String string = body.string();
                mHandler.post(() -> {
                    callback.onResponse(call, string);
                });
            }
        });
    }

} 