package com.ovm.uy.crd_import_script.view.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
 	public final static String CONFIGURATION_HOST = "Host";
 	public final static String CONFIGURATION_USER = "User";
	public final static String CONFIGURATION_PASSWORD = "Password";
	public final static String CONFIGURATION_TABLE = "Table";
 
	private final static String CONFIGURATION_FILE_NAME = "crd_import.properties";

	private static Configuration instance;
	private Properties prop;

	private Configuration() {
		prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(CONFIGURATION_FILE_NAME);

			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			try {
				OutputStream outputStream = new FileOutputStream(
						CONFIGURATION_FILE_NAME);
 				prop.put(CONFIGURATION_HOST, "localhost:5433/Ale");
 				prop.put(CONFIGURATION_USER, "Alevilla");
				prop.put(CONFIGURATION_PASSWORD, "Ha-23/n,");
				prop.put(CONFIGURATION_TABLE, "crd_2017");
 
				prop.store(outputStream, null);

				input = new FileInputStream(CONFIGURATION_FILE_NAME);

				// load a properties file
				prop.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	public String getProperty(String sProperty) {
		return prop.getProperty(sProperty);
	}

	public void putProperty(String sPropertyKey, String sPropertyValue) {
		OutputStream output = null;

		try {
			output = new FileOutputStream(CONFIGURATION_FILE_NAME);
			prop.put(sPropertyKey, sPropertyValue);

			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}
}
