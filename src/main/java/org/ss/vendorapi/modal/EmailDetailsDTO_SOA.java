package org.ss.vendorapi.modal;

public class EmailDetailsDTO_SOA {
    private String moduleName, to, from, subject, contentType,content,attachmentType,knumber;
    /**
     * 
     * @return
     */
    public String getKnumber() {
		return knumber;
	}
    /**
     * 
     * @param knumber
     */
	public void setKnumber(String knumber) {
		this.knumber = knumber;
	}

	private byte[] attachmentData;

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
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the attachmentType
	 */
	public String getAttachmentType() {
		return attachmentType;
	}

	/**
	 * @param attachmentType the attachmentType to set
	 */
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	/**
	 * @return the attachmentData
	 */
	public byte[] getAttachmentData() {
		return attachmentData;
	}

	/**
	 * @param attachmentData the attachmentData to set
	 */
	public void setAttachmentData(byte[] attachmentData) {
		this.attachmentData = attachmentData;
	}

}
