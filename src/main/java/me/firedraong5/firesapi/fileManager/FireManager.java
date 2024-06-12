package me.firedraong5.firesapi.fileManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FireManager {

	private final JavaPlugin plugin;
	private FileConfiguration config;
	private final File configFile;

	public FireManager(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		this.configFile = new File(plugin.getDataFolder(), fileName);
		this.config = YamlConfiguration.loadConfiguration(configFile);
	}


	public void save() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			plugin.getLogger().severe("Could not save config to " + configFile);
		}
	}

	public void reload() {
		this.config = YamlConfiguration.loadConfiguration(configFile);
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void set(String path, Object value) {
		config.set(path, value);
	}

	public Object get(String path) {
		return config.get(path);
	}


}
