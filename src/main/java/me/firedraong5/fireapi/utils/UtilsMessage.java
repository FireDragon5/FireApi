package me.firedraong5.fireapi.utils;

import jdk.jfr.internal.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class UtilsMessage implements Listener {

	public static String onChat(String s) {
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


//	Correct message with a green check mark
	public static void correctMessage(Player player, String message) {
		player.sendMessage(onChat("&a&l[✔] &a" + message));
	}


//	Incorrect message with a red X
	public static void incorrectMessage(Player player, String message) {
		player.sendMessage(onChat("&c&l[✘] &c" + message));
	}

//	Error message
	public static void errorMessage(Player player, String message) {
		player.sendMessage(onChat("&c&l[!] &c" + message));
	}

//	Warning message
	public static void warningMessage(Player player, String message) {
		player.sendMessage(onChat("&e&l[!] &e" + message));
	}


//	Join message
	public static void joinMessage(Player player) {
		broadcastMessage("&a&l[+] &a" + player.getName() + " has joined the game");
	}

//	Leave message
	public static void leaveMessage(Player player) {
		broadcastMessage("&c&l[-] &c" + player.getName() + " has left the game");
	}










}
