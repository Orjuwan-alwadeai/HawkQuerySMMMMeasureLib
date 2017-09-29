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
package org.hawk.measure.impl;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.hawk.service.api.Hawk;
import org.hawk.service.api.HawkInstance;
import org.hawk.service.api.HawkQueryOptions;
import org.hawk.service.api.QueryResult;
import org.hawk.service.api.QueryResult._Fields;
import org.hawk.service.api.utils.APIUtils;
import org.hawk.service.api.utils.APIUtils.ThriftProtocol;
import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.IntegerMeasurement;
import org.measure.smm.measure.defaultimpl.measures.DirectMeasure;


public class HawkQueryMeasure extends DirectMeasure {
	private Hawk.Client client;
	private ThriftProtocol clientProtocol;
	private String currentInstance;


	private String serverUrl;
	private String username;
	private String password;

	private String instanceName;

	private String queryLanguage;
	private String query;

	/** queryOptions */
	/**  (optional)	The default namespaces to be used to resolve ambiguous unqualified types. */
	private String defaultNamespaces; 									

	/**  (optional)	The file patterns for the query (e.g. *.uml). */
	private List<String> filePatterns = new ArrayList<String>(); 									

	/**  (optional)	Whether to include attributes (true) or not (false) in model element results. */
	private Boolean includeAttributes; 									

	/**  (optional)	Whether to include all the child elements of the model element results (true) or not (false). */
	private Boolean includeContained; 									

	/**  (optional)	Whether to include derived attributes (true) or not (false) in model element results. */
	private Boolean includeDerived; 									

	/**  (optional)	Whether to include node IDs (true) or not (false) in model element results. */
	private Boolean includeNodeIDs; 									

	/**  (optional)	Whether to include references (true) or not (false) in model element results. */
	private Boolean includeReferences;  								
	
	/**  (optional)	The repository for the query (or * for all repositories). */
	private String repository;  	

