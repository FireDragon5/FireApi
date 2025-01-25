package me.firedraong5.firesapi.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public abstract class FireCommand extends BukkitCommand {

	private final List<String> allAliases;
	private final String permissionErrorMessage;
	private final String playerOnlyErrorMessage;
	private static CommandMap commandMap;

	public FireCommand(@NotNull String command, String[] aliases, @NotNull String description,
					   @Nullable String permissionErrorMessage, @Nullable String playerOnlyErrorMessage) {
		super(command);
		setDescription(description);

		this.allAliases = new ArrayList<>(List.of(command));
		if (aliases != null) {
			this.allAliases.addAll(Arrays.asList(aliases));
			setAliases(Arrays.asList(aliases));
		}

		this.permissionErrorMessage = permissionErrorMessage != null ? permissionErrorMessage : "You don't have permission to execute this command.";
		this.playerOnlyErrorMessage = playerOnlyErrorMessage != null ? playerOnlyErrorMessage : "This command can only be executed by players.";

		registerCommand(this);
	}

	protected abstract void executeCommand(CommandSender sender, String[] args);

	protected abstract List<String> tabCompleteCommand(CommandSender sender, String[] args);

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
		try {
			executeCommand(sender, args);
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "An unexpected error occurred while executing the command.");
			e.printStackTrace();
		}
		return true;
	}

	private static void registerCommand(BukkitCommand command) {
		if (commandMap == null) {
			commandMap = getCommandMap();
		}
		if (commandMap != null) {
			commandMap.register(command.getLabel(), command);
		} else {
			Bukkit.getLogger().severe("Failed to register command: " + command.getLabel());
		}
	}

	private static CommandMap getCommandMap() {
		try {
			Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);
			return (CommandMap) commandMapField.get(Bukkit.getServer());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
		return tabCompleteCommand(sender, args);
	}

	public List<String> getAllAliases() {
		return allAliases;
	}

	protected boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	protected void requirePlayer(CommandSender sender) {
		if (!isPlayer(sender)) {
			throw new CommandException(playerOnlyErrorMessage);
		}
	}

	protected void checkPermission(CommandSender sender, String permission) {
		if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
			throw new CommandException(permissionErrorMessage);
		}
	}

	protected List<String> suggestPlayers(String prefix) {
		return Bukkit.getOnlinePlayers().stream()
				.map(Player::getName)
				.filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
				.collect(Collectors.toList());
	}
}
