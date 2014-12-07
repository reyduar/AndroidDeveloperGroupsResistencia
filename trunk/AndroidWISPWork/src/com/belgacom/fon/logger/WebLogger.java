package com.belgacom.fon.logger;

public interface WebLogger {
	public static final String CONNECTED = "CONNECTED";

	public static final String CONNECTION_CHECK_URL = "http://ubuntuone.com/p/F6b/";

	public LoggerResult login(String user, String password);
}
