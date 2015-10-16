package cutter.forensics.text;
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
import java.util.List;
/**
 * This Object stores a list of Strings especially NGramms and connects an Integer with them (typically their quantity in a text, sentence or string)
 * @author Phil Niehus
 *
 */
public class NGrammList {
	
	List<String> ngramm = new ArrayList<String>();
	List<Integer> cnt = new ArrayList<Integer>();
	/**
	 * 
	 * @param ngramm A list containing different Strings
	 * @param cnt A list containing integers connected to strings (if "hello" should be 215, index 0 of ngramm should be "hello" and index 0 of cnt should be 215)
	 * @throws Exception 
	 */
	public NGrammList(List<String> ngramm, List<Integer> cnt) throws Exception{
		if(ngramm.size() != cnt.size()){
			throw new Exception("WRONG INPUT! ngramm is longer/shorter than cnt!");
		}
		this.ngramm = ngramm;
		this.cnt = cnt;
	}
	/**
	 * Constructor for an empty NGrammList
	 */
	public NGrammList(){
		
	}
	/**
	 * 
	 * @return The internally stored List<Integer>
	 */
	public List<Integer> getCnt(){
		return cnt;
	}
	/**
	 * The internally stored List<String>
	 * @return
	 */
	public List<String> getNGramm(){
		return ngramm;
	}
	
	//Returns the Index of a item
	private int getItemIndex(String item) throws Exception{
		for(int i = 0; i<ngramm.size(); i++){
			if(ngramm.get(i).equals(item)){
				return i;
			}
		}
		throw new Exception("Item not found!");
	}
	/**
	 * Returns the Value for the given String
	 * @param item
	 * @return The Value for the given item
	 * @throws Exception Throws an Exception if the selected item wasn't found.
	 */
	public int getValueForItem(String item) throws Exception{
			return cnt.get(getItemIndex(item));
	}
	/**
	 * Adds an entry to the NGrammList
	 * @param item The item to add
	 * @param Value the value for the added item
	 */
	public void addItem(String item, int value){
		ngramm.add(item);
		cnt.add(value);
	}
	
	
	/**
	 * Adds 1 to the value of the given Index
	 * @param item The value of this item will be increased by 1
	 * @throws Exception Throws an Exception if the selected item was not found.
	 */
	public void addTo(String item) throws Exception{
		int index = getItemIndex(item);
		cnt.set(index, cnt.get(index)+1);
	}
	
	/**
	 * This methods changes the value of a selected item. The first matching item is selected if the NGrammList contains duplicated items.
	 * @param item The value of this item will be changed to the given value
	 * @param value
	 * @throws Exception Throws an Exception if the selected item doesn't exist.
	 */
	public void setItemValue(String item, int value) throws Exception{
		cnt.set(getItemIndex(item), value);
	}
	
	/**
	 * This method checks wether a specific NGramm already exists or not
	 * @param item
	 * @return
	 */
	public boolean isSet(String item){
		for(int i = 0; i<ngramm.size(); i++){
			if(ngramm.get(i).equals(item)){
				return true;
			}
		}
		return false;
	}
}
