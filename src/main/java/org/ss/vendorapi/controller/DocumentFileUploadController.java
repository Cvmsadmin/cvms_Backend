package org.ss.vendorapi.controller;
 
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
import org.ss.vendorapi.service.SftpUploaderService;
 
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class DocumentFileUploadController {
 
    @Autowired
    private SftpUploaderService sftpUploaderService;
 
    @Autowired
    private DocumentUploadRepository documentUploadRepository;
    
//    @EncryptResponse
    @PostMapping("/uploadClientDocs")
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("clientName") String clientName,
            @RequestParam("projectName") String projectName,
            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("applicantId") String applicantId) {

        List<String> uploadResults = new ArrayList<>();
        Map<String, Object> statusMap = new HashMap<>();
        if (files.size() != documentTypes.size()) {
            uploadResults.add("Error: The number of files and document types must match.");
            statusMap.put("statusMsg", "Error: The number of files and document types must match.");
            statusMap.put("status", "SUCCESS");
            return new ResponseEntity<>(statusMap,HttpStatus.OK);
        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            DocumentType documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                continue;
            }

            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            String uploadStatus = sftpUploaderService.uploadFileToServer(file, "/opt/cvmsdocuments/client", clientName, projectName, newFileName);

            if (uploadStatus.startsWith("File uploaded successfully")) {
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);
                fileUploadRequest.setDocumentPath("/opt/cvmsdocuments/client" + "/" + clientName + "/" + projectName + "/" + newFileName);

                documentUploadRepository.save(fileUploadRequest);
                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } else {
                uploadResults.add("File upload failed for document type: " + documentType);
            }
        }
        statusMap.put("statusMsg", "Error: The number of files and document types must match.");
        statusMap.put("status", "SUCCESS");
        return new ResponseEntity<>(statusMap,HttpStatus.OK);
    }
    
    
    @EncryptResponse
    @PostMapping("/uploadClientDocsByClientName")
    public List<String> uploadFilesByClientName(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("clientName") String clientName,
            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("applicantId") String applicantId) {

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

            // Generate the new file name based on the document type and file extension
            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());

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


    
    
    
    @EncryptResponse
    @PostMapping("/uploadVendorDocs")
    public List<String> uploadVendorFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("vendorName") String vendorName,
            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("applicantId") String applicantId) {

        List<String> uploadResults = new ArrayList<>();

        // Check if the number of files matches the number of document types
        if (files.size() != documentTypes.size()) {
            uploadResults.add("Error: The number of files and document types must match.");
            return uploadResults;
        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            DocumentType documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                continue;
            }

            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            String baseDir = "/opt/cvmsdocuments/vendor";
            String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, vendorName, "ERP", newFileName);

            if (uploadStatus.startsWith("File uploaded successfully")) {
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);
                fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + newFileName);

                documentUploadRepository.save(fileUploadRequest);
                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } else {
                uploadResults.add("File upload failed for document type: " + documentType);
            }
        }
        return uploadResults;
    }



    
//    @EncryptResponse
//    @PostMapping("/uploadClientDoc")
//    public String uploadFile(@RequestParam("file") MultipartFile file,
//                             @RequestParam("clientName") String clientName,
//                             @RequestParam("projectName") String projectName,
//                             @RequestParam("documentType") DocumentType documentType,
//                             @RequestParam("applicantId") String applicantId) {
//        if (file.isEmpty()) {
//            return "File is empty";
//        }
//
//        // Define base directory
//        String baseDir = "/opt/cvmsdocuments/Client";
//
//        // Create new file name based on document type
//        String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//
//        String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, clientName, projectName, newFileName);
//
//        if (uploadStatus.startsWith("File uploaded successfully")) {
//            // Create and save FileUploadRequestModal entity
//            FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//            fileUploadRequest.setApplicantId(applicantId);
//            fileUploadRequest.setDocumentType(documentType);
//            fileUploadRequest.setDocumentPath(baseDir + "/" + clientName + "/" + projectName + "/" + newFileName);
//
//            documentUploadRepository.save(fileUploadRequest);
//            return uploadStatus;
//        } else {
//            return uploadStatus;
//        }
//    }
    

