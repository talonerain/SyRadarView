package com.lsy.syradarview.demo;

import android.app.Activity;
import android.os.Bundle;

import com.lsy.radarview.SyRadarView;

public class MainActivity extends Activity {
    SyRadarView radarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radarView = (SyRadarView) this.findViewById(R.id.radarView);
    }
}
