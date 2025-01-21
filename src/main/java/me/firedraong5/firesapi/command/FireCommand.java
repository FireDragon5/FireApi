package me.firedraong5.firesapi.command;


import me.firedraong5.firesapi.annotation.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import java.util.stream.Collectors;


@SuppressWarnings("unused")
public abstract class FireCommand extends BukkitCommand {

	private CommandSender sender;

	public abstract void execute(CommandSender sender, String[] args);


	public final Map<String, Method> methods = new HashMap<>();

	public FireCommand(@NotNull String command,
					   @NotNull String[] aliases, @NotNull String description, String permission) {
		super(command);

		this.setAliases(Arrays.asList(aliases));
		this.setDescription(description);

//		If the String permission = "default" then the permission will be null
		if (permission.equals("default") || permission.isEmpty()) {
			permission = null;
		}
		this.setPermission(permission);

		this.findMethods();

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

		// If no @Parameter methods are found, call the abstract execute method
		if (this.methods.isEmpty()) {
			this.execute(sender, args);
			return true;
		}

		String param = args.length > 0 ? args[0] : "";
		Method method = this.methods.get(param.toLowerCase());

		if (method != null) {
			Parameter parameter = method.getDeclaredAnnotation(Parameter.class);

			if (!validateArgs(parameter, sender, args)) {
				return true;
			}

			try {
				method.invoke(this, sender, args);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "An error occurred while executing this command.");
				e.printStackTrace();
			}
			return true;
		}

		if (param.isEmpty()) {
			defaultExecute(sender, args);
		}
		return true;
	}

	// Validates all argument and permission requirements for subcommands
	private boolean validateArgs(Parameter parameter, CommandSender sender, String[] args) {
		if (parameter.requiresPlayer() && !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return false;
		}
		if (args.length < parameter.minArgs()) {
			sender.sendMessage(ChatColor.RED + "Insufficient arguments. Requires at least "
					+ parameter.minArgs() + " arguments.");
			return false;
		}
		if (!parameter.permission().isEmpty() && !sender.hasPermission(parameter.permission())) {
			sender.sendMessage(ChatColor.RED + "You lack permission: " + parameter.permission());
			return false;
		}
		return true;
	}

	// Fallback execution if no subcommand is specified
	protected void defaultExecute(CommandSender sender, String[] args) {
		this.execute(sender, args);
	}
	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender,
											 @NotNull String alias, @NotNull String[] args)
			throws IllegalArgumentException {

		if (args.length == 0) {
			// Return all online player names if no arguments are provided
			return Bukkit.getOnlinePlayers().stream()
					.map(Player::getName)
					.collect(Collectors.toList());
		}

		if (args.length == 1) {
			List<String> baseCompletions = this.methods.keySet().stream()
					.filter(methodName -> !methodName.isEmpty())
					.filter(methodName -> methodName.startsWith(args[0].toLowerCase()))
					.collect(Collectors.toList());

			// Delegate to sub-class for further filtering based on sender's rank
			return filterByRank(sender, baseCompletions);
		}

		// Check if the first argument matches a method with showPlayerNames > 0
		String param = args[0].toLowerCase();
		Method method = this.methods.get(param);
		if (method != null) {
			Parameter parameter = method.getDeclaredAnnotation(Parameter.class);
			if (parameter.showPlayerNames() > 0) {
				// Return player names matching the last argument
				return Bukkit.getOnlinePlayers().stream()
						.map(Player::getName)
						.filter(name -> name.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
						.collect(Collectors.toList());
			}
		}

		// Fall back to sub-class tab completion
		return onTabComplete(sender, args);
	}

	// New method to filter by rank
	protected List<String> filterByRank(CommandSender sender, List<String> completions) {
		return completions;
	}


	public abstract List<String> onTabComplete(CommandSender sender, String[] args);

	//	isPlayer
	protected final boolean isPlayer() {
		return !(this.sender instanceof Player);
	}

//	Make a method that the user can call to check if the sender was the player and not the console
//	Do this without taking the sender as a parameter

	protected final void checkConsole() throws CommandException {
		if (this.isPlayer()) {
			throw new CommandException(ChatColor.RED + "You must be a player to use this command!");
		}
	}

	private void findMethods() {
		for (Method method : this.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Parameter.class)) {
				Parameter parameter = method.getDeclaredAnnotation(Parameter.class);
				int modifiers = method.getModifiers();

				if (!Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
					this.methods.put(parameter.value().toLowerCase(), method);
				}
			}
		}
	}


	//	Method to print all the methods found in the class
	public void printMethods() {
		Bukkit.getLogger().info("Registered Commands:");
		this.methods.forEach((key, value) ->
				Bukkit.getLogger().info("Subcommand: " + key + " -> Method: " + value.getName())
		);
	}


}
