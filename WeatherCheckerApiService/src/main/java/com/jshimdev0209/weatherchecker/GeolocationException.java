package com.jshimdev0209.weatherchecker;

public class GeolocationException extends Exception {

    public GeolocationException(String message) {
        super(message);
    }

    public GeolocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
