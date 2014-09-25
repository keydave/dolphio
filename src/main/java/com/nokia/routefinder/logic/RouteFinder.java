/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.routefinder.logic;

import com.nokia.routefinder.exception.RouteFinderException;
import com.nokia.routefinder.model.Door;
import com.nokia.routefinder.model.House;
import com.nokia.routefinder.model.Room;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author david
 */
public class RouteFinder {

    private final List<Door> doors;
    private final Map<Integer, Room> rooms;
    private final Set<Room> visitedRooms = new HashSet<Room>();
    private final Set<Room> notVisitedRooms = new HashSet<Room>();
    private final Map<Room, Room> predecessors = new HashMap<Room, Room>();
    private final Map<Room, Integer> distance = new HashMap<Room, Integer>();
    private static final int DOORWEIGHT = 1;

    public RouteFinder(House house) {
        this.doors = house.getDoors();
        this.rooms = house.getRooms();
    }

    public void initializeSource(Integer sourceId) {
        if (!rooms.containsKey(sourceId)) {
            throw new RouteFinderException("source room not found");
        }
        Room source = rooms.get(sourceId);
        distance.put(source, 0);
        notVisitedRooms.add(source);
        while (!notVisitedRooms.isEmpty()) {
            Room room = getMinimum(notVisitedRooms);
            visitedRooms.add(room);
            notVisitedRooms.remove(room);
            for (Room neighbor : getNeighbors(room)) {
                if (getShortestDistance(neighbor) > getShortestDistance(room) + DOORWEIGHT) {
                    distance.put(neighbor, getShortestDistance(room) + DOORWEIGHT);
                    predecessors.put(neighbor, room);
                    notVisitedRooms.add(neighbor);
                }
            }
        }
    }

    private List<Room> getNeighbors(Room room) {
        List<Room> neighbors = new ArrayList<Room>();
        for (Door door : doors) {
            if (door.getSource().equals(room) && !visitedRooms.contains(door.getDestination()) && door.isOpen()) {
                neighbors.add(door.getDestination());
            }
        }
        return neighbors;
    }

    private Room getMinimum(Set<Room> rooms) {
        Room minimum = null;
        for (Room room : rooms) {
            if (minimum == null) {
                minimum = room;
            } else {
                if (getShortestDistance(room) < getShortestDistance(minimum)) {
                    minimum = room;
                }
            }
        }
        return minimum;
    }

    private int getShortestDistance(Room destination) {
        Integer dist = distance.get(destination);
        return (dist == null) ? Integer.MAX_VALUE : dist;
    }

    public LinkedList<Room> getPathToDestionation(Integer targetId) {
        if (!rooms.containsKey(targetId)) {
            throw new RouteFinderException("destination room not found");
        }
        Room target = rooms.get(targetId);
        LinkedList<Room> route = new LinkedList<Room>();
        Room step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        route.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            route.add(step);
        }
        // Put it into the correct order
        Collections.reverse(route);
        return route;
    }

}
