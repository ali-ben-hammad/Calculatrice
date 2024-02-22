package com.example.calculatrice;

import android.content.Context;
import android.os.RemoteException;
import android.service.carrier.CarrierMessagingService;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import org.mariuszgromada.math.mxparser.Expression;

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
        Expression expr = new Expression(expression);
        double result = expr.calculate();

        if (result > 1e9) {
            return String.format("%.2E", result);
        } else {
            return String.valueOf(result);
        }
    }

}