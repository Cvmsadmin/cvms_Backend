package org.ss.vendorapi.entity;

import java.io.Serializable;

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
    private String applicantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private String documentPath;
    
   

   
    public enum DocumentType {
        CM, VM, DWA, NDA, PAN, GST_CERTIFICATE, PO, MAF, E_WAY_BILL, INVOICE_UPLOAD,
        DELIVERY_ACCEPTANCE, PROPOSAL_UPLOAD, MISCELLANEOUS, AGREEMENT, RFP, BOM, BOQ, NIT,FRS,

    }

	public String getDocumentPath(String string) {
		return documentPath;
		
	}
    
    
    
//    public Long getDocumentId() {
//        return documentId;
//    }
//
//    public void setDocumentId(Long documentId) {
//        this.documentId = documentId;
//    }
//
//    public String getApplicantId() {
//        return applicantId;
//    }
//
//    public void setApplicantId(String applicantId) {
//        this.applicantId = applicantId;
//    }
//
//    public DocumentType getDocumentType() {
//        return documentType;
//    }
//
//    public void setDocumentType(DocumentType documentType) {
//        this.documentType = documentType;
//    }
//
//    public String getDocumentPath() {
//        return documentPath;
//    }
//
//    public void setDocumentPath(String documentPath) {
//        this.documentPath = documentPath;
//    }

   

	


	
}
