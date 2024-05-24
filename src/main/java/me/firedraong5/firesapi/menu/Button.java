package me.firedraong5.firesapi.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

	private final String name;
	private final int slot;


	public Button(String name, int slot) {
		this.name = name;
		this.slot = slot;
	}

	public String getName() {
		return name;
	}

	public abstract ItemStack getItem();


	public abstract void onClick(Player player);

	public int getSlot() {
		return slot;
	}


}
