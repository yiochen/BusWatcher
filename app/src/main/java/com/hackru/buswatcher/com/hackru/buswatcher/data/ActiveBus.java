package com.hackru.buswatcher.com.hackru.buswatcher.data;

/**
 * Created by Yiou on 10/11/2014.
 */
public class ActiveBus extends Bus{
    private Stop nextStop;
    private String busCode;//the unique identity of a bus in sequence of digits.

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

}
