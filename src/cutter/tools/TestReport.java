package cutter.tools;

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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a opportunity to automatically create protocols for unit tests
 * @author Phil Niehus
 *
 */
public class TestReport {
	private String testReport = "";
	private String reportName = "Test Report";
	private List<UnitTest> resultList = new ArrayList<UnitTest>();
	
	private final String head1 = "<!DOCTYPE html>\n<html>\n\t<head>\n\t\t<title>\n\t\t\t";
	//title
	private final String head2 = "\n\t\t</title>\n\t\t<style>\n\t\t\t#nr{\n\t\t\t\twidth: 30px;\n\t\t\t}\n\t\t\t#val{\n\t\t\t\twidth: 600px;\n\t\t\t}\n\t\t\ttd{\n\t\t\t\ttext-align: center;\n\t\t\t\tfont-family: Courier New;\n\t\t\t}\n\t\t\ttd:nth-child(1){\n\t\t\t\tmin-width: 100px;\n\t\t\t}\n\t\t\ttd:nth-child(2){\n\t\t\t\twidth: 100%;\n\t\t\t}\n\t\t\ttr:first-child td{\n\t\t\t\tbackground-color: #000;\n\t\t\t\tfont-family: Verdana;\n\t\t\t\tcolor: #fff;\n\t\t\t}\n\t\t\ttr:nth-child(even){\n\t\t\t\tbackground-color: #ddd;\n\t\t\t}\n\t\t\ttr:nth-child(odd){\n\t\t\t\tbackground-color: #ccc;\n\t\t\t}\n\t\t\ttable{\n\t\t\t\ttext-align: center;\n\t\t\t}\n\t\t</style>\n\t</head>\n\t<body style=\"width: 100%; height: 100%; background-color: #eee; margin: 0; padding: 0;\">\n\t\t<div style=\"margin-left: 15px; margin-right: 15px; width: auto; text-align: center; font-family: Verdana; font-size: 5mm; background-color: #fff;\">\n\t\t\t<p>\n\t\t\t\t<h1 style=\"font-size: 12mm; border-bottom-style: solid; border-bottom-width: 2px; text-align: left; border-color: #f47e3c;\">";
	//headline
	private final String head3 = "</h1>\n\t\t\t</p>\n\t\t\t<div style=\"padding-bottom: 5px; padding-top: 5px;\">";
	private final String section1 = "\n\t\t\t\t<p>\n\t\t\t\t\t<h1 style=\"font-size: 8mm; border-bottom-style: solid; border-bottom-width: 1px; text-align: left; margin-bottom: 5px; border-color: #f47e3c;\">";
	//Section headline
	private final String section2 = "</h1>\n\t\t\t\t\t<i>";
	//PassedTestNumber
	private final String section3 = "</i> of <i>";
	//TotalTestNumber
	private final String section4 = "</i> tests passed:\n\t\t\t\t\t<div style=\"border-style: solid; border-width: 1px; border-color: #f47e3c; margin-left: 15px; margin-right: 15px; margin-top: 5px; border-color: #000;\">\n\t\t\t\t\t\t<table style=\"border: none; border-collapse: collapse; border-spacing: 0;\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td><b>Test no.</b></td>\n\t\t\t\t\t\t\t\t<td><b>Test result</b></td>\n\t\t\t\t\t\t\t</tr>";
	
	private final String testResult1 = "\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td>";
	//TestNumber
	private final String testResult2 = "</td>\n\t\t\t\t\t\t\t\t<td>Actual value <b>";
	//Actual value
	private final String testPassed1 = "</b> <span style=\"color: green;\">equals expected value </span><b>";
	private final String testFailed1 = "</b> <span style=\"color: red;\">differs from expected value </span><b>";
	//Expected value
	private final String testPassed2 = "</b> - TEST PASSED!</td>\n\t\t\t\t\t\t\t</tr>";
	private final String testFailed2 = "</b> - TEST FAILED!</td>\n\t\t\t\t\t\t\t</tr>";
	
	private final String sectionEnd = "\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</div>\n\t\t\t\t</p>";
	private final String headEnd = "\n\t\t\t</div>\n\t\t</div>\n\t</body>\n</html>";
	/**
	 * This constructor creates a TestReport with the name given in reportName
	 * @param reportName the name for the TestReport as a String
	 */
	public TestReport(String reportName){
		this.reportName = reportName;
	}
	/**
	 * The default constructor for the TestReport object
	 */
	public TestReport(){
		
	}
	/**
	 * This method adds the given UnitTest to the TestReport
	 * @param ut the UnitTest which will be added to the Testreport
	 */
	public void addUnitTest(UnitTest ut){
		resultList.add(ut);
	}
	/**
	 * This method sets the TestReports name to the given String
	 * @param name
	 */
	public void setReportName(String name){
		reportName = name;
	}
	/**
	 * This method returns a TestReports name as a String
	 * @return
	 */
	public String getReportName(){
		return reportName;
	}
	
	/**
	 * This method returns the generated TestReport as HTML string
	 * @return
	 */
	public String getTestReport(){
		return testReport;
	}
	
	public void writeReportToFile(File f) throws Exception{
		if("".equals(testReport)) generateTestReport();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write(testReport);
		bw.close();
	}
	
	/**
	 * This method generates a TestReport as HTML
	 */
	public void generateTestReport(){
		testReport = "" + head1 + reportName + head2 + reportName + head3;
		for(UnitTest u : resultList){
			testReport = testReport + generateSection(u);
		}
		testReport = testReport + headEnd;
	}
	
	//Helper function generates section body
	private String generateSection(UnitTest ut){
		String section = section1 + ut.getTestName() + section2 + ut.passedTests + section3 + ut.getPassedList().size() + section4;
		List<Boolean> uPassed = ut.getPassedList();
		ArrayList actual = ut.getActualResults();
		ArrayList expected = ut.getExpectedResults();
		if(uPassed.size() == actual.size() && actual.size() == expected.size()){
			int i = 0;
			for(Object o : actual){
				section = section + generateResultEntry(i+1, o, expected.get(i), uPassed.get(i));
				i++;
			}
		}
		return section + sectionEnd;
	}
	
	//Helper function generates table entries
	private String generateResultEntry(int testNumber, Object actual, Object expected, boolean passed){
		return (testResult1 + testNumber + testResult2) + convertResult(actual) + ((passed) ? testPassed1 : testFailed1) + convertResult(expected) + ((passed) ? testPassed2 : testFailed2);
	}
	
	//Converts object to String
	private String convertResult(Object res){
		if (res.getClass().equals(Integer.class) ){
			return "" + (int) res;
		} else if(res.getClass().equals(Float.class)){
			return "" + (float) res;
		} else if(res.getClass().equals(String.class)){
			return "" + (String) res;
		} else if(res.getClass().equals(Byte.class)){
			return "" + (byte) res;
		} else if(res.getClass().equals(Boolean.class)){
			return "" + (boolean) res;
		} else if(res.getClass().equals(Double.class)){
			return "" + (double) res;
		} else if(res.getClass().equals(Long.class)){
			return "" + (long) res;
		} else if(res.getClass().equals(Short.class)){
			return "" + (short) res;
		} else if(res.getClass().equals(Character.class)){
			return "" + (char) res;
		}
		System.err.println("Error, the input doesn't match one of the basic types in java!");
		return "ERROR";
	}
}
