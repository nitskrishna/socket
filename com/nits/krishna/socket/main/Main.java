package com.nits.krishna.socket.main;

import com.nitskrishna.socket.client.Client;
import com.nitskrishna.socket.server.Server;

public class Main {
	public static void main(String[] args) {
		int port = 5000;
		String ip = "127.0.0.1";
		Server server = new Server();
		Client client = new Client();
		server.start(port);
		client.start(ip, port);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			client.stop();
			server.stop();
		}
	}
}
