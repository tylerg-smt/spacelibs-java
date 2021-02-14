package com.smt.data.report;

// JDK 11.x
import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * <b>Title</b>: NoStyleCellStyle.java <p/>
 * <b>Project</b>: SpaceLibs-Java <p/>
 * <b>Description: </b> produces an empty map, this allows the factory to produce
 * an empty style.  
 * <p/>
 * <b>Copyright:</b> Copyright (c) 2016<p/>
 * <b>Company:</b> Silicon Mountain Technologies<p/>
 * @author James Camire
 * @version 1.0
 * @since Feb 14, 2021<p/>
 * @updates:
 ****************************************************************************/
public class NoStyleCellStyle extends AbstractExcelCellStyle {
	
	public NoStyleCellStyle(){
		super();
		this.expandColumnFlg = false;
		buildStyleMap();
	}

	/* (non-Javadoc)
	 * @see com.siliconmtn.data.report.AbstractExcelCellStyle#buildStyleMap()
	 */
	@Override
	public void buildStyleMap() {
		//empty map sent intentionally
		Map<String, Map<String, Object>> styleMap = new HashMap<>();
		setStyleMap(styleMap);
	}

}
