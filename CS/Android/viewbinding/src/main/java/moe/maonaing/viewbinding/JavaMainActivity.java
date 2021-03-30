package moe.maonaing.viewbinding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import moe.maonaing.viewbinding.databinding.ActivityJavaMainBinding;

public class JavaMainActivity extends AppCompatActivity {
    private ActivityJavaMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取 ViewBinding 对象
        mBinding = ActivityJavaMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot()); // 替换原来的 R.layout.xml

        // 访问控件
        mBinding.changeText.setOnClickListener((view) -> {
            mBinding.textView.setText("你好，世界！");
        });

        mBinding.toMainActivity.setOnClickListener((view) -> {
            startActivity(new Intent(JavaMainActivity.this, MainActivity.class));
            JavaMainActivity.this.finish();
        });
    }
}