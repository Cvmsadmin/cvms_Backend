package org.ss.vendorapi.modal;

public class TrustMeterNMDetailsAccDTO {
	private String seq;
	private String uomCD;
	private String fullScale;
	private String digitsLeft;
	private String digitsRight;
	private String PrevReading;
	private String PrevReadDate;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUomCD() {
		return uomCD;
	}
	public void setUomCD(String uomCD) {
		this.uomCD = uomCD;
	}
	public String getFullScale() {
		return fullScale;
	}
	public void setFullScale(String fullScale) {
		this.fullScale = fullScale;
	}
	public String getDigitsLeft() {
		return digitsLeft;
	}
	public void setDigitsLeft(String digitsLeft) {
		this.digitsLeft = digitsLeft;
	}
	public String getDigitsRight() {
		return digitsRight;
	}
	public void setDigitsRight(String digitsRight) {
		this.digitsRight = digitsRight;
	}
	public String getPrevReading() {
		return PrevReading;
	}
	public void setPrevReading(String prevReading) {
		PrevReading = prevReading;
	}
	public String getPrevReadDate() {
		return PrevReadDate;
	}
	public void setPrevReadDate(String prevReadDate) {
		PrevReadDate = prevReadDate;
	}
}