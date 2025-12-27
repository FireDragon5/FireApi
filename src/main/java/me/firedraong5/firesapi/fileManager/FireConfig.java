package me.firedraong5.firesapi.fileManager;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Base class for managing YAML configuration files.
 * Features:
 * - Auto-create from resources if missing
 * - Type-safe getters with defaults
 * - Easy save/reload
 * - Comment preservation
 * Usage:
 * 1. Extend this class
 * 2. Call super(plugin, "filename.yml") in constructor
 * 3. Override loadValues() to cache your config values
 * 4. Call load() to initialize
 */
@SuppressWarnings("unused")
public abstract class FireConfig {

    protected final JavaPlugin plugin;
    protected final String fileName;
    protected File file;
    protected YamlConfiguration config;

    /**
     * Create a new config file manager
     *
     * @param plugin   Your plugin instance
     * @param fileName Name of the YAML file (e.g., "settings.yml")
     */
    public FireConfig(@NotNull JavaPlugin plugin, @NotNull String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
    }

    /**
     * Load the config file from disk.
     * If it doesn't exist, it will be created from your plugin's resources.
     */
    public void load() {
        // Ensure data folder exists
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        // Create file reference
        file = new File(plugin.getDataFolder(), fileName);

        // If file doesn't exist, save default from resources
        if (!file.exists()) {
            try {
                plugin.saveResource(fileName, false);
                plugin.getLogger().info("Created default " + fileName);
            } catch (Exception e) {
                plugin.getLogger().warning("Could not create " + fileName + " from resources. Creating empty file.");
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    plugin.getLogger().severe("Failed to create " + fileName + ": " + ex.getMessage());
                }
            }
        }

        // Load YAML
        config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);

        try {
            config.load(file);
            loadValues();
            plugin.getLogger().info("Loaded " + fileName);
        } catch (Exception e) {
            plugin.getLogger().severe("Error loading " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Override this method to load your config values into fields.
     * Called automatically after the file is loaded.
     */
    protected abstract void loadValues();

    /**
     * Save the config to disk
     */
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Error saving " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reload the config from disk and re-load values
     */
    public void reload() {
        load();
    }

    // ========================================
    // TYPE-SAFE GETTERS WITH DEFAULTS
    // ========================================

    protected String getString(@NotNull String path, @NotNull String defaultValue) {
        return config.getString(path, defaultValue);
    }

    protected String getString(@NotNull String path) {
        return config.getString(path);
    }

    protected int getInt(@NotNull String path, int defaultValue) {
        return config.getInt(path, defaultValue);
    }

    protected double getDouble(@NotNull String path, double defaultValue) {
        return config.getDouble(path, defaultValue);
    }

    protected boolean getBoolean(@NotNull String path, boolean defaultValue) {
        return config.getBoolean(path, defaultValue);
    }

    protected List<String> getStringList(@NotNull String path) {
        return config.getStringList(path);
    }

    protected @Nullable Location getLocation(@NotNull String path) {
        return config.getLocation(path);
    }

    protected long getLong(@NotNull String path, long defaultValue) {
        return config.getLong(path, defaultValue);
    }

    // ========================================
    // SETTERS
    // ========================================

    protected void set(@NotNull String path, @Nullable Object value) {
        config.set(path, value);
    }

    protected void setAndSave(@NotNull String path, @Nullable Object value) {
        config.set(path, value);
        save();
    }

    // ========================================
    // UTILITY
    // ========================================

    /**
     * Check if a path exists in the config
     */
    protected boolean contains(@NotNull String path) {
        return config.contains(path);
    }

    /**
     * Get the raw YamlConfiguration (for advanced usage)
     */
    public YamlConfiguration getConfig() {
        return config;
    }

    /**
     * Get the file reference
     */
    public File getFile() {
        return file;
    }

    /**
     * Get the file name
     */
    public String getFileName() {
        return fileName;
    }
}