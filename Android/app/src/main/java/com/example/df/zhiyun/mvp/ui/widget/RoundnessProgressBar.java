package com.example.df.zhiyun.mvp.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.df.zhiyun.R;

public class RoundnessProgressBar extends View {
    private Context mContext;
    private int cycleBgStrokeWidth;
    private int progressStrokeWidth;
    private boolean isProgressText;
    private boolean isShadow;
    private boolean showCircle;
    private int centerTextColor;
    private int centerTextSize;
    private int cycleBgColor;
    private int progressColor;

    private Paint progressPaint;
    private Paint cycleBgPaint;
    private Paint centerTextPaint;
    /**
     * 圆心x坐标
     */
    private float centerX;
    /**
     * 圆心y坐标
     */
    private float centerY;
    /**
     * 圆的半径
     */
    private float radius;

    private float mMaxValue = 100;

    /**
     * 进度
     */
    private float mProgress;

    /**
     * 当前角度
     */
    private float currentAngle;

    /**
     * 是否有动画
     */
    private boolean withAnimation;
    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 500;
    /**
     * 扇形所在矩形
     */
    private RectF rectF = new RectF();
    private ValueAnimator valueAnimator;

    public RoundnessProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public RoundnessProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundnessProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        getAttr(attrs);  // 获取控件属性，
        initPaint(); // 初始化圆圈画笔
        initTextPaint(); // 初始化文字画笔
    }

    /**
     * 获取控件属性（命名空间）
     */
    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RoundnessProgressBar);
        cycleBgStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.RoundnessProgressBar_circleWidth, 10);
        progressStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.RoundnessProgressBar_progressWidth, 10);
        showCircle = typedArray.getBoolean(R.styleable.RoundnessProgressBar_showCircle, true);
        isProgressText = typedArray.getBoolean(R.styleable.RoundnessProgressBar_showValue, false);
        isShadow = typedArray.getBoolean(R.styleable.RoundnessProgressBar_showShadow, false);
        withAnimation = typedArray.getBoolean(R.styleable.RoundnessProgressBar_withAnimation, true);
        centerTextColor = typedArray.getColor(R.styleable.RoundnessProgressBar_valueColor, mContext.getResources().getColor(R.color.colorAccent));
        centerTextSize = typedArray.getDimensionPixelOffset(R.styleable.RoundnessProgressBar_valueSize, 16);
        cycleBgColor = typedArray.getColor(R.styleable.RoundnessProgressBar_circleColor, mContext.getResources().getColor(R.color.colorPrimary));
        progressColor = typedArray.getColor(R.styleable.RoundnessProgressBar_progressColor, mContext.getResources().getColor(R.color.colorAccent));

        typedArray.recycle();
    }

    /**
     * 初始化圆圈画笔
     */
    private void initPaint() {
        progressPaint = getPaint(progressStrokeWidth, progressColor);
        if(isShadow){
            setLayerType(View.LAYER_TYPE_SOFTWARE, progressPaint);
        }

        cycleBgPaint = getPaint(cycleBgStrokeWidth, cycleBgColor);
    }

    private Paint getPaint(int width, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(width);
        // Paint.Style.FILL:填充内部  Paint.Style.FILL_AND_STROKE:填充内部和描边Paint.Style.STROKE :描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setAntiAlias(true); // 扛锯齿
        paint.setStrokeCap(Paint.Cap.ROUND); // 两端是方型
        return paint;
    }

    /**
     * 初始化文字画笔
     */

    private void initTextPaint() {
        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setColor(centerTextColor);
        centerTextPaint.setTextAlign(Paint.Align.CENTER);  // 设置文字位置在中间
        centerTextPaint.setTextSize(centerTextSize);  // 设置文字大小
        centerTextPaint.setAntiAlias(true);
    }

    /**
     * 初始化动画
     */

    private void initAanimator() {
        if(valueAnimator != null){
            valueAnimator.end();
        }

        valueAnimator = ValueAnimator.ofFloat(0, mProgress);
        valueAnimator.setDuration(duration);
        valueAnimator.setStartDelay(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float)valueAnimator.getAnimatedValue();
                currentAngle = 360*value/mMaxValue;
                invalidate();
            }
        });
    }

    /**
     * view发生改变的时候调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(w, h) / 2 - Math.max(cycleBgStrokeWidth, progressStrokeWidth); // 两数中的最小值 / 2 - 两数中的最大值

        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    SweepGradient mSweepGradient;
    /**
     * 画
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, cycleBgPaint);

//        if(mSweepGradient == null){
//            mSweepGradient = new SweepGradient(rectF.width()/2, rectF.height()/2
//                    , new int[] {Color.parseColor("#fabc89"),Color.parseColor("#ff7504")}
//                    ,null);
//            Matrix matrix = new Matrix();
//            matrix.setRotate(-90, rectF.width()/2, rectF.height()/2);
//            mSweepGradient.setLocalMatrix(matrix);
//            progressPaint.setShader(mSweepGradient);
//
//            progressPaint.setMaskFilter(new BlurMaskFilter(10,BlurMaskFilter.Blur.SOLID));
//        }
        if(withAnimation){
            canvas.drawArc(rectF, -90, currentAngle, false, progressPaint);
        }else{
            canvas.drawArc(rectF, -90, 360*mProgress/mMaxValue, false, progressPaint);
        }

        if (isProgressText) {
            Paint.FontMetrics fontMetrics = centerTextPaint.getFontMetrics();
            int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
            canvas.drawText((int) mProgress + "%", rectF.centerX(), baseline, centerTextPaint);
        }
    }

    public void setMaxValue(float value){
        this.mMaxValue = value;
    }

    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(float progress) {
        this.mProgress = progress;
        initAanimator();
        startAnimator();
    }

    public void setProgressNoAnimator(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    /**
     * 开始动画
     */
    public void startAnimator() {
        if(valueAnimator != null && withAnimation){
            valueAnimator.start();
        }
    }

    public void stopAnimator() {
        if(valueAnimator != null ){
            valueAnimator.end();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimator();
    }
}
