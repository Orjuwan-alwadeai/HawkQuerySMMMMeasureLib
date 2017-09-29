package org.hawk.measure.impl.tests;
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

import org.hawk.measure.impl.BooleanMeasurement;
import org.hawk.measure.impl.DoubleMeasurement;
import org.hawk.measure.impl.HawkQueryConstants;
import org.hawk.measure.impl.HawkQueryMeasure;
import org.hawk.measure.impl.ListMeasurement;
import org.hawk.measure.impl.LongMeasurement;
import org.hawk.measure.impl.MapMeasurement;
import org.hawk.measure.impl.StringMeasurement;
import org.junit.Test;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;

public class HawkQueryMeasureTest {
	final static String serverUrl = "http://localhost:8080/thrift/hawk/tuple";
	final static String instanceName = "instance_1";
	final static String queryLanguage = "org.hawk.epsilon.emc.EOLQueryEngine";


	@Test
	public void testQueryResult_Integer() throws Exception {
		System.out.println("\n\n testQueryResult_Integer *****");
		String queryString = "return 6;";

		List<IMeasurement> measurements = sendQuery(queryString);	

		// checks
		assertNotNull(measurements);
		assertNotNull(measurements.get(0));
		assertTrue(measurements.get(0) instanceof IntegerMeasurement);
		assertEquals((Integer) 6, ((IntegerMeasurement) measurements.get(0)).getValue());
		assertEquals("6", measurements.get(0).getLabel());
	}

	@Test
	public void testQueryResult_String() throws Exception {
		System.out.println("\n\n testQueryResult_String *****");
		String queryString = "return 'Animal';";//Class.all.select(t| t.Name='Animal').Name.flatten
		List<IMeasurement> measurements = sendQuery(queryString);	
		assertNotNull(measurements);
		assertNotNull(measurements.get(0));
		assertTrue(measurements.get(0) instanceof StringMeasurement);
		assertEquals("Animal", ((StringMeasurement) measurements.get(0)).getValue());
	}

	@Test
	public void testQueryResult_Boolean() throws Exception {
		System.out.println("\n\n testQueryResult_Boolean *****");
		String queryString = "return true;";
		List<IMeasurement> measurements = sendQuery(queryString);	
		assertNotNull(measurements);
		assertNotNull(measurements.get(0));
		assertTrue(measurements.get(0) instanceof BooleanMeasurement);
		assertEquals(true, ((BooleanMeasurement) measurements.get(0)).getValue());
	}

	@Test
	public void testQueryResult_Long() throws Exception {
		System.out.println("\n\n testQueryResult_Long *****");
		String queryString = "return 3l;";
		List<IMeasurement> measurements = sendQuery(queryString);	
		assertNotNull(measurements);
		assertNotNull(measurements.get(0));

		assertTrue(measurements.get(0) instanceof LongMeasurement);
		assertEquals((Long)3L, ((LongMeasurement) measurements.get(0)).getValue());
	}



	@Test
	public void testQueryResult_Double() throws Exception {
		System.out.println("\n\n testQueryResult_Double *****");
		String queryString = "return 3.5d;";
		List<IMeasurement> measurements = sendQuery(queryString);	
		assertNotNull(measurements);
		assertNotNull(measurements.get(0));
		assertTrue(measurements.get(0) instanceof DoubleMeasurement);
		assertEquals((Double) 3.5D, ((DoubleMeasurement) measurements.get(0)).getValue());
	}

	@Test
	public void testQueryResult_List() throws Exception {
		System.out.println("\n\n testListQueryResult_List *****");
		String queryString = "return  Sequence{1,2,3};";
		List<IMeasurement> measurements = sendQuery(queryString);	

		assertNotNull(measurements);

		assertNotNull(measurements.get(0));
		assertTrue(measurements.get(0) instanceof ListMeasurement);

		List<IMeasurement> measurements2 = ((ListMeasurement) measurements.get(0)).getList();
		assertEquals( 3, measurements2.size());

		assertTrue(measurements2.get(0) instanceof IntegerMeasurement);
		assertEquals(((IntegerMeasurement) (measurements2.get(0))).getValue() , (Integer)1);

		assertTrue(measurements2.get(1) instanceof IntegerMeasurement);
		assertEquals(((IntegerMeasurement) (measurements2.get(1))).getValue() , (Integer)2);

		assertTrue(measurements2.get(2) instanceof IntegerMeasurement);
		assertEquals(((IntegerMeasurement) (measurements2.get(2))).getValue() , (Integer)3);
	}

	@Test
	public void testQueryResult_Map() throws Exception {
		System.out.println("\n\n testQueryResult_Map*****");
		String queryString = "return Map{'a' = 1, 'b' = 2};";

		List<IMeasurement> measurements = sendQuery(queryString);
		assertNotNull(measurements);
		assertNotNull(measurements.get(0));
		assertTrue(measurements.get(0) instanceof MapMeasurement);

		assertTrue(measurements.get(0).getValues().get("a") instanceof IntegerMeasurement);
		assertEquals(((IntegerMeasurement) (measurements.get(0).getValues().get("a"))).getValue() , (Integer)1);


		assertTrue(measurements.get(0).getValues().get("b") instanceof IntegerMeasurement);
		assertEquals(((IntegerMeasurement) (measurements.get(0).getValues().get("b"))).getValue() , (Integer)2);

	}

	private List<IMeasurement> sendQuery(String queryString) throws Exception {
		HawkQueryMeasure measure = new HawkQueryMeasure();

		measure.getProperties().put(HawkQueryConstants.SERVER_URL, serverUrl);
		measure.getProperties().put(HawkQueryConstants.INSTANCE_NAME, instanceName);
		measure.getProperties().put(HawkQueryConstants.QUERY_LANGUAGE, queryLanguage);
		measure.getProperties().put(HawkQueryConstants.QUERY, queryString);
		try {
			List<IMeasurement> measurements = measure.getMeasurement();
			for(IMeasurement result :measurements){
				printMeasurement(result, "");
			}	

			return measurements;
		} catch (Exception e) {
			System.err.println("Exception encountered during test, Make sure Hawk server and instance_1 is running in local machine.");
			e.printStackTrace();
			throw e;
		}
	}

	private void printMeasurement(Object measurement, String identation) {
		printElement(measurement, identation);

		if(measurement instanceof ListMeasurement) {
			for(IMeasurement sub: ((ListMeasurement) measurement).getList()) {
				printMeasurement(sub, identation+">>");
			}
		} else if(measurement instanceof MapMeasurement) {
			for(Entry<String, Object> entry: ((MapMeasurement) measurement).getValues().entrySet()) {
				printMeasurement(entry.getValue(), identation + ">>" +  "Entry: " + entry.getKey());
			}
		} 
	}

	private void printElement(Object element, String identation) {

		System.out.print(identation);
		if(element instanceof IMeasurement) {
			System.out.println(((IMeasurement) element).getLabel());
		} else {
			System.out.println(element.toString());
		}
	}
}
