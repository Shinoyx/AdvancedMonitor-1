package com.netlynxtech.advancedmonitor.classes;

import java.util.Random;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.dd.processbutton.ProcessButton;
import com.netlynxtech.advancedmonitor.RegisterPhoneActivity;

public class ProgressGenerator {

	Context context;
	ProcessButton button;
	TextView tvStatusDesc;

	public interface OnCompleteListener {
		public void onComplete();
	}

	private OnCompleteListener mListener;
	private int mProgress = 0;

	public ProgressGenerator(OnCompleteListener listener) {
		mListener = listener;
	}

	public void start(final ProcessButton button) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mProgress += 10;
				button.setProgress(mProgress);
				if (mProgress < 100) {
					handler.postDelayed(this, generateDelay());
				} else {
					mListener.onComplete();
				}
			}
		}, generateDelay());
	}

	private Random random = new Random();

	private int generateDelay() {
		return random.nextInt(1000);
	}

	private class requestPin extends AsyncTask<String, Void, Void> {
		String res = "";

		@Override
		protected Void doInBackground(String... params) {
			res = new WebRequestAPI(context).RequestPin(params[0].trim(), params[1].trim());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (res.equals("success")) {
				Log.e("onPostExecute", "success");
				button.setProgress(100);
				button.setText(res);
			} else {
				mProgress = 0;
				button.setProgress(-1);
				button.setText(res);
				Log.e("onPostExecute", "failed");
			}
			mListener.onComplete();
		}

	}

	public void requestPin(final ProcessButton button, final String phoneNo, final String name, final Context context) {
		this.button = button;
		this.context = context;

		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				mProgress += 25;
				button.setProgress(mProgress);
				if (mProgress < 74) {
					handler.postDelayed(this, generateDelay());
				} else {
					new requestPin().execute(phoneNo, name);
				}
			}
		});
	}

	private class verifyPin extends AsyncTask<String, Void, Void> {
		String res = "";

		@Override
		protected Void doInBackground(String... params) {
			res = new WebRequestAPI(context).VerifyPin(params[0], params[1]);
			// res = new WebRequestAPI(context).checkPin(phoneNo, params[0], "123456", "123456");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (res.equals("success")) {
				Log.e("onPostExecute", "success");
				button.setProgress(100);
				button.setText(res);
			} else {
				mProgress = 0;
				button.setProgress(-1);
				button.setText(res);
				Log.e("onPostExecute", "failed");
			}
			mListener.onComplete();
		}

	}

	public void verifyPin(final ProcessButton button, final String pinNo, final String gcmid, final Context context) {
		this.button = button;
		this.context = context;

		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				mProgress += 25;
				button.setProgress(mProgress);
				if (mProgress < 74) {
					handler.postDelayed(this, generateDelay());
				} else {
					new verifyPin().execute(pinNo, gcmid);
				}
			}
		});
	}

	private class updateLocation extends AsyncTask<String, Void, Void> {
		String res = "";

		@Override
		protected Void doInBackground(String... str) {
			// res = new WebRequestAPI(context).updateLocation(str[0].toString(), str[1].toString(), str[2].toString());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (res.contains("success|")) {
				Log.e("onPostExecute", "success");
				button.setProgress(100);
				button.setText("Success");
				button.setEnabled(false);
			} else {
				mProgress = 0;
				button.setProgress(-1);
				button.setText(res);
				button.setEnabled(true);
				Log.e("onPostExecute", "failed");
			}
			mListener.onComplete();
		}

	}

	public void updateLocation(final ProcessButton button, final Location loc, final String deviceID, Context context) {
		this.context = context;
		this.button = button;
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				mProgress += 25;
				button.setProgress(mProgress);
				if (mProgress < 74) {
					handler.postDelayed(this, generateDelay());
				} else {
					new updateLocation().execute(deviceID, String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()));
				}
			}
		});
	}
}