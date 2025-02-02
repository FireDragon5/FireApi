package me.firedraong5.firesapi.cooldown;


import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class CooldownManager {
	private final HashMap<UUID, HashMap<String, Long>> commandCooldowns = new HashMap<>();
	private final HashMap<UUID, HashMap<String, Long>> featureCooldowns = new HashMap<>();
	private final HashMap<String, Long> cooldownTimes = new HashMap<>();
	private final HashMap<String, String> bypassPermissions = new HashMap<>();

	private static CooldownManager instance;

	private CooldownManager() {
	}

	public static CooldownManager getInstance() {
		if (instance == null) {
			instance = new CooldownManager();
		}
		return instance;
	}

	public void setCooldownTime(String name, long cooldownTimeInMinutes) {
		cooldownTimes.put(name, cooldownTimeInMinutes * 1000);
	}

	public void setCooldownBypassPermission(String permission, String name) {
		bypassPermissions.put(name, permission);
	}

	public void startCommandCooldown(Player player, String command) {
		startCooldown(player, command, commandCooldowns);
	}

	public void startFeatureCooldown(Player player, String feature) {
		startCooldown(player, feature, featureCooldowns);
	}

	private void startCooldown(Player player, String name, HashMap<UUID, HashMap<String, Long>> cooldownMap) {
		if (hasBypassPermission(player, name)) return;
		cooldownMap.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(name, System.currentTimeMillis() + cooldownTimes.getOrDefault(name, 0L));
	}

	public boolean isCommandCooldownActive(Player player, String command) {
		return isCooldownActive(player, command, commandCooldowns);
	}

	public boolean isFeatureCooldownActive(Player player, String feature) {
		return isCooldownActive(player, feature, featureCooldowns);
	}

	private boolean isCooldownActive(Player player, String name, HashMap<UUID, HashMap<String, Long>> cooldownMap) {
		if (hasBypassPermission(player, name)) return false;
		UUID playerUUID = player.getUniqueId();
		cooldownMap.getOrDefault(playerUUID, new HashMap<>()).entrySet().removeIf(entry -> entry.getValue() < System.currentTimeMillis());
		return cooldownMap.getOrDefault(playerUUID, new HashMap<>()).containsKey(name);
	}

	public long getRemainingCommandCooldown(Player player, String command) {
		return getRemainingCooldown(player, command, commandCooldowns);
	}

	public long getRemainingFeatureCooldown(Player player, String feature) {
		return getRemainingCooldown(player, feature, featureCooldowns);
	}

	private long getRemainingCooldown(Player player, String name, HashMap<UUID, HashMap<String, Long>> cooldownMap) {
		if (hasBypassPermission(player, name)) return 0;
		return cooldownMap.getOrDefault(player.getUniqueId(), new HashMap<>()).getOrDefault(name, 0L) - System.currentTimeMillis();
	}

	public void removeCommandCooldown(Player player) {
		commandCooldowns.remove(player.getUniqueId());
	}

	public void removeFeatureCooldown(Player player) {
		featureCooldowns.remove(player.getUniqueId());
	}

	public void sendCooldownMessage(Player player, String name) {
		long remainingTime = getRemainingCommandCooldown(player, name);
		if (remainingTime > 0) {
			UtilsMessage.sendMessage(player, "&cYou must wait &e" + remainingTime / 1000 + " &cseconds before using this command again.");
		}
	}

	private boolean hasBypassPermission(Player player, String name) {
		String permission = bypassPermissions.get(name);
		return permission != null && (player.hasPermission(permission) || player.isOp());
	}

}
