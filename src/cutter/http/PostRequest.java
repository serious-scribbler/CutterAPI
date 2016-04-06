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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * This class makes it easy to send POST-requests to a server
 * it extends from the Request class
 * @author Phil Niehus
 *
 */
public class PostRequest extends Request{
	/**
	 * @param url A String that contains the target-url
	 * @param params A String[] containing the names of the parameters
	 * @param values A String[] that contains for the parameters (has to be in the same order like params)
	 * @param mimetype A String containing the MIME-type for the post-request, default is like an html-form and can be used with php
	 * @author Phil Niehus
	 *
	 */
	public PostRequest(String url, String[] params, String[] values, String mimetype){
		super(url, params,values, mimetype);
	}
	/**
	 * @param url A String that contains the target-url
	 * @param params A String[] containing the names of the parameters
	 * @param values A String[] that contains for the parameters (has to be in the same order like params)
	 * @author Phil
	 *
	 */
	public PostRequest(String url, String[] params, String[] values){
		super(url, params,values);
	}
	/**
	 * This method executes the POST-request and saves the servers answer
	 * @throws Exception
	 */
	public void request() throws Exception{
		result = "";
		body = createBody();
		URL httpurl = new URL(url); 
		HttpURLConnection connection = (HttpURLConnection) httpurl.openConnection();
		
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		
		connection.setRequestProperty("Content-Type", mimetype);
		connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
		connection.setRequestProperty("User-Agent", UAS);
		
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(body);
		writer.flush();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		for(String line; (line = reader.readLine()) != null;){
			result = result + line;
		}
	}
	}
