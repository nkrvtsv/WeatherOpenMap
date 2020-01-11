package com.kravtsov.weatherapp;

import java.util.ArrayList;
import java.util.List;

public  class ResponseListener {

    private static ResponseListener instance;

    private List<Observer> mSubscribedPreResponseList;
    private List<Observer> mSubscribedPostResponseList;

    private ResponseListener() {
        mSubscribedPreResponseList = new ArrayList<>();
        mSubscribedPostResponseList = new ArrayList<>();
    }


    public static synchronized ResponseListener getInstance() {
        if (instance == null) {
            instance = new ResponseListener();
        }
        return instance;
    }

    void subscribeOnPreResponse(Observer observer) {
        mSubscribedPreResponseList.add(observer);
    }

    void notifyOnPreResponse() {
        if (mSubscribedPreResponseList != null) {
            for (Observer b : mSubscribedPreResponseList) {
                b.onPreResponse();
            }
        }
    }

    void subscibeOnPostExecute(Observer observer) {
        mSubscribedPostResponseList.add(observer);

    }

    void notifyOnPostExecute() {
        if (mSubscribedPostResponseList != null) {
            for (Observer b : mSubscribedPostResponseList) {
                b.onPostExecute();
            }
        }
    }
}
