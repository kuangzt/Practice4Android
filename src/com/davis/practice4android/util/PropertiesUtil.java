/**
 * 
 */
package com.davis.practice4android.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

public final class PropertiesUtil {

	private static PropertiesUtil instance = new PropertiesUtil();

	private PropertiesUtil() {

	}
	
	public static PropertiesUtil getInstance(){
		return instance;
	}
	
	private Hashtable<String, Properties> propertiesTable = new Hashtable<String, Properties>();
	private Hashtable<String,Long> fileLastModifiedTable = new Hashtable<String,Long>();
	
	public synchronized Object getProperty(String propertiesFilename,String propertyName,String defaultValue){
		File propertyFile = new File(propertiesFilename);
		long newlastModified = propertyFile.lastModified();
		if(newlastModified>0){
			long lastModified = 0;
			if(fileLastModifiedTable.containsKey(propertiesFilename)){
				lastModified = fileLastModifiedTable.get(propertiesFilename);
			}
			Properties properties = null;
			if(newlastModified>lastModified){
				properties = new Properties();
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(propertyFile);
					properties.load(fis);
					fileLastModifiedTable.put(propertiesFilename, newlastModified);
					propertiesTable.put(propertiesFilename, properties);
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				}finally{
					if(null!=fis){
						try {
							fis.close();
						} catch (IOException e) {
						}
					}
				}
				
			}else{
				properties = propertiesTable.get(propertiesFilename);
			}
			return properties.getProperty(propertyName, defaultValue);
		}else{
			try {
				propertyFile.createNewFile();
			} catch (IOException e) {
			}
		}
		return defaultValue;
	}
	
	public synchronized void setProperties(String propertiesFilename,String propertyName,String propertyValue){
		File propertyFile = new File(propertiesFilename);
		long newlastModified = propertyFile.lastModified();
		Properties properties = null;
		if(newlastModified>0){
			long lastModified = 0;
			if(fileLastModifiedTable.containsKey(propertiesFilename)){
				lastModified = fileLastModifiedTable.get(propertiesFilename);
			}
			
			if(newlastModified>lastModified){
				properties = new Properties();
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(propertyFile);
					properties.load(fis);
					fileLastModifiedTable.put(propertiesFilename, newlastModified);
					propertiesTable.put(propertiesFilename, properties);
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				}finally{
					if(null!=fis){
						try {
							fis.close();
						} catch (IOException e) {
						}
					}
				}
				
			}else{
				properties = propertiesTable.get(propertiesFilename);
			}
		}else{
			try {
				propertyFile.createNewFile();
				properties = new Properties();
			} catch (IOException e) {
				return;
			}
		}
		properties.setProperty(propertyName, propertyValue);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propertyFile);
			properties.store(fos, "update "+propertyName+"="+propertyValue);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}finally{
			if(null!=fos){
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		newlastModified = propertyFile.lastModified();
		fileLastModifiedTable.put(propertiesFilename, newlastModified);
		propertiesTable.put(propertiesFilename, properties);
	}
	

}

	

	

	

