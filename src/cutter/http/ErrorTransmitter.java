package cutter.http;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
/**
 * This class makes it easy to send StackTraces to your server
 * @author Phil Niehus
 *
 */
public class ErrorTransmitter {
	PostRequest rq;
	String url;
	/**
	 * This class has the capability to send bug-reports (StackTraces, OS-info and Java-version-info) to your personal bugtracker<br>You need our bug-tracker.php-kit to make use of this feature (its free)
	 * @param url The URL where your bug-tracker.php or your own implementation of this is located
	 * @param programName This variable will show up in the web-interface to difference between reports of multiple different programs
	 */
	public ErrorTransmitter(String url, String programName){
		this.url = url;
		rq = new PostRequest(url, new String[]{"OS", "jv"}, new String[]{System.getProperty("os.name"), System.getProperty("java.version")});
		rq.setUserAgent(programName);
	}
	/**
	 * Transmits a Throwable (for example a StackTrace) to your defined bug-tracker.php (or any similar linked application).<br>>You can find a tutorial for this at our tutorials-page
	 * @param error The Throwable, that will be transmitted
	 */
	public void transmitError(Throwable error){
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			error.printStackTrace(pw);
			String err = sw.toString();
			rq.updateParams(new String[]{"OS","jv", "ERR"}, new String[]{System.getProperty("os.name"), System.getProperty("java.version"), err});
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			rq.request();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
