/**
 * 
 */
package com.davis.practice4android.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * @author skyworth
 * @date 2013-10-11 下午5:44:28
 * @Description 
 */
public final class PropertiesUtil {

	private PropertiesUtil(){
		
	}
	
	public static String get(String propertiesFile,String key){
		 
		Reader reader = null;
		String value = null;
		try {
			reader = new InputStreamReader(new BufferedInputStream(
					new FileInputStream(propertiesFile)),
					EncodeType.getEncode(propertiesFile));
			Properties properties = new Properties();
			properties.load(reader);
			value = properties.getProperty(key);
		} catch (Exception e) {

		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public static void put(String propertiesFile,String keyName,String keyValue){
		OutputStream os = null;
        try{  
        	Reader reader = new InputStreamReader(new BufferedInputStream(
					new FileInputStream(propertiesFile)),
					EncodeType.getEncode(propertiesFile));
            Properties properties = new Properties();  
            properties.load(reader);
            reader.close();
            os = new FileOutputStream(propertiesFile);
            properties.setProperty(keyName, keyValue);  
            properties.store(os, "Update '" + keyName + "' value");  
        }catch(Exception e){
            
        }finally{  
        	if(null!=os){
        		try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        } 
	}
	
	public static void put(String propertiesFile,String keyName,int keyValue){
		OutputStream os = null;
        try{  
        	Reader reader = new InputStreamReader(new BufferedInputStream(
					new FileInputStream(propertiesFile)),
					EncodeType.getEncode(propertiesFile));
            Properties properties = new Properties();  
            properties.load(reader);
            reader.close();
            os = new FileOutputStream(propertiesFile);
            properties.setProperty(keyName, ""+keyValue);  
            properties.store(os, "Update '" + keyName + "' value");  
        }catch(Exception e){
            
        }finally{  
        	if(null!=os){
        		try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        } 
	}
}
