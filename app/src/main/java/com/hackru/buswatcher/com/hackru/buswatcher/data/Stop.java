package com.hackru.buswatcher.com.hackru.buswatcher.data;

/**
 * Created by Yiou on 10/11/2014.
 */
public class Stop {
    private String stopCode;
    private String street;

    /**
     * retun the stop code in string
     * @return
     */
    public String getCode(){
        return stopCode;
    }

    /**
     * retun the street in string
     * @return
     */
    public String getStreet() { return street; }

    /**
     * set the stop code by passing the string
     * @param stopCode
     * @return
     */
    public Stop setCode(String stopCode){
        this.stopCode=stopCode;
        return this;
    }

    public Stop setStreet(String street){
        this.street = street;
        return this;
    }
}
