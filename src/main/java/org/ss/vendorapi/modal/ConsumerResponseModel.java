package org.ss.vendorapi.modal;

import java.io.Serializable;

public class ConsumerResponseModel implements Serializable{
	private static final long serialVersionUID = 2L;
	
	private String kno;
	private String name;
    private String discome;

    
    public String getDiscome() {
		return discome;
	}

	public void setDiscome(String discome) {
		this.discome = discome;
	}

	
    public String getName() {
        return name;
    }

    public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}
    public void setName(String name) {
        this.name = name;
    }

}
