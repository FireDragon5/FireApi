package me.firedraong5.firesapi.command;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class FireCommand {


	private String name;
	private String description;
	private String usage;
	private String permission;
	private String permissionMessage;
	private List<String> aliases;


	public FireCommand(String name, String description, String usage, String permission, String permissionMessage, List<String> aliases) {
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.permission = permission;
		this.permissionMessage = permissionMessage;
		this.aliases = aliases;
	}

	public FireCommand(String name) {
		this.name = name;
	}

	//	getters
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getUsage() {
		return usage;
	}

	public String getPermission() {
		return permission;
	}

	public String getPermissionMessage() {
		return permissionMessage;
	}

	public List<String> getAliases() {
		return aliases;
	}


	//	Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}


	public void setPermissionMessage(String permissionMessage) {
		this.permissionMessage = UtilsMessage.onChat(permissionMessage);
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}


	//	Check console
	public void isConsole(CommandSender sender) {
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(UtilsMessage.onChat("&cYou can't use this command in console"));
		}
	}

	//	Check the permission
	public boolean hasPermission(CommandSender sender) {
		return sender.hasPermission(getPermission());
	}


	public void onCommand(CommandSender sender, String[] args) {

	}


	//	Tab completer
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return null;
	}

}
