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

/**
 * Base class for creating custom commands with automatic registration.
 * Features:
 * - Automatic command registration via reflection
 * - Built-in permission checking
 * - Player-only command support
 * - Tab completion helpers
 * - Subcommand support
 * - Usage message integration
 */
@SuppressWarnings("unused")
public abstract class FireCommand extends BukkitCommand {

    private final List<String> allAliases;
    private final String permission;
    private final String permissionErrorMessage;
    private final String playerOnlyErrorMessage;
    private static CommandMap commandMap;

    /**
     * Create a new FireCommand
     *
     * @param command                 Main command name
     * @param aliases                 Command aliases (can be null)
     * @param description             Command description
     * @param permission              Required permission (null = no permission required)
     * @param permissionErrorMessage  Custom permission error message (null = default)
     * @param playerOnlyErrorMessage  Custom player-only error message (null = default)
     */
    public FireCommand(@NotNull String command, @Nullable String[] aliases, @NotNull String description,
                       @Nullable String permission, @Nullable String permissionErrorMessage,
                       @Nullable String playerOnlyErrorMessage) {
        super(command);
        setDescription(description);

        this.allAliases = new ArrayList<>();
        this.allAliases.add(command);

        if (aliases != null && aliases.length > 0) {
            this.allAliases.addAll(Arrays.asList(aliases));
            setAliases(Arrays.asList(aliases));
        }

        this.permission = permission;
        setPermission(permission);
        this.permissionErrorMessage = permissionErrorMessage != null ? permissionErrorMessage : "&cYou don't have permission to execute this command.";
        this.playerOnlyErrorMessage = playerOnlyErrorMessage != null ? playerOnlyErrorMessage : "&cThis command can only be executed by players.";

        registerCommand(this);
    }

    /**
     * Simplified constructor with default error messages
     */
    public FireCommand(@NotNull String command, @Nullable String[] aliases, @NotNull String description, @Nullable String permission) {
        this(command, aliases, description, permission, null, null);
    }

    /**
     * Simplified constructor without permission
     */
    public FireCommand(@NotNull String command, @Nullable String[] aliases, @NotNull String description) {
        this(command, aliases, description, null, null, null);
    }

    /**
     * Execute the command logic.
     * Override this method to implement your command.
     *
     * @param sender Command sender
     * @param args   Command arguments
     */
    protected abstract void executeCommand(CommandSender sender, String[] args);

    /**
     * Provide tab completion suggestions.
     * Override this method to implement custom tab completion.
     *
     * @param sender Command sender
     * @param args   Current command arguments
     * @return List of suggestions
     */
    protected List<String> tabCompleteCommand(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    /**
     * Called when command usage is incorrect.
     * Override this to show custom usage/help messages.
     *
     * @param sender Command sender
     */
    protected void onWrongUsage(CommandSender sender) {
        UtilsMessage.sendMessage(sender, "&cIncorrect usage! Use: &e/" + getName() + " " + getUsage());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        try {
            // Check permission before executing
            if (permission != null && !permission.isEmpty()) {
                checkPermission(sender, permission);
            }
            executeCommand(sender, args);
        } catch (CommandException e) {
            // Send formatted error message
            UtilsMessage.sendMessage(sender, e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected errors
            UtilsMessage.errorMessage(Objects.requireNonNull(asPlayer(sender)), "An error occurred while executing this command.");
        }
        return true;
    }

    /**
     * Register the command to the server's command map
     */
    private static void registerCommand(BukkitCommand command) {
        if (commandMap == null) {
            commandMap = getCommandMap();
        }
        if (commandMap != null) {
            commandMap.register(command.getLabel(), command);
        } else {
            // Fallback to plugin.yml registration
            if (Bukkit.getPluginCommand(command.getLabel()) != null) {
                Objects.requireNonNull(Bukkit.getPluginCommand(command.getLabel())).setExecutor((CommandExecutor) command);
            }
        }
    }

    /**
     * Get the server's CommandMap via reflection
     */
    private static CommandMap getCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (Exception e) {
            UtilsMessage.sendMessageConsole("Failed to access CommandMap via reflection.");
            return null;
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        // Hide tab completion if sender doesn't have permission
        if (permission != null && !sender.hasPermission(permission)) {
            return Collections.emptyList();
        }

        List<String> suggestions = tabCompleteCommand(sender, args);

        // Filter suggestions based on what the user has typed
        if (suggestions != null && !suggestions.isEmpty() && args.length > 0) {
            String lastArg = args[args.length - 1].toLowerCase();
            return suggestions.stream()
                    .filter(s -> s.toLowerCase().startsWith(lastArg))
                    .collect(Collectors.toList());
        }

        return suggestions != null ? suggestions : Collections.emptyList();
    }

    /**
     * Get all aliases including the main command name
     */
    public List<String> getAllAliases() {
        return new ArrayList<>(allAliases);
    }

    /**
     * Check if the sender is a player
     */
    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    /**
     * Get the sender as a Player, or null if not a player
     */
    protected @Nullable Player asPlayer(CommandSender sender) {
        return isPlayer(sender) ? (Player) sender : null;
    }

    /**
     * Require that the sender is a player, throw exception if not
     *
     * @throws CommandException if sender is not a player
     */
    protected void requirePlayer(CommandSender sender) {
        if (!isPlayer(sender)) {
            throw new CommandException(playerOnlyErrorMessage);
        }
    }

    /**
     * Check if sender has the required permission
     *
     * @throws CommandException if sender lacks permission
     */
    protected void checkPermission(CommandSender sender, String permission) {
        if (permission != null && !permission.isEmpty() && !sender.hasPermission(permission)) {
            throw new CommandException(permissionErrorMessage);
        }
    }

    /**
     * Check if sender can view this command (based on permissions)
     */
    public boolean canSenderView(CommandSender sender) {
        return permission == null || permission.isEmpty() || sender.hasPermission(permission);
    }

    /* =========================================================
     *   TAB COMPLETION HELPERS
     * ========================================================= */

    /**
     * Suggest online player names matching the prefix
     *
     * @param prefix   Prefix to match
     * @param self     The command sender (Player)
     * @param showSelf Whether to include the sender in suggestions
     */
    protected List<String> suggestPlayers(String prefix, Player self, boolean showSelf) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> showSelf || !player.equals(self))
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Suggest online player names matching the prefix
     *
     * @param prefix   Prefix to match
     * @param self     The command sender
     * @param showSelf Whether to include the sender in suggestions
     */
    protected List<String> suggestPlayers(String prefix, CommandSender self, boolean showSelf) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> showSelf || !name.equals(self.getName()))
                .filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Suggest all online player names matching the prefix
     */
    protected List<String> suggestPlayers(String prefix) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Suggest from a list of options matching the prefix
     */
    protected List<String> suggest(String prefix, String... options) {
        return Arrays.stream(options)
                .filter(option -> option.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Suggest from a collection of options matching the prefix
     */
    protected List<String> suggest(String prefix, Collection<String> options) {
        return options.stream()
                .filter(option -> option.toLowerCase().startsWith(prefix.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Get the permission required for this command
     */
    public @Nullable String getPermission() {
        return permission;
    }

    /**
     * Get the permission error message
     */
    public String getPermissionErrorMessage() {
        return permissionErrorMessage;
    }

    /**
     * Get the player-only error message
     */
    public String getPlayerOnlyErrorMessage() {
        return playerOnlyErrorMessage;
    }
}