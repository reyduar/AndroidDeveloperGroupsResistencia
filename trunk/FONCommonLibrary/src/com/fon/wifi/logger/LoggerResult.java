package com.fon.wifi.logger;

import static com.fon.wifi.util.WISPrConstants.*;

public class LoggerResult {
	protected int result;

	protected String logOffUrl;

	public LoggerResult(int result, String logOffUrl) {
		this.result = result;
		this.logOffUrl = logOffUrl;
	}

	public int getResult() {
		return result;
	}

	public String getLogOffUrl() {
		return logOffUrl;
	}

	public boolean hasSucceded() {
		switch (result){
			case WISPR_RESPONSE_CODE_LOGIN_SUCCEEDED:
			case ALREADY_CONNECTED:
				return true;
			default:
				return false;
		}
	}

	public boolean hasFailed() {

		switch (result){
			case WISPR_RESPONSE_CODE_INTERNAL_ERROR:
			case WISPR_RESPONSE_CODE_LOGIN_FAILED:
			case WISPR_NOT_PRESENT:
				return true;
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "{result: " + result + ", logOffUrl:" + logOffUrl + "}";
	}
}
