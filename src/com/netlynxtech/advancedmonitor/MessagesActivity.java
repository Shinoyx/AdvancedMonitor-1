package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import mehdi.sakout.dynamicbox.DynamicBox;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.netlynxtech.advancedmonitor.adapters.MessageAdapter;
import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.Message;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class MessagesActivity extends ActionBarActivity {
	ArrayList<Message> data;
	DynamicBox box;
	ListView lvMessage;
	getMessages mTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		lvMessage = (ListView) findViewById(R.id.lvMessages);
		box = new DynamicBox(MessagesActivity.this, lvMessage);
		lvMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Message m = data.get(position);
				startActivity(new Intent(MessagesActivity.this, SubMessagesActivity.class).putExtra("message", m));
			}
		});
		mTask = null;
		mTask = new getMessages();
		mTask.execute();
	}

	private class getMessages extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			box.showLoadingLayout();
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			MessagesActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (data != null && data.size() > 0) {
						MessageAdapter adapter = new MessageAdapter(MessagesActivity.this, data);
						lvMessage.setAdapter(adapter);
						box.hideAll();
					} else {
						box.setOtherExceptionMessage("No messages");
						box.showExceptionLayout();
					}
				}
			});
		}

		@Override
		protected Void doInBackground(Void... params) {
			data = new WebRequestAPI(MessagesActivity.this).GetMessages();
			return null;
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
