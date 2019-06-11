package com.junga.airthai;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.junga.airthai.api.DataVO;
import com.junga.airthai.api.HttpConnection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import androidx.appcompat.widget.Toolbar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DataVO.class, new MyDeserializer())
            .create();
    private DataVO dataVO;
    private TextView requestedCity, aqi, qualityToText, update;
    HttpConnection httpConnection;
    private String cityName = "Bangkok";

    private SQLiteDatabase sqLiteDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestedCity = (TextView) findViewById(R.id.city_name);
        aqi = (TextView) findViewById(R.id.aqi);
        qualityToText = (TextView) findViewById(R.id.quality_text);
        update = (TextView) findViewById(R.id.update);


        httpConnection = HttpConnection.getInstance();
        httpConnection.requestWebServer(cityName, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String json = response.body().string();
                    dataVO = gson.fromJson(json, DataVO.class);

                    handler.sendEmptyMessage(1);
                }

            }
        });

        httpConnection.requestWebServer("seoul", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                dataVO = gson.fromJson(json, DataVO.class);
                handler.sendEmptyMessage(1);

            }
        });

        setToolbar();

        //Setting database.
        sqLiteDb = initDatabase();
        initTable();
        loadValues();

    }

    @SuppressLint("HandlerLeak")
    private final
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                //Check if the DataVO'memory has been allocated.
                Log.d(TAG, "dataVO : dominentpol " + dataVO.getDominentpol());
                Log.d(TAG, "dataVO : aqi " + dataVO.getAqi());
                Log.d(TAG, "dataVO : idx " + dataVO.getIdx());
                Log.d(TAG, "dataVO : time" + dataVO.getTime().getS());

                requestedCity.setText(cityName);
                aqi.setText(Integer.toString(dataVO.getAqi()));
                qualityToText.setText(aqiToText(dataVO.getAqi()));
                update.setText("Last Update\n" + dataVO.getTime().getS());
            }
        }
    };


    class MyDeserializer implements JsonDeserializer<DataVO> {
        @Override
        public DataVO deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
                throws JsonParseException {
            // Get the "content" element from the parsed JSON
            JsonElement content = je.getAsJsonObject().get("data");

            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(content, DataVO.class);

        }
    }


    private void setToolbar() {

        BottomAppBar bar = (BottomAppBar) findViewById(R.id.bar);
        setSupportActionBar(bar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    private String aqiToText(int aqi) {

        if (aqi >= 0 && aqi <= 50) {
            return "Good";
        } else if (aqi > 50 && aqi <= 100) {
            return "Moderate";
        } else if (aqi > 100 && aqi <= 150) {
            return "Unhealthy for\n Sensitive Groups";
        } else if (aqi > 150 && aqi <= 200) {
            return "Unhealthy";
        } else if (aqi > 200 && aqi <= 300) {
            return "Very Unhealthy";
        } else if (aqi > 300) {
            return "Hazardous";
        } else {
            return "Please refresh";
        }


    }


    private SQLiteDatabase initDatabase(){
        SQLiteDatabase db = null;
        File file = new File(getFilesDir(),"city.db");

        Log.d(TAG, "initDatabase: "+ file.toString()+" has been saved");
        try {
            db = SQLiteDatabase.openOrCreateDatabase(file,null);
        }catch (SQLException e){
            e.printStackTrace();
        }

        if(db == null){
            Log.d(TAG, "initDatabase: failed" + file.getAbsolutePath());
        }

        return db;
    }

    private void initTable(){
        String query = "CREATE TABLE IF NOT EXISTS CITY_T ("+
                "NAME"             + "TEXT NOT NULL," +
                "FAVORITE"         + "INTEGER NOT NULL)"; //Since sqlite doesn't have boolean type. true = 1, false = 0

        Log.d(TAG, "initTable: query string :" +query);
        sqLiteDb.execSQL(query);
    }

    private void loadValues(){
        if (sqLiteDb != null){
            String query = "SELECT * FROM CITY_T";
            Cursor cursor = null;

            cursor = sqLiteDb.rawQuery(query,null);

            while(cursor.moveToNext()){ // if the record exists,


            }
        }
    }
}
