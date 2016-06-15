package com.ckx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ckx.common.ESConnection;

public class PropertiesUtils {
	public static Properties loadProperties(String path) throws IOException {
		Properties props = new Properties();
		InputStream is = ESConnection.class.getResourceAsStream(path);
		props.load(is);
		return props;
	}
}
