package com.netlynxtech.advancedmonitor.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TCPClass {
	static String IP;
	static String PORT;
	public static File[] file = null;
	public static BufferedReader in;
	public static PrintWriter out;
	static String response;
	public static Socket sck = null;
	static Context context;

	static String responseToGetBack = "";
	private static OnMessageReceived mMessageListener = null;

	public TCPClass(Context c, String ip, String port, OnMessageReceived listener) {
		context = c;
		IP = ip;
		PORT = port;
		mMessageListener = listener;
		OpenConnection();
	}

	public void CloseConnection() {
		Log.e("Connection", "Close");
		context.stopService(new Intent(context, TCPService.class));
		new Thread(new Runnable() {
			public void run() {
				try {
					if (mMessageListener != null) {
						mMessageListener = null;
					}
					if (sck != null) {
						sck.close();
					}
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
					return;
				} catch (IOException localIOException) {
					localIOException.printStackTrace();
				}
			}
		}).start();
	}

	private void OpenConnection() {
		Log.e("OPENING", "CONNECTION");
		new Thread(new Runnable() {
			public void run() {
				int i = Integer.parseInt(PORT);
				if (sck == null) {
					Log.e("SOCET", "NULL");
				}
				try {
					sck = new Socket(IP, i);
					Log.e("Connected", "true");
					context.startService(new Intent(context, TCPService.class));
					return;
				} catch (UnknownHostException localUnknownHostException) {
					CloseConnection();
					sck = null;
					localUnknownHostException.printStackTrace();
					return;
				} catch (IOException localIOException) {
					CloseConnection();
					localIOException.printStackTrace();
					sck = null;
				}
			}
		}).start();
		try {
			Thread.sleep(101L);
			return;
		} catch (InterruptedException localInterruptedException) {
			CloseConnection();
			localInterruptedException.printStackTrace();
		}
	}

	public static void recievingMessage() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						if (sck != null) {
							Log.e("Message", "polling message");
							in = new BufferedReader(new InputStreamReader(sck.getInputStream()));
							out = new PrintWriter(sck.getOutputStream());
							char[] arrayOfChar = new char[2048];
							for (;;) {
								int i = in.read(arrayOfChar);
								if (i != -1) {
									response = new String(arrayOfChar).substring(0, i);
									Log.e("RESPONSE", response);
									mMessageListener.messageReceived(response);
								}
							}
						} else {
							Log.e("Socket", "SOcket is null");
						}
						return;
					} catch (IOException localIOException1) {
						localIOException1.printStackTrace();
						try {
							sck.close();
							in.close();
							out.close();
							sck = null;
							return;
						} catch (IOException localIOException2) {
							localIOException2.printStackTrace();
							return;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public static boolean sendDataWithString(String paramString) {
		Log.e("Message", "Sending message : " + paramString);
		try {
			out = new PrintWriter(sck.getOutputStream());
			if ((paramString != null) && (sck != null)) {
				out.write(paramString);
				out.flush();
				Log.e("Message", "SENT!");
			}
			return false;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
			return false;
		}
	}

	public interface OnMessageReceived {
		public void messageReceived(String message);
	}
}
