package me.firedraong5.fireapi.menus;

import com.google.gson.JsonArray;
import me.firedraong5.fireapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Menu implements Listener {

//	This is a menu class that will be used to create menus
//	This is to make the creation of menu easier


//	Store the actions of the buttons
	private final List<ClickAction> actions = new ArrayList<>();

	//	Stop the player click event
	@EventHandler
	public void onPlayerClick(InventoryClickEvent event) {

		if (event.getView().getTitle().equals(menuTitle)) {
			event.setCancelled(true);

//			Get the action of the button
			ClickAction action = null;
			for (int i = 0; i < menu.getSize(); i++) {
				if (event.getSlot() == i) {
					ItemStack item = event.getClickedInventory().getItem(i);
					if (item != null) {
						ItemMeta itemMeta = item.getItemMeta();
						if (itemMeta != null) {
							String displayName = itemMeta.getDisplayName();
							if (displayName != null) {
								for (int j = 0; j < menu.getSize(); j++) {
									if (event.getSlot() == j) {
										action = actions.get(j);
									}
								}
							}
						}
					}
				}
			}
			if (action != null) {
				action.onClick((Player) event.getWhoClicked());
			}

		}
	}

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
	public void addButton(String name, int slot, Material item, @Nullable List<String> lore , ClickAction action) {

		//Create a new item
		ItemStack newItem = new ItemStack(item);

		//Set the item name
		ItemMeta itemMeta = newItem.getItemMeta();

		itemMeta.setDisplayName(UtilsMessage.onChat(name));

		//Set the item lore
		// Set the item lore
		if (lore != null) {
			List<String> updatedLore = new ArrayList<>();
			for (String line : lore) {
				updatedLore.add(UtilsMessage.onChat(line));
			}
			itemMeta.setLore(updatedLore);
		}

		//Set the item meta
		newItem.setItemMeta(itemMeta);

		//Set the item to the menu
		menu.setItem(slot, newItem);
	}

//	Open the menu
	public void openMenu(Player player) {
		player.openInventory(menu);
	}


	public interface ClickAction {
		void onClick(Player player);
	}





	/**
	 * This will return a player head
	 * @param player - This is the player that will be used to get the head
	 * @param displayName - This is the display name of the player head
	 * @param slot - This is the slot of the player head
	 * @param lore - This is the lore of the player head
	 * @param action - This is the action of the player head
	 */

	public void addPlayerHeadsButton(Player player, @Nullable String displayName,
							   int slot, @Nullable List<String> lore, ClickAction action) {
		ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();

		//		If the display name is null then set the display name to the player name
		if (displayName == null) {
			playerHeadMeta.setDisplayName(UtilsMessage.onChat(player.getDisplayName()));
		} else {
			playerHeadMeta.setDisplayName(UtilsMessage.onChat(displayName));
		}




		if (lore != null) {
			List<String> updatedLore = new ArrayList<>();
			for (String line : lore) {
				updatedLore.add(UtilsMessage.onChat(line));
			}
			playerHeadMeta.setLore(updatedLore);
		}

		menu.setItem(slot, playerHead);
	}



	/**
	 *
	 * @param player - This is the player that will be used to get the head
	 * @param displayName - This is the display name of the player head
	 * @param slot - This is the slot of the player head
	 * @param lore - This is the lore of the player head
	 * @param action - This is the action of the player head
	 *
	 * This will return all the players on the server heads
	 *
	 */
	public void addAllPlayerHeadsButton(Player player, @Nullable String displayName,
							   int slot, List<String> lore,  ClickAction action) {
		for (Player players : Bukkit.getOnlinePlayers()){
			ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();

			//		If the display name is null then set the display name to the player name
			if (displayName == null) {
				playerHeadMeta.setDisplayName(UtilsMessage.onChat(players.getDisplayName()));
			} else {
				playerHeadMeta.setDisplayName(UtilsMessage.onChat(displayName));
			}

			if (lore != null) {
				List<String> updatedLore = new ArrayList<>();
				for (String line : lore) {
					updatedLore.add(UtilsMessage.onChat(line));
				}
				playerHeadMeta.setLore(updatedLore);
			}



			menu.setItem(slot, playerHead);
		}
	}




}
