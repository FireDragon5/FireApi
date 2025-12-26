package me.firedraong5.firesapi.tabList;

import me.firedraong5.firesapi.utils.UtilsMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for managing player tab list header and footer.
 *
 * Supports:
 * - Per-player header/footer.
 * - Global header/footer for all online players.
 * - String-based (with & color codes) or Component-based APIs.
 */
@SuppressWarnings("unused")
public class TabList {

    private static @Nullable Component globalHeader = null;
    private static @Nullable Component globalFooter = null;

    /* ==============================
     *   PER-PLAYER TABLIST (Component)
     * ============================== */

    /**
     * Set the tab list header and footer for a single player.
     *
     * @param player Player to set the header and footer
     * @param header Header of the tab list
     * @param footer Footer of the tab list
     */
    public static void setTabListHeaderAndFooter(@NotNull Player player,
                                                 @Nullable Component header,
                                                 @Nullable Component footer) {
        player.sendPlayerListHeaderAndFooter(
                header != null ? header : Component.empty(),
                footer != null ? footer : Component.empty()
        );
    }

    /**
     * Set the tab list header for a single player.
     *
     * @param player Player to set the header
     * @param header Header of the tab list
     */
    public static void setTabListHeader(@NotNull Player player, @Nullable Component header) {
        player.sendPlayerListHeader(header != null ? header : Component.empty());
    }

    /**
     * Set the tab list footer for a single player.
     *
     * @param player Player to set the footer
     * @param footer Footer of the tab list
     */
    public static void setTabListFooter(@NotNull Player player, @Nullable Component footer) {
        player.sendPlayerListFooter(footer != null ? footer : Component.empty());
    }

    /* ==============================
     *   PER-PLAYER TABLIST (String)
     * ============================== */

    /**
     * Set the tab list header and footer for a single player using & color codes.
     *
     * @param player Player to set
     * @param header Header text (supports & color codes, null = empty)
     * @param footer Footer text (supports & color codes, null = empty)
     */
    public static void setTabListHeaderAndFooter(@NotNull Player player,
                                                 @Nullable String header,
                                                 @Nullable String footer) {
        Component h = header != null ? Component.text(UtilsMessage.onChat(header)) : Component.empty();
        Component f = footer != null ? Component.text(UtilsMessage.onChat(footer)) : Component.empty();
        setTabListHeaderAndFooter(player, h, f);
    }

    /**
     * Set the tab list header for a single player using & color codes.
     *
     * @param player Player
     * @param header Header text (supports & color codes, null = empty)
     */
    public static void setTabListHeader(@NotNull Player player, @Nullable String header) {
        Component h = header != null ? Component.text(UtilsMessage.onChat(header)) : Component.empty();
        setTabListHeader(player, h);
    }

    /**
     * Set the tab list footer for a single player using & color codes.
     *
     * @param player Player
     * @param footer Footer text (supports & color codes, null = empty)
     */
    public static void setTabListFooter(@NotNull Player player, @Nullable String footer) {
        Component f = footer != null ? Component.text(UtilsMessage.onChat(footer)) : Component.empty();
        setTabListFooter(player, f);
    }

    /* ==============================
     *   GLOBAL TABLIST (Component)
     * ============================== */

    /**
     * Set a global header and footer applied to all currently online players.
     * Also stored so you can re-apply on join in your listener.
     *
     * @param header Global header (null = empty)
     * @param footer Global footer (null = empty)
     */
    public static void setGlobalTabHeaderAndFooter(@Nullable Component header,
                                                   @Nullable Component footer) {
        globalHeader = header;
        globalFooter = footer;
        for (Player player : Bukkit.getOnlinePlayers()) {
            setTabListHeaderAndFooter(player, header, footer);
        }
    }

    /**
     * Apply the current global header/footer to a single player.
     * Useful to call in a PlayerJoinEvent listener.
     *
     * @param player Player to apply to
     */
    public static void applyGlobalTabToPlayer(@NotNull Player player) {
        if (globalHeader == null && globalFooter == null) {
            return;
        }
        setTabListHeaderAndFooter(player, globalHeader, globalFooter);
    }

    /* ==============================
     *   GLOBAL TABLIST (String)
     * ============================== */

    /**
     * Set a global header and footer using & color codes.
     *
     * @param header Header text (supports & color codes, null = empty)
     * @param footer Footer text (supports & color codes, null = empty)
     */
    public static void setGlobalTabHeaderAndFooter(@Nullable String header,
                                                   @Nullable String footer) {
        Component h = header != null ? Component.text(UtilsMessage.onChat(header)) : Component.empty();
        Component f = footer != null ? Component.text(UtilsMessage.onChat(footer)) : Component.empty();
        setGlobalTabHeaderAndFooter(h, f);
    }

    /**
     * Get the current global header component.
     */
    public static @Nullable Component getGlobalHeader() {
        return globalHeader;
    }

    /**
     * Get the current global footer component.
     */
    public static @Nullable Component getGlobalFooter() {
        return globalFooter;
    }
}