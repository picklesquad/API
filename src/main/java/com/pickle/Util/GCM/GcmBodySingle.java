package com.pickle.Util.GCM;

/**
 * Created by andrikurniawan.id@gmail.com on 5/14/2016.
 */
public class GcmBodySingle<T> extends GcmBody<T>{

    private String to;

    public GcmBodySingle(){
        super();
    }

    public GcmBodySingle(T data, String to) {
        super(data);

        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
