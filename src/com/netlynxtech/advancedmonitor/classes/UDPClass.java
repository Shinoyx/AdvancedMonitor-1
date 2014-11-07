package com.netlynxtech.advancedmonitor.classes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.util.Log;

public class UDPClass {
	private static String UDP_IP;
	private static String UDP_PORT;
	private static String UDP_MESSAGE;

	public UDPClass(String ip, String port, String message) {
		UDP_IP = ip;
		UDP_PORT = port;
		UDP_MESSAGE = message;
	}

	public String run() {
		String ip = "";
		byte[] arrayOfByte;
		DatagramPacket localDatagramPacket2 = null;
		String str8;

		DatagramSocket localDatagramSocket = null;
		try {
			localDatagramSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InetAddress localInetAddress = null;
		DatagramPacket localDatagramPacket1 = null;
		String str2 = UDP_IP;
		int i = Integer.parseInt(UDP_PORT);
		String str3 = UDP_MESSAGE;
		if ((i > 65535) || (i < 0)) {
			Log.e("Input Error", "ip port invalid");
			Log.e("PORT ERROR", "PORT INVALID");
			return "";
		}
		try {
			localInetAddress = InetAddress.getByName(str2);
			localDatagramPacket1 = new DatagramPacket(str3.getBytes(), str3.length(), localInetAddress, i);
			try {
				localDatagramSocket.send(localDatagramPacket1);
				Log.e("Success", "UDP packet sent");
				try {
					localDatagramSocket.setSoTimeout(10000); // 1 min
					arrayOfByte = new byte[1514];
					localDatagramPacket2 = new DatagramPacket(arrayOfByte, arrayOfByte.length, localInetAddress, localDatagramSocket.getLocalPort());
					System.out.println("local port: " + localDatagramSocket.getLocalPort());
					for (;;) {
						try {
							localDatagramSocket.receive(localDatagramPacket2);
							str8 = new String(localDatagramPacket2.getData(), 0, localDatagramPacket2.getLength());
							System.out.println("received:" + str8);
							Log.e("Success", "UDP packet sent and received: '" + str8 + "'");
							return str8;
						} catch (SocketTimeoutException localSocketTimeoutException) {
							localSocketTimeoutException.printStackTrace();
							Log.e("IO Error", "UDP packet sent, but timeout on receipt");
						} catch (IOException localIOException2) {
							localIOException2.printStackTrace();
							Log.e("Error", localIOException2.getMessage());
						}

					}
				} catch (NumberFormatException localNumberFormatException) {
					localNumberFormatException.printStackTrace();
				} catch (SocketException localSocketException1) {
					localSocketException1.printStackTrace();
					Log.e("IO Error", "UDP packet sent, but cannot receive message ('DatagramSocket.setSoTimeout()' method failed");
				}
			} catch (IOException localIOException1) {
				localIOException1.printStackTrace();
				Log.e("IO Error", "cannot send message ('DatagramSocket.send()' method failed" + localIOException1.getMessage());
				// return;
			}
		} catch (UnknownHostException localUnknownHostException) {
			localUnknownHostException.printStackTrace();
			Log.e("IO Error", "cannot resolve host address ('InetAddress.getByName()' method failed)" + localUnknownHostException.getMessage());
			// return;
		}
		return "";
	}
}