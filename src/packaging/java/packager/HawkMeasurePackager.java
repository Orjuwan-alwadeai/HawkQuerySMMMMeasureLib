/*******************************************************************************
 * Copyright (c) 2017 Aston University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Orjuwan Al-Wadeai - Hawk Query SMMM Measure Implementation
 ******************************************************************************/
package packager;

import java.nio.file.Paths;
import org.measure.smm.service.MeasurePackager;

public class HawkMeasurePackager {
	public static final String PROJECTSPACE = "C:/Users/yaser And Orjuwan/workspaceForEclipseNeon/HawkQueryMeasureLib";
	public static final String JAR_RELATIVE_LOCATION = "/target/HawkQueryMeasureLib-0.0.1.jar";
	public static final String ZIP_NAME = "/HawkQueryMeasureLib-0.0.1.zip";


	public static void main(String[] args) {
		HawkQuerySMMMMeasure measure = new HawkQuerySMMMMeasure("HawkQueryMeasure", "Query");
		measure.setInstanceName("myhawk");
		measure.setQuery("return Class.all.size();");
		
		try {
			MeasurePackager.packageMeasure(Paths.get(PROJECTSPACE + JAR_RELATIVE_LOCATION),
					Paths.get(PROJECTSPACE +"/lib"),
					measure,
					Paths.get(PROJECTSPACE + ZIP_NAME));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
