package me.firedraong5.firesapi.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

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


}
