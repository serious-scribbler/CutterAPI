package cutter.math;
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
 * The Random-class contains methods for creating random numbers, strings and booleans.
 * This method has no support for calculation methods!
 * @author Phil Niehus
 *
 */
public class Random {
	/**
	 * This method returns a random Integer between 1 and Integer.MAX_VALUE
	 * @return A random integer
	 */
	public static int randomInt(){
		return randomInt(1, Integer.MAX_VALUE);
	}
	/**
	 * This method calculates a random integer between minValue and maxValue
	 * @param minValue The minimum value for the generated Integer
	 * @param maxValue The maximum value for the generated Integer
	 * @return A random Integer between minValue and maxValue
	 */
	public static int randomInt(int minValue, int maxValue){
		if(maxValue != Integer.MAX_VALUE) maxValue += 1;
		int rnd = (int) (Math.random() * (maxValue - minValue) + minValue);
		return rnd;
	}
	/**
	 * This method returns a random Double between 0 and Double.MAX_VALUE
	 * @return A random integer
	 */
	public static double randomDouble(){
		return randomDouble(0.0, Double.MAX_VALUE);
	}
	/**
	 * This method calculates a random Double between minValue and maxValue
	 * @param minValue The minimum value for the generated Double
	 * @param maxValue The maximum value for the generated Double
	 * @return A random Double between minValue and maxValue
	 */
	public static double randomDouble(double minValue, double maxValue){
		double rnd = Math.random() * (maxValue - minValue) + minValue;
		return rnd;
	}
	/**
	 * This method generates a random Boolean
	 * @return A random Boolean
	 */
	public static Boolean randomBool(){
		int rnd = randomInt(0, 100);
		if(rnd<=50){
			return true;
		} else{
			return false;
		}
	}
	/**
	 * This method generates a random String with the selected length, using only ASCII-characters
	 * @param length The length of the random String between 0 and Integer.MAX_VALUE
	 * @return
	 * @throws Exception when the given length is smaller than 1
	 */
	public static String randomString(int length) throws Exception{
		String rnd = "";
		if(length>0){
			for(int i = 0; i<length; i++){
				rnd += (char) randomInt(33, 126);
			}
		} else{
			throw new Exception("The length must be at least 1!");
		}
		return rnd;
	}
}
