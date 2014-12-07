package com.fon.wifi.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fon.wifi.R;
import com.fon.wifi.util.IntentActionsFactory;

abstract public class HelpListActivity extends FonActivity implements OnItemClickListener{
	private ArrayAdapter<String> listAdapter;
	private String HTML_PREFIX = "file:///android_asset/html/";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_list);
		TextView desc = (TextView)findViewById(R.id.help_text); 
		desc.setMovementMethod(LinkMovementMethod.getInstance());
		ListView questionList = (ListView)findViewById(R.id.help_list);
		listAdapter = new ArrayAdapter<String>(
				this, 
				R.layout.help_list_item, 
				R.id.question_title, 
				getResources().getStringArray(R.array.help_question_list));
		questionList.setAdapter(listAdapter);
		questionList.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		
		String url = HTML_PREFIX + getString(R.string.html_language_folder)+"/"+pos+".html";
		Intent i = new Intent(IntentActionsFactory.getWebActivityAction());
		i.putExtra(WebActivity.EXTRA_URL, url);
		i.putExtra(WebActivity.EXTRA_TITLE, getString(R.string.help_title));
		i.putExtra(WebActivity.EXTRA_IS_HELP, true);
		startActivity(i);
		finish();
	}

}