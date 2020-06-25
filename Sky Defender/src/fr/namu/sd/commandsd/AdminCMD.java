package fr.namu.sd.commandsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.ToolSD;

public class AdminCMD implements TabExecutor{
	final MainSD main;
	
	Random random = new Random();
	
	private int atknb = 0;
	private int defnb = 0;
	  
	  public AdminCMD(MainSD main1) {
	    this.main = main1;
	  }
	  
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  Player player = (Player)sender;
		  ItemStack[] stuffs, stuffd;
		    if (args.length == 0)
		      return false; 
		    if (!player.hasPermission("host.use")) {
		    	sender.sendMessage("§eVous n'avez pas les permissions nécessaires pour réaliser cette commande !");
		    	return true;
		    }	    	
		    switch (args[0]) {	    
		      case "menu":
		    	  this.main.menusd.configList(player);
		    	  return true;  
		      case "start":
		    	  if(this.main.getDefSpawn() == null || this.main.getDefUp() == null || this.main.getDefDown() == null || this.main.getBanner() == null) {
		    		  player.sendMessage("§cVous n'avez pas paramétré toutes les locations nécessaires !");
		    		  return true;
		    	  }
		    	  
		    	  /*
		    	  List<UUID> players1 = new ArrayList<>(this.main.playersd.keySet());
		    	  if(ToolSD.MULTIPLE_TEAM.getValue() == false) {
		    	  if (Camp.ATTAQUE.getValue() + Camp.DEFENSE.getValue() == players1.size()) {
				  while (!players1.isEmpty()) {
				    	UUID playername = players1.get(0);
				    	PlayerSD psd = this.main.playersd.get(playername);
				    	if(psd.getCamp() == Camp.ATTAQUE)
				    		this.atknb++;
				    	if(psd.getCamp() == Camp.DEFENSE)
				    		this.defnb++;
				    	players1.remove(0);
				    	}
		    	  }
		    	  }
		    	  List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
		    	  Collections.shuffle(players);
		    	  if (Camp.ATTAQUE.getValue() + Camp.DEFENSE.getValue() == players.size()) {
		    		  System.out.println(players.toString() + " " + players.size());
		    		for(Integer ind = 0; ind<players.size(); ind++){
				    	UUID playername = players.get(ind);
				    	Player player2 = Bukkit.getPlayer(playername);
				    	if(!this.main.mjc.isSpectator(player2)) {
				    	System.out.println(playername);
				    	PlayerSD psd = this.main.playersd.get(playername);
				    	if(psd.getCamp() == Camp.NULL) {
				    		if(Camp.ATTAQUE.getValue() > atknb) {
				    			psd.setCamp(Camp.ATTAQUE);
				    			player2.setCustomName("§7[§cAttaquant§7] " + player.getName());
				 	    	    player2.setPlayerListName("§7[§cAttaquant§7] " + player.getName());
				 	    	    player2.sendMessage("§7Vous avez rejoint l'équipe des §cAttaquants §7!");
				    			this.atknb++;
				    		} else {
				    			psd.setCamp(Camp.DEFENSE);
				    			player2.setCustomName("§7[§3Défenseur§7] " + player.getName());
				 	    	    player2.setPlayerListName("§7[§3Défenseur§7] " + player.getName());
				 	    	    player2.sendMessage("§7Vous avez rejoint l'équipe des §3Défenseurs §7!");
				 	    	    this.defnb++;
				    		}
				    	}
				    	if(psd.getCamp() != null) {
				    		this.main.scoreboard.getTeam(psd.getCamp().getName()).addPlayer(Bukkit.getOfflinePlayer(player2.getUniqueId()));
				    	}
				    	}
				    	
				    	}
		    	  } else {
		    		  Bukkit.broadcastMessage("§cLe nombre de joueurs dans les équipes n'est pas égale aux nombre de joueurs connectés !");
		    		  return true;
		    	  }*/
		    	  
		    	  List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
		    	  Collections.shuffle(players);
		    	  if(ToolSD.MULTIPLE_TEAM.getValue() == true) {
		    		  if(players.size() != Camp.RED.getValue() + Camp.ORANGE.getValue() + Camp.YELLOW.getValue() + Camp.AQUA.getValue() + Camp.GREEN.getValue() + Camp.PINK.getValue() + Camp.PURPLE.getValue() + Camp.DEFENSE.getValue()) {
		    			  Bukkit.broadcastMessage("§cLe nombre de joueurs dans les équipes n'est pas égale aux nombre de joueurs connectés !");
			    		  return true;
		    		  }
		    		  if(ToolSD.PREY.getValue() == true && Camp.RED.getValue() + Camp.ORANGE.getValue() + Camp.YELLOW.getValue() + Camp.AQUA.getValue() + Camp.GREEN.getValue() + Camp.PINK.getValue() + Camp.PURPLE.getValue() < Camp.DEFENSE.getValue()) {
			    		  if(atknb < defnb) {
			    			  Bukkit.broadcastMessage("§cLe Scénario §4Prey§c est actuellement activé, donc il faut mettre plus d'Attaquants que de défenseurs !");
			    			  return true;
			    		  }
			    	  }
		    		  for(Integer ind = 0; ind<players.size(); ind++){
		    			  UUID plname = players.get(ind);
		    			  Player pl = Bukkit.getPlayer(plname);
		    			  if(!this.main.mjc.isSpectator(pl)) {
		    				  PlayerSD psd = this.main.playersd.get(plname);
		    				  if(psd.getCamp() == Camp.NULL) {
		    					  if(Camp.DEFENSE.getValue() > this.main.ml.NbDefense) {
		    						  this.main.setCamp(pl, Camp.DEFENSE);
		    						  this.main.ml.NbDefense++;
		    					  }	else if(Camp.RED.getValue() > this.main.ml.NbRed) {
		    						  this.main.setCamp(pl, Camp.RED);
		    						  this.main.ml.NbRed++;
		    					  } else if (Camp.ORANGE.getValue() > this.main.ml.NbOrange) {
		    						  this.main.setCamp(pl, Camp.ORANGE);
		    						  this.main.ml.NbOrange++;
		    					  } else if (Camp.YELLOW.getValue() > this.main.ml.NbYellow) {
		    						  this.main.setCamp(pl, Camp.YELLOW);
		    						  this.main.ml.NbYellow++;
		    					  } else if (Camp.GREEN.getValue() > this.main.ml.NbGreen) {
		    						  this.main.setCamp(pl, Camp.GREEN);
		    						  this.main.ml.NbGreen++;
		    					  } else if (Camp.AQUA.getValue() > this.main.ml.NbAqua) {
		    						  this.main.setCamp(pl, Camp.AQUA);
		    						  this.main.ml.NbAqua++;
		    					  } else if (Camp.PINK.getValue() > this.main.ml.NbPink) {
		    						  this.main.setCamp(pl, Camp.PINK);
		    						  this.main.ml.NbPink++;
		    					  } else if (Camp.PURPLE.getValue() > this.main.ml.NbPurple) {
		    						  this.main.setCamp(pl, Camp.PURPLE);
		    						  this.main.ml.NbPurple++;
		    					  } 
		    				  }
		    			  }
		    		  }
		    		  
		    	  } else {
		    		  if(players.size() != Camp.ATTAQUE.getValue() + Camp.DEFENSE.getValue()) {
		    			  Bukkit.broadcastMessage("§cLe nombre de joueurs dans les équipes n'est pas égale aux nombre de joueurs connectés !");
			    		  return true;
		    		  }
		    		  if(ToolSD.PREY.getValue() == true && Camp.ATTAQUE.getValue() < Camp.DEFENSE.getValue()) {
			    		  if(atknb < defnb) {
			    			  Bukkit.broadcastMessage("§cLe Scénario §4Prey§c est actuellement activé, donc il faut mettre plus d'Attaquants que de défenseurs !");
			    			  return true;
			    		  }
			    	  }
		    		  for(Integer ind = 0; ind<players.size(); ind++){
		    			  UUID plname = players.get(ind);
		    			  Player pl = Bukkit.getPlayer(plname);
		    			  if(!this.main.mjc.isSpectator(pl)) {
		    				  PlayerSD psd = this.main.playersd.get(plname);
		    				  if(psd.getCamp() == Camp.NULL) {
		    					  if(Camp.ATTAQUE.getValue() > this.main.ml.NbAttaque) {
		    						  this.main.setCamp(pl, Camp.ATTAQUE);
		    						  this.main.ml.NbAttaque++;
		    					  } else {
		    						  this.main.setCamp(pl, Camp.DEFENSE);
		    						  this.main.ml.NbDefense++;
		    					  }
		    				  }
		    			  }
		    		  }
		    				  
		    	  }
		    	  		    	 		    	  		    	  		    	  
		    	  this.main.setState(StateSD.TELEPORTATION);
		    	  return true;
		      case "setDefSpawn":
		    	  this.main.setDefSpawn(player.getTargetBlock((Set<Material>)null, 10).getLocation());
		    	  player.sendMessage("§eLa §6zone d'apparition des Défenseurs§e a été définie aux coordonnées X: §b" + player.getTargetBlock((Set<Material>)null, 10).getX() + "§e Y: §b" + player.getTargetBlock((Set<Material>)null, 10).getY() + "§e Z: §b" + player.getTargetBlock((Set<Material>)null, 10).getZ());
		    	  return true;
		      case "setDefUp":
		    	  this.main.setDefUp(player.getTargetBlock((Set<Material>)null, 10).getLocation());
		    	  player.sendMessage("§eLa §6zone de téléportation des Défenseurs (château)§e a été définie aux coordonnées X: §b" + player.getTargetBlock((Set<Material>)null, 10).getX() + "§e Y: §b" + player.getTargetBlock((Set<Material>)null, 10).getY() + "§e Z: §b" + player.getTargetBlock((Set<Material>)null, 10).getZ());
		    	  return true;
		      case "setDefDown":
		    	  this.main.setDefDown(player.getTargetBlock((Set<Material>)null, 10).getLocation());	    	  
		    	  player.sendMessage("§eLa §6zone de téléportation des Défenseurs (sol)§e a été définie aux coordonnées X: §b" + player.getTargetBlock((Set<Material>)null, 10).getX() + "§e Y: §b" + player.getTargetBlock((Set<Material>)null, 10).getY() + "§e Z: §b" + player.getTargetBlock((Set<Material>)null, 10).getZ());
		    	  return true;
		      case "setBanner":
		    	  if(player.getTargetBlock((Set<Material>)null, 10).getType() == Material.STANDING_BANNER || player.getTargetBlock((Set<Material>)null, 10).getType() == Material.WALL_BANNER) {
		    		  this.main.setBanner(player.getTargetBlock((Set<Material>)null, 10).getLocation());  
		    		  player.sendMessage("§eLa §6Bannière§e a été définie aux coordonnées X: §b" + player.getTargetBlock((Set<Material>)null, 10).getX() + "§e Y: §b" + player.getTargetBlock((Set<Material>)null, 10).getY() + "§e Z: §b" + player.getTargetBlock((Set<Material>)null, 10).getZ());			    	  
		    	  }
		    	  return true;
		      case "lootStart":
		    	  if (!this.main.isState(StateSD.LOBBY)) {
		    		  player.sendMessage("§cLa partie a déjà commencé !");
		    		  return true;
		    	  }
		    		stuffs = player.getInventory().getContents();
		    		this.main.stuffsd.clearStartLoot();
		    		for (ItemStack i : stuffs) {
		    	          if (i != null)
		    	            this.main.stuffsd.addStartLoot(i); 
		    	        } 
		            player.getInventory().clear();
		            player.getInventory().setHelmet(null);
		            player.getInventory().setBoots(null);
		            player.getInventory().setChestplate(null);
		            player.getInventory().setLeggings(null);
		            player.setGameMode(GameMode.ADVENTURE);
		            this.main.menusd.baseStuff(player);
		            player.sendMessage("§aLe Stuff de départ a été défini avec succès !");
		    	  return true;
		      case "lootDeath":
		    	  if (!this.main.isState(StateSD.LOBBY)) {
		    		  player.sendMessage("§cLa partie a déjà commencé !");
		    		  return true;
		    	  }
		    		stuffd = player.getInventory().getContents();
		    		this.main.stuffsd.clearStartLoot();
		    		for (ItemStack i : stuffd) {
		    	          if (i != null)
		    	            this.main.stuffsd.addStartLoot(i); 
		    	        } 
		            player.getInventory().clear();
		            player.getInventory().setHelmet(null);
		            player.getInventory().setBoots(null);
		            player.getInventory().setChestplate(null);
		            player.getInventory().setLeggings(null);
		            player.setGameMode(GameMode.ADVENTURE);
		            this.main.menusd.baseStuff(player);
		    	  return true;
		      case "forcepvp":
		    	  World world = player.getWorld();
		    	  world.setPVP(true);
		    	  Bukkit.broadcastMessage("§cLe PVP est maintenant activé !");
		    	  return true;
		      case "fh":
		    	  for (Player p : Bukkit.getOnlinePlayers()) {
		    		  p.setHealth(p.getMaxHealth());
		    		  p.setFoodLevel(20);
		    		  p.sendMessage("§aVous avez reçu un Final Heal !");		    		  
		    	  }
		      case "border":
		    	  this.main.menusd.borderEdit(player);
		    	  return true;
		      case "setNewDev":
		    	  this.main.devcmd.setNewDev(player);
		    	  return true;
		    	  
		    } 
		    sender.sendMessage("commande ne figure pas dans notre liste des commandes. Pour plus d'informations : /h help");
		    return true;
		  }
		
	  
	  public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
		    String[] tabe = { "menu", "start", "forcepvp", "fh" };
		    List<String> tab = new ArrayList<>(Arrays.asList(tabe));
		    if (args.length == 0)
		      return tab; 
		    if (args.length == 1) {
		      for (int i = 0; i < tab.size(); i++) {
		        for (int j = 0; j < ((String)tab.get(i)).length() && j < args[0].length(); j++) {
		          if (((String)tab.get(i)).charAt(j) != args[0].charAt(j)) {
		            tab.remove(i);
		            i--;
		            break;
		          } 
		        } 
		      } 
		      return tab;
		    } 
		    return null;
		  }
}
