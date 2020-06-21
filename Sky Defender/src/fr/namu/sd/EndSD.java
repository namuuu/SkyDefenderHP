package fr.namu.sd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.ToolSD;

public class EndSD {
  private final MainSD main;
  
  private int i;
  private int atk;
  public EndSD(MainSD main1) {
    this.i = 40;
    this.atk = 0;
    this.main = main1;
  }
  
  public void check_victory() {
	this.atk = 0; 
	List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
	
	for(Integer ind = 0; ind<players.size(); ind++){
		UUID playername = players.get(ind);
		Player player = Bukkit.getPlayer(playername);
        PlayerSD psd = this.main.playersd.get(playername);
        
        if(psd.getCamp() == Camp.ATTAQUE && psd.isState(State.VIVANT) == true && player.isOnline() == true) {
        	this.atk++;
        	}
        if(psd.getCamp() == Camp.DEFENSE && psd.isState(State.VIVANT) == true && player.isOnline() == true) {
        	}       
        }
   if(atk == 0) {
	   this.win(Camp.DEFENSE);
   }
   
   if(ToolSD.MULTIPLE_TEAM.getValue() == true) {
	   if(this.main.ml.NbRed == 0 && this.main.ml.NbOrange == 0 && this.main.ml.NbYellow == 0 && this.main.ml.NbGreen == 0 && this.main.ml.NbAqua == 0 && this.main.ml.NbPink == 0 && this.main.ml.NbPurple == 0) {
		   this.win(Camp.DEFENSE);
	   }
   } else {
	   if(this.main.ml.NbAttaque == 0) {
		   this.win(Camp.DEFENSE);
	   }
   }
    
      } 
     
  
  
  public void win(Camp camp) {
    this.main.setState(StateSD.FIN);
    this.main.mjc.setLeaveRestricted(false);
    Bukkit.broadcastMessage(" ");
    while (this.i != -1) {
      List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
      for(Integer ind = 0; ind<players.size(); ind++){
          UUID playername = players.get(0);
          PlayerSD psd = this.main.playersd.get(playername);
          if (psd.getNbKill() == this.i && psd.getCamp() != Camp.NULL)
            Bukkit.broadcastMessage( psd.getCamp().getName() + "§7 | §c" + playername + "§7 » §6" + this.i + "§e kills"); 
          players.remove(0);
        } 
        this.i--;
      }
      /*while (!playersSD.isEmpty()) {
        String killername = playersSD.get(0);
        PlayerSD killerSD = this.main.playersd.get(killername);
        if (killerSD.getNbKill() == this.i)
          Bukkit.broadcastMessage(" •" + " | " + killername + "" + this.i + " kills"); 
        playersSD.remove(0);
      } 
      this.i--;
    }*/ 
    if (camp == Camp.DEFENSE) {
    	Bukkit.broadcastMessage(" ");
    	Bukkit.broadcastMessage("§6§lH.Party §7» §eLes §9Défenseurs §eremportent cette partie de §6Sky Defender §e!");
    	Bukkit.broadcastMessage(" ");
    } else {
    	Bukkit.broadcastMessage(" ");
    	Bukkit.broadcastMessage("§6§lH.Party §7» §eLes " + camp.getName() + " §eremportent cette partie de §6Sky Defender §e!");
    	Bukkit.broadcastMessage(" ");
    }
  }
  
  public void win(Player player) {
	  this.main.setState(StateSD.FIN);
	  this.main.mjc.setLeaveRestricted(false);
	  while (this.i != -1) {
	      List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
	      for(Integer ind = 0; ind<players.size(); ind++){
	          UUID playerid = players.get(0);
	          String playername = Bukkit.getPlayer(playerid).getName();
	          PlayerSD psd = this.main.playersd.get(playerid);
	          if (psd.getNbKill() == this.i && psd.getCamp() != Camp.NULL)
	            Bukkit.broadcastMessage( psd.getCamp().getName() + "§7 | §c" + playername + "§7 » §6" + this.i + "§e kills"); 
	          players.remove(0);
	        } 
	        this.i--;
	      } 
	    Bukkit.broadcastMessage(" ");
	    Bukkit.broadcastMessage("§6§lH.Party §7» §c" + player.getName() + " §eremporte cette partie de §6Sky Defender §e!");
	    Bukkit.broadcastMessage(" ");
	  }

  }

