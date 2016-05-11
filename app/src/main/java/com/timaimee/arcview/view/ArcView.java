package com.timaimee.arcview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.timaimee.arcview.R;

/**
 * Created by TimAimee on 2016/5/10.
 */
public class ArcView extends View {
    private Paint mPaintBehind, mPaintFront, mPaintCircle, mPaintText;
    private float mArcWidth = 15, mCircleRadius = 15, mCircleArcWidth=5,mTextSize = 35, maxValueWidth = 15;
    private float mBehindArcWidth = mArcWidth, mFrontArcWidth = mArcWidth;
    private int mFrontColor = Color.GREEN, mBehindColor = Color.GRAY, mCircleColor = Color.GREEN, mTextColor = mCircleColor;
    private float mStartAngle = 90f;
    private float mSweepAngle = 360f;
    private int mWidth, mHeight, mCenterX, mCenterY;
    private float mProgress = 0.1f;
    private RectF mArcRectF;
    private Rect mTextRect;
    public final static int CAP_ROUND = 0, CAP_SQUARE = 1, CAP_BUTT = 2;
    public final static int STYLE_FILL = 0,STYLE_STROKE = 1,  STYLE_FILL_AND_STROKE = 2;

    private int capModle=CAP_ROUND,styleModle=STYLE_STROKE;

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProperty(context, attrs);
        initPaint();
        mTextRect = new Rect();
    }


    private void initProperty(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mProgress = typeArray.getFloat(R.styleable.ArcView_progress, mProgress);
        mArcWidth = typeArray.getDimension(R.styleable.ArcView_arcWidth, mArcWidth);

        mStartAngle = typeArray.getFloat(R.styleable.ArcView_startAngle, mStartAngle);
        setStartAngle(mStartAngle);

        mFrontArcWidth = typeArray.getDimension(R.styleable.ArcView_frontArcWidth, mFrontArcWidth);
        mFrontColor = typeArray.getColor(R.styleable.ArcView_frontColor, mFrontColor);

        mBehindArcWidth = typeArray.getDimension(R.styleable.ArcView_behindArcWidth, mBehindArcWidth);
        mBehindColor = typeArray.getColor(R.styleable.ArcView_behindColor, mBehindColor);

        mTextSize = typeArray.getDimension(R.styleable.ArcView_textSize, mTextSize);
        mTextColor = typeArray.getColor(R.styleable.ArcView_textColor, mTextColor);

        mCircleRadius = typeArray.getDimension(R.styleable.ArcView_circleRadius, mCircleRadius);
        mCircleColor = typeArray.getColor(R.styleable.ArcView_circleColor, mCircleColor);
        mCircleArcWidth= typeArray.getDimension(R.styleable.ArcView_circleRadius, mCircleArcWidth);

        capModle=typeArray.getInteger(R.styleable.ArcView_arcCapModel, capModle);

        styleModle=typeArray.getInteger(R.styleable.ArcView_arcCircleStyle,styleModle);
        typeArray.recycle();

    }

    private void initPaint() {
        mPaintBehind = new Paint();
        mPaintBehind.setStrokeWidth(mBehindArcWidth);
        mPaintBehind.setColor(mBehindColor);
        mPaintBehind.setStyle(Paint.Style.STROKE);

        mPaintFront = new Paint();
        mPaintFront.setStrokeWidth(mFrontArcWidth);
        mPaintFront.setColor(mFrontColor);
        mPaintFront.setStyle(Paint.Style.STROKE);


        mPaintCircle = new Paint();
        mPaintCircle.setColor(mCircleColor);
        mPaintCircle.setStrokeWidth(mCircleArcWidth);

        mPaintText = new Paint();
        mPaintText.setStrokeWidth(mArcWidth);
        mPaintText.setColor(mTextColor);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setTextSize(mTextSize);

        setArcCapModel(capModle);
        setCircleStyle(styleModle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        maxValueWidth = mArcWidth > mCircleRadius ? mArcWidth : mCircleRadius;
        mArcRectF = new RectF(maxValueWidth, maxValueWidth, (float) mWidth - maxValueWidth, (float) mHeight - maxValueWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw the behind arc
        double pSweepAngle = mSweepAngle * mProgress;

        canvas.drawArc(mArcRectF, mStartAngle, mSweepAngle, false, mPaintBehind);

        //draw the front arc
        canvas.drawArc(mArcRectF, mStartAngle, (float) pSweepAngle, false, mPaintFront);

        //draw the circle
        double pCircle[] = getCirclePosition(mStartAngle + pSweepAngle);
        canvas.drawCircle((float) pCircle[0], (float) pCircle[1], mCircleRadius, mPaintCircle);

        //draw the center text
        int progressInt = (int) (mProgress * 100);
        String text = progressInt + "%";
        mPaintText.getTextBounds(text, 0, text.length(), mTextRect);
        int pTextWidth = mTextRect.right - mTextRect.left;
        canvas.drawText(text, mCenterX - pTextWidth / 2, mCenterY, mPaintText);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float xValue = event.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //根据X轴左右滑动的比例来改变进度显示
                setProgress(xValue / (mWidth - maxValueWidth * 2));
                break;
            case MotionEvent.ACTION_MOVE:
                setProgress(xValue / (mWidth - maxValueWidth * 2));
                break;
            case MotionEvent.ACTION_UP:
                setProgress(xValue / (mWidth - maxValueWidth * 2));
                break;
        }
        return true;
    }

    private double[] getCirclePosition(double arcAngle) {
        double[] circle = new double[2];
        //X点
        circle[0] = mCenterX + Math.cos(Math.toRadians(arcAngle)) * (mCenterX - maxValueWidth);
        //Y点
        circle[1] = mCenterY + Math.sin(Math.toRadians(arcAngle)) * (mCenterY - maxValueWidth);
        return circle;
    }

    /**
     * progress的范围在[0,1]
     */
    public void setProgress(float progress) {
        if (progress <= 0) {
            progress = 0;
        }
        if (progress >= 1) {
            progress = 1;
        }
        this.mProgress = progress;
        invalidate();
    }

    public float getProgress() {
        return this.mProgress;
    }

    /**
     * mstartAngle的范围在[90-180]
     */
    public void setStartAngle(float startAngle) {
        if (startAngle < 90 || startAngle > 180) {
            startAngle = 90;
        }
        this.mStartAngle = startAngle;
        this.mSweepAngle = 360 - (mStartAngle - 90) * 2;
        invalidate();
    }

    public void setArcWidth(float arcWidth) {
        this.mArcWidth = arcWidth;
        mPaintBehind.setStrokeWidth(mArcWidth);
        mPaintFront.setStrokeWidth(mArcWidth);
        mPaintText.setStrokeWidth(mArcWidth);
        invalidate();
    }

    public void setArcCapModel(int model) {
        Paint.Cap capModel = Paint.Cap.ROUND;
        switch (model) {
            case CAP_ROUND:
                capModel = Paint.Cap.ROUND;
                break;
            case CAP_SQUARE:
                capModel = Paint.Cap.SQUARE;
                break;
            case CAP_BUTT:
                capModel = Paint.Cap.BUTT;
                break;
        }
        mPaintCircle.setStrokeCap(capModel);
        mPaintBehind.setStrokeCap(capModel);
        mPaintFront.setStrokeCap(capModel);
    }

    public void setBehindArcWidth(float behindWidth) {
        this.mBehindArcWidth = behindWidth;
        mPaintBehind.setStrokeWidth(mBehindArcWidth);
        invalidate();
    }

    public void setBehindColor(int behindColor) {
        mPaintBehind.setColor(behindColor);
        invalidate();
    }

    public void setFrontArcWidth(float frontArcWidth) {
        this.mFrontArcWidth = frontArcWidth;
        mPaintFront.setStrokeWidth(mFrontArcWidth);
        invalidate();
    }

    public void setFrontColor(int frontColor) {
        mPaintFront.setColor(frontColor);
        invalidate();
    }

    public void setTextSize(float textSize) {
        mPaintText.setTextSize(textSize);
        invalidate();
    }

    public void setTextColor(int textColor) {
        mPaintFront.setColor(textColor);
        invalidate();
    }

    public void setCircleColor(int circleColor) {
        mPaintCircle.setColor(circleColor);
        invalidate();
    }

    public void setCircleRadius(float circleRadius) {
        this.mCircleRadius = circleRadius;
        invalidate();
    }

    public void setCircleArcWidth(Float circleArcWidth){
        this.mCircleArcWidth=circleArcWidth;
        mPaintCircle.setStrokeWidth(mCircleArcWidth);
    }
    public void setCircleStyle(int style) {
        Paint.Style paintStyle = Paint.Style.FILL;
        switch (style) {
            case STYLE_FILL:
                paintStyle = Paint.Style.FILL;
                break;
            case STYLE_STROKE:
                paintStyle = Paint.Style.STROKE;
                break;
            case STYLE_FILL_AND_STROKE:
                paintStyle = Paint.Style.FILL_AND_STROKE;
                break;
        }
        mPaintCircle.setStyle(paintStyle);
        invalidate();
    }

}
