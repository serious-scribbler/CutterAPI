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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for ngramm-based text processing
 * @author Phil Niehus
 * 
 */
public class NGramm {
	/**
	 * This method is capable of calculating the similarity of two texts based on a 2,4 and 7-ngramm-analysis
	 * This is currently not working good, so i would recommend using the compare contents method
	 * @param text1 First text
	 * @param text2 Second text
	 * @param filterChars Chars contained in this String will be removed from the texts before creating the ngramms
	 * @return The similarity between the two texts as a percentage
	 * @throws Exception 
	 */
	public static double getTextSimilarity(String text1, String text2, String filterChars) throws Exception{
		text1 = filter(text1, filterChars);
		text2 = filter(text2, filterChars);
		text1 = text1.toLowerCase();
		text2 = text2.toLowerCase();
		String[] t1 = text1.split(" ");
		String[] t2 = text2.split(" ");
		t1 = addFiller(t1);
		t2 = addFiller(t2);
		text1 = backToString(t1);
		text2 = backToString(t2);
		List<String> n2_1 = ngramm(text1, 2);
		List<String> n2_2 = ngramm(text2, 2);
		
		List<String> n4_1 = ngramm(text1, 4);
		List<String> n4_2 = ngramm(text2, 4);
		
		List<String> n7_1 = ngramm(text1, 7);
		List<String> n7_2 = ngramm(text2, 7);
				
		NGrammList n2_a = count(n2_1);
		NGrammList n2_b = count(n2_2);
		
		NGrammList n4_a = count(n4_1);
		NGrammList n4_b = count(n4_2);
		
		NGrammList n7_a = count(n7_1);
		NGrammList n7_b = count(n7_2);
		
		n2_a = sort(n2_a);
		n2_b = sort(n2_b);
		
		n4_a = sort(n4_a);
		n4_b = sort(n4_b);
		
		n7_a = sort(n7_a);
		n7_b = sort(n7_b);
		
		double p2 = calcPercentage(n2_a, n2_b, n2_1.size(), n2_2.size());
		double p4 = calcPercentage(n4_a, n4_b, n4_1.size(), n4_2.size());
		double p7 = calcPercentage(n7_a, n7_b, n7_1.size(), n7_2.size());
		//System.out.println("2-Gramm: " + p2 + " 4-Gramm: " + p4 + " 7-Gramm: " +p7);
		return ((p2+p4+p7)/3)*100;
	}
	
