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
package org.hawk.measure.model;

import java.util.ArrayList;
import java.util.List;

import org.hawk.measure.impl.HawkQueryConstants;
import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.measure.model.ScopeProperty;

public class HawkQuerySMMMMeasure extends SMMMeasure {
	private List<String> defaultNamespaces = new ArrayList<String>(); 

	private List<String> filePatterns = new ArrayList<String>(); 									

	private List<String> repository = new ArrayList<String>();  			

	public HawkQuerySMMMMeasure() {
		super();
		this.setName("HawkQueryMeasure");
		this.setDescription("Generic Query for Hawk Server tool");
		this.setType(MeasureType.DIRECT);
		this.setUnite(MeasureUnite.Undefined);

		this.setServerUrl("http://localhost:8080/thrift/hawk/tuple");
		this.setUsername("");
		this.setPassword("");
		this.setInstanceName("");
		this.setQueryLanguage("org.hawk.epsilon.emc.EOLQueryEngine");
		this.setQuery("");
		this.addDefaultNamespace("");
		this.addFilePattern("");
		this.setIncludeAttributes(true);
		this.setIncludeContained(true);
		this.setIncludeDerived(true);
		this.setIncludeNodeIDs(false);
		this.setIncludeReferences(true);
		this.addRepository("");
	}

	/***
	 * 
	 * @param name	the name of the measure
	 * @param description description of the measure
	 */
	public HawkQuerySMMMMeasure(String name, String description) {
		this();
		this.setName(name);
		this.setDescription(description);
	}

	/**
	 *  Sets a required property "serverUrl" 
	 * @param serverUrl the URL of Hawk Server to connect to
	 */
	public void setServerUrl(String serverUrl) {
		replaceScopeProperty(HawkQueryConstants.SERVER_URL, serverUrl, "Server URL inclusing Protocol");
	}

	/**
	 * Sets an optional property "username"
	 * @param username the username used to access Hawk Server
	 */
	public void setUsername(String username) {
		replaceScopeProperty(HawkQueryConstants.USERNAME, username, "username");
	}

	/**
	 * Sets an optional property "password"
	 * @param password the password used to access Hawk Server
	 */
	public void setPassword(String password) {
		replaceScopeProperty(HawkQueryConstants.PASSWORD, password, "passeword");
	}

	/**
	 * Sets a required property "instanceName"
	 * @param instanceName the Hawk instance name to send queries to
	 */
	public void setInstanceName(String instanceName) {
		replaceScopeProperty(HawkQueryConstants.INSTANCE_NAME, instanceName, "Hawk instance to Query");
	}

	/**
	 * Sets a required property "queryLanguage"
	 * @param queryLanguage the language used to specify queries 
	 */
	public void setQueryLanguage(String queryLanguage) {
		replaceScopeProperty(HawkQueryConstants.QUERY_LANGUAGE, queryLanguage, "Query Language");
	}

	/**
	 * Sets a required property "query"
	 * @param query the query logic body to be executed
	 */
	public void setQuery(String query) {
		replaceScopeProperty(HawkQueryConstants.QUERY, query, "Query Logic");
	}

	/**
	 * Sets an  optional property "defaultNamespaces"
	 * @param defaultNamespaces The default namespaces to be used to resolve ambiguous unqualified types. 
	 */
	public void setDefaultNamespaces(String defaultNamespaces) {
		replaceScopeProperty(HawkQueryConstants.DEFAULT_NAMESPACES, defaultNamespaces, "(optional) The default namespaces to be used to resolve ambiguous unqualified types.");
	}

