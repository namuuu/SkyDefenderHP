package fr.namu.sd.commandsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.ToolSD;

public class DevCMD implements TabExecutor {
	final MainSD main;
	
	private String dev = "13e71f76-3166-43f9-821e-ea44f09b9315";
	
	public DevCMD(MainSD main) {
		this.main = main;
	}
	

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		
		if(args.length == 0) {
			player.sendMessage("Il n'y a aucune commande associée !");
			return true;
		}
		if (dev.equals(player.getUniqueId().toString())) {
			player.sendMessage("§cBarbare Imposteur ! Truand !"); 
			return true;	
	    }
			
		switch (args[0]) {	
		case "playerInfo":
			 if(args.length == 2) {
				 String playername = Bukkit.getPlayer(args[1]).getName();
				 PlayerSD psd = this.main.playersd.get(Bukkit.getPlayer(args[1]).getUniqueId());
				 if(psd.isState(State.VIVANT)) {
					 player.sendMessage("§e" + playername + " est considéré comme VIVANT");
				 } else if (psd.isState(State.MORT)) {
					 player.sendMessage("§e" + playername + " est considéré comme MORT");
				 } else if (this.main.mjc.isSpectator(Bukkit.getPlayer(args[1]).getUniqueId())) {
					 player.sendMessage("§e" + playername + " est considéré comme SPECTATEUR");
				 }
				 if (psd.getLastDamager() != null) {
					 player.sendMessage("§e" + playername + " a pour LastDamager " + Bukkit.getPlayer(psd.getLastDamager()).getName());
				 }
			 }
			
		case "teamInfo":
			if(args.length == 2) {
				String playername = Bukkit.getPlayer(args[1]).getName();
				if(this.main.scoreboard.getTeam(Camp.ATTAQUE.getName()).getPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
					player.sendMessage("§e" + playername + " appartient au Camp Attaque (ScoreBoard)");
				} else if (this.main.scoreboard.getTeam(Camp.DEFENSE.getName()).getPlayers().contains(Bukkit.getOfflinePlayer(args[1]))) {
					player.sendMessage("§e" + playername +  " appartient au Camp Defense (ScoreBoard)");
				} else {
					player.sendMessage("§e" + playername + " n'appartient à Aucun Camp (ScoreBoard)");
				}
				player.sendMessage("§eLe joueur appartient au Camp " +this.main.playersd.get(Bukkit.getPlayer(args[1]).getUniqueId()).getCamp().getName() + " (CampSD)");	
				player.sendMessage(" ");
				player.sendMessage(this.main.scoreboard.getTeam(Camp.ATTAQUE.getName()).getSize() + "");
			}
			return true;
			
			
		case "locInfo":
			Location banner = this.main.getBanner();
			Location tpChateau = this.main.getDefUp();
			Location tpSol = this.main.getDefDown();
			player.sendMessage("§6Position Bannière §eX : §b" + banner.getBlockX() + " §eY : §b" + banner.getBlockY() + " §eZ : §b" + banner.getBlockZ());
			player.sendMessage("§6Position TP (Château) §eX : §b" + tpChateau.getBlockX() + " §eY : §b" + tpChateau.getBlockY() + " §eZ : §b" + tpChateau.getBlockZ());
			player.sendMessage("§6Position TP (Wilderness) §eX : §b" + tpSol.getBlockX() + " §eY : §b" + tpSol.getBlockY() + " §eZ : §b" + tpSol.getBlockZ());
			
			
		case "timerInfo":
			player.sendMessage("§eTimer actuel : §b" + this.main.score.getTimer());
			
			
		case "preyInfo":
			if (args.length == 2) {
				String playername = Bukkit.getPlayer(args[1]).getName();
				PlayerSD psd = this.main.playersd.get(Bukkit.getPlayer(args[1]).getUniqueId());
				if(ToolSD.PREY.getValue() == false) {
					player.sendMessage("§cLe Scénario PREY n'a pas été activé !");
					return true;
				}
				if(psd.getPrey() != null) {
					player.sendMessage("§6" + playername + "§e a pour proie " + Bukkit.getPlayer(psd.getPrey()).getName());
				}
			}
		case "teamsInfo":
			player.sendMessage("ATTAQUANTS :" + Camp.ATTAQUE.getValue());
			player.sendMessage("DEFENSEURS :" + Camp.DEFENSE.getValue());
			player.sendMessage("ROUGES :" + Camp.RED.getValue());
			player.sendMessage("ORANGES :" + Camp.ORANGE.getValue());
			player.sendMessage("YELLOW" + Camp.YELLOW.getValue());
			player.sendMessage("GREEN :" + Camp.GREEN.getValue());
			player.sendMessage("AQUA :" + Camp.AQUA.getValue());
			player.sendMessage("PINK :" + Camp.PINK.getValue());
			player.sendMessage("PURPLE :" + Camp.PURPLE.getValue());			
			
	    default:
	    	return true;
		}
	}
	
	public void setNewDev(Player player) {
		dev = player.getUniqueId().toString();
	}
	
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
	    String[] tabe = { "setNewDev", "playerInfo", "teamInfo", "locInfo", "timerInfo", "preyInfo" };
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
