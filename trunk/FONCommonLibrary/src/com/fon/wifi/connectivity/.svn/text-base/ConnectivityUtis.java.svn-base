package com.fon.wifi.connectivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.fon.wifi.blacklist.FonBlackListManager;
import com.fon.wifi.util.FONUtils;

public class ConnectivityUtis {

	private static String TAG = ConnectivityUtis.class.getCanonicalName();

	public static boolean isAlreadyConnected(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isConnected();
	}

	public static boolean isAnyPreferedNetworkAvailable(WifiManager wm) {
		Set<String> scanResultsKeys = new HashSet<String>();
		boolean found = false;

		List<WifiConfiguration> configuredNetworks = wm.getConfiguredNetworks();
		if (!configuredNetworks.isEmpty()) {
			List<ScanResult> scanResults = wm.getScanResults();
			if (!scanResults.isEmpty()) {
				for (ScanResult scanResult : scanResults) {
					scanResultsKeys.add(FONUtils.cleanSSID(scanResult.SSID));
				}

				for (WifiConfiguration wifiConf : configuredNetworks) {
					if (FONUtils.isSupportedNetwork(wifiConf.SSID, wifiConf.BSSID)) {
						continue;
					}
					if (wifiConf.SSID == null) {
						wm.removeNetwork(wifiConf.networkId);
					} else {
						found = found || scanResultsKeys.contains(FONUtils.cleanSSID(wifiConf.SSID));
					}
				}
			}
		}
		wm.saveConfiguration();
		return found;
	}

	public static String getFonNetworkSSID(Context context) {
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		String fonSSID = null;

		List<ScanResult> scanResults = wm.getScanResults();
		for (ScanResult scan : scanResults) {
			if (FONUtils.isSupportedNetwork(scan.SSID, scan.BSSID)
					&& !FonBlackListManager.isBlackListed(context, scan.SSID, scan.BSSID)) {
				fonSSID = scan.SSID;
				break;
			}
			if (FonBlackListManager.isBlackListed(context, scan.SSID, scan.BSSID)) {
				Log.d(TAG, "BLACK LISTED: " + scan.SSID + "-" + scan.BSSID);
			}
		}

		return fonSSID;
	}

	public static void connectToRouter(Context context, String ssid) {
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiConfiguration fonNetwork = new WifiConfiguration();
		fonNetwork.SSID = '"' + ssid + '"';
		fonNetwork.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		fonNetwork.status = WifiConfiguration.Status.ENABLED;
		fonNetwork.networkId = wm.addNetwork(fonNetwork);
		wm.saveConfiguration();
		wm.enableNetwork(fonNetwork.networkId, true);
	}

	public static boolean removeFONRouter(Context context, String ssid) {
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		List<WifiConfiguration> configuredNetworks = wm.getConfiguredNetworks();
		for (WifiConfiguration wifiConfiguration : configuredNetworks) {
			if (wifiConfiguration.SSID != null) {
				if (FONUtils.cleanSSID(wifiConfiguration.SSID).equals(FONUtils.cleanSSID(ssid))) {
					if (wm.removeNetwork(wifiConfiguration.networkId) && wm.saveConfiguration()) {
						Log.d(TAG, "FON ROUTER REMOVED " + ssid);
						return true;
					} else {
						Log.d(TAG, "FON ROUTER NOT REMOVED " + ssid);
					}
				}
			}
		}
		return false;
	}
}
