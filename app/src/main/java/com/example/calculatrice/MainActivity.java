package com.example.calculatrice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.service.carrier.CarrierMessagingService;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {


    private EditText inputEditText;
    private TextView resultTextView;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portrait);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.portrait);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.landscape);
        }
        inputEditText = findViewById(R.id.inputEditText);
        resultTextView = findViewById(R.id.resultText);

        setButtonClickListeners();

        calculator = new Calculator(this);

    }

    private void setButtonClickListeners() {

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log the click event
                Log.d("ButtonClick", "Button 1 clicked");

                // Append "1" to the inputEditText
                inputEditText.append("1");
            }
        });
        // Define the numeric buttons
        int[] numericButtonIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

        for (int numericButtonId : numericButtonIds) {
            Button numericButton = findViewById(numericButtonId);
            numericButton.setOnClickListener(new View.OnClickListener() {
                // print the button clicked

                @Override
                public void onClick(View v) {
                    // Append the clicked number to the inputEditText
                    Button button = (Button) v;
                    inputEditText.append(button.getText().toString());
                }
            });
        }

        // Define the operator buttons

        // Get the basic operator button IDs
        int[] operatorButtonIds = {R.id.btnPlus, R.id.btnMinus, R.id.btnMulitply, R.id.btnDivide, R.id.btnPercent, R.id.btnDot, R.id.btnDel};

// Check if the advanced operator buttons are available
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int[] advancedButtonIds = {R.id.btnSin, R.id.btnCos, R.id.btnTan, R.id.btnLn, R.id.btnLeftParentheses, R.id.btnRightParentheses, R.id.btnSqrt, R.id.btnPi};

            // Append the advanced operator button IDs to the basic ones
            operatorButtonIds = Arrays.copyOf(operatorButtonIds, operatorButtonIds.length + advancedButtonIds.length);
            System.arraycopy(advancedButtonIds, 0, operatorButtonIds, operatorButtonIds.length - advancedButtonIds.length, advancedButtonIds.length);

        }
        for (int operatorButtonId : operatorButtonIds) {
            Button operatorButton = findViewById(operatorButtonId);
            operatorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Append the clicked operator to the inputEditText
                    Button button = (Button) v;
                    inputEditText.append(button.getText());
                }
            });
        }

        // Define the equals button
        Button equalsButton = findViewById(R.id.btnEqual);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the calculation when equals button is clicked
                calculate();
            }
        });

        // Define the clear button
        Button clearButton = findViewById(R.id.btnC);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the inputEditText
                inputEditText.getText().clear();
                // Clear the resultTextView
                resultTextView.setText("0");
            }
        });
        // Set an OnClickListener to the delete button that calls a new method
        Button btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current input string
                String currentInput = inputEditText.getText().toString();

                // If the input string is not empty, remove the last character
                if (!TextUtils.isEmpty(currentInput)) {
                    String newInput = currentInput.substring(0, currentInput.length() - 1);
                    inputEditText.setText(newInput);
                }
            }
        });
    }

    private void calculate() {
        Calculator.expression = inputEditText.getText().toString();

        double result = calculator.calculateResult();
        if (Double.isNaN(result)) {
            resultTextView.setText("Error");
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#########"); // Only 6 digits after decimal point
        String formattedResult = decimalFormat.format(result);
        resultTextView.setText(formattedResult);
    }

}