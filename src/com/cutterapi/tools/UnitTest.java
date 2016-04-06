package com.cutterapi.tools;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements unit tests for java methods. Its possible to test multiple types within the same UnitTest
 * @author Phil Niehus
 * Copyright (C) 2015 Phil Niehus
 */
public class UnitTest {
	private int testCount = 0;
	private String testName = "Unit Test";
	private ArrayList actualResults = new ArrayList();
	private ArrayList expectedResults = new ArrayList();
	private List<Boolean> passed = new ArrayList<Boolean>();
	int failedTests = 0;
	int passedTests = 0;
	
	/**
	 * The default constructor for a UnitTest
	 */
	public UnitTest(){
		
	}
	
	/**
	 * This constructor creates a UnitTest with the given String as name.
	 * Names are only used in {@link com.cutterapi.tools.TestReport TestReports}.
	 * @param testName
	 */
	public UnitTest(String testName){
		this.testName = testName;
	}
	
	/**
	 * This method sets the name of the UnitTest to the given String
	 * @param testName
	 */
	public void setTestName(String testName){
		this.testName = testName;
	}
	
	/**
	 * This method can be used to evaluate multiple results. All objects contained in the ArrayList should however<br>be instances of the same class.<br>This method supports all basic types that are available in java.
	 * @param actual An ArrayList of all actual values
	 * @param expected An ArrayList of expected values
	 */
	public void evaluateMultiple(ArrayList actual, ArrayList expected){
		if(actual.size() != expected.size() || !(actual.get(0).getClass().equals(expected.get(0).getClass()))){
			System.err.println("ERROR: Incorrect INPUT!");
			return;
		}
		Object res = actual.get(0);
		if (res.getClass().equals(Integer.class) ){
			for(int i = 0; i < actual.size(); i++){
				evaluate((int) actual.get(i), (int) expected.get(i));
			}
		} else if(res.getClass().equals(Float.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((float) actual.get(i), (float) expected.get(i));
			}
		} else if(res.getClass().equals(String.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((String) actual.get(i), (String) expected.get(i));
			}
		} else if(res.getClass().equals(Byte.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((byte) actual.get(i), (byte) expected.get(i));
			}
		} else if(res.getClass().equals(Boolean.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((boolean) actual.get(i), (boolean) expected.get(i));
			}
		} else if(res.getClass().equals(Double.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((double) actual.get(i), (double) expected.get(i));
			}
		} else if(res.getClass().equals(Long.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((long) actual.get(i), (long) expected.get(i));
			}
		} else if(res.getClass().equals(Short.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((short) actual.get(i), (short) expected.get(i));
			}
		} else if(res.getClass().equals(Character.class)){
			for(int i = 0; i < actual.size(); i++){
				evaluate((char) actual.get(i), (char) expected.get(i));
			}
		} else{
			System.err.println("ERROR, unknown type in" + getTestName() + "-UnitTest. Called from:");
			StackTraceElement[] trace = Thread.currentThread().getStackTrace();
			for(int o = 2; o < trace.length; o++){
				System.err.println(trace[o]);
			}
		}
		
	}
	
	/**
	 * This method returns the name of the UnitTest
	 * @return
	 */
	public String getTestName(){
		return testName;
	}
	
	/**
	 * This method tests wether an actual String equals an expected String
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(String actualValue, String expectedValue){
		if(actualValue.equals(expectedValue)){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		
		testCount++;
	}
	
	/**
	 * This method tests wether an actual int equals an expected int
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(int actualValue, int expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		
		testCount++;
	}
	
	/**
	 * This method tests wether an actual float equals an expected float
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(float actualValue, float expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		
		testCount++;
	}
	
	/**
	 * This method tests wether an actual long equals an expected long
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(long actualValue, long expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		
		testCount++;
	}
	
	/**
	 * This method tests wether an actual byte equals an expected byte
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(byte actualValue, byte expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		testCount++;
	}

	/**
	 * This method tests wether an actual short equals an expected short
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(short actualValue, short expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		testCount++;
	}

	/**
	 * This method tests wether an actual double equals an expected double
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(double actualValue, double expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		testCount++;
	}
	
	/**
	 * This method tests wether an actual boolean equals an expected boolean
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(boolean actualValue, boolean expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		testCount++;
	}
	
	/**
	 * This method tests wether an actual char equals an expected char
	 * @param actualValue
	 * @param expectedValue
	 */
	public void evaluate(char actualValue, char expectedValue){
		if(actualValue == expectedValue){
			passed.add(true);
			passedTests++;
		} else{
			passed.add(false);
			failedTests++;
		}
		addResults(actualValue, expectedValue);
		testCount++;
	}
	
	//Helper function for the result list
	private void addResults(Object actual, Object expected){
		actualResults.add(actual);
		expectedResults.add(expected);
	}
	
	/**
	 * This method returns the ArrayList of actual results
	 * @return
	 */
	public ArrayList getActualResults(){
		return actualResults;
	}
	
	/**
	 * This method returns the ArrayList of expected results
	 * @return
	 */
	public ArrayList getExpectedResults(){
		return expectedResults;
	}
	
	/**
	 * This method returns the list with the information wether a test was passed or not
	 */
	public List<Boolean> getPassedList(){
		return passed;
	}
}
