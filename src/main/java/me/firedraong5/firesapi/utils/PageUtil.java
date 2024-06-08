package me.firedraong5.firesapi.utils;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {


	/**
	 * Get the page items
	 *
	 * @param items Items to get
	 * @param page Page to get
	 * @param spaces Spaces to get
	 * @return Page items
	 */

	public static List<ItemStack> getPageItems(List<ItemStack> items, int page, int spaces) {

		int startBound = page * spaces;
		int endBound = startBound - spaces;

		List<ItemStack> pageItems = new ArrayList<>();
		for (int i = endBound; i < startBound; i++) {

			try {
				pageItems.add(items.get(i));
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}

		return pageItems;

	}


	/**
	 * Check if the page is valid
	 *
	 * @param items Items to check
	 * @param page Page to check
	 * @param spaces Spaces to check
	 * @return If the page is valid
	 */
	public static boolean isPageValid(List<ItemStack> items, int page, int spaces) {

		if (page <= 0) {
			return false;
		}

		int startBound = page * spaces;
		int endBound = startBound - spaces;

		return items.size() > endBound;


	}

}
