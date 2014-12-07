package com.belgacom.fon.logger;

import java.io.IOException;
import java.util.Map;

import android.util.Log;

import com.belgacom.fon.util.FONUtils;
import com.belgacom.fon.util.HttpUtils;
import static com.belgacom.fon.util.WISPrConstants.*;

public abstract class SimpleHTTPLogger extends HTTPLogger {
	protected static String TAG = SimpleHTTPLogger.class.getName();

	protected SimpleHTTPLogger(String targetUrl) {
		this.targetURL = targetUrl;
	}

	@Override
	public LoggerResult login(String user, String password) {
		int res = WISPR_RESPONSE_CODE_INTERNAL_ERROR;
		try {
			if (!FONUtils.isConnected()) {
				if (FONUtils.isSafeUrl(targetURL)) {
					Map<String, String> postParams = getPostParameters(user, password);
					Log.d(TAG, "Posting username & password");
					HttpUtils.getUrlByPost(targetURL, postParams);

					Log.d(TAG, "Verifying if now we have connection");

					if (FONUtils.isConnected()) {
						res = WISPR_RESPONSE_CODE_LOGIN_SUCCEEDED;
					}
				} else {
					Log.e(TAG, "Not safe URL:" + targetURL);
				}
			} else {
				res = ALREADY_CONNECTED;
			}
		} catch (IOException e) {
			Log.e(TAG, "Error trying to log", e);
			res = WISPR_RESPONSE_CODE_INTERNAL_ERROR;
		}

		return new LoggerResult(res, getLogOffUrl());
	}

	abstract protected Map<String, String> getPostParameters(String user, String password);
}
