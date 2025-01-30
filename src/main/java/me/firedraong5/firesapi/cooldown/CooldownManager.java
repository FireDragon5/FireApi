package me.firedraong5.firesapi.cooldown;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class CooldownManager {
	private final HashMap<UUID, HashMap<String, Long>> cooldowns = new HashMap<>();
	private final HashMap<String, Long> cooldownTimes = new HashMap<>();
	private final HashMap<String, String> cooldownByPassPermissions = new HashMap<>();

	private String singlePermission;
	private boolean useSinglePermission;


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
	public void setCooldownTime(String cooldownName, long cooldownTimeInMil) {

		cooldownTimes.put(cooldownName, cooldownTimeInMil);
	}

	public void setCooldownTimeFromFile(String cooldownName, String cooldownTimeInString) {

		long cooldownTime;

//		When the user puts 3D or 3H in the config file convert the time to milliseconds
		if (cooldownTimeInString.contains("D")) {
			cooldownTime = Long.parseLong(cooldownTimeInString.replace("D", "")) * 86400000;
		} else if (cooldownTimeInString.contains("H")) {
			cooldownTime = Long.parseLong(cooldownTimeInString.replace("H", "")) * 3600000;
		} else if (cooldownTimeInString.contains("M")) {
			cooldownTime = Long.parseLong(cooldownTimeInString.replace("M", "")) * 60000;
		} else if (cooldownTimeInString.contains("S")) {
			cooldownTime = Long.parseLong(cooldownTimeInString.replace("S", "")) * 1000;
		}else {
			cooldownTime = Long.parseLong(cooldownTimeInString);
		}


		cooldownTimes.put(cooldownName, cooldownTime);
	}

	public void setCooldownByPassPermission(String permission, String command) {
		cooldownByPassPermissions.put(permission, command);
	}

	public void setSinglePermission(String permission, boolean useSinglePermission) {
		this.singlePermission = permission;
		this.useSinglePermission = useSinglePermission;
	}

	public void startCooldown(Player player, String command) {
		UUID playerUUID = player.getUniqueId();
		String permission = useSinglePermission ? singlePermission : cooldownByPassPermissions.get(command);

		if (permission != null && (player.hasPermission(permission) || player.isOp())) {
			return;
		}

		if (!cooldowns.containsKey(playerUUID)) {
			cooldowns.put(playerUUID, new HashMap<>());
		}

		cooldowns.get(playerUUID).put(command, System.currentTimeMillis() + cooldownTimes.get(command));
	}

	public boolean isCooldownActive(Player player, String command) {
		UUID playerUUID = player.getUniqueId();
		String permission = useSinglePermission ? singlePermission : cooldownByPassPermissions.get(command);

		if (permission != null && (player.hasPermission(permission) || player.isOp())) {
			return true;
		}

//		remove the player if the cooldown is >= 0
		if (cooldowns.containsKey(playerUUID) && cooldowns.get(playerUUID).containsKey(command) && cooldowns.get(playerUUID).get(command) <= System.currentTimeMillis()) {
			cooldowns.get(playerUUID).remove(command);
		}


		return !cooldowns.containsKey(playerUUID) || !cooldowns.get(playerUUID).containsKey(command) || cooldowns.get(playerUUID).get(command) <= System.currentTimeMillis();
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
	public void sendCooldownMessage(Player player, String command) {
		UUID playerUUID = player.getUniqueId();
		double remainingTime = getRemainingCooldownTime(player, command) / 1000.0;

		if (!cooldowns.containsKey(playerUUID) || !cooldowns.get(playerUUID).containsKey(command)) {
			return;
		}

		if (remainingTime < 60) {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTime) + "&e seconds for &c" + command + "&e.");
		} else if (remainingTime < 3600) {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTime / 60) + "&e minutes for &c" + command + "&e.");
		} else if (remainingTime < 86400) {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTime / 3600) + "&e hours for &c" + command + "&e.");
		} else {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTime / 86400) + "&e days for &c" + command + "&e.");
		}
	}

	//	Show all the player cooldowns
	public void showCooldowns(@NotNull Player player) {
		UUID playerUUID = player.getUniqueId();

		if (cooldowns.containsKey(playerUUID)) {
			UtilsMessage.sendMessage(player, "&eYou are on cooldown for the following commands:");
			UtilsMessage.sendMessage(player, "&e-------------------------------------");
			for (String command : cooldowns.get(playerUUID).keySet()) {
				long remainingTime = cooldowns.get(playerUUID).get(command) - System.currentTimeMillis();
				double remainingTimeInSeconds = remainingTime / 1000.0;

				if (remainingTimeInSeconds < 60) {
					UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTimeInSeconds) + "&e seconds for &c" + command + "&e.");
				} else if (remainingTimeInSeconds < 3600) {
					UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTimeInSeconds / 60) + "&e minutes for &c" + command + "&e.");
				} else if (remainingTimeInSeconds < 86400) {
					UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTimeInSeconds / 3600) + "&e hours for &c" + command + "&e.");
				} else {
					UtilsMessage.sendMessage(player, "&eYou are on cooldown for another &c" + Math.round(remainingTimeInSeconds / 86400) + "&e days for &c" + command + "&e.");
				}
			}
			UtilsMessage.sendMessage(player, "&e-------------------------------------");
		}else {
			UtilsMessage.sendMessage(player, "&cNo cooldowns active");

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