package com.cutterapi.imageprocessing;
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
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This class implements a faster alternative for the getRGB()-method of BufferedImage.
 * This also avoids the incorrect RGB-values from the original getRGB()-method.
 * @author Phil Niehus
 *
 */
public class BetterRGB {
	BufferedImage img;
	int[][][] image;
	boolean monochrome = true;
	/**
	 * Creates a BetterRGB-object from the given BufferedImage and detects wether its colored or monochrome
	 * @param img
	 */
	public BetterRGB(BufferedImage img){
		image = new int[img.getWidth()][img.getHeight()][4];
		for (int x = 0; x < img.getWidth(); x++) {
		    for (int y = 0; y < img.getHeight(); y++) {
		        image[x][y] = img.getRaster().getPixel(x, y, new int[4]);
		        if(image[x][y][1] != 0 || image[x][y][2] != 0){
		        	monochrome = false;
		        }
		    }
		}
	}
	
	/**
	 * Returns the color at the specific position in the image
	 * @param x The pixels x-coordinate
	 * @param y	The pixels y-coordinate
	 * @return
	 */
	public Color getColorAt(int x, int y){
		if(monochrome){
			return new Color(image[x][y][0], image[x][y][0], image[x][y][0]);
		}
		return new Color(image[x][y][0], image[x][y][1], image[x][y][2]);
	}
	
	/**
	 * This method makes it possible to find out wether an image is monochrome or not
	 * @return
	 */
	public boolean isMonochrome(){
		return monochrome;
	}
}
