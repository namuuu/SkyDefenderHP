package fr.namu.sd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.RedLine.RankGestion.RankGestion;
import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.ToolSD;

public class AutoLaunchSD extends BukkitRunnable {
	
	  private final MainSD main;
	  
	  private int i;
	  
	  private int number = 0;
	  
	  public AutoLaunchSD(MainSD main) {
	    this.i = 0;
	    this.main = main;
	  }
	  
	  @SuppressWarnings("deprecation")
	public void run() {
		  this.main.score.updateBoard();
		  
	if (this.main.isState(StateSD.TELEPORTATION)) {	
	      World world = Bukkit.getWorld("world");
	      this.main.setState(StateSD.TELEPORTATION);
	      world.setTime(0L);
	      WorldBorder worldBorder = world.getWorldBorder();
	      worldBorder.setCenter(world.getSpawnLocation().getX(), world.getSpawnLocation().getZ());
	      worldBorder.setSize(BorderSD.BORDER_MAX.getValue());
	      worldBorder.setWarningDistance((int)(worldBorder.getSize() / 7.0D));
	      
	      List<UUID> atk = new ArrayList<>(this.main.playersd.keySet());
	      Collections.shuffle(atk);
	      while (atk.size() > number) {
	    	  UUID playername = atk.get(number);
	    	  PlayerSD psd = this.main.playersd.get(playername);
	    	  if(psd.getCamp() != Camp.ATTAQUE) {
	    		  atk.remove(number);
	    	  }
	    	  this.number++;
	    	  if (atk.isEmpty() || !(atk.size() > number))
	    		  break;
	      }
	      
	      List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
	      while (!players.isEmpty()) {
	    	  UUID playername = players.get(0);
	    	  
	    	  if (Bukkit.getPlayer(playername) != null) {
		          Player player = Bukkit.getPlayer(playername);
		          PlayerSD psd = this.main.playersd.get(playername);
		          player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 20.0F); 
		          player.setMaxHealth(20.0D);
		          player.setHealth(20.0D);
		          player.setExp(0.0F);
		          player.setLevel(0);
		          player.getInventory().clear();
		          player.getInventory().setHelmet(null);
		          player.getInventory().setChestplate(null);
		          player.getInventory().setLeggings(null);
		          player.getInventory().setBoots(null);
		          player.setNoDamageTicks(600);
		          player.setGameMode(GameMode.SURVIVAL);
		          for (PotionEffect po : player.getActivePotionEffects()) /* Reset des Effets de Potion   */
		            player.removePotionEffect(po.getType());
		          for (ItemStack it : this.main.stuffsd.getStartLoot()) { /* Attribution des Loots de Départ   */
		              player.getInventory().addItem(new ItemStack[] { it });
		            } 
		          if(ToolSD.PREY.getValue() == true) { /*  Scenario PREY      */
		        	  for (OfflinePlayer OfflinePlayerDefenseur : this.main.scoreboard.getTeam(Camp.DEFENSE.getName()).getPlayers()) {
		        		  Player Defenseur = Bukkit.getPlayer(OfflinePlayerDefenseur.getName());
		        		  PlayerSD DefenseurSD = this.main.playersd.get(Defenseur.getUniqueId());
		        		  DefenseurSD.setPrey(atk.get(0));
		        		  Defenseur.setMaxHealth(16.0D);
		        		  Defenseur.sendMessage("§6On vous a attribué une cible ! Tant que §e" + DefenseurSD.getPrey() + " §6n'est pas mort, vous n'aurez que 8 coeurs maximum !");
		        		  atk.remove(0);
		        	  }
		          }
		          if(ScenarioSD.CAT_EYES.getValue() == true)      /*Scenario CATEYES   */
		        	  player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 2147483647, 0, false, false)); 
		          
		          if(ToolSD.MULTIPLE_TEAM.getValue() == false) {
		        	  if(psd.getCamp() == Camp.ATTAQUE) {
		        		  this.eparpillementSolo(playername, this.i, "");
		        		  this.i++;
		        	  } 		        	  		          	         
		          }
		          if (psd.getCamp() == Camp.DEFENSE) {
	        		  Location tpdef = new Location(world, this.main.getDefSpawn().getBlockX(), this.main.getDefSpawn().getBlockY() + 1, this.main.getDefSpawn().getBlockZ());
	        		  player.teleport(tpdef);		        	  
	        	  } else if (this.main.mjc.isSpectator(player.getUniqueId())) {
	        		  player.setGameMode(GameMode.SPECTATOR);
	        		  this.main.mjc.setLeaveRestrictedPlayer(player, Boolean.valueOf(false));
	        	  }
		        }
	    	  players.remove(0);
	    	  if(players.isEmpty())
	    		  break;
	      }
	      
	      if(ToolSD.MULTIPLE_TEAM.getValue()) {
	    	  List<Camp> campValues = Arrays.asList(Camp.values());
	    	  Collections.shuffle(campValues);
	    	  for(Integer ind = 0; ind<campValues.size(); ind++){
	    		  Camp camp = campValues.get(ind);
	    		  if(camp == Camp.ATTAQUE || camp == Camp.DEFENSE) {
	    			  
	    		  } else {
	    			  eparpillementTeam(camp, i, "");
	    			  this.i++;
	    		  }
	    	  }
	      }
	      
	      for (Player p : Bukkit.getOnlinePlayers()) {
	        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 20.0F);
	         
	      } 
	      int x = (int)world.getSpawnLocation().getX();
	      int z = (int)world.getSpawnLocation().getZ();
	      int y = (int)world.getSpawnLocation().getY();
	      world.setSpawnLocation(x, y, z); // 184
	      for (int i = -16; i <= 16; i++) {
	        for (int j = -16; j <= 16; j++) {
	          (new Location(world, (i + x), y-1, (j + z))).getBlock().setType(Material.BARRIER);
	          (new Location(world, (i + x), y+3, (j + z))).getBlock().setType(Material.BARRIER);
	        } 
	        (new Location(world, (i + x), y  , (z - 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+1, (z - 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+3, (z - 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y  , (z + 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+1, (z + 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+2, (z + 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x - 16), y  , (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x - 16), y+1, (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x - 16), y+2, (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x + 16), y  , (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x + 16), y+1, (i + z))).getBlock().setType(Material.BARRIER); 
	        (new Location(world, (x + 16), y+2, (i + z))).getBlock().setType(Material.BARRIER);
	        	} 
	      world.setTime(0L);
	      this.main.setState(StateSD.GAME);
	      this.main.mjc.startGame();
	      this.main.mjc.setLeaveRestricted(true);
	      AutoStartSD start = new AutoStartSD(this.main);
	      start.runTaskTimer((Plugin)this.main, 0L, 20L);
	      cancel();
	    } 
	  }
	  
	  public void eparpillementSolo(UUID playername, double d, String message) {
		  if(this.main.rg == null)
			  this.main.rg = RankGestion.getRankg();
		    if (Bukkit.getPlayer(playername) != null) {
		      Player player = Bukkit.getPlayer(playername);
		      World world = player.getWorld();
		      WorldBorder wb = world.getWorldBorder();
		      double a = d * 2.0D * Math.PI / Bukkit.getOnlinePlayers().size();
		      int x = (int)Math.round(wb.getSize() / 3.0D * Math.cos(a) + world.getSpawnLocation().getX());
		      int z = (int)Math.round(wb.getSize() / 3.0D * Math.sin(a) + world.getSpawnLocation().getZ());
		      Location spawnp = new Location(world, x, (world.getHighestBlockYAt(x, z) + 1), z);
		      ((PlayerSD)this.main.playersd.get(playername)).setSpawn(spawnp.clone());
		      if (this.main.isState(StateSD.TELEPORTATION))
		        spawnp.setY(spawnp.getY() + 100.0D); 
		      player.setFoodLevel(20);
		      player.setSaturation(20.0F);
		      player.setRemainingAir(300);
		      player.setCompassTarget(spawnp);
		      player.setGameMode(GameMode.SURVIVAL);
		      player.sendMessage(message);
		      RankGestion.nL.acmArrayList.add(playername);
		      player.teleport(spawnp);
		    } 
		  }
	  
	  public void eparpillementTeam(Camp camp, double d, String message) {
		  if(this.main.rg == null)
			  this.main.rg = RankGestion.getRankg();
		  World world = Bukkit.getWorld("world");
	      WorldBorder wb = world.getWorldBorder();
	      double a = d * 2.0D * Math.PI / Bukkit.getOnlinePlayers().size();
	      int x = (int)Math.round(wb.getSize() / 3.0D * Math.cos(a) + world.getSpawnLocation().getX());
	      int z = (int)Math.round(wb.getSize() / 3.0D * Math.sin(a) + world.getSpawnLocation().getZ());
	      Location teamSpawn = new Location(world, x, (world.getHighestBlockYAt(x, z) + 1), z);
	      if (this.main.isState(StateSD.TELEPORTATION))
		        teamSpawn.setY(teamSpawn.getY() + 100.0D); 
	      List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
	      for(Integer ind = 0; ind<players.size(); ind++){
	    	  UUID playername = players.get(ind);
	    	  Player player = Bukkit.getPlayer(playername);
	    	  PlayerSD psd = this.main.playersd.get(playername);
	    	  if(psd.getCamp() == camp) {
	    		  player.setFoodLevel(20);
			      player.setSaturation(20.0F);
			      player.setRemainingAir(300);
			      player.setCompassTarget(teamSpawn);
			      player.setGameMode(GameMode.SURVIVAL);
			      player.sendMessage(message);
			      RankGestion.nL.acmArrayList.add(playername);
			      player.teleport(teamSpawn);
	    	  }
	      }
	  }
	  
	  public ItemStack metaGlass(String ItemName, Short Durability) {
		  ItemStack item = new ItemStack(Material.STAINED_GLASS, 1);
		  ItemMeta im = item.getItemMeta();
		  im.setDisplayName(ItemName);
		  item.setItemMeta(im);
		  item.setDurability(Durability);
		  return item;
	  } // metaGlass("", (Short))
	  
}

