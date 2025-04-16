package org.ss.vendorapi.controller;
 
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.ss.vendorapi.advice.EncryptResponse;
import org.ss.vendorapi.entity.FileUploadRequestModal;
import org.ss.vendorapi.entity.FileUploadRequestModal.DocumentType;
import org.ss.vendorapi.repository.DocumentUploadRepository;
import org.ss.vendorapi.service.InvoiceEmailSchedulerService;
//import org.ss.vendorapi.service.SftpService;
import org.ss.vendorapi.service.SftpUploaderService;
 
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class DocumentFileUploadController {
 
    @Autowired
    private SftpUploaderService sftpUploaderService;
 
    @Autowired
    private DocumentUploadRepository documentUploadRepository;
    
    @Autowired
    private InvoiceEmailSchedulerService invoiceEmailSchedulerService;

        
    @EncryptResponse
    @PostMapping("/uploadClientDocs")
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("clientName") String clientName,
            @RequestParam("projectName") String projectName,
            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("applicantId") String applicantId,
            @RequestParam(value = "PONO", required = false) String PONO,  
            @RequestParam(value = "invoiceNo", required = false) String invoiceNo) {  // Added invoiceNo for Invoice document type
        
        Map<String, Object> response = new HashMap<>();
        List<String> uploadResults = new ArrayList<>();

        // Validate input
        if (files.size() != documentTypes.size()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "The number of files and document types must match.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean allUploadsSuccessful = true;

        // Process each file
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            DocumentType documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                allUploadsSuccessful = false;
                continue;
            }

            String newFileName = "";

            // If the document type is INVOICE, name the file based on invoiceNo
            if (documentType == DocumentType.INVOICE && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
                // Replace '/' with '-' in the invoiceNo
                String correctedInvoiceNo = invoiceNo.replaceAll("/", "-").trim();
                newFileName = "Invoice_" + correctedInvoiceNo + ".pdf";  // Invoice file naming pattern
            } else {
                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            }

            String remotePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + newFileName;

            try {
                // Upload file to SFTP with the new file name
                String uploadStatus = sftpUploaderService.uploadFileToServer(file, "/opt/cvmsdocuments/client", clientName, projectName, newFileName);

                // Save file info to database
                FileUploadRequestModal fileRecord = new FileUploadRequestModal();
                fileRecord.setApplicantId(applicantId);
                fileRecord.setDocumentType(documentType);
                fileRecord.setDocumentPath(remotePath);
                documentUploadRepository.save(fileRecord);

                uploadResults.add("File uploaded successfully for document type: " + documentType);
                
                if (documentType == DocumentType.INVOICE && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
                    invoiceEmailSchedulerService.scheduleInvoiceEmail(invoiceNo, 60000); // Delay: 60 seconds
                }
                
            } catch (Exception e) {
                uploadResults.add("File upload failed for document type: " + documentType + ". Error: " + e.getMessage());
                allUploadsSuccessful = false;
            }
        }

        // Build response
        if (allUploadsSuccessful) {
            response.put("status", "SUCCESS");
            response.put("statusMsg", "All files uploaded successfully.");
        } else {
            response.put("status", "FAIL");
            response.put("statusMsg", "Some files failed to upload.");
        }
        response.put("details", uploadResults);

        return new ResponseEntity<>(response, allUploadsSuccessful ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    
    
    
//  ****************************************************************************************************************************************************  
//    @EncryptResponse
//    @PostMapping("/uploadClientDocs")
//    public ResponseEntity<?> uploadFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("clientName") String clientName,
//            @RequestParam("projectName") String projectName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String PONO) {  // Add PONO parameter
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
//
//        // Process each file
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            DocumentType documentType = documentTypes.get(i);
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//
//            // If document type is PO, append PONO to the file name
//            if (documentType == DocumentType.PO && PONO != null && !PONO.isEmpty()) {
//                newFileName = "PO_" + PONO + getFileExtension(file.getOriginalFilename());
//            }
//
//            String remotePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + newFileName;
//
//            try {
//                // Upload file to SFTP with newFileName
//                String uploadStatus = sftpUploaderService.uploadFileToServer(file, "/opt/cvmsdocuments/client", clientName, projectName, newFileName);
//
//                // Save file info to database
//                FileUploadRequestModal fileRecord = new FileUploadRequestModal();
//                fileRecord.setApplicantId(applicantId);
//                fileRecord.setDocumentType(documentType);
//                fileRecord.setDocumentPath(remotePath);
//                documentUploadRepository.save(fileRecord);
//
//                uploadResults.add("File uploaded successfully for document type: " + documentType);
//            } catch (Exception e) {
//                uploadResults.add("File upload failed for document type: " + documentType + ". Error: " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//
//        }
//
//        // Build response
//        if (allUploadsSuccessful) {
//            response.put("status", "SUCCESS");
//            response.put("statusMsg", "All files uploaded successfully.");
//        } else {
//            response.put("status", "FAIL");
//            response.put("statusMsg", "Some files failed to upload.");
//        }
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, allUploadsSuccessful ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    *****************************************************************************************************************************************
    
//    @EncryptResponse
//    @PostMapping("/uploadClientDocs")
//    public ResponseEntity<?> uploadFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("clientName") String clientName,
//            @RequestParam("projectName") String projectName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
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
//
//        // Process each file
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            DocumentType documentType = documentTypes.get(i);
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String newFileName = documentType.name() + getFileExtension1(file.getOriginalFilename());
//            String remotePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + newFileName;
//
//            try {
//                // Upload file to SFTP with newFileName
//                String uploadStatus = sftpUploaderService.uploadFileToServer(file, "/opt/cvmsdocuments/client", clientName, projectName, newFileName);
//                
//                // Save file info to database
//                FileUploadRequestModal fileRecord = new FileUploadRequestModal();
//                fileRecord.setApplicantId(applicantId);
//                fileRecord.setDocumentType(documentType);
//                fileRecord.setDocumentPath(remotePath);
//                documentUploadRepository.save(fileRecord);
//
//                uploadResults.add("File uploaded successfully for document type: " + documentType);
//            } catch (Exception e) {
//                uploadResults.add("File upload failed for document type: " + documentType + ". Error: " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//
//        }
//
//        // Build response
//        if (allUploadsSuccessful) {
//            response.put("status", "SUCCESS");
//            
//            response.put("statusMsg", "All files uploaded successfully.");
//        } else {
//            response.put("status", "FAIL");
//            response.put("statusMsg", "Some files failed to upload.");
//        }
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, allUploadsSuccessful ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    /**
//     * Helper method to get file extension.
//     *
//     * @param fileName the original file name
//     * @return file extension including the dot (e.g., ".pdf"), or an empty string if none
//     */
//    private String getFileExtension1(String fileName) {
//        if (fileName == null || fileName.lastIndexOf('.') == -1) {
//            return "";
//        }
//        return fileName.substring(fileName.lastIndexOf('.'));
//    }

    
    @EncryptResponse
    @PostMapping("/uploadClientDocsByClientName")
    public List<String> uploadFilesByClientName(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("clientName") String clientName,
            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("applicantId") String applicantId,
            @RequestParam(value = "PONO", required = false) String pono) {  // PONO is an optional parameter

        List<String> uploadResults = new ArrayList<>();

        // Check if the number of files matches the number of document types
        if (files.size() != documentTypes.size()) {
            uploadResults.add("Error: The number of files and document types must match.");
            return uploadResults;
        }

        // Loop through each file and upload it
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            DocumentType documentType = documentTypes.get(i);

            // Check if the file is empty
            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                continue;
            }

            // Handle special case for PO document type by appending PONO
            String newFileName;
            if (documentType == DocumentType.PO && pono != null && !pono.isEmpty()) {
                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
            } else {
                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            }

            // Upload the file to the server, specifying "ERP" as the default project
            String uploadStatus = sftpUploaderService.uploadFileToServer(file, "/opt/cvmsdocuments/client", clientName, "ERP", newFileName);

            if (uploadStatus.startsWith("File uploaded successfully")) {
                // Create a file upload request object
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);
                fileUploadRequest.setDocumentPath("/opt/cvmsdocuments/client" + "/" + clientName + "/" + "ERP" + "/" + newFileName);

                // Save the file upload request information
                documentUploadRepository.save(fileUploadRequest);

                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } else {
                uploadResults.add("File upload failed for document type: " + documentType);
            }
        }
        return uploadResults;
    }

  
