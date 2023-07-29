package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Menu implements Listener {

	private final Player player;
	private final Inventory inventory;

	private String title;
	private int size = 9;
	private boolean slotNumbersVisible = false;


	/**
	 * Create a new menu
	 *
	 * @param player Player to open the menu
	 * @param name   Name of the menu
	 * @param size   Size of the menu
	 */
	public Menu(Player player, String name, int size) {
		this.player = player;
		this.title = name;
		this.size = size;

		this.inventory = Bukkit.createInventory(player, size, UtilsMessage.onChat(title));

		slotNumbers();

	}


	/**
	 * Create a new menu
	 */
	public Menu() {
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
	public Menu setName(String name) {
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
	public Menu setSize(int size) {
		this.size = size;
		return this;
	}

	/**
	 * Get if the slot numbers are visible
	 * @return Slot numbers visible
	 */
	public boolean isSlotNumbersVisible() {
		return slotNumbersVisible;
	}

	/**
	 * Set if the slot numbers are visible
	 *
	 * @param slotNumbersVisible Slot numbers visible
	 * @return Menu
	 */
	public boolean setSlotNumbersVisible(boolean slotNumbersVisible) {
		this.slotNumbersVisible = slotNumbersVisible;
		return slotNumbersVisible;
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

		ItemStack item = new ItemStack(material);

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

		ItemStack item = new ItemStack(material);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(name));
		item.setItemMeta(meta);


		inventory.setItem(slot, item);
	}

	/**
	 * Set the inventory
	 * @param slot Slot to set the item
	 * @param material Material of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 */
	public void setItem(int slot, Material material, String name, ArrayList<String> lore) {

		ItemStack item = new ItemStack(material);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(name));
		item.setItemMeta(meta);

//		item.getItemMeta().setLore(UtilsMessage.onChat(lore));
		inventory.setItem(slot, item);
	}





	/**
	 * Open the inventory
	 */
	public void openMenu() {
		player.openInventory(inventory);
	}

	/**
	 * When slotNumbers are turn turn it will show the slot numbers
	 */
	private void slotNumbers() {
		if (slotNumbersVisible) {
			for (int i = 0; i < size; i++) {
				ItemStack item = inventory.getItem(i);
				if (item == null || item.getType() == Material.AIR) {
					inventory.setItem(i, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, (byte) 15));
				}
			}
		}
	}

	/**
	 * When the inventory is clicked
	 * @param event InventoryClickEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {

		if (!event.getInventory().equals(inventory)) {
			event.setCancelled(true);
		}


	}

}
