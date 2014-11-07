package com.netlynxtech.advancedmonitor.classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TCPService extends Service {
	public IBinder onBind(Intent paramIntent) {
		return null;
	}

	public void onCreate() {
		super.onCreate();
	}

	public void onDestroy() {
	}

	public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
		Log.e("TCPService", "Starting to receive");
		TCPClass.recievingMessage();
		return 1;
	}
}
