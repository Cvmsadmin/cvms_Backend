package org.ss.vendorapi.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.ss.vendorapi.entity.FileUploadRequestModal;
import org.ss.vendorapi.entity.FileUploadRequestModal.DocumentType;
import org.ss.vendorapi.repository.DocumentUploadRepository;
import org.ss.vendorapi.service.SftpUploaderService;



@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class DocumentFileUploadController {
	
	@Autowired
	private SftpUploaderService sftpUploaderService;
	
	@Autowired
    private DocumentUploadRepository documentUploadRepository;
	
	
	
	@PostMapping("/uploadClientDoc")
    public String uploadFile(@RequestParam("file") MultipartFile file, 
                             @RequestParam("clientName") String clientName, 
                             @RequestParam("projectName") String projectName, 
                             @RequestParam("documentType") DocumentType documentType, 
                             @RequestParam("applicantId") String applicantId) {
        if (file.isEmpty()) {
            return "File is empty";
        }

        // Define base directory
        String baseDir = "/opt/cvmsdocuments/Client";
        String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, clientName, projectName);

        if (uploadStatus.startsWith("File uploaded successfully")) {
            // Create and save FileUploadRequestModal entity
            FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
            fileUploadRequest.setApplicantId(applicantId);
            fileUploadRequest.setDocumentType(documentType);
            fileUploadRequest.setDocumentPath(baseDir + "/" + clientName + "/" + projectName + "/" + file.getOriginalFilename());

            documentUploadRepository.save(fileUploadRequest);
            return uploadStatus;
        } else {
            return uploadStatus;
        }
    }
	
	
	@PostMapping("/uploadVendorDoc")
	public String uploadVendorFile(@RequestParam("file") MultipartFile file, 
	                               @RequestParam("vendorName") String vendorName, 
	                               @RequestParam("documentType") DocumentType documentType, 
	                               @RequestParam("applicantId") String applicantId) {
	    if (file.isEmpty()) {
	        return "File is empty";
	    }

	    String baseDir = "/opt/cvmsdocuments/Vendor";
	    String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, vendorName);

	    if (uploadStatus.startsWith("File uploaded successfully")) {
	        FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
	        fileUploadRequest.setApplicantId(applicantId);
	        fileUploadRequest.setDocumentType(documentType);
	        fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + file.getOriginalFilename());

	        documentUploadRepository.save(fileUploadRequest);
	        return uploadStatus;
	    } else {
	        return uploadStatus;
	    }
	}

	@GetMapping("/downloadVendor")
	public ResponseEntity<byte[]> downloadVendorFile(
	        @RequestParam("vendorName") String vendorName,
	        @RequestParam("fileName") String fileName) {

	    String remoteFilePath = "/opt/cvmsdocuments/Vendor/" + vendorName + "/ERP/" + fileName;

	    byte[] fileContent;
	    try {
	        fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }

	    if (fileContent == null || fileContent.length == 0) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }

	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
	            .body(fileContent);
	}
	
	    
	    
	@GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(
            @RequestParam("clientName") String clientName,
            @RequestParam("projectName") String projectName,
            @RequestParam("fileName") String fileName) {

        // Construct the remote file path
        String remoteFilePath = "/opt/cvmsdocuments/Client/" + clientName + "/" + projectName + "/" + fileName;

        byte[] fileContent;
        try {
            // Retrieve the file from the server
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Check if file content is empty
        if (fileContent == null || fileContent.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Create the HTTP response with file content
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileContent);
    }
}


	
	

