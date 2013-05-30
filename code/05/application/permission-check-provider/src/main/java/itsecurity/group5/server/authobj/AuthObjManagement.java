package itsecurity.group5.server.authobj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//For testing purposes
public class AuthObjManagement {
	
	private List<Integer> users;
	private Map<Integer, List<String>> userZones;
	private List<Integer> terminals;
	private Map<Integer, String> terminalZones;
	private List<String> zones;
	
	public AuthObjManagement(){
		users = new ArrayList<Integer>();
		terminals = new ArrayList<Integer>();
		userZones = new HashMap<Integer, List<String>>();
		terminalZones = new HashMap<Integer, String>();
		zones = new ArrayList<String>();
		initUsers();
		initTerminals();
		initZones();
	}
	
	//User Test Data
	public void initUsers(){
		users.add(1);
		userZones.put(1, new ArrayList<String>(Arrays.asList("Zone1")));
		users.add(2);
		userZones.put(2, new ArrayList<String>(Arrays.asList("Zone1", "Zone2")));
		users.add(3);
		userZones.put(3, new ArrayList<String>(Arrays.asList("Zone1", "Zone2", "Zone3")));
		users.add(4);
		userZones.put(4, new ArrayList<String>(Arrays.asList("Zone1", "Zone2", "Zone3", "Zone4")));
		users.add(5);
		userZones.put(5, new ArrayList<String>(Arrays.asList("Zone1", "Zone2", "Zone3", "Zone4", "Zone5")));
	}
	
	//Terminal Test Data
	public void initTerminals(){
		terminals.add(101);
		terminalZones.put(101, "Zone1");
		terminals.add(102);
		terminalZones.put(102, "Zone2");
		terminals.add(103);
		terminalZones.put(103, "Zone3");
		terminals.add(104);
		terminalZones.put(104, "Zone4");
		terminals.add(105);
		terminalZones.put(105, "Zone5");
	}
	
	//Zone Test Data
	public void initZones(){
		zones.add("Zone1");
		zones.add("Zone2");
		zones.add("Zone3");
		zones.add("Zone4");
		zones.add("Zone5");
	}
	
	public String auhtorizeTerminal(Integer terminal){
		System.out.println("Authorizing Terminal: "+terminal);
		if(terminals.contains(terminal)){
			if(terminalZones.containsKey(terminal)){
				System.out.println("Terminal Auth granted: "+terminalZones.get(terminal));
				return terminalZones.get(terminal);
			}
		}
		return null;
	}
	
	public Boolean authorizeZone(String zone){
		System.out.println("Authorizing Zone: "+zone);
		if(zones.contains(zone)){
			System.out.println("Zone Auth granted");
			return true;
		}
		return false;
	}
	
	public Boolean authorizeUser(Integer user, String zone){
		System.out.println("Authorizing User: "+user+", "+zone);
		if(users.contains(user)){
			if(userZones.containsKey(user)){
				List<String> userZoneList = userZones.get(user);
				if(userZoneList.contains(zone)){
					System.out.println("User Auth granted");
					return true;
				}
			}
		}
		return false;
	}
}
