package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.itemCreation.CustomItemCreator;
import me.firedraong5.firesapi.utils.PageUtil;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PageMenu {


	private final Player player;
	private final Inventory inventory;

	private String title = "Page Menu";
	private int size = 52;

	private boolean displayPageInfo = true;

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

		this.inventory = Bukkit.createInventory(player, size, UtilsMessage.onChat(name));
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

	//	open the menu
	private void open() {
		player.openInventory(inventory);
	}


//	If this is true show it else don't show it in the inventory

	/**
	 * Display the page info
	 */
	private boolean isDisplayPageInfo() {
		return displayPageInfo;
	}

	public void setDisplayPageInfo(boolean displayPageInfo) {
		this.displayPageInfo = displayPageInfo;
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

		itemStackPage(page, allItems, Material.ARROW, Material.BARRIER);

		open();


	}



	/**
	 * @param page Page to get
	 */
	//	PageGUI for player heads
	public void PlayerHeadsMenu(int page, String displayName, List<String> lore) {
		List<ItemStack> allItems = new ArrayList<>();

		for (Player playersOnline : Bukkit.getOnlinePlayers()) {
			// Use the new parameters here
			ItemStack item = new FireMenu().getPlayerHead(playersOnline, displayName, lore);
			allItems.add(item);
		}

		// Add parameters for GUI customization
		itemStackPage(page, allItems, Material.ARROW, Material.BARRIER);

		open();
	}


	/**
	 * @param page Page to get
	 * @param allItems Items to get
	 * @param prevPageMaterial Material of the previous page
	 * @param nextPageMaterial Material of the next page
	 */
	private void itemStackPage(int page, List<ItemStack> allItems, Material prevPageMaterial, Material nextPageMaterial) {
		ItemStack left;
		if (PageUtil.isPageValid(allItems, page - 1, 52))
			left = CustomItemCreator.createItem(prevPageMaterial, 1, "&aPrevious Page");
		else left = CustomItemCreator.createItem(Material.BARRIER, 1, "&cPrevious Page");
		inventory.setItem(0, left);

		ItemStack right;

		if (PageUtil.isPageValid(allItems, page + 1, 52))
			right = CustomItemCreator.createItem(nextPageMaterial, 1, "&aNext Page");
		else right = CustomItemCreator.createItem(Material.BARRIER, 1, "&cNext Page");


		inventory.setItem(8, right);

		for (ItemStack is : PageUtil.getPageItems(allItems, page, 52)) {
			inventory.setItem(inventory.firstEmpty(), is);
		}
	}

	/**
	 * <p>
	 *     Add a menu info item to the inventory
	 * 	  Will be place at the last slot in the inventory
	 * </p>
	 * @param name Name of the item
	 * @param material Material of the item
	 * @param lore Lore of the item
	 */
	public void addMenuInfo(String name, Material material, @Nullable List<String> lore) {
		ItemStack info = CustomItemCreator.createItem(material, 1, name, lore);

		inventory.setItem(size - 1, info);
	}



}
