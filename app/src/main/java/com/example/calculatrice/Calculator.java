package com.example.calculatrice;

import android.content.Context;
import android.webkit.ValueCallback;

public class Calculator {

    public static String expression;
    public String result;
    private Context context;

    public Calculator(Context context) {
        this.context = context;
        expression = "";
        result = "";
    }

    public void appendExpression(String str) {
        expression += str;
    }

    public void clear() {
        expression = "";
        result = "";
    }

    public String calculateResult() {
        android.webkit.WebView webView = new android.webkit.WebView(context);
        webView.evaluateJavascript("eval(" + expression + ")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                result = s;
            }
        });
        return result;
    }
}