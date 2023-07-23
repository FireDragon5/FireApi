package me.firedraong5.firesapi.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class UtilsMessage implements Listener {


	/**
	 *
	 * @param s String to translate
	 * @return String translated
	 */
	@Contract("_ -> new")
	public static @NotNull String onChat(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}




/*

	Normal messages

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

//	No premession message
	/**
	 * This will send the player a message with the prefix
	 * <br>&8[&c✖&8] &7You don't have the permission &c" + prems + " &7to do that!
	 * @param player Player to send the message
	 * @param prems Premission that the player doesn't have
	 */
	public static void noPremsMessage(Player player, String prems){
		player.sendMessage(onChat("&8[&c✖&8] &7You don't have the permission &c" + prems + " &7to do that!"));
	}









}
