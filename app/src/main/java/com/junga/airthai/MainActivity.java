package com.junga.airthai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
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
    private HttpConnection httpConnection;
    FloatingActionButton fab;
    private final String cityName = "Bangkok";

    private SQLiteDatabase sqLiteDb;
    private ArrayList<CityVO>  cityList = new ArrayList<>();
    private ArrayList<String> cityListForSearch = new ArrayList<>();

    private FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestedCity = findViewById(R.id.city_name);
        aqi = findViewById(R.id.aqi);
        qualityToText = findViewById(R.id.quality_text);
        update = findViewById(R.id.update);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Fab is clicked");
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


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
        DropTable("CITY_T");
        initTable();


        insertValue(new CityVO("seoul",1));
        insertValue(new CityVO("bangkok",0));

        loadValues();

        //setting search function.

        setSearchFunction();


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

        BottomAppBar bar = findViewById(R.id.bar);
        setSupportActionBar(bar);
//        bar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(view.getId() == R.id.fab){
//                    Log.d(TAG, "onClick: fabclicked");
//                } else if (view.getId() == android.R.id.home){
//                    Log.d(TAG, "onContextItemSelected: clicked");
//                    refreshDrawerItems();
//                }
//            }
//        });

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
                "NAME "             + "TEXT," +
                "FAVORITE "         + "INTEGER NOT NULL)"; //Since sqlite doesn't have boolean type. true = 1, false = 0

        Log.d(TAG, "initTable: query string :" +query);
        sqLiteDb.execSQL(query);
    }

    private void loadValues(){
        if (sqLiteDb != null){
            String query = "SELECT * FROM CITY_T";
            Cursor cursor;

            cursor = sqLiteDb.rawQuery(query,null);

            while(cursor.moveToNext()){ // if the record exists,

                String name = cursor.getString(0);
                int favorite = cursor.getInt(1);
                CityVO city = new CityVO(name,favorite);
                cityList.add(city);

                Log.d(TAG, "loadValues: "+city.toString());



            }
        }
    }

    private void DeleteValue(String cityName){
        if(sqLiteDb!=null){
            String query = "DELETE FROM CITY_T WHERE NAME ="+cityName;
            sqLiteDb.execSQL(query);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            Log.d(TAG, "onContextItemSelected: clicked");
            refreshDrawerItems();}
//        }else if (item.getItemId()==R.id.fab){ //I don't know exactly why but these
//            Log.d(TAG, "onOptionsItemSelected: FabClicked");
//            cityList.add(new CityVO("fab1 clicked",0));
//            refreshDrawerItems();
//        }
        return super.onOptionsItemSelected(item);
    }


    private void insertValue(CityVO city){
        if(sqLiteDb != null){
            String query = "INSERT INTO CITY_T (NAME, FAVORITE) VALUES ("+"'"+city.getName()+"',"+city.getFavorite()+")";

            Log.d(TAG, "insertValue: insert query: "+query);
            sqLiteDb.execSQL(query);
        }
    }

    private void DropTable(String tableName){
        if(sqLiteDb != null){
            String query = "DROP TABLE "+tableName;
            sqLiteDb.execSQL(query);
        }
    }

    private String cityNameParsing(String url){
        String cityName="";


        return cityName;
    }

    private void refreshDrawerItems(){
        DrawerFragment drawerFragment = new DrawerFragment();
        Bundle bundle = new Bundle();

        ArrayList<String> stringArrayList = new ArrayList<>();

        for(CityVO data: cityList){
            stringArrayList.add(data.getName());
        }
        bundle.putStringArrayList("city",stringArrayList);
        drawerFragment.setArguments(bundle);
        Log.d(TAG, "refreshDrawerItems: "+bundle.toString());
        drawerFragment.show(getSupportFragmentManager(),drawerFragment.getTag());
    }

    private void setSearchFunction(){
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
    }
}
