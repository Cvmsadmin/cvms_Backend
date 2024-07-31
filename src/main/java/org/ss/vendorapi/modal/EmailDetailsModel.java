 /**
 * This code contains copyright information which is the proprietary property
 * of Uttar Pradesh Power Corporation Ltd. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of Uttar
 * Pradesh Power Corporation Ltd.
 *
 * Copyright (C) Uttar Pradesh Power Corporation Ltd. 2010
 * All rights reserved.
 * 
 *@author SONY
 */
package org.ss.vendorapi.modal;


public class EmailDetailsModel extends UPPCLModel {
	
	private String moduleName;
	
	private String from;
	
	private String subject;
	
	private String contentType;
	
	private String htmlPath;
	
	private String htmlBody;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

	
}
