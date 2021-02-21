package com.svashishtha.eclipsetomaven.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public enum ApplicationConfig {
	INSTANCE(); 
	private Configuration config;
	ApplicationConfig(){
		try {
			config = new PropertiesConfiguration("application.properties");
		} catch (ConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}
	public String getValue(String key) {
		return config.getString(key);
	}
}