//    @EncryptResponse
//    @PostMapping("/uploadClientDocsByClientName")
//    public List<String> uploadFilesByClientName(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("clientName") String clientName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
//            @RequestParam("applicantId") String applicantId) {
//
//        List<String> uploadResults = new ArrayList<>();
//
//        // Check if the number of files matches the number of document types
//        if (files.size() != documentTypes.size()) {
//            uploadResults.add("Error: The number of files and document types must match.");
//            return uploadResults;
//        }
//
//        // Loop through each file and upload it
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            DocumentType documentType = documentTypes.get(i);
//
//            // Check if the file is empty
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                continue;
//            }
//
//            // Generate the new file name based on the document type and file extension
//            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//
//            // Upload the file to the server, specifying "ERP" as the default project
//            String uploadStatus = sftpUploaderService.uploadFileToServer(file, "/opt/cvmsdocuments/client", clientName, "ERP", newFileName);
//
//            if (uploadStatus.startsWith("File uploaded successfully")) {
//                // Create a file upload request object
//                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//                fileUploadRequest.setApplicantId(applicantId);
//                fileUploadRequest.setDocumentType(documentType);
//                fileUploadRequest.setDocumentPath("/opt/cvmsdocuments/client" + "/" + clientName + "/" + "ERP" + "/" + newFileName);
//
//                // Save the file upload request information
//                documentUploadRepository.save(fileUploadRequest);
//
//                uploadResults.add("File uploaded successfully for document type: " + documentType);
//            } else {
//                uploadResults.add("File upload failed for document type: " + documentType);
//            }
//        }
//        return uploadResults;
//    }
    
    
    @EncryptResponse
    @PostMapping("/uploadVendorDocs")
    public List<String> uploadVendorFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("vendorName") String vendorName,
            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("applicantId") String applicantId,
            @RequestParam(value = "PONO", required = false) String pono) {  // Optional PONO parameter

        List<String> uploadResults = new ArrayList<>();

        // Check if the number of files matches the number of document types
        if (files.size() != documentTypes.size()) {
            uploadResults.add("Error: The number of files and document types must match.");
            return uploadResults;
        }

        // Loop through each file and upload it
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            DocumentType documentType = documentTypes.get(i);

            // Check if the file is empty
            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                continue;
            }

            // Handle special case for PO document type by appending PONO
            String newFileName;
            if (documentType == DocumentType.PO && pono != null && !pono.isEmpty()) {
                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
            } else {
                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            }

            // Define the base directory
            String baseDir = "/opt/cvmsdocuments/vendor";

            // Upload the file to the server, specifying "ERP" as the default project
            String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, vendorName, "ERP", newFileName);

            if (uploadStatus.startsWith("File uploaded successfully")) {
                // Create a file upload request object
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);
                fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + "ERP" + "/" + newFileName);

                // Save the file upload request information
                documentUploadRepository.save(fileUploadRequest);

                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } else {
                uploadResults.add("File upload failed for document type: " + documentType);
            }
        }
        return uploadResults;
    }
  
    