	//Nachnamen.txt und Vornamen.txt werden aus dem Package geladen
	/**
	 * Searches for all names in a text (Currently only given names or given names+last names). Detects up to 1 billion combinations of names
	 * @param text The text to check for names.
	 * @return A List<String> containing all the names which were found in the text
	 * @throws Exception
	 */
	public static List<String> getAllNames(String text) throws Exception{
		List<String> preNames = new ArrayList<String>();
		List<String> famNames = new ArrayList<String>();
		String s = "";
		try {
			Class c = NGramm.class;
			BufferedReader r = new BufferedReader(new InputStreamReader(c.getResourceAsStream("Vornamen.txt")));
			while((s = r.readLine()) != null){
				if(s.length() >= 2){
					if(s.charAt(s.length()-1) == ' '){
						s = s.substring(0, s.length()-2);
					}
				}
				s = filter(s);
				preNames.add(s);
			}
			r.close();
			s = "";
			r = new BufferedReader(new InputStreamReader(c.getResourceAsStream("Nachnamen.txt")));
			while((s = r.readLine()) != null){
				if(s.length() >= 2){
					if(s.charAt(s.length()-1) == ' '){
						s = s.substring(0, s.length()-2);
					}
				}
				s = filter(s);
				famNames.add(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("Internal Error! Try again! If this error occurs multiple times, please contact the developer!");
		}
		text = filter(text);
		String[] t = text.split(" ");
		List<String> names = new ArrayList<String>();
		for(int i = 0; i<t.length; i++){
			if(preNames.size() != 0 && famNames.size()!= 0){
				String pnom = "";
				String fnom ="";
				if(Name(t[i], preNames)){
					pnom = t[i];
					if(i+1<t.length){
						if(Name(t[i+1], famNames)){
							fnom = t[i+1];
							i++;
						}
						names.add(pnom + placeholder(pnom, fnom) + fnom);
					}
				} else{
					if(Name(t[i], famNames)){
						fnom = t[i];
						if(i+1<t.length){
							if(Name(t[i+1], preNames)){
								pnom = t[i+1];
								i++;
								names.add(pnom + placeholder(pnom, fnom) + fnom);
							}
						}
					}
				}
				
			} else{
				if(preNames.size() == 0 && famNames.size() == 0){
					throw new Exception("ERROR! Name lists not found or inaccessible!");
				} else{
					if(preNames.size() != 0){
						if(Name(t[i], preNames)){
							names.add(t[i]);
						}
					}else{
						/*if(Name(t[i], famNames)){
							names.add(t[i]);
						}
						*/
					}
				}
		}
		}
		return names;
	}
	//Placeholder for names
	private static String placeholder(String a, String b){
		if(!a.equals("") && !b.equals("")){
			return " ";
		}
		return "";
	}
	//Searches for Name
	private static boolean Name(String s, List<String> preNames){
		for(int o = 0; o<preNames.size(); o++){
			if(preNames.get(o).equals(s)){
				return true;
			}
		}
		return false;
	}
	/**
	 * This method returns the most relevant words from a text and their quantity of occurrence in a NGrammList 
	 * It's highly recommended to use removeWordsFromText() to remove verbs like 'is' or 'has' to get better results
	 * @param text The text to take words from
	 * @param n The n most relevant words will be returned
	 * @return	An NGrammList containing the n most relevant words and their quantity in the text
	 * @throws Exception
	 */
	public static NGrammList getMostRelevantWords(String text, int n) throws Exception{
		text = filter(text);
		List<String> words = arrayToList(text.split(" "));
		NGrammList counted = count(words);
		counted = sort(counted);
		NGrammList relevant = new NGrammList();
		for(int i = counted.getNGramm().size()-1; i>counted.getNGramm().size()-(n+1); i--){
			relevant.addItem(counted.getNGramm().get(i), counted.getCnt().get(i));
		}
		return relevant;
	}
	/**
	 * This method removes all words contained in the String[] from a String
	 * @param text The String to remove words from
	 * @param remove An Array of Strings that should be removed from the input
	 * @return The String without any unwanted containments
	 */
	public static String removeWordsFromText(String text, String[] remove){
		return removeWordsFromText(text, arrayToList(remove));
	}
	/**
	 * This method removes all words contained in the List from a String
	 * @param text The String to remove words from
	 * @param remove A List of Strings that should be removed from the input
	 * @return The String without any unwanted containments
	 */
	public static String removeWordsFromText(String text, List<String> remove){
		for(int i = 0; i<remove.size(); i++){
			text = text.replace(remove.get(i), "");
		}
		return text;
	}
	/**
	 * This method generates a List<String> from a String[]
	 * @param array The String[] to convert into a List
	 * @return The converted List<String>
	 */
	public static List<String> arrayToList(String[] array){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i<array.length; i++){
			list.add(array[i]);
		}
		return list;
	}
	/**
	 * This method makes it easy to find similar strings to a given one in a given list
	 * This could be used for a spam-filter that takes care of spelling mistakes [be aware of words with nearly similar spelling!]
	 * @param inputText The input text that will be checked for the containment of words similar to the ones in the stringPool array
	 * @param stringPool 
	 * @return
	 * @throws Exception
	 */
	public static String getMostSimilarString(String inputText, String[] stringPool) throws Exception{
		NGrammList n = new NGrammList();
		for(int i = 0; i<stringPool.length; i++){
			n.addItem(stringPool[i], (int)Math.rint(compareWords(inputText, stringPool[i])*1000));
		}
		n = sort(n);
		return n.getNGramm().get(n.getNGramm().size()-1);
	}
	/**
	 * Compares how similar two words are
	 * @param word1 First word
	 * @param word2 Second Word
	 * @return The similarity between the two words as a percentage
	 */
	public static double compareWords(String word1, String word2){
		List<String> l1 = ngramm(word1, 3);
		List<String> l2 = ngramm(word2, 3);
		return cmp(l1, l2)*100;
	}
	
	//This method calculates the similarity of two sorted NGramm-Lists
	private static double calcPercentage(NGrammList l1, NGrammList l2, int nr1, int nr2){
		int top = (l1.getNGramm().size()>l2.getNGramm().size()) ? l2.getNGramm().size() : l1.getNGramm().size();
		//System.out.println(top);
		List<String> n1 = l1.getNGramm().subList(0, top);
		List<String> n2 = l2.getNGramm().subList(0, top);
		double cn = 0;
		for(int i = 0; i<n2.size(); i++){
			for(int o = 0; o<n1.size(); o++){
				if(l2.getNGramm().get(i).equals(l1.getNGramm().get(o))){
					cn = cn + ((double) l2.getCnt().get(i) / nr2 + (double) l2.getCnt().get(i) / nr1)/2;
					
				}
			}
		}
		return cn;
	}
	/**
	 * This method generates a NGrammList containing the given Strings and their quantity of occurrence
	 * @param strings A list containing strings for counting
	 * @return Returns an NGrammList containing the quantity for every String (like an associative array in php)
	 * @throws Exception 
	 */
	public static NGrammList count(List<String> strings) throws Exception{
		List<String> cnt = removeDuplicates(strings);
		List<Integer> cn = new ArrayList<Integer>();
		for(int me = 0; me<cnt.size(); me++){
			cn.add(0);
		}
		for(int i = 0; i<strings.size(); i++){
			for(int o = 0; o<cnt.size(); o++){
				if(strings.get(i).equals(cnt.get(o))){
					cn.set(o, cn.get(o) + 1);
				}
			}
		}
		return new NGrammList(cnt, cn);
	}
	/**
	 * This method removes the following Chars from the given String: =&%(){}[]\\/\".,;:!'?
	 * @param s String for filtering
	 * @return Filtered String
	 */
	public static String filter(String s){
		return filter(s, "=&%(){}[]\\/\".,;:!'?");
	}
	/**
	 * This method removes a selected set of chars from a String
	 * @param s String for filtering
	 * @param filterChars A String containing all Chars that will be removed from the input
	 * @return The filtered String
	 */
	public static String filter(String s, String filterChars){
		char[] filter = filterChars.toCharArray();
		for(int i = 0; i<filter.length; i++){
			s = s.replace("" + filter[i], "");
		}
		return s;
	}
	//This method elongates short strings with _
	private static String[] addFiller(String[] s){
		for(int i = 0; i<s.length; i++){
			if(s[i].length() <= 5){
				s[i] = "_" + s[i] +"_";
			}
		}
		return s;
	}
	/**
	 * Creates a list of ngramms with the length n from a string
	 * @param s String to create ngramms of
	 * @param n The length for the ngramms
	 * @return A list containing all the ngramm-strings (with duplictes! use removeDuplicates() to get a list of unique Strings)
	 */
	public static List<String> ngramm(String s, int n){
		List<String> list = new ArrayList<String>();
		for(int i = 0; i<s.length()-(n-1); i++){
			list.add(s.substring(i, i+n));
		}
		return list;
	}
	private static String backToString(String[] s){
		String str = "";
		for(int i = 0; i<s.length; i++){
			str = str + s[i];
		}
		return str;
	}
	private static double cmp(List<String> nl1, List<String> nl2){
		int length = Math.min(nl1.size(), nl2.size());
		int similarities = 0;
		for(int i = 0; i<length; i++){
			if(nl1.get(i).equals(nl2.get(i))){
				similarities++;
			}
		}
		return 2* (double)similarities/(double)(nl1.size() + nl2.size());
	}
	
	/**
	 * This method removes duplicated items in the given list
	 * @param list The list with duplicates
	 * @return A list containing only unique items
	 */
	public static List<String> removeDuplicates(List<String> list){
		List<String> newL= new ArrayList<String>();
		for(int i = 0; i<list.size(); i++){
			if(newL.size() != 0){
				int d = 0;
				for(int o = 0; o<newL.size(); o++){
					if(list.get(i).equals(newL.get(o))){
						d++;
					}
				}
				if(d == 0){
					newL.add(list.get(i));
				}
			} else{
				newL.add(list.get(i));
			}
		}
		return newL;
	}
	/**
	 * Sorts an NGrammList in ascending order (very fast algorithm based on Merge-sort)
	 * @param ngr The NGrammList to sort
	 * @return The NGrammList in ascending order
	 * @throws Exception 
	 */
	public static NGrammList sort(NGrammList ngr) throws Exception{
		List<String> ngramm = ngr.getNGramm();
		List<Integer> numbers = ngr.getCnt();
		
		if(numbers.size() <= 1) return new NGrammList(ngramm, numbers);
		int ll = numbers.size()/2;
		
		List<Integer> leftNr = new ArrayList<Integer>();
		List<Integer> rightNr = new ArrayList<Integer>();
		List<String> leftN = new ArrayList<String>();
		List<String> rightN = new ArrayList<String>();
		
		for(int i = 0; i<numbers.size(); i++){
			if(i<ll){
				leftNr.add(numbers.get(i));
				leftN.add(ngramm.get(i));
			} else{
				rightNr.add(numbers.get(i));
				rightN.add(ngramm.get(i));
			}
		}
		NGrammList l = new NGrammList(leftN, leftNr);
		NGrammList r = new NGrammList(rightN, rightNr);
		if(leftNr.size() >1 | rightNr.size() > 1){
			l = sort(l);
			r = sort(r);
		}
		return mergeLists(l, r);
	}
		
	private static NGrammList mergeLists(NGrammList leftNGrammList, NGrammList rightNGrammList) throws Exception{
		List<Integer> leftList = leftNGrammList.getCnt();
		List<Integer> rightList = rightNGrammList.getCnt();
		
		List<String> lNGr = leftNGrammList.getNGramm();
		List<String> rNGr = rightNGrammList.getNGramm();
		//NGramm-Listen hier laden
		
		List<String> ngrm = new ArrayList<String>(); //Neue NGramm-Liste
		List<Integer> mList = new ArrayList<Integer>();
		while(!leftList.isEmpty() && !rightList.isEmpty()){
			if(leftList.get(0) <= rightList.get(0)){
				mList.add(leftList.get(0));
				leftList.remove(0);
				//NGramm-Liste verarbeiten:
				ngrm.add(lNGr.get(0));
				lNGr.remove(0);
			} else{
				mList.add(rightList.get(0));
				rightList.remove(0);
				//NGramm-Liste verarbeiten:
				ngrm.add(rNGr.get(0));
				rNGr.remove(0);
			}
		}
		while(!leftList.isEmpty()){
			mList.add(leftList.get(0));
			leftList.remove(0);
			ngrm.add(lNGr.get(0));
			lNGr.remove(0);
		}
		while(!rightList.isEmpty()){
			mList.add(rightList.get(0));
			rightList.remove(0);
			ngrm.add(rNGr.get(0));
			rNGr.remove(0);
		}
		return new NGrammList(ngrm, mList);
	}
}
