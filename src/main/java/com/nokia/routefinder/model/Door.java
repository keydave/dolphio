/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.routefinder.model;

/**
 *
 * @author david
 */
public class Door {

    private final Room source;
    private final Room destination;
    private final boolean open;

    public Door(Room source, Room destination, boolean open) {
        this.source = source;
        this.destination = destination;
        this.open = open;
    }

    public Room getDestination() {
        return destination;
    }

    public Room getSource() {
        return source;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }

    public boolean isOpen() {
        return open;
    }

}
