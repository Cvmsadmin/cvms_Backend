
/**
 * Implemented CR No 13/Oct/2023 and Request Reference No C72.
 * 
 * This code contains proprietary information of R-APDRP project and has been developed by
 *  Technologies Ltd. for UP Power Corporation Limited (UPPCL) and its 4 Discoms viz. MVVNL, PVVNL, DVVNL and PuVVNL.
 * No part of this code may be reproduced, stored, copied, or transmitted in any form
 * or by means of electronic, mechanical, photocopying or otherwise, without the consent of Program Director, HCL
 * Technologies Ltd.
 * 
 * This code is intended only for internal circulation within  Technologies Ltd, UPPCL and 4 Discoms.
 * 
 *@author @author Sony
 * 
 * 
 */
package org.ss.vendorapi.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "FEEDBACK")
public class FeedbackModel implements Serializable{

	private static final long serialVersionUID = 2L;
	@Id
	//@Column(name="TRACK_ID")
    private String trackId;
	//@Column(name="NAME")
    private String name;
	//@Column(name="ADDRESS")
    private String address;
    @Column(name="ACC_NO")
    private String accNo;
    @Column(name="SERV_CONN_NO")
    private String servConnNo;
    //@Column(name="PIN")
    private String pin;
    //@Column(name="CITY")
    private String city;
    //@Column(name="STATE")
    private String state;
    //@Column(name="COUNTRY")
    private String country;
    //@Column(name="EMAIL")
    private String email;
    //@Column(name="COMMENTS")
    private String comments;
    //@Column(name="COL1")
    private String col1;
    //@Column(name="COL2")
    private String col2;//phoneNo
    //@Column(name="COL3")
    private String col3;
    //@Column(name="COL4")
    private String col4;
    //@Column(name="COL5")
    private String col5;    
    //@Column(name="DISCOM_NAME")
    private String discomName;

}
