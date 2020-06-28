package fr.namu.sd.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.RulesSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TimerSD;
import fr.namu.sd.enumsd.ToolSD;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MenuListener implements Listener {
	private final MainSD main;
	  
	  public MenuListener(MainSD main) {
	    this.main = main;
	  }
	  
public int NbAttaque = 0;

public int NbDefense = 0;

public int NbRed = 0;

public int NbOrange = 0;

public int NbYellow = 0;

public int NbGreen = 0;

public int NbAqua = 0;

public int NbPink = 0;

public int NbPurple = 0;
	
	
@EventHandler
	public void onPlayerClickEvent(PlayerInteractEvent event) {
	Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
		ItemStack handitem = event.getPlayer().getItemInHand();
  			if (handitem != null) {

			if(handitem.hasItemMeta()){

				String dispname = handitem.getItemMeta().getDisplayName();

				if(dispname != null){

		  				if(dispname.equals("§eChoisir une équipe !")) {
			 				event.setCancelled(true);
			  				event.getPlayer().performCommand("sd team"); 
		  				}
		  				if(dispname.equals("§eLes règles")) {
			  				event.setCancelled(true);
			 				event.getPlayer().performCommand("sd rules");
		  				}
		  				if(dispname.equals("§eParamétrer la partie")) {
			  				event.setCancelled(true);
			 				event.getPlayer().performCommand("h menu");
		  				}
		  				if(dispname.equals("§cPlacer le spawn des défenseurs")) {
			  				event.setCancelled(true);
			  				event.getPlayer().performCommand("h setDefSpawn");
		  				}
		  				if(dispname.equals("§cPlacer le téléporteur des défenseurs (sol)")) {
			  				event.setCancelled(true);
			  				event.getPlayer().performCommand("h setDefDown"); 
		  				}
		  				if(dispname.equals("§cPlacer le téléporteur des défenseurs (chateau)")) {
			 				event.setCancelled(true);
			  				event.getPlayer().performCommand("h setDefUp");
		 				}
		  				if(dispname.equals("§cQuitter le mode d'éditions des locations")) {
			  				event.setCancelled(true);
			 				event.getPlayer().setGameMode(GameMode.ADVENTURE);
			  				event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
			 				this.main.menusd.baseStuff(event.getPlayer());
		  				}
		  				if(dispname.equals("§cDéfinir la bannière")) {
			 				event.setCancelled(true);
			 				event.getPlayer().performCommand("h setBanner");
		  				}
				}
			}
		}
  		}
	}

	
	@EventHandler
	  private void onSousMenu(InventoryClickEvent event) {
	    InventoryView view = event.getView();
	    Inventory invent = event.getInventory();
	    Player player = (Player)event.getWhoClicked();
	    PlayerSD psd = (PlayerSD)this.main.playersd.get(player.getUniqueId());
	    ItemStack current = event.getCurrentItem();
	    if(event.getInventory().getType().equals(InventoryType.PLAYER) && player.getGameMode().equals(GameMode.ADVENTURE) && this.main.isState(StateSD.LOBBY) && current != null){
	    	event.setCancelled(true);	    	
	    }
	    if(current != null && current.hasItemMeta() == true && current.getItemMeta().getDisplayName() != null) {
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
	    if (invent.getName() == "§7Choisis ton Équipe !" && ToolSD.RANDOM_TEAM.getValue() == false) {
	    	event.setCancelled(true);	
	    	
	    	
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe des attaquants") {
	    	   if(Camp.ATTAQUE.getValue() > NbAttaque) {
	    		   this.removeCamp(psd);
	    		   this.NbAttaque++;
	    		   this.main.setCamp(player, Camp.ATTAQUE);
	    		   player.sendMessage("§7Vous avez rejoint l'équipe des §cAttaquants §7!");
	    		   setBanner(player, psd.getCamp());
	    	   }
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe des défenseurs") {
	    	   if(Camp.DEFENSE.getValue() > NbDefense ) {
	    	   this.removeCamp(psd);
	    	   this.NbDefense++;
	    	   this.main.setCamp(player, Camp.DEFENSE);
	    	   player.sendMessage("§7Vous avez rejoint l'équipe des §3Défenseurs §7!");
	    	   setBanner(player, psd.getCamp());
	    	   }
	       } 
	       if(current.getItemMeta().getDisplayName() == "§eNe choisir aucune équipe") {
	    	   this.removeCamp(psd);
	    	   this.main.setCamp(player, Camp.NULL);
	    	   player.sendMessage("§7Vous n'avez plus d'équipe §7!");
	    	   setBanner(player, psd.getCamp());
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe rouge") {
	    	   if(Camp.RED.getValue() > NbRed ) {
	    	   this.removeCamp(psd);
	    	   this.NbRed++;
	    	   this.main.setCamp(player, Camp.RED);	    	   
	    	   player.sendMessage("§7Vous avez rejoint l'Équipe §4Rouge §7!");	    	   
	    	   setBanner(player, psd.getCamp());
	    	   }
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe orange") {
	    	   if(Camp.ORANGE.getValue() > NbOrange ){
	    	   this.removeCamp(psd);
	    	   this.NbOrange++;
	    	   this.main.setCamp(player, Camp.ORANGE);
	    	   player.sendMessage("§7Vous avez rejoint l'Équipe §6Orange §7!");
	    	   setBanner(player, psd.getCamp());
	    	   }
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe jaune") {
	    	   if(Camp.YELLOW.getValue() > NbYellow) {
	    	   this.removeCamp(psd);
	    	   this.NbYellow++;
	    	   this.main.setCamp(player, Camp.YELLOW);
	    	   player.sendMessage("§7Vous avez rejoint l'Équipe §eJaune §7!");
	    	   setBanner(player, psd.getCamp());
	    	   }
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe verte") {
	    	   if(Camp.GREEN.getValue() > NbGreen) {
	    	   this.removeCamp(psd);
	    	   this.NbGreen++;
	    	   this.main.setCamp(player, Camp.GREEN);
	    	   player.sendMessage("§7Vous avez rejoint l'Équipe §aVerte §7!");
	    	   setBanner(player, psd.getCamp());
	    	   }
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe cyan") {
	    	   if(Camp.AQUA.getValue() > NbAqua) {
	    	   this.removeCamp(psd);
	    	   this.NbAqua++;
	    	   this.main.setCamp(player, Camp.AQUA);
	    	   player.sendMessage("§7Vous avez rejoint l'Équipe §bCyan §7!");
	    	   setBanner(player, psd.getCamp());
	    	   }
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe rose") {
	    	   if(Camp.PINK.getValue() > NbPink) {
	    	   this.removeCamp(psd);
	    	   this.NbPink++;
	    	   this.main.setCamp(player, Camp.PINK);
	    	   player.sendMessage("§7Vous avez rejoint l'Équipe §dRose §7!");
	    	   setBanner(player, psd.getCamp());
	    	   }
	       }
	       if(current.getItemMeta().getDisplayName() == "§eChoisir l'équipe violette") {
	    	   if(Camp.PURPLE.getValue() > NbPurple) {
	    	   this.removeCamp(psd);
	    	   this.NbPurple++;
	    	   this.main.setCamp(player, Camp.PURPLE);
	    	   player.sendMessage("§7Vous avez rejoint l'Équipe §5Violette §7!");
	    	   setBanner(player, psd.getCamp());
	    	   }
	       }
	       
	       
	       player.closeInventory();
	    } else if (invent.getName() == "§7Choisis ton Équipe !" && ToolSD.RANDOM_TEAM.getValue() == true) {
	    	player.sendMessage("§cLe scénario §9Random Team §c est activé ! Par conséquent, vous ne pouvez choisir votre équipe !");
	    	player.closeInventory();
	    }
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    if (invent.getName() == "§7Paramètres de la partie") {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName() == "§eModifier les Équipes") {
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§eModifier la Bordure") {
	    		this.main.menusd.borderEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§eModifier l'activation du PVP") {
	    		this.main.menusd.pvpEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§eModifier les locations de la partie") {
	    		this.main.menusd.locConfig(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§eModifier les limites d'Équipement") {
	    		this.main.menusd.enhancementEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§eModifier l'inventaire de départ") {
	    		player.getInventory().clear();
	    		player.closeInventory();
	    		player.setGameMode(GameMode.CREATIVE);
	    		for (ItemStack i : this.main.stuffsd.getStartLoot()) {
	                if (i != null)
	                  player.getInventory().addItem(new ItemStack[] { i }); 
	              } 
	    		TextComponent msg = new TextComponent("§aCliquez ici pour valider l'inventaire de départ !");
	    		msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/h lootStart"));
	    		player.sendMessage(" ");
	    		player.spigot().sendMessage(msg);
	    		player.sendMessage("§7Attention ! §cCette commande ne comprends pas l'armure.");
	    		player.sendMessage("§cSi vous souhaitez donner une armure à un jour, mettez-la dans votre inventaire.");
	    		player.sendMessage(" ");
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§eModifier les Scénarios de la partie") {
	    		this.main.menusd.ScenarioConfig(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains("Se mettre en tant que Spectateur de la Partie")) {
	    		this.switchSpec(player, event.getClick());    		
	    		this.main.menusd.configList(player);
	    		
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§aLancer la Partie !") {
	    		player.performCommand("h start");
	    	}
	    }
	    if (view.getTitle().equals("§7Paramètres du PVP")) {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName() == "§eRetour") {	  
	    		this.main.menusd.configList(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b- 30 Secondes" && event.getSlot() == 12) {
	    		TimerSD.PVP.setValue(TimerSD.PVP.getValue() - 30);
	    		this.main.menusd.pvpEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b+ 30 Secondes" && event.getSlot() == 14) {
	    		TimerSD.PVP.setValue(TimerSD.PVP.getValue() + 30);
	    		this.main.menusd.pvpEdit(player);
	    	}
	    }
	    if(view.getTitle().equals("§7Les règles de la partie")) {
	    	event.setCancelled(true);
	    }
	    	    	    	    	    
	    if (view.getTitle().equals("§7Paramètres de la Bordure")) {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName() == "§eRetour") {	  
	    		this.main.menusd.configList(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b- 50 Blocs" && event.getSlot() == 14) {	  
	    		editBorder(BorderSD.BORDER_MIN, -50);
	    		this.main.menusd.borderEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b+ 50 Blocs" && event.getSlot() == 16) {	  
	    		editBorder(BorderSD.BORDER_MIN, 50);
	    		this.main.menusd.borderEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b- 50 Blocs" && event.getSlot() == 10) {	  
	    		editBorder(BorderSD.BORDER_MAX, -50);
	    		this.main.menusd.borderEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b+ 50 Blocs" && event.getSlot() == 12) {	  
	    		editBorder(BorderSD.BORDER_MAX, 50);
	    		this.main.menusd.borderEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b- 30 Secondes") {	  
	    		TimerSD.BORDER_BEGIN.setValue(TimerSD.BORDER_BEGIN.getValue() - 30);
	    		this.main.menusd.borderEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName() == "§b+ 30 Secondes") {	  
	    		TimerSD.BORDER_BEGIN.setValue(TimerSD.BORDER_BEGIN.getValue() + 30);
	    		this.main.menusd.borderEdit(player);
	    	}
	    }
	    if (view.getTitle().equals("§7Paramètres des Enchantements")) {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName() == "§eRetour") {	  
	    		this.main.menusd.configList(player);
	    	}
	    	if(current.getType() == Material.DIAMOND) {
	    		increaseEnhancement(RulesSD.DIAMOND_ARMOR_NB, event.getClick());
	    		this.main.menusd.enhancementEdit(player);
	    	}
	    	if(current.getType() == Material.DIAMOND_SWORD) {
	    		increaseEnhancement(RulesSD.DIAMOND_SHARPNESS, event.getClick());
	    		this.main.menusd.enhancementEdit(player);
	    	}
	    	if(current.getType() == Material.DIAMOND_CHESTPLATE) {
	    		increaseEnhancement(RulesSD.DIAMOND_PROTECTION, event.getClick());
	    		this.main.menusd.enhancementEdit(player);
	    	}
	    	if(current.getType() == Material.IRON_SWORD) {
	    		increaseEnhancement(RulesSD.IRON_SHARPNESS, event.getClick());
	    		this.main.menusd.enhancementEdit(player);
	    	}
	    	if(current.getType() == Material.IRON_CHESTPLATE) {
	    		increaseEnhancement(RulesSD.IRON_PROTECTION, event.getClick());
	    		this.main.menusd.enhancementEdit(player);
	    	}
	    	if(current.getType() == Material.BOW) {
	    		increaseEnhancement(RulesSD.BOW_POWER, event.getClick());
	    		this.main.menusd.enhancementEdit(player);
	    	}	    		    	
	    }
	    if(view.getTitle().equals("§7Gestion des scénarios")) {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName() == "§eRetour") {	  
	    		this.main.menusd.configList(player);
	    	}
    		if(current.getType() == Material.IRON_ORE) {
    			switchScenario(ScenarioSD.CUT_CLEAN, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.FURNACE) {
    			switchScenario(ScenarioSD.FAST_SMELTING, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.EYE_OF_ENDER) {
    			switchScenario(ScenarioSD.CAT_EYES, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.WOOL) {
    			if(event.getClick() == ClickType.LEFT || event.getClick() == ClickType.SHIFT_LEFT) {
    				ToolSD.RANDOM_TEAM.setValue(false);
    			}
    			if(event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.SHIFT_RIGHT) {
    				ToolSD.RANDOM_TEAM.setValue(true);
    				List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
    			    while (!players.isEmpty()) {
    			      UUID playernames = players.get(0);
    			      Player playerss = Bukkit.getPlayer(playernames);
    			      PlayerSD psds = this.main.playersd.get(playernames);   			      
    			      psds.setCamp(Camp.NULL);
    		    	  playerss.setCustomName("§7[§fAucune Équipe§7] " + playerss.getName());
    		    	  playerss.setPlayerListName("§7[§fAucune Équipe§7] " + playerss.getName());
    		    	  playerss.sendMessage("§7Vous n'avez plus d'équipe §7!");
    		    	  playerss.getInventory().setItem(4, metaBanner(DyeColor.WHITE, "§eChoisir une équipe !"));   			      
    			      players.remove(0);
    			    }
    			}
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.BEACON) {
    			if(event.getClick() == ClickType.LEFT || event.getClick() == ClickType.SHIFT_LEFT) {
    				if(RulesSD.FINAL_HEAL.getValue() != 0)
    					RulesSD.FINAL_HEAL.setValue(RulesSD.FINAL_HEAL.getValue() - 60);
    			}
    			if(event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.SHIFT_RIGHT) {
    				RulesSD.FINAL_HEAL.setValue(RulesSD.FINAL_HEAL.getValue() + 60);
    			}
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.GOLDEN_APPLE) {
    			switchScenario(ScenarioSD.GOLDEN_HEAD, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.GOLD_PICKAXE) {
    			switchScenario(ScenarioSD.HASTEY_BOYS, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.RABBIT_FOOT) {
    			switchTool(ToolSD.PREY, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.FISHING_ROD) {
    			switchScenario(ScenarioSD.ROD_LESS, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.SADDLE) {
    			switchScenario(ScenarioSD.HORSE_LESS, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.BLAZE_POWDER) {
    			switchScenario(ScenarioSD.FIRE_LESS, event.getClick());
    			this.main.menusd.ScenarioConfig(player);
    		}
    		if(current.getType() == Material.EXP_BOTTLE) {
    			if(event.getClick() == ClickType.LEFT || event.getClick() == ClickType.SHIFT_LEFT) {
    				if(RulesSD.XP_BOOST.getValue() != 0)
    					RulesSD.XP_BOOST.setValue(RulesSD.XP_BOOST.getValue() - 1);
    			}
    			if(event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.SHIFT_RIGHT) {
    				RulesSD.XP_BOOST.setValue(RulesSD.XP_BOOST.getValue() + 1);
    			}
    			this.main.menusd.ScenarioConfig(player);
    		}
    		
    	}
	    if(view.getTitle().equals("§7Gestion des Équipes")) {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName().contains("Retour")) {	  
	    		this.main.menusd.configList(player);
	    	}
	    	if(current.getType() == Material.REDSTONE_BLOCK) {
	    		increaseTeams(Camp.ATTAQUE, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getType() == Material.LAPIS_BLOCK) {
	    		increaseTeams(Camp.DEFENSE, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains(Camp.RED.getName())) {
	    		increaseTeams(Camp.RED, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains(Camp.ORANGE.getName())) {
	    		increaseTeams(Camp.ORANGE, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains(Camp.YELLOW.getName())) {
	    		increaseTeams(Camp.YELLOW, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains(Camp.GREEN.getName())) {
	    		increaseTeams(Camp.GREEN, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains(Camp.AQUA.getName())) {
	    		increaseTeams(Camp.AQUA, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains(Camp.PINK.getName())) {
	    		increaseTeams(Camp.PINK, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	if(current.getItemMeta().getDisplayName().contains(Camp.PURPLE.getName())) {
	    		increaseTeams(Camp.PURPLE, event.getClick());
	    		this.main.menusd.teamEdit(player);
	    	}
	    	
	    }
	    if(view.getTitle().equals("§7Les règles de la partie")) {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName().equals("§eAfficher les Scénarios de la Partie")) {
	    		this.main.menusd.ScenarioList(player);
	    	}
	    }
	    if(view.getTitle().equals("§7Liste des scénarios")) {
	    	event.setCancelled(true);
	    	if(current.getItemMeta().getDisplayName().contains("Retour")) {	  
	    		this.main.menusd.rulesList(player);
	    	}
	    }
	    }
	}
	
	public void increaseEnhancement(RulesSD ench, ClickType click) {
		if(click == ClickType.LEFT || click == ClickType.SHIFT_LEFT) {
			if(ench.getValue() >= 1) {
			ench.setValue(ench.getValue() - 1);
			}
		} else if (click == ClickType.RIGHT || click == ClickType.SHIFT_RIGHT) {
			ench.setValue(ench.getValue() + 1);
		} else {
			
		}
	}
	
	public void switchScenario(ScenarioSD scenar, ClickType click) {
		if(click == ClickType.LEFT || click == ClickType.SHIFT_LEFT) {
			scenar.setValue(false);
			
		} else if (click == ClickType.RIGHT || click == ClickType.SHIFT_RIGHT) {
			scenar.setValue(true);
		} else {
			
		}
	}
	
	public void switchTool(ToolSD tool, ClickType click) {
		if(click == ClickType.LEFT || click == ClickType.SHIFT_LEFT) {
			tool.setValue(false);
			
		} else if (click == ClickType.RIGHT || click == ClickType.SHIFT_RIGHT) {
			tool.setValue(true);
		} else {
			
			}
		}

		public void switchSpec(Player player, ClickType click) {
			PlayerSD psd = this.main.playersd.get(player.getUniqueId());
			if(this.main.mjc.isSpectator(player.getUniqueId()) == true) {
				this.main.mjc.setSpectator(player.getUniqueId(), Boolean.valueOf(false));
				psd.setState(State.VIVANT);
	    		this.main.score.addPlayerSize();	
	    		player.setCustomName("§7[§fAucune Équipe§7] " + player.getName());
		    	player.setPlayerListName("§7[§fAucune Équipe§7] " + player.getName());
		    	player.getInventory().setItem(4, metaBanner(DyeColor.WHITE, "§eChoisir une équipe !")); 
			} else if (this.main.mjc.isSpectator(player.getUniqueId()) == false) {
				this.main.mjc.setSpectator(player.getUniqueId(), Boolean.valueOf(true));
				psd.setState(State.SPEC);
	    		psd.setCamp(Camp.NULL);
				this.main.score.removePlayerSize();	
				player.setCustomName("§7[§fSpectateur§7] " + player.getName());
				player.setPlayerListName("§7[§fSpectateur§7] " + player.getName());
				player.getInventory().setItem(4, metaExtra(Material.AIR, "", 1, new String[] {"", ""}));  
	    		this.removeCamp(psd);
					
			} else {
				
			}
		}
		
		public void editBorder(BorderSD border, int value) {
			if(border.getValue() + value > 0) {
				border.setValue(border.getValue() + value);
			}
		}
		
		public void increaseTeams(Camp camp, ClickType click) {
			if(click == ClickType.LEFT || click == ClickType.SHIFT_LEFT) {
				if(camp.getValue() >= 1) {
					if(camp == Camp.DEFENSE) {
						if(Camp.DEFENSE.getValue() > 1) {
							camp.setValue(camp.getValue() - 1);
						}
					} else {
						camp.setValue(camp.getValue() - 1);
					}
					List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
					while (!players.isEmpty()) {
						UUID playernames = players.get(0);
						Player playerss = Bukkit.getPlayer(playernames);
					PlayerSD psds = this.main.playersd.get(playernames);   
					if(psds.getCamp() != Camp.NULL) {
						if(psds.getCamp() == Camp.ATTAQUE) {
							this.NbAttaque--;
						} else if(psds.getCamp() == Camp.DEFENSE) {
							this.NbDefense--;
						} else if (psds.getCamp() == Camp.RED) {
							this.NbRed--;
						} else if (psds.getCamp() == Camp.ORANGE) {
							this.NbOrange--;
						} else if (psds.getCamp() == Camp.YELLOW) {
							this.NbYellow--;
						} else if (psds.getCamp() == Camp.GREEN) {
							this.NbGreen--;
						} else if (psds.getCamp() == Camp.AQUA) {
							this.NbAqua--;
						} else if (psds.getCamp() == Camp.PINK) {
							this.NbPink--;
						} else if (psds.getCamp() == Camp.PURPLE) {
							this.NbPurple--;
						}
					}
					psds.setCamp(Camp.NULL);
					playerss.setCustomName("§7[§fAucune Équipe§7] " + playerss.getName());
					playerss.setPlayerListName("§7[§fAucune Équipe§7] " + playerss.getName());
					playerss.getInventory().setItem(4, metaBanner(DyeColor.WHITE, "§eChoisir une équipe !"));   			      
					players.remove(0);
				}
			}
		} else if (click == ClickType.RIGHT || click == ClickType.SHIFT_RIGHT) {
			camp.setValue(camp.getValue() + 1);
			List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
		    while (!players.isEmpty()) {
		      UUID playernames = players.get(0);
		      Player playerss = Bukkit.getPlayer(playernames);
		      PlayerSD psds = this.main.playersd.get(playernames);   
		      if(psds.getCamp() != Camp.NULL) {
	    		   if(psds.getCamp() == Camp.ATTAQUE) {
	    			   this.NbAttaque--;
	    		   } else if(psds.getCamp() == Camp.DEFENSE) {
	    			   this.NbDefense--;
	    		   } else if (psds.getCamp() == Camp.RED) {
	    			   this.NbRed--;
	    		   } else if (psds.getCamp() == Camp.ORANGE) {
	    			   this.NbOrange--;
	    		   } else if (psds.getCamp() == Camp.YELLOW) {
	    			   this.NbYellow--;
	    		   } else if (psds.getCamp() == Camp.GREEN) {
	    			   this.NbGreen--;
	    		   } else if (psds.getCamp() == Camp.AQUA) {
	    			   this.NbAqua--;
	    		   } else if (psds.getCamp() == Camp.PINK) {
	    			   this.NbPink--;
	    		   } else if (psds.getCamp() == Camp.PURPLE) {
	    			   this.NbPurple--;
	    		   }
	    	   }
		      psds.setCamp(Camp.NULL);
	    	  playerss.setCustomName("§7[§fAucune Équipe§7] " + playerss.getName());
	    	  playerss.setPlayerListName("§7[§fAucune Équipe§7] " + playerss.getName());
	    	  playerss.getInventory().setItem(4, metaBanner(DyeColor.WHITE, "§eChoisir une équipe !"));   			      
		      players.remove(0);
		    }
		} else {
			
		}
	}
	
	public void setBanner(Player player, Camp camp) {
		if(camp == Camp.ATTAQUE)
			player.getInventory().setItem(4, metaBanner(DyeColor.RED, "§eChoisir une équipe !"));
	    if(camp == Camp.DEFENSE)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.BLUE, "§eChoisir une équipe !"));
	    if(camp == Camp.NULL)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.WHITE, "§eChoisir une équipe !"));
	    if(camp == Camp.RED)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.RED, "§eChoisir une équipe !"));
	    if(camp == Camp.ORANGE)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.ORANGE, "§eChoisir une équipe !"));
	    if(camp == Camp.YELLOW)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.YELLOW, "§eChoisir une équipe !"));
	    if(camp == Camp.GREEN)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.GREEN, "§eChoisir une équipe !"));
	    if(camp == Camp.AQUA)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.LIGHT_BLUE, "§eChoisir une équipe !"));
	    if(camp == Camp.PINK)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.PINK, "§eChoisir une équipe !"));
	    if(camp == Camp.PURPLE)
	    	player.getInventory().setItem(4, metaBanner(DyeColor.PURPLE, "§eChoisir une équipe !"));
	}
	
	public void removeCamp(PlayerSD psd) {
		if(psd.getCamp() != Camp.NULL) {
			if(psd.getCamp() == Camp.ATTAQUE) {
 			   this.NbAttaque--;
 		   } else if(psd.getCamp() == Camp.DEFENSE) {
 			   this.NbDefense--;
 		   } else if (psd.getCamp() == Camp.RED) {
 			   this.NbRed--;
 		   } else if (psd.getCamp() == Camp.ORANGE) {
 			   this.NbOrange--;
 		   } else if (psd.getCamp() == Camp.YELLOW) {
 			   this.NbYellow--;
 		   } else if (psd.getCamp() == Camp.GREEN) {
 			   this.NbGreen--;
 		   } else if (psd.getCamp() == Camp.AQUA) {
 			   this.NbAqua--;
 		   } else if (psd.getCamp() == Camp.PINK) {
 			   this.NbPink--;
 		   } else if (psd.getCamp() == Camp.PURPLE) {
 			   this.NbPurple--;
 		   }
 	   }
	}
	
	public ItemStack metaBanner(DyeColor color, String ItemName) {
		  ItemStack item = new ItemStack(Material.BANNER, 1);
		  BannerMeta bm = (BannerMeta) item.getItemMeta();
		  bm.setDisplayName(ItemName);
		  bm.setBaseColor(color);
		  item.setItemMeta(bm);
		  return item;
	  }
	
	public ItemStack metaExtra(Material m, String ItemName, int Amount, String[] lore) {
	    ItemStack item = new ItemStack(m, Amount);
	    ItemMeta im = item.getItemMeta();
	    im.setDisplayName(ItemName);
	    im.setLore(Arrays.asList(lore));
	    item.setItemMeta(im);
	    return item;
	  } // metaExtra(Material, "", 1, new String[] {"", ""})

}
