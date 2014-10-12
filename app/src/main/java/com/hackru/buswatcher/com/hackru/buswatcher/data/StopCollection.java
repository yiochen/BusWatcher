package com.hackru.buswatcher.com.hackru.buswatcher.data;

import java.util.ArrayList;

/**
 * Singleton class, manages stops
 * use getInstance to obtain a instance of the class
 */
public class StopCollection {
    protected ArrayList<Stop> stopList;
    private static StopCollection mCollection;

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
        for (int i = 0; i < 50; i++) {
            stopList.add(new Stop().setCode("123stop" + i));
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

    public static StopCollection getInstance() {
        if (mCollection == null) {
            mCollection = new StopCollection();
            return mCollection;
        } else return mCollection;
    }
}
