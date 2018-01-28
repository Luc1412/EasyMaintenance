package de.luc1412.em.socket;

import de.luc1412.em.EasyMaintenance;
import de.luc1412.em.utils.MaintenanceStartCountdownManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Luc1412 on 12.08.2017 at 12:04
 */
class SocketHandler implements Runnable {

	private BufferedReader reader;
	private MaintenanceStartCountdownManager countdownManager;


	SocketHandler(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public void run() {
		String data;

		try {
			while ((data = reader.readLine()) != null) {
				if (data.startsWith("toggle")) {
					String[] args = data.split(" ");
					startCountdown(Integer.valueOf(args[1]));
				} else if (data.startsWith("cancel")) {
					cancelCountdown();
				} else if (data.startsWith("skip")) {
					String[] args = data.split(" ");
					skipCountdown(Integer.valueOf(args[1]));
				} else if (data.startsWith("sound")) {
					String[] args = data.split(" ");
					if (args[1].equals("maintenanceend")) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "MaintenanceEnd", 1000);
						}
					}
				}
			}
		} catch (IOException e) {
			EasyMaintenance.getUtils().log("Error while reading a Message from SocketServer!");
			e.printStackTrace();
			EasyMaintenance.getSocketClient().connectToSocketServer(EasyMaintenance.getSocketClient().getHost(), EasyMaintenance.getSocketClient().getPort());
		}
	}

	private void startCountdown(int seconds) {
		if (countdownManager == null) {
			countdownManager = new MaintenanceStartCountdownManager(seconds);
			countdownManager.startMaintenanceCountdown();
		} else EasyMaintenance.getSocketClient().sendDataToSocketServer("CountdownRunning");
	}

	private void cancelCountdown() {
		if (countdownManager != null) {
			countdownManager.stopCountdown(countdownManager.getTaskID());
			countdownManager.removeBossBar();
			for (Player all : Bukkit.getOnlinePlayers()) {
				try {
					EasyMaintenance.getUtils().sendActionBar(all, EasyMaintenance.getUtils().getTranslatedMsgOfConfig("ActionBar.CountdownStopped", false));
				} catch (Exception e) {
					e.printStackTrace();
				}
				EasyMaintenance.getUtils().playSoundOfConfig(all, all.getLocation(), "CountdownStopped", 1000);
				all.sendMessage(EasyMaintenance.getUtils().getTranslatedMsgOfConfig("Countdown.CountdownStopped", true));
			}
			resetCountdown();
		} else EasyMaintenance.getSocketClient().sendDataToSocketServer("CountdownNotRunning");
	}

	private void skipCountdown(int seconds) {
		if (countdownManager != null) {
			if (seconds < countdownManager.getSeconds()) {
				int current = countdownManager.getSeconds();
				int chache = current - seconds;
				countdownManager.setSeconds(chache);
			} else EasyMaintenance.getSocketClient().sendDataToSocketServer("SkipTimeToHigh");
		} else EasyMaintenance.getSocketClient().sendDataToSocketServer("CountdownNotRunning");
	}

	private void resetCountdown() {
		countdownManager = null;
	}

}
