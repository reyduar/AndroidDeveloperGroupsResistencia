package com.fon.wifi.notification;

import android.app.Notification;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationUtils {
	static private Integer notification_content_color = android.R.color.black;
	static private float notification_content_size = 9;
	static private Integer notification_title_color = android.R.color.black;
	static private float notification_title_size = 11;
	static private boolean loaded = false;
	static private final String TEXT_TITLE = "TITLE";
	static private final String TEXT_CONTENT = "CONTENT";

	public static Integer getNotificationContentColor(Context context) {
		if(!loaded)
			loadNotificationAppearence(context);
		return notification_content_color;
	}

	public static float getNotification_content_size(Context context) {
		if(!loaded)
			loadNotificationAppearence(context);
		return notification_content_size;
	}

	public static Integer getNotificationTitleColor(Context context) {
		if(!loaded)
			loadNotificationAppearence(context);
		return notification_title_color;
	}

	public static float getNotification_title_size(Context context) {
		if(!loaded)
			loadNotificationAppearence(context);
		return notification_title_size;
	}

	static private void getAppearence(Context context, ViewGroup gp) {
		final int count = gp.getChildCount();
		for (int i = 0; i < count; ++i) {
			if (gp.getChildAt(i) instanceof TextView) {
				TextView text = (TextView) gp.getChildAt(i);
				String szText = text.getText().toString();
				if (TEXT_CONTENT.equals(szText)) {
					notification_content_color = text.getTextColors().getDefaultColor();
					notification_content_size = text.getTextSize();
					DisplayMetrics metrics = new DisplayMetrics();
					WindowManager systemWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
					systemWM.getDefaultDisplay().getMetrics(metrics);
					notification_content_size /= metrics.scaledDensity;
				}
				if (TEXT_TITLE.equals(szText)) {
					notification_title_color = text.getTextColors().getDefaultColor();
					notification_title_size = text.getTextSize();
					DisplayMetrics metrics = new DisplayMetrics();
					WindowManager systemWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
					systemWM.getDefaultDisplay().getMetrics(metrics);
					notification_title_size /= metrics.scaledDensity;
				}
			} else if (gp.getChildAt(i) instanceof ViewGroup)
				getAppearence(context, (ViewGroup) gp.getChildAt(i));
		}
	}

	static private void loadNotificationAppearence(Context context) {
		if (!loaded) {
			try {
				Notification ntf = new Notification();
				ntf.setLatestEventInfo(context, TEXT_TITLE, TEXT_CONTENT, null);
				LinearLayout group = new LinearLayout(context);
				ViewGroup event = (ViewGroup) ntf.contentView.apply(context, group);
				getAppearence(context, event);
				group.removeAllViews();
			} catch (Exception e) {
				loaded = true;
			}
		}
	}
}
