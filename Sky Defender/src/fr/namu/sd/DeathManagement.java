package fr.namu.sd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.ToolSD;

public class DeathManagement {
  final MainSD main;
  
  public DeathManagement(MainSD main1) {
    this.main = main1;
  }
  
  public void killStep(String killername, String deadname) {

  }
  
  public void deathStep(Player deadname, Player killername) {
    World world = Bukkit.getWorld("world");
    PlayerSD puhg = this.main.playersd.get(deadname.getUniqueId());
    this.main.mjc.setLeaveRestrictedPlayer(deadname, false);
    this.main.ml.removeCamp(puhg);
    if (killername != null) {
      PlayerSD killeruhg = this.main.playersd.get(killername.getUniqueId());
      killeruhg.addOneKill();
      puhg.setKiller(killername.getUniqueId());
    } 
    puhg.setState(State.MORT);
    this.main.score.removePlayerSize();
    if (deadname != null) {
      deadname.teleport(puhg.getSpawn());
      for (ItemStack i : puhg.getItemDeath()) {
        if (i != null)
          world.dropItem(puhg.getSpawn(), i); 
      } 
      for (Player p : Bukkit.getOnlinePlayers())
        p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 1.0F, 20.0F); 
      deadname.setGameMode(GameMode.SPECTATOR);
    } 
    if (ToolSD.PREY.getValue() == true) {
    	this.checkPrey(deadname);
    }
    this.main.endsd.check_victory();
  }
  
  public void checkPrey(Player deadname) {
	  List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
	    while (!players.isEmpty()) {
	      UUID playername = players.get(0);
	      Player player = Bukkit.getPlayer(playername);
	      PlayerSD psd = this.main.playersd.get(playername);
	      
	      if(psd.getPrey() == deadname.getUniqueId()) {
	    	  player.setMaxHealth(20.0D);
	    	  player.setHealth(player.getHealth() + 4.0D);
	      }
	      
	      players.remove(0);
	    }
  }
}

