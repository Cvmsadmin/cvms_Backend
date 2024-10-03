package org.ss.vendorapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class DocumentUploadController {
	
	
//	private static final Logger logger = LoggerFactory.getLogger(DocumentUploadController.class);
//
//	    @Autowired
//	    private DocumentUploadService documentUploadService;
//
//	    @Autowired
//	    private ObjectMapper objectMapper;
//
//	    @Value("${file.upload.path}")
//	    private String path;
//
//		@PostMapping("/upload")
//	    public ResponseEntity<?> fileUpload(
//	                                         @RequestParam String fileData,
//	                                         @RequestParam MultipartFile[] files) throws java.io.IOException {
//
////	       
//
//	        logger.info("## Inside method fileUpload ##");
//
//	        FileUploadRequestModal fileDataObj;
//	        Map<String, Object> statusMap = new HashMap<>();
//
//	        try {
//	            fileDataObj = objectMapper.readValue(fileData, FileUploadRequestModal.class);
//
//	            if (fileDataObj.getDocumentPath().length() == files.length) {
//	                for (MultipartFile file : files) {
//	                    String contentType = file.getContentType();
//	                    if (!CommonUtils.isValidFileType(contentType)) {
//	                        statusMap.put(Parameters.statusMsg, "Invalid file type. Only PDF files or images are allowed.");
//	                        statusMap.put(Parameters.status, "FAIL");
//	                        return ResponseEntity.badRequest().body(statusMap);
//	                    }
//	                }
//
//	                fileDataObj = documentUploadService.uploadImage(path, files, fileDataObj);
//
//	            } else {
//	                statusMap.put(Parameters.statusMsg, "Invalid Request");
//	                statusMap.put(Parameters.status, "FAIL");
//	                return ResponseEntity.badRequest().body(statusMap);
//	            }
//
//	        } catch (JsonProcessingException e) {
//	            logger.error("Invalid Request", e);
//	            statusMap.put(Parameters.statusMsg, "Invalid Request");
//	            statusMap.put(Parameters.status, "FAIL");
//	            return ResponseEntity.badRequest().body(statusMap);
//	        } catch (IOException e) {
//	            logger.error("Image is not uploaded due to server error", e);
//	            return ResponseEntity.status(500).body("Image is not uploaded due to server error !!");
//	        }
//
//	        statusMap.put("result", fileDataObj);
//	        statusMap.put(Parameters.status, "SUCCESS");
//	        return ResponseEntity.ok(statusMap);
//	    }
//	
	
	

//	@PostMapping("/upload")
//    public ResponseEntity<?> fileUpload(
//            @RequestHeader("appServiceKey") String appServiceKey,
//            @RequestParam("fileData") String fileData,
//            @RequestParam("files") MultipartFile[] files) {
//
//        String apikey = "YOUR_API_KEY"; // Replace with actual API key retrieval
//        ResponseEntity<?> responseEntity = CommonUtils.validateAppServiceKey(appServiceKey, apikey);
//        if (responseEntity != null) {
//            return responseEntity;
//        }
//
//        log.log(Level.INFO, "## Inside method fileUpload ##");
//        DocumentUploadEntity fileDataObj;
//        Map<String, Object> statusMap = new HashMap<>();
//        try {
//            fileDataObj = objectMapper.readValue(fileData, DocumentUploadEntity.class);
//        } catch (JsonProcessingException e) {
//            statusMap.put("statusMsg", "Invalid Request");
//            statusMap.put("status", "FAIL");
//            return new ResponseEntity<>(statusMap, HttpStatus.SC_BAD_REQUEST);
//        }
//
//        try {
//            if (fileDataObj.getFiles().size() == files.length) {
//                for (MultipartFile file : files) {
//                    String contentType = file.getContentType();
//                    if (!CommonUtils.isValidFileType(contentType)) {
//                        statusMap.put("statusMsg", "Invalid file type. Only PDF files or images are allowed.");
//                        statusMap.put("status", "FAIL");
//                        return new ResponseEntity<>(statusMap, HttpStatus.SC_BAD_REQUEST);
//                    }
//                }
//                DocumentUploadEntity response = documentUploadService.uploadFiles(path, files, fileDataObj);
//                statusMap.put("result", response);
//                statusMap.put("status", "SUCCESS");
//                return new ResponseEntity<>(statusMap, HttpStatus.OK);
//            } else {
//                statusMap.put("statusMsg", "Invalid Request");
//                statusMap.put("status", "FAIL");
//                return new ResponseEntity<>(statusMap, HttpStatus.SC_BAD_REQUEST);
//            }
//        } catch (IOException e) {
//            log.log(Level.SEVERE, "File upload failed", e);
//            return new ResponseEntity<>("File is not uploaded due to server error!", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
