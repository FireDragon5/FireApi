package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.error.Valid;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Menu {


	private String title = "&bDefault Title";

	private Integer size = 9 * 3;

	private Player viewer;

	private boolean slotNumbersVisible;

	private final boolean buttonsRegistered = false;

	private final List<MenuButtons> registeredButtons = new ArrayList<>();

	public Menu() {

	}


//	Setters

	/**
	 * Sets the title of the menu
	 * @param title The title of the menu
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the size of the menu
	 * @param size The size of the menu
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Sets the slot numbers visible
	 */
	public void setSlotNumbersVisible() {
		this.slotNumbersVisible = true;
	}

	/**
	 * Sets the viewer of the menu
	 * @param viewer The viewer of the menu
	 */
	public void setViewer(Player viewer) {
		this.viewer = viewer;
	}




//	Getters

	/**
	 * Gets the title of the menu
	 * @return The title of the menu
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the size of the menu
	 * @return The size of the menu
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets if the slot numbers are visible
	 * @return If the slot numbers are visible
	 */
	public boolean getSlotNumbersVisible() {
		return slotNumbersVisible;
	}

	/**
	 * Gets the viewer of the menu
	 * @return The viewer of the menu
	 */
	public Player getViewer() {
		return viewer;
	}



//	Menu Messages
	/**
	 * Sends a message to the player
	 * @param player The player to send the message to
	 * @param message The message to send
	 */
	public static void playerMessage(Player player, String message) {
		UtilsMessage.sendMessage(player, message);
	}

	private void registerButtons() {
		registeredButtons.clear();

		List<MenuButtons> buttons = getButtons();

		if (buttons != null)
			registeredButtons.addAll(buttons);


	}

	private List<MenuButtons> getButtons() {
		return null;
	}


	/**
	 * Displays the menu to the player
	 * @param player The player to display the menu to
	 */
	public void displayMenu(Player player) {

		Valid.checkNotNull(size, "Size cannot be null");
		Valid.checkNotNull(title, "Title cannot be null");

		viewer = player;

		MenuCreator menuCreator = MenuCreator.createMenu(size, title);
		slotNumbers(menuCreator);


		menuCreator.displayMenu(player);
	}

	/**
	 * Displays the menu to the player
	 *
	 * @param menu The menu to display
	 */
	private void slotNumbers(MenuCreator menu) {
		if (slotNumbersVisible)
			for (int i = 0; i < menu.getSize(); i++) {
				ItemStack item = menu.getItem(i);

				if (item == null)
					menu.setItem(i, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE,
							1, (byte) 15), "&7" + i);
			}
	}


	public void handleMenu(InventoryClickEvent event) {

		event.setCancelled(true);

	}
}
