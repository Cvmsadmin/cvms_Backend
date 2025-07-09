package org.ss.vendorapi.controller;
 
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
//import org.ss.vendorapi.entity.FileUploadRequestModal.DocumentType;
//import org.ss.vendorapi.entity.FileUploadRequestModal.DocumentType;
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
    @PostMapping("/uploadClientInvoiceDocs")
    public ResponseEntity<?> uploadClientInvoiceDocs(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("clientName") String clientName,
            @RequestParam("projectName") String projectName,
            @RequestParam("documentTypes") List<String> documentTypes,
            @RequestParam("applicantId") String applicantId,
            @RequestParam(value = "invoiceNo", required = false) String invoiceNo) {

        Map<String, Object> response = new HashMap<>();
        List<String> uploadResults = new ArrayList<>();

        if (files.size() != documentTypes.size()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "The number of files and document types must match.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean allUploadsSuccessful = true;
        String baseDir = "/opt/cvmsdocuments/client_invoice";

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                allUploadsSuccessful = false;
                continue;
            }

            // 📁 Folder structure: invoices/INV-{invoiceNo}
            String invoiceFolder = (invoiceNo != null && !invoiceNo.isBlank())
                    ? "INV-" + invoiceNo.replaceAll("/", "-").trim()
                    : "UNKNOWN-INVOICE";

            String subFolder = projectName + "/invoices/" + invoiceFolder;

            // 📄 File naming
            String newFileName;
            if (documentType.equalsIgnoreCase("INVOICE") && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
                newFileName = "Invoice_" + invoiceNo.replaceAll("/", "-").trim() + getFileExtension(file.getOriginalFilename());
            } else {
                newFileName = documentType + getFileExtension(file.getOriginalFilename());
            }

            String remotePath = baseDir + "/" + clientName + "/" + subFolder + "/" + newFileName;

            try {
                // Upload to SFTP
                String uploadStatus = sftpUploaderService.uploadFileToServer(
                        file, baseDir, clientName, subFolder, newFileName
                );

                if (uploadStatus.startsWith("File uploaded successfully")) {
                    // Save file metadata
                    FileUploadRequestModal fileRecord = new FileUploadRequestModal();
                    fileRecord.setApplicantId(applicantId);
                    fileRecord.setDocumentType(documentType);
                    fileRecord.setDocumentPath(remotePath);
                    documentUploadRepository.save(fileRecord);

                    uploadResults.add("File uploaded successfully for document type: " + documentType);

                    if (documentType.equalsIgnoreCase("INVOICE") && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
                        invoiceEmailSchedulerService.scheduleInvoiceEmail(invoiceNo, 60000); // 60 sec
                    }
                } else {
                    uploadResults.add("File upload failed for document type: " + documentType);
                    allUploadsSuccessful = false;
                }

            } catch (Exception e) {
                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
                allUploadsSuccessful = false;
            }
        }

        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
        response.put("details", uploadResults);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
