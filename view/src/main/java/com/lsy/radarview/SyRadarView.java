package com.lsy.radarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liusiyu.taloner on 2018/3/30.
 */

public class SyRadarView extends View {
    private int mSideCount;  //边数
    private int mLayerCount; //层数
    private float mAngle;    //每条边的圆心角
    private Point mCenter;  //中心位置坐标
    private float mRadius;   //圆半径
    private String[] mTexts;    //显示文字
    private Double[] mPercents; //所占比重

    private Paint mNgonPaint; //多边形画笔
    private Paint mLinePaint;   //连线画笔
    private Paint mTextPaint;   //文字画笔
    private Paint mShadowPaint; //阴影区域画笔

    public SyRadarView(Context context) {
        super(context);
        init();
    }

    public SyRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(attrs);
    }

    public SyRadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttrs(attrs);
    }

    public void setTextList(String[] strs) {
        this.mTexts = strs;
        mSideCount = strs.length;
        mAngle = (float) (Math.PI * 2 / mSideCount);
    }

    public void setPercentList(Double[] percents) {
        this.mPercents = percents;
    }

    public void setLayerCount(int count) {
        this.mLayerCount = count > 0 ? count : 4;
    }

    public void setNgonPaint(Paint paint) {
        if (paint != null) {
            this.mNgonPaint = paint;
            //确保为描边样式
            mNgonPaint.setStyle(Paint.Style.STROKE);
        }
    }

    public void setLinePaint(Paint paint) {
        if (paint != null) {
            this.mLinePaint = paint;
            mLinePaint.setStyle(Paint.Style.STROKE);
        }
    }

    public void setTextPaint(Paint paint) {
        if (paint != null) {
            this.mTextPaint = paint;
        }
    }

    public void setShadowPaint(Paint paint) {
        if (paint != null) {
            this.mShadowPaint = paint;
            //确保为填充样式
            mShadowPaint.setStyle(Paint.Style.FILL);
        }
    }

    public void setNgonColor(int color) {
        this.mNgonPaint.setColor(color);
    }

    public void setNgonSize(float size) {
        this.mNgonPaint.setStrokeWidth(size);
    }

    public void setLineColor(int color) {
        this.mLinePaint.setColor(color);
    }

    public void setLineSize(float size) {
        this.mLinePaint.setStrokeWidth(size);
    }

    public void setTextColor(int color) {
        this.mTextPaint.setColor(color);
    }

    public void setTextSize(float size) {
        this.mTextPaint.setTextSize(size);
    }

    public void setShadowColor(int color) {
        this.mShadowPaint.setColor(color);
    }

    public void setShadowAlpha(int alpha) {
        this.mShadowPaint.setAlpha(alpha);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray style = getContext().obtainStyledAttributes(attrs, R.styleable.style);
        int ngonColor = style.getColor(R.styleable.style_ngonColor, Color.BLACK);
        float ngonWidth = style.getFloat(R.styleable.style_ngonSize, 4f);
        int lineColor = style.getColor(R.styleable.style_connLineColor, Color.GRAY);
        float lineWidth = style.getFloat(R.styleable.style_connLineSize, 2f);
        int shadowColor = style.getColor(R.styleable.style_shadowColor, Color.GRAY);
        int shadowAlpha = style.getInt(R.styleable.style_shadowAlpha, 150);
        int textColor = style.getColor(R.styleable.style_textColor, Color.RED);
        float textSize = style.getInt(R.styleable.style_textSize, 30);
        style.recycle();    //回收实例

        setNgonColor(ngonColor);
        setNgonSize(ngonWidth);
        setLineColor(lineColor);
        setLineSize(lineWidth);
        setShadowColor(shadowColor);
        setShadowAlpha(shadowAlpha);
        setTextSize(textSize);
        setTextColor(textColor);
    }

    private void init() {
        mCenter = new Point();
        mLayerCount = 4;
        initPaint();
    }

    private void initPaint() {
        mNgonPaint = new Paint();
        mNgonPaint.setAntiAlias(true);
        mNgonPaint.setStyle(Paint.Style.STROKE);  //描边

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();

        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);    //填充
        mShadowPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenter.set(w/2, h/2);
        mRadius = Math.min(w, h) / 2 * 0.8f; //保证圆形在给定范围内即可；
    }

    //多边形
    private void drawNgon(Canvas canv) {
        Path path = new Path();
        float size = mRadius / mLayerCount;  //每层间距；
        //逐层绘制，中心点为第一层
        for(int i = 1; i <= mLayerCount; i++){
            //当前层半径
            float r = size * i;
            //逐点绘制，以位于中心点上方的点为第一个点
            for(int j = 0; j < mSideCount; j++) {
                if (j == 0) {
                    path.moveTo(mCenter.x, mCenter.y - r);
                } else {
                    //加上半径在水平方向的偏移即横坐标
                    float x = (float) (mCenter.x + Math.sin(mAngle * j) * r);
                    float y = (float) (mCenter.y - Math.cos(mAngle * j) * r);
                    //绘制连线
                    path.lineTo(x, y);
                }
            }
            path.close();
            canv.drawPath(path, mNgonPaint);
        }
    }

    //绘制连接线
    private void drawConnect(Canvas canv) {
        Path path = new Path();
        for(int i = 0; i < mSideCount; i++){
            path.reset();
            path.moveTo(mCenter.x, mCenter.y);
            float x = (float) (mCenter.x + mRadius * Math.sin(mAngle * i));
            float y = (float) (mCenter.y - mRadius * Math.cos(mAngle * i));
            path.lineTo(x, y);
            canv.drawPath(path, mLinePaint);
        }
    }

    //绘制文字
    private void drawText(Canvas canv) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < mSideCount; i++) {
            float x = (float) (mCenter.x + (mRadius + fontHeight / 2) * Math.sin(mAngle * i));
            float y = (float) (mCenter.y - (mRadius + fontHeight / 2) * Math.cos(mAngle * i));
            if (i == 0) {
                //上下顶点与中心点横坐标相同，需设置从中间绘制文字，默认是从左下角绘制
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                canv.drawText(mTexts[i], x, y, mTextPaint);
            } else if((mSideCount % 2 == 0 && mSideCount / 2 == i)){
                //下顶点需要将文字向下微调
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                canv.drawText(mTexts[i], x, y + fontHeight / 2, mTextPaint);
            } else if (mAngle * i >= 0 && mAngle * i <= Math.PI / 2) {
                //第1象限和第4象限文字靠右，从左下角绘制
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                canv.drawText(mTexts[i], x, y, mTextPaint);
            } else if (mAngle * i >= 3 * Math.PI / 2 && mAngle * i <= Math.PI * 2) {
                //第2象限和第3象限文字靠左，从右下角绘制
                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                canv.drawText(mTexts[i], x, y, mTextPaint);
            } else if (mAngle * i >= Math.PI && mAngle * i < 3 * Math.PI / 2) {
                //第3象限，因为从左下绘制，所以需要将文字向下微调
                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                canv.drawText(mTexts[i], x, y + fontHeight / 2, mTextPaint);
            } else if (mAngle * i > Math.PI / 2 && mAngle * i <= Math.PI) {
                //第4象限，因为从左下绘制，所以需要将文字向下微调
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                canv.drawText(mTexts[i], x, y + fontHeight / 2, mTextPaint);
            }
        }
    }

    //绘制阴影
    private void drawShadow(Canvas canv) {
        Path path = new Path();
        float r = mRadius / mLayerCount;
        for (int i = 0; i < mSideCount; i++) {
            if (i == 0) {
                path.moveTo(mCenter.x, (float) (mCenter.y - r - (mRadius - r) * mPercents[i]));
            } else {
                float x = (float) (mCenter.x + Math.sin(mAngle * i) * (mPercents[i] * (mRadius - r) + r));
                float y = (float) (mCenter.y - Math.cos(mAngle * i) * (mPercents[i] * (mRadius - r) + r));
                path.lineTo(x, y);
            }
        }
        path.close();
        canv.drawPath(path, mShadowPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNgon(canvas);
        drawConnect(canvas);
        drawText(canvas);
        drawShadow(canvas);
    }
}
