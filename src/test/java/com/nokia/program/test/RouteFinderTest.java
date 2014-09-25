/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.program.test;

import com.nokia.routefinder.logic.RouteFinder;
import com.nokia.routefinder.model.Door;
import com.nokia.routefinder.model.House;
import com.nokia.routefinder.model.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author david
 */
public class RouteFinderTest {

    private Map<Integer, Room> rooms;
    private List<Door> doors;
    private RouteFinder routeFinder;

    @Before
    public void initializeHouse() {
        rooms = new HashMap<Integer, Room>();
        doors = new ArrayList<Door>();

        for (int i = 0; i < 11; i++) {
            Room location = new Room(i);
            rooms.put(location.getId(), location);
        }

        addDoor(0, 1, true);
        addDoor(1, 0, true);
        addDoor(0, 2, true);
        addDoor(2, 0, true);
        addDoor(0, 4, true);
        addDoor(4, 0, true);
        addDoor(2, 6, true);
        addDoor(6, 2, false);
        addDoor(2, 7, true);
        addDoor(7, 2, true);
        addDoor(3, 7, true);
        addDoor(7, 3, true);
        addDoor(5, 8, true);
        addDoor(8, 5, true);
        addDoor(8, 9, true);
        addDoor(9, 8, true);
        addDoor(7, 9, true);
        addDoor(9, 7, true);
        addDoor(4, 9, true);
        addDoor(9, 4, true);
        addDoor(9, 10, true);
        addDoor(10, 9, true);
        addDoor(1, 10, true);
        addDoor(10, 1, false);

        House house = new House(rooms, doors);
        routeFinder = new RouteFinder(house);
    }

    @Test
    public void globalTest() {
        Integer source = 9;
        Integer destination = 0;

        routeFinder.initializeSource(source);
        LinkedList<Room> route = routeFinder.getPathToDestionation(destination);

        assertNotNull("expected route should not be null", route);
        assertEquals("route size should be 3", 3, route.size());
    }

    @Test
    public void openDoorTest() {
        Integer source = 0;
        Integer destination = 10;

        routeFinder.initializeSource(source);
        LinkedList<Room> route = routeFinder.getPathToDestionation(destination);

        assertNotNull("expected route should not be null", route);
        assertEquals("route size should be 3", 3, route.size());
    }

    @Test
    public void closedDoorTest() {
        Integer source = 10;
        Integer destination = 0;

        routeFinder.initializeSource(source);
        LinkedList<Room> route = routeFinder.getPathToDestionation(destination);

        assertNotNull("expected route should not be null", route);
        assertEquals("route size should be 4", 4, route.size());
    }

    @Test
    public void closedDoorNoAlternativeRouteTest() {
        Integer source = 6;
        Integer destination = 2;

        routeFinder.initializeSource(source);
        LinkedList<Room> route = routeFinder.getPathToDestionation(destination);

        assertNull("expected route should be null", route);
    }

    private void addDoor(int sourceRoomNr, int destRoomNr, boolean open) {
        doors.add(new Door(rooms.get(sourceRoomNr), rooms.get(destRoomNr), open));
    }
}
