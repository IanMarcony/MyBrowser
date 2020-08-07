package com.ianmarcony.mybrowser;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText urlSite;
    private Button btnSearch;
    private WebView myBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlSite = findViewById(R.id.urlSite);
        btnSearch = findViewById(R.id.btnSearch);
        myBrowser =(WebView) findViewById(R.id.myBrowser);

        WebSettings webSettings = myBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myBrowser.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return false;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!urlSite.getText().toString().equals("")){

                    String url = urlSite.getText().toString();

                    myBrowser.loadUrl(url);

                }else{
                    Toast.makeText(getApplicationContext(),"Preencha o campo com alguma url de site",Toast.LENGTH_SHORT);
                }
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && myBrowser.canGoBack()){
            myBrowser.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}