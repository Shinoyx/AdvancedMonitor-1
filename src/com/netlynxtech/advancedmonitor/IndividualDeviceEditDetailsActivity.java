package com.netlynxtech.advancedmonitor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class IndividualDeviceEditDetailsActivity extends ActionBarActivity {
	EditText etInput1, etInput2, etOutput1, etOutput2, etDescriptions;
	Button bUpdate;
	String deviceId = "", deviceDescription = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_edit_details);
		Intent i = getIntent();
		if (i.hasExtra("deviceId")) {
			deviceId = i.getStringExtra("deviceId");
			deviceDescription = i.getStringExtra("deviceDescription");
		} else {
			finish();
		}
		getSupportActionBar().setTitle(deviceDescription);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		etInput1 = (EditText) findViewById(R.id.etInput1);
		if (i.hasExtra("input1")) {
			etInput1.setText(i.getStringExtra("input1"));
		} else {
			etInput1.setEnabled(false);
		}
		etInput2 = (EditText) findViewById(R.id.etInput2);
		if (i.hasExtra("input2")) {
			etInput2.setText(i.getStringExtra("input2"));
		} else {
			etInput2.setEnabled(false);
		}
		etOutput1 = (EditText) findViewById(R.id.etOutput1);
		if (i.hasExtra("output1")) {
			etOutput1.setText(i.getStringExtra("output1"));
		} else {
			etOutput1.setEnabled(false);
		}
		etOutput2 = (EditText) findViewById(R.id.etOutput2);
		if (i.hasExtra("output2")) {
			etOutput2.setText(i.getStringExtra("output2"));
		} else {
			etOutput2.setEnabled(false);
		}
		etDescriptions = (EditText) findViewById(R.id.etDescriptions);
		etDescriptions.setText(deviceDescription);
		bUpdate = (Button) findViewById(R.id.bUpdate);
		bUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new updateDescriptions().execute(etDescriptions.getText().toString(), etInput1.getText().toString(), etInput2.getText().toString(), etOutput1.getText().toString(), etOutput2.getText()
						.toString());
			}
		});
	}

	private class updateDescriptions extends AsyncTask<String, Void, Void> {
		String data = "";

		@Override
		protected Void doInBackground(String... params) {
			// data = new WebRequestAPI(IndividualDeviceEditDetailsActivity.this).UpdateDescriptions("", params[0].toString(), params[1].toString(), params[2].toString(), params[3].toString(),
			// params[4].toString());
			data = new WebRequestAPI(IndividualDeviceEditDetailsActivity.this).UpdateDescriptions(deviceId, params[0].toString(), params[1].toString(), params[2].toString(), params[3].toString(),
					params[4].toString());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			IndividualDeviceEditDetailsActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (data.startsWith("success|")) {
						Toast.makeText(IndividualDeviceEditDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(IndividualDeviceEditDetailsActivity.this, data, Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}
}
