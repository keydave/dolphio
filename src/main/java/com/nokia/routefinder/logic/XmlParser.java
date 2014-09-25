/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.routefinder.logic;

import com.nokia.routefinder.exception.RouteFinderException;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author david
 */
class XmlParser extends DefaultHandler {

    private final Map<Integer, ParsedRoom> parsedRoomMap = new HashMap<Integer, ParsedRoom>();
    private ParsedRoom room = null;
    private ParsedDoor door = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("room")) {
            if (attributes.getValue("id") == null) {
                throw new RouteFinderException("id not set in xml element 'room'");
            }
            try {
                room = new ParsedRoom(Integer.parseInt(attributes.getValue("id")));
            } catch (Exception ex) {
                throw new RouteFinderException(ex.getMessage());
            }
        } else if (qName.equals("door")) {
            if (attributes.getValue("direction") == null || attributes.getValue("open") == null || attributes.getValue("room") == null) {
                throw new RouteFinderException("attributes not properly set in xml element 'room'");
            }
            try {
                door = new ParsedDoor(parseDirection(attributes.getValue("direction")), Integer.parseInt(attributes.getValue("room")), Boolean.valueOf(attributes.getValue("open")));
            } catch (Exception ex) {
                throw new RouteFinderException(ex.getMessage());
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("room")) {
            getParsedRoomMap().put(room.getId(), room);
        } else if (qName.equals("door")) {
            room.getDoors().add(door);
        }
    }

    public Integer parseDirection(String direction) {
        if (direction.equals("north")) {
            return 1;
        }
        if (direction.equals("south")) {
            return -1;
        }
        if (direction.equals("east")) {
            return 2;
        }
        if (direction.equals("west")) {
            return -2;
        }
        throw new RouteFinderException("wrong direction in xml attribute");
    }

    public Map<Integer, ParsedRoom> getParsedRoomMap() {
        return parsedRoomMap;
    }
}
