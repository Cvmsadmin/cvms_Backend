package org.ss.vendorapi.resources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix="spring.ss")
@Configuration
@Data
public class PaymentKeyProperties {
	private String pmMerchantId;
	private String pmMerchantId1;
	private String pmMerchantId2;
	private String pmMerchantId3;
	private String pmMerchantId4;
	private String pmMerchantId5;
	private String pmClientId;
	private String pmCreateOrderUrl;
	private String pmCreateOrderReturnUrl;
	private String pmRetrieveTransactionUrl;
	private String pgBillPaymRedirectionUrl;
	private String pmSecretKey;
	private String escom1;
	private String escom2;
	private String escom3;
	private String escom4;
	private String escom5;
	private String paymentStatusType;
	private String processPaymentNoOfDays;
}
