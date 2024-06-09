package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.itemCreation.CustomItemCreator;
import me.firedraong5.firesapi.utils.PageUtil;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PageMenu {


	private final Player player;
	private final Inventory inventory;

	private String title = "Page Menu";
	private int size = 52;

	/**
	 * Create a new menu
	 *
	 * @param player Player to open the menu
	 * @param name Name of the menu
	 * @param size Size of the menu
	 */
	public PageMenu(Player player, String name, int size) {
		this.player = player;
		this.title = name;
		this.size = size;

		this.inventory = Bukkit.createInventory(player, size, Component.text(UtilsMessage.onChat(name)));
	}


	/**
	 * Create a new menu
	 */
	public PageMenu() {
		this.player = null;
		inventory = null;
	}

	/**
	 * Get the player
	 *
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the inventory
	 *
	 * @return Inventory
	 */
	public String getName() {
		return title;
	}

	/**
	 * Set the name of the menu
	 *
	 * @param name Name of the menu
	 * @return Menu
	 */
	public PageMenu setName(String name) {
		this.title = UtilsMessage.onChat(name);
		return this;
	}

	/**
	 * Get the size of the menu
	 *
	 * @return Size of the menu
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set the size of the menu
	 *
	 * @param size Size of the menu
	 * @return Menu
	 */
	public PageMenu setSize(int size) {
		this.size = size;
		return this;
	}


	/**
	 * @param material Material of the item
	 * @param name Name of the item
	 * @param page Page to get
	 */
	public void PageGui(Material material, String name, int page) {
		List<ItemStack> allItems = new ArrayList<>();

		for (int i = 0; i < 135; i++) {
			ItemStack item = CustomItemCreator.createItem(material, name);
			allItems.add(item);
		}

		itemStackPage(page, allItems);

	}


	/**
	 * @param name Name of the head (null = player name)
	 * @param page Page to get
	 */
	//	PageGUI for player heads
	private void PageGui(@Nullable String name, int page) {
		List<ItemStack> allItems = new ArrayList<>();

		for (Player playersOnline : Bukkit.getOnlinePlayers()) {
			ItemStack item = new FireMenu().getPlayerHead(playersOnline, name);
			allItems.add(item);
		}

		itemStackPage(page, allItems);

	}


	/**
	 * @param page Page to get
	 * @param allItems All the items
	 */
	private void itemStackPage(int page, List<ItemStack> allItems) {
		ItemStack left;
		if (PageUtil.isPageValid(allItems, page - 1, 52))
			left = CustomItemCreator.createItem(Material.ARROW, 1, "&aPrevious Page");
		else left = CustomItemCreator.createItem(Material.BARRIER, 1, "&cPrevious Page");
		inventory.setItem(0, left);


		ItemStack right;

		if (PageUtil.isPageValid(allItems, page + 1, 52))
			right = CustomItemCreator.createItem(Material.ARROW, 1, "&aNext Page");
		else right = CustomItemCreator.createItem(Material.BARRIER, 1, "&cNext Page");


		inventory.setItem(8, right);


		for (ItemStack is : PageUtil.getPageItems(allItems, page, 52)) {
			inventory.setItem(inventory.firstEmpty(), is);
		}
	}

}
