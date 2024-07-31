//package org.ss.vendorapi.service;
//
//import java.io.IOException;
//import java.lang.System.Logger;
//import java.lang.System.Logger.Level;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.ss.vendorapi.entity.FileEntity;
//import org.ss.vendorapi.exceptions.RequestNotFoundException;
//import org.ss.vendorapi.repository.FileDataRepository;
//import org.ss.vendorapi.util.Constants;
//import org.ss.vendorapi.util.Parameters;
//
//
//@Service
//public class FileServiceImpl implements FileService {
//	private static final Logger log = System.getLogger("FileController");
//
//
//	@Autowired
//	private FileDataRepository fileDataRepository;
//	
//	@Override
//	public ResponseEntity<?> uploadApplicantDocuments(MultipartFile file,String userId,String fileType) {
//          Map<String, Object>	statusMap=new HashMap<>();
//          log.log(Level.INFO, "inside upload file service");
//          try 
//          {
//        	  //System.out.println(file.getContentType());
//              
//        	  
//        	  if(fileType.equals("profilePic"))
//        	  {
//        		  //System.out.println(file.getSize()/(1024));
//					if(!file.getContentType().equalsIgnoreCase("image/jpg")&&!file.getContentType().equalsIgnoreCase("image/jpeg")) 
//					{
//						throw new RequestNotFoundException("file format is not allowed.");
//					}
//					
//					if(file.getSize()/1024>100) 
//					{
//						
//						throw new RequestNotFoundException("file Size is not allowed.");
//					}
//        	  }
//        	  else  if(fileType.equals("houseDocument"))
//        	  {
//        		  //System.out.println(file.getSize()/(1024*1000));
//					if(!file.getContentType().equalsIgnoreCase("image/jpg")&&!file.getContentType().equalsIgnoreCase("image/jpeg")
//							&&!file.getContentType().equalsIgnoreCase("application/pdf")) 
//					{
//						throw new RequestNotFoundException("file format is not allowed.");
//					}
//					
//					if(file.getSize()/(1024*1000)>5) 
//					{
//						throw new RequestNotFoundException("file Size is not allowed.");
//					}
//        	  }
//        	  else  if(fileType.equals("idProof")||fileType.equals("b&lForm"))
//        	  {
//        		  //System.out.println(file.getSize()/(1024*1000));
//
//					if(!file.getContentType().equalsIgnoreCase("image/jpg")&&!file.getContentType().equalsIgnoreCase("image/jpeg")
//							&&!file.getContentType().equalsIgnoreCase("application/pdf")) 
//					{
//						throw new RequestNotFoundException("file format is not allowed.");
//					}
//					
//					if(file.getSize()/(1024*1000)>1) 
//					{
//						throw new RequestNotFoundException("file Size is not allowed.");
//					}
//        	  }
//	
//		 FileEntity fileEntity=new FileEntity();
//	     byte[] images=null;
//		try {
//			images = file.getBytes();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	     fileEntity.setImageByte(images);
//	    // System.out.println(Base64.getEncoder().encodeToString(images));
//	     fileEntity.setUserId(userId);
//	     fileEntity.setUserFileType(fileType);
//	     
//	        fileDataRepository.save(fileEntity);
//			statusMap.put(Parameters.statusCode, "FUPLOAD_100");
//			statusMap.put(Parameters.status, Constants.SUCCESS);
//			statusMap.put(Parameters.statusMsg,"Image is Uploaded Successfully.");
//			return new ResponseEntity<>(statusMap, HttpStatus.OK);
//		}
//		catch (RequestNotFoundException e) {
//			
//			if(e.getMessage().equalsIgnoreCase("file format is not allowed."))
//					{
//				log.log(Level.ERROR, "inside uploading file service got exception file format is not allowed.");
//				statusMap.put(Parameters.statusCode, "FUPLOAD_101");
//				statusMap.put(Parameters.status, Constants.FAIL);
//				statusMap.put(Parameters.statusMsg, e.getMessage());
//				return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//				
//					}
//			else if(e.getMessage().equalsIgnoreCase("file Size is not allowed."))
//			{
//		log.log(Level.ERROR, "inside uploading file service got exception file Size is not allowed.");
//		statusMap.put(Parameters.statusCode, "FUPLOAD_102");
//		statusMap.put(Parameters.status, Constants.FAIL);
//		statusMap.put(Parameters.statusMsg, e.getMessage());
//		return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//		
//			}
//			
//			
//		}
//          catch (Exception ex) 
//          {
//        	  ex.printStackTrace();
//          }
//  			statusMap.put(Parameters.status, Constants.FAIL);
//  		    return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
//	}
//
//	
//}
