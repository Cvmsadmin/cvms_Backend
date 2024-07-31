package org.ss.vendorapi.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "S2SPAYMENTDETAILS")
public class S2SPaymentDTO implements Serializable  {

	private static final long serialVersionUID = 2L;

	@Id
	@Column(name = "track_id", nullable = false) 
    private String trackId;
	
	@Column(name = "MSG")
    private String MSG;

	@Column(name = "MID")
    private String MID;

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getMSG() {
		return MSG;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}
}
