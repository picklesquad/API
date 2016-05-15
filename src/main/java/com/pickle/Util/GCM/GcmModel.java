package com.pickle.Util.GCM;

/**
 * Created by andrikurniawan.id@gmail.com on 5/14/2016.
 */
public class GcmModel<E> {
    private String to;
    private E data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public GcmModel(String to, E data) {
        this.to = to;
        this.data = data;
    }
}
