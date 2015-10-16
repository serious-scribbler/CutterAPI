package cutter.imageprocessing;
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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * This class contains multiple methods for image-processing-purposes
 * @author Phil Niehus
 *
 */
public class ImageTools {
	public ImageTools(){
		
	}
	
	
	/**
	 * Reads an image from a file
	 * @param path Path of the Image
	 * @return The Image as BufferedImage
	 * @throws IOException
	 */
	public static BufferedImage readImageFromFile(String path) throws IOException{
		return readImageFromFile(new File(path));
	}
	
	
	/**
	 * Reads an image from a file
	 * @param file The image file to read from
	 * @return The Image as BufferedImage
	 * @throws IOException
	 */
	public static BufferedImage readImageFromFile(File file) throws IOException{
		BufferedImage img = ImageIO.read(file);
		return img;
	}
	
	
	/**
	 * Converts an image to grayscale
	 * @param img the image that will be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toGrayscale(BufferedImage img){
		int[][] matrix = toGrayscaleMatrix(img);
		for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){
				if(matrix[x][y] == 8){
					img.setRGB(x, y, new Color(255, 255, 255).getRGB());
				} else{
					img.setRGB(x, y, new Color(matrix[x][y]*32, matrix[x][y]*32, matrix[x][y]*32).getRGB());
				}
			}
		}
		return img;
	}
	
	
	public static int[][] toGrayscaleMatrix(BufferedImage img){
		BetterRGB rgb = new BetterRGB(img);
		int[][] matrix = new int[img.getWidth()][img.getHeight()];
		for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){
				Color c = rgb.getColorAt(x, y);
				int avg = (c.getRed() + c.getGreen() + c.getBlue())/3;
				int scale = avg/32;
				if(((avg - scale*32) > 16) && avg != 0){
					scale++;
				}
				matrix[x][y] = scale;
			}
		}
		return matrix;
	}

	
	/**
	 * Creates a negative of the given image
	 * @param img BufferedImage to negate
	 * @return
	 */
	public static BufferedImage toNegative(BufferedImage img){
		for(int x = 0; x < img.getWidth(); x++){
			for(int y = 0; y < img.getHeight(); y++){
				Color c = new Color(img.getRGB(x, y));
				img.setRGB(x, y, new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue()).getRGB());
			}	
		}
		return img;
	}
	
	
	/**
	 * Writes an image to a file
	 * @param img BufferedImage for writing
	 * @param f The file the image will be written in
	 * @throws IOException
	 */
	public static void writeImageToFile(BufferedImage img, File f) throws IOException{
		ImageIO.write(img, "png", f);
	}
}
