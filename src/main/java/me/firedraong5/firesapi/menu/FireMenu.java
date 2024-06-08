package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.itemCreation.CustomItemCreator;
import me.firedraong5.firesapi.utils.PageUtil;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;


import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public final class FireMenu {

	private final Player player;
	private final Inventory inventory;

	private String title = "Menu";
	private int size = 9;


	/**
	 * Create a new menu
	 *
	 * @param player Player to open the menu
	 * @param name   Name of the menu
	 * @param size   Size of the menu
	 */
	public FireMenu(Player player, String name, int size) {
		this.player = player;
		this.title = name;
		this.size = size;

		this.inventory = Bukkit.createInventory(player, size, Component.text(UtilsMessage.onChat(name)));
	}

	//	Get the class name
	private String getClassName() {
		return this.getClass().getSimpleName();
	}


	/**
	 * Create a new menu
	 */

	public FireMenu() {
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
	public FireMenu setName(String name) {
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
	public FireMenu setSize(int size) {
		this.size = size;
		return this;
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
	public void setItem(int slot, Material material, String name, List<String> lore) {

		ItemStack item = new ItemStack(material);

		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(UtilsMessage.onChat(name));

		meta.setLore(UtilsMessage.onChat(lore));

		item.setItemMeta(meta);


		inventory.setItem(slot, item);
	}

	/**
	 * Set the inventory
	 *
	 * @param slot Slot to set the item
	 * @param material Material of the item
	 * @param name Name of the item
	 * @param lore Lore of the item
	 * @param amount Amount of the item
	 */
	public void setItem(int slot, Material material, String name, List<String> lore, int amount) {
		ItemStack item = CustomItemCreator.createItem(material, amount, name, lore);
	}






	/**
	 * Open the inventory
	 */
	public void openMenu() {
		player.openInventory(inventory);
	}


	/**
	 * Set the inventory
	 */
	public void slotNumbers() {
			ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
			ItemMeta meta = item.getItemMeta();

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

			for (int i = 0; i < size; i++) {
				meta.setDisplayName(UtilsMessage.onChat("&e" + i));
				item.setItemMeta(meta);
				if (item.getType() == Material.AIR || inventory.getItem(i) == null) {
					inventory.setItem(i, item);
				}
			}

	}

	/**
	 * Make the rest of the gui look like glass
	 *
	 * @param material Material of the glass
	 * - If null it will be black stained-glass
	 */
	public void glass(@Nullable Material material) {

		if (material == null) {
			material = Material.BLACK_STAINED_GLASS_PANE;
		}

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		for (int i = 0; i < size; i++) {
			meta.setDisplayName(UtilsMessage.onChat("&e"));
			item.setItemMeta(meta);
			if (item.getType() == Material.AIR || inventory.getItem(i) == null) {
				inventory.setItem(i, item);
			}
		}

	}


	/**
	 * Place the glass in a border of the gui
	 *
	 * @param material Material of the glass
	 * - If null it will be black stained-glass
	 */
	public void borderGlass(@Nullable Material material) {

		if (material == null) {
			material = Material.BLACK_STAINED_GLASS_PANE;
		}

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();


		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		for (int i = 0; i < size; i++) {
			if (i < 9 || i > size - 9 || i % 9 == 0 || i % 9 == 8) {
				meta.setDisplayName(UtilsMessage.onChat("&e"));
				item.setItemMeta(meta);
				if (item.getType() == Material.AIR || inventory.getItem(i) == null) {
					inventory.setItem(i, item);
				}
			}
		}

	}


//	Methods where the user can place the glass pane

	/**
	 * Make the rest of the gui look like glass
	 *
	 * @param material Material of the glass pane
	 * @param name Name of the glass pane
	 * @param slotNumbers Show the slot numbers
	 */
	public void glass(@NotNull Material material, @Nullable String name, @NotNull List<Integer> slotNumbers) {

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		for (Integer slotNumber : slotNumbers) {

			meta.setDisplayName(UtilsMessage.onChat(Objects.requireNonNullElse(name, "&e")));
			item.setItemMeta(meta);
			if (item.getType() == Material.AIR || inventory.getItem(slotNumber) == null) {
				inventory.setItem(slotNumber, item);
			}
		}


	}


	/**
	 * Add a player head to the inventory
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 *           <br> - When %player% is in the name it will replace it with the player name
	 * @param slot Slot to set the head
	 */
	public void addPlayerHead(Player player, @Nullable String name, int slot) {

		ItemStack item = getPlayerHead(player, name);

		inventory.setItem(slot, item);

	}


	/**
	 * Add a player head to the inventory
	 *
	 * @param player Player to get the head
	 * @param name   Name of the head (null = player name)
	 *               <br> - When %player% is in the name it will replace it with the player name
	 * @param lore   Lore of the head
	 * @param slot   Slot to set the head
	 */
	public void addPlayerHead(Player player, @Nullable String name, int slot, @NotNull List<String> lore) {

		ItemStack item = getPlayerHead(player, name, lore);

		inventory.setItem(slot, item);

	}


	/**
	 * Get all the online players heads in the server
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @param lore Lore of the head (null = no lore)
	 */
	public void getAllPlayerHeads(Player player, @Nullable String name, @NotNull List<String> lore) {

		ItemStack item = getPlayerHead(player, name, lore);

		inventory.addItem(item);


	}

	/**
	 * Get all the online players heads in the server
	 *
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @param lore Lore of the head (null = no lore)
	 * @param slot Slot to set the head
	 */

	public void getAllPlayerHeads(Player player, @Nullable String name, @NotNull List<String> lore, int slot) {

		ItemStack item = getPlayerHead(player, name, lore);

		inventory.setItem(slot, item);

	}


	/**
	 * Get all the online players heads in the server
	 *    @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 */
	public void getAllPlayerHeads(Player player, @Nullable String name) {

		ItemStack item = getPlayerHead(player, name);

		inventory.addItem(item);

	}


	/**
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @param lore Lore of the head
	 * @return ItemStack
	 */
	private @NotNull ItemStack getPlayerHead(Player player, @Nullable String name, @NotNull List<String> lore) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		if (name == null)
			meta.displayName(Component.text(UtilsMessage.onChat(player.getName())));

		else if (name.contains("%player%"))
			meta.displayName(Component.text(UtilsMessage.onChat(name.replace("%player%", player.getName()))));

		else
			meta.displayName(Component.text(UtilsMessage.onChat(name)));

		meta.setLore(UtilsMessage.onChat(lore));

		meta.setOwningPlayer(player);

		item.setItemMeta(meta);
		return item;
	}


	/**
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @return ItemStack
	 */
	private @NotNull ItemStack getPlayerHead(Player player, @Nullable String name) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		if (name == null)
			meta.displayName(Component.text(UtilsMessage.onChat(player.getName())));

		else if (name.contains("%player%"))
			meta.displayName(Component.text(UtilsMessage.onChat(name.replace("%player%", player.getName()))));

		else
			meta.displayName(Component.text(UtilsMessage.onChat(name)));


		meta.setOwningPlayer(player);

		item.setItemMeta(meta);
		return item;
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
	 * @param player Player to get the head
	 * @param name Name of the head (null = player name)
	 * @param page Page to get
	 */
	//	PageGUI for player heads
	private void PageGui(Player player, @Nullable String name, int page) {
		List<ItemStack> allItems = new ArrayList<>();

		for (int i = 0; i < 135; i++) {
			ItemStack item = getPlayerHead(player, name);
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
			inventory.addItem(is);
		}
	}


}
