//package org.ss.vendorapi.logging;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import ch.qos.logback.classic.LoggerContext;
//
//public class UPPCLLogger {
//
//	private static String methodStartAndEndLogging = "enabled";
//
//	public static final String LOGLEVEL_INFO = "info";
//
//	public static final String LOGLEVEL_WARN = "warn";
//
//	public static final String LOGLEVEL_DEBUG = "debug";
//
//	public static final String LOGLEVEL_FATAL = "fatal";
//
//	public static final String LOGLEVEL_ERROR = "error";
//
//	private Logger loggerModule;
//
//	public String loggingClient;
//
//	// Module level logging constants
//	public static final String MODULE_LOGIN = "Login";
//	public static final String MODULE_REGISTRATION = "Registration";
//	public static final String MODULE_BILLING = "Billing";
//	public static final String MODULE_CONSUMPTION = "Consumption";
//	public static final String MODULE_MANAGEACCOUNT = "ManageAccount";
//	public static final String MODULE_COMPLAINT = "Complaint";
//	public static final String MODULE_FRAMEWORK = "Framework";
//	public static final String MODULE_SOA = "SOA";
//	public static final String MODULE_MOBILE = "Mobile";
//	public static final String MODULE_CHATBOT = "Chatbot";
//	public static final String MODULE_SELFBILLGEN = "SelfBillGen";
//	public static final String MODULE_TEMPCONN = "TempConn";
//	public static final String MODULE_FORGOTPASSWORD = "ForgotPassword";
//	public static final String MODULE_FEEDBACK = "Feedback";
//	public static final String MODULE_KNOWYOURACCOUNT = "KnowYourAcccount";
//	public static final String MODULE_LASTONLINEPAYMENTRECEIPT = "LastOnlinePaymentReciept";
//	public static final String MODULE_WHATSAPPSUBRIPTION = "WhatsAppSubscription";
//	public static final String MODULE_VIEWBILL = "ViewBill";
//	public static final String MODULE_PAYMENT = "Payment";
//	public static final String MODULE_THANKYOUBILLDESK = "ThankyouBillDesk";
//	public static final String MODULE_SERVICEREQUEST = "ServiceRequest";
//	public static final String MODULE_CONFIRMEMAIL = "ConfirmEmail";
//	
//	/**
//	 * 
//	 * @param loggingClient
//	 */
//	private UPPCLLogger(String loggingClient) {
//		this.loggingClient = loggingClient;
//	}
//
//	/**
//	 * @param Type of log i.e. Application
//	 * @return instance of UppclLogger
//	 */
//	public static UPPCLLogger getInstance(String loggingClient) {
//		UPPCLLogger uppclLogger = getInstance(null, loggingClient);
//		return uppclLogger;
//
//	}
//
//	/**
//	 * @param module
//	 * @param loggingClient
//	 * @return instance of UppclLogger
//	 */
//
//	public static UPPCLLogger getInstance(String module, String loggingClient) {
//		UPPCLLogger uppclLogger = new UPPCLLogger(loggingClient);
//		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//		uppclLogger.loggerModule = loggerContext.getLogger(module + "." + loggingClient);
//		return uppclLogger;
//	}
//
//	/**
//	 * Logs the msg whenever method start
//	 * 
//	 * @param String method name
//	 * @return void
//	 */
//	public void logMethodStart(String methodName) {
//		if ("enabled".equals(methodStartAndEndLogging)) {
//			loggerModule.info("START:" + loggingClient + " " + methodName);
//		}
//	}
//
//	/**
//	 * Logs the msg whenever method end
//	 * 
//	 * @param String method name
//	 * @return void
//	 */
//	public void logMethodEnd(String methodName) {
//		if ("enabled".equals(methodStartAndEndLogging))
//			loggerModule.info("END:" + loggingClient + " " + methodName);
//	}
//
//	/**
//	 * 
//	 * @param className
//	 * @param methodName
//	 * @param message
//	 * @return
//	 */
//	private static String appendString(String className, String methodName, String message) {
//		StringBuffer str = new StringBuffer();
//		str.append(className);
//		str.append(" ");
//		str.append(methodName);
//		str.append(":");
//		str.append(" ");
//		str.append(message);
//
//		return str.toString();
//	}
//
//	/**
//	 * 
//	 * @param logLevel
//	 * @param methodName
//	 * @param message
//	 */
//	public void log(String logLevel, String methodName, String message) {
//		try {
//			if (logLevel == null || message == null || methodName == null) {
//				throw new Exception();
//			}
//
//			// this will log messages depending on log type and log level
//
//			logMessage(loggerModule, logLevel, loggingClient, methodName, message);
//
//		} catch (Exception fe) {
//
//		}
//	}
//
//	/**
//	 * 
//	 * @param logger
//	 * @param logLevel
//	 * @param className
//	 * @param methodName
//	 * @param message
//	 */
//	public void logMessage(Logger logger, String logLevel, String className, String methodName, String message) {
//
//		String logMessage = appendString(className, methodName, message);
//
//		if (logLevel.equals(UPPCLLogger.LOGLEVEL_DEBUG)) {
//			logger.debug(logMessage);
//		} else if (logLevel.equals(UPPCLLogger.LOGLEVEL_INFO)) {
//			logger.info(logMessage);
//		} else if (logLevel.equals(UPPCLLogger.LOGLEVEL_WARN)) {
//			logger.warn(logMessage);
//		} else if (logLevel.equals(UPPCLLogger.LOGLEVEL_ERROR)) {
//			logger.error(logMessage);
//		} else {
//			throw new RuntimeException("Loglevel " + logLevel + " is unknown!!!");
//		}
//	}
//
//	/**
//	 * 
//	 * @param logLevel
//	 * @param methodName
//	 * @param message
//	 * @param throwable
//	 */
//	public void log(String logLevel, String methodName, String message, Throwable throwable) {
//		try {
//			if (logLevel == null || message == null || methodName == null) {
//				throw new Exception();
//			}
//
//			// this will log messages depending on log type and log level
//			/*
//			 * if (logLevel.equals(UPPCLLogger.LOGLEVEL_FATAL)) { logMessage(loggerModule,
//			 * logLevel, loggingClient, methodName, message, throwable); }
//			 */
//			logMessage(loggerModule, logLevel, loggingClient, methodName, message, throwable);
//		} catch (Exception fe) {
//
//		}
//	}
//
//	/**
//	 * 
//	 * @param logger
//	 * @param logLevel
//	 * @param className
//	 * @param methodName
//	 * @param message
//	 * @param throwable
//	 */
//	public void logMessage(Logger logger, String logLevel, String className, String methodName, String message,
//			Throwable throwable) {
//
//		String logMessage = "";
//
//		if (logLevel.equals(UPPCLLogger.LOGLEVEL_DEBUG)) {
//			logMessage = appendString(className, methodName, createMessage(message, throwable));
//			logger.debug(logMessage);
//		} else if (logLevel.equals(UPPCLLogger.LOGLEVEL_INFO)) {
//			logMessage = appendString(className, methodName, createMessage(message, throwable));
//			logger.info(logMessage);
//		} else if (logLevel.equals(UPPCLLogger.LOGLEVEL_WARN)) {
//			logMessage = appendString(className, methodName, createMessage(message, throwable));
//			logger.warn(logMessage);
//		} else if (logLevel.equals(UPPCLLogger.LOGLEVEL_ERROR)) {
//			logMessage = appendString(className, methodName, createMessage(message, throwable));
//			logger.error(logMessage);
//		} else {
//			logMessage = appendString(className, methodName, createMessage(message, throwable));
//			throw new RuntimeException("Loglevel " + logLevel + " is unknown!!!");
//		}
//	}
//
//	/**
//	 * 
//	 * @param message
//	 * @param throwable
//	 * @return
//	 */
//	public String createMessage(String message, Throwable throwable) {
//
//		StringBuilder sb = new StringBuilder();
//		sb.append(message);
//		sb.append("Exception Message=[");
//		sb.append(throwable.getMessage() + "], Stacktrace=[");
//		sb.append(throwable.getStackTrace().toString());
//		sb.append("\n]");
//		return sb.toString();
//
//	}
//
//	/**
//	 * 
//	 * @return <code>true</code> if info logging is enabled
//	 */
//	public boolean isDebugLoggingEnabled() {
//		return this.loggerModule.isDebugEnabled();
//
//	}
//
//	/**
//	 * 
//	 * @return <code>true</code> if info logging is enabled
//	 */
//	public boolean isInfoLoggingEnabled() {
//		return this.loggerModule.isInfoEnabled();
//	}
//
//	/**
//	 * 
//	 * @return <code>true</code> if error logging is enabled
//	 */
//	public boolean isErrorLoggingEnabled() {
//		return loggerModule.isErrorEnabled();
//	}
//
//}