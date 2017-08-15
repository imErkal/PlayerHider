package ro.imerkal.PlayerHider;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	//Copyright @2017 ImErkal_
	//Nu aveti voie sa repostati sau sa distrubuiti acest plugin/cod
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void toggleGUI(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "§aPlayerHider §f-Menu");
		
		ItemStack arata = new ItemStack(Material.DIAMOND);
		ItemStack ascunde = new ItemStack(Material.GOLD_INGOT);
		
		ItemMeta arataMeta = arata.getItemMeta();
		ItemMeta ascundeMeta = ascunde.getItemMeta();
		
		arataMeta.setDisplayName("§aAfiseaza toti jucatori");
		ascundeMeta.setDisplayName("§cAscunde toti jucatori");
		
		arata.setItemMeta(arataMeta);
		ascunde.setItemMeta(ascundeMeta);
		
		inv.setItem(3, arata);
		inv.setItem(5, ascunde);
		
		p.openInventory(inv);
		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
	}
	
	@EventHandler 
	public void InvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName() != "§aPlayerHider §f-Menu") {
			return;
		}
		if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aAfiseaza toti jucatori")) {
			e.setCancelled(true);
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
			arataJucatori(p);
			p.sendMessage("§aAi afisat toti jucatori");
		}
		if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§cAscunde toti jucatori")) {
			e.setCancelled(true);
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
			ascundeJucatori(p);
			p.sendMessage("§cAi ascuns toti jucatori");
		}
	}
	
	public void arataJucatori(Player p) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			p.showPlayer(all);
			p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);
		}
	}
	
	public void ascundeJucatori(Player p) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(all);
			p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 1.0F);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("phide")) {
			if (!(p.hasPermission("phide.use"))) {
				return true;
			}
			if (!(sender instanceof Player)) {
				return true;
			}
			toggleGUI(p);
		}
		return true;
	}
}
