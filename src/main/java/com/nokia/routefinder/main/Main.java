/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.routefinder.main;

import com.nokia.routefinder.exception.RouteFinderException;
import com.nokia.routefinder.logic.ConstructHouse;
import com.nokia.routefinder.logic.RouteFinder;
import com.nokia.routefinder.model.Door;
import com.nokia.routefinder.model.House;
import com.nokia.routefinder.model.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author david
 */
public class Main {

    public static void main(String[] args) {

        Integer source;
        Integer destination;

        String filePath = null;
        try {
            filePath = args[0];
        } catch (Exception e) {
            throw new RouteFinderException("no xml file path set");
        }

        try {
            source = Integer.parseInt(args[1]);
            destination = Integer.parseInt(args[2]);
        } catch (Exception e) {
            throw new RouteFinderException("destination or soure is not set");
        }

        Map<Integer, Room> rooms = new HashMap<Integer, Room>();
        List<Door> doors = new ArrayList<Door>();
        new ConstructHouse().execute(rooms, doors, filePath);

        if (!rooms.containsKey(source) || !rooms.containsKey(destination)) {
            throw new RouteFinderException("no source or destination found");
        }

        RouteFinder routeFinder = new RouteFinder(new House(rooms, doors));
        routeFinder.initializeSource(source);
        LinkedList<Room> route = routeFinder.getPathToDestionation(destination);

        if (route == null || route.isEmpty()) {
            System.out.println("There is no route.");
            return;
        }

        System.out.print("The shortest route: ");
        for (Room room : route) {
            System.out.print(room + " ");
        }
        System.out.println();

    }
}
