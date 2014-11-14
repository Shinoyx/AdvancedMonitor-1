package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class MapsActivity extends ActionBarActivity {
	private GoogleMap googleMap;
	Device device;
	ArrayList<String> data = new ArrayList<String>();
	getLocation mTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.activity_map);
		device = (Device) getIntent().getSerializableExtra("device");
		mTask = null;
		mTask = new getLocation();
		mTask.execute();
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

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			// double latitude = Double.parseDouble(data.get(0));
			// double longitude = Double.parseDouble(data.get(1));
			double latitude = 1.358364;
			double longitude = 103.833461;

			LatLng deviceLocation = new LatLng(latitude, longitude);
			googleMap.setMyLocationEnabled(true);
			MarkerOptions marker = new MarkerOptions().position(deviceLocation).title(device.getDescription());
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deviceLocation, 17));
			googleMap.addMarker(marker);
			if (googleMap == null) {
				Log.e("MapsActivity", "Error loading map");
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// initilizeMap();
	}

	private class getLocation extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			data = new WebRequestAPI(MapsActivity.this).GetLocation(device.getDeviceID());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			MapsActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					try {
						initilizeMap();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	}
}
