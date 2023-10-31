package me.firedraong5.firesapi.fileManager;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public abstract class FireManager {


	//	Loads
	public abstract void load();

	//	Save
	public abstract void save();

	//	Reload
	public abstract void reload();

	//	Check file
	public abstract void checkFile();


}
