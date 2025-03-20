package org.ss.vendorapi.service;
 
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.ss.vendorapi.config.SftpConfig;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
 
@Service
public class SftpUploaderService {
 
    @Autowired
    private SftpConfig sftpConfig;
    
//    @Value("${sftp.privateKeyPath}")
//    private String privateKeyPath;

 
    public String uploadFileToServer(MultipartFile file, String baseDir, String clientName, String newFileName) {
        return uploadFileToServer(file, baseDir, clientName, "ERP");
    }
 
    public String uploadFileToServer(MultipartFile file, String baseDir, String clientName, String projectName, String newFileName) {
        Session session = null;
        ChannelSftp channelSftp = null;
 
        try {
            // creating SFTP session
            JSch jsch = new JSch();
            
            // Load private key for authentication
//            jsch.addIdentity(privateKeyPath);
            
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
            
//            try (InputStream inputStream = file.getInputStream()) {
//                channelSftp.put(inputStream, fullPath + "/" + file.getOriginalFilename());
//                return "File uploaded successfully to: " + fullPath;
//            }
    
    // Upload the file with the new name
    try (InputStream inputStream = file.getInputStream()) {
        channelSftp.put(inputStream, fullPath + "/" + newFileName);
        return "File uploaded successfully to: " + fullPath + "/" + newFileName;
    }
        } catch (SftpException se) {
            // Handle specific SFTP exceptions (e.g., permission issues)
            se.printStackTrace();
            return "SFTP Error: " + se.getMessage();
 
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
        
     // Load private key for authentication
//        jsch.addIdentity(privateKeyPath);
 
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
}
 
 