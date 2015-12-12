package com.example.yms.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText input1, input2;
    TextView resultView;
    String num1, num2;
    Double result;
    Button[] numButtons = new Button[10];
    Integer[] numBtnIDs = {R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine};
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.gridlayout);

        input1 = (EditText) findViewById(R.id.input1);
        input2 = (EditText) findViewById(R.id.input2);
        resultView = (TextView) findViewById(R.id.resultView);

        for (i = 0; i < numBtnIDs.length; i++) {
            numButtons[i] = (Button) findViewById(numBtnIDs[i]);
        }
        for (i = 0; i < numBtnIDs.length; i++) {
            final int index;
            index = i;
            numButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (input1.isFocused()) {
                        num1 = input1.getText().toString() + numButtons[index].getText().toString();
                        input1.setText(num1);
                    } else if (input2.isFocused()) {
                        num2 = input2.getText().toString() + numButtons[index].getText().toString();
                        input2.setText(num2);
                    } else {
                        Toast.makeText(getApplicationContext(), "입력공간을 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onClick(View view) {
        if (num1 == null|| num2==null) {
            Toast.makeText(getApplicationContext(), "값이 입력되지않았습니다.", Toast.LENGTH_SHORT).show();
        } else {
            switch (view.getId()) {
                case R.id.plus:
                    result = Double.parseDouble(input1.getText().toString()) + Double.parseDouble(input2.getText().toString());
                    break;
                case R.id.minus:
                    result = Double.parseDouble(input1.getText().toString()) - Double.parseDouble(input2.getText().toString());
                    break;
                case R.id.multi:
                    result = Double.parseDouble(input1.getText().toString()) * Double.parseDouble(input2.getText().toString());
                    break;
                case R.id.divide:
                    result = Double.parseDouble(input1.getText().toString()) / Double.parseDouble(input2.getText().toString());
                    break;
            }
            resultView.setText("계산결과: " + result);
        }
    }



}
