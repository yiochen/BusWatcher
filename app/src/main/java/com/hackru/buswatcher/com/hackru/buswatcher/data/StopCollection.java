package com.hackru.buswatcher.com.hackru.buswatcher.data;

import android.app.Activity;

import com.hackru.buswatcher.ChooseBusActivity;
import com.hackru.buswatcher.R;

import java.util.ArrayList;

/**
 * Singleton class, manages stops
 * use getInstance to obtain a instance of the class
 */
public class StopCollection {
    protected ArrayList<Stop> stopList;
    private static StopCollection mCollection;
    private static Activity activity;

    private StopCollection() {
        stopList = new ArrayList<Stop>();
        setStopData(1);
    }


    /**
     * modify this method to populate the stopList based on the busCode;
     * for example, if bus code is Q17, this method need to fill the stopList with all the stops in Q17 line.
     * Right now, it only provide dummy data;
     *
     * @param busCode
     */
    protected void setStopData(int busCode) {
        //the default
        String[] data = activity.getResources().getStringArray(R.array.Q18);
        switch (busCode) {
            case 0:
                data = activity.getResources().getStringArray(R.array.Q18);
                break;
            default:
                activity.getResources().getStringArray(R.array.Q18);
        }

        stopList.clear();
        for (String entry : data) {
            String street = entry.split(",")[0];
            String stop_code = entry.split(",")[1];
            stopList.add(new Stop().setCode(stop_code).setStreet(street));
        }

    }

    /**
     * return the list of the stops
     *
     * @return
     */
    public ArrayList<Stop> getData() {
        return stopList;
    }

    public static StopCollection getInstance(Activity activity) {
        if (StopCollection.activity == null) {
            StopCollection.activity = activity;
        }
        if (mCollection == null) {
            mCollection = new StopCollection();
            return mCollection;
        } else return mCollection;
    }
}
