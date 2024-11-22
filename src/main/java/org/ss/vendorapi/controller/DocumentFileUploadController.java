package org.ss.vendorapi.controller;
 
import java.util.ArrayList;
import java.util.List;

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
    
    @EncryptResponse
    @PostMapping("/uploadClientDocs")
    public List<String> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("clientName") String clientName,
            @RequestParam("projectName") String projectName,
            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("applicantId") String applicantId) {

        List<String> uploadResults = new ArrayList<>();

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
        
    
    @EncryptResponse
    @GetMapping("/downloadVendor")
    public ResponseEntity<byte[]> downloadVendorFile(@RequestParam("vendorName") String vendorName,
                                                     @RequestParam("fileName") String fileName) {

        String remoteFilePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/ERP/" + fileName;

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

        // Determine the content type based on the file extension
        String contentType = "application/octet-stream"; // Default content type
        if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            contentType = "text/html";
        } else if (fileName.endsWith(".xls")) {
            contentType = "application/vnd.ms-excel"; // For BIFF .xls files
        } else if (fileName.endsWith(".xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; // For .xlsx files
        } else if (fileName.endsWith(".csv")) {
            contentType = "text/csv"; // For CSV files
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileContent);
    }


    
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
 
    
    @EncryptResponse
    @GetMapping("/downloadClientDoc")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("clientName") String clientName,
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Check if file content is empty
        if (fileContent == null || fileContent.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Determine the content type based on the file extension
        String contentType = "application/octet-stream"; // Default content type
        if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            contentType = "text/html";
        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            contentType = "application/vnd.ms-excel"; // For .xls and .xlsx
        } else if (fileName.endsWith(".csv")) {
            contentType = "text/csv"; // For CSV files
        }

        // Create the HTTP response with file content
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileContent);
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
    
    
   
    
    @EncryptResponse
    @GetMapping("/downloadSalesOpportunityDoc")
    public ResponseEntity<byte[]> downloadSalesOpportunityFile(@RequestParam("customerName") String customerName,
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Check if file content is empty
        if (fileContent == null || fileContent.length == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Determine the content type based on the file extension
        String contentType = "application/octet-stream"; // Default content type
        if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            contentType = "text/html";
        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            contentType = "application/vnd.ms-excel"; // For .xls and .xlsx
        } else if (fileName.endsWith(".csv")) {
            contentType = "text/csv"; // For CSV files
        }

        // Create the HTTP response with file content
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileContent);
    }

    
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