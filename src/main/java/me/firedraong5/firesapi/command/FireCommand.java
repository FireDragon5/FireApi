package me.firedraong5.firesapi.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public abstract class FireCommand extends BukkitCommand {

	public FireCommand(@NotNull String command, @NotNull String description, String... aliases) {
		super(command);
		setDescription(description);
		setAliases(Arrays.asList(aliases));
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
			sender.sendMessage(ChatColor.RED + "An error occurred while executing the command.");
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
		return tabCompleteCommand(sender, args);
	}

	protected boolean isPlayer(CommandSender sender) {
		return sender instanceof Player;
	}

	protected void requirePlayer(CommandSender sender) {
		if (!isPlayer(sender)) {
			throw new CommandException("This command can only be executed by players.");
		}
	}

	protected List<String> suggestPlayers(String prefix) {
		return Bukkit.getOnlinePlayers().stream()
				.map(Player::getName)
				.filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
				.collect(Collectors.toList());
	}
}
