package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import mehdi.sakout.dynamicbox.DynamicBox;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.netlynxtech.advancedmonitor.adapters.MessageAdapter;
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
		lvMessage = (ListView) findViewById(R.id.lvMessages);
		box = new DynamicBox(MessagesActivity.this, lvMessage);
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
}
