package com.lsy.radarview;

import android.content.Context;
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
    private String[] titles = {"李白","阿珂","孙悟空","赵云","兰陵王","娜可露露"};

    public SyRadarView(Context context) {
        super(context);
        init();
    }

    public SyRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SyRadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mCenter = new Point();
        mSideCount = 6;
        mLayerCount = 4;
        mAngle = (float) (Math.PI * 2 / mSideCount);
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
        Paint polygonPaint = new Paint();
        polygonPaint.setColor(Color.BLACK);
        polygonPaint.setAntiAlias(true);
        polygonPaint.setStyle(Paint.Style.STROKE);
        polygonPaint.setStrokeWidth(4f);
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
            canv.drawPath(path, polygonPaint);
        }
    }

    //绘制连接线
    private void drawConnect(Canvas canv) {
        Path path = new Path();
        Paint linePaint = new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2f);

        for(int i = 0; i < mSideCount; i++){
            path.reset();
            path.moveTo(mCenter.x, mCenter.y);
            float x = (float) (mCenter.x + mRadius * Math.sin(mAngle * i));
            float y = (float) (mCenter.y - mRadius * Math.cos(mAngle * i));
            path.lineTo(x, y);
            canv.drawPath(path, linePaint);
        }
    }

    private void drawText(Canvas canv) {
        Paint textPaint = new Paint();
        textPaint.setTextSize(32);
        textPaint.setColor(Color.RED);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < mSideCount; i++) {
            float x = (float) (mCenter.x + (mRadius + fontHeight / 2) * Math.sin(mAngle * i));
            float y = (float) (mCenter.y - (mRadius + fontHeight / 2) * Math.cos(mAngle * i));
            if (i == 0) {
                //上下顶点与中心点横坐标相同，需设置从中间绘制文字，默认是从左下角绘制
                textPaint.setTextAlign(Paint.Align.CENTER);
                canv.drawText(titles[i], x, y, textPaint);
            } else if((mSideCount % 2 == 0 && mSideCount / 2 == i)){
                //下顶点需要将文字向下微调
                textPaint.setTextAlign(Paint.Align.CENTER);
                canv.drawText(titles[i], x, y + fontHeight / 2, textPaint);
            } else if (mAngle * i >= 0 && mAngle * i <= Math.PI / 2) {
                //第1象限和第4象限文字靠右，从左下角绘制
                textPaint.setTextAlign(Paint.Align.LEFT);
                canv.drawText(titles[i], x, y, textPaint);
            } else if (mAngle * i >= 3 * Math.PI / 2 && mAngle * i <= Math.PI * 2) {
                //第2象限和第3象限文字靠左，从右下角绘制
                textPaint.setTextAlign(Paint.Align.RIGHT);
                canv.drawText(titles[i], x, y, textPaint);
            } else if (mAngle * i > Math.PI / 2 && mAngle * i <= Math.PI) {
                //第4象限，因为从左下绘制，所以需要将文字向下微调
                textPaint.setTextAlign(Paint.Align.LEFT);
                canv.drawText(titles[i], x, y + fontHeight / 2, textPaint);
            } else if (mAngle * i >= Math.PI && mAngle * i < 3 * Math.PI / 2) {
                //第3象限，因为从左下绘制，所以需要将文字向下微调
                textPaint.setTextAlign(Paint.Align.RIGHT);
                canv.drawText(titles[i], x, y + fontHeight / 2, textPaint);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNgon(canvas);
        drawConnect(canvas);
        drawText(canvas);
    }
}
