package com.bhtc.huajuan.push.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.bhtc.huajuan.push.R;

public class ProtocolActivity extends BaseActivity {

    private WebView webView;
    private RelativeLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);

        webView = (WebView) findViewById(R.id.webview);
        back = (RelativeLayout) findViewById(R.id.back);
        webView.loadUrl("file:///android_asset/protocol.html");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
