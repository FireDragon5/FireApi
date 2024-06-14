package me.firedraong5.firesapi.cooldown;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class CooldownManager {
	private final HashMap<UUID, Long> cooldowns = new HashMap<>();
	private long cooldownTimeInMs;

	private static CooldownManager instance;
	private String cooldownByPassPermission;

	private CooldownManager() {
	}

	public static CooldownManager getInstance() {
		if (instance == null) {
			instance = new CooldownManager();
		}
		return instance;
	}

	// Set the cooldown time in milliseconds
	public void setCooldownTimeInMs(long cooldownTimeInMs) {
		this.cooldownTimeInMs = cooldownTimeInMs;
	}

	public void setCooldownByPassPermission(String permission) {
		this.cooldownByPassPermission = permission;
	}

	public void startCooldown(Player player) {
		UUID playerUUID = player.getUniqueId();

		if (player.hasPermission(cooldownByPassPermission) || player.isOp()) {
			return;
		}

		cooldowns.put(playerUUID, System.currentTimeMillis() + cooldownTimeInMs);
	}

	public boolean isCooldownActive(Player player) {
		UUID playerUUID = player.getUniqueId();

		if (player.hasPermission(cooldownByPassPermission) || player.isOp()) {
			return false;
		}
		return cooldowns.containsKey(playerUUID) && cooldowns.get(playerUUID) > System.currentTimeMillis();
	}


	public long getRemainingCooldownTime(Player player) {
		UUID playerUUID = player.getUniqueId();

		return isCooldownActive(player) ? cooldowns.get(playerUUID) - System.currentTimeMillis() : 0;
	}

	//	Cooldown message
	public void sendCooldownMessage(Player player) {
//		if cooldown is less then a min then show seconds else show min or hour or day
		long remainingTime = getRemainingCooldownTime(player);
		if (remainingTime < 60000) {
			player.sendMessage("You are on cooldown for another " + remainingTime / 1000 + " seconds.");
		} else if (remainingTime < 3600000) {
			player.sendMessage("You are on cooldown for another " + remainingTime / 60000 + " minutes.");
		} else if (remainingTime < 86400000) {
			player.sendMessage("You are on cooldown for another " + remainingTime / 3600000 + " hours.");
		} else {
			player.sendMessage("You are on cooldown for another " + remainingTime / 86400000 + " days.");
		}
	}

	public void removeCooldown(Player player) {
		UUID playerUUID = player.getUniqueId();

		cooldowns.remove(playerUUID);
	}

	public void removeCooldown(UUID playerUUID) {
		cooldowns.remove(playerUUID);
	}

	public void removeCooldown(CommandSender sender) {
		if (sender instanceof Player) {
			removeCooldown((Player) sender);
		}
	}

	public void clearCooldowns() {
		cooldowns.clear();
	}
}