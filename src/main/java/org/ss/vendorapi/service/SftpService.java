//package org.ss.vendorapi.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelExec;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//
//@Service
//public class SftpService {
//
//    private static final String SFTP_HOST = "10.11.1.244";
//    private static final int SFTP_PORT = 22;
//    private static final String SFTP_USERNAME = "cvms";
////    private static final String PRIVATE_KEY_PATH = "/opt/cvmsdocuments/client/cvms2_cvms2.pem";
//    private static final String PRIVATE_KEY_PATH ="C:/Users/amitrawa/Downloads/CVMS_PPK_files/cvms2_cvms2.pem";
//
//
//    public void uploadFile(MultipartFile file, String remotePath) throws Exception {
//        JSch jsch = new JSch();
//        jsch.addIdentity(PRIVATE_KEY_PATH);
//
//        // Establish the SFTP session
//        Session session = jsch.getSession(SFTP_USERNAME, SFTP_HOST, SFTP_PORT);
//        session.setConfig("StrictHostKeyChecking", "no");
//        session.connect();
//
//        // Open an SFTP channel
//        Channel channel = session.openChannel("exec");
//        ((ChannelExec) channel).setCommand("ls -l");
//        channel.setInputStream(null);
//        java.io.InputStream in = channel.getInputStream();
//        channel.connect();
//        ChannelExec sftpChannel = (ChannelExec) channel;
//
//        try (InputStream inputStream = file.getInputStream()) {
////            sftpChannel.put(inputStream, remotePath);
//        } catch (Exception e) {
//            throw new Exception("Error uploading file to SFTP server", e);
//        } finally {
//            sftpChannel.disconnect();
//            session.disconnect();
//        }
//    }
//    
//
//
//
//    /**
//     * Downloads a file from the remote SFTP server.
//     *
//     * @param remoteFilePath the path of the remote file on the SFTP server
//     * @param localFile the local file where the remote file will be saved
//     * @throws Exception if any error occurs during the download
//     */
//    public void downloadFile(String remoteFilePath, File localFile) throws Exception {
//        JSch jsch = new JSch();
//        Session session = null;
//        ChannelSftp sftpChannel = null;
//
//        try {
//            // Set the private key for authentication
//            jsch.addIdentity(PRIVATE_KEY_PATH);
//
//            // Open session and connect
//            session = jsch.getSession(SFTP_USERNAME, SFTP_HOST, SFTP_PORT);
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.connect();
//
//            // Open an SFTP channel
//            Channel channel = session.openChannel("sftp");
//            channel.connect();
//            sftpChannel = (ChannelSftp) channel;
//
//            // Download file from the remote server
//            try (InputStream inputStream = sftpChannel.get(remoteFilePath)) {
//                Files.copy(inputStream, localFile.toPath());
//                System.out.println("File downloaded successfully to: " + localFile.getAbsolutePath());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("Error downloading file from SFTP server", e);
//        } finally {
//            if (sftpChannel != null && sftpChannel.isConnected()) {
//                sftpChannel.disconnect();
//            }
//            if (session != null && session.isConnected()) {
//                session.disconnect();
//            }
//        }
//    }
//
//
//}
