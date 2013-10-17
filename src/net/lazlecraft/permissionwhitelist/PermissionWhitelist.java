package net.lazlecraft.permissionwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionWhitelist extends JavaPlugin implements Listener {
	
	public boolean Enabled;
	public String kickMessage;
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	public void reConf () {
		Enabled = getConfig().getBoolean("Enabled");
		kickMessage = getConfig().getString("KickReason");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (((commandLabel.equalsIgnoreCase("PermissionWhitelist")) || (commandLabel.equalsIgnoreCase("pw")))) {
			if (args.length == 0) {
			sender.sendMessage(ChatColor.GOLD + "This plugin is created by the almighty" + ChatColor.GREEN + " LaxWasHere");
			sender.sendMessage(ChatColor.GOLD + "Running version: " + this.getDescription().getVersion());
			if (sender.hasPermission("permissionwhitelist.reload")) {
				reConf();
				sender.sendMessage(ChatColor.RED + "PermissionWhitelist reloaded");
				} 
			} else if (args.length == 1 && sender.hasPermission("permissionwhitelist.admin")) {
					if (args[0].equalsIgnoreCase("enable")) {
						Enabled = true;
						this.getConfig().set("Enabled", true);
						this.saveConfig();
						sender.sendMessage(ChatColor.GREEN + "PermissionWhitelist Enabled!");
					} if (args[0].equalsIgnoreCase("disable")) {
						Enabled = false;
						this.getConfig().set("Enabled", false);
						this.saveConfig();
						sender.sendMessage(ChatColor.RED + "PermissionWhitelist Disabled!");
					}
				}
			}
		return true;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent ev) {
		Player p = ev.getPlayer();
		if (!p.hasPermission("whitelisted.player") && Enabled) {
			ev.disallow(Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', kickMessage.replace("%PLAYER%", p.getName())));
		}
	}
}
