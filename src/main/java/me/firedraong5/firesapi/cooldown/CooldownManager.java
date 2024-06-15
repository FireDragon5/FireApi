package me.firedraong5.firesapi.cooldown;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class CooldownManager {
	private final HashMap<UUID, HashMap<String, Long>> cooldowns = new HashMap<>();
	private final HashMap<String, Long> cooldownTimesInMs = new HashMap<>();
	private final HashMap<String, String> cooldownByPassPermissions = new HashMap<>();

	private static CooldownManager instance;

	private CooldownManager() {
	}

	public static CooldownManager getInstance() {
		if (instance == null) {
			instance = new CooldownManager();
		}
		return instance;
	}

	// Set the cooldown time in milliseconds
	public void setCooldownTimeInMs(String cooldownName, long cooldownTimeInMs) {
		cooldownTimesInMs.put(cooldownName, cooldownTimeInMs);
	}

	public void setCooldownByPassPermission(String permission) {
		cooldownByPassPermissions.put(permission, permission);
	}

	public void startCooldown(Player player, String command) {
		UUID playerUUID = player.getUniqueId();
		String permission = cooldownByPassPermissions.get(command);

		if (player.hasPermission(permission) || player.isOp()) {
			return;
		}

		if (!cooldowns.containsKey(playerUUID)) {
			cooldowns.put(playerUUID, new HashMap<>());
		}

		cooldowns.get(playerUUID).put(command, System.currentTimeMillis() + cooldownTimesInMs.get(command));
	}

	public boolean isCooldownActive(Player player, String command) {
		UUID playerUUID = player.getUniqueId();
		String permission = cooldownByPassPermissions.get(command);

		if (player.hasPermission(permission) || player.isOp()) {
			return false;
		}

		return cooldowns.containsKey(playerUUID) && cooldowns.get(playerUUID).containsKey(command) && cooldowns.get(playerUUID).get(command) > System.currentTimeMillis();
	}


	public long getRemainingCooldownTime(Player player, String command) {
		UUID playerUUID = player.getUniqueId();
		String permission = cooldownByPassPermissions.get(command);

		if (player.hasPermission(permission) || player.isOp()) {
			return 0;
		}

		if (cooldowns.containsKey(playerUUID) && cooldowns.get(playerUUID).containsKey(command)) {
			return cooldowns.get(playerUUID).get(command) - System.currentTimeMillis();
		}

		return 0;
	}

	//	Get the remaining cooldown time
	public long getRemainingCooldownTime(Player player) {
		UUID playerUUID = player.getUniqueId();
		long remainingTime = 0;

		if (cooldowns.containsKey(playerUUID)) {
			for (String command : cooldowns.get(playerUUID).keySet()) {
				long time = cooldowns.get(playerUUID).get(command) - System.currentTimeMillis();
				if (time > remainingTime) {
					remainingTime = time;
				}
			}
		}

		return remainingTime;
	}

	//	Cooldown message
	public void sendCooldownMessage(Player player) {
//		if cooldown is less then a min then show seconds else show min or hour or day
		long remainingTime = getRemainingCooldownTime(player);
		if (remainingTime < 60000) {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + remainingTime / 1000 + "&e seconds.");
		} else if (remainingTime < 3600000) {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + remainingTime / 60000 + "&e minutes.");
		} else if (remainingTime < 86400000) {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + remainingTime / 3600000 + "&e hours.");
		} else {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + remainingTime / 86400000 + "&e days.");
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