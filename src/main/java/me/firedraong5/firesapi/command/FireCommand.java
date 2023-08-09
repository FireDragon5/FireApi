package me.firedraong5.firesapi.command;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FireCommand extends Command {


	private String name;
	private List<String> aliases;

	private String permission;


	private FireCommand(@NotNull String name, @NotNull List<String> aliases) {
		super(name);

		this.name = name;
		this.setName(name);

		if (aliases != null) {
			this.setAliases(aliases);
		}


	}


	@Override
	public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {


		return false;
	}


	//	Setters
	public boolean setName(@NotNull String name) {
		this.name = name;
		return super.setName(name);
	}


	public void setPermission(String permission) {
		this.permission = permission;

	}


	//Getters
	public @NotNull String getName() {
		return name;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public String getPermission() {
		return permission;
	}



}
