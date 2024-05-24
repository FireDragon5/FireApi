package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.FiresApi;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	private final List<Button> buttons = new ArrayList<>();

	private int size = 9 * 3;
	private String title = "Menu";

	protected final void addButton(Button button) {
		this.buttons.add(button);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	protected final int getSize() {
		return size;
	}

	protected final String getTitle() {
		return title;
	}

	protected final void setSize(int size) {
		this.size = size;
	}

	protected final void setTitle(String title) {
		this.title = title;
	}

	public final void open(Player player) {
		Inventory inventory = Bukkit.createInventory(player, size, UtilsMessage.onChat(title));

		for (Button button : buttons) {
			inventory.setItem(button.getSlot(), button.getItem());
		}

		player.setMetadata("FireApiMenu", new FixedMetadataValue(FiresApi.getInstance(), this));
		player.openInventory(inventory);

	}


}
