package cutter.math;

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
 * This class contains several methods for sorting number- and text-arrays
 * @author Phil Niehus
 *
 */
public class Sort {
	/**
	 * This method sorts an int[] wether in ascending or descening order, depending on the value of boolean 'ascending'
	 * Warning, use the Mergesort()-method for fast sorting! This method uses Bubblesort which is super slow.
	 * @param numbers an int[] containing unsorted values
	 * @param ascending	Sets the order of the output - true = ascending; false = descending
	 * @return returns a sorted int[] wether ascending or descending
	 */
	public static int[] Bubble(int[] numbers, boolean ascending){
		boolean finnished = false;
		int now = 0;
		String last = array2String(numbers);
		while(!finnished){
			int b = 0;
			int a = numbers[now];
			if(now < numbers.length - 1){
				b = numbers[now + 1];
			}
			if(ascending){
				if(a > b){
					numbers[now] = b;
					numbers[now+1] = a;
				}
			} else{
				if(b > a){
					numbers[now] = b;
					numbers[now+1] = a;
				}
			}
			if(now < numbers.length - 2){
				now++;
			} else{
				String check = array2String(numbers);
				if(check.equals(last)){
					finnished = true;
					return numbers;
				} else{
					now = 0;
					last = check;
				}
			}
		}
		return null;
	}
	private static String array2String(int[] arr){
		String str = "";
		for(int i = 0; i < arr.length; i++){
			str += arr[i] + ",";
		}
		return str;
	}
	/**
	 * This method sorts an int[] with a Mergesort-algorithm which is very fast and stable
	 * @param numbers an int[] that should be sorted
	 * @param ascending an boolean, true = ascending order, false = descending order;
	 * @return The sorted int[]
	 */
	public static int[] Mergesort(int[] numbers, boolean ascending){
		if(numbers.length <= 1) return numbers;
		int ll = numbers.length/2;
		int l2l = ll;
		if(numbers.length-ll != ll) l2l = numbers.length - ll;
		int [] leftList = new int[ll];
		int [] rightList = new int[l2l];
		for(int i = 0; i<numbers.length; i++){
			if(i<leftList.length){
				leftList[i] = numbers[i];
			} else{
				rightList[i-ll] = numbers[i];
			}
		}
		if(leftList.length >1 | rightList.length > 1){
			leftList = Mergesort(leftList, true);
			rightList = Mergesort(rightList, true);
		}
		if(ascending == true){
			return mergeLists(leftList, rightList);
		} else{
			int[] merged = mergeLists(leftList, rightList);
			return turnArray(merged);
		}
	}
	private static int[] mergeLists(int[] left, int[] right){
		List<Integer> leftList = arrayToList(left);
		List<Integer> rightList = arrayToList(right);
		List<Integer> mList = new ArrayList<Integer>();
		while(!leftList.isEmpty() && !rightList.isEmpty()){
			if(leftList.get(0) <= rightList.get(0)){
				mList.add(leftList.get(0));
				leftList.remove(0);
			} else{
				mList.add(rightList.get(0));
				rightList.remove(0);
			}
		}
		while(!leftList.isEmpty()){
			mList.add(leftList.get(0));
			leftList.remove(0);
		}
		while(!rightList.isEmpty()){
			mList.add(rightList.get(0));
			rightList.remove(0);
		}
		return intListToArray(mList);
	}
	/**
	 * This method creates an int[] from a List&lt;Integer&gt;
	 * @param input The list that should be converted to an array
	 * @return an int[]
	 */
	public static int[] intListToArray(List<Integer> input){
		int[] out = new int[input.size()];
		for(int i = 0; i<input.size(); i++){
			out[i] = input.get(i);
		}
		return out;
	}
	/**
	 * This method creates a List&lt;Integer&gt; from an int[]
	 * @param input an int[]
	 * @return a List&lt;Integer&gt;
	 */
	public static List<Integer> arrayToList(int[] input){
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i<input.length; i++){
			l.add(input[i]);
		}
		return l;
	}
	/**
	 * This method puts an int[] in its exact opposite order
	 * @param input an int[]
	 * @return the turned int[]
	 */
	public static int[] turnArray(int[] input){
		int[] turned = new int[input.length];
		int n = 0;
		for(int o = turned.length-1; o>=0; o--){
			turned[o] = input[n];
			n++;
		}
		return turned;
	}
}
