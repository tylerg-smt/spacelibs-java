package com.siliconmtn.data.report;

// JDK 11.x
import java.lang.reflect.Constructor;

// Lombok 1.18.x
import lombok.extern.log4j.Log4j2;

/****************************************************************************
 * <b>Title</b>: ExcelStyleFactory.java <p/>
 * <b>Project</b>: SpaceLibs-Java <p/>
 * <b>Description: </b> uses a string to identify the correct style and returns its class
 * <p/>
 * <b>Copyright:</b> Copyright (c) 2016<p/>
 * <b>Company:</b> Silicon Mountain Technologies<p/>
 * @author James Camire
 * @version 1.0
 * @since Feb 14, 2021<p/>
 * @updates:
 ****************************************************************************/
@Log4j2
public class ExcelStyleFactory {
	/**
	 * Styles enum with fully qualified class names
	 */
	public enum Styles {
		STANDARD("com.smt.data.report.GreyHeadingBorderDataCellStyle"), 
		BORDERS_ONLY("com.smt.data.report.BordersOnlyCellStyle"),
		NO_STYLE("com.smt.data.report.NoStyleCellStyle");
		
		String classPath;
		Styles(String classPath) { this.classPath = classPath; }
		public String getClassPath() { return classPath; }
	}

	/**
	 * Since the class is all statics, add a private default constructor
	 */
	private ExcelStyleFactory() {
		super();
	}
	
	/**
	 * Gets the style of the enum type passed into the factory
	 * @param style
	 * @return
	 */
	public static ExcelStyleInterface getExcelStyle(Styles style) {
		try {
			Class<?> styleObj = Class.forName(style.getClassPath());
			Constructor<?> constructor = styleObj.getDeclaredConstructor();
			return (ExcelStyleInterface) constructor.newInstance();

		} catch (Exception e) {
			log.error("can not load excel style class " + e);
		}

		return new BordersOnlyCellStyle();
	}
}