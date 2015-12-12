package com.example.yms.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText writeURL;
    Button moveBtn, pBtn;
    WebView webV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        writeURL = (EditText) findViewById(R.id.inputURL);
        moveBtn = (Button) findViewById(R.id.movBtn);
        pBtn = (Button) findViewById(R.id.preBtn);
        webV = (WebView) findViewById(R.id.webView);

        webV.setWebViewClient(new CookWebViewClient());


        WebSettings webSet = webV.getSettings();
        webSet.setBuiltInZoomControls(true);
    }

    public void onClick(View v) {
        String http = writeURL.getText().toString();


        switch (v.getId()) {
            case R.id.movBtn:
                if (!http.startsWith("http://"))
                    http = "http://" + http;


                writeURL.setText(http);
                webV.loadUrl(writeURL.getText().toString());
                break;
            case R.id.preBtn:
                webV.goBack();
                break;
        }
    }

    public class CookWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

    }

}
