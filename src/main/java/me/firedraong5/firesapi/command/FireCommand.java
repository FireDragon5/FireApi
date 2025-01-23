package me.firedraong5.firesapi.command;

import me.firedraong5.firesapi.annotation.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class FireCommand extends BukkitCommand {

	private final Map<String, Method> subCommands = new HashMap<>();

	public FireCommand(@NotNull String command, @NotNull String description, String... aliases) {
		super(command);
		setDescription(description);
		setAliases(Arrays.asList(aliases));
		registerSubCommands();
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
		// If no subcommands are defined, call the default executeCommand method
		if (subCommands.isEmpty()) {
			executeCommand(sender, args);
			return true;
		}

		// Handle subcommands
		String subCommand = args.length > 0 ? args[0].toLowerCase() : "";
		Method method = subCommands.get(subCommand);

		if (method != null) {
			Parameter parameter = method.getAnnotation(Parameter.class);

			// Validate arguments and permissions
			if (!validateArgs(parameter, sender, args)) {
				return true;
			}

			try {
				method.invoke(this, sender, args);
			} catch (Exception e) {
				sendError(sender, "An error occurred while executing this command.");
				e.printStackTrace();
			}
			return true;
		}

		// Fallback to default executeCommand method if no subcommand matches
		executeCommand(sender, args);
		return true;
	}

	/**
	 * Abstract method for commands without subcommands.
	 */
	protected abstract void executeCommand(CommandSender sender, String[] args);

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
		if (args.length == 1) {
			// Suggest subcommands
			return subCommands.keySet().stream()
					.filter(name -> name.startsWith(args[0].toLowerCase()))
					.collect(Collectors.toList());
		}

		if (args.length > 1) {
			// Handle subcommands that may suggest player names
			String subCommand = args[0].toLowerCase();
			Method method = subCommands.get(subCommand);

			if (method != null) {
				Parameter parameter = method.getAnnotation(Parameter.class);

				if (parameter.showPlayerNames() > 0) {
					String prefix = args[args.length - 1].toLowerCase();
					return Bukkit.getOnlinePlayers().stream()
							.map(Player::getName)
							.filter(name -> name.toLowerCase().startsWith(prefix))
							.collect(Collectors.toList());
				}
			}
		}

		return Collections.emptyList();
	}

	private boolean validateArgs(Parameter parameter, CommandSender sender, String[] args) {
		if (parameter.requiresPlayer() && !(sender instanceof Player)) {
			sendError(sender, "This command can only be used by players!");
			return false;
		}

		if (args.length - 1 < parameter.minArgs()) {
			sendError(sender, "Insufficient arguments. This command requires at least " + parameter.minArgs() + " arguments.");
			return false;
		}

		if (!parameter.permission().isEmpty() && !sender.hasPermission(parameter.permission())) {
			sendError(sender, "You do not have permission to use this command.");
			return false;
		}

		return true;
	}

	private void registerSubCommands() {
		for (Method method : this.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Parameter.class)) {
				Parameter parameter = method.getAnnotation(Parameter.class);
				subCommands.put(parameter.value().toLowerCase(), method);
			}
		}
	}

	protected void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GREEN + message);
	}

	protected void sendError(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.RED + message);
	}
}
