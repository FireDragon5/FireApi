package me.firedraong5.firesapi;


import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.plugin.java.JavaPlugin;


public final class FiresApi extends JavaPlugin {

	//    Get the instance of the plugin
	private static FiresApi instance;


    @Override
    public void onEnable() {

		instance = this;

        getServer().getPluginManager().registerEvents(new UtilsMessage(), this);
    }

    @Override
    public void onDisable() {


	}

	public static FiresApi getInstance() {
		return instance;
    }

}
