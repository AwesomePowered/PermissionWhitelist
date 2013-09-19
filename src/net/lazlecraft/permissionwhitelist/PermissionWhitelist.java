package net.lazlecraft.permissionwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionWhitelist extends JavaPlugin implements Listener {
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent ev) {
		Player p = ev.getPlayer();
		if (!p.hasPermission("whitelisted.player")) {
			ev.disallow(Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', getConfig().getString("KickReason").replace("%PLAYER%", p.getName())));
		}
	}
}
