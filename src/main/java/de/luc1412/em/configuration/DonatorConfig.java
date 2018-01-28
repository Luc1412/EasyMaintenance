package de.luc1412.em.configuration;

import de.luc1412.em.EasyMaintenance;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Luc1412 on 29.09.2017 at 21:43
 */
public class DonatorConfig {

	private File file = new File(EasyMaintenance.getInstance().getDataFolder() + File.separator + "donator-config.yml");
	private YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	private Map<String, Object> cfgValues;

	public DonatorConfig() {
		try {
			createConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cfgValues = cfg.getValues(true);
	}

	private void createConfig() throws IOException {
		if (!EasyMaintenance.getInstance().getDataFolder().exists())
			EasyMaintenance.getInstance().getDataFolder().mkdir();
		if (!file.exists()) file.createNewFile();

		cfg.options().header("Thank you for your donation ;)\n" +
				"Here you can change the donator features.\n" +
				" \n" +
				"If you want to use the custom Prefix you must change\n" +
				"in the normal Config the PrefixStyle to 5.\n" +
				" ");
		cfg.options().copyHeader(true);

		cfg.addDefault("CustomPrefix", "&7&l[&1Easy&4Maintenance&7&l] &r");

		cfg.addDefault("ListHoverText.Line6", "");
		cfg.addDefault("ListHoverText.Line7", "");
		cfg.addDefault("ListHoverText.Line8", "");
		cfg.addDefault("ListHoverText.Line9", "");
		cfg.addDefault("ListHoverText.Line10", "");

		cfg.options().copyDefaults(true);

		cfg.save(file);
	}


	public String getString(String path) {
		return ChatColor.translateAlternateColorCodes('&', (String) cfgValues.get(path));
	}

	public void reloadConfig() {
		cfg = YamlConfiguration.loadConfiguration(file);
		cfgValues = cfg.getValues(true);
	}


}
