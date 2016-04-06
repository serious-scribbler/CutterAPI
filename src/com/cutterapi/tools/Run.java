package com.cutterapi.tools;

import java.util.ArrayList;
import java.util.List;

import com.cutterapi.systemevents.WirelessSsidReader;

public class Run {
	public static void main(String[] args) {
		try {
			WirelessSsidReader s = new WirelessSsidReader(20000);
			s.startListening();
			for(int i = 0; i < 200000; i++){}
			s.stopListening();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
