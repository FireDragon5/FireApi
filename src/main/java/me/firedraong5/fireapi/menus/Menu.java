package me.firedraong5.fireapi.menus;

import me.firedraong5.fireapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Listener {

	private final String menuTitle;
	private final int menuSize;
	private final Inventory menu;
	private final List<ClickAction> actions = new ArrayList<>();

	public Menu(String menuTitle, int menuSize) {
		this.menuTitle = UtilsMessage.onChat(menuTitle);
		this.menuSize = menuSize;
		this.menu = Bukkit.createInventory(null, menuSize, this.menuTitle);
	}

	public Menu(){
		this.menuTitle = null;
		this.menuSize = 0;
		this.menu = null;
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
	/**
	 * 	Menu creation methods
	 * @param name - This is the name of the item
	 * @param slot - This is the slot of the item
	 * @param item - This is the itemstack
	 * @param lore - This is a list of strings
	 */
	public void addButton(String name, int slot, Material item, @Nullable List<String> lore, ClickAction action) {
		ItemStack newItem = new ItemStack(item);
		ItemMeta itemMeta = newItem.getItemMeta();

		itemMeta.setDisplayName(UtilsMessage.onChat(name));

		if (lore != null) {
			List<String> updatedLore = new ArrayList<>();
			for (String line : lore) {
				updatedLore.add(UtilsMessage.onChat(line));
			}
			itemMeta.setLore(updatedLore);
		}

		newItem.setItemMeta(itemMeta);

		menu.setItem(slot, newItem);
		actions.add(action); // Store the action associated with the button
	}

	@EventHandler
	public void onPlayerClick(@NotNull InventoryClickEvent event) {
		if (event.getView().getTitle().equals(menuTitle)) {
			event.setCancelled(true);

			ClickAction action = actions.get(event.getRawSlot());
			if (action != null) {
				action.onClick((Player) event.getWhoClicked());
			}
		}
	}

	public interface ClickAction {
		void onClick(Player player);
	}



//	Open the menu
	public void openMenu(Player player) {
		player.openInventory(menu);
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

		playerHeadMeta.setOwningPlayer(player);

		playerHead.setItemMeta(playerHeadMeta);

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

	/**
	 * This will show the numbers of the slots
	 *
	 * Use this only for debugging purposes
	 */
	public void showSlots(){
		int slotNumber = 0 ;

		for (int i = 0; i < menuSize; i++) {
			ItemStack itemStack = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(UtilsMessage.onChat("&c&l" + slotNumber));
			itemStack.setItemMeta(itemMeta);
			menu.setItem(slotNumber, itemStack);
			slotNumber++;
		}
	}





}
