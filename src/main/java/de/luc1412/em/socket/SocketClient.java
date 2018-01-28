package de.luc1412.em.socket;

import de.luc1412.em.EasyMaintenance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Luc1412 on 11.08.2017 at 13:33
 */
public class SocketClient {

	private Socket socketClient;
	private PrintWriter writer;
	private BufferedReader reader = null;
	private String host;
	private int port;

	public SocketClient(String host, int port) {
		this.host = host;
		this.port = port;
		connectToSocketServer(host, port);
	}

	void connectToSocketServer(String host, int port) {

		try {
			socketClient = new Socket(host, port);
			reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			writer = new PrintWriter(socketClient.getOutputStream());
			EasyMaintenance.getUtils().log("Client was started on the host " + host + " and the port " + port);

			Thread thread = new Thread(new SocketHandler(reader));
			thread.start();
		} catch (IOException e) {
			EasyMaintenance.getUtils().log("Client could not connect to the Socket Server! Retrying in 10 Seconds...");
			e.printStackTrace();
			try {
				Thread.sleep(10000);
				connectToSocketServer(host, port);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
		}
	}

	//SendData
	public void sendDataToSocketServer(String data) {
		writer.println(data);
		writer.flush();
	}

	protected void disconnectFromSocketServer() {
		try {
			socketClient.close();
			writer.close();
			reader.close();
		} catch (IOException e) {
			EasyMaintenance.getUtils().log("Error while disconnecting from SocketServer");
			e.printStackTrace();
		}
	}

	String getHost() {
		return host;
	}

	int getPort() {
		return port;
	}
}
