package me.firedraong5.firesapi.cooldown;

import java.util.HashMap;
import java.util.UUID;


@SuppressWarnings("unused")
public class CooldownManager {
	private final HashMap<UUID, Long> cooldowns = new HashMap<>();

	private long cooldownTimeInMs;


	public static CooldownManager getInstance() {
		return new CooldownManager();
	}

	//	Set the cooldown time in milliseconds
	public void setCooldownTimeInMs(long cooldownTimeInMs) {
		this.cooldownTimeInMs = cooldownTimeInMs;
	}

	public void startCooldown(UUID playerUUID) {
		cooldowns.put(playerUUID, System.currentTimeMillis() + cooldownTimeInMs);
	}

	public boolean isCooldownActive(UUID playerUUID) {
		return cooldowns.containsKey(playerUUID) && cooldowns.get(playerUUID) > System.currentTimeMillis();
	}

	public long getRemainingCooldownTime(UUID playerUUID) {
		return isCooldownActive(playerUUID) ? cooldowns.get(playerUUID) - System.currentTimeMillis() : 0;
	}

}
