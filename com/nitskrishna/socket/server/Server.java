package com.nitskrishna.socket.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private Thread commThread;
	private int counter = 1;
	public void start(final int port) {
		commThread = new Thread() {
			@Override
			public void run() {
				startServer(port);
			}
		};
		commThread.start();
	}
	private void startServer(int port) {
		Socket socket = null;
		ServerSocket serverSocket = null;
		BufferedReader reader = null;
		BufferedOutputStream outputStream = null;
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new BufferedOutputStream(socket.getOutputStream());
			try {
				while(!Thread.currentThread().isInterrupted()) {
					outputStream.write(("Message is : "+counter).getBytes());
					outputStream.flush();
					char[] buffer = new char[20];
					reader.read(buffer);
					print("Message Recieved :: "+new String(buffer));
					counter++;
					
					Thread.sleep(1000);
				}
			}catch(InterruptedException exception) {
				print("Communication completed!!");
			}catch(IOException ioException) {
				print("IO Exception Occurred!!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader != null) {
					reader.close();
				}
			}catch (IOException ioexcp) {
				print("ioExcep occured");
			}
			try {
				if(outputStream != null) {
					outputStream.close();
				}
			}catch (IOException ioexcp) {
				print("ioExcep occured");
			}
			try {
				if(socket != null) {
					socket.close();
				}
			}catch (IOException ioexcp) {
				print("ioExcep occured");
			}
		}
	}
	public void stop() {
		if(commThread != null && commThread.isAlive()) {
			commThread.interrupt();
		}
	}
	public void print(String content) {
		System.out.println("Server End :: "+content);
	}
}
