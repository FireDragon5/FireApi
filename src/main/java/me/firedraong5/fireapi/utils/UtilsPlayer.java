package me.firedraong5.fireapi.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UtilsPlayer {


	static class PlayerStats{


//		Setters

		/**
		 * Will set the player health
		 * @param player - This is the player
		 * @param health - This is the health
		 */
		public static void setHealth(@NotNull Player player, int health) {
			player.setHealth(health);
		}

		/**
		 * Will set the player health
		 * @param player - This is the player
		 * @param health - This is the health
		 * @param message - This is the message
		 */
		public static void setHealth(@NotNull Player player, double health, String message) {
			player.setHealth(health);
			UtilsMessage.correctMessage(player, message);
		}

		/**
		 * Will set the player food
		 * @param player - This is the player
		 * @param food - This is the food
		 */
		public static void setFood(@NotNull Player player, int food) {
			player.setFoodLevel(food);
		}

		/**
		 * Will set the player food
		 * @param player - This is the player
		 * @param food - This is the food
		 * @param message - This is the message
		 */
		public static void setFood(@NotNull Player player, int food, String message) {
			player.setFoodLevel(food);
			UtilsMessage.correctMessage(player, message);
		}

		/**
		 * Will set the player saturation
		 * @param player - This is the player
		 * @param saturation - This is the saturation
		 */
		public static void setSaturation(@NotNull Player player, int saturation) {
			player.setSaturation(saturation);
		}

		/**
		 * Will set the player saturation
		 * @param player - This is the player
		 * @param saturation - This is the saturation
		 * @param message - This is the message
		 */
		public static void setSaturation(@NotNull Player player, int saturation, String message) {
			player.setSaturation(saturation);
			UtilsMessage.correctMessage(player, message);
		}

		/**
		 * Will set the player level
		 * @param player - This is the player
		 * @param level - This is the level
		 */
		public static void setLevel(@NotNull Player player, int level) {
			player.setLevel(level);
		}

		/**
		 * Will set the player level
		 * @param player - This is the player
		 * @param level - This is the level
		 * @param message - This is the message
		 */
		public static void setLevel(@NotNull Player player, int level, String message) {
			player.setLevel(level);
			UtilsMessage.correctMessage(player, message);
		}


//		Getter

		/**
		 * Will get the player health
		 * @param player - This is the player
		 * @return - This will return the player health
		 */
		public static double getHealth(@NotNull Player player) {
			return player.getHealth();
		}

		/**
		 * Will get the player food
		 * @param player - This is the player
		 * @return - This will return the player food
		 */
		public static int getFood(@NotNull Player player) {
			return player.getFoodLevel();
		}

		/**
		 * Will get the player saturation
		 * @param player - This is the player
		 * @return - This will return the player saturation
		 */
		public static int getSaturation(@NotNull Player player) {
			return (int) player.getSaturation();
		}

		/**
		 * Will get the player level
		 * @param player - This is the player
		 * @return - This will return the player level
		 */
		public static int getLevel(@NotNull Player player) {
			return player.getLevel();
		}

	}


	static class PlayerInventory{




	}








}
