package com.robomotic.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.codec.digest.DigestUtils;

import com.robomotic.config.ConfigLoader;

public class Util {

	/**
	 * Utility method to close a {@link Closeable} or {@link EntityManager}
	 * object in a null-safe and exception-safe manner.
	 *
	 * @param obj The object to close.
	 */
	public static void closeObject(Object obj) {
		if(obj != null) {
			if(obj instanceof Closeable) {
				try {
					((Closeable)obj).close();
				} catch(Exception e){}
			}
			else if(obj instanceof EntityManager) {
				try {
					((EntityManager)obj).close();
				} catch(Exception e){}
			}
		}
	}

	/**
	 * Utility method to encode users passwords.
	 *
	 * @param plainPassword The pplain text password.
	 * @return The encoded password.
	 */
	public static String encodeUserPassword(String plainPassword) {
		return DigestUtils.md5Hex(plainPassword.getBytes());
	}

	/**
	 * Utility method to retrieve a configuration parameter as a
	 * <code>{@link List}&lt;String&gt;</code>. If the specified
	 * parameter is a string, this method returns a List containing
	 * only one item. If the parameter doesn't exists, it returns
	 * an empty list.
	 *
	 * @param paramName The name of the configuration parameter to read.
	 * @return A <code>{@link List}&lt;String&gt;</code> with the values
	 * of the parameter as defined in the configuration file.
	 */
	public static List<String> getConfigurationParamAsList(String paramName) {
		List<String> ret = new LinkedList<String>();
		Object paramValue = ConfigLoader.getInstance().get(paramName);
		if(paramValue instanceof String) {
			ret.add((String)paramValue);
		}
		else if(paramValue instanceof List) {
			ret = (List<String>)paramValue;
		}
		return ret;
	}

	/**
	 * Utility method to retrieve a configuration parameter as a
	 * string. This method is useful when trying to read a parameter
	 * value that contains a string with commas.
	 *
	 * @param paramName The name of the configuration parameter to read.
	 * @return The value of the parameter as defined in the configuration
	 * file.
	 */
	public static String getConfigurationParamAsString(String paramName) {
		Object paramValue = ConfigLoader.getInstance().get(paramName);
		if(paramValue == null) {
			return null;
		} else if(paramValue instanceof String) {
			return (String) paramValue;
		} else if(paramValue instanceof List) {
			StringBuffer ret = new StringBuffer();
			boolean first = true;
			for(Object paramPart : (List) paramValue) {
				if(!first)
					ret.append(", ");
				ret.append(paramPart.toString());
				first = false;
			}
			return ret.toString();
		}

		return paramValue.toString();
	}

	/**
	 * Utility method to test if a string is empty (null)
	 * or white space only.
	 *
	 * @param test The string to test for.
	 * @return <code>true</code> if the string is empty or
	 * white space only, <code>false</code> otherwise.
	 */
	public static boolean isEmptyOrWhiteSpaceOnly(String test) {
		return test == null || test.trim().length() == 0;
	}

	/**
	 * Utility method to read all lines in a file.
	 *
	 * @param file The {@link File} object that will be read.
	 * @return A <code>{@link List}&lt;{@link String}&gt;</code>
	 * with all the lines in the specified file. If an error
	 * occurred, an empty list is returned.
	 */
	public static List<String> readFileLines(File file) {
		List<String> ret = new LinkedList<String>();
		if(!file.exists() || file.isDirectory()) {
			return ret;
		}

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is = new FileInputStream(file);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			String line = null;
			while((line = br.readLine()) != null) {
				ret.add(line);
			}
		} catch(Exception e){
			ret = new LinkedList<String>();
		} finally {
			Util.closeObject(br); br = null;
			Util.closeObject(isr); isr = null;
			Util.closeObject(is); is = null;
		}
		return ret;
	}
}
