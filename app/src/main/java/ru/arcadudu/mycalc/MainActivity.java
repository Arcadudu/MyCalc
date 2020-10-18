package ru.arcadudu.mycalc;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private TextView history;
    private ImageButton ibBackspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.et_display);
        display.setShowSoftInputOnFocus(false); // remove keyboard appearance

        history = findViewById(R.id.history);


        // longPush for backspace
        ibBackspace = findViewById(R.id.ib_backspace);
        ibBackspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                display.setText("");
                return true;
            }
        });


    }

    public void updateText(String newStr) {
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        display.setText(String.format("%s%s%s", leftStr, newStr, rightStr));
        display.setSelection(cursorPos + 1);
    }

    // backspace
    public void backspaceIBtn(View view) {
        int cursorPos = display.getSelectionStart();
        int stringLen = display.getText().length();
        if (cursorPos != 0 && stringLen != 0) {
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos - 1);
        }
    }

    // num buttons
    public void zeroBtn(View view) {
        updateText("0");
    }

    public void oneBtn(View view) {
        updateText("1");
    }

    public void twoBtn(View view) {
        updateText("2");
    }

    public void threeBtn(View view) {
        updateText("3");
    }

    public void fourBtn(View view) {
        updateText("4");
    }

    public void fiveBtn(View view) {
        updateText("5");
    }

    public void sixBtn(View view) {
        updateText("6");
    }

    public void sevenBtn(View view) {
        updateText("7");
    }

    public void eightBtn(View view) {
        updateText("8");
    }

    public void nineBtn(View view) {
        updateText("9");
    }

    // ops buttons
    public void clearBtn(View view) {
        display.setText("");
        history.setText("");
    }

    public void plusBtn(View view) {
        updateText("+");
    }

    public void minusBtn(View view) {
        updateText("-");
    }

    public void powerBtn(View view) {
        updateText("^");
    }

    public void divideBtn(View view) {
        updateText("÷");
    }

    public void multiplyBtn(View view) {
        updateText("×");
    }

    public void parenthesesBtn(View view) {
        int cursorPos = display.getSelectionStart();
        int openPar = 0;
        int closePar = 0;
        int textLen = display.getText().length();
        for (int i = 0; i < cursorPos; i++) {
            if (display.getText().toString().substring(i, i + 1).equals("(")) {
                openPar += 1;
            }
            if (display.getText().toString().substring(i, i + 1).equals(")")) {
                closePar += 1;
            }
        }

        if (openPar == closePar || display.getText().toString().substring(textLen - 1, textLen).equals("(")) {
            updateText("(");
        } else if (closePar < openPar && !display.getText().toString().substring(textLen - 1, textLen).equals("(")) {
            updateText(")");
        }
        display.setSelection(cursorPos + 1);
    }

    public void percentBtn(View view) {
        updateText("%");
    }

    public void dotBtn(View view) {
        updateText(".");
    }

    public void equalsBtn(View view) {
        String expression = display.getText().toString();
        history.setText(expression);
        expression = expression.replaceAll("÷", "/");
        expression = expression.replaceAll("×", "*");

        Expression exp = new Expression(expression);
        String result = String.valueOf(exp.calculate());

        display.setText(result);
        display.setSelection(result.length());

    }


}