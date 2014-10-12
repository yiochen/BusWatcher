package com.hackru.buswatcher.com.hackru.buswatcher.data;

/**
 * Created by Yiou on 10/11/2014.
 */
public class Bus {
    /**
     * The array of names of the buses
     */
    public static final String[] BUS_NAMES = {"Q15", "Q17", "Q19"};
    private String busCode;//the unique identity of a bus in sequence of digits.
    private int busName;//the index of the name user usually associate the bus with, for example, Q17.

    /**
     * set the code of the bus, ususally in a sequence of digits. this is the unique identity of the bus, fetched from server.
     *
     * @param code
     * @return
     */
    public Bus setCode(String code) {
        this.busCode = code;
        return this;
    }

    /**
     * set the code of the bus, ususally in a sequence of digits. this is the unique identity of the bus, fetched from server.
     */
    public String getCode() {
        return busCode;
    }

    /**
     * get the index of the name of the bus
     *
     * @return
     */
    public int getName() {
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
