/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.routefinder.logic;

import com.nokia.routefinder.exception.RouteFinderException;
import com.nokia.routefinder.model.Door;
import com.nokia.routefinder.model.Room;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author david
 */
public class ConstructHouse {

    public void execute(Map<Integer, Room> rooms, List<Door> doors, String filePath) {

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            XmlParser xmlParser = new XmlParser();
            File file = new File(filePath);
            if (!file.exists() || !file.canRead()) {
                throw new RouteFinderException("file is missing or is not readable");
            }
            saxParser.parse(file, xmlParser);
            
            Map<Integer, ParsedRoom> parsedRoomMap = xmlParser.getParsedRoomMap();
            
            for (Integer dummyRoomId : parsedRoomMap.keySet()) {
                rooms.put(dummyRoomId, new Room(dummyRoomId));
            }
            
            for (Integer roomId : rooms.keySet()) {
                ParsedRoom prSrc = parsedRoomMap.get(roomId);
                for (ParsedDoor pdSrc : prSrc.getDoors()) {
                    ParsedRoom prDest = parsedRoomMap.get(pdSrc.getRoom());
                    for (ParsedDoor pdDest : prDest.getDoors()) {
                        if (pdDest.getRoom() == prSrc.getId()) {
                            if ((pdDest.getDirection() + pdSrc.getDirection()) == 0) {
                                doors.add(new Door(rooms.get(prSrc.getId()), rooms.get(prDest.getId()), pdSrc.isOpen()));
                            } else {
                                throw new RouteFinderException("door failure");
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException ex) {
            throw new RouteFinderException(ex.getMessage(), ex);
        } catch (SAXException ex) {
            throw new RouteFinderException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new RouteFinderException(ex.getMessage(), ex);
        }
    }

}

class ParsedRoom {

    private final int id;
    private final List<ParsedDoor> doors;

    public ParsedRoom(int id) {
        this.id = id;
        doors = new ArrayList<ParsedDoor>();
    }

    public int getId() {
        return id;
    }

    public List<ParsedDoor> getDoors() {
        return doors;
    }
}

class ParsedDoor {

    private final int direction;
    private final int room;
    private final boolean open;

    public ParsedDoor(int direction, int room, boolean open) {
        this.direction = direction;
        this.open = open;
        this.room = room;
    }

    public int getDirection() {
        return direction;
    }

    public int getRoom() {
        return room;
    }

    public boolean isOpen() {
        return open;
    }
}
