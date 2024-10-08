package me.firedraong5.firesapi.command;


import me.firedraong5.firesapi.annotation.Parameter;
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


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import java.util.stream.Collectors;


@SuppressWarnings("unused")
public abstract class FireCommand extends BukkitCommand {

	private CommandSender sender;

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
		String param = args.length > 0 ? args[0] : "";
		Method method = this.methods.get(param.toLowerCase());

		if (method != null) {
			if (method.isAnnotationPresent(Parameter.class)) {
				Parameter parameter = method.getDeclaredAnnotation(Parameter.class);
				if (parameter.requiresPlayer() && this.isPlayer()) {
					assert sender instanceof Player;
					UtilsMessage.sendMessage((Player) sender, "&cYou must be a player to use this command!");
					return true;
				}
				if (args.length < parameter.minArgs()) {
					UtilsMessage.sendMessage(sender, "&cInsufficient arguments. This command requires at least "
							+ parameter.minArgs() + " arguments.");
					return true;
				}

				if (!parameter.permission().isEmpty() && !sender.hasPermission(parameter.permission())) {
					UtilsMessage.noPermissionMessage((Player) sender, parameter.permission(), false);
					return true;
				}
			}
		} else if (!param.isEmpty()) {
			UtilsMessage.sendMessage(sender, "&cUsage: /" + this.getName() +
					" <" + this.methods.keySet().stream()
					.filter(methodName -> !methodName.isEmpty())
					.collect(Collectors.joining("|")) + ">");
			return true;
		}

		if (this.getPermission() != null && !sender.hasPermission(this.getPermission())) {
			assert sender instanceof Player;
			UtilsMessage.noPermissionMessage((Player) sender, this.getPermission());
			return true;
		}

		try {
			if (method != null) {
				method.invoke(this, sender, args);
			} else {
				this.execute(sender, args);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public abstract void execute(CommandSender sender, String[] args);

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender,
											 @NotNull String alias, @NotNull String[] args)
			throws IllegalArgumentException {
		if (args.length == 1) {
			return this.methods.keySet().stream()
					.filter(methodName -> !methodName.isEmpty())
					.collect(Collectors.toList());
		}

		return onTabComplete(sender, args);
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
		this.methods.forEach((key, value) -> {
			System.out.println(key + " : " + value.getName());
		});
	}


}
