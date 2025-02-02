package me.firedraong5.firesapi.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;


@SuppressWarnings("unused")
public class UtilsMessage implements Listener {

	static Map<UUID, List<String>> delayMessage = new HashMap<>();
	private static final Map<String, Object> placeholders = new HashMap<>();
	private static String prefix;

	public static void addPlaceholder(String placeholder, Object value) {
		placeholders.put(placeholder, value);
	}

	//	Method that will add a prefix to all the sendMessage
	public void setPrefix(String prefix) {
		UtilsMessage.prefix = prefix;
	}

	private static String getPrefix() {
		return prefix;
	}


	public static String replacePlaceholders(String message, Player player) {
		addPlaceholder("%player%", player.displayName());
		for (Map.Entry<String, Object> entry : placeholders.entrySet()) {
			message = message.replace(entry.getKey(), entry.getValue().toString());
		}
		return message;
	}

	/**
	 * Send a message to the player
	 * @param s String to translate
	 * @return String translated
	 */
	@Contract("_ -> new")
	public static @NotNull String onChat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}


	/**
	 * Translate a list of strings
	 * @param s List of strings to translate
	 * @return List of strings translated
	 */
	public static @NotNull List<String> onChat(List<String> s) {
		List<String> list = new ArrayList<>();
		for (String string : s) {
			list.add(onChat(string));
		}
		return list;
	}


/*

	Normal player messages

 */


	/**
	 *
	 * @param player Player to send the message
	 * @param message Message to send
	 */
	public static void sendMessage(Player player, String message) {
		player.sendMessage(onChat(message));
	}

