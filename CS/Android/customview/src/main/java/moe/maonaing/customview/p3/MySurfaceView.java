package moe.maonaing.customview.p3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

/**
 * SurfaceView 可以独立于 UI 线程绘制。
 *      1. 利用 SurfaceHolder 监听 SurfaceView 的创建
 *      2. 开启异步线程的 while 循环
 *      3. 通过 SurfaceHolder 获取 Canvas 对象，开始绘制
 *
 * SurfaceView 的应用场景：
 *      * 小游戏
 *      * 视频播放、预览、拍照时相机预览
 *      * 特别复杂的动画
 */
public class MySurfaceView extends SurfaceView implements Runnable {

    private Thread mThread;
    private volatile boolean isRunning;

    private Paint mPaint;

    private int maxRadius;
    private int minRadius;
    private float mRadius;

    private int flags;

    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initPaint();
    }

    private void init(Context context) {
        isRunning = true;
        flags = 1;

        final SurfaceHolder holder = getHolder();
        // 监听 SurfaceView 的创建
        holder.addCallback(new SurfaceHolder.Callback() {
            // SurfaceView 创建完毕
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                mThread = new Thread(MySurfaceView.this);
                mThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            // SurfaceView 被销毁
            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                isRunning = false;
            }
        });
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxRadius = Math.min(w, h) - 100;
        minRadius = maxRadius / 10;
        mRadius = minRadius;
    }

    @Override
    public void run() {
        while (isRunning) {
            drawSelf();
        }
    }

    private void drawSelf() {
        // 1. 获取 Canvas
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            // 2. 执行绘制
            drawView(canvas);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            //3. 释放 Canvas
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawView(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(getWidth()/2, getHeight()/2, mRadius, mPaint);

        if (mRadius >= maxRadius) {
            flags = -1;
        } else if (mRadius <= minRadius){
            flags = 1;
        }
        mRadius += flags * 2;
    }
}