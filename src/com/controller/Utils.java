package com.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;


public class Utils {

	static String TOMCAT_HOME = System.getProperty("catalina.home");
	private static final String RESOURCE_FILE_PATH = TOMCAT_HOME+"/conf";
	private static Object rlock = new Object();
	private static HashMap<String, ResourceBundle> resourceMap = new HashMap<String, ResourceBundle>();
	
	public static ResourceBundle getBundle(String file) {
		ResourceBundle bundle= null;
		synchronized (rlock) {
			try {
				if(resourceMap.containsKey(file))
					bundle = resourceMap.get(file);
				else {
					System.out.println("Checking for "+RESOURCE_FILE_PATH+"/"+file+".properties");
					bundle = new PropertyResourceBundle(new FileInputStream(RESOURCE_FILE_PATH+"/"+file+".properties"));
					resourceMap.put(file, bundle);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return bundle;
	}
	
	public static String getValue(String contextpath, String key) throws Exception {
		ResourceBundle bundle = getBundle(contextpath);			
		try {
			String value = bundle.getString(key);
			System.out.println("Key >>>"+key+"\tValue >>>"+value);
			return value;
			
		}catch(Exception e) {
			System.out.println(e);
			throw new Exception();
		}
		
	}
	
	
	@SuppressWarnings("deprecation")
	public static String getResponseFromURL(String url, String proxyHost,
			int proxyPort, int conTimeout, int timeout) throws Exception{

		System.out.println("in here");
		HostConfiguration config = null;
		HttpClient client = new HttpClient();
		client.setConnectionTimeout(conTimeout * 1000);
		client.setTimeout(timeout * 1000);
		String subStatus = null;
		int httpStatusCode = -1;
		HttpMethod method = new GetMethod(url);
		config = method.getHostConfiguration();
		if (null != proxyHost && 0 != proxyPort) {
			System.out.println("setting proxy host : " + proxyHost);
			System.out.println("setting proxy port : " + proxyPort);
			config.setProxy(proxyHost, proxyPort);
		}
		client.setHostConfiguration(config);

		try {
			httpStatusCode = client.executeMethod(method);
			System.out.println("httpStatusCode : " + httpStatusCode);
			if (httpStatusCode != HttpStatus.SC_OK) {
				String msg = "Method failed: " + method.getStatusLine();
				System.out.println(msg);
			}
			subStatus = method.getResponseBodyAsString();
			if (subStatus != null)
				subStatus = subStatus.trim();
			//System.out.println("url response : " + subStatus);
			return subStatus;
		} catch (HttpException hte) {
			hte.printStackTrace();
			throw new Exception();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new Exception();
		} finally {
			try{
				method.releaseConnection();
			}catch (Exception e) {
			}
		}

	}
	
	
	@SuppressWarnings("deprecation")
	public static InputStream getResponseFromURLasStream(String url, String proxyHost,
			int proxyPort, int conTimeout, int timeout) throws Exception{

		System.out.println("in here");
		HostConfiguration config = null;
		HttpClient client = new HttpClient();
		client.setConnectionTimeout(conTimeout * 1000);
		client.setTimeout(timeout * 1000);
		InputStream responseStream = null;
		int httpStatusCode = -1;
		HttpMethod method = new GetMethod(url);
		config = method.getHostConfiguration();
		if (null != proxyHost && 0 != proxyPort) {
			System.out.println("setting proxy host : " + proxyHost);
			System.out.println("setting proxy port : " + proxyPort);
			config.setProxy(proxyHost, proxyPort);
		}
		client.setHostConfiguration(config);

		try {
			httpStatusCode = client.executeMethod(method);
			System.out.println("httpStatusCode : " + httpStatusCode);
			if (httpStatusCode != HttpStatus.SC_OK) {
				String msg = "Method failed: " + method.getStatusLine();
				System.out.println(msg);
			}
			responseStream = method.getResponseBodyAsStream();
			
			//System.out.println("url response : " + subStatus);
			return responseStream;
		} catch (HttpException hte) {
			hte.printStackTrace();
			throw new Exception();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new Exception();
		} finally {
			try{
				method.releaseConnection();
			}catch (Exception e) {
			}
		}

	}
	
	public static <T> T readObject(Class<T> objType, InputStream is) throws Exception
    {
        JAXBContext context;
        try
        {
            context = JAXBContext.newInstance(objType);
            Unmarshaller u = context.createUnmarshaller();
            T obj = (T) u.unmarshal(is);
            return obj;
        }
        catch (JAXBException e)
        {
            throw new Exception(e);
        }
    }


	
	 public static String objectToJSON( Object o) throws JsonGenerationException, JsonMappingException, IOException{
	    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	    	String json = ow.writeValueAsString(o);
	    	//System.out.println("JSON:\n"+json);
	    	return json;
	    }
}
