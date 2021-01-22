package com.smt.data.format;

import com.smt.data.text.StringUtil;

/****************************************************************************
 * <b>Title</b>: TimeZoneVO.java
 * <b>Project</b>: SpaceLibs-Java
 * <b>Description: </b> Manages the data for a single time zone
 * <b>Copyright:</b> Copyright (c) 2021
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author James Camire
 * @version 3.0
 * @since Jan 21, 2021
 * @updates:
 ****************************************************************************/
public class TimeZoneVO {
	
	private String id;
	private String name;
	private String description;
	private String isoCode;
	
	/**
	 * Default constructor
	 */
	public TimeZoneVO() {
		super();
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param isoCode
	 */
	public TimeZoneVO(String id, String name, String description, String isoCode) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isoCode = isoCode;
	}

	/*
	 * (non-javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringUtil.getToString(this);
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the isoId
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param isoId the isoId to set
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

}
