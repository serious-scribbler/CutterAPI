package cutter.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
 * This class makes it possible to execute console commands and read the output
 * @author Phil Niehus
 *
 */
public class ConsoleExecutor {
	List<String> consoleOutput = new ArrayList<String>();
	boolean reset = false;
	/**
	 * 
	 * @param resetOutputHistoryBeforeExecution this Boolean defines wether the history is cleared before the execution of a command or not
	 */
	public ConsoleExecutor(boolean resetOutputHistoryBeforeExecution){
		reset = resetOutputHistoryBeforeExecution;
	}
	
	/**
	 * This method executes the given command with the given parameters and saves the console output
	 * Keep in mind, that even a comma is a new parameter for example:
	 * <code>execute(new String[]{"WMIC", "PROCESS", "get", "Commandline", ",", "Processid"})</code>
	 * @param command the Command and parameters the command will be executed with (each parameter as a new index)
	 * @throws Exception
	 */
	public void execute(String[] command) throws Exception{
		if(reset){
			resetOutputHistory();
		}
		ProcessBuilder builder;
		builder = new ProcessBuilder(command);
		builder.redirectErrorStream(true);
		Process process = builder.start();
		process.getOutputStream().close(); 
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while((line = consoleReader.readLine()) != null){
			consoleOutput.add(line);
		}
	}
	
	/**
	 * This method executes the given command and saves the console output
	 * WARNING: If you want to add some parameters to your command, call the execute method like this:
	 * <code>execute(new String[]{"command", "parameter", "parameter"});</code> 
	 * @param command the command that will be executed
	 * @throws Exception
	 */
	public void execute(String command) throws Exception{
		execute(command);		
	}
	
	/**
	 * This method clears the output history	
	 */
	public void resetOutputHistory(){
		consoleOutput = new ArrayList<String>();
	}
	
	/**
	 * Enables the reset of the output history before the execution of a command
	 */
	public void enableAutomaticHistoryReset(){
		reset = true;
	}
	
	/**
	 * Disables the reset of the output history before the execution of a command
	 */
	public void disableAutomaticHistoryReset(){
		reset = false;
	}
	
	/**
	 * This method returns the console output since the last reset of the console history
	 * @return the output history since the last reset as List<String>
	 */
	public List<String> getConsoleOutput(){
		return consoleOutput;
	}
}
