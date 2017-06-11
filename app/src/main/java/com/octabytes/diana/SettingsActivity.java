package com.octabytes.diana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinnerOriginLang = (Spinner) findViewById(R.id.spinnerOriginLang);
        String[] countries=getResources().getStringArray(R.array.hellos_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.single_item, R.id.text, countries);

        spinnerOriginLang.setAdapter(adapter);
    }
}
