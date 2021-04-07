package moe.maonaing.network.java.okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.lang.ref.WeakReference;

import moe.maonaing.network.databinding.ActivityOkHttpBinding;
import moe.maonaing.network.java.okhttp.base.OkHttpUtils;
import moe.maonaing.network.java.okhttp.base.PostUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    private ActivityOkHttpBinding mBinding;
    private H mH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOkHttpBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mH = new H(this);
        //get();

        PostUtils.postMarkdown(new moe.maonaing.network.java.okhttp.base.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, String string) {
                mBinding.okHttpText.setText(string);
            }
        });
    }

    private void get() {
        // 创建 OkHttpClient 对象
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(OkHttpUtils.URL)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            // 注意:这里还是工作线程
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 将响应体读入内存, 响应体超过 1MB 不要使用此方法
                // 因为这个方法会被所有内容全部读入内存
                String html = response.body().string();
                // 更新 UI
                Message message = Message.obtain();
                message.obj = html;
                mH.sendMessage(message);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mH != null) {
            mH.removeCallbacksAndMessages(null);
            mH = null;
        }
    }

    private final class H extends Handler {

        private WeakReference<OkHttpActivity> mWeakReference;

        private H(OkHttpActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            OkHttpActivity okHttpActivity = mWeakReference.get();
            if (okHttpActivity == null) {
                return;
            }
            String html = (String) msg.obj;
            okHttpActivity.mBinding.okHttpText.setText(html);
        }
    }

}