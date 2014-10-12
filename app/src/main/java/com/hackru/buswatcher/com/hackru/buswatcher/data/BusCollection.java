package com.hackru.buswatcher.com.hackru.buswatcher.data;

import java.util.ArrayList;

/**
 * Singleton class, manages buses, could be used with Bus or ActiveBus
 * use getInstance to obtain a instance of the class
 */
public class BusCollection {
    protected ArrayList<Bus> busList;
    private static BusCollection mCollection;


    private BusCollection(){
        busList=new ArrayList<Bus>();
        setBusData();
    }

    /**
     * override this method to populate the ArrayList of Bus from local storage or Web service.
     * The default implementation just uses some dummy data
     */
    protected void setBusData(){
        for (int i=0;i<50;i++){
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
    public static BusCollection getInstance(){
        if (mCollection==null) {
            mCollection=new BusCollection();
            return mCollection;
        }
        else return mCollection;
    }

}
