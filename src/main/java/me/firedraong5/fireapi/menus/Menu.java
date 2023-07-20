package me.firedraong5.fireapi.menus;

import me.firedraong5.fireapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Menu implements Listener {

//	This is a menu class that will be used to create menus
//	This is to make the creation of menu easier


	private String menuTitle;
	private int menuSize;

	private Inventory menu;

	public Menu(String menuTitle, int menuSize) {
		this.menuTitle = menuTitle;
		this.menuSize = menuSize;
		this.menu = Bukkit.createInventory(null, menuSize, menuTitle);
	}

	public Menu(){

	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public int getMenuSize() {
		return menuSize;
	}

	public Inventory getMenu() {
		return menu;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public void setMenuSize(int menuSize) {
		this.menuSize = menuSize;
	}

	public void setMenu(Inventory menu) {
		this.menu = menu;
	}

	/**
	* 	Menu creation methods
	* @param name - This is the name of the item
	* @param slot - This is the slot of the item
	* @param item - This is the itemstack
	* @param lore - This is a list of strings
	 */
	public void addButton(String name, int slot, ItemStack item, List<String> lore , ClickAction action) {

		//Create a new item
		ItemStack newItem = new ItemStack(item);

		//Set the item name
		ItemMeta itemMeta = newItem.getItemMeta();

		itemMeta.setDisplayName(UtilsMessage.onChat(name));

		//Set the item lore
		if (lore != null) {
			lore.replaceAll(UtilsMessage::onChat);
			itemMeta.setLore(lore);
		}

		//Set the item meta
		newItem.setItemMeta(itemMeta);

		//Set the item to the menu
		menu.setItem(slot, item);
	}

	public void openMenu(Player player) {
		player.openInventory(menu);
	}


	public interface ClickAction {
		void onClick(Player player);
	}


//	Stop the player click event
	@EventHandler
	public void onPlayerClick(InventoryClickEvent event) {
		if (event.getView().getTitle().equals(menuTitle)) {
			event.setCancelled(true);
		}
	}



}
