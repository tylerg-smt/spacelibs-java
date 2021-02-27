package com.siliconmtn.io.image;

//Junit 5
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;

/****************************************************************************
 * <b>Title</b>: ImageManipulatorTest.java
 * <b>Project</b>: spacelibs-java
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Feb 27, 2021
 * @updates:
 ****************************************************************************/
class ImageManipulatorTest {
	public static final String RESOURCE = "/home/etewa/Pictures/backyard.jpg";
	
	/**
	 * Test method for {@link com.siliconmtn.io.image.ImageManipulator#ImageManipulator(java.lang.String)}.
	 */
	@Test
	void testImageManipulatorString() throws Exception {
		assertDoesNotThrow(() -> {
			new ImageManipulator(RESOURCE);
		});
	}

	/**
	 * Test method for {@link com.siliconmtn.io.image.ImageManipulator#ImageManipulator(java.io.File)}.
	 */
	@Test
	void testImageManipulatorFile() throws Exception {
		assertDoesNotThrow(() -> {
			new ImageManipulator(new File(RESOURCE));
		});
	}

	/**
	 * Test method for {@link com.siliconmtn.io.image.ImageManipulator#rotateImage(int)}.
	 */
	@Test
	void testRotateImageInt() throws Exception {
		ImageManipulator im = new ImageManipulator(new File(RESOURCE));
		byte[] image = im.rotateImage(90);
		
		assertDoesNotThrow(() -> {
			im.rotateImage(90);
		});
		
		assertNotNull(image);
		assertTrue(image.length > 0);
	}

	/**
	 * Test method for {@link com.siliconmtn.io.image.ImageManipulator#rotateImage(int, java.io.File)}.
	 */
	@Test
	void testRotateImageIntFile() throws Exception {
		ImageManipulator im = new ImageManipulator(new File(RESOURCE));
		
		assertDoesNotThrow(() -> {
			im.rotateImage(90, new File("/home/etewa/Pictures/backyard_rotate.jpg"));
		});
	}

}
