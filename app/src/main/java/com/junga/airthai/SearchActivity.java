package com.junga.airthai;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    ListView listView;
    private ArrayList<String> cityListForSearch = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment);

        cityListForSearch.add("bangkok");
        cityListForSearch.add("samut sakhon");
        cityListForSearch.add("nonthaburi");
        cityListForSearch.add("Rayong");
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



        String[] list = {};

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,cityListForSearch);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);



    }
}
