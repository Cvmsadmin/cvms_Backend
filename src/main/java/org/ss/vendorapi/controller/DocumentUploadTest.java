//package org.ss.vendorapi.controller;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//

//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.ss.vendorapi.advice.EncryptResponse;

//import javax.validation.constraints.NotNull;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.ss.vendorapi.advice.EncryptResponse;
//import org.ss.vendorapi.entity.FileUploadRequestModal;
//import org.ss.vendorapi.entity.ParentEntity;
//import org.ss.vendorapi.repository.DocumentUploadRepository;
//import org.ss.vendorapi.service.InvoiceEmailSchedulerService;
//import org.ss.vendorapi.service.SftpUploaderService;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("v2/api")
//public class DocumentUploadTest {
//	
//	  @Autowired
//	    private SftpUploaderService sftpUploaderService;
//	 
//	    @Autowired
//	    private DocumentUploadRepository documentUploadRepository;
//	    
//	    @Autowired
//	    private InvoiceEmailSchedulerService invoiceEmailSchedulerService;
//	
//    @PostMapping("/uploadClientDataDocuments")
//    public ResponseEntity<?> uploadClientDataDocuments(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("clientName") String clientName,
//            @RequestParam("documentTypes") List<String> documentTypes,
//            @RequestParam("applicantId") String applicantId) {
//
//        Map<String, Object> response = new HashMap<>();
//        List<String> uploadResults = new ArrayList<>();
//
//        if (files.size() != documentTypes.size()) {
//            response.put("status", "FAIL");
//            response.put("statusMsg", "The number of files and document types must match.");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        boolean allUploadsSuccessful = true;
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            String documentType = documentTypes.get(i); // e.g., Bridge_NDA
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String newFileName = documentType + getFileExtension(file.getOriginalFilename());
//            String remotePath = "/opt/cvmsdocuments/client/" + clientName + "/" + newFileName;
//
//            try {
//                // Upload to SFTP
//                String uploadStatus = sftpUploaderService.uploadFileToServer(
//                        file, "/opt/cvmsdocuments/client", clientName, "", newFileName
//                );
//
//                // Save metadata
//                FileUploadRequestModal fileRecord = new FileUploadRequestModal();
//                fileRecord.setApplicantId(applicantId);
//                fileRecord.setDocumentType(documentType); // dynamic name
//                fileRecord.setDocumentPath(remotePath);
//                documentUploadRepository.save(fileRecord);
//
//                uploadResults.add("Uploaded: " + documentType);
//            } catch (Exception e) {
//                uploadResults.add("Failed: " + documentType + " → " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//        }
//
//        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
//        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded." : "Some uploads failed.");
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//
//
//    @EncryptResponse
//    @PostMapping("/uploadVendorDataDocs")
//    public ResponseEntity<?> uploadVendorDataDocs(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("vendorName") String vendorName,
//            @RequestParam("documentTypes") List<String> documentTypes,
//            @RequestParam("applicantId") String applicantId) {
//
//        Map<String, Object> response = new HashMap<>();
//        List<String> uploadResults = new ArrayList<>();
//
//        if (files.size() != documentTypes.size()) {
//            response.put("status", "FAIL");
//            response.put("statusMsg", "The number of files and document types must match.");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        boolean allUploadsSuccessful = true;
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            String documentType = documentTypes.get(i); // e.g., PO, GST_Certificate
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String newFileName = documentType + getFileExtension(file.getOriginalFilename());
//            String remotePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/" + newFileName;
//
//            try {
//                // Upload to SFTP
//                String uploadStatus = sftpUploaderService.uploadFileToServer(
//                        file, "/opt/cvmsdocuments/vendor", vendorName, "", newFileName
//                );
//
//                // Save metadata
//                FileUploadRequestModal fileRecord = new FileUploadRequestModal();
//                fileRecord.setApplicantId(applicantId);
//                fileRecord.setDocumentType(documentType); // use string directly
//                fileRecord.setDocumentPath(remotePath);
//                documentUploadRepository.save(fileRecord);
//
//                uploadResults.add("Uploaded: " + documentType);
//            } catch (Exception e) {
//                uploadResults.add("Failed: " + documentType + " → " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//        }
//
//        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
//        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded." : "Some uploads failed.");
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    
//    
//    
//    @EncryptResponse
//    @PostMapping("/uploadProjectDataDocuments")
//    public ResponseEntity<?> uploadProjectDataDocuments(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("projectName") String projectName,
//            @RequestParam("documentTypes") List<String> documentTypes,
//            @RequestParam("applicantId") String applicantId) {
//
//        Map<String, Object> response = new HashMap<>();
//        List<String> uploadResults = new ArrayList<>();
//
//        // Validate input
//        if (files.size() != documentTypes.size()) {
//            response.put("status", "FAIL");
//            response.put("statusMsg", "The number of files and document types must match.");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        boolean allUploadsSuccessful = true;
//        String baseDir = "/opt/cvmsdocuments/project";
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            String documentType = documentTypes.get(i); // e.g., Agreement, SOW
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String newFileName = documentType + getFileExtension(file.getOriginalFilename());
//            String remotePath = baseDir + "/" + projectName + "/" + newFileName;
//
//            try {
//                // Upload to SFTP
//                String uploadStatus = sftpUploaderService.uploadFileToServer(
//                        file, baseDir, projectName, "", newFileName
//                );
//
//                // Save metadata to DB
//                FileUploadRequestModal fileRecord = new FileUploadRequestModal();
//                fileRecord.setApplicantId(applicantId);
//                fileRecord.setDocumentType(documentType); // string-based
//                fileRecord.setDocumentPath(remotePath);
//                documentUploadRepository.save(fileRecord);
//
//                uploadResults.add("Uploaded: " + documentType);
//            } catch (Exception e) {
//                uploadResults.add("Failed: " + documentType + " → " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//        }
//
//        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
//        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded." : "Some uploads failed.");
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//
//
//
////     Helper method to get the file extension
//    private String getFileExtension(String originalFileName) {
//        int lastIndex = originalFileName.lastIndexOf('.');
//        return (lastIndex == -1) ? "" : originalFileName.substring(lastIndex);
//    }



