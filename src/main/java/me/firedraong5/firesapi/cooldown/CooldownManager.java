package me.firedraong5.firesapi.cooldown;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A central cooldown manager for both commands and features.
 * <p>
 * - Command cooldowns: For plugin commands (/warp, /heal, etc.)
 * - Feature cooldowns: For abilities, kits, or any non-command usage.
 */
public class CooldownManager {

    // Separate storage for command and feature cooldowns
    private final Map<UUID, Map<String, Long>> commandCooldowns = new HashMap<>();
    private final Map<UUID, Map<String, Long>> featureCooldowns = new HashMap<>();

    // Stores default cooldown durations (ms) for each cooldown name
    private final Map<String, Long> cooldownDurations = new HashMap<>();

    // Stores bypass permissions per cooldown name
    private final Map<String, String> bypassPermissions = new HashMap<>();

    private static CooldownManager instance;

    private CooldownManager() {}

    public static CooldownManager getInstance() {
        if (instance == null) {
            instance = new CooldownManager();
        }
        return instance;
    }

    /* ===========================
       Setup Methods
    ============================ */

    public void setCooldownTimeInMinutes(String name, long minutes) {
        cooldownDurations.put(name.toLowerCase(), minutes * 60_000);
    }

    public void setCooldownTimeInSeconds(String name, long seconds) {
        cooldownDurations.put(name.toLowerCase(), seconds * 1_000);
    }

    public void setCooldownBypassPermission(String name, String permission) {
        bypassPermissions.put(name.toLowerCase(), permission);
    }

    /* ===========================
       Starting Cooldowns
    ============================ */

    public void startCommandCooldown(Player player, String command) {
        startCooldown(player, command, commandCooldowns);
    }

    public void startFeatureCooldown(Player player, String feature) {
        startCooldown(player, feature, featureCooldowns);
    }

    private void startCooldown(Player player, String name, Map<UUID, Map<String, Long>> storage) {
        if (hasBypassPermission(player, name)) return;

        long duration = cooldownDurations.getOrDefault(name.toLowerCase(), 0L);
        if (duration <= 0) return; // No cooldown set

        storage.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(name.toLowerCase(), System.currentTimeMillis() + duration);
    }

    /* ===========================
       Checking Cooldowns
    ============================ */

    public boolean isCommandCooldownActive(Player player, String command) {
        return isCooldownActive(player, command, commandCooldowns);
    }

    public boolean isFeatureCooldownActive(Player player, String feature) {
        return isCooldownActive(player, feature, featureCooldowns);
    }

    private boolean isCooldownActive(Player player, String name, Map<UUID, Map<String, Long>> storage) {
        if (hasBypassPermission(player, name)) return false;
        cleanupExpiredCooldowns(player.getUniqueId(), storage);
        return storage.getOrDefault(player.getUniqueId(), Collections.emptyMap())
                .containsKey(name.toLowerCase());
    }

    /* ===========================
       Getting Remaining Time
    ============================ */

    public long getRemainingCommandCooldown(Player player, String command) {
        return getRemainingCooldown(player, command, commandCooldowns);
    }

    public long getRemainingFeatureCooldown(Player player, String feature) {
        return getRemainingCooldown(player, feature, featureCooldowns);
    }

    private long getRemainingCooldown(Player player, String name, Map<UUID, Map<String, Long>> storage) {
        if (hasBypassPermission(player, name)) return 0;

        cleanupExpiredCooldowns(player.getUniqueId(), storage);

        return Math.max(
                0,
                storage.getOrDefault(player.getUniqueId(), Collections.emptyMap())
                        .getOrDefault(name.toLowerCase(), 0L) - System.currentTimeMillis()
        );
    }

    /* ===========================
       Removing Cooldowns
    ============================ */

    public void clearCommandCooldown(Player player, String command) {
        removeCooldown(player, command, commandCooldowns);
    }

    public void clearFeatureCooldown(Player player, String feature) {
        removeCooldown(player, feature, featureCooldowns);
    }

    public void clearAllCommandCooldowns(Player player) {
        commandCooldowns.remove(player.getUniqueId());
    }

    public void clearAllFeatureCooldowns(Player player) {
        featureCooldowns.remove(player.getUniqueId());
    }

    private void removeCooldown(Player player, String name, Map<UUID, Map<String, Long>> storage) {
        Map<String, Long> map = storage.get(player.getUniqueId());
        if (map != null) map.remove(name.toLowerCase());
    }

    /* ===========================
       Messages
    ============================ */

    public void sendCooldownCommandMessage(Player player, String name) {
        long remaining = getRemainingCommandCooldown(player, name);
        if (remaining > 0) {
            UtilsMessage.sendMessage(player, "&cYou must wait &e" + formatTime(remaining) + " &cto use this again.");
        }
    }

    public void sendCooldownCommandMessage(Player player, String name, String customMessage) {
        long remaining = getRemainingCommandCooldown(player, name);
        if (remaining > 0) {
            UtilsMessage.sendMessage(player, customMessage.replace("%time%", formatTime(remaining)));
        }
    }

    public void sendCooldownMessage(Player player, String name) {
                long remaining = getRemainingCommandCooldown(player, name);
        if (remaining > 0) {
            UtilsMessage.sendMessage(player, "&cYou must wait &e" + formatTime(remaining) + " &cto use this again.");
        }
    }

    public void sendCooldownMessage(Player player, String name, String customMessage) {
        long remaining = getRemainingCommandCooldown(player, name);
        if (remaining > 0) {
            UtilsMessage.sendMessage(player, customMessage.replace("%time%", formatTime(remaining)));
        }
    }

    public void sendFeatureCooldownMessage(Player player, String name) {
        long remaining = getRemainingFeatureCooldown(player, name);
        if (remaining > 0) {
            UtilsMessage.sendMessage(player, "&cYou must wait &e" + formatTime(remaining) + " &cto use this again.");
        }
    }

    public void sendFeatureCooldownMessage(Player player, String name, String customMessage) {
        long remaining = getRemainingFeatureCooldown(player, name);
        if (remaining > 0) {
            UtilsMessage.sendMessage(player, customMessage.replace("%time%", formatTime(remaining)));
        }
    }

    /* ===========================
       Helpers
    ============================ */

    private void cleanupExpiredCooldowns(UUID uuid, Map<UUID, Map<String, Long>> storage) {
        Map<String, Long> map = storage.get(uuid);
        if (map != null) {
            map.entrySet().removeIf(entry -> entry.getValue() < System.currentTimeMillis());
            if (map.isEmpty()) storage.remove(uuid);
        }
    }

    private boolean hasBypassPermission(Player player, String name) {
        String permission = bypassPermissions.get(name.toLowerCase());
        return permission != null && (player.hasPermission(permission) || player.isOp());
    }

    /**
     * Formats milliseconds into a human-readable string (e.g., "2d 3h", "15m 10s").
     */
    public String formatTime(long millis) {
        long seconds = millis / 1000;
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;
        long sec = seconds % 60;

        if (days > 0) return days + "d " + hours + "h";
        if (hours > 0) return hours + "h " + minutes + "m";
        if (minutes > 0) return minutes + "m " + sec + "s";
        return sec + "s";
    }
}
