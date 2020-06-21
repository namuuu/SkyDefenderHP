package fr.namu.sd.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.spigotmc.event.entity.EntityMountEvent;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.StateSD;
import fr.redline.event.WhiteListEvent;

public class PlayerListener implements Listener {
  private final MainSD main;
  
  private int def = 0;
  
  private String hostName = "";
  
  public PlayerListener(MainSD main1) {
    this.main = main1;
  }
  
  @EventHandler
  private void onWhiteList(WhiteListEvent event) {
	  if (event.isHost()) {
		  hostName = event.getPlPseudo().getName();
	  }
  }
  
  @EventHandler
  private void onJoin(PlayerJoinEvent event) {
    this.main.joinPlayer(event.getPlayer());
    Player player = event.getPlayer();
    PlayerSD puhg = (PlayerSD)this.main.playersd.get(player.getUniqueId());
    if (this.main.isState(StateSD.LOBBY)) {   
    	puhg.setCamp(Camp.NULL);
    	event.getPlayer().getInventory().setItem(4, metaBanner(DyeColor.WHITE, "�eChoisir une �quipe !"));
    	player.setCustomName("�7[�fAucune �quipe�7] " + player.getName());
 	    player.setPlayerListName("�7[�fAucune �quipe�7] " + player.getName());
 	    event.getPlayer().getInventory().setItem(7, metaExtra(Material.BOOK, "�eLes r�gles", 1, new String[] {""}));
 	    if(player.getName().equals(hostName)) {
 	    	event.getPlayer().getInventory().setItem(1, metaExtra(Material.NETHER_STAR, "�eParam�trer la partie", 1, new String[] {""}));
 	    }
    } else if (this.main.mjc.isSpectator(player.getUniqueId())) {
    	event.getPlayer().setGameMode(GameMode.SPECTATOR);
    	this.main.mjc.setLeaveRestrictedPlayer(player, Boolean.valueOf(false));
    } 
  }
  
  
  @EventHandler
  public void onJoinMessage(PlayerJoinEvent e) {
    if (this.main.isState(StateSD.LOBBY)) {
      e.setJoinMessage("�a+ �7� �e"+ e.getPlayer().getName());
    } else if (this.main.playersd.containsKey(e.getPlayer().getUniqueId()) && ((PlayerSD)this.main.playersd.get(e.getPlayer().getUniqueId())).isState(State.VIVANT)) {
      e.setJoinMessage("�c- �7� �e"+ e.getPlayer().getName());
    } 
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    Player player = e.getPlayer();
    Team playerteam = this.main.scoreboard.getTeam(player.getUniqueId().toString());
    PlayerSD psd = this.main.playersd.get(player.getUniqueId());
    this.main.score.removePlayerSize();
    if(playerteam != null) {
    	playerteam.unregister();
    }
    if(psd.getCamp() != Camp.NULL) {
		   if(psd.getCamp() == Camp.ATTAQUE) {
			   this.main.ml.NbAttaque--;
		   } else if(psd.getCamp() == Camp.DEFENSE) {
			   this.main.ml.NbDefense--;
		   }
	   }
    if(this.main.isState(StateSD.GAME)) {
    this.main.endsd.check_victory();
    }
    e.setQuitMessage("�c- �7� �e"+ e.getPlayer().getName());
  }
  
  
  @EventHandler
  public void onPlayerSneak(PlayerToggleSneakEvent e) {
	  World world = e.getPlayer().getWorld();
	  Player player = e.getPlayer();
	  PlayerSD psd = this.main.playersd.get(player.getUniqueId());
	  
	  if(this.main.getDefUp() != null && this.main.getDefDown() != null) {
		  Location loc = player.getLocation();
		  Location defup = new Location(world, this.main.getDefUp().getBlockX(), this.main.getDefUp().getBlockY() + 1, this.main.getDefUp().getBlockZ());
		  Location defuptp = new Location(world, this.main.getDefUp().getBlockX() + 0.5, this.main.getDefUp().getBlockY() + 2, this.main.getDefUp().getBlockZ() + 0.5);
		  Location defdown = new Location(world, this.main.getDefDown().getBlockX(), this.main.getDefDown().getBlockY() + 1, this.main.getDefDown().getBlockZ());
		  Location defdowntp = new Location(world, this.main.getDefDown().getBlockX() + 0.5, this.main.getDefDown().getBlockY() + 2, this.main.getDefDown().getBlockZ() + 0.5);
	  	  
		  if(loc.getBlockX() == defup.getBlockX() && loc.getBlockY() == defup.getBlockY() && loc.getBlockZ() == defup.getBlockZ() && player.isSneaking() == true && psd.getCamp() == Camp.DEFENSE) {
			  player.teleport(defdowntp);
			  player.setSneaking(false);
			  player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 20.0F);
		  } else if (loc.getBlockX() == defdown.getBlockX() && loc.getBlockY() == defdown.getBlockY() && loc.getBlockZ() == defdown.getBlockZ() && player.isSneaking() == true && psd.getCamp() == Camp.DEFENSE) {
			  player.teleport(defuptp);
			  player.setSneaking(false);
			  player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 20.0F);
		  }
	  }
  }
  
  @EventHandler 
  public void onPlayerDeath(PlayerDeathEvent event) {
	  Player player = (Player) event.getEntity();
	  
	  if (!this.main.playersd.containsKey(player.getUniqueId()))
	      return; 
	    PlayerSD puhg = (PlayerSD)this.main.playersd.get(player.getUniqueId());
	    if (!puhg.isState(State.VIVANT))
	      return; 
	    puhg.setSpawn(player.getLocation());
	    puhg.clearItemDeath();
	    player.spigot().respawn();
	    puhg.setItemDeath((ItemStack[])player.getInventory().getContents().clone());
	    if (player.getInventory().getHelmet() != null)
	      puhg.addItemDeath(player.getInventory().getHelmet()); 
	    if (player.getInventory().getChestplate() != null)
	      puhg.addItemDeath(player.getInventory().getChestplate()); 
	    if (player.getInventory().getLeggings() != null)
	      puhg.addItemDeath(player.getInventory().getLeggings()); 
	    if (player.getInventory().getBoots() != null)
	      puhg.addItemDeath(player.getInventory().getBoots()); 
	    if (player.getKiller() != null) {
	      this.main.death_manage.deathStep(player, player.getKiller());
	    } else {
	      this.main.death_manage.deathStep(player, null);
	    } 
  }
  
  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
	  
	  Entity entity = event.getEntity();
	  
	  if (entity instanceof Player && this.main.isState(StateSD.GAME) && this.main.score.getTimer() <= 30) {
	      event.setCancelled(true);
	      return;
	  }
	    if (entity instanceof Player && !this.main.isState(StateSD.GAME)) {
	      event.setCancelled(true);
	    return;
	  }
	    
	  if (entity instanceof Player && ScenarioSD.FIRE_LESS.getValue()) {
		  if (event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK || event.getCause() == DamageCause.LAVA) {
			  event.setCancelled(true);
			  return;
		  }
	  }
	    
	  if(!(event.getEntity() instanceof Player) || event.getCause() == DamageCause.ENTITY_ATTACK || ((Player)event.getEntity()).getHealth() - event.getFinalDamage() > 0 || !this.main.isState(StateSD.GAME))
		  return;	  
	  
	  event.setCancelled(true);
	  
	  Player player = (Player) event.getEntity();
	  
	  
	  
	  if (!this.main.playersd.containsKey(player.getUniqueId()))
	      return; 
	    PlayerSD puhg = (PlayerSD)this.main.playersd.get(player.getUniqueId());
	    if(puhg.getLastDamager() != null) {
	    	Bukkit.getServer().broadcastMessage("�b"+ player.getName() + " �ea �t� tu� par �b" + Bukkit.getPlayer(puhg.getLastDamager()).getName());
	    } else {
	    	Bukkit.getServer().broadcastMessage("�b"+ player.getName() + " �eest mort.");
	    }
	    if (!puhg.isState(State.VIVANT))
	      return; 
	    puhg.setSpawn(player.getLocation());
	    puhg.clearItemDeath();
	    puhg.setItemDeath((ItemStack[])player.getInventory().getContents().clone());
	    if (player.getInventory().getHelmet() != null)
	      puhg.addItemDeath(player.getInventory().getHelmet()); 
	    if (player.getInventory().getChestplate() != null)
	      puhg.addItemDeath(player.getInventory().getChestplate()); 
	    if (player.getInventory().getLeggings() != null)
	      puhg.addItemDeath(player.getInventory().getLeggings()); 
	    if (player.getInventory().getBoots() != null)
	      puhg.addItemDeath(player.getInventory().getBoots()); 
	    if (player.getKiller() != null) {
	      this.main.death_manage.deathStep(player, player.getKiller());
	    } else {
	    	if (puhg.getLastDamager() != null) {
	      this.main.death_manage.deathStep(player, Bukkit.getPlayer(puhg.getLastDamager()));
	    	} else {
	      this.main.death_manage.deathStep(player, null);
	    	}
	    } 	    
  }
  
  @EventHandler
  public void onPlayerDrop(PlayerDropItemEvent event) {
	  if (this.main.isState(StateSD.LOBBY)) {
		  event.setCancelled(true);
	  }
  }
  
  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
	  if(event.getEntity() instanceof Player) {
		  UUID lastHit = event.getEntity().getUniqueId();
		  if(event.getDamager() instanceof Player) {
			  UUID lastDamager = event.getDamager().getUniqueId();
			  this.main.playersd.get(lastHit).setLastDamager(lastDamager);
			  
		  } else if (event.getDamager() instanceof Arrow) {
			  Entity lastDamagerEntity = event.getDamager();
			  if(((Arrow)lastDamagerEntity).getShooter() instanceof Entity) {
				  lastDamagerEntity = (Entity) ((Arrow)lastDamagerEntity).getShooter();
				  if(lastDamagerEntity instanceof Player) {
					  Player lastDamager = (Player)lastDamagerEntity;
					  UUID lastDamagerID = lastDamager.getUniqueId();
					  this.main.playersd.get(lastHit).setLastDamager(lastDamagerID);
				  }
			  }
		  }
		  
	  }
	  
	  if(!(event.getEntity() instanceof Player) || ((Player)event.getEntity()).getHealth() - event.getFinalDamage() > 0 || !this.main.isState(StateSD.GAME))
		  return;
	  
	  event.setCancelled(true);
	  
	  Player player = (Player) event.getEntity();
	  String playername = player.getName();
	  
	  Entity damager = event.getDamager();
      if(damager instanceof Arrow) {

          if(((Arrow)damager).getShooter() instanceof Entity) {

          damager = (Entity) ((Arrow)damager).getShooter();

          }

      }
      if (!(damager instanceof Player)) {
    	  return;
      }
	  String killername = damager.getName();
	  Bukkit.getServer().broadcastMessage("�b"+ playername + " �ea �t� tu� par �b" + killername);

	  
	  if (!this.main.playersd.containsKey(player.getUniqueId()))
	      return; 
	    PlayerSD puhg = (PlayerSD)this.main.playersd.get(player.getUniqueId());
	    if (!puhg.isState(State.VIVANT))
	      return; 
	    puhg.setSpawn(player.getLocation());
	    puhg.clearItemDeath();
	    puhg.setItemDeath((ItemStack[])player.getInventory().getContents().clone());
	    if (player.getInventory().getHelmet() != null)
	      puhg.addItemDeath(player.getInventory().getHelmet()); 
	    if (player.getInventory().getChestplate() != null)
	      puhg.addItemDeath(player.getInventory().getChestplate()); 
	    if (player.getInventory().getLeggings() != null)
	      puhg.addItemDeath(player.getInventory().getLeggings()); 
	    if (player.getInventory().getBoots() != null)
	      puhg.addItemDeath(player.getInventory().getBoots()); 
	    if (damager != null) {
	      this.main.death_manage.deathStep(player, (Player)damager);
	      
	    } 
	  
  }
  
  @EventHandler
  public void onPlayerBreak(BlockBreakEvent e) {

	  

	  
	  if (this.main.getBanner() != null) {
		  int bannerX = this.main.getBanner().getBlockX();
		  int bannerY = this.main.getBanner().getBlockY();
		  int bannerZ = this.main.getBanner().getBlockZ();
		  if(e.getBlock().getLocation().getBlockX() == bannerX && e.getBlock().getLocation().getBlockY() == bannerY && e.getBlock().getLocation().getBlockZ() == bannerZ) {
			  List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
			  while (!players.isEmpty()) {
		    	PlayerSD psd = this.main.playersd.get(players.get(0));
		    	
		    	if (psd.getCamp() == Camp.DEFENSE && psd.isState(State.VIVANT) == true)
		    		this.def++;
		    	
		    		
		    	
		    
		    players.remove(0);
		    }
			  PlayerSD psd = this.main.playersd.get(e.getPlayer().getUniqueId());
			  if(def >= 1) {	
		    		e.setCancelled(true);
		    		if(psd.getCamp() == Camp.ATTAQUE) {
		    			e.getPlayer().sendMessage("�cTous les d�fenseurs ne sont pas morts !");
		    		} else if(psd.getCamp() == Camp.DEFENSE) {
		    			e.getPlayer().sendMessage("�cTu ne peux pas supprimer la banni�re en tant que d�fenseur !");
		    		}
		    	} else
			    if (def == 0) {
		    		this.main.endsd.win(e.getPlayer());
		    	}
		    
	  }
		  double dist = e.getBlock().getLocation().distance(this.main.getBanner());
		  if (dist<=2 && dist!=0) {
			  e.setCancelled(true);
		  }
	  }
	  
	  
  }
  
  @EventHandler
  public void onPlayerPlace(BlockPlaceEvent e) {
	  double dist = e.getBlock().getLocation().distance(this.main.getBanner());
	  
	  if (dist<= 2) {
		  e.setCancelled(true);
	  }
  }
  
  @EventHandler
  public void onPlayerEat(PlayerItemConsumeEvent e) {
	  ItemStack item = e.getItem();
	  if(item.hasItemMeta()) {
		  if(item.getItemMeta().getDisplayName().equals("�bGolden Head")) {
			  e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10*20, 1, false, false));
		  }
	  }
  }
  
  @EventHandler
  public void onEntityMount(EntityMountEvent event) {
	  if (ScenarioSD.HORSE_LESS.getValue() == true && event.getEntity() != null && event.getEntity() instanceof Player && event.getMount() != null && event.getMount() instanceof Horse) {
	      event.setCancelled(true); 
	  }
  }
  
  public ItemStack metaExtra(Material m, String ItemName, int Amount, String[] lore) {
	    ItemStack item = new ItemStack(m, Amount);
	    ItemMeta im = item.getItemMeta();
	    im.setDisplayName(ItemName);
	    im.setLore(Arrays.asList(lore));
	    item.setItemMeta(im);
	    return item;
	  } // metaExtra(Material, "", 1, new String[] {"", ""})
  
  public ItemStack metaBanner(DyeColor color, String ItemName) {
	  ItemStack item = new ItemStack(Material.BANNER, 1);
	  BannerMeta bm = (BannerMeta) item.getItemMeta();
	  bm.setDisplayName(ItemName);
	  bm.setBaseColor(color);
	  item.setItemMeta(bm);
	  return item;
  } //metaBanner(DyeColor, "");
  
}

