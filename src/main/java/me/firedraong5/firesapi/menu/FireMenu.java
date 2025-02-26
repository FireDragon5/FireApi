package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.itemCreation.CustomItemCreator;

import me.firedraong5.firesapi.utils.UtilsMessage;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;


import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;


@SuppressWarnings("unused")
public class FireMenu {

	private final Player player;
	private final Inventory inventory;
	private final Map<Integer, Consumer<Player>> clickActions = new HashMap<>();

	private String title = "Menu";
	private int size = 9;


	/**
	 * Create a new menu
	 *
	 * @param player Player to open the menu
	 * @param name   Name of the menu
	 * @param size   Size of the menu
	 */
	public FireMenu(Player player, String name, int size) {
		if (size <= 0 || size % 9 != 0 || size > 54) {
			throw new IllegalArgumentException("Size must be a multiple of 9 and between 9 and 54.");
		}
		this.player = player;
		this.title = name;
		this.size = size;
		this.inventory = Bukkit.createInventory(player, size, UtilsMessage.onChat(name));
	}

	/**
	 * Create a new menu
	 */
	public FireMenu() {
		this.player = null;
		inventory = null;
	}

	/**
	 * Get the player
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the inventory
	 * @return Inventory
	 */
	public String getName() {
		return title;
	}

	/**
	 * Set the name of the menu
	 * @param name Name of the menu
	 * @return Menu
	 */
	public FireMenu setName(String name) {
		this.title = UtilsMessage.onChat(name);
		return this;
	}

	/**
	 * Get the size of the menu
	 * @return Size of the menu
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set the size of the menu
	 * @param size Size of the menu
	 * @return Menu
	 */
	public FireMenu setSize(int size) {
		this.size = size;
		return this;
	}


	/**
	 * Get the inventory
	 * @return Inventory
	 */
	public ItemStack getItem(int slot) {
		return inventory.getItem(slot);
	}

	/**
	 * Set the inventory
	 * @param slot Slot to set the item
	 * @param material Material of the item
	 */
	public void setItem(int slot, Material material) {

		ItemStack item = CustomItemCreator.createItem(material, 1, "&e");

		inventory.setItem(slot, item);
	}

	/**
	 * Set the inventory
	 *
	 * @param slot     Slot to set the item
	 * @param material Material of the item
	 * @param name     Name of the item
	 */
	public void setItem(int slot, Material material, String name) {
		ItemStack item = CustomItemCreator.createItem(material, 1, name);

		inventory.setItem(slot, item);
	}

	/**
	 * Set the inventory
	 * @param slot Slot to set the item
	 * @param material Material of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 */
	public FireMenu setItem(int slot, Material material, String name, List<String> lore) {
		if (slot < 0 || slot >= size) {
			throw new IllegalArgumentException("Slot is out of bounds.");
		}
		ItemStack item = CustomItemCreator.createItem(material, 1, name, lore);
		inventory.setItem(slot, item);
		return this;
	}

	/**
	 * Set the inventory
	 *
	 * @param slot Slot to set the item
	 * @param material Material of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 * @param amount Amount of the item
	 */
	public void setItem(int slot, Material material, String name, List<String> lore, int amount) {
		ItemStack item = CustomItemCreator.createItem(material, amount, name, lore);
	}

	/**
	 * Set the inventory
	 *
	 * @param slot Slot to set the item
	 * @param material Material of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 * @param glow Glow of the item
	 */
	public void setItemGlow(int slot, Material material, String name, List<String> lore, boolean glow) {
		ItemStack item = CustomItemCreator.createItem(material, 1, name, lore, glow);
		inventory.setItem(slot, item);
	}


	/**
	 * Open the inventory
	 */
	public void openMenu() {
		player.openInventory(inventory);
	}


	/**
	 * Set the inventory
	 */
	public void slotNumbers() {
			ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

			for (int i = 0; i < size; i++) {
				meta.setDisplayName(UtilsMessage.onChat("&e" + i));
				item.setItemMeta(meta);
				if (item.getType() == Material.AIR || inventory.getItem(i) == null) {
					inventory.setItem(i, item);
				}
			}

	}

