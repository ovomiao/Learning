package moe.maonaing.customview.p2.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import moe.maonaing.customview.R;

/**
 * 自定义 View 的通用逻辑
 */
public class MyView extends View {
    private static final String INSTANCE = "instance";
    private static final String TEXT = "text";


    private String mString = "默认值";

    private String text = "Hello";

    private Paint mPaint;
    private Paint mTextPaint;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 有一些默认属性就需要重新此构造，然后在两个参数的构造中调用这个构造
     * 并传入默认属性. 可以参考系统控件例如 Button、TextView 等等
     */
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyView);

        // 这里一定要设置默认值，因为用户不一定会在 xml 中声明用到的属性

        // 获取 Int 值
        int iInt = typedArray.getInteger(R.styleable.MyView_testInteger,-1);
        // 获取枚举值, 使用 getInt 获取枚举值
        int enumVal = typedArray.getInt(R.styleable.MyView_testEnum, 0);

        /**
         * 这种方式的一个可能出现的坑
         *      * 默认值被覆盖
         */
        // 这里的 mString 本来是有默认值的，假设 xml 中没有使用 testString 属性，那么这里的
        // getString() 将会返回 null，使用这个 null 覆盖 mString 的默认值。
        mString = typedArray.getString(R.styleable.MyView_testString);
        // 对于这种情况，可以使用 if 判断；（不推荐，太多了）

        // 解决默认值被覆盖的问题
        int count = typedArray.getIndexCount(); // 返回用户在 XML 中所以以定义过的属性数组元素总数
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            // 只要用户声明了就去获取，不会覆盖成员变量（推荐，毕竟低风险）
            switch (index) {
                case R.styleable.MyView_testInteger:
                    break;
                case R.styleable.MyView_testEnum:
                    break;
                case R.styleable.MyView_testDimension:
                    break;
                case R.styleable.MyView_testString:
                    mString = typedArray.getString(index);
                    break;
            }
        }

        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(100f);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 测量: View（非 ViewGroup）的测量步骤实际上就是这个逻辑，
     * 所变动的只有 View 自身内容有变
     * @param widthMeasureSpec  由父控件传入
     * @param heightMeasureSpec 由父控件传入
     *   MeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int width = 0;

        // 用户设置了一个固定值：直接设置给 View 即可
        if (widthMode == MeasureSpec.EXACTLY) {
            // 此时 Spec 中的 Size 就是 View 实际需要的 Size
            width = widthSize;
        }
        // 用户没有设置固定大小，即值为 wrap_content 或 match_parent
        // 这是需要自己测量自己的大小
        else {
            // 如果要支持 padding ，那么当前 View 所需要的总宽度
            // 是 width + paddingLeft + paddingRight
            int needWidth = measureWidth() + getPaddingStart() + getPaddingEnd();

            // 如果是 AT_MOST 那么 View 最多只能是父 View 的宽度
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(needWidth, widthSize);
            }
            else {
                width = needWidth; //测量多大就是多大，因为父 View 不限制尺寸
            }
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }
        else {
            int needHeight = measureHeight() + getPaddingEnd() + getPaddingStart();
            if (height == MeasureSpec.EXACTLY) {
                height = Math.min(needHeight, heightSize);
            }
            else {
                height = needHeight;
            }
        }

        // 设置最终测量的结果
        setMeasuredDimension(width, height);
    }

    // 绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 当前 View 的最终尺寸，实际上就是测量中的 width、height 值
        int width = getWidth();
        int height = getHeight();

        int radius = Math.min(height, width) / 2;
        // 圆心应该减去
        radius = (int) (radius - mPaint.getStrokeWidth() / 2);
        // 在屏幕中心画一个圆
        canvas.drawCircle(width/2, height/2, radius, mPaint);

        // 绘制文字
        canvas.drawText(text, width/2, height/2, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            text = "你好";
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 实现了 View 的保存和恢复，之后还不一定能让系统完成 View 状态的保存和恢复功能
     * 还需要给 View 设置一个 ID。因为系统在存储 View 状态数据是通过 View ID 作为
     * Key 来存储的。而恢复也是根据 View ID 作为 Key 来获取数据恢复到 View 的
     *
     * @return
     */
    // 保存横竖屏切换可能丢失的数据
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable date = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putString(TEXT, text);
        // 这里保存 super.onSaveInstanceState() 主要用于
        // 在继承了父 View 时，保存它们已保存的内容，不然父 view 的
        // 保存的状态会失效
        bundle.putParcelable(INSTANCE, date);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // 如果时 Bundle 类型，则是我们自己的 View 保存的数据，需要执行恢复
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            // 先恢复父 View 的数据
            Parcelable parcelable = bundle.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            // 恢复自己的 View 数据
            text = bundle.getString(TEXT);
            // 结束
            return;
        }
        super.onRestoreInstanceState(state);
    }

    private int measureHeight() {
        return 0;
    }

    /**
     * 测量自己内容展示所需要的宽度
     *      * 例如：一个 Text 控件就和文字内容有关
     */
    private int measureWidth() {
        return 0;
    }
}