package com.pickle.Domain;

import java.util.Map;

/**
 * Created by andrikurniawan.id@gmail.com on 5/14/2016.
 */
public class GcmBody {
    private String to;
    private Map<String,String> data;

    public GcmBody(String to, Map<String,String> data) {
        this.to = to;
        this.data = data;
    }

    public GcmBody(){

    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String,String> getData() {
        return data;
    }

    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
