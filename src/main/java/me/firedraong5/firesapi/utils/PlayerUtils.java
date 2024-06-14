package me.firedraong5.firesapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class PlayerUtils {

	/**
	 * Teleport a player to a location
	 *
	 * @param player Player to teleport
	 * @param location Location to teleport the player
	 */
	public static void teleportPlayer(Player player, Location location) {
		player.teleport(location);
	}


	/**
	 * Get all the players from the server
	 *
	 * @return All the players from the server
	 */
	public static Player getAllPlayers() {

		for (Player player : Bukkit.getOnlinePlayers()) {
			return player;
		}

		return null;
	}


	//	has permission if not send a no permission message
	public static boolean hasPermission(Player player, String permission) {
		if (!player.hasPermission(permission)) {
			UtilsMessage.errorMessage(player, "&7You don't have the permission to do that!");
			return false;
		}
		return true;
	}


}
