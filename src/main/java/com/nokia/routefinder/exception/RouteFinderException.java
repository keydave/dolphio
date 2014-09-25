/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.routefinder.exception;

/**
 *
 * @author david
 */
public class RouteFinderException extends IllegalArgumentException {

    public RouteFinderException() {

    }

    public RouteFinderException(String message) {
        super(message);
    }

    public RouteFinderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteFinderException(Throwable cause) {
        super(cause);
    }
}