	/**
	 * Sets an  optional property "includeAttributes"
	 * @param includeAttributes Whether to include attributes (true) or not (false) in model element results. 
	 */
	public void setIncludeAttributes(boolean includeAttributes) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_ATTRIBUTES, String.valueOf(includeAttributes), "");
	}

	/**
	 * Sets an  optional property "includeContained"
	 * @param includeContained Whether to include all the child elements of the model element results (true) or not (false). 
	 */
	public void setIncludeContained(boolean includeContained) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_CONTAINED, String.valueOf(includeContained), "");
	}

	/**
	 * Sets an  optional property "includeDerived"
	 * @param includeDerived Whether to include derived attributes (true) or not (false) in model element results. 
	 */
	public void setIncludeDerived(boolean includeDerived) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_DERIVED, String.valueOf(includeDerived), "");
	}

	/**
	 * Sets an  optional property "includeNodeIDs"
	 * @param includeNodeIDs 	Whether to include node IDs (true) or not (false) in model element results. 
	 */
	public void setIncludeNodeIDs(boolean includeNodeIDs) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_NODE_IDs, String.valueOf(includeNodeIDs), "");
	}

	/**
	 * Sets an  optional property "includeReferences"
	 * @param includeReferences Whether to include references (true) or not (false) in model element results.
	 */
	public void setIncludeReferences(boolean includeReferences) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_REFERENCES, String.valueOf(includeReferences), "");
	}

	/**
	 * add a default namespace to list of default namespaces
	 * @param newValue  default namespace to be used to resolve ambiguous unqualified types. 
	 */
	public void addDefaultNamespace(String newValue) {
		this.defaultNamespaces.add(newValue);
		String property = listToCommaSeperatedString(this.defaultNamespaces);
		replaceScopeProperty(HawkQueryConstants.DEFAULT_NAMESPACES, property, "(optional) The default namespaces to be used to resolve ambiguous unqualified types.");
	}

	/**
	 *  add a file pattern(s) to list of file pattterns
	 * @param newValue String file pattern(s) for the query (e.g. *.uml). 
	 */
	public void addFilePattern(String newValue) {
		this.filePatterns.add(newValue);
		String property = listToCommaSeperatedString(this.filePatterns);
		replaceScopeProperty(HawkQueryConstants.FILE_PATTERNS, property, "(optional)	The file patterns for the query (e.g. *.uml)");
	}

	/**
	 * adds a new repository to list of repositories
	 * @param newValue repository for the query (or * for all repositories).
	 */
	public void addRepository(String newValue) {
		this.repository.add(newValue);
		String property = listToCommaSeperatedString(this.repository);
		replaceScopeProperty(HawkQueryConstants.REPOSITORY, property, "(optional)	The repository for the query (or * for all repositories)");
	}

	/**
	 * creates and adds new scope properties
	 * @param name the Scope Property name
	 * @param defaultValue of the property
	 * @param description the scope properties Description
	 * @return the newly created scope properties
	 */
	public ScopeProperty addScopeProperty(String name, String defaultValue, String description) {

		ScopeProperty property = new ScopeProperty();

		property.setName(name);
		property.setDefaultValue(defaultValue);
		property.setDescription(description);

		this.getScopeProperties().add(property);

		return property;
	}

	/**
	 * replace an old value in scope properties with a new one
	 * @param name the Scope Property name
	 * @param defaultValue of the property
	 * @param description the scope properties Description
	 * 
	 */
	public void replaceScopeProperty(String name, String defaultValue, String description) {

		for(ScopeProperty property : this.getScopeProperties()) {
			if(property.getName().equals(name)) {
				// replace
				property.setDefaultValue(defaultValue);
				property.setDescription(description);
				return;
			}
		}

		// else add
		addScopeProperty(name, defaultValue, description);

	}

	/**
	 * remoes a scope properties
	 * @param name the Scope Property name
	 * 
	 */
	public void removeScopeProperty(String name) {
		ScopeProperty propertyToRemove = null;
		for(ScopeProperty property : this.getScopeProperties()) {
			if(property.getName().equals(name)) {
				// replace
				propertyToRemove = property;
			}
		}

		// else add
		if(propertyToRemove != null) {
			this.getScopeProperties().remove(propertyToRemove);
		}

	}

	private String listToCommaSeperatedString(List<String> list) {
		StringBuffer property = new StringBuffer();

		for(String item : list) {
			if(property.length() > 0) {
				property.append(',');
			}
			property.append(item);
		}
		return property.toString();
	}
}
