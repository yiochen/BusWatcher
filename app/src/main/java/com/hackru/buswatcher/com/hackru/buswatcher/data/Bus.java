package com.hackru.buswatcher.com.hackru.buswatcher.data;

/**
 * Created by Yiou on 10/11/2014.
 * modify a bit
 */
public class Bus {
    /**
     * The array of names of the buses
     */
    public static final String[] BUS_NAMES = {"Q15", "Q17", "Q19"};

    private int busName;//the index of the name user usually associate the bus with, for example, Q17.


    public Bus setNameInt(int name){
        this.busName=name;
        return this;
    }
    /**
     * get the index of the name of the bus
     *
     * @return
     */
    public int getNameInt() {
        return busName;
    }

    /**
     * return the string name of the bus
     *
     * @return if found the bus, return the name in string, other wise, return "no bus found"
     */
    public String getNameString() {
        if (busName < BUS_NAMES.length) return BUS_NAMES[busName];
        else return "no bus found";
    }
}
