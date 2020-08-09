package com.ianmarcony.mybrowser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText urlSite;
    private Button btnSearch;
    private WebView myBrowser;
    private ImageButton btnBack,btnForward;
    private TextView urlView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlSite = findViewById(R.id.urlSite);
        btnSearch = findViewById(R.id.btnSearch);
        myBrowser =(WebView) findViewById(R.id.myBrowser);
        btnBack= findViewById(R.id.btnBack);
        btnForward = findViewById(R.id.btnForward);
        urlView = findViewById(R.id.urlView);
        progressBar =findViewById(R.id.progressBar);


        urlSite.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    if(!urlSite.getText().toString().equals("")){
                        String url = !urlSite.getText().toString().contains("https://")?"https://"+urlSite.getText().toString():urlSite.getText().toString();

                        myBrowser.loadUrl(url);
                        urlSite.setText("");
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                    }else{
                        Toast.makeText(getApplicationContext(),"Preencha o campo com alguma URL de site",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });


        WebSettings webSettings = myBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myBrowser.setWebViewClient(new WebViewClient() {
                                       @Override
                                       public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                           view.loadUrl(url);
                                           return false;
                                       }

                                       @Override
                                       public void onPageStarted(WebView view, String url, Bitmap favicon) {

                                           super.onPageStarted(view, url, favicon);
                                           urlView.setText("Carregando...");
                                           progressBar.setVisibility(View.VISIBLE);
                                       }

                                       @Override
                                       public void onPageFinished(WebView view, String url) {

                                           super.onPageFinished(view, url);

                                           urlView.setText(url);
                                           progressBar.setVisibility(View.GONE);
                                       }



                                   }

                );



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!urlSite.getText().toString().equals("")){

                    String url = !urlSite.getText().toString().contains("https://")?"https://"+urlSite.getText().toString():urlSite.getText().toString();

                    myBrowser.loadUrl(url);
                    urlSite.setText("");


                }else{
                    Toast.makeText(getApplicationContext(),"Preencha o campo com alguma URL de site",Toast.LENGTH_SHORT).show();
                }
            }
        });

    btnBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(myBrowser.canGoBack()){
                myBrowser.goBack();
            }
        }
    });
    btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBrowser.canGoForward()){
                    myBrowser.goForward();
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