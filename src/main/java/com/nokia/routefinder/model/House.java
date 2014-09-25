/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.routefinder.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author david
 */
public class House {

    private final Map<Integer, Room> rooms;
    private final List<Door> doors;

    public House(Map<Integer, Room> rooms, List<Door> doors) {
        this.rooms = rooms;
        this.doors = doors;
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public List<Door> getDoors() {
        return doors;
    }

}
