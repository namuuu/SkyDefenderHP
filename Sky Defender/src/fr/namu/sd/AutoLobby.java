package fr.namu.sd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.StateSD;

public class AutoLobby extends BukkitRunnable {
	
	private final MainSD main;
	
	public AutoLobby(MainSD main) {
		this.main = main;
	}
	
	public void run() {
		List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
		for(Integer ind = 0; ind<players.size(); ind++) {
			  Player playerColor = Bukkit.getPlayer(players.get(ind));
			  PlayerSD psdColor = this.main.playersd.get(players.get(ind));
			  
			  this.colorMovement(playerColor, psdColor.getCamp());
		}
	}
		
	    @SuppressWarnings("deprecation")
		public void colorMovement(Player player, Camp camp) {
			  World world = player.getWorld();
			  PlayerSD psd = this.main.playersd.get(player.getUniqueId());
			  Location ploc = player.getLocation();
			  Material block1 = world.getBlockAt(ploc.getBlockX(), ploc.getBlockY() - 1, ploc.getBlockZ()).getType();
			  Material block2 = world.getBlockAt(ploc.getBlockX(), ploc.getBlockY() - 2, ploc.getBlockZ()).getType();
			  
			  if(player.getGameMode() == GameMode.ADVENTURE && this.main.isState(StateSD.LOBBY)) {
				  if(block1 == Material.BARRIER || block2 == Material.BARRIER || block1 == Material.STAINED_GLASS || block2 == Material.STAINED_GLASS) {
					  Block changeBlock = world.getBlockAt(ploc.getBlockX(),  (int)world.getSpawnLocation().getY() - 1, ploc.getBlockZ());
					    if(psd.getCamp() == Camp.ATTAQUE) {
						  changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.RED.getData());
						} else if(psd.getCamp() == Camp.DEFENSE) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.BLUE.getData());
						} else if (psd.getCamp() == Camp.RED) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.RED.getData());
						} else if (psd.getCamp() == Camp.ORANGE) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.ORANGE.getData());
						} else if (psd.getCamp() == Camp.YELLOW) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.YELLOW.getData());
						} else if (psd.getCamp() == Camp.GREEN) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.GREEN.getData());
						} else if (psd.getCamp() == Camp.AQUA) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.LIGHT_BLUE.getData());
						} else if (psd.getCamp() == Camp.PINK) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.PINK.getData());
						} else if (psd.getCamp() == Camp.PURPLE) {
							changeBlock.setType(Material.STAINED_GLASS);
							changeBlock.setData(DyeColor.PURPLE.getData());
						}				  
				  }
			  }
		  }

}
