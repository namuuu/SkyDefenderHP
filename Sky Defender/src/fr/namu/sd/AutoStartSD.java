package fr.namu.sd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.RulesSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TimerSD;

public class AutoStartSD extends BukkitRunnable{
	private final MainSD main;
	
	private int closeServer = 15;
	  
	  public AutoStartSD(MainSD main) {
	    this.main = main;
	  }
	  
	  public void run() {
		  World world = Bukkit.getWorld("world");
		  this.main.score.updateBoard();
		  WorldBorder wb = world.getWorldBorder();
		  
		  List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
		    while (!players.isEmpty()) {
		    	UUID playername = players.get(0);
		        Player player = Bukkit.getPlayer(playername);
		        		        		        		        		   
		        
		    	if(this.main.score.getTimer() == RulesSD.FINAL_HEAL.getValue()) {
		    		player.setHealth(player.getMaxHealth());
		    		player.setFoodLevel(20);
		    		player.sendMessage("Vous avez reçu le Final Heal !");
		    	}
		        
		    	players.remove(0);		    
		    }
		    
		    if (this.main.score.getTimer() == TimerSD.PVP.getValue() && !world.getPVP()) {
	            world.setPVP(true);
	            for (Player p : Bukkit.getOnlinePlayers()) {
	            	p.sendMessage("§cLe PVP est maintenant activé.");
	            	p.playSound(p.getLocation(), Sound.WOLF_GROWL, 1.0F, 20.0F);
	            }
	          } 
		    
		    if(this.main.score.getTimer() == 30) {
	        	Bukkit.broadcastMessage("§cVotre période de grâce est terminée !");
	        }
		  
		  if (this.main.score.getTimer() >= TimerSD.BORDER_BEGIN.getValue()) {
		      if (wb.getSize() == BorderSD.BORDER_MAX.getValue()) {
		        Bukkit.broadcastMessage("§cLa bordure commence se rétrécir !");
		      } 
		      if (wb.getSize() > BorderSD.BORDER_MIN.getValue()) {
		        wb.setSize(wb.getSize() - 0.5D);
		        wb.setWarningDistance((int)(wb.getSize() / 7.0D));
		      } 
		    } 
		  
		  if(this.main.isState(StateSD.FIN)) {
			  System.out.println(closeServer);
			  this.closeServer = closeServer - 1;
			  if(this.closeServer == 5 || this.closeServer == 4 || this.closeServer == 3 || this.closeServer == 2 || this.closeServer == 1)
				  Bukkit.broadcastMessage("§9Vous allez être redirigés vers le lobby dans §b" + this.closeServer + " §9secondes.");
			  if(this.closeServer == 0) {
				  this.main.mjc.leaveAllPlayer();
				  Bukkit.shutdown();
			  }
		  }
		  
		  this.main.score.addTimer();
	  }
}