//    @EncryptResponse
//    @PostMapping("/uploadVendorDocs")
//    public List<String> uploadVendorFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("vendorName") String vendorName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
//            @RequestParam("applicantId") String applicantId) {
//
//        List<String> uploadResults = new ArrayList<>();
//
//        // Check if the number of files matches the number of document types
//        if (files.size() != documentTypes.size()) {
//            uploadResults.add("Error: The number of files and document types must match.");
//            return uploadResults;
//        }
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            DocumentType documentType = documentTypes.get(i);
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                continue;
//            }
//
//            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//            String baseDir = "/opt/cvmsdocuments/vendor";
//            String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, vendorName, "ERP", newFileName);
//
//            if (uploadStatus.startsWith("File uploaded successfully")) {
//                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//                fileUploadRequest.setApplicantId(applicantId);
//                fileUploadRequest.setDocumentType(documentType);
//                fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + newFileName);
//
//                documentUploadRepository.save(fileUploadRequest);
//                uploadResults.add("File uploaded successfully for document type: " + documentType);
//            } else {
//                uploadResults.add("File upload failed for document type: " + documentType);
//            }
//        }
//        return uploadResults;
//    } 
    
    @EncryptResponse
    @PostMapping("/uploadSalesOpportunityDocs")
    public List<String> uploadSalesOpportunityFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("customerName") String customerName,
            @RequestParam("srNo") String srNo,
            @RequestParam("documentTypes") List<String> documentTypes,  // Updated to List<String> to accept string values
            @RequestParam("applicantId") String applicantId,
            @RequestParam(value = "PONO", required = false) String pono) {  // Optional PONO parameter

        List<String> uploadResults = new ArrayList<>();

        // Check if the number of files matches the number of document types
        if (files.size() != documentTypes.size()) {
            uploadResults.add("Error: The number of files and document types must match.");
            return uploadResults;
        }

        // Define base directory for Sales/Opportunity
        String baseDir = "/opt/cvmsdocuments/sales_opportunity";

        // Loop through files and document types
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String documentTypeString = documentTypes.get(i); // Get the string value of document type

            // Convert string to DocumentType enum
            DocumentType documentType;
            try {
                documentType = DocumentType.valueOf(documentTypeString);  // Convert to enum
            } catch (IllegalArgumentException e) {
                uploadResults.add("Invalid document type: " + documentTypeString);
                continue;  // Skip invalid document types
            }

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                continue;
            }

            // Handle special case for PO document type by appending PONO
            String newFileName;
            if (documentType == DocumentType.PO && pono != null && !pono.isEmpty()) {
                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
            } else {
                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            }

            // Upload the file to the server
            String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, customerName, srNo, newFileName);

            // Check upload status
            if (uploadStatus.startsWith("File uploaded successfully")) {
                // Create and save FileUploadRequestModal entity for Sales/Opportunity
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);
                fileUploadRequest.setDocumentPath(baseDir + "/" + customerName + "/" + srNo + "/" + newFileName);

                documentUploadRepository.save(fileUploadRequest);
                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } else {
                uploadResults.add("File upload failed for document type: " + documentType);
            }
        }

        return uploadResults;
    }


