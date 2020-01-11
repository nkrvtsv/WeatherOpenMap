package com.androdocs.weatherapp;

import com.kravtsov.weatherapp.GeoWorker;

import android.content.Context;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GeoWorkerTest {

    @Mock
    Context context;


    @Test
    public void UT_01_locatePos() {
        GeoWorker.getInstance().locatePos(context);
        assertEquals("1", 0, GeoWorker.getInstance().getLat());
    }

}