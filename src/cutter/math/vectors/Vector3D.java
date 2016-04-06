package cutter.math.vectors;

import cutter.math.CalculationMethod;
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
 * This class implements vectors as objects for calculations in 3 dimensional spaces
 * 
 * @author Phil Niehus
 * 
 */
public class Vector3D {
	double x, y, z;
	boolean calcmethod = false;
	CalculationMethod cm = null;
	
	public boolean internal = false; //stops internal steps from being added to the calculation method
	
	//Descriptiv things here
	int vn = 0;
	//MathML-Strings start here
	String opStart = "<math display='block'>\n";
	String opEnd = "</math>\n";
	String vc1 = "\t<mfenced open='(' close=')' separators=' '>\n\t\t<mtable rowspacing='3px'>\n\t\t\t<mtr>\n\t\t\t\t<mn>";
	String vc2 = "</mn>\n\t\t\t</mtr>\n\t\t\t<mtr>\n\t\t\t\t<mn>";
	String vc3 = "</mn>\n\t\t\t</mtr>\n\t\t\t<mtr>\n\t\t\t\t<mn>";
	String vc4 = "</mn>\n\t\t\t</mtr>\n\t\t</mtable>\n\t</mfenced>\n";
	
	String named1 = "\t<mover><mi mathvariant='bold'>V";
	String named2 = "</mi><mo>&rarr;</mo></mover>\n";
	
	String cross = "&Cross;";
	String scalar = "&compfn;";
	String value = "<mo>&Vert;</mo>";
	
	String op1 = "<mo>";
	String op2 = "</mo>";
	
