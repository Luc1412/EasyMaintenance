package de.luc1412.em.configuration;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luc1412 on 17.04.2017 at 17:12
 */

public class SettingsConfig {

	private File file = new File(EasyMaintenance.getInstance().getDataFolder().toString() + File.separator + "settings.yml");
	private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	public SettingsConfig() {
		createConfig();
	}

	private void createConfig() {
		try {
			if (!EasyMaintenance.getInstance().getDataFolder().exists())
				EasyMaintenance.getInstance().getDataFolder().mkdir();
			if (!file.exists()) file.createNewFile();

			cfg.options().header("PLEASE DON´T CHANGE SOMETHING HERE!");
			cfg.options().copyHeader(true);

			cfg.addDefault("Maintenance", false);
			cfg.addDefault("warn_v1_0_2", false);
			cfg.addDefault("warn_v1_0_3", false);
			cfg.addDefault("warn_v1_0_4", false);
			cfg.addDefault("warn_v1_1_0", false);
			cfg.addDefault("warn_v1_1_2", false);
			cfg.addDefault("warn_v1_2_0", false);
			cfg.addDefault("warn_v1_2_1", false);

			cfg.options().copyDefaults(true);

			cfg.save(file);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public boolean getBoolean(String path) {
		return cfg.getBoolean(path);
	}

	public void setBoolean(String path, boolean bool) {
		cfg.set(path, bool);

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
