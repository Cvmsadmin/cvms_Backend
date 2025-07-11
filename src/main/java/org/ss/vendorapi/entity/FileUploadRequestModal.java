package org.ss.vendorapi.entity;
 
import java.io.Serializable;

import javax.validation.constraints.NotNull;

//import org.ss.vendorapi.entity.FileUploadRequestModal.DocumentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@NoArgsConstructor @Setter @Getter @AllArgsConstructor
@Entity
@Table(name = "DocumentUpload")
public class FileUploadRequestModal extends ParentEntity implements Serializable{
	

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long documentId;
 
    @Column(nullable = false)
    @NotNull
    private String applicantId;
 
    @Column(nullable = false)
    private String documentPath;
       
    @Column(nullable = false)
    private String documentType;   
 
	public String getDocumentPath(String string) {
		return documentPath;
	}	
	
//  @Enumerated(EnumType.STRING)
//  @Column(nullable = false)
//  private DocumentType documentType;
//  
//  public enum DocumentType {
//
//      CM, VM, DWA, NDA, PAN, GST_CERTIFICATE, PO, MAF, E_WAY_BILL, INVOICE,
//
//      DELIVERY_ACCEPTANCE, PROPOSAL_UPLOAD, MISCELLANEOUS, AGREEMENT, RFP, BOM, BOQ, NIT, FRS, ROCS, MSME, DECLARATION
//  }
		
}

 