package org.ss.vendorapi.modal;

public class ConsumpitonHistoryDTO {

	private String billNo;  
	private String billFrom; 
	private String billTo;
	private String meterReading_prev; 
	private String meterReading_curr;
	private String meterReading_unit;
	private String unitsBilled_consumption;
	private String unitsBilled_unit;  
	private String meterReadingDate;
	private String period;
	private String amount;
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillFrom() {
		return billFrom;
	}
	public void setBillFrom(String billFrom) {
		this.billFrom = billFrom;
	}
	public String getBillTo() {
		return billTo;
	}
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}
	public String getMeterReading_prev() {
		return meterReading_prev;
	}
	public void setMeterReading_prev(String meterReading_prev) {
		this.meterReading_prev = meterReading_prev;
	}
	public String getMeterReading_curr() {
		return meterReading_curr;
	}
	public void setMeterReading_curr(String meterReading_curr) {
		this.meterReading_curr = meterReading_curr;
	}
	public String getMeterReading_unit() {
		return meterReading_unit;
	}
	public void setMeterReading_unit(String meterReading_unit) {
		this.meterReading_unit = meterReading_unit;
	}
	public String getUnitsBilled_consumption() {
		return unitsBilled_consumption;
	}
	public void setUnitsBilled_consumption(String unitsBilled_consumption) {
		this.unitsBilled_consumption = unitsBilled_consumption;
	}
	public String getUnitsBilled_unit() {
		return unitsBilled_unit;
	}
	public void setUnitsBilled_unit(String unitsBilled_unit) {
		this.unitsBilled_unit = unitsBilled_unit;
	}
	public String getMeterReadingDate() {
		return meterReadingDate;
	}
	public void setMeterReadingDate(String meterReadingDate) {
		this.meterReadingDate = meterReadingDate;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}