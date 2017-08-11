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
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;
import org.measure.hawkquery.HawkQueryConstants;
import org.measure.hawkquery.HawkQueryMeasure;
import org.measure.hawkquery.ListMeasurement;
import org.measure.hawkquery.MapMeasurement;
import org.measure.hawkquery.StringMeasurement;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;

public class HawkQueryMeasureTest {

	@Test
	public void testQueryResult_Integer() {
		System.out.println("\n\ntestIntQueryResult *****");
		String queryString = "return Class.all.size();";
		List<IMeasurement> measurements = sendQuery(queryString);	
		if(measurements != null && measurements.get(0) != null) {
			assertTrue(measurements.get(0) instanceof IntegerMeasurement);
			assertEquals((Integer) 6, ((IntegerMeasurement) measurements.get(0)).getValue());
			assertEquals("6", measurements.get(0).getLabel());
		}
	}

	@Test
	public void testQueryResult_List() {
		System.out.println("\n\ntestListQueryResult *****");
		String queryString = "return Class.all.Name;";
		sendQuery(queryString);	
	}

	@Test
	public void testQueryResult_Map() {
		MapMeasurement measuremet = new MapMeasurement();
		StringMeasurement measurement1 = new StringMeasurement();
		measurement1.setValue("MEASURE 1");

		StringMeasurement measurement2 = new StringMeasurement();
		measurement2.setValue("MEASURE 2");

		StringMeasurement measurement3 = new StringMeasurement();
		measurement3.setValue("MEASURE 3");

		StringMeasurement measurement4 = new StringMeasurement();
		measurement4.setValue("MEASURE 4");

		ListMeasurement measurementList = new ListMeasurement();
		measurementList.add(measurement3);
		measurementList.add(measurement4);

		measuremet.add("key 1", measurement1);
		measuremet.add("key 2", measurement2);
		measuremet.add("key 3", measurementList);

		printMeasurement(measuremet, "");

	}

	@Test
	public void testQueryResult_ListofLists() {
		System.out.println("\n\ntestMapQueryResult *****");
		String queryString = "return Model.types.select(t|t.name='Class').references;";
		sendQuery(queryString);
	}

	@Test
	public void testQueryResult_ModelElementType() {
		System.out.println("\n\n testModelElementTypeQueryResult *****");
		sendQuery( "return Model.types;");
	}

	@Test
	public void testQueryResult_ModelElement() {
		System.out.println("\n\n testModelElementQueryResult *****");
		sendQuery( "return Model.metamodels;");
	}

	@Test
	public void testFilesQueryResult() {
		System.out.println("\n\n testFilesQueryResult *****");
		sendQuery( "return Model.files;");
	}

	private List<IMeasurement> sendQuery(String queryString) {
		HawkQueryMeasure measure = new HawkQueryMeasure();
		measure.getProperties().put(HawkQueryConstants.SERVER_URL, "http://localhost:8080/thrift/hawk/tuple");
		measure.getProperties().put(HawkQueryConstants.INSTANCE_NAME, "instance_2");
		measure.getProperties().put(HawkQueryConstants.QUERY_LANGUAGE, "org.hawk.epsilon.emc.EOLQueryEngine");
		measure.getProperties().put(HawkQueryConstants.QUERY, queryString);
		try {
			List<IMeasurement> measurements = measure.getMeasurement();
			for(IMeasurement result :measurements){
				printMeasurement(result, "");
			}	

			return measurements;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void printMeasurement(Object measurement, String identation) {
		printElement(measurement, identation);

		if(measurement instanceof ListMeasurement) {
			for(IMeasurement sub: ((ListMeasurement) measurement).getList()) {
				printMeasurement(sub, identation+">>");
			}
		} else if(measurement instanceof MapMeasurement) {
			for(Entry<String, IMeasurement> entry: ((MapMeasurement) measurement).getMap().entrySet()) {
				printMeasurement(entry.getValue(), identation + ">>" +  "Entry: " + entry.getKey());
			}
		} 
	}

	private void printElement(Object element, String identation) {

		System.out.print(identation);
		System.out.println(((IMeasurement) element).getLabel());
	}


}