//@EncryptResponse
//    @GetMapping("/downloadClientDataDoc")
//    public ResponseEntity<Object> downloadClientDataDoc(
//            @RequestParam("clientName") String clientName,
//            @RequestParam("fileName") String fileName) {
//
//        try {
//            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
//            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Invalid encoding in parameters.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//        }
//
//        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + fileName;
//
//        try {
//            byte[] fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//            if (fileContent == null || fileContent.length == 0) {
//                Map<String, String> error = new HashMap<>();
//                error.put("message", "The selected document is not available.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//            }
//
//            Map<String, String> response = new HashMap<>();
//            response.put("fileName", fileName);
//            response.put("fileContent", Base64.getEncoder().encodeToString(fileContent));
//            return ResponseEntity.ok().body(response);
//
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    
//    
//    @EncryptResponse
//    @GetMapping("/downloadVendorDataDoc")
//    public ResponseEntity<Object> downloadVendorDataDoc(
//            @RequestParam("vendorName") String vendorName,
//            @RequestParam("fileName") String fileName) {
//
//        try {
//            vendorName = URLDecoder.decode(vendorName, StandardCharsets.UTF_8);
//            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Invalid encoding in parameters.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//        }
//
//        String remoteFilePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/" + fileName;
//
//        try {
//            byte[] fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//            if (fileContent == null || fileContent.length == 0) {
//                Map<String, String> error = new HashMap<>();
//                error.put("message", "The selected document is not available.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//            }
//
//            Map<String, String> response = new HashMap<>();
//            response.put("fileName", fileName);
//            response.put("fileContent", Base64.getEncoder().encodeToString(fileContent));
//            return ResponseEntity.ok().body(response);
//
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    
//    
//    @EncryptResponse
//    @GetMapping("/downloadProjectDataDoc")
//    public ResponseEntity<Object> downloadProjectDataDoc(
//            @RequestParam("projectName") String projectName,
//            @RequestParam("fileName") String fileName) {
//
//        try {
//            projectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
//            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Invalid encoding in parameters.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//        }
//
//        String remoteFilePath = "/opt/cvmsdocuments/project/" + projectName + "/" + fileName;
//
//        try {
//            byte[] fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//            if (fileContent == null || fileContent.length == 0) {
//                Map<String, String> error = new HashMap<>();
//                error.put("message", "The selected document is not available.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//            }
//
//            Map<String, String> response = new HashMap<>();
//            response.put("fileName", fileName);
//            response.put("fileContent", Base64.getEncoder().encodeToString(fileContent));
//            return ResponseEntity.ok().body(response);
//
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    
//    @EncryptResponse
//    @GetMapping("/getClientDocuments")
//    public ResponseEntity<?> getClientDocuments(@RequestParam("clientName") String clientName) {
//        try {
//            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
//            String folderPath = "/opt/cvmsdocuments/client/" + clientName;
//
//            List<String> fileNames = sftpUploaderService.listFilesInDirectory(folderPath);
//            return ResponseEntity.ok().body(Map.of("documents", fileNames));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to fetch client documents: " + e.getMessage()));
//        }
//    }
//
//    @EncryptResponse
//    @GetMapping("/getVendorDocuments")
//    public ResponseEntity<?> getVendorDocuments(@RequestParam("vendorName") String vendorName) {
//        try {
//            vendorName = URLDecoder.decode(vendorName, StandardCharsets.UTF_8);
//            String folderPath = "/opt/cvmsdocuments/vendor/" + vendorName;
//
//            List<String> fileNames = sftpUploaderService.listFilesInDirectory(folderPath);
//            return ResponseEntity.ok().body(Map.of("documents", fileNames));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to fetch vendor documents: " + e.getMessage()));
//        }
//    }
//
//    @EncryptResponse
//    @GetMapping("/getProjectDocuments")
//    public ResponseEntity<?> getProjectDocuments(@RequestParam("projectName") String projectName) {
//        try {
//            projectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
//            String folderPath = "/opt/cvmsdocuments/project/" + projectName;
//
//            List<String> fileNames = sftpUploaderService.listFilesInDirectory(folderPath);
//            return ResponseEntity.ok().body(Map.of("documents", fileNames));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "Failed to fetch project documents: " + e.getMessage()));
//        }
//    }

//	
//		
//	}
