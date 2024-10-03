package org.ss.vendorapi.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ConfigFileReader {

	/**
	 * Reads the file from the specified file path and
	 * 
	 * @return
	 */
	public static String getConfigFile(String filePath)
	//			throws UPPCLSystemException
	{
		final String METHOD_NAME = "getConfigFile(String filePath)";
		//		logger.logMethodStart(METHOD_NAME);
		//		if (logger.isDebugLoggingEnabled())
		//            logger.log(UPPCLLogger.LOGLEVEL_DEBUG, METHOD_NAME,new StringBuilder("File path is :: ").append(filePath).toString());
		Reader reader = null;
		//BufferedReader bufferedReader = null;
		Closeable resource = reader;
		String configFile = "";
		try {
			FileInputStream fis = new FileInputStream(filePath);
			//reader = new FileReader(filePath);
			// Construct the BufferedReader object
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			resource = bufferedReader;
			String input = "";
			while ((input = bufferedReader.readLine()) != null) {
				configFile += input + '\n';
			}
		} catch (FileNotFoundException ex) {
			//			if (logger.isErrorLoggingEnabled())
			//	            logger.log(UPPCLLogger.LOGLEVEL_ERROR, METHOD_NAME,new StringBuilder("Exception while reading the File from location: ").append(filePath).toString() , ex);
			//			throw new UPPCLSystemException(CLASS_NAME,"Exception while reading the File from location",ex);

		} catch (IOException ex) {
			//			if (logger.isErrorLoggingEnabled())
			//	            logger.log(UPPCLLogger.LOGLEVEL_ERROR, METHOD_NAME,new StringBuilder("Exception while retrieving the file: ").append(filePath).toString() , ex);
			//			throw new UPPCLSystemException(CLASS_NAME,"Exception while retrieving the file",ex);
		} finally {
			// Close the BufferedReader
			try {
				if(null != resource){
					resource.close();
				}
				/*
				 * if (null != bufferedReader)
				 * bufferedReader.close();
				 */
			} catch (IOException ex) {
				//				if (logger.isErrorLoggingEnabled())
				//		            logger.log(UPPCLLogger.LOGLEVEL_ERROR, METHOD_NAME,new StringBuilder("Exception in closing the resource : ").append(filePath).toString() , ex);
			}
		}
		//		logger.logMethodEnd(METHOD_NAME);
		return configFile;
	}
}
