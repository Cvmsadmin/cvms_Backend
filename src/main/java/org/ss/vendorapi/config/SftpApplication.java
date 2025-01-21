//package org.ss.vendorapi.config;
//
//import java.io.File;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.ss.vendorapi.service.SftpService;
//
//@SpringBootApplication
//public class SftpApplication implements CommandLineRunner{
//	
//	 @Autowired
//	 private SftpService sftpUploaderService;
//
//	 public static void main(String[] args) {
//	     SpringApplication.run(SftpApplication.class, args);
//	    }
//
//	 @Override
//	 public void run(String... args) throws Exception {
//	     File fileToUpload = new File("path/to/local/file.txt");
//	     sftpUploaderService.uploadFile(fileToUpload);
//	    }
//	
//	
//
//}
