package com.lsy.radarview;

import android.content.Context;
import android.graphics.Canvas;
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
    private float mAngle = (float) (Math.PI * 2 / mSideCount);    //每条边的圆心角
    private Point mCenter;  //中心位置坐标
    private float mRadius;   //圆半径

    public SyRadarView(Context context) {
        super(context);
    }

    public SyRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SyRadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenter.set(w/2, h/2);
        mRadius = Math.min(w, h) / 2 * 0.8f; //保证圆形在给定范围内即可；
    }

    private void drawNgon() {
        Path path = new Path();
        float size = mRadius / mLayerCount;  //每层间距；
        //逐层绘制，中心点为第一层
        for(int i = 1; i < mLayerCount; i++){
            //当前层半径
            float r = size * i;
            //逐点绘制，以位于中心点上方的点为第一个点
            for(int j = 0; j < mSideCount; j++) {
                if (j == 0) {
                    path.moveTo(mCenter.x, mCenter.y - r);
                } else {
                    float x = (float) (mCenter.x + Math.sin(mAngle * j) * r);
                    float y = (float) (mCenter.y - Math.cos(mAngle * j) * r);
                }
            }
        }
        //path.moveTo();
    }
}
