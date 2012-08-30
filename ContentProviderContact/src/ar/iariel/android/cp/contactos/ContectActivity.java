package ar.iariel.android.cp.contactos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ContectActivity extends Activity implements OnClickListener {
	private static final int REQUEST_CHOOSE_PHONE = 1;
	private TextView vPhone;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		vPhone = (TextView) findViewById(R.id.TextView01);
		findViewById(R.id.Button01).setOnClickListener(this);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode == REQUEST_CHOOSE_PHONE)
				&& (resultCode == Activity.RESULT_OK)) {
			try {
				String phone = data.getStringExtra("phone");
				vPhone.setText(phone);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onClick(View v) {
		Intent intent = new Intent("org.francho.CHOOSE_PHONE");
		startActivityForResult(intent, REQUEST_CHOOSE_PHONE);
	}

}
