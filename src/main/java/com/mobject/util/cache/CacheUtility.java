package com.mobject.util.cache;

import java.io.InputStream;
import java.util.Properties;


/**
 * Class is responsible to reading the cache-config.properties file and return the config params
 * form cache-config.properties from the class path.
 * @author sougata
 *
 */
public class CacheUtility {
	
	private static final String CONFIG_FILE = "cache-config.properties";
	private static String DEFAULT_PROPERTY = "100"; 
	
	/**
	 * =Get the Property Value for the passed key
	 * @param key
	 * @return
	 */
	public static String getValue(String key)
	{
		String value  = null;
		try{
			InputStream is  = CacheUtility.class.getResourceAsStream(CONFIG_FILE);
			Properties prop = new Properties();  
			prop.load(is);
            value = prop.getProperty(key);
            
            
		}catch(Exception ex){
			//Setting the Value to the Default property 
			value = DEFAULT_PROPERTY;
		}
		
		return value;
		
	}

}
