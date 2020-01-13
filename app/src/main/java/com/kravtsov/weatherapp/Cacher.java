package com.kravtsov.weatherapp;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Cacher{

    private static final String FILE_NAME = "cachedData.json";

    private static Cacher instance;

    private WeatherTask weatherTask;

    public static synchronized Cacher getInstance() {
        if (instance == null) {
            instance = new Cacher();
        }
        return instance;
    }

    public WeatherTask loadCache(Context context) {
        weatherTask = new WeatherTask();
        JSONParser jsonParser = new JSONParser();
        try {
            FileInputStream fileIn;
            fileIn = context.openFileInput(FILE_NAME);
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            Object obj = jsonParser.parse(InputRead);
            JSONArray weatherArray= (JSONArray) obj;
            JSONObject weatherData = (JSONObject)weatherArray.get(0);
            weatherTask.setAddress((String)weatherData.get("address"));
            weatherTask.setHumidity((String)weatherData.get("humidity"));
            weatherTask.setPressure((String)weatherData.get("pressure"));
            weatherTask.setSunrise((Long)weatherData.get("sunrise"));
            weatherTask.setSunset((Long)weatherData.get("sunset"));
            weatherTask.setTemp((String)weatherData.get("temp"));
            weatherTask.setTempMax((String)weatherData.get("tempMax"));
            weatherTask.setTempMin((String)weatherData.get("tempMin"));
            weatherTask.setUpdatedAtText((String)weatherData.get("updatedAt"));
            weatherTask.setWeatherDescription((String)weatherData.get("weatherDescription"));
            weatherTask.setWindSpeed((String)weatherData.get("windSpeed"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return weatherTask;
    }

    public void saveCache(Context context, WeatherTask weatherTask) {
            JSONObject weatherData = new JSONObject();
            weatherData.put("address", weatherTask.getAddress());
            weatherData.put("humidity", weatherTask.getHumidity());
            weatherData.put("pressure", weatherTask.getPressure());
            weatherData.put("sunrise", weatherTask.getSunrise());
            weatherData.put("sunset", weatherTask.getSunset());
            weatherData.put("temp", weatherTask.getTemp());
            weatherData.put("tempMax", weatherTask.getTempMax());
            weatherData.put("tempMin", weatherTask.getTempMin());
            weatherData.put("updatedAt", weatherTask.getUpdatedAtText());
            weatherData.put("weatherDescription", weatherTask.getWeatherDescription());
            weatherData.put("windSpeed", weatherTask.getWindSpeed());
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(weatherData);

            FileOutputStream fileout = null;
            try {
                fileout = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            try {
                outputWriter.write(jsonArray.toJSONString());
                outputWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo()
                != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
