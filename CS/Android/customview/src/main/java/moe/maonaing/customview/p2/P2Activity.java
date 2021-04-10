package moe.maonaing.customview.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import moe.maonaing.customview.R;
import moe.maonaing.customview.p2.view.RoundProgressBar;

/**
 * 【自定义 View 的步骤】
 * * 1. 自定义属性的声明与获取
 * * 2. 测量
 * * 3. 绘制
 * * 4. View 的状态保存与恢复（主要是处理 Activity/Fragment 重建的问题）
 * <p>
 * 测量阶段的三种模式：
 * * EXACTLY        用户在设定固定值，直接获取并设置给 View 即可
 * * AT_POST       用户设置了 warp_content
 * * 这种情况需要自己测量自己的值，测量完成之后和父 View 比较
 * 不能超过父 View 的尺寸
 * * UNSPECIFIED  不限定子 View 的大小，子控件要多大就可以设置多大
 * * 例如：ListView、RecyclerView、ScrollView 等
 * * 测量中重要的类 MeasureSpec
 * * 设置 View 的尺寸
 * * setMeasuredDimension(w,h) 在 View 测量完成之后，需要调用此方法去设置 View 的最终尺寸
 * 即 View 最终的绘制尺寸。
 * * requestLayout()
 * * 请求重新测量和布局
 */
public class P2Activity extends AppCompatActivity {
    private RoundProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2);

        mProgressBar = findViewById(R.id.myView);

        mProgressBar.setOnClickListener((view) -> {
            if (mProgressBar.getProgress() >= 100) {
                mProgressBar.setProgress(0);
            } else {
                ObjectAnimator animator = ObjectAnimator.ofInt(mProgressBar, "progress", 0, 100);
                animator.setDuration(5000);
                animator.start();
            }
        });
    }

}