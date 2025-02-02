package me.firedraong5.firesapi.command;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public abstract class FireCommand extends BukkitCommand {

	private final List<String> allAliases;
	private final String permission;
	private final String permissionErrorMessage;
	private final String playerOnlyErrorMessage;
	private static CommandMap commandMap;

	public FireCommand(@NotNull String command, String[] aliases, @NotNull String description,
					   @Nullable String permission, @Nullable String permissionErrorMessage,
					   @Nullable String playerOnlyErrorMessage) {
		super(command);
		setDescription(description);

		this.allAliases = new ArrayList<>(List.of(command));
		if (aliases != null) {
			this.allAliases.addAll(Arrays.asList(aliases));
			setAliases(Arrays.asList(aliases));
		}

		this.permission = permission;
		setPermission(permission);
		this.permissionErrorMessage = permissionErrorMessage != null ? permissionErrorMessage : "You don't have permission to execute this command.";
		this.playerOnlyErrorMessage = playerOnlyErrorMessage != null ? playerOnlyErrorMessage : "This command can only be executed by players.";

		registerCommand(this);
	}

	protected abstract void executeCommand(CommandSender sender, String[] args);

	protected abstract List<String> tabCompleteCommand(CommandSender sender, String[] args);

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
		try {
			if (permission != null && !permission.isEmpty()) {
				checkPermission(sender, permission);
			}
			executeCommand(sender, args);
		} catch (CommandException e) {
			UtilsMessage.sendMessage(sender, "&c" + e.getMessage());
		} catch (Exception e) {
			UtilsMessage.sendMessage(sender, "&cAn error occurred while executing this command.");
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
			Objects.requireNonNull(Bukkit.getPluginCommand(command.getLabel())).setExecutor((CommandExecutor) command);
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
		if (permission != null && !sender.hasPermission(permission)) {
			return Collections.emptyList(); // Restrict visibility of tab completion if permission is missing
		}
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

	protected List<String> suggestPlayers(String prefix, Player self, boolean showSelf) {
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> showSelf || !player.equals(self))
				.map(Player::getName)
				.filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
				.collect(Collectors.toList());
	}

	protected List<String> suggestPlayers(String prefix, CommandSender self, boolean showSelf) {
		return Bukkit.getOnlinePlayers().stream()
				.filter(player -> showSelf || !player.equals(self))
				.map(Player::getName)
				.filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
				.collect(Collectors.toList());
	}

	protected List<String> suggestPlayers(String prefix) {
		return Bukkit.getOnlinePlayers().stream()
				.map(Player::getName)
				.filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
				.collect(Collectors.toList());
	}




	public boolean canSenderView(CommandSender sender) {
		// Check if the sender can view the command based on permissions
		return permission == null || sender.hasPermission(permission);
	}
}