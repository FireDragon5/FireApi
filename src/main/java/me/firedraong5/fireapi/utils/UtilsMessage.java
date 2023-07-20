package me.firedraong5.fireapi.utils;

import jdk.jfr.internal.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UtilsMessage {

	private static String onChat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}



//	Create a player send message
	public static void playerSendMessage(Player player, String message) {
		player.sendMessage(onChat(message));

	}


//	Broadcast a message to all players
	public static void broadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(onChat(message));
		}
	}


//	Correct message with a







}
