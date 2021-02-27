package com.example.voiders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class TranslateActivity extends AppCompatActivity {
    WebView webView;
    String txt, lang, langTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        Intent intent = getIntent();
        txt = intent.getStringExtra("text");
        lang = intent.getStringExtra("lang");

        switch(lang){
            case "Kannada":
                langTag = "kn";
                break;
            case "Hindi":
                langTag = "hi";
                break;
            case "Tamil":
                langTag = "ta";
                break;
            case "Telugu":
                langTag = "te";
                break;
            case "Malayalam":
                langTag = "ml";
                break;
        }

        webView = (WebView)findViewById(R.id.webView);
        webView.loadUrl("https://translate.google.com/?sl=en&tl="+langTag+"&text="+txt+"&op=translate");



    }
}