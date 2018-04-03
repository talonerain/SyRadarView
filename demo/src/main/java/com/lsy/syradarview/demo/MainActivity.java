package com.lsy.syradarview.demo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lsy.radarview.SyRadarView;

public class MainActivity extends Activity {
    SyRadarView radarView;
    TextView btn_website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radarView = (SyRadarView) this.findViewById(R.id.radarView);
        btn_website = (TextView) findViewById(R.id.btn_website);
        btn_website.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (TextUtils.isEmpty(btn_website.getText())) {
                    return false;
                }
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("Lable", btn_website.getText());
                manager.setPrimaryClip(data);
                Toast.makeText(MainActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
