package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mehdi.sakout.dynamicbox.DynamicBox;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import com.netlynxtech.advancedmonitor.adapters.ListRequestAdapter;
import com.netlynxtech.advancedmonitor.classes.Consts;
import com.netlynxtech.advancedmonitor.classes.DeviceRequest;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;
import com.securepreferences.SecurePreferences;

public class ReceivedMemberPermissionActivity extends ActionBarActivity {
	DynamicBox box;
	ListView lvReceivedMemberPermission;
	HashMap<String, String> roles = new HashMap<String, String>();
	ArrayList<DeviceRequest> data = new ArrayList<DeviceRequest>();
	SecurePreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_received_member_permission);
		sp = new SecurePreferences(ReceivedMemberPermissionActivity.this);
		Log.e("GCM", sp.getString(Consts.PREFERENCES_GCMID, ""));
		String[] rolesValue = getResources().getStringArray(R.array.roles_array_value);
		ArrayList<String> rolesValueArray = new ArrayList<String>(Arrays.asList(rolesValue));

		String[] rolesEntries = getResources().getStringArray(R.array.roles_array_text_string);
		ArrayList<String> rolesEntriesArray = new ArrayList<String>(Arrays.asList(rolesEntries));
		for (int i = 0; i < rolesEntriesArray.size(); i++) {
			roles.put(rolesValueArray.get(i), rolesEntriesArray.get(i));
		}
		lvReceivedMemberPermission = (ListView) findViewById(R.id.lvReceivedMemberPermission);
		box = new DynamicBox(ReceivedMemberPermissionActivity.this, lvReceivedMemberPermission);
		new listRequests().execute();
	}

	private class listRequests extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			box.showLoadingLayout();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ReceivedMemberPermissionActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (data != null && data.size() > 0) {
						ListRequestAdapter adapter = new ListRequestAdapter(ReceivedMemberPermissionActivity.this, data, roles);
						lvReceivedMemberPermission.setAdapter(adapter);
						box.hideAll();
					} else {
						box.setOtherExceptionMessage("You have no invites yet.");
						box.showExceptionLayout();
					}
				}
			});
		}

		@Override
		protected Void doInBackground(Void... params) {
			data = new WebRequestAPI(ReceivedMemberPermissionActivity.this).ListMemberRequests();
			return null;
		}

	}
}