//    @EncryptResponse
//    @PostMapping("/uploadSalesOpportunityDocs")
//    public List<String> uploadSalesOpportunityFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("customerName") String customerName,
//            @RequestParam("srNo") String srNo,
//            @RequestParam("documentTypes") List<String> documentTypes,  // Updated to List<String> to accept string values
//            @RequestParam("applicantId") String applicantId) {
//
//        List<String> uploadResults = new ArrayList<>();
//
//        // Check if the number of files matches the number of document types
//        if (files.size() != documentTypes.size()) {
//            uploadResults.add("Error: The number of files and document types must match.");
//            return uploadResults;
//        }
//
//        // Define base directory for Sales/Opportunity
//        String baseDir = "/opt/cvmsdocuments/sales_opportunity";
//
//        // Loop through files and document types
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            String documentTypeString = documentTypes.get(i); // Get the string value of document type
//
//            // Convert string to DocumentType enum
//            DocumentType documentType;
//            try {
//                documentType = DocumentType.valueOf(documentTypeString);  // Convert to enum
//            } catch (IllegalArgumentException e) {
//                uploadResults.add("Invalid document type: " + documentTypeString);
//                continue;  // Skip invalid document types
//            }
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                continue;
//            }
//
//            // Create a new file name based on document type and timestamp
//            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//
//            // Upload the file to the server
//            String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, customerName, srNo, newFileName);
//
//            // Check upload status
//            if (uploadStatus.startsWith("File uploaded successfully")) {
//                // Create and save FileUploadRequestModal entity for Sales/Opportunity
//                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//                fileUploadRequest.setApplicantId(applicantId);
//                fileUploadRequest.setDocumentType(documentType);
//                fileUploadRequest.setDocumentPath(baseDir + "/" + customerName + "/" + srNo + "/" + newFileName);
//
//                documentUploadRepository.save(fileUploadRequest);
//                uploadResults.add("File uploaded successfully for document type: " + documentType);
//            } else {
//                uploadResults.add("File upload failed for document type: " + documentType);
//            }
//        }
//
//        return uploadResults;
//    }


    // Helper method to get the file extension
    private String getFileExtension(String originalFileName) {
        int lastIndex = originalFileName.lastIndexOf('.');
        return (lastIndex == -1) ? "" : originalFileName.substring(lastIndex);
    }
    
    
    @EncryptResponse
    @GetMapping("/downloadVendor")
    public ResponseEntity<Object> downloadVendorFile(@RequestParam("vendorName") String vendorName,
                                                     @RequestParam("fileName") String fileName) {
        try {
            // Decode URL-encoded parameters to handle spaces correctly
            vendorName = URLDecoder.decode(vendorName, StandardCharsets.UTF_8);
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        } catch (Exception e) {
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid encoding in parameters.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Construct the remote file path
        String remoteFilePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/ERP/" + fileName;

        byte[] fileContent;
        try {
            // Attempt to download the file content
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (com.jcraft.jsch.SftpException e) {
            e.printStackTrace();
            if (e.getMessage().contains("No such file")) {
                java.util.Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "The selected document is not available.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(errorResponse);
            } else {
                java.util.Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        if (fileContent == null || fileContent.length == 0) {
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "The selected document is not available.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Base64 encode the file content
        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);

        // Create the response body with the base64 string
        java.util.Map<String, String> successResponse = new HashMap<>();
        successResponse.put("fileName", fileName);
        successResponse.put("fileContent", base64EncodedFile);

        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(successResponse);
    }


    @EncryptResponse   
    @GetMapping("/downloadClientDoc")
    public ResponseEntity<Object> downloadFile(@RequestParam("clientName") String clientName,
                                               @RequestParam("projectName") String projectName,
                                               @RequestParam("fileName") String fileName) {

        try {
            // Decode URL-encoded parameters to handle spaces correctly
            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
            projectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        } catch (Exception e) {
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid encoding in parameters.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Construct the remote file path
        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + fileName;

        byte[] fileContent;
        try {
            // Attempt to retrieve the file from the server
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (com.jcraft.jsch.SftpException e) {
            e.printStackTrace();
            if (e.getMessage().contains("No such file")) {
                java.util.Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "The selected document is not available.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(errorResponse);
            } else {
                java.util.Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .body(errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        if (fileContent == null || fileContent.length == 0) {
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "The selected document is not available.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Encode file content to Base64
        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);

        java.util.Map<String, Object> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("fileContent", base64EncodedFile);

        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }



//    @GetMapping("/downloadClientDocsByClientName")
//    public ResponseEntity<Object> downloadClientFileByClientName(
//            @RequestParam("clientName") String clientName,
//            @RequestParam("fileName") String fileName) {
//
//        // Construct the remote file path
//        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/ERP/" + fileName;
//
//        byte[] fileContent;
//        try {
//            // Retrieve the file content from the server
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return JSON response in case of error
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", e.getMessage()); // Include exception message
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Check if the file content is null or empty
//        if (fileContent == null || fileContent.length == 0) {
//            // Return error response if the file is not found or is empty
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "Error: The requested file does not exist or is empty.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Determine the content type based on the file extension
//        String contentType = "application/octet-stream"; // Default content type
//        if (fileName.endsWith(".pdf")) {
//            contentType = "application/pdf";
//        } else if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
//            contentType = "text/html";
//        } else if (fileName.endsWith(".xls")) {
//            contentType = "application/vnd.ms-excel"; // For BIFF .xls files
//        } else if (fileName.endsWith(".xlsx")) {
//            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; // For .xlsx files
//        } else if (fileName.endsWith(".csv")) {
//            contentType = "text/csv"; // For CSV files
//        }
//
//        // Return the file content with the correct content type and download header
//        return ResponseEntity.ok()
////                             .contentType(MediaType.parseMediaType(contentType))
//        		             .contentType(MediaType.APPLICATION_OCTET_STREAM)  // Always return as raw bytes
//                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                             .body(fileContent);
//    }
    
	    @GetMapping("/downloadClientDocsByClientName")
	    public ResponseEntity<Object> downloadClientFileByClientName(
	            @RequestParam("clientName") String clientName,
	            @RequestParam("fileName") String fileName) {
	
	        try {
	            // Decode parameters to handle spaces properly
	            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
	            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                                 .contentType(MediaType.APPLICATION_JSON)
	                                 .body(Map.of("error", "Invalid encoding in parameters."));
	        }
	        // Construct the remote file path
	        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/ERP/" + fileName;
	
	        byte[] fileContent;
	        try {
	            // Retrieve the file content from the server
	            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Check for specific exception indicating file not found
	            String errorMessage = e.getMessage();
	            // You can customize this to be more specific based on your exception handling
	            if (errorMessage != null && errorMessage.contains("No such file")) {
	                errorMessage = "The selected document is not available."; // Custom message
	            }
	
	            // Return JSON response in case of error
	            Map<String, String> errorResponse = new HashMap<>();
	            errorResponse.put("error", errorMessage); // Include the error message
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .contentType(MediaType.APPLICATION_JSON)
	                                 .body(errorResponse);
	        }
	
	        // Check if the file content is null or empty
	        if (fileContent == null || fileContent.length == 0) {
	            // Return error response if the file is not found or is empty
	            Map<String, String> errorResponse = new HashMap<>();
	            errorResponse.put("message", "The selected document is not available.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .contentType(MediaType.APPLICATION_JSON)
	                                 .body(errorResponse);
	        }
	
	        // Encode the file content into Base64
	        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);
	
	        // Create the response
	        Map<String, Object> response = new HashMap<>();
	        response.put("fileName", fileName);
	        response.put("fileType", getFileType(fileName));  // Optional: Include the file type (MIME type)
	        response.put("imageByte", base64EncodedFile);   // The base64 string of the file content
	
	        return ResponseEntity.ok()
	                             .contentType(MediaType.APPLICATION_JSON)
	                             .body(response);
	    }
	
	    // Helper method to determine the file type (MIME type) based on file extension
	    private String getFileType(String fileName) {
	        if (fileName.endsWith(".pdf")) {
	            return "application/pdf";
	        } else if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
	            return "text/html";
	        } else if (fileName.endsWith(".xls")) {
	            return "application/vnd.ms-excel";
	        } else if (fileName.endsWith(".xlsx")) {
	            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	        } else if (fileName.endsWith(".csv")) {
	            return "text/csv";
	        } else {
	            return "application/octet-stream";  // Default MIME type
	        }
	    }

    
    @GetMapping("/downloadSalesOpportunityDoc")
    public ResponseEntity<Object> downloadSalesOpportunityFile(@RequestParam("customerName") String customerName,
                                                                @RequestParam("srNo") String srNo,
                                                                @RequestParam("fileName") String fileName) {

    	try {
            // Decode parameters to handle spaces properly
    		customerName = URLDecoder.decode(customerName, StandardCharsets.UTF_8);
    		srNo = URLDecoder.decode(srNo, StandardCharsets.UTF_8);
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(Map.of("error", "Invalid encoding in parameters."));
        }
        // Construct the remote file path for Sales/Opportunity
        String remoteFilePath = "/opt/cvmsdocuments/sales_opportunity/" + customerName + "/" + srNo + "/" + fileName;

        byte[] fileContent;
        try {
            // Retrieve the file from the server
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            // Return JSON response for internal error
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("No such file")) {
                errorMessage = "No file content available for the selected document."; // Custom message
            }

            // Create a JSON error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", errorMessage);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Check if file content is empty or null
        if (fileContent == null || fileContent.length == 0) {
            // Return JSON response for file not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "No file content available for the selected document.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Encode the file content as a Base64 string
        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);

        // Prepare the JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("fileContent", base64EncodedFile);

        // Return the Base64-encoded file content as part of a JSON response
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }
      
   
}