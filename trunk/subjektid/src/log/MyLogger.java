package log;

import org.apache.log4j.Logger;

public class MyLogger {

	static Logger logger = Logger.getLogger(MyLogger.class);

	public static void log(String method_name, String msg) {
		String log_row = "APP_ERROR: " + method_name
				+ ": " + msg;

		try {
			logger.info(log_row);
		} catch (Exception ex) {
			System.out.println("MyLogger.Log():" + ex.getMessage());
		}
	}

	public static void logMessage(String msg) {
		String log_row = "APP_MESSAGE:" + msg;

		try {
			logger.info(log_row);
		} catch (Exception ex) {
			System.out.println("MyLogger.Log():" + ex.getMessage());
		}

	}
}
