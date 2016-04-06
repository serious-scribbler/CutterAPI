package com.cutterapi.systemevents;

import java.util.HashMap;
import java.util.List;
/*

Copyright (C) 2015 Phil Niehus

The CutterAPI is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

The CutterAPI is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
/**
 * This class holds the ssids of aivailable and connected wireless networks and the device names of active w-lan adaptors gathered by a WirelessSsidReader
 * @author Phil Niehus
 *
 */
public class NetworkInformation {
	private List<String> nicNameList;
	private HashMap<String, String> connectedSsids = new HashMap<String, String>();
	private List<String> availableNetworks;
	
	/**
	 * Default constructor for NetworkInformation objects
	 * @param nicNameList
	 * @param connectedSsids
	 * @param availableNetworks
	 */
	public NetworkInformation(List<String> nicNameList, HashMap<String, String> connectedSsids, List<String> availableNetworks){
		this.nicNameList = nicNameList;
		this.connectedSsids = connectedSsids;
		this.availableNetworks = availableNetworks;
	}
	
	/**
	 * This method returns a list containing the names of all active wireless network cards
	 * @return
	 */
	public List<String> getActiveWirelessNetworkCardsNames(){
		return nicNameList;
	}
	
	/**
	 * This method returns the ssid of the network the device with the given name is connected to
	 * @param deviceName The name of the selected device (get a list of all device names using {@link #getActiveWirelessNetworkCardsNames() getActiveWirelessNetworkCardsNames} method
	 * @return The ssid of the wireless network connected to the selected wireless interface
	 * @throws Exception Throws an Exception if the selected device is inactive or not present
	 */
	public String getConnectedNetworkSsidByDeviceName(String deviceName) throws Exception{
		if(!connectedSsids.containsKey(deviceName)) throw new Exception("The selected device is inactive or not present!");
		return connectedSsids.get(deviceName);
	}
	
	/**
	 * Retruns a list containing the ssids of all available wifi networks
	 * @return
	 */
	public List<String> getAvailableNetworksSsids(){
		return availableNetworks;
	}
	
}
