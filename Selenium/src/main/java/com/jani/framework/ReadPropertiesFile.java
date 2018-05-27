package com.jani.framework;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;



public class ReadPropertiesFile {

	
	
	static Properties PropDetails = new Properties();
	InputStream input = null;
	OutputStream output = null;
	

	public static String FileRead(String FileName, String ObName)
	{
		try {
			 		
			 InputStream FilePath =  ReadPropertiesFile.class.getResourceAsStream("/propertyfiles/"+FileName);
				 
			// load a properties file
			PropDetails.load(FilePath);
			
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		String ObValue = PropDetails.getProperty(ObName);
		return ObValue;
	}
