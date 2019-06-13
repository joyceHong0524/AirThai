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
        cityListForSearch.add("Bangkok");
        cityListForSearch.add("Samut Sakhon");
//        cityListForSearch.add("nonthaburi");
        cityListForSearch.add("Rayong");
        cityListForSearch.add("Chiang mai");
        cityListForSearch.add("Nakhon sawan");
        cityListForSearch.add("Surat Thani");
        cityListForSearch.add("Phuket");
        cityListForSearch.add("Chiang Rai");
//        cityListForSearch.add("mae hong son"); //doesn't work
        cityListForSearch.add("Phrae");
        cityListForSearch.add("Phayao");
        cityListForSearch.add("Samut Prakan");
        cityListForSearch.add("Ratchaburi");
//        cityListForSearch.add("songkhla");
        cityListForSearch.add("Khon Kaen");
        cityListForSearch.add("Nakhon Ratchasima");
//        cityListForSearch.add("chachoengsao");
        cityListForSearch.add("Narathiwat");
//        cityListForSearch.add("yala");
        cityListForSearch.add("Lamphun");
//        cityListForSearch.add("sa kaeo");
        cityListForSearch.add("Pathum Thani");
        cityListForSearch.add("Satun");
        cityListForSearch.add("Yangon");
    }
}