	public void fillGlass(@Nullable Material material, @Nullable String name, boolean isBorder) {
		if (material == null) {
			material = Material.BLACK_STAINED_GLASS_PANE;
		}
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		if (name != null) {
			meta.setDisplayName(UtilsMessage.onChat(name));
		}
		item.setItemMeta(meta);

		for (int i = 0; i < size; i++) {
			boolean isSlotBorder = (i < 9 || i >= size - 9 || i % 9 == 0 || i % 9 == 8);
			if ((isBorder && isSlotBorder) || (!isBorder && !isSlotBorder)) {
				if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
					inventory.setItem(i, item);
				}
			}
		}
	}

//	Methods where the user can place the glass pane

	/**
	 * Make the rest of the gui look like glass
	 *
	 * @param material Material of the glass pane
	 * @param name Name of the glass pane
	 * @param slotNumbers Show the slot numbers
	 */
	public void glass(@NotNull Material material, @Nullable String name, @NotNull List<Integer> slotNumbers) {

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		for (Integer slotNumber : slotNumbers) {

			meta.setDisplayName(UtilsMessage.onChat(Objects.requireNonNullElse(name, "&e")));
			item.setItemMeta(meta);
			if (item.getType() == Material.AIR || inventory.getItem(slotNumber) == null) {
				inventory.setItem(slotNumber, item);
			}
		}
	}

	/**
	 * Add a player head to the inventory
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 *           <br> - When %player% is in the name it will replace it with the player name
	 * @param slot Slot to set the head
	 */
	public void addPlayerHead(Player player, @Nullable String name, int slot) {

		ItemStack item = getPlayerHead(player, name, null);

		inventory.setItem(slot, item);
	}


	/**
	 * Add a player head to the inventory
	 *
	 * @param player Player to get the head
	 * @param name   Name of the head (null = player name)
	 *               <br> - When %player% is in the name it will replace it with the player name
	 * @param lore   Lore of the head
	 * @param slot   Slot to set the head
	 */
	public void addPlayerHead(Player player, @Nullable String name, int slot, @NotNull List<String> lore) {

		ItemStack item = getPlayerHead(player, name, lore);

		inventory.setItem(slot, item);
	}


	/**
	 * Get all the online players heads in the server
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @param lore Lore of the head (null = no lore)
	 */
	public void getAllPlayerHeads(Player player, @Nullable String name, @NotNull List<String> lore) {

		ItemStack item = getPlayerHead(player, name, lore);

		inventory.addItem(item);
	}

	/**
	 * Get all the online players heads in the server
	 *
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @param lore Lore of the head (null = no lore)
	 * @param slot Slot to set the head
	 */

	public void getAllPlayerHeads(Player player, @Nullable String name, @NotNull List<String> lore, int slot) {
		ItemStack item = getPlayerHead(player, name, lore);

		inventory.setItem(slot, item);
	}

	/**
	 * Get all the online players heads in the server
	 *    @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 */
	public void getAllPlayerHeads(Player player, @Nullable String name) {

		ItemStack item = getPlayerHead(player, name, null);

		inventory.addItem(item);
	}


	/**
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @param lore Lore of the head
	 * @return ItemStack
	 */
	private @NotNull ItemStack getPlayerHead(Player player, @Nullable String name, @Nullable List<String> lore) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		String displayName = name != null
				? UtilsMessage.onChat(name.replace("%player%", player.getName()))
				: UtilsMessage.onChat(player.getName());
		meta.displayName(Component.text(displayName));

		if (lore != null) {
			meta.setLore(UtilsMessage.onChat(lore));
		}

		meta.setOwningPlayer(player);
		item.setItemMeta(meta);

		return item;
	}


	//	Display a menu info item
	public void displayMenuInfo(int slot, Material material, String name, List<String> lore) {
		ItemStack item = CustomItemCreator.createItem(material, 1, name, lore);

		inventory.setItem(slot, item);
	}
}
