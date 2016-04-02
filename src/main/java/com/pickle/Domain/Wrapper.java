package com.pickle.Domain;

/**
 * Created by andrikurniawan.id@gmail.com on 3/23/2016.
 */
public class Wrapper <E> {
    private int status;
    private String message;
    private E data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) { this.message = message; }

    public E getData() { return data; }

    public void setData(E data) { this.data = data; }

    public Wrapper() {
    }

    public Wrapper(int status, String message, E data) {

        this.status = status;
        this.message = message;
        this.data = data;
    }
}
