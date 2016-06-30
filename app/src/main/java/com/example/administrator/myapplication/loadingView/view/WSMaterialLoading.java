package com.example.administrator.myapplication.loadingView.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by jms on 2016/6/29.
 */
public class WSMaterialLoading extends View {
    private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();

    private Paint mPaint;
    private Context mContext;

    private int circleCenterX, circleCenterY;
    private int circleRadius;

    private float mEndDegrees;
    private float mOriginEndDegrees;
    private float mOriginStartDegrees;

    private float mOriginRotationIncrement;
    private float mRotationIncrement;

    private float mStartAngle;
    private float mSweepAngle;
    private float mValueAnimator;

    private float mRotationCount;
    private float mGroupRotation;

    private final static float RADIUS_RATIO = 2 / 3f;

    private static final int DEGREE_360 = 360;
    private static final int NUM_POINTS = 5;

    private static final float MIN_SWEEP_ANGLE = 0.1f;
    private static final float MAX_SWEEP_ANGLE = 0.8f * DEGREE_360;

    private static final float FULL_GROUP_ROTATION = 3.0f * DEGREE_360;
    private static final float MAX_ROTATION_INCREMENT = 0.25f * DEGREE_360;

    private static final float END_TRIM_DURATION_OFFSET = 1.0f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;
    private static final float COLOR_START_DELAY_OFFSET = 0.8f;

    private static final float MATERIAL_RADIUS_RATIO = 0.2f;

    private int[] mColors;
    private int mColorIndex;
    private int mCurrentColor;


    private static final int[] DEFAULT_COLORS = new int[]{
            Color.RED, Color.GREEN, Color.BLUE
    };

    public WSMaterialLoading(Context context) {
        this(context, null);
    }

    public WSMaterialLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WSMaterialLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        mSweepAngle = MIN_SWEEP_ANGLE;
        mColors = DEFAULT_COLORS;
        setColorIndex(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //处理 wrap_content问题
        int defaultDimension = dip2px(100);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultDimension, defaultDimension);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultDimension, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defaultDimension);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        circleCenterX = w / 2;
        circleCenterY = h / 2;

        //处理padding情况
        circleRadius = (int) (Math.min(Math.min(circleCenterY - getPaddingTop(), circleCenterY - getPaddingBottom()),
                Math.min(circleCenterX - getPaddingLeft(), circleCenterX - getPaddingRight())) * RADIUS_RATIO);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mCurrentColor);
        mPaint.setStrokeWidth(circleRadius * MATERIAL_RADIUS_RATIO);

        canvas.rotate(mGroupRotation,circleCenterX,circleCenterY);

        RectF rectF = new RectF(circleCenterX - circleRadius, circleCenterY - circleRadius, circleCenterX + circleRadius, circleCenterY + circleRadius);
        canvas.drawArc(rectF, mStartAngle, mSweepAngle, false, mPaint);

    }

    //开始动画
    public void startAnimator() {
        post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
                animator.setDuration(1300);
                animator.setInterpolator(new LinearInterpolator());
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mValueAnimator = (float) valueAnimator.getAnimatedValue();
                        updateRingColor(mValueAnimator);

                        if (mValueAnimator <= START_TRIM_DURATION_OFFSET) {
                            float startTrimProgress = mValueAnimator / START_TRIM_DURATION_OFFSET;
                            mStartAngle = mOriginStartDegrees + MAX_SWEEP_ANGLE * MATERIAL_INTERPOLATOR.getInterpolation(startTrimProgress);
                        }

                        if (mValueAnimator > START_TRIM_DURATION_OFFSET) {
                            float endTrimProgress = (mValueAnimator - START_TRIM_DURATION_OFFSET) / (END_TRIM_DURATION_OFFSET - START_TRIM_DURATION_OFFSET);
                            mEndDegrees = mOriginEndDegrees + MAX_SWEEP_ANGLE *  MATERIAL_INTERPOLATOR.getInterpolation(endTrimProgress);
                        }

                        if (Math.abs(mEndDegrees - mStartAngle) > MIN_SWEEP_ANGLE) {
                            mSweepAngle = mEndDegrees - mStartAngle;
                        }

                        mGroupRotation = ((FULL_GROUP_ROTATION / NUM_POINTS) * mValueAnimator) + (FULL_GROUP_ROTATION * (mRotationCount / NUM_POINTS));
                        mRotationIncrement = mOriginRotationIncrement + (MAX_ROTATION_INCREMENT * mValueAnimator);

                        postInvalidate();
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);

                        mOriginEndDegrees = mEndDegrees;
                        mOriginStartDegrees = mStartAngle;
                        mOriginRotationIncrement = mRotationIncrement;

                        goToNextColor();

                        mStartAngle = mEndDegrees;
                        mRotationCount = (mRotationCount + 1) % (NUM_POINTS);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        mRotationCount = 0;
                    }
                });
                animator.start();
            }
        });
    }

    public void setColorIndex(int index) {
        mColorIndex = index;
        mCurrentColor = mColors[mColorIndex];
    }

    private int getNextColor() {
        return mColors[getNextColorIndex()];
    }

    private int getNextColorIndex() {
        return (mColorIndex + 1) % (mColors.length);
    }

    private void goToNextColor() {
        setColorIndex(getNextColorIndex());
    }


    public void setPaintColor(int color) {
        mPaint.setColor(color);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getStartingColor() {
        return mColors[mColorIndex];
    }

    private void updateRingColor(float interpolatedTime) {
        if (interpolatedTime > COLOR_START_DELAY_OFFSET) {
            mCurrentColor = evaluateColorChange((interpolatedTime - COLOR_START_DELAY_OFFSET)
                    / (1.0f - COLOR_START_DELAY_OFFSET), getStartingColor(), getNextColor());
        }
    }

    private int evaluateColorChange(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24)
                | ((startR + (int) (fraction * (endR - startR))) << 16)
                | ((startG + (int) (fraction * (endG - startG))) << 8)
                | ((startB + (int) (fraction * (endB - startB))));
    }

}
