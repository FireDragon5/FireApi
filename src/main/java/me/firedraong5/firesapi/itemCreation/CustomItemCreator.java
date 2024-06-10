package me.firedraong5.firesapi.itemCreation;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@SuppressWarnings("unused")
public class CustomItemCreator {


	//	Return the item name
	public static String getItemName(@NotNull ItemStack item) {
		return item.getItemMeta().getDisplayName();
	}

//	Get the item lore
public static String getItemLore(@NotNull ItemStack item) {
	return Objects.requireNonNull(item.getItemMeta().getLore()).toString();
	}

//	Get the item material
public static @NotNull Material getItemMaterial(@NotNull ItemStack item) {
		return item.getType();
	}

//	Get the item amount
public static int getItemAmount(@NotNull ItemStack item) {
		return item.getAmount();
	}

//	Get the item durability
public static int getItemDurability(@NotNull ItemStack item) {
		return item.getDurability();
	}

//	Get the item meta
public static ItemMeta getItemMeta(@NotNull ItemStack item) {
		return item.getItemMeta();
	}

	/**
	 * Get the item
	 *
	 * @param item Item to get
	 * @return Item
	 */
	public static ItemStack getItem(ItemStack item) {
		return item;
	}

	/**
	 * Set the item name
	 *
	 * @param item Item to set
	 * @param name Name to set
	 */
	public static void setItemName(@NotNull ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

	/**
	 * Set the item lore
	 *
	 * @param item Item to set
	 * @param lore Lore to set
	 */
	public static void setItemLore(@NotNull ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();

		meta.setLore(UtilsMessage.onChat(lore));
		item.setItemMeta(meta);
	}

	/**
	 * Set the item
	 *
	 * @param item Item to set
	 * @param material Material of the item
	 */
	public static void setItemMaterial(@NotNull ItemStack item, Material material) {
		item.setType(material);
	}

	/**
	 * Set the item amount
	 * @param item Item to set
	 * @param amount Amount to set
	 */
	public static void setItemAmount(@NotNull ItemStack item, int amount) {
		item.setAmount(amount);
	}

	/**
	 * Set the item durability
	 *
	 * @param item Item to set
	 * @param durability Durability to set
	 */
	public static void setItemDurability(@NotNull ItemStack item, int durability) {
		item.setDurability((short) durability);
	}

	/**
	 * Set the item meta
	 * @param item Item to set
	 * @param meta Meta to set
	 */
	public static void setItemMeta(@NotNull ItemStack item, ItemMeta meta) {
		item.setItemMeta(meta);
	}

	/**
	 * Hide the NBT of the item
	 *
	 * @param item Item to hide
	 */
	public static void hideNBT(@NotNull ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(meta.getDisplayName()));
		item.setItemMeta(meta);
	}


	/**
	 * Create a new item
	 * @param material Material of the item
	 * @param amount Amount of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 * @return The item
	 */
	public static @NotNull ItemStack createItem(Material material, int amount, String name, List<String> lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(name));
		meta.setLore(UtilsMessage.onChat(lore));
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Create a new item
	 * @param material Material of the item
	 * @param amount Amount of the item
	 * @param name Name of the item
	 * @param durability Durability of the item
	 * @param lore Lore of the item
	 * @return The item
	 */
	public static @NotNull ItemStack createItem(Material material, int amount, String name, int durability, List<String> lore) {
		ItemStack item = createItem(material, amount, name, lore);
		item.setDurability((short) durability);
		return item;
	}


	/**
	 * Create a new item
	 *
	 * @param material Material of the item
	 * @param name Name of the item
	 * @return The item
	 */
	public static @NotNull ItemStack createItem(Material material, String name) {
		return createItem(material, 1, name, null);
	}


	/**
	 * Create a new item
	 * @param player Player to send the message
	 * @param material Material of the item
	 * @param amount Amount of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 * @param playerMessage Message to send to the player
	 * @return The item
	 */
	public static @NotNull ItemStack createItemWithPlayerMessage(Player player, Material material, int amount, String name,
																 int durability, String playerMessage, List<String> lore) {
		ItemStack item = createItem(material, amount, name, durability, lore);

		UtilsMessage.sendMessage(player, playerMessage);

		return item;
	}



	/**
	 * This will add the item to the player inventory
	 * @param item Item to add
	 * @param player Player to add the item
	 */

	public static  void addToInventory(@NotNull Player player, ItemStack item) {
		player.getInventory().addItem(item);
	}


	/**
	 * Create a new item
	 *
	 * @param material Material of the item
	 * @param amount Amount of the item  default value = 1
	 * @param itemName Name of the item
	 * @return ItemStack
	 */
	public static ItemStack createItem(Material material, int amount
			, String itemName) {

		return createItem(material, amount, itemName, Collections.singletonList(""));

	}
}
