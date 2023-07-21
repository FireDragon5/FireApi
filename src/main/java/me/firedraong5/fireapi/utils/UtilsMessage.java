package me.firedraong5.fireapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UtilsMessage implements Listener {

	@Contract("_ -> new")
	public static @NotNull String onChat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}


	/**
	 * Will send the player a message
	 * @param player - This is the player
	 * @param message - This is the message
	 */
	public static void playerSendMessage(@NotNull Player player, String message) {
		player.sendMessage(onChat(message));

	}


	/**
	 * Will send the player a message
	 * @param message - This is the message
	 */
	public static void broadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(onChat(message));
		}
	}


	/**
	 * Will send the player a message
	 * @param message - This is the message
	 */
	public static void correctMessage(@NotNull Player player, String message) {
		player.sendMessage(onChat("&8&l[&a✔&8&l] &f" + message));
	}


	/**
	 * Will send the player a message
	 * @param message - This is the message
	 *                (This message will be red)
	 *                for example, [✘] This is a message
	 */
	public static void incorrectMessage(@NotNull Player player, String message) {
		player.sendMessage(onChat("&8&l[&c✘&8&l] &f" + message));
	}

	/**
	 * Will send the player a message
	 * @param message - This is the message
	 *                for example, [!]This is a message
	 */
	public static void errorMessage(@NotNull Player player, String message) {
		player.sendMessage(onChat("&8&l[&c✘&8&l] &f" + message));
	}

	/**
	 * Will send the player a message
	 * @param message - This is the message
	 *                (This message will be red)
	 *                for example, [!] This is a message
	 */
	public static void warningMessage(@NotNull Player player, String message) {
		player.sendMessage(onChat("&8&l[&c✘&8&l] &f" + message));
	}


	/**
	 * Will send the player a message
	 * @param player - This is the player
	 * @param message - This is the message
	 */
	public void noPermissionMessage(@NotNull Player player, String message) {
		player.sendMessage(onChat(message));
	}

	/**
	 * Will send the player a message
	 * @param player - This is the player
	 * @param title - This is the title
	 * @param subtitle - This is the subtitle
	 */
	public static void titleMessage(@NotNull Player player, String title, String subtitle) {
		player.sendTitle(onChat(title), onChat(subtitle));
	}

	/**
	 * Will send the player a message
	 * @param player - This is the player
	 * @param title - This is the title
	 * @param subtitle - This is the subtitle
	 * @param fadeIn - This is the fade in time
	 * @param stay - This is the stay time
	 * @param fadeOut - This is the fade out time
	 */
	public static void titleMessage(@NotNull Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		player.sendTitle(onChat(title), onChat(subtitle), fadeIn, stay, fadeOut);
	}

	/**
	 * Will send the player a message
	 * @param player - This is the player
	 * @param message - This is the message
	 */
	public static void actionBarMessage(@NotNull Player player, String message) {
		player.sendActionBar(onChat(message));
	}
















}
