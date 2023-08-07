package me.firedraong5.firesapi;

import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.menu.Menu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FiresApi extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new UtilsMessage(), this);

        FireCommand fireCommand = new FireCommand("test");
        Objects.requireNonNull(getCommand("test")).setExecutor((CommandExecutor) fireCommand);

    }

    @Override
    public void onDisable() {


    }
}
