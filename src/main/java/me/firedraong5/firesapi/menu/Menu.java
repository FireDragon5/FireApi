package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu implements Listener {

	private final Player player;
	private final Inventory inventory;

	private String title = "&0Default Menu";
	private int size = 9;
	private boolean slotNumbersVisible = false;

	public Menu(Player player, String name, int size) {
		this.player = player;
		this.title = UtilsMessage.onChat(name);
		this.size = size;
		this.inventory = Bukkit.createInventory(player, size, title);
	}

	public Player getPlayer() {
		return player;
	}

	public String getName() {
		return title;
	}

	public Menu setName(String name) {
		this.title = UtilsMessage.onChat(name);
		return this;
	}

	public int getSize() {
		return size;
	}

	public Menu setSize(int size) {
		this.size = size;
		return this;
	}

	public boolean isSlotNumbersVisible() {
		return slotNumbersVisible;
	}

	public Menu setSlotNumbersVisible(boolean slotNumbersVisible) {
		this.slotNumbersVisible = slotNumbersVisible;
		return this;
	}

	public ItemStack getItem(int slot) {
		return inventory.getItem(slot);
	}

	public void setItem(int slot, ItemStack item) {
		inventory.setItem(slot, item);
	}

	public void setItem(int slot, ItemStack item, String name) {
		item.getItemMeta().setDisplayName(UtilsMessage.onChat(name));
		inventory.setItem(slot, item);
	}

	public void openMenu() {
		player.openInventory(inventory);
	}

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

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory() == null || !event.getInventory().equals(inventory)) return;
		event.setCancelled(true);
	}
}
