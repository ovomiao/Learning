package moe.maonaing.network.java.okhttp.base;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostUtils {

    // text 表示我发送的请求的媒体类型是一个文本
    // x-markdown 这是服务端规定的, 点击 base url 就可以看到 github 的 api 说明
    private static final MediaType MARKDOWN_MEDIA_TYPE = MediaType.parse("text/x-markdown;charset=utf-8");

    private static final String BASE_URL = "https://api.github.com/markdown/raw";

    public static void postMarkdown(moe.maonaing.network.java.okhttp.base.Callback callback) {
        String markdown = "# 这是一级标题\n **加粗**";

        OkHttpClient client = new OkHttpClient();

        // 创建请求体
        // RequestBody create(@Nullable MediaType contentType, 多类型 content)
        // MediaType 就是指定请求头中的 contentType
        // content 时 POST 请求体中的内容
        //      * 可以是字符串,字节数组,文件
        RequestBody body = RequestBody.create(MARKDOWN_MEDIA_TYPE, markdown);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 响应成功
                if (response.isSuccessful()) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    String html = response.body().string();
                    handler.post(() -> {
                        callback.onResponse(call, html);
                    });
                }
            }
        });
    }

} 