package com.kravtsov.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;
import com.androdocs.weatherapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements Observer {

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    SearchView searchView;
    ArrayAdapter<String> arrayAdapter;
    Context context;
    ListView listView;
    private WeatherTask weatherTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        temp_minTxt = findViewById(R.id.temp_min);
        temp_maxTxt = findViewById(R.id.temp_max);
        sunriseTxt = findViewById(R.id.sunrise);
        sunsetTxt = findViewById(R.id.sunset);
        windTxt = findViewById(R.id.wind);
        pressureTxt = findViewById(R.id.pressure);
        humidityTxt = findViewById(R.id.humidity);
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        context = this;

        GeoWorker.getInstance().locatePos(context);
        ResponseListener.getInstance().subscribeOnPreResponse(this);
        ResponseListener.getInstance().subscibeOnPostExecute(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GeoWorker.getInstance().setCityName(query);
                GeoWorker.getInstance().locatePos(context);
                weatherTask = new WeatherTask();
                weatherTask.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        weatherTask = new WeatherTask();
        weatherTask.execute();
    }

/*    void cityGotCallback() {
        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new GeoWorker);
        listView.setAdapter(arrayAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listView.setVisibility(View.GONE);
                new WeatherTask().execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                //               arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
*/

    @Override
    public void onPreResponse() {

        /* Showing the ProgressBar, Making the main design GONE */
        findViewById(R.id.loader).setVisibility(View.VISIBLE);
        findViewById(R.id.mainContainer).setVisibility(View.GONE);
        findViewById(R.id.errorText).setVisibility(View.GONE);

    }

    @Override
    public void onPostExecute() {
        addressTxt.setText(weatherTask.getAddress());
        updated_atTxt.setText(weatherTask.getUpdatedAtText());
        statusTxt.setText(weatherTask.getWeatherDescription().toUpperCase());
        tempTxt.setText(weatherTask.getTemp());
        temp_minTxt.setText(weatherTask.getTempMin());
        temp_maxTxt.setText(weatherTask.getTempMax());
        sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(new Date(weatherTask.getSunrise() * 1000)));
        sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(new Date(weatherTask.getSunset() * 1000)));
        windTxt.setText(weatherTask.getWindSpeed());
        pressureTxt.setText(weatherTask.getPressure());
        humidityTxt.setText(weatherTask.getHumidity());

        /* Views populated, Hiding the loader, Showing the main design */
        findViewById(R.id.loader).setVisibility(View.GONE);
        findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);
    }
}
