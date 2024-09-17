package org.ss.vendorapi.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.ss.vendorapi.entity.FileUploadRequestModal;
import org.ss.vendorapi.repository.DocumentUploadRepository;

import io.jsonwebtoken.io.IOException;
import jakarta.persistence.criteria.Path;

public class DocumentUploadService {
	
	 @Autowired
	    private DocumentUploadRepository repository;

//	    public FileUploadRequestModal uploadImage(String path, MultipartFile[] files, FileUploadRequestModal fileDataObj)
//	            throws IOException, java.io.IOException {
//
//	        int i = 0;
//	        for (MultipartFile file : files) {
//	            String name = file.getOriginalFilename();
//	            if (name == null) {
//	                throw new IOException("File name is null");
//	            }
//
//	            // Generate random name for the file
//	            String docType = fileDataObj.getDocumentType().name();
//	            docType = docType.replace("/ ", " or ").replace("/", " or ").replace("?", "");
//	            docType = docType.length() > 13 ? docType.substring(0, 13) : docType;
//
//	            String randomID = docType + UUID.randomUUID().toString();
//	            String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
//
//	            // Full path
//	            java.nio.file.Path filePath = Paths.get(path, fileName);
//
//	            // Create folder if not created
//	            Files.createDirectories(filePath.getParent());
//
//	            // File copy
//	            Files.copy(file.getInputStream(), filePath);
//
//	            // Update fileDataObj
//	            fileDataObj.getDocumentPath(filePath.toString()); // Assuming you have a list of paths
//
//	            i++;
//	        }
//	        return fileDataObj;
//	    }
//	
	
	
	

//    private final String LOCAL_UPLOAD_DIR = "uploads/";
//
//    public DocumentUploadEntity uploadFiles(String path, MultipartFile[] files, DocumentUploadEntity fileDataObj) throws IOException {
//        int i = 0;
//        for (MultipartFile file : files) {
//            String name = file.getOriginalFilename();
//            String docType = fileDataObj.getFiles().get(i).getDocumentType();
//            docType = docType.replace("/ ", " or ").replace("/", " or ").replace("?", "");
//            docType = docType.length() < 13 ? docType : docType.substring(0, 13);
//            String randomID = docType + UUID.randomUUID().toString();
//            String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
//            String filePath = path + File.separator + fileName;
//
//            File folder = new File(path);
//            if (!folder.exists()) {
//                folder.mkdir();
//            }
//
//            Files.copy(file.getInputStream(), Paths.get(filePath));
//
//            // Save file metadata in the database (optional)
//            fileDataObj.getFiles().get(i).setPathOfFile(filePath);
//            i++;
//        }
//
//        // Optionally, upload the files to SFTP server
//        // SftpUploader.uploadFileToServer(localFilePath, remoteFilePath);
//
//        return fileDataObj;
//    }
}
