package org.ss.vendorapi.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ss.vendorapi.config.SftpConfig;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;



//import jakarta.websocket.Session;
	
@Service
public class SftpUploaderService {
	
	@Autowired
    private SftpConfig sftpConfig;
	
	/**
	* <h1> Infinite Computer Solutions </h1>
	* <p> WSS-BESCOM</p>
	* @author Jaydeep Pal
	* @since 04-Feb-2024
	* {@summary IT REFERS TO file upload }
	*/
	
	
	 public String uploadFileToServer(MultipartFile file, String baseDir, String clientName) {
	        return uploadFileToServer(file, baseDir, clientName, "ERP");
	    }

	
	 public String uploadFileToServer(MultipartFile file, String baseDir, String clientName, String projectName) {
	        Session session = null;
	        ChannelSftp channelSftp = null;

	        try {
	            // creating SFTP session
	            JSch jsch = new JSch();
	            session = jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHost(), sftpConfig.getPort());
	            session.setPassword(sftpConfig.getPassword());

	            Properties prop = new Properties();
	            prop.put("StrictHostKeyChecking", "no");
	            session.setConfig(prop);

	            session.connect();

	            channelSftp = (ChannelSftp) session.openChannel("sftp");
	            channelSftp.connect();

	            // Build the full directory path
	            String fullPath = baseDir + "/" + clientName + "/" + projectName;
	            
	            // Ensure directory exists
	            ensureDirectoryExists(channelSftp, fullPath);

	            try (InputStream inputStream = file.getInputStream()) {
	                channelSftp.put(inputStream, fullPath + "/" + file.getOriginalFilename());
	                return "File uploaded successfully to: " + fullPath;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "File upload failed.";
	        } finally {
	            // Disconnect from the server
	            if (channelSftp != null && channelSftp.isConnected()) {
	                channelSftp.disconnect();
	            }
	            if (session != null && session.isConnected()) {
	                session.disconnect();
	            }
	        }
	    }

	    private void ensureDirectoryExists(ChannelSftp channelSftp, String dirPath) throws SftpException {
	        String[] folders = dirPath.split("/");
	        String currentPath = "";

	        for (String folder : folders) {
	            if (folder.isEmpty()) continue; // Skip empty parts (e.g., leading slash)

	            currentPath += "/" + folder;

	            try {
	                channelSftp.cd(currentPath);
	            } catch (SftpException e) {
	                channelSftp.mkdir(currentPath);
	            }
	        }
	    }
	
	    
		
	    public byte[] downloadFileFromServer(String remoteFilePath) throws Exception {
	        JSch jsch = new JSch();
	        Session session = jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHost(), sftpConfig.getPort());
	        session.setPassword(sftpConfig.getPassword());

	        // Avoid asking for key confirmation
	        session.setConfig("StrictHostKeyChecking", "no");
	        session.connect();

	        Channel channel = session.openChannel("sftp");
	        channel.connect();

	        ChannelSftp sftpChannel = (ChannelSftp) channel;
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	        try {
	            sftpChannel.get(remoteFilePath, outputStream);
	            return outputStream.toByteArray();
	        } finally {
	            sftpChannel.disconnect();
	            session.disconnect();
	        }
	    }
	    
	    
	
//	public String uploadFileToServer(MultipartFile file, String remoteDirPath, String clientName, String projectName,  String host, int port, String username, String password) {
//		Session session = null;
//	    ChannelSftp channelSftp = null;
//	 
//	    try {
//	        // creating SFTP session
//	        JSch jsch = new JSch();
//	        session = jsch.getSession(username, host, port);
//	        session.setPassword(password);
//	 
//	        Properties prop = new Properties();
//	        prop.put("StrictHostKeyChecking", "no");
//	        session.setConfig(prop);
//	 
//	        session.connect();
//	 
//	        channelSftp = (ChannelSftp) session.openChannel("sftp");
//	        channelSftp.connect();
//	 
//	        boolean clientDirExists = false;
//	        Vector<ChannelSftp.LsEntry> fileList = channelSftp.ls(remoteDirPath);
//	        for (ChannelSftp.LsEntry entry : fileList) {
//	            if (entry.getFilename().equals(clientName) && entry.getAttrs().isDir()) {
//	                clientDirExists = true;
//	                break;
//	            }
//	        }
//	        String clientDirPath = null;
//	 
//	        if (!clientDirExists) {
//	            clientDirPath = remoteDirPath + "/"+clientName+"/"+projectName+"/";
//	            channelSftp.mkdir(clientDirPath);
////	            log.log(System.Logger.Level.INFO, "Created directory: " + clientDirPath);
//	        }
//	        try (InputStream inputStream = file.getInputStream()) {
//	            channelSftp.put(inputStream, clientDirPath);
////	            log.log(System.Logger.Level.INFO, "File uploaded successfully to: " + remoteDirPath);
//	            return "File uploaded successfully to: " + clientDirPath;
//	        }
//	 
//	    } catch (Exception e) {
////	        log.log(System.Logger.Level.ERROR, "Failed to upload file to server. Error: " + e.getMessage(), e);
//	        return "File upload failed.";
//	    } finally {
//	        // Disconnect from the server
//	        if (channelSftp != null && channelSftp.isConnected()) {
//	            channelSftp.disconnect();
//	        }
//	        if (session != null && session.isConnected()) {
//	            session.disconnect();
//	        }
////	        log.log(System.Logger.Level.INFO, "Session and channel disconnected.");
//	    }
//	}
	
	
	
	
	
	
//	public String uploadFileToServer(MultipartFile file, String remoteFilePath, String host, int port, String username, String password) {
//        Session session = null;
//        ChannelSftp channelSftp = null;
// 
//        try {
//            JSch jsch = new JSch();
//            session = jsch.getSession(username, host, port);
//            session.setPassword(password);
// 
//            Properties config = new Properties();
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//            session.connect();
// 
//            // Open the SFTP channel and connect
//            channelSftp = (ChannelSftp) session.openChannel("sftp");
//            channelSftp.connect();
// 
//            // Get the file input stream and upload it to the remote server
//            try (InputStream inputStream = file.getInputStream()) {
//                channelSftp.put(inputStream, remoteFilePath);
//                return "File uploaded successfully to: " + remoteFilePath;
//            }
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "File upload failed: " + e.getMessage();
//        } finally {
//            if (channelSftp != null && channelSftp.isConnected()) {
//                channelSftp.disconnect();
//            }
//            if (session != null && session.isConnected()) {
//                session.disconnect();
//            }
//        }
//    }

	
		
	
}
