package org.ss.vendorapi.enums;

public enum PaymentConstants {

    PENDING("4"), SUCESSFUL("1"), REJECTED("2"), INITIATED("0"), SCH_STATUS_YES("Y"), SCH_STATUS_NO("N"), SUCCESS_CODE2(
            "sucessCode2"), SUCCESS_CODE1("sucessCode1"), SUCCESS("SUCCESS"), FAIL("FAIL"), REJECT("REJECT"), FAILED(
            "FAILED"), Y("Y"), RESULT("result"), TRANSID("tranid"), TRACKID("trackid"), AMT("amt"), ERROR("Error"), CAPTURED(
            "CAPTURED"), APPORVED("APPORVED"), VPC_RETURN("vpcURL_return"), PROXY_HOST("10.112.62.72"),SUCCESSCODE_P("successcode_p"),
            SUCCESSCODE_N("successcode_n"),SUCCESSCODE_R("successcode_r"),SUCCESSCODE_Y("successcode_y"),SUCCESSCODE_D("successcode_d"),PENDING_TPSL("PENDING");

    private String constantValue = null;

    PaymentConstants(String constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public String toString() {
        return constantValue;
    }

}
