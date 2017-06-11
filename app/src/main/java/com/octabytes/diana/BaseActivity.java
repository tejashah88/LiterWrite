package com.octabytes.diana;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Tehas on 6/11/2017
 */

public class BaseActivity extends AppCompatActivity {
    public void makeToast(CharSequence text) {
        Toast.makeText(this.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void makeToastLong(CharSequence text) {
        Toast.makeText(this.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public void goToActivity(Context context, Class<?> clazz) {
        startActivity(new Intent().setClass(context, clazz));
    }

    public void goToActivity(Context context, Class<?> clazz, Intent intent) {
        startActivity(intent.setClass(context, clazz));
    }
}
