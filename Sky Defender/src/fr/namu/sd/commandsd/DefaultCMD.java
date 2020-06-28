package fr.namu.sd.commandsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.namu.sd.MainSD;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class DefaultCMD implements TabExecutor{
	final MainSD main;
	  
	public DefaultCMD(MainSD main1) {
	    this.main = main1;
	  }

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
	    if (args.length == 0)
	      return false; 
	    switch (args[0]){
	      case "team" :	        
	        if (sender instanceof Player)
	          this.main.menusd.teamList(player); 
	        return true;
	      case "rules" :
	    	if(sender instanceof Player)
	    		this.main.menusd.rulesList(player);
	    	return true;
	      case "bug" : 
	    	  //https://forms.gle/xk1vyTqqEfgRFrgT6
	    	  TextComponent msg = new TextComponent("§9Vous avez rencontré un bug au cours de votre partie ? Pour le signaler, veuillez cliquer §e§l§nICI");
	    	  msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://forms.gle/xk1vyTqqEfgRFrgT6"));

	    	  player.spigot().sendMessage(msg);
	      case "inv" :
	    	  if(sender instanceof Player && this.main.mjc.isSpectator((((Player) sender).getUniqueId()))) {
	    		  try {
	    			  Player Sender = (Player)sender;
	    			  Player Select = Bukkit.getPlayer(args[1]);
	    			  this.main.menusd.playerInvSee(Sender, Select);
	    		  } catch (Exception e) {
	    			  player.sendMessage("§cCe joueur est déconnecté ou n'existe pas !");
	    		  }
	    	  }
	    		  
	    		  
	    } 
	    sender.sendMessage("commande ne figure pas dans notre liste des commandes. Pour plus d'informations : /sd help");
	    return true;
	  }
	
	
	
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
	    String[] tabe = { "team", "rules", "bug" };
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
