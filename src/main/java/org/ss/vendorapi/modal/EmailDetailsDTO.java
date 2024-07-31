package org.ss.vendorapi.modal;

public class EmailDetailsDTO {
	private String moduleName;
	
	private String from;
	
	private String subject;
	
	private String contentType;
	
	private String htmlPath;
	
	private String htmlBody;

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the htmlPath
	 */
	public String getHtmlPath() {
		return htmlPath;
	}

	/**
	 * @param htmlPath the htmlPath to set
	 */
	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	/**
	 * @return the htmlBody
	 */
	public String getHtmlBody() {
		return htmlBody;
	}

	/**
	 * @param htmlBody the htmlBody to set
	 */
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
}
