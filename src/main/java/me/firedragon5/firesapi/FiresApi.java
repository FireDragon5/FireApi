package me.firedragon5.firesapi;


import me.firedragon5.firesapi.utils.UtilsMessage;
import org.bukkit.plugin.java.JavaPlugin;


public final class FiresApi extends JavaPlugin {

	private static FiresApi instance;

    @Override
    public void onEnable() {

		instance = this;

        getServer().getPluginManager().registerEvents(new UtilsMessage(), this);
    }

    public static FiresApi getInstance() {
		return instance;
    }

}
