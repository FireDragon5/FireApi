package me.firedraong5.firesapi.command;


import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public abstract class FireCommand extends BukkitCommand {

	private CommandSender sender;

	public FireCommand(@NotNull String command,
					   @NotNull String[] aliases, @NotNull String description, String permission) {
		super(command);

		this.setAliases(Arrays.asList(aliases));
		this.setDescription(description);
		this.setPermission(permission);

		try {
			Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			field.setAccessible(true);
			CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
			commandMap.register(command, this);


		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {

		this.sender = sender;

		if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
			UtilsMessage.noPermissionMessage((Player) sender, this.getPermission());
			return true;
		}

		this.execute(sender, args);
		return true;


	}

	public abstract void execute(CommandSender sender, String[] args);

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender,
											 @NotNull String alias, @NotNull String[] args)
			throws IllegalArgumentException {

		return onTabComplete(sender, args);
	}

	public abstract List<String> onTabComplete(CommandSender sender, String[] args);


	//	isPlayer
	protected final boolean isPlayer() {
		return this.sender instanceof Player;
	}

//	Make a method that the user can call to check if the sender was the player and not the console
//	Do this without taking the sender as a parameter

	protected final void checkConsole() throws CommandException {
		if (!this.isPlayer()) {
			throw new CommandException(ChatColor.RED + "You must be a player to use this command!");
		}
	}


}
