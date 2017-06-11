package com.octabytes.diana;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.myscript.atk.scw.SingleCharWidget;
import com.myscript.atk.scw.SingleCharWidgetApi;
import com.myscript.atk.sltw.SingleLineWidget;
import com.myscript.atk.sltw.SingleLineWidgetApi;

import java.util.Random;

public class TimeAttackActivity extends BaseActivity implements
        SingleLineWidgetApi.OnConfiguredListener,
        SingleLineWidgetApi.OnTextChangedListener {

    private SingleLineWidgetApi widget;
    private TextView txtWordGoal, txtMainCountdown;
    private int lastInt = -1;
    private int count = 0;
    private final int max_count = Constants.words.length;
    private String currentWord = "";

    CountDownTimer mainCountdown = new CountDownTimer(60000, 1000) {
        public void onTick(long millisUntilFinished) {
            txtMainCountdown.setText((millisUntilFinished / 1000) + " seconds left!");
        }

        public void onFinish() {
            txtMainCountdown.setText("");
            txtWordGoal.setText("Done!");
            txtWordGoal.setTextColor(Color.RED);
            showScore();
        }
    };

    public void showScore() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("You were able to write " + count + " words correctly! Good Job!");
        dlgAlert.setTitle("Your Score");
        dlgAlert.setCancelable(false);
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });
        dlgAlert.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack);

        widget = (SingleLineWidget) findViewById(R.id.singleLine_widget);
        if (!widget.registerCertificate(MyCertificate.getBytes())) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Please use a valid certificate.");
            dlgAlert.setTitle("Invalid certificate");
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.create().show();
            return;
        }

        widget.setOnConfiguredListener(this);
        widget.setOnTextChangedListener(this);

        txtWordGoal = (TextView) findViewById(R.id.txtWordGoal);
        txtMainCountdown = (TextView) findViewById(R.id.txtMainCountdown);
        txtMainCountdown.setText("");

        widget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf");
        widget.configure("en_US", "cur_text");
    }

    @Override
    protected void onDestroy() {
        widget.setOnTextChangedListener(null);
        widget.setOnConfiguredListener(null);

        super.onDestroy();
    }

    @Override
    public void onConfigured(SingleLineWidgetApi widget, boolean success) {
        if (!success) {
            makeToast(widget.getErrorString());
            return;
        }

        widget.setSpeedQualityCompromise(255);
        widget.setAutoScrollEnabled(false);
        count = 0;
        startCountdown();
    }

    public void generateWord() {
        int nextInt;
        do {
            nextInt = new Random().nextInt(50);
        } while (nextInt == lastInt);
        lastInt = nextInt;

        currentWord = Constants.words[lastInt];
        txtWordGoal.setText(currentWord);
    }

    public void startCountdown() {
        //txtWordGoal.setText("5");
        (new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                txtWordGoal.setText("" + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                generateWord();
                mainCountdown.start();
            }
        }).start();
    }

    public void checkInput(String input, String goal) {
        if (goal.equals(input)) {
            count++;
            if (count == max_count) {
                mainCountdown.cancel();
                txtWordGoal.setText("Done!");
                txtWordGoal.setTextColor(Color.RED);
                showScore();
            }

            generateWord();
            widget.clear();
        } else {
            makeToast("Try again!");
        }
    }

    @Override
    public void onTextChanged(SingleLineWidgetApi widget, String s, boolean intermediate) {
        if (!intermediate) {
            String input = widget.getText();
            widget.setText("");
            checkInput(input, currentWord);
        }
    }
}
