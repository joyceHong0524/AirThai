package com.junga.airthai;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    ArrayAdapter adapter;
    private ArrayList<String> cityListForSearch = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment);

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);

        configCityList();


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cityListForSearch);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //adpater.getItem(i) shows content of the clicked item.

                Intent intent = new Intent();
                intent.putExtra("city", adapter.getItem(i).toString());
                setResult(RESULT_OK, intent);
                finish();
            }


        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SearchActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void configCityList() {
        cityListForSearch.add("bangkok");
        cityListForSearch.add("samut sakhon");
        cityListForSearch.add("nonthaburi");
        cityListForSearch.add("rayong");
        cityListForSearch.add("chiang mai");
        cityListForSearch.add("nakhon sawan");
        cityListForSearch.add("surat thani");
        cityListForSearch.add("phuket");
        cityListForSearch.add("chiang rai");
        cityListForSearch.add("mae hong son"); //doesn't work
        cityListForSearch.add("phrae");
        cityListForSearch.add("phayao");
        cityListForSearch.add("samut prakan");
        cityListForSearch.add("ratchaburi");
        cityListForSearch.add("songkhla");
        cityListForSearch.add("khon kaen");
        cityListForSearch.add("nakhon ratchasima");
        cityListForSearch.add("chachoengsao");
        cityListForSearch.add("narathiwat");
        cityListForSearch.add("yala");
        cityListForSearch.add("lamphun");
        cityListForSearch.add("sa kaeo");
        cityListForSearch.add("pathum thani");
        cityListForSearch.add("satun");
        cityListForSearch.add("yangon");
    }
}