//	info message

	/**
	 * This will send the player a message with the prefix &8[&b?&8]
	 *
	 * @param player Player to send the message
	 * @param message Message to send
	 */
	public static void infoMessage(Player player, String message) {
		player.sendMessage(onChat("&8[&b?&8] &7" + message));
	}

	//	Message list send to the player
	public static void sendMessage(Player player, List<String> messages) {
		for (String message : messages) {
			sendMessage(player, message);
		}
	}

	//	Offline player message this message will send to the player as soon as they join the server
	public static void offlineMessage(Player player, String message) {
		if (player.isOnline()) {
			player.sendMessage(onChat(message));
		} else {
			if (delayMessage.containsKey(player.getUniqueId())) {
				delayMessage.get(player.getUniqueId()).add(message);
			} else {
				List<String> list = new ArrayList<>();
				list.add(message);
				delayMessage.put(player.getUniqueId(), list);
			}
		}

	}

	//	Check pending messages
	public static void checkPendingMessages() {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			UUID playerId = onlinePlayer.getUniqueId();
			if (delayMessage.containsKey(playerId)) {
				List<String> messages = delayMessage.get(playerId);
				for (String message : messages) {
					UtilsMessage.sendMessage(onlinePlayer, message);
				}
				// Clear the messages for the player
				delayMessage.remove(playerId);
			}
		}
	}


	/**
	 * Send a message to the command sender
	 *
	 * @param sender CommandSender to send the message
	 * @param message Message to send
	 */
	public static void sendMessage(CommandSender sender, String message, boolean prefix) {
		if (prefix) {
			sender.sendMessage(getPrefix() + onChat(message));
		} else {
			sender.sendMessage(onChat(message));
		}
	}

	// Overloaded method with default prefix value
	public static void sendMessage(CommandSender sender, String message) {
		sendMessage(sender, message, false);
	}


	/**
	 *
	 * @param message Message to broadcast
	 */
	public static void broadcastMessage(String message) {
		for (Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
			player.sendMessage(onChat(message));
		}
	}



	/**
	 * This will send the player a message with the prefix &8[&a✔&8]
	 * @param player Player to send the message
	 * @param message Message to send
	 */
	public static void correctMessage(Player player, String message) {
		player.sendMessage(onChat("&8[&a✔&8] &7" + message));
	}


	/**
	 * This will send the player a message with the prefix &8[&c✖&8]
	 * @param player Player to send the message
	 * @param message Message to send
	 */
	public static void errorMessage(Player player, String message) {
		player.sendMessage(onChat("&8[&c✖&8] &7" + message));
	}




	/**
	 * This will send the player a message with the prefix &8[&e!&8]
	 * @param player Player to send the message
	 * @param message Message to send
	 */
	public static void warningMessage(Player player, String message) {
		player.sendMessage(onChat("&8[&e!&8] &7" + message));
	}


	/**
	 * This will send the player a message with the prefix
	 * <br>&8[&c✖&8] &7You don't have the permission &c" + permission + " &7to do that!
	 * @param player Player to send the message
	 * @param permission Permission that the player doesn't have
	 */
	public static void noPermissionMessage(@Nonnull Player player, String permission, boolean showPermissionInMessage) {

		if (showPermissionInMessage) {
			UtilsMessage.errorMessage(player, "&7You don't have the permission &c" + permission + " &7to do that!");
		} else {
			UtilsMessage.errorMessage(player, "&7You don't have the permission to do that!");
		}
	}

	public static void noPermissionMessage(@Nonnull Player player, String permission) {
		UtilsMessage.errorMessage(player, "&7You don't have the permission &c" + permission + " &7to do that!");
	}

	/**
	 * This will send the player a custom message if they don't have permission
	 *
	 * @param player     - Player to send the message
	 * @param permission - Permission that the player doesn't have
	 *                   <br>Use %permission% in the message to include the permission
	 * @param message    - Custom message
	 */
	public static void noPermissionMessage(@Nonnull Player player, @Nullable String permission, String message) {

//		Include the permission in the message
		if (permission != null) {
			message = message.replace("%permission%", permission);
		}

		UtilsMessage.sendMessage(player, message);
	}


	/**
	 * Title message to send to the player
	 * @param player Player to send the message
	 * @param title Title to send
	 * @param subtitle Subtitle to send
	 * @param fadeIn Fade in time - 20 ticks = 1 second
	 * @param stay Stay time - 20 ticks = 1 second
	 * @param fadeOut Fade out time - 20 ticks = 1 second
	 */
	public static void titleMessage(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut){
		player.showTitle(Title.title(
				Component.text(onChat(title)),
				Component.text(onChat(subtitle)),
				Title.Times.times(Duration.ofMillis(fadeIn * 50L), Duration.ofMillis(stay * 50L), Duration.ofMillis(fadeOut * 50L))
		));
	}

	/**
	 * Title message to send to the player
	 * fadeIn - 10 ticks = .5 second
	 * stay - 70 ticks = 3.5 seconds
	 * fadeOut - 20 ticks = 1 second
	 * @param player Player to send the message
	 * @param title Title to send
	 * @param subtitle Subtitle to send
	 */
	public static void titleMessage(Player player, String title, String subtitle){
		player.showTitle(Title.title(
				Component.text(onChat(title)),
				Component.text(onChat(subtitle)),
				Title.Times.times(Duration.ofMillis(10 * 50L), Duration.ofMillis(70 * 50L), Duration.ofMillis(20 * 50L))
		));
	}

//	Action bar message
	/**
	 * Action bar message to send to the player
	 * @param player Player to send the message
	 * @param message Message to send
	 */
	public static void actionBarMessage(Player player, String message) {
		player.sendActionBar(Component.text(onChat(message)));
	}

/*

	Server Chat Messages

 */


	/**
	 * Clickable message to send to the player
	 * @param player Player to send the message
	 * @param message Message to send
	 * @param command Command to run when clicked
	 */
	public static void clickableMessage(Player player, String message, String command){

		player.sendMessage(String.valueOf(Component.text(onChat(message))
				.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, command)))

		);

	}


	/**
	 * Hover text to send to the player
	 *
	 * @param player    Player to send the message
	 * @param message   Message to send
	 * @param hoverText Hover text to send
	 */
	public static void hoverText(Player player, String message, String hoverText) {
		player.sendMessage(String.valueOf(Component.text(onChat(message))
				.hoverEvent(HoverEvent.showText(Component.text(onChat(hoverText))))));
	}

	/**
	 * @param player Player to send the message
	 * @param message Message to send
	 * @param replacements Replacements to make in the message
	 */
//	Text Replace method
	public static void sendMessage(Player player, String message, Map<String, String> replacements) {
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			message = message.replace(entry.getKey(), entry.getValue());
		}
		player.sendMessage(onChat(message));
	}

	//	Send a message to the console
	public static void sendMessageConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(onChat(message));
	}












}
