package de.luc1412.em.utils;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by Luc1412 on 05.10.2017 at 22:02
 */
public class DonatorChecker {

	private boolean hasDonator = false;

	public DonatorChecker() {
		startCheck();
	}

	private void startCheck() {
		try {
			Socket socket = new Socket("127.0.0.1", 6252);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream out = new PrintStream(socket.getOutputStream());

			String request = "donatorEM";
			Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §b§lSend Request to the Auth Server with your Server IP \n" +
					"to check if you have §3Donator Version§b.");
			out.println(request);

			String replyString = in.readLine();
			int replyInt = 0;

			if (replyString != null) {
				replyInt = Integer.parseInt(replyString);
			}

			if (replyInt == 1) {
				hasDonator = true;
				Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §b§lThe §3§lDONATOR Version §bwas activated! Thank you for your Donation <3");
			} else {
				if (replyString != null) {
					Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §b§lThe §3§lNORMAL Version §bwas activated!");
				} else {
					Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §4§lFailed to Connect to the Auth Server. Use Normal Version!");
				}
			}
			in.close();
			out.close();
			socket.close();
		} catch (ConnectException ex) {
			Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §4§lFailed to Connect to the Auth Server. Use Normal Version!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
		private void startClient() {

			ZMQ.Context context = ZMQ.context(1);

			ZMQ.Socket requester = context.socket(ZMQ.REQ);
			requester.setReceiveTimeOut(5000);
			requester.setLinger(0);


			requester.connect("tcp://Luc1412.tk:5555");

				try {
					String request = InetAddress.getLocalHost().getHostAddress();
					Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §b§lSend Request to the Auth Server with your Server IP \n" +
							"to check if you have §3Donator Version§b. Your IP: §3" + request);
					requester.send(request, 0);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}


				String replyString = requester.recvStr(0);
				int replyInt = 0;

				if (replyString != null) {
					replyInt = Integer.parseInt(replyString);
				}

				if (replyInt == 1) {
					hasDonator = true;
					Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §b§lThe §3§lDONATOR Version §bwas activated! Thank you for your Donation <3");
				} else {
					if (replyString != null) {
						Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §b§lThe §3§lNORMAL Version §bwas activated!");
					} else {
						Bukkit.getConsoleSender().sendMessage("§8[§1Easy§4Maintenance§8] §4§lFailed to Connect to the Auth Server. Use Normal Version!");
					}
				}
			requester.close();
			context.term();
		}
	*/
	public boolean hasDonator() {
		return hasDonator;
	}

}
