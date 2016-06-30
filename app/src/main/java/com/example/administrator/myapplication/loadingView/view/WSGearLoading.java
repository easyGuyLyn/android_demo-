package com.example.administrator.myapplication.loadingView.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by jms on 2016/6/29.
 */
public class WSGearLoading extends View {
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    private Paint mPaint;
    private Context mContext;
    private RectF rectF;
    private int circleCenterY;
    private int circleCenterX;
    private int circleRadius;
    private float mValueAnimator;

    private final static float RADIUS_RATIO = 2 / 3f;
    private static final float STROKE_RADIUS_RATIO = 0.2f;

    private static final int GEAR_COUNT = 4;
    private static final int NUM_POINTS = 3;
    private static final int MAX_ALPHA = 255;
    private static final int DEGREE_360 = 360;

    private static final float MIN_SWIPE_DEGREE = 0.1f;
    private static final float MAX_SWIPE_DEGREES = 0.17f * DEGREE_360;
    private static final float FULL_GROUP_ROTATION = 3.0f * DEGREE_360;
    private static final float MAX_ROTATION_INCREMENT = 0.25f * DEGREE_360;

    private static final float START_SCALE_DURATION_OFFSET = 0.3f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;
    private static final float END_TRIM_DURATION_OFFSET = 0.7f;
    private static final float END_SCALE_DURATION_OFFSET = 1.0f;

    private static final int DEFAULT_COLOR = Color.WHITE;

    private float mRotationCount;
    private float mGroupRotation;

    private float mScale;
    private float mEndDegrees;
    private float mStartDegrees;
    private float mSwipeDegrees;
    private float mRotationIncrement;
    private float mOriginEndDegrees;
    private float mOriginStartDegrees;
    private float mOriginRotationIncrement;


    public WSGearLoading(Context context) {
        this(context, null);
    }

    public WSGearLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WSGearLoading(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPaint.setColor(DEFAULT_COLOR);

        resetOriginals();
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

        canvas.rotate(mGroupRotation, circleCenterX, circleCenterY);

        mPaint.setAlpha((int) (MAX_ALPHA * mScale));
        mPaint.setStrokeWidth(circleRadius * STROKE_RADIUS_RATIO * mScale);

        rectF = new RectF(circleCenterX - circleRadius, circleCenterY - circleRadius, circleCenterX + circleRadius, circleCenterY + circleRadius);

        rectF.inset(rectF.width() * (1.0f - mScale) / 2.0f, rectF.width() * (1.0f - mScale) / 2.0f);

        for (int i = 0; i < GEAR_COUNT; i++) {
            canvas.drawArc(rectF, mStartDegrees + DEGREE_360 / GEAR_COUNT * i, mSwipeDegrees, false, mPaint);
        }

    }

    //开始动画
    public void startAnimator() {
        post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
                animator.setDuration(1500);
                animator.setInterpolator(new LinearInterpolator());
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mValueAnimator = (float) valueAnimator.getAnimatedValue();

                        if (mValueAnimator <= START_SCALE_DURATION_OFFSET) {
                            float startScaleProgress = (mValueAnimator) / START_SCALE_DURATION_OFFSET;
                            mScale = DECELERATE_INTERPOLATOR.getInterpolation(startScaleProgress);
                        }

                        // Moving the start trim only occurs between 20% to 50% of a
                        // single ring animation
                        if (mValueAnimator <= START_TRIM_DURATION_OFFSET && mValueAnimator > START_SCALE_DURATION_OFFSET) {
                            float startTrimProgress = (mValueAnimator - START_SCALE_DURATION_OFFSET) / (START_TRIM_DURATION_OFFSET - START_SCALE_DURATION_OFFSET);
                            mStartDegrees = mOriginStartDegrees + MAX_SWIPE_DEGREES * LINEAR_INTERPOLATOR.getInterpolation(startTrimProgress);
                        }

                        // Moving the end trim starts between 50% to 80% of a single ring
                        // animation completes
                        if (mValueAnimator <= END_TRIM_DURATION_OFFSET && mValueAnimator > START_TRIM_DURATION_OFFSET) {
                            float endTrimProgress = (mValueAnimator - START_TRIM_DURATION_OFFSET) / (END_TRIM_DURATION_OFFSET - START_TRIM_DURATION_OFFSET);
                            mEndDegrees = mOriginEndDegrees + MAX_SWIPE_DEGREES * LINEAR_INTERPOLATOR.getInterpolation(endTrimProgress);
                        }

                        // Scaling down the end size starts after 80% of a single ring
                        // animation completes
                        if (mValueAnimator > END_TRIM_DURATION_OFFSET) {
                            float endScaleProgress = (mValueAnimator - END_TRIM_DURATION_OFFSET) / (END_SCALE_DURATION_OFFSET - END_TRIM_DURATION_OFFSET);
                            mScale = 1.0f - ACCELERATE_INTERPOLATOR.getInterpolation(endScaleProgress);
                        }

                        if (Math.abs(mEndDegrees - mStartDegrees) > MIN_SWIPE_DEGREE) {
                            mSwipeDegrees = mEndDegrees - mStartDegrees;
                        }

                        if (mValueAnimator <= END_TRIM_DURATION_OFFSET && mValueAnimator > START_SCALE_DURATION_OFFSET) {
                            float rotateProgress = (mValueAnimator - START_SCALE_DURATION_OFFSET) / (END_TRIM_DURATION_OFFSET - START_SCALE_DURATION_OFFSET);
                            mGroupRotation = ((FULL_GROUP_ROTATION / NUM_POINTS) * rotateProgress) + (FULL_GROUP_ROTATION * (mRotationCount / NUM_POINTS));
                            mRotationIncrement = mOriginRotationIncrement + (MAX_ROTATION_INCREMENT * rotateProgress);
                        }

                        postInvalidate();
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        super.onAnimationRepeat(animation);
                        storeOriginals();

                        mStartDegrees = mEndDegrees;
                        mRotationCount = (mRotationCount + 1) % NUM_POINTS;
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

    private void resetOriginals() {
        mOriginEndDegrees = 0;
        mOriginStartDegrees = 0;
        mOriginRotationIncrement = 0;

        mEndDegrees = 0;
        mStartDegrees = 0;
        mRotationIncrement = 0;

        mSwipeDegrees = MIN_SWIPE_DEGREE;
    }

    private void storeOriginals() {
        mOriginEndDegrees = mEndDegrees;
        mOriginStartDegrees = mStartDegrees;
        mOriginRotationIncrement = mRotationIncrement;
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

}
