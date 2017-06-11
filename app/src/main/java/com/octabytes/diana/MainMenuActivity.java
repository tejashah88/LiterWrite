package com.octabytes.diana;

import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void toLearn(View v) {
        goToActivity(this, BasicLearnActivity.class);
    }

    public void toPractice(View v) {
        goToActivity(this, TimeAttackActivity.class);
    }

    public void toSettings(View v) {
        goToActivity(this, SettingsActivity.class);
    }
}
