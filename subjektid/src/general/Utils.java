package general;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import log.MyLogger;

public class Utils {

	public static String generateMD5(String passw) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			BigInteger bi = new BigInteger(1, m.digest(passw.getBytes()));
			return String.format("%1$032x", bi);
		} catch (NoSuchAlgorithmException e) {
			MyLogger.log("SubjectsDAO.generateMD5(): ", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date parseDate(String date) {
		String[] formatStrings = {"d.M.y", "d/M/y", "d-M-y"};
		
		for (String formatString : formatStrings) {
			try {
				return new SimpleDateFormat(formatString).parse(date);
			} catch (ParseException e) { }
		}
		return null;
	}
	
}
