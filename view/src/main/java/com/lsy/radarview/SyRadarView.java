package com.lsy.radarview;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liusiyu.taloner on 2018/3/30.
 */

public class SyRadarView extends View {
    private int sideCount;  //边数
    private int layerCount; //层数
    private float angle = (float) (Math.PI * 2 / sideCount);    //每条边的圆心角
    private Point center;  //中心位置坐标
    private float radius;   //圆半径

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
        center.set(w/2, h/2);
    }

    private void drawNgon() {
        Path path = new Path();
        float size = radius / layerCount;  //每层间距；
    }
}
