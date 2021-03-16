package com.siliconmtn.data.report;

// JDK 11.x
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/****************************************************************************
 * <b>Title</b>: AbstractReport.java
 * <b>Project</b> SpaceLibs-java
 * <b>Description: Abstract and commonality across any downloadable report we
 *  might generate.</b> 
 * 
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * @author James Camire
 * @version 1.0
 * @since Feb 14, 2021
 ****************************************************************************/
public abstract class AbstractReport implements Serializable {
	
	// Members
	private static final long serialVersionUID = -3789238527320645654L;
	protected String contentType = null;
	protected String fileName = null;
	protected boolean isAttachment = false;
	protected transient Map<String, Object> attributes = new LinkedHashMap<>();
	
	
	/**
	 * method called by PageBuilderServlet to write the report into the response object.
	 * must be overwritten by all implementing classes.
	 * must always return a byte array
	 */
	public abstract byte[] generateReport() throws IOException;
	
	
	/**
	 * all reports will have their own definition of what "setData" means.
	 * @param o Object (can be whatever your concrete implementation requires)
	 */
	public abstract void setData(Object o);
	
	
	/**
	 * Each report will set it's content type so when it is being streamed back
	 * to the user, the proper type can be set in the stream
	 * @return
	 */
	public String getContentType() {
		return this.contentType;
	}
	
	/**
	 * Takes the file extension and sets the content type
	 */
	protected void setContentTypeByFileName() {
		Path path = new File(this.fileName).toPath();
	    try {
			contentType = Files.probeContentType(path);
		} catch (IOException e) {
			contentType = null;
		}
	}
	
	/**
	 * Returns the name of the report file
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.  The content type is also set based upon the file extension
	 * @param fileName Name of the file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
		setContentTypeByFileName();
	}
	
	/**
	 * Sets the header attachment, which is used when streaming a file to the browser
	 * @param b
	 */
	public void setHeaderAttachment(boolean b) {
		isAttachment = b;
	}
	
	/**
	 * Gets the header attachment, which is used when streaming a file to the browser
	 * @return
	 */
	public boolean isHeaderAttachment() {
		return isAttachment;
	}
	
	/**
	 * Gets the attribute by the provided key
	 * @return
	 */
	public Map<String,Object> getAttributes() {
		return attributes;
	}

	/**
	 * Overwrites the existing attributes with the new map
	 * @param attributes
	 */
	public void setAttributes(Map<String, ?> attributes) {
		if (attributes == null) {
			this.attributes = new LinkedHashMap<>();
		} else {
			this.attributes.putAll(attributes);
		}
	}
	
	/**
	 * Adds an element to the attribute map
	 * @param key
	 * @param value
	 */
	public void addAttribute(String key, Object value) {
		attributes.put(key, value);
	}

}