	/**
	 * Performs all steps needed to execute a query. It connects to Hawk server, 
	 * select Hawk instance, send query and process response. 
	 * after executing query
	 * @return list of measurements the returned by Hawk Server after executing
	 * query
	 */
	public List<IMeasurement> getMeasurement() throws Exception {
		
		List<IMeasurement> result = new ArrayList<IMeasurement>();;
		try {
			// retrieve Properties
			initProperties();

			// check query is valid
			if(query == null || query.isEmpty()) {
				// throw exception
	            throw new Exception("No Query is specified");
			}
			
			// connect to server if not connected
			connect(serverUrl, username, password);

			// select instance if not selected
			selectInstance(instanceName);

			// send query
			QueryResult ret = executeQuery();

			// process result
			if(ret != null) {
				IMeasurement measurement = processQueryResult(ret);
				result.add(measurement);
			} else {
	            throw new Exception("Query Result is null");
			}

			// disconnect
			disconnect();
			
		} catch (Exception e) {
            throw new Exception("Error during Measure Execution : " + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Retrieves the measure's properties and store them locally. 
	 */
	public void initProperties() {
		this.serverUrl = this.getProperty(HawkQueryConstants.SERVER_URL);
		
		this.username = this.getProperty(HawkQueryConstants.USERNAME);
		
		this.password = this.getProperty(HawkQueryConstants.PASSWORD);
		
		this.instanceName = this.getProperty(HawkQueryConstants.INSTANCE_NAME);

		this.queryLanguage = this.getProperty(HawkQueryConstants.QUERY_LANGUAGE);
		if(this.queryLanguage == null) {
			this.queryLanguage = "org.hawk.epsilon.emc.EOLQueryEngine";
		}
		
		this.query = this.getProperty(HawkQueryConstants.QUERY);

		this.defaultNamespaces = this.getProperty(HawkQueryConstants.DEFAULT_NAMESPACES);

		String filePatternsString = this.getProperty(HawkQueryConstants.FILE_PATTERNS);
		if(filePatternsString != null) {
			String[] patterns = filePatternsString.split(",");
			this.filePatterns = Arrays.asList(patterns);
		}

		this.includeAttributes = Boolean.valueOf(this.getProperty(HawkQueryConstants.INCLUDE_ATTRIBUTES));
		if(this.includeAttributes == null) {
			this.includeAttributes = true;
		}

		this.includeContained = Boolean.valueOf(this.getProperty(HawkQueryConstants.INCLUDE_CONTAINED));
		if(this.includeAttributes == null) {
			this.includeAttributes = true;
		}
		this.includeDerived = Boolean.valueOf(this.getProperty(HawkQueryConstants.INCLUDE_DERIVED));
		if(this.includeDerived == null) {
			this.includeDerived = true;
		}
		this.includeNodeIDs = Boolean.valueOf(this.getProperty(HawkQueryConstants.INCLUDE_NODE_IDs));
		if(this.includeNodeIDs == null) {
			this.includeNodeIDs = false;
		}
		this.includeReferences = Boolean.valueOf(this.getProperty(HawkQueryConstants.INCLUDE_REFERENCES));
		if(this.includeReferences == null) {
			this.includeReferences = true;
		}

		this.repository = this.getProperty(HawkQueryConstants.REPOSITORY);
		//if (this.repository == null) {
			///this.repository = "*";
		//}
	}

	/**
	 * Connects to a Hawk Server.
	 * @param url the Hawk server URL
	 * @param username username needed to access Hawk server if required. 
	 * @param password password needed to access Hawk server if required.
	 * @throws Exception when connection fails
	 * 
	 */
	public void connect(String url, String username, String password) throws Exception {
		clientProtocol = ThriftProtocol.guessFromURL(url);
		if (client != null) {
			final TTransport transport = client.getInputProtocol().getTransport();
			transport.close();
		}
		client =  APIUtils.connectTo(Hawk.Client.class, url, clientProtocol, username, password);
	}
	
	/**
	 * Frees connection to Hawk Server.
	 * @throws Exception if something goes wrong
	 * 
	 */
	public void disconnect() throws Exception {
		if (client != null) {
			final TTransport transport = client.getInputProtocol().getTransport();
			transport.close();

			client = null;
			currentInstance = null;
			username = null;
			password = null;
		}	
	}

	/**
	 * Selects a Hawk instance 
	 * @param name the Hawk instance name
	 * @throws Exception if selection fails
	 * 
	 */
	public void selectInstance(String name) throws Exception {
		checkConnected();
		findInstance(name);
		currentInstance = name;
	}

	/**
	 * Sends query to the selected Hawk instance and returns the result.
	 * @return query result
	 * @throws Exception if query fails
	 * 
	 */
	public QueryResult executeQuery() throws Exception  {
		checkInstanceSelected();

		HawkQueryOptions opts = new HawkQueryOptions();
		opts.setDefaultNamespaces(defaultNamespaces);
		opts.setFilePatterns(filePatterns);
		opts.setRepositoryPattern(repository);
		opts.setIncludeAttributes(includeAttributes);
		opts.setIncludeReferences(includeReferences);
		opts.setIncludeNodeIDs(includeNodeIDs);
		opts.setIncludeContained(includeContained);

		QueryResult ret = null;

		ret = client.query(currentInstance, query, queryLanguage, opts);
				
		return ret;
	}

	/**
	 * Processes the query result to produce measurement values.
	 * @param ret query result
	 * @return a measurement object
	 * 
	 */
	public IMeasurement processQueryResult(QueryResult ret) {
		IMeasurement measurement = null;
		_Fields _field = ret.getSetField();

		switch(_field) {
		case V_SHORT:
			measurement = new ShortMeasurement();
			((ShortMeasurement)measurement).setValue(ret.getVShort());
			break;
		case  V_STRING:
			measurement = new StringMeasurement();
			((StringMeasurement)measurement).setValue(ret.getVString());
			break;
		case V_MODEL_ELEMENT_TYPE:
			measurement = new ModelElementTypeMeasurement();
			((ModelElementTypeMeasurement)measurement).setValue(ret.getVModelElementType());
			break;
		case V_MODEL_ELEMENT:
			measurement = new ModelElementMeasurement();
			((ModelElementMeasurement)measurement).setValue(ret.getVModelElement());
			break;
		case V_MAP:
			measurement = new MapMeasurement();
			for( Entry<String, QueryResult> entry : ret.getVMap().entrySet()) {
				IMeasurement measurementInMap = processQueryResult(entry.getValue());
				((MapMeasurement)measurement).add(entry.getKey(), measurementInMap);
			}
			break;
		case V_LONG:
			measurement = new LongMeasurement();
			((LongMeasurement)measurement).setValue(ret.getVLong());
			break;
		case V_LIST:
			measurement = new ListMeasurement();
			for(QueryResult retListValue : ret.getVList()) {
				IMeasurement measurementInList = processQueryResult(retListValue);
				((ListMeasurement)measurement).add(measurementInList);
			}
			break;
		case V_INTEGER:
			measurement = new IntegerMeasurement();
			((IntegerMeasurement)measurement).setValue(ret.getVInteger());
			break;
		case V_DOUBLE:
			measurement = new DoubleMeasurement();
			((DoubleMeasurement)measurement).setValue(ret.getVDouble());
			break;
		case V_BYTE:
			measurement = new ByteMeasurement();
			((ByteMeasurement)measurement).setValue(ret.getVByte());
			break;
		case V_BOOLEAN:
			measurement = new BooleanMeasurement();
			((BooleanMeasurement)measurement).setValue(ret.getVBoolean());
			break;
		}
		return measurement;
	}

	/**
	 * Checks if client is connected to Hawk Server
	 * @throws ConnectException if not connected 
	 */
	public void checkConnected() throws ConnectException {
		if (client == null) {
			throw new ConnectException("Please connect to a Thrift endpoint first!");
		}
	}

	/**
	 * Checks if the Hawk instance specified in properties is selected on
	 * Hawk Server.
	 * @throws ConnectException if not selected 
	 */
	public void checkInstanceSelected() throws ConnectException {
		checkConnected();
		if (currentInstance == null) {
			throw new IllegalArgumentException("No Hawk instance has been selected");
		}
	}

	/**
	 * Finds a Hawk instance with 'name'
	 * @param name Hawk instance name
	 * @return the Hawk instance
	 * @throws TException if instance not found
	 */
	public HawkInstance findInstance(final String name) throws TException {
		for (HawkInstance i : client.listInstances()) {
			if (i.name.equals(name)) {
				return i;
			}
		}
		throw new NoSuchElementException(String.format("No instance exists with the name '%s'", name));
	}
}