	/**
	 * The constructor for initializing a Vector-object with its components
	 * @param xComponent the x-component for the new vector as double
	 * @param yComponent the y-component for the new vector as double
	 * @param zComponent the z-component for the new vector as double
	 */
	public Vector3D(double xComponent, double yComponent, double zComponent){
		this.x = xComponent;
		this.y = yComponent;
		this.z = zComponent;
	}
	/**
	 * Creates a Vector between the given points
	 * @param startingPoint the starting point for the new vector
	 * @param endPoint the end point for the new vector
	 */
	public Vector3D(Point3D startingPoint, Point3D endPoint){
		this.x = endPoint.x - startingPoint.x;
		this.y = endPoint.y - startingPoint.y;
		this.z = endPoint.z - startingPoint.z;
	}
	/**
	 * Creates a Vector between the given points and saves every calculation step as MathML in the CalculationMethod object
	 * @param startingPoint the starting point for the new vector
	 * @param endPoint the end point for the new vector
	 * @param calculationMethod The CalculationMethod used to store every calculation
	 */
	public Vector3D(Point3D startingPoint, Point3D endPoint, CalculationMethod calculationMethod){
		calcmethod = true;
		cm = calculationMethod;
		this.x = endPoint.x - startingPoint.x;
		this.y = endPoint.y - startingPoint.y;
		this.z = endPoint.z - startingPoint.z;
		vn = cm.vectorCount;
		cm.vectorCount++;
		cm.addCalculationStep(opStart + named1 + vn + named2 + "<mo>=</mo>" + "<mover><mi>P" + startingPoint.getPn() + "P" + endPoint.getPn() + "</mi><mo>&rarr;</mo></mover>" + op1 + "=" + op2 + vc1 + x + vc2 + y + vc3 + z + vc4 + opEnd);
	}
	/**
	 * This creates a Vector3D by its components and saves every calculation step as MathML in the CalculationMethod pbject
	 * @param xComponent the x-component for the new vector as double
	 * @param yComponent the y-component for the new vector as double
	 * @param zComponent the z-component for the new vector as double
	 * @param calculationMethod the CalculationMethod used to store every calculation
	 */
	public Vector3D(double xComponent, double yComponent, double zComponent, CalculationMethod calculationMethod){
		calcmethod = true;
		cm = calculationMethod;
		this.x = xComponent;
		this.y = yComponent;
		this.z = zComponent;
		vn = cm.vectorCount;
		cm.vectorCount++;
		cm.addCalculationStep(opStart + named1 + vn + named2 + "<mo>=</mo>" + vc1 + x + vc2 + y + vc3 + z + vc4 + opEnd);
	}
	/**
	 * Calculates the scalar product of this vector and v
	 * @param v the vector to multiply this vector with
	 * @return the scalar product of this vector and v
	 */
	public double scalarProduct(Vector3D v){
		double res =  x*v.x + y*v.y + z*v.z;
		if(calcmethod && !internal){
			cm.addCalculationStep(opStart + named1 + vn + named2 + op1 + scalar + op2 + named1 + v.getVn() + named2 + op1 + "=" + op2 + "<mn>" + res + "</mn>" + opEnd);
		}
		return res;
	}
	/**
	 * 
	 * @return the x-component of this vector
	 */
	public double getX(){
		return x;
	}
	/**
	 * 
	 * @return the y-component of this vector
	 */
	public double getY(){
		return y;
	}
	/**
	 * 
	 * @return the z-component of this vector
	 */
	public double getZ(){
		return z;
	}
	/**
	 * Calculates the cross product of this vector and v
	 * @param v an Vector3D-object
	 * @return the cross product of this vector and v
	 */
	public Vector3D crossProduct(Vector3D v){
		double a = y*v.z - z*v.y;
		double b = z*v.x - x*v.z;
		double c = x*v.y - y*v.x;
		if(calcmethod && !internal){
			cm.addCalculationStep(opStart + named1 + vn + named2 + op1 + cross + op2 + named1 + v.getVn() + named2 + op1 + "=" + op2 + vc1 + a + vc2 + b + vc3 + c + vc4 + opEnd);
		}
		return new Vector3D(a, b, c);
		
	}
	/**
	 * Adds this vector and an other one
	 * @param v the vector to add to this vector
	 * @return the sum of this vector and v
	 */
	public Vector3D add(Vector3D v){
		if(calcmethod && !internal){
			cm.addCalculationStep(opStart + named1 + vn + named2 + op1 + "<mo>+</mo>" + op2 + named1 + v.getVn() + named2 + op1 + "=" + op2 + vc1 + (x+v.x) + vc2 + (y+v.y) + vc3 + (z+v.z) + vc4 + opEnd);
		}
		return new Vector3D(x+v.x, y+v.y, z+v.z);
	}
	/**
	 * Similar to multiplication with -1
	 * @return an inverted version of this vector
	 */
	public Vector3D invert(){
		if(calcmethod && !internal){
			cm.addCalculationStep(opStart + "<mo>-</mo>" + named1 + vn + named2 + op1 + "=" + op2 + vc1 + (-x) + vc2 + (-y) + vc3 + (-z) + vc4 + opEnd);
		}
		return new Vector3D(-x, -y, -z);
	}
	/**
	 * Subtracts v from this vector
	 * @param v vector to subtract from this vector
	 * @return the difference between this vector and v
	 */
	public Vector3D subtract(Vector3D v){
		if(calcmethod && !internal){
			cm.addCalculationStep(opStart + named1 + vn + named2 + op1 + "<mo>-</mo>" + op2 + named1 + v.getVn() + named2 + op1 + "=" + op2 + vc1 + (x-v.x) + vc2 + (y-v.y) + vc3 + (z-v.z) + vc4 + opEnd);
		}
		return new Vector3D(x-v.x, y-v.y, z-v.z);
	}
	/**
	 * 
	 * @return the amount of this vector as a double
	 */
	public double amount(){
		double amnt = Math.sqrt(x*x + y*y + z*z);
		if(calcmethod && !internal){
			cm.addCalculationStep(opStart + value + named1 + vn + named2 + value + op1 + "=" + op2 + "<mn>" + amnt + "</mn>" + opEnd);
		}
		return amnt;
	}
	/**
	 * Calculates the angle between this vector and v
	 * @param v
	 * @return	the angle between this vector and v as double
	 */
	public double angle(Vector3D v){
		internal = true;
		v.internal = true;
		double ang = Math.toDegrees(Math.acos(this.scalarProduct(v)/(this.amount()*v.amount())));
		if(calcmethod){
			cm.addCalculationStep(opStart + named1 + vn + named2 + op1 + "&ang;" + op2 + named1 + v.getVn() + named2 + op1 + "=" + op2 + "<msup><mn>" + ang + "</mn><mo>&deg;</mo></msup>" + opEnd);
		}
		v.internal = false;
		internal = false;
		return ang;
	}
	/**
	 * Checks wether v is parallel to this vector or not
	 * @param v
	 * @return
	 */
	public boolean isParallelTo(Vector3D v){
		internal = true;
		Vector3D r = this.crossProduct(v);
		internal = false;
		boolean parallel = (r.getX() == 0 && r.getY() == 0 && r.getZ() == 0) ? true : false;
		if(calcmethod){
			String paral = (parallel) ? "&spar;" : "&npar;";
			cm.addCalculationStep(opStart + named1 + vn + named2 + op1 + paral + op2 + named1 + v.getVn() + named2 + opEnd);
		}
		return parallel;
	}
	/**
	 * This method is only for calculation methods - so simply ignore it
	 * @return
	 */
	public int getVn(){
		return vn;
	}
	/**
	 * Sets the description of the last calculation step to the given Text/HTML - This is just for calculation methods, you could also use the method of your CalculationMethod
	 * This method needs to be called directly after the execution of any kind of calculation to work well (this also includes the definition of this object)
	 * @param description
	 */
	public void setDescriptionForLastStep(String description){
		cm.setDescriptionForLastStep(description);
	}
}