//    @EncryptResponse
//    @PostMapping("/uploadVendorDoc")
//    public String uploadVendorFile(@RequestParam("file") MultipartFile file,
//                                   @RequestParam("vendorName") String vendorName,
//                                   @RequestParam("documentType") DocumentType documentType,
//                                   @RequestParam("applicantId") String applicantId) {
//        if (file.isEmpty()) {
//            return "File is empty";
//        }
//
//        String baseDir = "/opt/cvmsdocuments/Vendor";
//
//        // Create new file name based on document type
//        String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//
//        String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, vendorName, "ERP", newFileName);
//
//        if (uploadStatus.startsWith("File uploaded successfully")) {
//            FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//            fileUploadRequest.setApplicantId(applicantId);
//            fileUploadRequest.setDocumentType(documentType);
//            fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + newFileName);
//
//            documentUploadRepository.save(fileUploadRequest);
//            return uploadStatus;
//        } else {
//            return uploadStatus;
//        }
//    }
    
    
    
    
    
//    *************************************
//    @EncryptResponse
//    @PostMapping("/uploadSalesOpportunityDocs")
//    public List<String> uploadSalesOpportunityFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("customerName") String customerName,
//            @RequestParam("srNo") String srNo,
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
//        // Define base directory for Sales/Opportunity
//        String baseDir = "/opt/cvmsdocuments/sales_opportunity";
//
//        // Loop through files and document types
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//       @EncryptResponse
    @PostMapping("/uploadSalesOpportunityDocs")
    public List<String> uploadSalesOpportunityFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("customerName") String customerName,
            @RequestParam("srNo") String srNo,
            @RequestParam("documentTypes") List<String> documentTypes,  // Updated to List<String> to accept string values
            @RequestParam("applicantId") String applicantId) {

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

            // Create a new file name based on document type and timestamp
            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());

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
//     DocumentType documentType = documentTypes.get(i);

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
//         ****************************************************************
    
    

//    @EncryptResponse
//    @PostMapping("/uploadSalesOpportunityDoc")
//    public String uploadSalesOpportunityFile(@RequestParam("file") MultipartFile file,
//                                             @RequestParam("customerName") String customerName,
//                                             @RequestParam("srNo") String srNo,
//                                             @RequestParam("documentType") DocumentType documentType,
//                                             @RequestParam("applicantId") String applicantId) {
//        if (file.isEmpty()) {
//            return "File is empty";
//        }
//
//        // Define base directory for Sales/Opportunity
//        String baseDir = "/opt/cvmsdocuments/Sales_Opportunity";
//
//        // Create new file name based on document type
//        String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//
//        String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, customerName, srNo, newFileName);
//
//        if (uploadStatus.startsWith("File uploaded successfully")) {
//            // Create and save FileUploadRequestModal entity for Sales/Opportunity
//            FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//            fileUploadRequest.setApplicantId(applicantId);
//            fileUploadRequest.setDocumentType(documentType);
//            fileUploadRequest.setDocumentPath(baseDir + "/" + customerName + "/" + srNo + "/" + newFileName);
//
//            documentUploadRepository.save(fileUploadRequest);
//            return uploadStatus;
//        } else {
//            return uploadStatus;
//        }
//    }

    // Helper method to get the file extension
    private String getFileExtension(String originalFileName) {
        int lastIndex = originalFileName.lastIndexOf('.');
        return (lastIndex == -1) ? "" : originalFileName.substring(lastIndex);
    }
    


