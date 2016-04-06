package com.cutterapi.tools;

import java.util.ArrayList;
import java.util.List;

import com.cutterapi.systemevents.NetworkInformation;
import com.cutterapi.systemevents.SsidListener;
import com.cutterapi.systemevents.WirelessSsidReader;

public class Run {
	
	public static void main(String[] args) {
		String last = "none";
		try {
			WirelessSsidReader s = new WirelessSsidReader(2500);
			s.addSsidListener(new SsidListener() {
				
				@Override
				public void onSsidInfoUpdate(NetworkInformation update) {
					if(last.equals("none")){
						try {
							System.out.println(update.getConnectedNetworkSsidByDeviceName(update.getActiveWirelessNetworkCardsNames().get(0)));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			});
			s.startListening();
			long a = 129;
			while(true){
				a = a*(a%123);
				System.out.println(a);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
