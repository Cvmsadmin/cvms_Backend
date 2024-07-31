package org.ss.vendorapi.modal.response;

import lombok.Data;

@Data
public class ChatBotResponse {
	public String consumerName;
	public String paymentAmount;
	public String paymentDate;
	public String paymentReceipt;
}