//    @EncryptResponse
//    @PostMapping("/uploadSalesOpportunityDoc")
//    public String uploadSalesOpportunityFile(@RequestParam("file") MultipartFile file,
//                                             @RequestParam("customerName") String customerName,
//                                             @RequestParam("Sr_no") String srNo,
//                                             @RequestParam("documentType") DocumentType documentType,
//                                             @RequestParam("applicantId") String applicantId) {
//        if (file.isEmpty()) {
//            return "File is empty";
//        }
// 
//        // Define base directory for Sales/Opportunity
//        String baseDir = "/opt/cvmsdocuments/Sales_Opportunity";
//        String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, customerName, srNo);
// 
//        if (uploadStatus.startsWith("File uploaded successfully")) {
//            // Create and save FileUploadRequestModal entity for Sales/Opportunity
//            FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//            fileUploadRequest.setApplicantId(applicantId);
//            fileUploadRequest.setDocumentType(documentType);
//            fileUploadRequest.setDocumentPath(baseDir + "/" + customerName + "/" + srNo + "/" + file.getOriginalFilename());
// 
//            documentUploadRepository.save(fileUploadRequest);
//            return uploadStatus;
//        } else {
//            return uploadStatus;
//        }
//    }
    
    @GetMapping("/downloadVendor")
    public ResponseEntity<Object> downloadVendorFile(@RequestParam("vendorName") String vendorName,
                                                     @RequestParam("fileName") String fileName) {

        String remoteFilePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/ERP/" + fileName;

        byte[] fileContent;
        try {
            // Attempt to download the file content
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            // Return JSON response when there's an error
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Check if the file content is null or empty
        if (fileContent == null || fileContent.length == 0) {
            // Return a message indicating no file was found
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error: The requested file does not exist or is empty.");
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
        
    
//    @GetMapping("/downloadVendor")
//    public ResponseEntity<Object> downloadVendorFile(@RequestParam("vendorName") String vendorName,
//                                                     @RequestParam("fileName") String fileName) {
//
//        String remoteFilePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/ERP/" + fileName;
//
//        byte[] fileContent;
//        try {
//            // Attempt to download the file content
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return JSON response when there's an error
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Check if the file content is null or empty
//        if (fileContent == null || fileContent.length == 0) {
//            // Return a message indicating no file was found
//            java.util.Map<String, String> errorResponse = new HashMap<>();
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
//        // Return the file content as bytecode in the response body
//        return ResponseEntity.ok()
//        		.contentType(MediaType.APPLICATION_OCTET_STREAM)  // Always return as raw bytes
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(fileContent);
//    }


    
//    @GetMapping("/downloadVendor")
//    public ResponseEntity<byte[]> downloadVendorFile(@RequestParam("vendorName") String vendorName,
//                                                     @RequestParam("fileName") String fileName) {
//
//        String remoteFilePath = "/opt/cvmsdocuments/Vendor/" + vendorName + "/ERP/" + fileName;
//
//        byte[] fileContent;
//        try {
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//
//        if (fileContent == null || fileContent.length == 0) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        // Determine the content type based on the file extension
//        String contentType = "application/octet-stream"; // default content type
//        if (fileName.endsWith(".pdf")) {
//            contentType = "application/pdf";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(fileContent);
//    }

    
//    @GetMapping("/downloadVendor")
//    public ResponseEntity<byte[]> downloadVendorFile(@RequestParam("vendorName") String vendorName,
//                                                     @RequestParam("fileName") String fileName) {
// 
//        String remoteFilePath = "/opt/cvmsdocuments/Vendor/" + vendorName + "/ERP/" + fileName;
// 
//        byte[] fileContent;
//        try {
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
// 
//        if (fileContent == null || fileContent.length == 0) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
// 
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(fileContent);
//    }

 
    @GetMapping("/downloadClientDoc")
    public ResponseEntity<Object> downloadFile(@RequestParam("clientName") String clientName,
                                               @RequestParam("projectName") String projectName,
                                               @RequestParam("fileName") String fileName) {

        // Construct the remote file path
        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + fileName;

        byte[] fileContent;
        try {
            // Retrieve the file from the server
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            // Return JSON response for internal error
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());  // Include the exception message if necessary
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Check if file content is empty or null
        if (fileContent == null || fileContent.length == 0) {
            // Return JSON response for file not found
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error: There are no documents in the folder.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Encode file content to Base64
        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);

        // Return Base64-encoded file content in the response body
        java.util.Map<String, Object> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("fileContent", base64EncodedFile);

        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }
//    @GetMapping("/downloadClientDoc")
//    public ResponseEntity<Object> downloadFile(@RequestParam("clientName") String clientName,
//                                               @RequestParam("projectName") String projectName,
//                                               @RequestParam("fileName") String fileName) {
//
//        // Construct the remote file path
//        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + fileName;
//
//        byte[] fileContent;
//        try {
//            // Retrieve the file from the server
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return JSON response for internal error
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//           
//            errorResponse.put("error", e.getMessage());  // Include the exception message if necessary
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Check if file content is empty or null
//        if (fileContent == null || fileContent.length == 0) {
//            // Return JSON response for file not found
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "Error: There are no documents in the folder.");
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
//        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
//            contentType = "application/vnd.ms-excel"; // For .xls and .xlsx
//        } else if (fileName.endsWith(".csv")) {
//            contentType = "text/csv"; // For CSV files
//        }
//
//        // Return the file content with the correct content type
//        return ResponseEntity.ok()
//        		.contentType(MediaType.APPLICATION_OCTET_STREAM)  // Always return as raw bytes
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(fileContent);
//    }
    
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

        // Construct the remote file path
        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/ERP/" + fileName;

        byte[] fileContent;
        try {
            // Retrieve the file content from the server
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            // Return JSON response in case of error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage()); // Include exception message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Check if the file content is null or empty
        if (fileContent == null || fileContent.length == 0) {
            // Return error response if the file is not found or is empty
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error: The requested file does not exist or is empty.");
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




    
//    @GetMapping("/downloadClientDoc")
//    public ResponseEntity<byte[]> downloadFile(@RequestParam("clientName") String clientName,
//                                               @RequestParam("projectName") String projectName,
//                                               @RequestParam("fileName") String fileName) {
// 
//        // Construct the remote file path
//        String remoteFilePath = "/opt/cvmsdocuments/Client/" + clientName + "/" + projectName + "/" + fileName;
// 
//        byte[] fileContent;
//        try {
//            // Retrieve the file from the server
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
// 
//        // Check if file content is empty
//        if (fileContent == null || fileContent.length == 0) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
// 
//        // Create the HTTP response with file content
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(fileContent);
//    }
    
    @GetMapping("/downloadSalesOpportunityDoc")
    public ResponseEntity<Object> downloadSalesOpportunityFile(@RequestParam("customerName") String customerName,
                                                                @RequestParam("srNo") String srNo,
                                                                @RequestParam("fileName") String fileName) {

        // Construct the remote file path for Sales/Opportunity
        String remoteFilePath = "/opt/cvmsdocuments/sales_opportunity/" + customerName + "/" + srNo + "/" + fileName;

        byte[] fileContent;
        try {
            // Retrieve the file from the server
            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            // Return JSON response for internal error
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());  // Include exception message if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Check if file content is empty or null
        if (fileContent == null || fileContent.length == 0) {
            // Return JSON response for file not found
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error: There are no documents in the folder.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(errorResponse);
        }

        // Encode the file content as a base64 string
        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);

        // Prepare the JSON response
        java.util.Map<String, Object> response = new HashMap<>();
        response.put("fileName", fileName);
        response.put("fileContent", base64EncodedFile);

        // Return the base64-encoded file content as part of a JSON response
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(response);
    }
   
    
//    @GetMapping("/downloadSalesOpportunityDoc")
//    public ResponseEntity<Object> downloadSalesOpportunityFile(@RequestParam("customerName") String customerName,
//                                                                @RequestParam("srNo") String srNo,
//                                                                @RequestParam("fileName") String fileName) {
//
//        // Construct the remote file path for Sales/Opportunity
//        String remoteFilePath = "/opt/cvmsdocuments/sales_opportunity/" + customerName + "/" + srNo + "/" + fileName;
//
//        byte[] fileContent;
//        try {
//            // Retrieve the file from the server
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Return JSON response for internal error
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            
//            errorResponse.put("error", e.getMessage());  // Include exception message if needed
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Check if file content is empty or null
//        if (fileContent == null || fileContent.length == 0) {
//            // Return JSON response for file not found
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "Error: There are no documents in the folder.");
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
//        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
//            contentType = "application/vnd.ms-excel"; // For .xls and .xlsx
//        } else if (fileName.endsWith(".csv")) {
//            contentType = "text/csv"; // For CSV files
//        }
//
//        // Return the file content with the correct content type
//        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(contentType))
//        		.contentType(MediaType.APPLICATION_OCTET_STREAM)  // Always return as raw bytes
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(fileContent);
//    }

    
//    @GetMapping("/downloadSalesOpportunityDoc")
//    public ResponseEntity<byte[]> downloadSalesOpportunityFile(@RequestParam("customerName") String customerName,
//                                                               @RequestParam("srNo") String srNo,
//                                                               @RequestParam("fileName") String fileName) {
// 
//        // Construct the remote file path for Sales/Opportunity
//        String remoteFilePath = "/opt/cvmsdocuments/Sales_Opportunity/" + customerName + "/" + srNo + "/" + fileName;
// 
//        byte[] fileContent;
//        try {
//            // Retrieve the file from the server
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
// 
//        // Check if file content is empty
//        if (fileContent == null || fileContent.length == 0) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
// 
//        // Create the HTTP response with file content
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(fileContent);
//    }
      
   
}