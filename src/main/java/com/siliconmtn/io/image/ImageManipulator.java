package com.siliconmtn.io.image;

// JDK 11
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/****************************************************************************
 * <b>Title</b>: ImageManipulator.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> Wrapper class that calls other classes that can transform
 * various aspects of an image.
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Feb 27, 2021
 * @updates:
 ****************************************************************************/
public class ImageManipulator {
	
	// Members
	private File image;
	
	/**
	 * Constructor requires the path to the image to be manipulated
	 * @param filePath
	 */
	public ImageManipulator(String filePath) {
		super();
		this.image = new File(filePath);
	}

	/**
	 * Constructor requires the path to the image to be manipulated
	 * @param filePath
	 */
	public ImageManipulator(File image) {
		super();
		this.image = image;
	}
	
	/**
	 * Rotates the image
	 * @param degrees number of degrees to rotate in a clockwise direction
	 * @return rotated image in a byte array
	 * @throws IOException 
	 */
	public byte[] rotateImage(int degrees) throws IOException {
		Rotator ir = new Rotator(image);
		return ir.rotate(degrees);
	}
	
	/**
	 * Rotates the image and writes the image to the provided file
	 * @param degrees number of degrees to rotate in a clockwise direction
	 * @return rotated image in a byte array
	 * @throws IOException 
	 */
	public void rotateImage(int degrees, File newImage) throws IOException {
		Rotator ir = new Rotator(image);
		Files.write(Paths.get(newImage.toURI()), ir.rotate(degrees));
	}
	
	
	
}
