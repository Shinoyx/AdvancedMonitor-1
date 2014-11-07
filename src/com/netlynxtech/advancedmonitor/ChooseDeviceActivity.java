package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.netlynxtech.advancedmonitor.classes.Consts;
import com.netlynxtech.advancedmonitor.classes.TCPClass;

public class ChooseDeviceActivity extends ActionBarActivity {
	Spinner sDeviceList, sWifi;
	WifiManager wifiManager;
	ArrayList<String> data;
	ArrayAdapter<String> adapter;
	EditText etWifiPassword;
	Button bConnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = new ArrayList<String>();
		wifiManager = (WifiManager) ChooseDeviceActivity.this.getSystemService(Context.WIFI_SERVICE);
		registerReceiver(wifiBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager.startScan();
		sDeviceList = (Spinner) findViewById(R.id.sDeviceList);
		sWifi = (Spinner) findViewById(R.id.sWifi);
		etWifiPassword = (EditText) findViewById(R.id.etWifiPassword);
		bConnect = (Button) findViewById(R.id.bConnect);
		adapter = new ArrayAdapter<String>(ChooseDeviceActivity.this, android.R.layout.simple_spinner_item, data);
		sDeviceList.setAdapter(adapter);
		sWifi.setAdapter(adapter);
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mWifi.isConnected()) {
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			Log.d("SSID", wifiInfo.getSSID());
			sDeviceList.setSelection(adapter.getPosition(wifiInfo.getSSID()));
		}
		bConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bConnect.setEnabled(false);
				String wifiToConnectTo = sWifi.getSelectedItem().toString();
				String wifiToConnectToPassword = etWifiPassword.getText().toString().trim();

				if (wifiToConnectToPassword.length() > 0) {
					new startConnecting().execute(wifiToConnectTo, wifiToConnectToPassword);
				} else {
					Toast.makeText(ChooseDeviceActivity.this, "Password field is blank", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private BroadcastReceiver wifiBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			Log.e("RECEIVED", "RECEIVED");
			final List<ScanResult> results = wifiManager.getScanResults();
			for (ScanResult n : results) {
				data.add(n.SSID);
				adapter.notifyDataSetChanged();
			}
			unregisterReceiver(this);
		}
	};

	@Override
	protected void onPause() {
		try {
			unregisterReceiver(wifiBroadcastReceiver);
		} catch (Exception e) {
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(wifiBroadcastReceiver);
		} catch (Exception e) {
		}
		super.onDestroy();
	}

	private class startConnecting extends AsyncTask<String, String, Void> {
		private ProgressDialog dialog;
		boolean success = false;
		String deviceId = "", progressMessage = "";

		@Override
		protected void onPreExecute() {
			ChooseDeviceActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					dialog = new ProgressDialog(ChooseDeviceActivity.this);
					dialog.setMessage("Starting connection.. Please wait..");
					dialog.setCancelable(false);
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
				}
			});
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			dialog.setMessage(values[0]);
		}

		@Override
		protected Void doInBackground(String... params) {
			final TCPClass tcp = new TCPClass(ChooseDeviceActivity.this, Consts.DEVICE_SOFT_ACCESS_IP, Consts.DEVICE_SOFT_ACCESS_PORT, new TCPClass.OnMessageReceived() {
				@Override
				public void messageReceived(String message) {
					Log.e("messageReceived", message);
					if (message.startsWith("^B|")) {
						// ^B|[Version]|[Device ID]|[User ID]|[CheckSum]~
						String[] split = message.split("\\|");
						deviceId = split[2].toString().trim();
						progressMessage += "1/3 Device ID found..\n";
					} else if (message.startsWith("^x|1")) {
						progressMessage += "2/3 Configuration found..\n";
					} else if (message.startsWith("^x|2")) {
						if (!message.equals(Consts.X_CONFIGURE_USERID_NULL)) {
							Pattern p = Pattern.compile("\\^x\\|2\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)~");
							Matcher m = p.matcher(message);
							if (m.find()) {
								success = true;
								Log.e("TASK", "Ending task");
								progressMessage += "3/3 Configuration done.. [" + m.group(1) + "]";
							}
						} else {
							progressMessage += "3/3 [FAILED] UserID not found.. Device not returning registered infomation.";
						}
					}
					publishProgress(progressMessage);
				}
			});

			TCPClass.sendDataWithString("^B~");
			SystemClock.sleep(3000);
			TCPClass.sendDataWithString("^X|1|81396537|ZZ~");
			SystemClock.sleep(3000);
			// TCPClass.sendDataWithString(String.format(Consts.X_CONFIGURE_TWO_WIFISERVER_TODEVICE, params[0], params[1], "192.168.10.8", "5090", "192.168.10.8", "5090", "ZZ"));
			TCPClass.sendDataWithString(String.format(Consts.X_CONFIGURE_TWO_WIFISERVER_TODEVICE, "YEN", params[1], "192.168.10.8", "5090", "192.168.10.8", "5090", "ZZ"));
			SystemClock.sleep(3000);
			if (success) {
				tcp.CloseConnection();
				publishProgress("Done.. Cleaning up..");
			} else {
				publishProgress("Failed.. Cleaning up..");
			}
			SystemClock.sleep(3000);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ChooseDeviceActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					dialog.cancel();
					if (success) {
						startActivity(new Intent(ChooseDeviceActivity.this, SetupSuccessfulActivity.class).putExtra("deviceId", deviceId));
						finish();
					} else {
						bConnect.setEnabled(true);
						Toast.makeText(ChooseDeviceActivity.this, "Failed. Please retry..", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

	}
}
