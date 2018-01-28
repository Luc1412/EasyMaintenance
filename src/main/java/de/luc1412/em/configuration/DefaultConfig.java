package de.luc1412.em.configuration;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

/**
 * Created by Luc1412 on 03.10.2017 at 18:09
 */
public class DefaultConfig {

	private File file = new File(EasyMaintenance.getInstance().getDataFolder(), "config.yml");
	private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	private Map<String, Object> cfgValues;

	public DefaultConfig() {
		cfgValues = cfg.getValues(true);
	}

	public int getInt(String path) {
		return (int) cfgValues.get(path);
	}

	public String getString(String path) {
		return (String) cfgValues.get(path);
	}

	public boolean getBoolean(String path) {
		return (boolean) cfgValues.get(path);
	}

	public void reloadConfig() {
		cfg = YamlConfiguration.loadConfiguration(file);
		cfgValues = cfg.getValues(true);
	}

}
