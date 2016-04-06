package com.cutterapi.systemevents;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import com.cutterapi.tools.ConsoleExecutor;

public class WirelessSsidReader {
	protected int updateInterval = 5000;
	private boolean keepRunning = false;
	private Thread reader = new Thread(new WirelessReader());
	private List<String> interfaceNames = new ArrayList<String>();
	
	/**
	 * The WirelessSsidReader continuously reads information about the available and connected wireless networks ssids
	 * This is currently only working on windows!
	 * @param updateInterval The interval to read the ssid information
	 * @throws Exception Throws an Exception when executed on unsupported systems
	 */
	public WirelessSsidReader(int updateInterval) throws Exception{
		String os = System.getProperty("os.name");
		if(!os.contains("Windows")){
			throw new Exception("This feature is currently only available for Windows!");
		}
		this.updateInterval = updateInterval;
		createWirelessNetworkList();
	}
	
	//Creates a list of wireless network adapters
	private void createWirelessNetworkList(){
		try {
			Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
			for(NetworkInterface intface : Collections.list(nics)){
				if(!intface.isVirtual() && intface.isUp()){
					if(intface.getName().contains("wlan")){
						interfaceNames.add(intface.getDisplayName());
					}
				}
			}
		} catch (SocketException e1) {
			System.err.println("Network error");
			e1.printStackTrace();
			
		}
	}
	/**
	 * Starts listening for ssid changes
	 */
	public void startListening(){
		if(reader.isAlive()) return;
		keepRunning = true;
		reader.start();
		
	}
	
	/**
	 * Check if the WirelessSsidReader is listening for ssid information
	 * @return The WirelessSsidReaders listening status
	 */
	public boolean isListening(){
		return reader.isAlive();
	}
	
	/**
	 * Stops listening for ssid changes
	 */
	public void stopListening(){
		if(!reader.isAlive()) return;
		keepRunning = false;
	}
	
	/**
	 * Sets the update interval for the ssid information
	 * @param interval
	 */
	public void setUpdateInterval(int interval){
		updateInterval = interval;
	}
	
	//Send updated informations to SsidListeners
	private void networkUpdate(NetworkInformation info){
		//inform listeners
	}
	
	//Reads the network information
	private class WirelessReader implements Runnable{
		HashMap<String, String> connectedSsids = new HashMap<String, String>();
		List<String> ssids = new ArrayList<String>();
		ConsoleExecutor executor = new ConsoleExecutor(true);
		
		@Override
		public void run() {
			while(keepRunning){
				try {
					executor.execute(new String[]{"netsh", "wlan", "show", "interfaces"});
					List<String> out = executor.getConsoleOutput();
					String net = "undifined";
					for(String s : out){
						for(String name : interfaceNames){
							if(s.contains(name)){
								net = name;
								break;
							}
						}
						if(s.contains("SSID") && !s.contains("BSSID")){
							String nssid = s.split(":")[1];
							if(nssid.charAt(0) == ' ') nssid = nssid.substring(1, nssid.length());
							connectedSsids.put(net, nssid);
							net = "undefined";
						}
					}
					executor.execute(new String[]{"netsh", "wlan", "show", "networks"});
					out = executor.getConsoleOutput();
					for(String s : out){
						if(s.contains("SSID")){
							String nssid = s.split(":")[1];
							if(nssid.charAt(0) == ' ') nssid = nssid.substring(1, nssid.length());
							if(!nssid.equals("")){
								ssids.add(nssid);
							}
						}
					}
					networkUpdate(new NetworkInformation(interfaceNames, connectedSsids, ssids));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Thread.sleep((long) updateInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
	
}
