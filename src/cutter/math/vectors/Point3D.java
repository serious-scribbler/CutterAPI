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
 * This class implements points for calculations in 3 dimensional space
 * @author Phil Niehus
 *
 */
public class Point3D {
	double x, y, z;
	int pn = 0;
	CalculationMethod cm = null;
	//MathML-Strings start here
	String opStart = "<math display='block'>\n";
	String opEnd = "</math>\n";
		
	/**
	 * Constructor for Points
	 * @param x x-coordinate of the new Point
	 * @param y y-coordinate of the new Point
	 * @param z z-coordinate of the new Point
	 */
	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * Defines a Point and adds the declaration as MathML in the CalculationMethod object 
	 * @param x x-coordinate of the new Point
	 * @param y y-coordinate of the new Point
	 * @param z z-coordinate of the new Point
	 * @param calculationMethod The CalculationMethod used to store the definition
	 */
	public Point3D(double x, double y, double z, CalculationMethod calculationMethod){
		this.x = x;
		this.y = y;
		this.z = z;
		cm = calculationMethod;
		pn = calculationMethod.pointCount;
		calculationMethod.pointCount++;
		calculationMethod.addCalculationStep(opStart + "<mi mathvariant='bold'>P" + pn + "</mi><mo>=</mo><mfenced separators='|'><mi>" + x + "</mi><mi>" + y + "</mi><mi>" + z + "</mi></mfenced>" + opEnd);
	}
	/**
	 * 
	 * @return The x-coordinate of the Point
	 */
	public double getX(){
		return x;
	}
	/**
	 * 
	 * @return The y-coordinate of the Point
	 */
	public double getY(){
		return y;
	}
	/**
	 * 
	 * @return The x-coordinate of the Point
	 */
	public double getZ(){
		return z;
	}
	/**
	 * Creates the position-vector for this point
	 * @return the position-vector of this point as a Vector-object
	 */
	public Vector3D toPositionVector(){
		return new Vector3D(x, y, z);
	}
	/**
	 * This method is only for calculation methods - so simply ignore it
	 * @return
	 */
	public int getPn(){
		return pn;
	}
	/**
	 * Sets the description of the last calculation step to the given Text/HTML - This is just for calculation methods, you could also use the method of your CalculationMethod
	 * This method needs to be called directly after the creation of the object to work well
	 * @param description
	 */
	public void setDescriptionForLastStep(String description){
		cm.setDescriptionForLastStep(description);
	}
}
