package com.pickle.Domain;

/**
 * Wrapper for API responses
 *
 * <p>Determines the API responses as 3 core properties: the status, the message, and the data.</p>
 *
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
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

    public Wrapper(int status, String message, E data) {

        this.status = status;
        this.message = message;
        this.data = data;
    }
}
