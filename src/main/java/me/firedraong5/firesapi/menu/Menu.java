package me.firedraong5.firesapi.menu;

import me.firedraong5.firesapi.FiresApi;
import me.firedraong5.firesapi.itemCreation.CustomItemCreator;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Menu {

	private final List<Button> buttons = new ArrayList<>();

	private int size = 9 * 3;
	private String title = "Menu";

	private final Menu parent;
	private boolean extraButtonsRegister = false;


	public Menu() {
		this(null);
	}


	public Menu(@Nullable Menu parent) {
		this.parent = parent;


	}





	protected final void addButton(Button button) {
		this.buttons.add(button);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	protected int getSize() {
		return size;
	}

	protected final String getTitle() {
		return title;
	}

	protected void setSize(int size) {
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

		if (parent != null && !extraButtonsRegister) {
			this.extraButtonsRegister = true;

			Button returnButton = new Button("Return", this.size - 1) {
				@Override
				public ItemStack getItem() {
					return CustomItemCreator.createItem(Material.BARRIER, UtilsMessage.onChat("&cReturn"));
				}

				@Override
				public void onClick(Player player) {
					try {

						Menu newMenuInstance = parent.getClass().getConstructor().newInstance();


						newMenuInstance.open(player);

					} catch (ReflectiveOperationException e) {
						e.printStackTrace();
					}
				}
			};

			inventory.setItem(returnButton.getSlot(), returnButton.getItem());
		}

		player.setMetadata("FireApiMenu", new FixedMetadataValue(FiresApi.getInstance(), this));
		player.openInventory(inventory);

	}


}
