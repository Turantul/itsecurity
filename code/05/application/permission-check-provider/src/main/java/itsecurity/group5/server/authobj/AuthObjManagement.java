package itsecurity.group5.server.authobj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//For testing purposes
public class AuthObjManagement {
    private List<Integer> users;
    private Map<Integer, Byte[]> userIrisData;
    private Map<Integer, List<Integer>> userZones;
    private List<Integer> terminals;
    private Map<Integer, Integer> terminalZones;
    private List<Integer> zones;
    private List<Integer> objects;
    private Map<Integer, List<Integer>> objectZones;
    private Map<Integer, Integer> zoneLevel;

    public AuthObjManagement() {
        users = new ArrayList<Integer>();
        userIrisData = new HashMap<Integer, Byte[]>();
        terminals = new ArrayList<Integer>();
        userZones = new HashMap<Integer, List<Integer>>();
        terminalZones = new HashMap<Integer, Integer>();
        zones = new ArrayList<Integer>();
        objects = new ArrayList<Integer>();
        objectZones = new HashMap<Integer, List<Integer>>();
        zoneLevel = new HashMap<Integer, Integer>();
        initTerminals();
        initZones();
        initObjects();
    }

    // Terminal Test Data
    private void initTerminals() {
        terminals.add(101);
        terminalZones.put(101, 201);
        terminals.add(102);
        terminalZones.put(102, 202);
        terminals.add(103);
        terminalZones.put(103, 203);
        terminals.add(104);
        terminalZones.put(104, 204);
        terminals.add(105);
        terminalZones.put(105, 205);
    }

    // Zone Test Data
    private void initZones() {
        // Zone level 1 no iris data
        // Zone level 2 iris data
        zones.add(201);
        zoneLevel.put(201, 1);
        zones.add(202);
        zoneLevel.put(202, 1);
        zones.add(203);
        zoneLevel.put(203, 2);
        zones.add(204);
        zoneLevel.put(204, 2);
        zones.add(205);
        zoneLevel.put(201, 1);
    }

    // Secure Object Test Data
    private void initObjects() {
        objects.add(301);
        objectZones.put(301, new ArrayList<Integer>(Arrays.asList(201, 202, 203, 204)));
        objects.add(305);
        objectZones.put(305, new ArrayList<Integer>(Arrays.asList(205)));
    }

    public Integer auhtorizeTerminal(Integer terminal) {
        System.out.println("Authorizing Terminal: " + terminal);
        if (terminals.contains(terminal)) {
            if (terminalZones.containsKey(terminal)) {
                System.out.println("Terminal Auth granted: " + terminalZones.get(terminal));
                return terminalZones.get(terminal);
            }
        }
        return null;
    }

    public Boolean authorizeZone(Integer object, Integer zone) {
        System.out.println("Authorizing Zone: " + zone + " - " + object);
        if (objects.contains(object)) {
            if (zones.contains(zone)) {
                if (objectZones.get(object).contains(zone)) {
                    System.out.println("Zone Auth granted");
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean authorizeUser(Integer user, Integer zone, Byte[] irisData) {
        System.out.println("Authorizing User: " + user + ", " + zone);
        if (users.contains(user)) {
            if (userZones.containsKey(user)) {
                List<Integer> userZoneList = userZones.get(user);
                if (userZoneList.contains(zone)) {
                    if (zoneLevel.get(zone) == 2) {
                        if (verifyIrisData(user, irisData)) {
                            return true;
                        }
                    } else if (zoneLevel.get(zone) == 1) {
                        if (true) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    private Boolean verifyIrisData(Integer user, Byte[] irisData) {
        if (userIrisData.containsKey(user)) {
            if (userIrisData.get(user).equals(irisData)) {
                return true;
            }
        }
        return false;
    }
}
