package com.techm.app.bean;
public class SMSData {
	String smsText;
	String attributeJson;

	public SMSData() {
		super();
	}

	public SMSData(final String smsText, final String attributeJson) {
		super();
		this.smsText = smsText;
		this.attributeJson = attributeJson;
	}

	/**
	 * Return SMS text
	 * 
	 * @return
	 */
	public String getSmsText() {
		return smsText;
	}

	/**
	 * @param smsText
	 */
	public void setSmsText(final String smsText) {
		this.smsText = smsText;
	}

	/**
	 * Return AttributeJson
	 * 
	 * @return
	 */
	public String getAttributeJson() {
		return attributeJson;
	}

	/**
	 * @param attributeJson
	 */
	public void setAttributeJson(final String attributeJson) {
		this.attributeJson = attributeJson;
	}

}



from gitlocalrepo
