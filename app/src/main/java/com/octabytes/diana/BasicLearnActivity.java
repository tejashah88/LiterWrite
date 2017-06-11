package com.octabytes.diana;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;
import com.myscript.atk.scw.SingleCharWidget;
import com.myscript.atk.scw.SingleCharWidgetApi;

public class BasicLearnActivity extends BaseActivity implements
        SingleCharWidgetApi.OnConfiguredListener,
        SingleCharWidgetApi.OnTextChangedListener {

    private SingleCharWidgetApi widget;
    private char recentChar;
    private ImageView imgLetter;
    private int char_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_learn);

        imgLetter = (ImageView) findViewById(R.id.imgLetter);

        widget = (SingleCharWidget) findViewById(R.id.singleChar_widget);
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

        widget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf");
        widget.configure("en_US", "si_text");

        widget.setAutoFreezeDelay(10000);
    }

    @Override
    protected void onDestroy() {
        widget.setOnTextChangedListener(null);
        widget.setOnConfiguredListener(null);

        super.onDestroy();
    }

    @Override
    public void onConfigured(SingleCharWidgetApi widget, boolean success) {
        if (!success) {
            makeToast(widget.getErrorString());
            return;
        }

        widget.setSpeedQualityCompromise(255);

        loadCharBoard(Constants.alphabet[char_index]);
        imgLetter.setVisibility(View.VISIBLE);
    }

    public void loadCharBoard(char ch) {
        Ion.with(imgLetter)
                .error(R.mipmap.ic_launcher)
                .animateGif(AnimateGifMode.ANIMATE)
                .load("file:///android_asset/lower/" + ch + ".gif");
    }

    public void checkChar(char input, char goal) {
        if (input == goal) {
            if (goal == 'z') {
                onBackPressed();
            }

            loadCharBoard(Constants.alphabet[++char_index]);
            widget.clear();
        } else {
            makeToast("Try again!");
        }
    }

    @Override
    public void onTextChanged(SingleCharWidgetApi widget, String s, boolean intermediate) {
        if (widget.getText().length() == 0)
            return;

        recentChar = widget.getText().charAt(0);

        if (intermediate) {
            widget.setText("");
            checkChar(recentChar, Constants.alphabet[char_index]);
        }
    }
}
