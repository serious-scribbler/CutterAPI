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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * This class has the functionality to automatically generate a MathML or HTML based calculation method
 * You need to use CutterAPIs own math methods to make use of this feature, or implement the addCalculationStep()-method and the add descriptionForLastStep-method into your own code.
 * 
 * @author Phil Niehus
 */
public class CalculationMethod {
	
	//Declaration of internal variables starts here
	String title = "CutterAPI - CalculationMethod";
	boolean html = false;
	boolean taskSet = false;
	List<String> steps= new ArrayList<String>();
	List<String> description = new ArrayList<String>();
	String task = "";
	public int vectorCount = 1; //Number of assigned vectors
	public int pointCount = 1; //Number of assigned points
	//HTML texts
	String docstart = "<!DOCTYPE html>\n<html lang='en'>\n\t<head>\t<title>";
	String bodyStart = "\t<script type='text/x-mathjax-config'>\n  MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]}, displayAlign: \"left\"});\n</script>\n<script type='text/javascript'\n  src='http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML'>\n</script>\n\t<link rel=\"stylesheet\" href=\"http://cutterapi.com/resources/calc_method.css\">\n\t<meta charset='UTF-8'>\n\t\n</head>\n<body>\n\t<div id='frame'>\n";
	String stepstart = "\t\t<div id='step'>\n";
	String stepend = "\n\t\t</div>";
	
	/**
	 * This constructor defines the CalculationMethod with title
	 * @param title The title of the CalculationMethod as a String
	 */
	public CalculationMethod(String title){
		this.title = title;
		html = true;
	}
	
	
	/**
	 * This constructor defines the CalculationMethod without title
	 */
	public CalculationMethod(){
		
	}
	
	
	/**
	 * This method returns the calculation method as HTML as a String
	 * @return
	 */
	public String getHTML(){
		String result = docstart + title + "</title>\n" + bodyStart + "\t\t<div id='header'>\n\t\t\t" + title + "\n\t\t</div>\n";
		if(taskSet){
			result += task;
		}
		for(int i = 0; i<steps.size(); i++){
			result += stepstart + "\t\t\t" + steps.get(i) + "\n";
			if((description.get(i)).equals("") == false){ //Enter description
				result += "\t\t\t<div id='description'>" + description.get(i) + "</div>";
			}
			result += stepend;
		}
		result += "\n\t\t<div id='footer'>\n\t\t\tCalculation method generated with <a href='http://cutterapi.com'>CutterAPI</a>. This website makes use of MathML and <a href=\"https://www.mathjax.org\">MathJax</a>.\n\t\t</div>\n\t</div>\n</body>\n</html>";
		return result;
	}
	
	/**
	 * This method returns the calculation method as MathML
	 * @return
	 */
	public String getMathML(){
		String result = "";
		for(int i = 0; i<steps.size(); i++){	
			result += steps.get(i);
		}
		return result;
	}
	
	/**
	 * This method sets the task that is shown in the HTML version of the calculation method
	 * The box for the task will not be added if there is no task set
	 * @param task
	 */
	public void setTask(String task){
		taskSet = true;
		this.task = "\t\t<div id='task'>" + task + "</div>\n";
	}
	
	/**
	 * This method removes a task that has been set before
	 */
	public void unsetTask(){
		taskSet = false;
	}
	
	/**
	 * This method adds MathML code to the calculation method
	 * @param stepMathML The step as MathML code
	 */
	public void addCalculationStep(String stepMathML){
		steps.add(stepMathML);
		description.add("");
	}
	
	/**
	 * Sets the description of the last calculation step to the given Text/HTML
	 * @param description
	 */
	public void setDescriptionForLastStep(String description){
		this.description.set(this.description.size()-1, description);
	}
	
	public void saveHtmlToFile(File file) throws IOException{
		BufferedWriter b = new BufferedWriter(new FileWriter(file));
		b.write(getHTML());
		b.close();
	}
}
