package com.cutterapi.tools;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.filechooser.FileSystemView;
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
 * This class is intended to be a "developer's swiss knife" providing simple and helpful functionalities for many purposes
 * @author Phil Niehus
 *
 */
public class MultiTool {
	
	/**
	 * This method returns a screenshot as BufferedImage
	 * @return the screenshot as BufferedImage
	 * @throws Exception
	 */
	public static BufferedImage screenshot() throws Exception{
		return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	}
	
	/**
	 * This method returns a fully formated List of all system drives/partitions, their free and total size and name
	 * @return a List<String> containing the information for every drive and the column headers
	 */
	public static List<String> getPrintReadyDriveSpaceList(){
		List<String> driveList = new ArrayList<String>();
		File[] a = File.listRoots();
		driveList.add("---------------------------------------------------------------------------------------------------------");
		driveList.add("Drive\t\tFree Space\t\t\tDrive Name");
		driveList.add("---------------------------------------------------------------------------------------------------------");
		for(int i = 0; i < a.length; i++){
			String name = FileSystemView.getFileSystemView().getSystemDisplayName(a[i]);
			String space = a[i].getFreeSpace()/1000000 + " of " + a[i].getTotalSpace()/1000000 + " MB";
			String tabs = "\t\t\t";
			if(space.length() > 15){
				tabs = "\t\t";
			}
			driveList.add("" + (a[i].getAbsolutePath()).replace(":\\", "") + "\t\t" + space + tabs + name + "");
		}
		driveList.add("---------------------------------------------------------------------------------------------------------");
		return driveList;
	}
	
	/**
	 * This methods prints the content of a List<String> line by line. This is method is supposed to safe some time on debbuging just by removing the need to write a loop.
	 * @param list
	 */
	public static void printListString(List<String> list){
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
	}
	
	/**
	 * This method adds the currently running Java program to the Windows startup
	 * @throws Exception Throws an Exception when called on non-Windows systems
	 */
	public static void addThisToStartup() throws Exception{
		String jar = getJarPath();
		String startname = "j" + System.currentTimeMillis();
		Preferences p = Preferences.userNodeForPackage(MultiTool.class);
		p.put("startup", startname);
		addJarToStartup(jar, startname);
	}
	
	/**
	 * This method checks wether the currently running program has been added to startup or not
	 * @return
	 * @throws BackingStoreException
	 */
	public static boolean addedToStartup() throws BackingStoreException{
		if(Preferences.userNodeForPackage(MultiTool.class).get("startup", "unset").equals("unset")){
			return false;
		}
		return true;
	}
	
	/**
	 * Removes the currently running program form the windows startup
	 * @throws Exception Throws an Exception when called on non-Windows systems or when the startup file doesn't exist
	 */
	public static void removeThisFromStartup() throws Exception{
		if(addedToStartup()){
			String s = Preferences.userNodeForPackage(MultiTool.class).get("startup", "unset");
			Preferences.userNodeForPackage(MultiTool.class).remove("startup");
			removeJarFromStartup(s);
		} else{
			throw new Exception("Can't find the startup entry!");
		}
	}
	
	/**
	 * This method removes the specified Script from the Windows startup
	 * @param startUpScriptName the name of your startup script without extension
	 * @throws Exception Throws an Exception when called on non-Windows systems or when the startup file doesn't exist
	 */
	public static void removeJarFromStartup(String startupScriptName) throws Exception{
		File f = new File(getAppDataPath() + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\qU4px" + startupScriptName + ".bat");
		if(f.exists()){
			f.delete();
		} else{
			throw new Exception(startupScriptName + ".bat isn't existing!");
		}
	}
	
	/**
	 * This method adds the specific .jar to the <b>Windows</b> startup, using a script called [startUpScriptName].bat
	 * @param programPath The absolute path of the .jar-file which will be added to startup
	 * @param startupScriptName The name of the created startup-script (without extension)
	 * @throws Exception Throws an Exception on non-Windows systems
	 */
	public static void addJarToStartup(String programPath, String startupScriptName) throws Exception{
		String os = System.getProperty("os.name");
		if(os.contains("Windows")){
			String Appdata = getAppDataPath();
			BufferedWriter wr = new BufferedWriter(new FileWriter(Appdata + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\qU4px" + startupScriptName + ".bat"));
			wr.write("@echo off");
			wr.newLine();
			wr.write("javaw -jar " + programPath);
			wr.close();
		} else{
			throw new Exception("StartUp currently only works on Windows!");
		}
	}
	/**
	 * Returns the absolute path of the currently running .jar
	 * @return The absolute path of the running .jar as String
	 */
	public static String getJarPath(){
		return new File(MultiTool.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsoluteFile().toString();
	}
	
	/**
	 * This method helps to get the path of the AppData folder
	 * @return The path to the AppData folder
	 * @throws Exception Throws an Exception if the method is called on a non-Windows system
	 */
	public static String getAppDataPath() throws Exception{
		if(System.getProperty("os.name").contains("Windows")){
			return System.getProperty("user.home")  + "\\AppData\\Roaming";
		}
		throw new Exception("This method is for Windows only!");
	}
	
	/**
	 * This method should be called to kill the blank commandline after automated startup
	 * Don't worry, this doesn't affect any other commandlines, because it kills only the one with the right PID
	 * @throws Exception
	 */
	public static void executeAfterStartup() throws Exception{
		ConsoleExecutor cmd = new ConsoleExecutor(true);
		cmd.execute(new String[]{"WMIC", "PROCESS", "WHERE", "Name=\"cmd.exe\"", "get", "Commandline", ",", "Processid"});
		List<String> out = cmd.getConsoleOutput();
		for(int i = 1; i < out.size(); i++){
			String o = out.get(i);
			String[] s = o.split(" ");
			if(o.contains("Startup\\qU4px")){
				cmd.execute(new String[]{"taskkill", "/PID", s[s.length-1]});
			}
		}
	}
}
