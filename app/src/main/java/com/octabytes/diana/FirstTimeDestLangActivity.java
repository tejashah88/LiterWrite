package com.octabytes.diana;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FirstTimeDestLangActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ListView destLangList;

    String[] arrayDestHellos = Constants.availableHellos;

    ArrayList<String> listDestLangs = new ArrayList<>();

    ArrayAdapter<String> listDestLangsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_dest_lang);

        destLangList = (ListView) findViewById(R.id.list_origin_langs);
        destLangList.setOnItemClickListener(this);

        listDestLangsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listDestLangs);

        destLangList.setAdapter(listDestLangsAdapter);


        for (String destHello : arrayDestHellos) {
            listDestLangsAdapter.add(destHello);
        }
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        LocalDatabase.destLang = Constants.availableLangs[position];
        goToActivity(this, MainMenuActivity.class);
    }
}
