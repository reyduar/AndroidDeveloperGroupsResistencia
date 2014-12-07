package com.fon.wifi.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fon.wifi.R;
import com.fon.wifi.util.FONPreferences;
import com.fon.wifi.util.WISPrConstants;

abstract public class AccountActivity extends FonActivity implements OnCheckedChangeListener{

	public static final String EXTRA_ERROR = "EXTRA_ERROR";
	public static final String EXTRA_SHOW_ERROR = "EXTRA_SHOW_ERROR";
	private EditText editUser;
	private EditText editPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		editUser = (EditText)findViewById(R.id.edit_username);
		editPassword = (EditText)findViewById(R.id.edit_password);
		editUser.setText(FONPreferences.getUsername(this));
		editPassword.setText(FONPreferences.getPassword(this));
		CheckBox chkShowPassword = (CheckBox)findViewById(R.id.chk_show_password);
		chkShowPassword.setOnCheckedChangeListener(this);
		
		if(getIntent().getBooleanExtra(EXTRA_SHOW_ERROR, false)){
			showError();
		}
	}

	private void showError() {
		
		final Dialog d = new Dialog(this, R.style.AlertDialog);
		d.setContentView(R.layout.alert_dialog);
		TextView title = (TextView) d.findViewById(R.id.alert_dialog_title);
		title.setText(R.string.notif_error);
		TextView text = (TextView) d.findViewById(R.id.alert_dialog_msg);
		int error = getIntent().getIntExtra(EXTRA_ERROR, WISPrConstants.UNKNOWN_ERROR);
		switch (error) {
		case WISPrConstants.NOT_AUTHORIZED:
			text.setText(R.string.notif_error_100);
			break;
		case WISPrConstants.NO_SUCH_USER:
			text.setText(R.string.notif_error_100);
			break;
		case WISPrConstants.USER_NOT_FOUND:
			text.setText(R.string.notif_no_credentials_text);
			break;
		case WISPrConstants.USER_IN_BLACK_LIST:
			text.setText(R.string.notif_error_user_in_black_list);
			break;
		case WISPrConstants.NOT_ENOUGH_CREDIT:
			text.setText(R.string.notif_error_not_enough_credit);
			break;
		case WISPrConstants.WISPR_RESPONSE_CODE_LOGIN_FAILED:
			text.setText(R.string.notif_error_100);
			break;
		case WISPrConstants.CREDENTIALS_ERROR:
			text.setText(R.string.notif_no_credentials_text);
			break;
		default:
			text.setText(R.string.notif_error_unknown);
			break;
		}

		Button btn = (Button) d.findViewById(R.id.alert_dialog_btn_ok);
		btn.setOnClickListener(new  OnClickListener() {
			public void onClick(View arg0) {
				d.dismiss();
			}
		});
		d.show();
	}

	public void save(View view){
		FONPreferences.saveUsername(this, editUser.getText().toString());
		FONPreferences.savePassword(this, editPassword.getText().toString());
		finish();
	}

	public void onCheckedChanged(CompoundButton checkBox, boolean isChecked) {
		if(isChecked){
			editPassword.setTransformationMethod 
			(null); 
			editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
		}
		else{
			editPassword.setTransformationMethod 
			(PasswordTransformationMethod.getInstance()); 
			editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}
}