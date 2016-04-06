package com.cutterapi.systemevents;

import java.util.HashMap;
import java.util.List;

public class NetworkInformation {
	
	private List<String> nicNameList;
	private HashMap<String, String> connectedSsids = new HashMap<String, String>();
	private List<String> availableNetworks;
	
	public NetworkInformation(List<String> nicNameList, HashMap<String, String> connectedSsids, List<String> availableNetworks){
		this.nicNameList = nicNameList;
		this.connectedSsids = connectedSsids;
		this.availableNetworks = availableNetworks;
	}
	
	
	
}
