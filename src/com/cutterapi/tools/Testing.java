package com.cutterapi.tools;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cutterapi.text.NGramm;
import com.cutterapi.text.NGrammList;

public class Testing {
	TestReport tr = new TestReport("CutterAPI 0.2.0 UnitTest");
	ArrayList<UnitTest> tests = new ArrayList<UnitTest>();
	UnitTest t;
	public Testing() {
		
		testNGrammGetNames();
		unitTestFlexibility();
		removeWordTest();
		arrayToListTest();
		mostSimilarStringTest();
		compareWordTest();
		countWortAmountTest();
		filterTest();
		ngrammTest();
		removeDuplicatesTest();
		try {
			ngrammCountTest();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		for(UnitTest ut : tests){
			tr.addUnitTest(ut);
		}
		try {
			tr.writeReportToFile(new File("C:/test/test.html"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Namefinder test
	private void testNGrammGetNames(){
		UnitTest t = new UnitTest("NGramm.getAllNames()");
		List<String> names;
		try {
			
			names = NGramm.getAllNames("Phil Niehus apple orange Peter Meyer banana cake potatoe Paul Panzer house modell programm Dieter Smith cable fabric James Schneider");
			String[] expNames = new String[]{"Phil Niehus", "Peter Meyer", "Paul Panzer", "Dieter Smith", "James Schneider"};
			t = evalString(t, expNames, names);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		tests.add(t);
	}
	
	private void unitTestFlexibility(){
		UnitTest t = new UnitTest("UnitTest and TestReport type flexibility test");
		ArrayList ar = new ArrayList();
		ar.add(7.11);
		ar.add(4.12);
		ar.add(9.9);
		ar.add(-1.1);
		t.evaluateMultiple(ar, ar);
		ar.clear();
		ar.add("Apfel");
		ar.add("Kuchen");
		t.evaluateMultiple(ar, ar);
		ar.clear();
		ar.add(true);
		ar.add(false);
		t.evaluateMultiple(ar, ar);
		tests.add(t);
	}
	
	private void removeWordTest(){
		UnitTest t = new UnitTest("NGramm.removeWordsFromText()");
		t.evaluate(NGramm.removeWordsFromText("Cheese is awesome! Is cheese awesome? Cheese rocks.", new String[]{"Cheese", "cheese"}), " is awesome! Is  awesome?  rocks.");
		t.evaluate(NGramm.removeWordsFromText("This is an evil word: 'evil'.", new String[]{"evil"}), "This is an  word: ''.");
		tests.add(t);
	}
	
	private void arrayToListTest(){
		UnitTest t = new UnitTest("NGramm.arrayToList()");
		ArrayList a = new ArrayList();
		a.add("car");
		a.add("cpu");
		a.add("computer");
		t.evaluateMultiple(a, (ArrayList) NGramm.arrayToList(new String[]{"car", "cpu", "computer"}));
		tests.add(t);
		
	}
	
	private void mostSimilarStringTest(){
		UnitTest t = new UnitTest("NGramm.getMostSimilarString()");
		try {
			t.evaluate(NGramm.getMostSimilarString("Applrpie", new String[]{"Applepie", "Applause"}), "Applepie");
			t.evaluate(NGramm.getMostSimilarString("Ferarri", new String[]{"Ferrari", "Fabric"}), "Ferrari");
			t.evaluate(NGramm.getMostSimilarString("Saul", new String[]{"Paul", "Soul"}), "Paul");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tests.add(t);
	}
	
	private void compareWordTest(){
		UnitTest t = new UnitTest("NGramm.CompareWords()");
		t.evaluate(NGramm.compareWords("sock", "rock"), 50.0); //Differs from human understanding
		t.evaluate(NGramm.compareWords("fisher", "disher"), 75.0);
		tests.add(t);
	}
	
	private void countWortAmountTest(){
		UnitTest t = new UnitTest("NGramm.count()");
		
		List<String> s = new ArrayList<String>();
		s.add("cpu");
		s.add("ram");
		s.add("hdd");
		s.add("computer");
		s.add("windows");
		s.add("cpu");
		
		try {
			NGrammList nl = NGramm.count(s);
			t.evaluate(nl.getNGramm().get(0), "cpu");
			t.evaluate(nl.getCnt().get(0), 2);
			tests.add(t);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void filterTest(){
		UnitTest ut = new UnitTest("NGramm.filter()");
		ut.evaluate(NGramm.filter("{Fish}potato[cpu"), "Fishpotatocpu");
		ut.evaluate(NGramm.filter("!test%cpu.ram"), "testcpuram");
		ut.evaluate(NGramm.filter("#hashtag", "#"), "hashtag");
		tests.add(ut);
	}
	
	private void ngrammTest(){
		UnitTest ut = new UnitTest("NGramm.ngramm()");
		List<String> n = NGramm.ngramm("This is a test.", 3);
		String[] cmp = new String[]{"Thi", "his", "is ", "s i", " is", "is ", "s a", " a ", "a t", " te", "tes", "est", "st."};
		ut = evalString(ut, cmp, n);
		tests.add(ut);
	}
	
	private void removeDuplicatesTest(){
		UnitTest u = new UnitTest("NGramm.removeDuplicates()");
		List<String> ls = NGramm.arrayToList(new String[]{"apple", "orange", "lemon", "strawberry", "lemon"});
		String[] cmp = new String[]{"apple", "orange", "lemon", "strawberry"};
		ls = NGramm.removeDuplicates(ls);
		u.evaluate(ls.size(), cmp.length);
		tests.add(u);
	}
	
	private void ngrammCountTest() throws Exception{
		UnitTest u = new UnitTest("Ngramm.count()");
		NGrammList ngl = NGramm.count(NGramm.arrayToList(new String[]{"a", "b", "c", "c", "d", "b"}));
		u.evaluate(ngl.getValueForItem("a"), 1);
		u.evaluate(ngl.getValueForItem("b"), 2);
		u.evaluate(ngl.getValueForItem("c"), 2);
		u.evaluate(ngl.getValueForItem("d"), 1);
		tests.add(u);
	}
	
	private UnitTest evalString(UnitTest u, String[] ar, List<String> ls){
		
		if(ar.length != ls.size()){
			u.evaluate(false, true);
			return u;
		}
		for(int i = 0; i < ls.size(); i++){
			u.evaluate(ls.get(i), ar[i]);
		}
		return u;
	}
}
