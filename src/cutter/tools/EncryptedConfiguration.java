package cutter.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
/*
 *
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
 * This class makes it possible to create, edit and read encrypted configuration files. Its encryption is very simple and would not keep experienced hackers from manipulating your configuration, but it will make it impossible to manipulate for normal users.
 * 
 */
public class EncryptedConfiguration {
	byte[] secret = new byte[256];
	List<String> keys = new ArrayList<String>();
	List<String> values = new ArrayList<String>();
	String pID = "";
	String configName = "";
	File out = null;
	/**
	 * Creates an EncryptedConfiguration with the given parameters
	 * @param programIdentifier A unique name or ID for your software as string.
	 * @param configurationName The name of this configuration (this makes it possible to have multiple configuration files for the same piece of software).
	 * @param passphrase The passphrase to encrypt your configuration with (maximum length 256 Bytes)
	 * @throws Exception
	 */
	public EncryptedConfiguration(String programIdentifier, String configurationName, String passphrase) throws Exception{
		pID = programIdentifier;
		configName = configurationName;
		secret = passphrase.getBytes(Charset.forName("UTF-8"));
		defineFilePath();
		reloadConfiguration();
	}
	/**
	 * Creates an EncryptedConfiguration with the given parameters
	 * @param programIdentifier A unique name or ID for your software as string.
	 * @param configurationName The name of this configuration (this makes it possible to have multiple configuration files for the same piece of software).
	 * @param passphrase The secret key to encrypt your configuration with. Use the getUserDependendKeyPhrase()-method if you do not know what to choose.
	 * @throws Exception
	 */
	public EncryptedConfiguration(String programIdentifier, String configurationName, byte[] secretKey) throws Exception{
		pID = programIdentifier;
		configName = configurationName;
		secret = secretKey;
		defineFilePath();
		reloadConfiguration();
	}
	/**
	 * Creates an EncryptedConfiguration and saves it in the given file
	 * @param configurationFile This needs to be a full filepath with filename
	 * @param passphrase The passphrase to encrypt your configuration with
	 */
	public EncryptedConfiguration(File configurationFile, String passphrase){
		secret = passphrase.getBytes(Charset.forName("UTF-8"));
		out = configurationFile;
		reloadConfiguration();
	}
	/**
	 * Creates an EncryptedConfiguration and saves it in the given file
	 * @param configurationFile This needs to be a full filepath with filename
	 * @param secretKey The secret key to encrypt your configuration with. Use the getUserDependendKeyPhrase()-method if you do not know what to choose.
	 */
	public EncryptedConfiguration(File configurationFile, byte[] secretKey){
		secret = secretKey;
		out = configurationFile;
		reloadConfiguration();
	}
	/**
	 * This method returns a secret key depending on the user's name and os.
	 * @return The secret key as byte[]
	 */
	public static byte[] getUserDependentKeyPhrase(){
		String props = "" + System.getProperty("os.arch") + System.getProperty("os.name") + System.getProperty("user.name");
		byte[] key = props.getBytes(Charset.forName("UTF-8"));
		for(int i = 0; i < key.length; i++){
			key[i] = (byte) (key[i] >> 1);
		}
		return key;
	}
	private void defineFilePath() throws Exception{
		if(System.getProperty("os.name").contains("Windows")){
			out = new File(MultiTool.getAppDataPath() + "\\" + pID);
		} else{
			out = new File(System.getProperty("user.home") + "/." + pID);
		}
	}
	/**
	 * This method encrypts and saves the generated configuration
	 * @throws Exception 
	 */
	public void save() throws Exception{
		//boolean success = out.mkdirs();
		//System.out.println(success);
		out.mkdirs();
		File write = new File(out.getAbsolutePath() + "\\" + configName + ".cfg");
		byte[] encrypted = encrypt(generateText().getBytes(Charset.forName("UTF-8")), secret);
		FileOutputStream output = new FileOutputStream(write);
		try{
			output.write(encrypted);
		} finally{
			output.close();
		}
	}
	/**
	 * Use this method to reload your configuration from file. Discards any changes.
	 */
	public void reloadConfiguration(){
		File read = new File(out.getAbsolutePath() + "\\" + configName + ".cfg");
		if(read.exists()){
			try {
				FileInputStream inputStream = new FileInputStream(read);
				byte[] encrypted = new byte[(int)read.length()];
				inputStream.read(encrypted);
				inputStream.close();
				parse(new String(decrypt(encrypted, secret)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This method defines a new key and sets its value to the given String. ':' are removed from the input!
	 * @param keyName
	 * @param value
	 * @throws Exception Throws an Exception if the selected key is already defined
	 */
	public void defineKey(String keyName, String value) throws Exception{
		keyName = keyName.replace(":", "");
		value = value.replace(":", "");
		if(isPresent(keyName)){
			throw new Exception("The key '" + keyName + "' is already existing in this configuration");
		}
		keys.add(keyName);
		values.add(value);
	}
	/**
	 * This method changes the value of an existing key. ':' are removed from the input!
	 * @param keyName
	 * @param newValue
	 * @throws Exception Throws an Exception if the selected key doesn't exist
	 */
	public void changeValue(String keyName, String newValue) throws Exception{
		int index = keyCheck(keyName);
		newValue = newValue.replace(":", "");
		values.set(index, newValue);
	}
	/**
	 * This method returns the value of the selected key
	 * @param keyName
	 * @return
	 * @throws Exception Throws an Exception if the selected key doesn't exist
	 */
	public String getValue(String keyName) throws Exception{
		int index = keyCheck(keyName);
		return values.get(index);
	}
	private int keyCheck(String keyName) throws Exception{
		int index = findKey(keyName);
		if(index == -1){
			throw new Exception("The key '" + keyName + "' doesn't exist!");
		}
		return index;
	}
	/**
	 * Removes an existing key from the configuration
	 * @param keyName
	 * @throws Exception Throws an Exception if the selected key doesn't exist
	 */
	public void removeKey(String keyName) throws Exception{
		int index = keyCheck(keyName);
		values.remove(index);
		keys.remove(index);
	}
	/**
	 * This method can be used to check if a key has been defined in the given configuration
	 * @param key
	 * @return
	 */
	public boolean isPresent(String keyName){
		return (findKey(keyName) != -1) ? true : false;
	}
	//Detects the index of a key
	private int findKey(String key){
		for(int i = 0; i < keys.size(); i++){
			if(keys.get(i).equals(key)){
				return i;
			}
		}
		return -1;
	}
	private String generateText(){
		StringBuilder stb = new StringBuilder();
		for(int i = 0; i < keys.size(); i++){
			stb.append(keys.get(i) + ":" + values.get(i) + "\n");
		}
		return stb.toString();
	}
	private void parse(String input) throws Exception{
		String[] in = input.split("\n");
		for(int i = 0; i < in.length; i++){
			String[] read = in[i].split(":");
			if(read.length < 2){
				throw new Exception("ERROR 3: Invalid configuration syntax!");
			}
			if(read.length > 2){
				throw new Exception("ERROR 2: Invalid value syntax!");
			}
			try {
				defineKey(read[0], read[1]);
			} catch (Exception e) {
				throw new Exception("ERROR 1: Duplicated key!");
			}
		}
	}
	private byte[] encrypt(byte[] input, byte[] secretKeyPhrase) throws Exception{
		byte elongator;
		if(secretKeyPhrase.length > 256){
			throw new Exception("Secret keys can not be longer than 256 bytes!");
		}
		int difference = (input.length % secretKeyPhrase.length);
		if((input.length + difference) % secretKeyPhrase.length == 0){ //Anzahl der Zerobytes berechnen, wird zum Entschlüsseln benötigt
			elongator = (byte) difference;
		} else{
			elongator = (byte) (secretKeyPhrase.length - difference);
		}
		int longer = (elongator < 0) ? elongator + 256: elongator;
		byte[] zeropad = new byte[input.length + longer];
		byte[] result = new byte[zeropad.length];
		System.arraycopy(input, 0, zeropad, 0, input.length);
		result = doXor(zeropad, secretKeyPhrase);
		result = doXor(result, convert(secretKeyPhrase));
		byte[] output = new byte[zeropad.length + 1];
		output[0] = elongator;
		System.arraycopy(result, 0, output, 1, result.length);
		return output;
	}
	private byte[] decrypt(byte[] input, byte[] secretKeyPhrase) throws Exception{
		if((secretKeyPhrase.length > 256) | ((input.length-1) % secretKeyPhrase.length != 0)){
			throw new Exception("The secret Key you entered can't be used to decrypt the input!");
		}
		byte elongator = input[0];
		int longer = (elongator < 0) ? elongator + 256: elongator;
		byte[] zeropad = new byte[input.length - 1];
		byte[] result = new byte[zeropad.length];
		System.arraycopy(input, 1, zeropad, 0, zeropad.length);
		result = doXor(zeropad, convert(secretKeyPhrase));
		result = doXor(result, secretKeyPhrase);
		byte[] output = new byte[result.length - longer];
		System.arraycopy(result, 0, output, 0, output.length);
		return output;
	}
	private byte[] doXor(byte[] zeropad, byte[] secretKeyPhrase){
		byte[] result = new byte[zeropad.length];
		int sec = 0;
		for(int i = 0; i<zeropad.length; i++){
			if(sec == (secretKeyPhrase.length - 1)) sec = 0;
			result[i] = (byte) (zeropad[i] ^ secretKeyPhrase[sec]);
			sec++;
		}
		return result;
	}
	private byte[] convert(byte[] input){
		byte[] output = new byte[input.length];
		for(int i = 0; i<input.length; i++){
			output[i] = (byte) (input[i] >> 1);
		}
		return output;
	}
}
