package com.cutterapi.http;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * This is the superclass for
 * {@link com.cutterapi.http.PostRequest}
 * and
 * {@link com.cutterapi.http.GetRequest}
 * This conatins all methods that are used in the specific request-classes except the request()-methods
 * @author Phil Niehus
 *
 */
public class Request {
	String[] params;
	String[] values;
	String url;
	String body = "";
	String result = "";
	String mimetype = "application/x-www-form-urlencoded";
	String UAS = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)";
	
	public Request(){
		
	}
	/**
	 * @param url A String that contains the target-url
	 * @param params A String[] containing the names of the parameters
	 * @param values A String[] that contains for the parameters (has to be in the same order like params)
	 * @param mimetype A String containing the MIME-type for the post-request, default is like an html-form and can be used with php
	 * @author Phil
	 *
	 */
	public Request(String url, String[] params, String[] values, String mimetype){
		this.url = url;
		this.mimetype = mimetype;
		this.params = params;
		this.values = values;
	}
	/**
	 * @param url A String that contains the target-url (Without '?xyz=')
	 * @param params A String[] containing the names of the parameters
	 * @param values A String[] that contains for the parameters (has to be in the same order like params)
	 * @author Phil
	 *
	 */
	public Request(String url, String[] params, String[] values){
		this.url = url;
		this.params = params;
		this.values = values;
	}
	/**
	 * @return The Answer of the Server formated as a String
	 */
	public String getAnswer(){
		return result;
	}
	/**
	 * updates the parameter-names and values
	 * @param params like in the default-constructor
	 * @param values like in the default-constructor
	 * @throws UnsupportedEncodingException
	 */
	public void updateParams(String[] params, String[] values) throws UnsupportedEncodingException{
		this.values = values;
		this.params = params;
		body = createBody();
	}
	/**
	 * Generates the body of any request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String createBody() throws UnsupportedEncodingException{
		body = "";
		for(int i = 0; i<params.length; i++){
			body += params[i] + "=";
			if(values[i] != null ){
				body += URLEncoder.encode(values[i], "UTF-8");
			} else{
				body += "null";
			}
			if(i != params.length - 1){
				body += "&";
			}
		}
		return body;
	}
	/**
	 * This Method changes the UserAgentString that is transmitted to the target
	 * The Default UAS is: Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)
	 * @param UserAgentString
	 */
	public void setUserAgent(String UserAgentString){
		UAS = UserAgentString;
	}
}
