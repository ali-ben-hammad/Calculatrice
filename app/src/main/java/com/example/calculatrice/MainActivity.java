package com.example.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    private EditText inputEditText;
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
                    System.out.println("Button clicked");
                    Button button = (Button) v;
                    inputEditText.append(button.getText().toString());
                }
            });
        }

        // Define the operator buttons
        int[] operatorButtonIds = {R.id.btnPlus, R.id.btnMinus, R.id.btnMulitply, R.id.btnDevide};

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
            }
        });
    }

    private void calculate() {
        // Get the expression from the inputEditText
        Calculator.expression = inputEditText.getText().toString();

        // Clear the inputEditText
        inputEditText.getText().clear();
        // Append the result to the inputEditText
        inputEditText.append(calculator.calculateResult());

    }

}