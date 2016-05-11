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

    /**
     * Returns the status of this wrapper.
     * @return the {@code status} property
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status of this wrapper with the specified {@code status}.
     * @param status the {@code status} for this wrapper
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the message of this wrapper.
     * @return the {@code message} property
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of this wrapper with the specified {@code message}.
     * @param message the {@code message} for this wrapper
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * Returns the aata of this wrapper.
     * @return the {@code data} property
     */
    public E getData() { return data; }

    /**
     * Sets the data of this wrapper with the specified {@code data}.
     * @param data the {@code data} for this wrapper
     */
    public void setData(E data) { this.data = data; }

    /**
     * Constructs a new Wrapper with the specified status, message, and data.
     * @param status the status for this wrapper
     * @param message the message for this wrapper
     * @param data the data for this wrapper
     */
    public Wrapper(int status, String message, E data) {

        this.status = status;
        this.message = message;
        this.data = data;
    }
}
