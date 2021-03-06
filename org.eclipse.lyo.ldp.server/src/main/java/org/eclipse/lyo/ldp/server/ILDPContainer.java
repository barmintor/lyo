/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation.
 *
 *	All rights reserved. This program and the accompanying materials
 *	are made available under the terms of the Eclipse Public License v1.0
 *	and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 *	
 *	The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *	and the Eclipse Distribution License is available at
 *	http://www.eclipse.org/org/documents/edl-v10.php.
 *	
 *	Contributors:
 *	
 *	   Steve Speicher - support for various container types
 *	   Samuel Padgett - remove setConfigParameters() method from interface
 *	   Samuel Padgett - add support for LDP Non-RDF Source
 *******************************************************************************/
package org.eclipse.lyo.ldp.server;

import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


public interface ILDPContainer extends ILDPRDFSource {

	/**
	 * Execute the specified SPARQL query over the container and its entries,
	 * specifying the output format. Supported formats are "application/sparql-results+xml"
	 * and "application/sparql-results+json".
	 * @param outStream the output stream in which to write the query result.
	 * @param queryString the SPARQL query to execute.
	 * @param resultsFormat the desired result format content type ; if <code>null</code>
	 * or not supported, the format will be XML.
	 */
	public abstract void query(OutputStream outStream, String queryString,
			String resultsFormat);

	/**
	 * Post a new member to the container.
	 * <p>The Content-Type of the input stream is specified by the
	 * <code>contentType</code> argument. Supported values are "application/rdf+xml",
	 * "text/turtle", and "application/x-turtle".</p>
	 * @param stream the input stream containing the posted resource representation.
	 * @param contentType the Content-Type of the input stream.
	 * @param user The user URI to use for dcterms:creator
	 * @param nameHint Value from Slug header or other source, used to determine the newly created resource's URL 
	 * @param isResourceInteractionModel If true, treat container as resource (don't allow POST)
	 * @return the new resource URI
	 */
	public abstract String post(InputStream stream, String contentType,
			String user, String nameHint, boolean isResourceInteractionModel);

	/**
	 * Post a new LDP-NR (non-RDF source) to the container.
	 * @param stream the input stream containing the posted resource representation.
	 * @param contentType the Content-Type of the input stream.
	 * @param nameHint Value from Slug header or other source, used to determine the newly created resource's URL 
	 * 
	 * @return the new resource URI
	 */
	public abstract Response postNonRDFSource(InputStream content, String stripCharset, String user, String slug);
	
	
	/**
	 * Creates a new resource at resourceURI with the contents of the specified stream.
	 * <p>The Content-Type of the input stream is specified by the
	 * <code>contentType</code> argument.</p>
	 * @param resourceURI the URI of to-be-created resource.
	 * @param stream the input stream containing the resource representation.
	 * @param contentType the Content-Type of the input stream.
	 * @param requestHeaders the HTTP request headers
	 * @return true if a new resource was created or false if the resource already existed
	 */
	public abstract boolean putCreate(String resourceURI, InputStream stream,
			String contentType, String user, HttpHeaders requestHeaders);
}