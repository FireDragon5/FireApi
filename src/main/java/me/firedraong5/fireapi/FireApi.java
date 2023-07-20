package me.firedraong5.fireapi;

import me.firedraong5.fireapi.menus.Menu;
import me.firedraong5.fireapi.utils.UtilsMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class FireApi extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(new Menu(), this);
        getServer().getPluginManager().registerEvents(new UtilsMessage(), this);

    }


}
