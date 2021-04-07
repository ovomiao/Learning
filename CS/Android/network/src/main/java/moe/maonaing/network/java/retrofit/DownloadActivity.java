package moe.maonaing.network.java.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import moe.maonaing.network.databinding.ActivityDownloadBinding;

public class DownloadActivity extends AppCompatActivity {

    private ActivityDownloadBinding mBinding;

    private static final String URL = "iBiliPlayer-bilibili.apk";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDownloadBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initView();
    }

    private void initView() {
        mBinding.btnRetrofitDownload.setOnClickListener((view) -> {

        });
    }
}
