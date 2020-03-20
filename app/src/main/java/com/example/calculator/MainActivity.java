package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.widget.Button;
import android.widget.Toast;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;

import static java.lang.String.valueOf;


public class MainActivity extends AppCompatActivity {


    private String expression;
    private String[] history;

    private boolean isNumberPressed, isSignPressed;
    private TextView viewAnswer,viewHistory;

    long startTime;




    private Button button0, button1,button2,button3,button4,button5,button6,button7, button8, button9, buttonMinus,buttonDivide,buttonMulti,getButtonMinus,buttonPlus,buttonErase, buttonEqul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewAnswer = (TextView) findViewById(R.id.answer);
        viewHistory = (TextView) findViewById(R.id.history);
        history = new String[4];
        expression = "";
        isNumberPressed = false;
        isSignPressed = false;
        startTime = System.currentTimeMillis();

    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCloseB:
                pressBracket(")");
                break;
            case R.id.button0:
                pressNumber(0);
                break ;
            case R.id.button1:
                pressNumber(1);
                break ;
            case R.id.button2:
                pressNumber(2);
                break;
            case R.id.button3:
                pressNumber(3);
                break ;
            case R.id.button4:
                pressNumber(4);
                break;
            case R.id.button5:
                pressNumber(5);
                break ;
            case R.id.button6:
                pressNumber(6);
                break;
            case R.id.button7:
                pressNumber(7);
                break ;
            case R.id.button8:
                pressNumber(8);
                break;
            case R.id.button9:
                pressNumber(9);
                break ;
            case R.id.buttonMinus:
                pressSign("-");
                break;
            case R.id.buttonPlus:
                pressSign("+");
                break ;
            case R.id.buttonMulti:
                pressSign("×");
                break;
            case R.id.buttonDivide:
                pressSign("÷");
                break;
            case R.id.buttonEqual:
                pressEqual();
                break;
            case R.id.buttonOpenB:
                pressBracket("(");
                break;
            case R.id.buttonErase:
                if((System.currentTimeMillis() - startTime)<1000){
                    pressCancel();
                }
                else{
                    pressErase();
                }
                startTime = System.currentTimeMillis();
                break;
            case R.id.buttonDot:
                pressDot();
                break;
            case R.id.buttonPercent:
                pressPercent();
                break;

        }
        viewAnswer.setText(expression);
    }
    public void pressBracket(String str){
        if (isNumberPressed){
            String numBefore;
            if (expression.contains(" ")){
                numBefore = expression.substring(expression.lastIndexOf(" ") + 1);
            }
            else {
                numBefore = expression;
            }
            if(numBefore.equals("0")){
                pressErase();
                
            }


        }

    }

    private void pressDot() {
        if (isNumberPressed) {
            String numBefore;
            if (expression.contains(" ")){
                numBefore = expression.substring(expression.lastIndexOf(" ") + 1);
            }
            else {
                numBefore = expression;
            }
            if (numBefore.contains("%")||numBefore.contains(".")){
                Toast.makeText(getApplicationContext(),"점을 찍을 수 없어요",Toast.LENGTH_LONG).show();
            }
            else {
                expression = expression + ".";
            }
        }
        else if (isSignPressed) {
            pressNumber(0);
            pressDot();
        }
        else{
            pressNumber(0);
            pressDot();
        }
    }

    private void pressNumber(int i) {
        if (isNumberPressed) {
            if (expression.substring(expression.length() - 1).equals("%")) {
                pressSign("×");
                pressNumber(i);
            }
            else {
                if (expression.equals("0")) {
                    expression = valueOf(i);
                }
                else if ((2 <= expression.length()) && (expression.substring(expression.length() - 2).equals( " 0"))) {
                        expression = expression.substring(0, expression.length() - 1) + valueOf(i);
                }
                else {
                    expression = expression + valueOf(i);
                }
            }
        }
        else if (isSignPressed) {
            expression = expression + " " + valueOf(i);
        }
        else {
            expression = valueOf(i);
        }
        isNumberPressed=true;
        isSignPressed=false;
    }
    private void pressPercent() {
        if (isNumberPressed) {
            if ( (expression.substring(expression.length()-1)).equals(".") ) {
                expression = expression.substring(0,expression.length()-1);
            }
            isNumberPressed=true;
            isSignPressed=false;
            expression = expression+"%";
        }
        else if (isSignPressed) {
            isNumberPressed=true;
            isSignPressed=false;
            expression = expression.substring(0, expression.length()-2)+"%";
        }
        else{
            Toast.makeText(getApplicationContext(),"숫자를 먼저 입력하쇼",Toast.LENGTH_LONG).show();
        }
    }

    private void pressSign(String str) {
        if (isNumberPressed) {
            if (expression.substring(expression.length()-1).equals(".")){
                pressErase();
                pressSign(str);
                isNumberPressed = false;
                isSignPressed = true;
            }
            else {
                isNumberPressed = false;
                isSignPressed = true;
                expression = expression + " " + str;
            }
        }
        else if (isSignPressed) {
            isNumberPressed=false;
            isSignPressed=true;
            expression = expression.substring(0, expression.length()-1)+str;
        }
        else{
            Toast.makeText(getApplicationContext(),"숫자를 먼저 입력하쇼",Toast.LENGTH_LONG).show();
        }

    }

    public void pressErase() {
        if (isNumberPressed) {
            if (expression.contains(" "))  {
                String numBefore = expression.substring(expression.lastIndexOf(" ") + 1);
                if (numBefore.length() == 1){
                    isNumberPressed = false;
                    isSignPressed = true;
                    expression = expression.substring(0, expression.length() - 2);
                }
                else {
                    isNumberPressed = true;
                    isSignPressed = false;
                    expression = expression.substring(0,expression.length()-1);
                }
            }
            else {
                if (expression.length() == 1) {
                    expression = "0";
                    isNumberPressed = true;
                    isSignPressed = false;
                }
                else {
                    isNumberPressed = true;
                    isSignPressed = false;
                    expression = expression.substring(0, expression.length() - 1);
                }
            }
        }
        else if (isSignPressed) {
            expression = expression.substring(0, expression.length() - 2);
            isNumberPressed = true;
            isSignPressed = false;
        }
        else {
            Toast.makeText(getApplicationContext(),"지울게 없는걸",Toast.LENGTH_LONG).show();
        }
    }
    public void pressCancel() {
        expression = "";
        isNumberPressed = false;
        isSignPressed = false;
    }
    private void pressEqual() {
        if (isSignPressed){
            pressErase();
        }
        if (expression.substring(expression.length()-1).equals(".")){
            pressErase();
        }
        if(isNumberPressed){

            List<String> numbers = new ArrayList<String> (Arrays.asList(expression.split(" ")));
            for(int i=0; i<numbers.size(); i++){
                String str = numbers.get(i);
                if (str.contains("%")){
                    BigDecimal num1 = new BigDecimal(str.substring(0,str.length()-1));
                    num1 = num1.multiply(new BigDecimal("0.01"));
                    numbers.set(i,num1.toString());
                    }
                System.out.format("[%2d] = '%s'\n", i, str);
            }
            numbers = calculate(numbers);
            System.out.println(numbers);
            updateHistory(numbers.get(0));

            expression = numbers.get(0);

        }
        else {
            Toast.makeText(getApplicationContext(),"수식을 입력하세요",Toast.LENGTH_LONG).show();
        }
    }
    private void updateHistory(String ans){
        history[0]= expression+" = "+ans;
        viewHistory.setText(history[0]);

    }
    private List<String> calculate(List<String> calc) {
        if (calc.size() == 1){
            return calc;
        }

        int i = calc.size();
        int j = calc.size();
        if (calc.contains("×")) i = calc.indexOf("×");
        if (calc.contains("÷"))  j = calc.indexOf("÷");
        if (i < j){
            BigDecimal num1 = new BigDecimal(calc.get(i-1));
            BigDecimal num2 = new BigDecimal(calc.get(i+1));
            BigDecimal num3 = num1.multiply(num2);

            calc.set(i-1, num3.toString());
            calc.remove(i);
            calc.remove(i);


            return calculate(calc);
        }
        else if (j < i){

            BigDecimal num1 = new BigDecimal(calc.get(j-1));
            BigDecimal num2 = new BigDecimal(calc.get(j+1));
            BigDecimal num3 = num1.divide(num2, MathContext.DECIMAL64);
            calc.set(j-1, num3.toString());
            calc.remove(j);
            calc.remove(j);

            return calculate(calc);
        }

        int k = 1;
        BigDecimal num1 = new BigDecimal(calc.get(k-1));
        BigDecimal num2 = new BigDecimal(calc.get(k+1));
        BigDecimal num3;
        if (calc.get(k).equals("+"))  num3 = num1.add(num2);
        else if (calc.get(k).equals("-"))  num3 = num1.subtract(num2);
        else num3 = num1;
        calc.set(k-1, num3.toString());
        calc.remove(k);
        calc.remove(k);

        return calculate(calc);

    }

}
