package com.octabytes.diana;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FirstTimeOriginLangActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ListView originLangList;

    String[] arrayOriginHellos = Constants.availableHellos;

    ArrayList<String> listOriginLangs = new ArrayList<>();

    ArrayAdapter<String> listOriginLangsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_origin_lang);

        originLangList = (ListView) findViewById(R.id.list_origin_langs);
        originLangList.setOnItemClickListener(this);

        listOriginLangsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listOriginLangs);

        originLangList.setAdapter(listOriginLangsAdapter);


        for (String originHello : arrayOriginHellos) {
            listOriginLangsAdapter.add(originHello);
        }
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        LocalDatabase.originLang = Constants.availableLangs[position];
        goToActivity(this, MainMenuActivity.class);
    }
}