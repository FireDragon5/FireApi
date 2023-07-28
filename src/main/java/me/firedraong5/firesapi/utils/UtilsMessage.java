package me.firedraong5.firesapi.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class UtilsMessage implements Listener {


	/**
	 *
	 * @param s String to translate
	 * @return String translated
	 */
	public static String onChat(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
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
		player.sendTitle(onChat(title), onChat(subtitle), fadeIn, stay, fadeOut);
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
		player.sendTitle(onChat(title), onChat(subtitle), 10, 70, 20);
	}


//	Action bar message
	/**
	 * Action bar message to send to the player
	 * @param player Player to send the message
	 * @param message Message to send
	 */
	public static void actionBarMessage(Player player, String message){
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
				TextComponent.fromLegacyText(onChat(message)));

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










}
