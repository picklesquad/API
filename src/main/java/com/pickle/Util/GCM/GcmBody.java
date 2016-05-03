package com.pickle.Util.GCM;

/**
 * Created by andrikurniawan.id@gmail.com on 5/4/2016.
 */
public class GcmBody<T> {
    protected T data;

    public GcmBody(){

    }

    public GcmBody(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }
}
