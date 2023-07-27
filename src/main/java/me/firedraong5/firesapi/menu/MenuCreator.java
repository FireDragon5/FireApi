package me.firedraong5.firesapi.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuCreator {

	private int size;

	private String title;

	private ItemStack[] items;

	/**
	 *
	 * @param size The size of the menu
	 * @param title The title of the menu
	 */
	public MenuCreator(int size, String title) {
		this.size = size;
		this.title = title;

		this.items = new ItemStack[size];
	}

//	Getters

	/**
	 *
	 * @return The size of the menu
	 */
	public int getSize() {
		return size;
	}

	/**
	 *
	 * @return The title of the menu
	 */
	public String getTitle() {
		return title;
	}

	/**
	 *
	 * @return The items of the menu
	 */
	public ItemStack[] getItems() {
		return items;
	}

//	Setters

	/**
	 *
	 * @param size The size of the menu
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 *
	 * @param title The title of the menu
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 *
	 * @param items The items of the menu
	 */
	public void setItems(ItemStack[] items) {
		this.items = items;
	}

	/**
	 * Set item at the slot
	 * @param item The item to set
	 * @param slot The slot to set the item
	 */
	public void setItem(int slot, ItemStack item) {
		this.items[slot] = item;
	}

	/**
	 * Set the item at the slot with the name
	 *
	 * @param item The item to set
	 * @param slot The slot to set the item
	 * @param name The name of the item
	 */
	public void setItem(int slot, ItemStack item, String name) {
		this.items[slot] = item;
		this.items[slot].getItemMeta().setDisplayName(name);
	}


	/**
	 @param size The size of the menu
	 @param title The title of the menu

	 */
	public static MenuCreator createMenu(int size, String title) {
		return new MenuCreator(size, title);
	}

	/**
	 * Display the menu to the player
	 *
	 * @param player The player to display the menu
	 */
	public void displayMenu(Player player) {

		Inventory inv = Bukkit.createInventory(player, size, title);

		inv.setContents(items);

		if (player.getInventory() != null) {

			player.closeInventory();

		}
		player.openInventory(inv);
	}


	public ItemStack getItem(int i) {
		return items[i];
	}
}
