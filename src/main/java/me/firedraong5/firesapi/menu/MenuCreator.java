package me.firedraong5.firesapi.menu;

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
	 *
	 * @param item The item to set
	 * @param slot The slot to set the item
	 */
	public void setItem(ItemStack item, int slot) {
		this.items[slot] = item;
	}


	public static MenuCreator createMenu(int size, String title) {
		return new MenuCreator(size, title);
	}


}
