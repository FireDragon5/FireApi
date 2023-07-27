package me.firedraong5.firesapi.itemCreation;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class CustomItemCreator {

//	Get the item name
	public static String getItemName(ItemStack item) {
		return item.getItemMeta().getDisplayName();
	}

//	Get the item lore
	public static String getItemLore(ItemStack item) {
		return item.getItemMeta().getLore().toString();
	}

//	Get the item material
	public static Material getItemMaterial(ItemStack item) {
		return item.getType();
	}

//	Get the item amount
	public static int getItemAmount(ItemStack item) {
		return item.getAmount();
	}

//	Get the item durability
	public static int getItemDurability(ItemStack item) {
		return item.getDurability();
	}

//	Get the item meta
	public static ItemMeta getItemMeta(ItemStack item) {
		return item.getItemMeta();
	}

//	Get the item
	public static ItemStack getItem(ItemStack item) {
		return item;
	}

//	Set the item name
	public static void setItemName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
	}

//	Set the item lore
	public static void setItemLore(ItemStack item, String... lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
	}

//	Set the item material
	public static void setItemMaterial(ItemStack item, Material material) {
		item.setType(material);
	}

//	Set the item amount
	public static void setItemAmount(ItemStack item, int amount) {
		item.setAmount(amount);
	}

//	Set the item durability
	public static void setItemDurability(ItemStack item, int durability) {
		item.setDurability((short) durability);
	}

//	Set the item meta
	public static void setItemMeta(ItemStack item, ItemMeta meta) {
		item.setItemMeta(meta);
	}

//	Set the item
	public static void setItem(ItemStack item, ItemStack newItem) {
		item = newItem;
	}

	/**
	 * Create a new item
	 * @param material Material of the item
	 * @param amount Amount of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 * @return The item
	 */
	public static ItemStack createItem(Material material, int amount, String name, String... lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(name));
		meta.setLore(Collections.singletonList(UtilsMessage.onChat(Arrays.toString(lore))));
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
	public static ItemStack createItem(Material material, int amount, String name, int durability, String... lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(name));
		meta.setLore(Collections.singletonList(UtilsMessage.onChat(Arrays.toString(lore))));
		item.setItemMeta(meta);
		item.setDurability((short) durability);
		return item;
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
	public static ItemStack createItemWithPlayerMessage(Player player,Material material, int amount, String name,
														int durability, String playerMessage, String... lore) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(name));
		meta.setLore(Collections.singletonList(UtilsMessage.onChat(Arrays.toString(lore))));
		item.setItemMeta(meta);
		item.setDurability((short) durability);

		UtilsMessage.sendMessage(player, playerMessage);

		return item;
	}



	/**
	 * This will add the item to the player inventory
	 * @param item Item to add
	 * @param player Player to add the item
	 */

	public static void addToInventory(Player player,ItemStack item) {
		player.getInventory().addItem(item);
	}

}
