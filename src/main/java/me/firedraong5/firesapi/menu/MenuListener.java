package me.firedraong5.firesapi.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {


	@EventHandler
	public void onMenuClick(InventoryClickEvent event) {

		if (event.getClickedInventory() == null) return;

		if (event.getClickedInventory().getHolder() instanceof Menu) {

			Menu menu = (Menu) event.getClickedInventory().getHolder();

			if (event.getWhoClicked() == menu.getViewer()) {

				event.setCancelled(true);

				if (event.getCurrentItem() == null) return;

				if (event.getCurrentItem().getItemMeta() == null) return;

				event.getCurrentItem().getItemMeta().getDisplayName();

				menu.handleMenu(event);

			}

		}


	}


}