//  GET API
    @EncryptResponse
    @GetMapping("/getClientInvoiceDocsByInvoiceNo")
    public ResponseEntity<?> getClientInvoiceDocsByInvoiceNo(@RequestParam("invoiceNo") String invoiceNo) {
        Map<String, Object> response = new HashMap<>();

        if (invoiceNo == null || invoiceNo.trim().isEmpty()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "Invoice number is required.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String normalizedInvoiceNo = invoiceNo.replaceAll("/", "-").trim(); // e.g., INV/2025/TS/12345 → INV-2025-TS-12345
        String folderIdentifier = "INV-" + normalizedInvoiceNo;

        try {
            // Get all matching documents
            List<FileUploadRequestModal> docs = documentUploadRepository
                    .findByDocumentPathContainingIgnoreCase(folderIdentifier);

            if (docs.isEmpty()) {
                response.put("status", "FAIL");
                response.put("statusMsg", "No documents found for invoice no: " + invoiceNo);
            } else {
                List<String> fileNames = docs.stream()
                    .map(doc -> {
                        String fullPath = doc.getDocumentPath();
                        return fullPath.substring(fullPath.lastIndexOf('/') + 1); // Extract file name
                    })
                    .collect(Collectors.toList());

                response.put("status", "SUCCESS");
                response.put("statusMsg", "Documents retrieved successfully.");
                response.put("documents", fileNames);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "FAIL");
            response.put("statusMsg", "Error occurred while fetching documents.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
//    @EncryptResponse
//    @PostMapping("/uploadClientInvoiceDocs")
//    public ResponseEntity<?> uploadClientInvoiceDocs(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("clientName") String clientName,
//            @RequestParam("projectName") String projectName,
//            @RequestParam("documentTypes") List<String> documentTypes,
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String PONO,
//            @RequestParam(value = "invoiceNo", required = false) String invoiceNo) {
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
//        String baseDir = "/opt/cvmsdocuments/client_invoice";
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            String documentType = documentTypes.get(i);
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String newFileName;
//
//            // Special naming for INVOICE document type
//            if (documentType.equalsIgnoreCase("INVOICE") && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
//                String correctedInvoiceNo = invoiceNo.replaceAll("/", "-").trim();
//                newFileName = "Invoice_" + correctedInvoiceNo + ".pdf";
//            } else {
//                newFileName = documentType + getFileExtension(file.getOriginalFilename());
//            }
//
//            String remotePath = baseDir + "/" + clientName + "/" + projectName + "/" + newFileName;
//
//            try {
//                // Upload to SFTP
//                String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, clientName, projectName, newFileName);
//
//                if (uploadStatus.startsWith("File uploaded successfully")) {
//                    // Save file metadata
//                    FileUploadRequestModal fileRecord = new FileUploadRequestModal();
//                    fileRecord.setApplicantId(applicantId);
//                    fileRecord.setDocumentType(documentType); // Now it's just a string
//                    fileRecord.setDocumentPath(remotePath);
//                    documentUploadRepository.save(fileRecord);
//
//                    uploadResults.add("File uploaded successfully for document type: " + documentType);
//
//                    if (documentType.equalsIgnoreCase("INVOICE") && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
//                        invoiceEmailSchedulerService.scheduleInvoiceEmail(invoiceNo, 60000); // Send after 60 seconds
//                    }
//                } else {
//                    uploadResults.add("File upload failed for document type: " + documentType);
//                    allUploadsSuccessful = false;
//                }
//
//            } catch (Exception e) {
//                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//        }
//
//        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
//        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

        
//    @EncryptResponse
//    @PostMapping("/uploadClientInvoiceDocs")
//    public ResponseEntity<?> uploadFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("clientName") String clientName,
//            @RequestParam("projectName") String projectName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String PONO,  
//            @RequestParam(value = "invoiceNo", required = false) String invoiceNo) {  // Added invoiceNo for Invoice document type
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
//            String newFileName = "";
//
//            // If the document type is INVOICE, name the file based on invoiceNo
//            if (documentType == DocumentType.INVOICE && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
//                // Replace '/' with '-' in the invoiceNo
//                String correctedInvoiceNo = invoiceNo.replaceAll("/", "-").trim();
//                newFileName = "Invoice_" + correctedInvoiceNo + ".pdf";  // Invoice file naming pattern
//            } else {
//                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//            }
//
//            String remotePath = "/opt/cvmsdocuments/client_invoice/" + clientName + "/" + projectName + "/" + newFileName;
//            
//
//            try {
//                // Upload file to SFTP with the new file name
//                String uploadStatus = sftpUploaderService.uploadFileToServer(file, "/opt/cvmsdocuments/client_invoice", clientName, projectName, newFileName);
//
//                // Save file info to database
//                FileUploadRequestModal fileRecord = new FileUploadRequestModal();
//                fileRecord.setApplicantId(applicantId);
//                fileRecord.setDocumentType(documentType);
//                fileRecord.setDocumentPath(remotePath);
//                documentUploadRepository.save(fileRecord);
//
//                uploadResults.add("File uploaded successfully for document type: " + documentType);
//                
//                if (documentType == DocumentType.INVOICE && invoiceNo != null && !invoiceNo.trim().isEmpty()) {
//                    invoiceEmailSchedulerService.scheduleInvoiceEmail(invoiceNo, 60000); // Delay: 60 seconds
//                }
//                
//            } catch (Exception e) {
//                uploadResults.add("File upload failed for document type: " + documentType + ". Error: " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
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
//        HttpStatus test = allUploadsSuccessful ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
//        System.out.println("response "+response);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    
//    @EncryptResponse
//    @PostMapping("/uploadClientDocsByClientName")
//    public List<String> uploadFilesByClientName(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("clientName") String clientName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String pono) {  // PONO is an optional parameter
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
//            // Handle special case for PO document type by appending PONO
//            String newFileName;
//            if (documentType == DocumentType.PO && pono != null && !pono.isEmpty()) {
//                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
//            } else {
//                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//            }
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

//   ***********************************************************************************************************************************************
      
      
    @EncryptResponse
    @PostMapping("/uploadVendorInvoiceDocs")
    public ResponseEntity<?> uploadVendorInvoiceDocs(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("vendorName") String vendorName,
            @RequestParam("documentTypes") List<String> documentTypes,
            @RequestParam("applicantId") String applicantId,
            @RequestParam(value = "PONO", required = false) String pono) {

        Map<String, Object> response = new HashMap<>();
        List<String> uploadResults = new ArrayList<>();

        if (files.size() != documentTypes.size()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "The number of files and document types must match.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean allUploadsSuccessful = true;
        String baseDir = "/opt/cvmsdocuments/vendor_invoice";

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String documentType = documentTypes.get(i).toLowerCase();

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                allUploadsSuccessful = false;
                continue;
            }

            String subFolder;
            String newFileName;

            if (documentType.startsWith("invoice")) {
                // ✅ Extract invoice number from documentType, e.g., INVOICE_456784_doc -> 456784
                String invoiceNumber = extractInvoiceNumber(documentType);
                subFolder = "invoices/" + invoiceNumber;
                newFileName = documentType + getFileExtension(file.getOriginalFilename());

            } else {
                // 🔒 PONO is required for non-invoice documents
                if (pono == null || pono.isEmpty()) {
                    uploadResults.add("PONO is required for document type: " + documentType);
                    allUploadsSuccessful = false;
                    continue;
                }
                subFolder = "purchase-orders/PO-" + pono;
                newFileName = documentType.toUpperCase() + "_" + pono + getFileExtension(file.getOriginalFilename());
            }

            try {
                String uploadStatus = sftpUploaderService.uploadFileToServer(
                        file, baseDir, vendorName, subFolder, newFileName
                );

                if (uploadStatus.startsWith("File uploaded successfully")) {
                    FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                    fileUploadRequest.setApplicantId(applicantId);
                    fileUploadRequest.setDocumentType(documentType);
                    fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + subFolder + "/" + newFileName);
                    documentUploadRepository.save(fileUploadRequest);

                    uploadResults.add("File uploaded successfully for document type: " + documentType);
                } else {
                    uploadResults.add("File upload failed for document type: " + documentType);
                    allUploadsSuccessful = false;
                }

            } catch (Exception e) {
                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
                allUploadsSuccessful = false;
            }
        }

        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
        response.put("details", uploadResults);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String extractInvoiceNumber(String documentType) {
        // Expected: INVOICE_456784_doc
        // Output: 456784
        String[] parts = documentType.split("_");
        if (parts.length >= 2) {
            return parts[1]; // invoice number
        }
        return "UNKNOWN";
    }
    
    
//  GET API
    @EncryptResponse
    @GetMapping("/getVendorInvoiceDocs")
    public ResponseEntity<?> getVendorInvoiceDocs(
            @RequestParam(value = "invoiceNo", required = false) String invoiceNo,
            @RequestParam(value = "pono", required = false) String pono) {

        Map<String, Object> response = new HashMap<>();

        if ((invoiceNo == null || invoiceNo.trim().isEmpty()) &&
            (pono == null || pono.trim().isEmpty())) {
            response.put("status", "FAIL");
            response.put("statusMsg", "Either invoice number or PO number must be provided.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<String> matchingFileNames = new ArrayList<>();

        try {
            if (invoiceNo != null && !invoiceNo.trim().isEmpty()) {
                // 🔎 Search using invoice number in invoice folder
                String normalizedInvoiceNo = invoiceNo.replaceAll("/", "-").trim(); // e.g., 456784
                List<FileUploadRequestModal> invoiceDocs =
                        documentUploadRepository.findByDocumentPathContainingIgnoreCase("/invoices/" + normalizedInvoiceNo + "/");

                matchingFileNames.addAll(
                        invoiceDocs.stream()
                            .map(doc -> doc.getDocumentPath().substring(doc.getDocumentPath().lastIndexOf('/') + 1))
                            .collect(Collectors.toList())
                );
            }

            if (pono != null && !pono.trim().isEmpty()) {
                // 🔎 Search using PO number in purchase-order folder
                String poFolder = "PO-" + pono.trim();
                List<FileUploadRequestModal> poDocs =
                        documentUploadRepository.findByDocumentPathContainingIgnoreCase("/purchase-orders/" + poFolder + "/");

                matchingFileNames.addAll(
                        poDocs.stream()
                            .map(doc -> doc.getDocumentPath().substring(doc.getDocumentPath().lastIndexOf('/') + 1))
                            .collect(Collectors.toList())
                );
            }

            if (matchingFileNames.isEmpty()) {
                response.put("status", "FAIL");
                response.put("statusMsg", "No documents found for the provided input.");
            } else {
                response.put("status", "SUCCESS");
                response.put("statusMsg", "Documents retrieved successfully.");
                response.put("documents", matchingFileNames);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "FAIL");
            response.put("statusMsg", "Error while retrieving documents.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
//    @EncryptResponse
//    @PostMapping("/uploadVendorInvoiceDocs")
//    public ResponseEntity<?> uploadVendorInvoiceDocs(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("vendorName") String vendorName,
//            @RequestParam("documentTypes") List<String> documentTypes,
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String pono) {
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
//        String baseDir = "/opt/cvmsdocuments/vendor_invoice";
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            String documentType = documentTypes.get(i).toLowerCase();
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            if (pono == null || pono.isEmpty()) {
//                uploadResults.add("PONO is required for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String subFolder = "";
//            String newFileName = "";
//
//            if (documentType.equalsIgnoreCase("invoice")) {
//                subFolder = "invoices/INV-" ;
//                newFileName = "INV_" + getFileExtension(file.getOriginalFilename());
//            } else {
//                subFolder = "purchase-orders/PO-" + pono;
//                newFileName = documentType.toUpperCase() + "_" + pono + getFileExtension(file.getOriginalFilename());
//            }
//
//            try {
//                String uploadStatus = sftpUploaderService.uploadFileToServer(
//                        file, baseDir, vendorName, subFolder, newFileName
//                );
//
//                if (uploadStatus.startsWith("File uploaded successfully")) {
//                    FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//                    fileUploadRequest.setApplicantId(applicantId);
//                    fileUploadRequest.setDocumentType(documentType);
//                    fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + subFolder + "/" + newFileName);
//                    documentUploadRepository.save(fileUploadRequest);
//
//                    uploadResults.add("File uploaded successfully for document type: " + documentType);
//                } else {
//                    uploadResults.add("File upload failed for document type: " + documentType);
//                    allUploadsSuccessful = false;
//                }
//            } catch (Exception e) {
//                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//        }
//
//        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
//        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
  
    
//    @EncryptResponse
//    @PostMapping("/uploadVendorInvoiceDocs")                                                     //  [update in document type]
//    public ResponseEntity<?> uploadVendorInvoiceDocs(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("vendorName") String vendorName,
//            @RequestParam("documentTypes") List<String> documentTypes,
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String pono) {
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
//        String baseDir = "/opt/cvmsdocuments/vendor_invoice";
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            String documentType = documentTypes.get(i);
//
//            if (file.isEmpty()) {
//                uploadResults.add("File is empty for document type: " + documentType);
//                allUploadsSuccessful = false;
//                continue;
//            }
//
//            String newFileName;
//            if (documentType.equalsIgnoreCase("PO") && pono != null && !pono.isEmpty()) {
//                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
//            } else {
//                newFileName = documentType + getFileExtension(file.getOriginalFilename());
//            }
//
//            try {
//                // Upload to SFTP server
//                String uploadStatus = sftpUploaderService.uploadFileToServer(
//                        file, baseDir, vendorName, "ERP", newFileName
//                );
//
//                // Save metadata if upload succeeded
//                if (uploadStatus.startsWith("File uploaded successfully")) {
//                    FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//                    fileUploadRequest.setApplicantId(applicantId);
//                    fileUploadRequest.setDocumentType(documentType); // Now accepts string directly
//                    fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/ERP/" + newFileName);
//                    documentUploadRepository.save(fileUploadRequest);
//
//                    uploadResults.add("File uploaded successfully for document type: " + documentType);
//                } else {
//                    uploadResults.add("File upload failed for document type: " + documentType);
//                    allUploadsSuccessful = false;
//                }
//            } catch (Exception e) {
//                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
//                allUploadsSuccessful = false;
//            }
//        }
//
//        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
//        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
//        response.put("details", uploadResults);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


    
    
//    @EncryptResponse
//    @PostMapping("/uploadVendorInvoiceDocs")
//    public List<String> uploadVendorFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("vendorName") String vendorName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String pono) {  // Optional PONO parameter
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
//            // Handle special case for PO document type by appending PONO
//            String newFileName;
//            if (documentType == DocumentType.PO && pono != null && !pono.isEmpty()) {
//                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
//            } else {
//                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//            }
//
//            // Define the base directory
//            String baseDir = "/opt/cvmsdocuments/vendor_invoice"; 
//
//            // Upload the file to the server, specifying "ERP" as the default project
//            String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, vendorName, "ERP", newFileName);
//
//            if (uploadStatus.startsWith("File uploaded successfully")) {
//                // Create a file upload request object
//                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
//                fileUploadRequest.setApplicantId(applicantId);
//                fileUploadRequest.setDocumentType(documentType);
//                fileUploadRequest.setDocumentPath(baseDir + "/" + vendorName + "/" + "ERP" + "/" + newFileName);
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
  
// **********************************************************************************************************************************************
    
    
    @EncryptResponse
    @PostMapping("/uploadSalesOpportunityDocs")
    public ResponseEntity<?> uploadSalesOpportunityDocs(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("customerName") String customerName,
            @RequestParam("srNo") String srNo,
            @RequestParam("documentTypes") List<String> documentTypes,
            @RequestParam("applicantId") String applicantId,
            @RequestParam(value = "PONO", required = false) String pono) {

        Map<String, Object> response = new HashMap<>();
        List<String> uploadResults = new ArrayList<>();

        // Validate input
        if (files.size() != documentTypes.size()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "The number of files and document types must match.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean allUploadsSuccessful = true;
        String baseDir = "/opt/cvmsdocuments/sales_opportunity";

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                allUploadsSuccessful = false;
                continue;
            }

            String newFileName;
            if (documentType.equalsIgnoreCase("PO") && pono != null && !pono.trim().isEmpty()) {
                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
            } else {
                newFileName = documentType + getFileExtension(file.getOriginalFilename());
            }

            String remotePath = baseDir + "/" + customerName + "/" + srNo + "/" + newFileName;

            try {
                // Upload file to SFTP
                String uploadStatus = sftpUploaderService.uploadFileToServer(file, baseDir, customerName, srNo, newFileName);

                if (uploadStatus.startsWith("File uploaded successfully")) {
                    FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                    fileUploadRequest.setApplicantId(applicantId);
                    fileUploadRequest.setDocumentType(documentType); // Now just a String
                    fileUploadRequest.setDocumentPath(remotePath);
                    documentUploadRepository.save(fileUploadRequest);

                    uploadResults.add("File uploaded successfully for document type: " + documentType);
                } else {
                    uploadResults.add("File upload failed for document type: " + documentType);
                    allUploadsSuccessful = false;
                }

            } catch (Exception e) {
                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
                allUploadsSuccessful = false;
            }
        }

        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
        response.put("details", uploadResults);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
//   GET API 
    @EncryptResponse
    @GetMapping("/getSalesOpportunityDocsByCustomer")
    public ResponseEntity<?> getSalesOpportunityDocsByCustomer(
            @RequestParam("customerName") String customerName) {

        Map<String, Object> response = new HashMap<>();

        if (customerName == null || customerName.trim().isEmpty()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "Customer name is required.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // 🔍 Fetch all documents where path contains the customer folder
            List<FileUploadRequestModal> docs = documentUploadRepository
                    .findByDocumentPathContainingIgnoreCase("/" + customerName + "/");

            if (docs.isEmpty()) {
                response.put("status", "FAIL");
                response.put("statusMsg", "No documents found for customer: " + customerName);
            } else {
                List<String> fileNames = docs.stream()
                        .map(doc -> {
                            String fullPath = doc.getDocumentPath();
                            return fullPath.substring(fullPath.lastIndexOf('/') + 1); // extract file name
                        })
                        .collect(Collectors.toList());

                response.put("status", "SUCCESS");
                response.put("statusMsg", "Documents retrieved successfully.");
                response.put("documents", fileNames);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "FAIL");
            response.put("statusMsg", "Error while fetching documents.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @EncryptResponse
//    @PostMapping("/uploadSalesOpportunityDocs")
//    public List<String> uploadSalesOpportunityFiles(
//            @RequestParam("files") List<MultipartFile> files,
//            @RequestParam("customerName") String customerName,
//            @RequestParam("srNo") String srNo,
//            @RequestParam("documentTypes") List<String> documentTypes,  // Updated to List<String> to accept string values
//            @RequestParam("applicantId") String applicantId,
//            @RequestParam(value = "PONO", required = false) String pono) {  // Optional PONO parameter
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
//            // Handle special case for PO document type by appending PONO
//            String newFileName;
//            if (documentType == DocumentType.PO && pono != null && !pono.isEmpty()) {
//                newFileName = "PO_" + pono + getFileExtension(file.getOriginalFilename());
//            } else {
//                newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
//            }
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

//    
//    
//    @EncryptResponse
//    @GetMapping("/downloadVendor")
//    public ResponseEntity<Object> downloadVendorFile(@RequestParam("vendorName") String vendorName,
//                                                     @RequestParam("fileName") String fileName) {
//        try {
//            // Decode URL-encoded parameters to handle spaces correctly
//            vendorName = URLDecoder.decode(vendorName, StandardCharsets.UTF_8);
//            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid encoding in parameters.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Construct the remote file path
//        String remoteFilePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/ERP/" + fileName;
//
//        byte[] fileContent;
//        try {
//            // Attempt to download the file content
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (com.jcraft.jsch.SftpException e) {
//            e.printStackTrace();
//            if (e.getMessage().contains("No such file")) {
//                java.util.Map<String, String> errorResponse = new HashMap<>();
//                errorResponse.put("message", "The selected document is not available.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                     .contentType(MediaType.APPLICATION_JSON)
//                                     .body(errorResponse);
//            } else {
//                java.util.Map<String, String> errorResponse = new HashMap<>();
//                errorResponse.put("error", e.getMessage());
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                     .contentType(MediaType.APPLICATION_JSON)
//                                     .body(errorResponse);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        if (fileContent == null || fileContent.length == 0) {
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "The selected document is not available.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Base64 encode the file content
//        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);
//
//        // Create the response body with the base64 string
//        java.util.Map<String, String> successResponse = new HashMap<>();
//        successResponse.put("fileName", fileName);
//        successResponse.put("fileContent", base64EncodedFile);
//
//        return ResponseEntity.ok()
//                             .contentType(MediaType.APPLICATION_JSON)
//                             .body(successResponse);
//    }
//
//
//    @EncryptResponse   
//    @GetMapping("/downloadClientDoc")
//    public ResponseEntity<Object> downloadFile(@RequestParam("clientName") String clientName,
//                                               @RequestParam("projectName") String projectName,
//                                               @RequestParam("fileName") String fileName) {
//
//        try {
//            // Decode URL-encoded parameters to handle spaces correctly
//            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
//            projectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
//            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid encoding in parameters.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Construct the remote file path
//        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + fileName;
//
//        byte[] fileContent;
//        try {
//            // Attempt to retrieve the file from the server
//            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//        } catch (com.jcraft.jsch.SftpException e) {
//            e.printStackTrace();
//            if (e.getMessage().contains("No such file")) {
//                java.util.Map<String, String> errorResponse = new HashMap<>();
//                errorResponse.put("message", "The selected document is not available.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                     .contentType(MediaType.APPLICATION_JSON)
//                                     .body(errorResponse);
//            } else {
//                java.util.Map<String, String> errorResponse = new HashMap<>();
//                errorResponse.put("error", e.getMessage());
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                     .contentType(MediaType.APPLICATION_JSON)
//                                     .body(errorResponse);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "An unexpected error occurred: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        if (fileContent == null || fileContent.length == 0) {
//            java.util.Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "The selected document is not available.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Encode file content to Base64
//        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);
//
//        java.util.Map<String, Object> response = new HashMap<>();
//        response.put("fileName", fileName);
//        response.put("fileContent", base64EncodedFile);
//
//        return ResponseEntity.ok()
//                             .contentType(MediaType.APPLICATION_JSON)
//                             .body(response);
//    }
//
//
//	    @GetMapping("/downloadClientDocsByClientName")
//	    public ResponseEntity<Object> downloadClientFileByClientName(
//	            @RequestParam("clientName") String clientName,
//	            @RequestParam("fileName") String fileName) {
//	
//	        try {
//	            // Decode parameters to handle spaces properly
//	            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
//	            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//	        } catch (Exception e) {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//	                                 .contentType(MediaType.APPLICATION_JSON)
//	                                 .body(Map.of("error", "Invalid encoding in parameters."));
//	        }
//	        // Construct the remote file path
//	        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/ERP/" + fileName;
//	
//	        byte[] fileContent;
//	        try {
//	            // Retrieve the file content from the server
//	            fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            // Check for specific exception indicating file not found
//	            String errorMessage = e.getMessage();
//	            // You can customize this to be more specific based on your exception handling
//	            if (errorMessage != null && errorMessage.contains("No such file")) {
//	                errorMessage = "The selected document is not available."; // Custom message
//	            }
//	
//	            // Return JSON response in case of error
//	            Map<String, String> errorResponse = new HashMap<>();
//	            errorResponse.put("error", errorMessage); // Include the error message
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                                 .contentType(MediaType.APPLICATION_JSON)
//	                                 .body(errorResponse);
//	        }
//	
//	        // Check if the file content is null or empty
//	        if (fileContent == null || fileContent.length == 0) {
//	            // Return error response if the file is not found or is empty
//	            Map<String, String> errorResponse = new HashMap<>();
//	            errorResponse.put("message", "The selected document is not available.");
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	                                 .contentType(MediaType.APPLICATION_JSON)
//	                                 .body(errorResponse);
//	        }
//	
//	        // Encode the file content into Base64
//	        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);
//	
//	        // Create the response
//	        Map<String, Object> response = new HashMap<>();
//	        response.put("fileName", fileName);
//	        response.put("fileType", getFileType(fileName));  // Optional: Include the file type (MIME type)
//	        response.put("imageByte", base64EncodedFile);   // The base64 string of the file content
//	
//	        return ResponseEntity.ok()
//	                             .contentType(MediaType.APPLICATION_JSON)
//	                             .body(response);
//	    }
//	
//	    // Helper method to determine the file type (MIME type) based on file extension
//	    private String getFileType(String fileName) {
//	        if (fileName.endsWith(".pdf")) {
//	            return "application/pdf";
//	        } else if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
//	            return "text/html";
//	        } else if (fileName.endsWith(".xls")) {
//	            return "application/vnd.ms-excel";
//	        } else if (fileName.endsWith(".xlsx")) {
//	            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//	        } else if (fileName.endsWith(".csv")) {
//	            return "text/csv";
//	        } else {
//	            return "application/octet-stream";  // Default MIME type
//	        }
//	    }
//
//    
//    @GetMapping("/downloadSalesOpportunityDoc")
//    public ResponseEntity<Object> downloadSalesOpportunityFile(@RequestParam("customerName") String customerName,
//                                                                @RequestParam("srNo") String srNo,
//                                                                @RequestParam("fileName") String fileName) {
//
//    	try {
//            // Decode parameters to handle spaces properly
//    		customerName = URLDecoder.decode(customerName, StandardCharsets.UTF_8);
//    		srNo = URLDecoder.decode(srNo, StandardCharsets.UTF_8);
//            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(Map.of("error", "Invalid encoding in parameters."));
//        }
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
//            String errorMessage = e.getMessage();
//            if (errorMessage != null && errorMessage.contains("No such file")) {
//                errorMessage = "No file content available for the selected document."; // Custom message
//            }
//
//            // Create a JSON error response
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", errorMessage);
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Check if file content is empty or null
//        if (fileContent == null || fileContent.length == 0) {
//            // Return JSON response for file not found
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "No file content available for the selected document.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .contentType(MediaType.APPLICATION_JSON)
//                                 .body(errorResponse);
//        }
//
//        // Encode the file content as a Base64 string
//        String base64EncodedFile = Base64.getEncoder().encodeToString(fileContent);
//
//        // Prepare the JSON response
//        Map<String, Object> response = new HashMap<>();
//        response.put("fileName", fileName);
//        response.put("fileContent", base64EncodedFile);
//
//        // Return the Base64-encoded file content as part of a JSON response
//        return ResponseEntity.ok()
//                             .contentType(MediaType.APPLICATION_JSON)
//                             .body(response);
//    }
//
//    
//    
    @EncryptResponse
    @PostMapping("/uploadClientDataDocuments")
    public ResponseEntity<?> uploadClientDataDocuments(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("clientName") String clientName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("documentTypes") List<String> documentTypes,
            @RequestParam("applicantId") String applicantId) {

        Map<String, Object> response = new HashMap<>();
        List<String> uploadResults = new ArrayList<>();

        // Validate input
        if (files.size() != documentTypes.size()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "The number of files and document types must match.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean allUploadsSuccessful = true;
        String baseDir = "/opt/cvmsdocuments/client";

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
//            DocumentType documentType = documentTypes.get(i);
            String documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                allUploadsSuccessful = false;
                continue;
            }

//            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            String newFileName = documentType + getFileExtension(file.getOriginalFilename());

            String remotePath = baseDir + "/" + clientName + "/" + newFileName;

            try {
                // Upload to SFTP
                String uploadStatus = sftpUploaderService.uploadFileToServer(
                        file, baseDir, clientName, "/", newFileName
                );

                // Save metadata
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);  // <-- updated line
                fileUploadRequest.setDocumentPath(remotePath);
                documentUploadRepository.save(fileUploadRequest);

                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } catch (Exception e) {
                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
                allUploadsSuccessful = false;
            }
        }

        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
        response.put("details", uploadResults);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @EncryptResponse
    @PostMapping("/uploadVendorDataDocs")
    public ResponseEntity<?> uploadVendorDataDocs(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("vendorName") String vendorName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("documentTypes") List<String> documentTypes,
            @RequestParam("applicantId") String applicantId) {

        Map<String, Object> response = new HashMap<>();
        List<String> uploadResults = new ArrayList<>();

        // Validate input
        if (files.size() != documentTypes.size()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "The number of files and document types must match.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean allUploadsSuccessful = true;
        String baseDir = "/opt/cvmsdocuments/vendor";

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
//            DocumentType documentType = documentTypes.get(i);
            String documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                allUploadsSuccessful = false;
                continue;
            }

//            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            String newFileName = documentType + getFileExtension(file.getOriginalFilename());
            String remotePath = baseDir + "/" + vendorName + "/" + newFileName;

            try {
                // Upload file to: /opt/cvmsdocuments/vendor/<vendorName>/<filename>
                String uploadStatus = sftpUploaderService.uploadFileToServer(
                        file, baseDir, vendorName, "/", newFileName);

                // Save metadata to DB
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);
                fileUploadRequest.setDocumentPath(remotePath);
                documentUploadRepository.save(fileUploadRequest);

                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } catch (Exception e) {
                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
                allUploadsSuccessful = false;
            }
        }

        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
        response.put("details", uploadResults);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    
    @EncryptResponse
    @PostMapping("/uploadProjectDataDocuments")
    public ResponseEntity<?> uploadProjectDataDocuments(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("projectName") String projectName,
//            @RequestParam("documentTypes") List<DocumentType> documentTypes,
            @RequestParam("documentTypes") List<String> documentTypes,
            @RequestParam("applicantId") String applicantId) {

        Map<String, Object> response = new HashMap<>();
        List<String> uploadResults = new ArrayList<>();

        // Validate input
        if (files.size() != documentTypes.size()) {
            response.put("status", "FAIL");
            response.put("statusMsg", "The number of files and document types must match.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        boolean allUploadsSuccessful = true;
        String baseDir = "/opt/cvmsdocuments/project";

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
//            DocumentType documentType = documentTypes.get(i);
            String documentType = documentTypes.get(i);

            if (file.isEmpty()) {
                uploadResults.add("File is empty for document type: " + documentType);
                allUploadsSuccessful = false;
                continue;
            }

//            String newFileName = documentType.name() + getFileExtension(file.getOriginalFilename());
            String newFileName = documentType + getFileExtension(file.getOriginalFilename());
            String remotePath = baseDir + "/" + projectName + "/" + newFileName;

            try {
                // Upload file to: /opt/cvmsdocuments/project/<projectName>/<filename>
                String uploadStatus = sftpUploaderService.uploadFileToServer(
                        file, baseDir, projectName, "/", newFileName);

                // Save metadata to DB
                FileUploadRequestModal fileUploadRequest = new FileUploadRequestModal();
                fileUploadRequest.setApplicantId(applicantId);
                fileUploadRequest.setDocumentType(documentType);
                fileUploadRequest.setDocumentPath(remotePath);
                documentUploadRepository.save(fileUploadRequest);

                uploadResults.add("File uploaded successfully for document type: " + documentType);
            } catch (Exception e) {
                uploadResults.add("Upload failed for document type: " + documentType + ". Error: " + e.getMessage());
                allUploadsSuccessful = false;
            }
        }

        response.put("status", allUploadsSuccessful ? "SUCCESS" : "FAIL");
        response.put("statusMsg", allUploadsSuccessful ? "All files uploaded successfully." : "Some files failed to upload.");
        response.put("details", uploadResults);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



//     Helper method to get the file extension
    private String getFileExtension(String originalFileName) {
        int lastIndex = originalFileName.lastIndexOf('.');
        return (lastIndex == -1) ? "" : originalFileName.substring(lastIndex);
    }
    
    
    @EncryptResponse
    @GetMapping("/downloadClientDataDoc")
    public ResponseEntity<Object> downloadClientDataDoc(
            @RequestParam("clientName") String clientName,
            @RequestParam("fileName") String fileName) {

        try {
            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid encoding in parameters.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + fileName;

        try {
            byte[] fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
            if (fileContent == null || fileContent.length == 0) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "The selected document is not available.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("fileContent", Base64.getEncoder().encodeToString(fileContent));
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    
    @EncryptResponse
    @GetMapping("/downloadVendorDataDoc")
    public ResponseEntity<Object> downloadVendorDataDoc(
            @RequestParam("vendorName") String vendorName,
            @RequestParam("fileName") String fileName) {

        try {
            vendorName = URLDecoder.decode(vendorName, StandardCharsets.UTF_8);
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid encoding in parameters.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String remoteFilePath = "/opt/cvmsdocuments/vendor/" + vendorName + "/" + fileName;

        try {
            byte[] fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
            if (fileContent == null || fileContent.length == 0) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "The selected document is not available.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("fileContent", Base64.getEncoder().encodeToString(fileContent));
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    
    @EncryptResponse
    @GetMapping("/downloadProjectDataDoc")
    public ResponseEntity<Object> downloadProjectDataDoc(
            @RequestParam("projectName") String projectName,
            @RequestParam("fileName") String fileName) {

        try {
            projectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
            fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid encoding in parameters.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        String remoteFilePath = "/opt/cvmsdocuments/project/" + projectName + "/" + fileName;

        try {
            byte[] fileContent = sftpUploaderService.downloadFileFromServer(remoteFilePath);
            if (fileContent == null || fileContent.length == 0) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "The selected document is not available.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("fileContent", Base64.getEncoder().encodeToString(fileContent));
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    
    @EncryptResponse
    @GetMapping("/getClientDocuments")
    public ResponseEntity<?> getClientDocuments(@RequestParam("clientName") String clientName) {
        try {
            clientName = URLDecoder.decode(clientName, StandardCharsets.UTF_8);
            String folderPath = "/opt/cvmsdocuments/client/" + clientName;

            List<String> fileNames = sftpUploaderService.listFilesInDirectory(folderPath);
            return ResponseEntity.ok().body(Map.of("documents", fileNames));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch client documents: " + e.getMessage()));
        }
    }

    @EncryptResponse
    @GetMapping("/getVendorDocuments")
    public ResponseEntity<?> getVendorDocuments(@RequestParam("vendorName") String vendorName) {
        try {
            vendorName = URLDecoder.decode(vendorName, StandardCharsets.UTF_8);
            String folderPath = "/opt/cvmsdocuments/vendor/" + vendorName;

            List<String> fileNames = sftpUploaderService.listFilesInDirectory(folderPath);
            return ResponseEntity.ok().body(Map.of("documents", fileNames));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch vendor documents: " + e.getMessage()));
        }
    }

    @EncryptResponse
    @GetMapping("/getProjectDocuments")
    public ResponseEntity<?> getProjectDocuments(@RequestParam("projectName") String projectName) {
        try {
            projectName = URLDecoder.decode(projectName, StandardCharsets.UTF_8);
            String folderPath = "/opt/cvmsdocuments/project/" + projectName;

            List<String> fileNames = sftpUploaderService.listFilesInDirectory(folderPath);
            return ResponseEntity.ok().body(Map.of("documents", fileNames));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch project documents: " + e.getMessage()));
        }
    }

    
    
    
    
    
   
}