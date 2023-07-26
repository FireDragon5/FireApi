package me.firedraong5.firesapi;

import me.firedraong5.firesapi.menu.MenuListener;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class FiresApi extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new UtilsMessage(), this);

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
