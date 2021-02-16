package com.siliconmtn.data.parser;

// JDK 11.x
import java.io.Serializable;

// J2E
import javax.servlet.http.HttpServletRequest;

//Log4j 2.x
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.siliconmtn.data.text.StringUtil;

/********************************************************************
 * <b>Title: </b>BeanDataVO.java<br/>
 * <b>Description: </b>Base class to automate the setting of parameters
 * from a result set or a HTTP request into a java bean's properties<br/>
 * <b>Copyright: </b>Copyright (c) 2021<br/>
 * <b>Company: </b>Silicon Mountain Technologies
 * @author james
 * @version 1.x
 * @since Jan 22, 2021
 * Last Updated:
 * 	
 *******************************************************************/
public class BeanDataVO implements Serializable, AutoPopulateIntfc {
	private static final long serialVersionUID = 1L;
	protected static Logger log;

	/**
	 * 
	 */
	public BeanDataVO() {
		super();
		log = LogManager.getLogger(getClass());
	}

	/**
	 * 
	 * @param req
	 */
	public BeanDataVO(HttpServletRequest req) {
		this();
		populateData(req);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringUtil.getToString(this);
	}
}