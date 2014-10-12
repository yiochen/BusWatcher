package com.hackru.buswatcher.com.hackru.buswatcher.data;

import android.app.Activity;

import com.hackru.buswatcher.R;

import java.util.ArrayList;

/**
 * Singleton class, manages buses, could be used with Bus or ActiveBus
 * use getInstance to obtain a instance of the class
 */
public class BusCollection {
    protected ArrayList<Bus> busList;
    private static BusCollection mCollection;
    static Activity activity;


    private BusCollection(){
        busList=new ArrayList<Bus>();
        setBusData();
    }

    /**
     * override this method to populate the ArrayList of Bus from local storage or Web service.
     * The default implementation just uses some dummy data
     */
    protected void setBusData(){
        String[] buses = activity.getResources().getStringArray(R.array.buses);

        busList.clear();
        for (int i = 0; i < buses.length; i++) {
            busList.add(new Bus().setNameInt(i));
        }
    }

    /**
     * return the array of the buses
     * @return
     */
    public ArrayList<Bus> getData(){
        return busList;
    }
    /**
     * return the instance of the manager class
     * @return
     */
    public static BusCollection getInstance(Activity activity){
        if (BusCollection.activity == null) {
            BusCollection.activity = activity;
        }
        if (mCollection==null) {
            mCollection=new BusCollection();
            return mCollection;
        }
        else return mCollection;
    }

}
