package fr.namu.sd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.ToolSD;

public class EndSD {
  private final MainSD main;
  
  public EndSD(MainSD main1) {
    this.main = main1;
  }
  
  public void check_victory() { 
	  if(ToolSD.MULTIPLE_TEAM.getValue() == true) {
		  if(this.main.ml.NbRed == 0 && this.main.ml.NbOrange == 0 && this.main.ml.NbYellow == 0 && this.main.ml.NbGreen == 0 && this.main.ml.NbAqua == 0 && this.main.ml.NbPink == 0 && this.main.ml.NbPurple == 0) {
			  this.win(Camp.DEFENSE);
		  }
	  } else if (ToolSD.MULTIPLE_TEAM.getValue() == false){
		  if(this.main.ml.NbAttaque == 0) {
			  this.win(Camp.DEFENSE);
		  }
	  }
  } 
     
  
  
  public void win(Camp camp) {
	int i = 40;
    this.main.setState(StateSD.FIN);
    this.main.mjc.setLeaveRestricted(false);
    Bukkit.broadcastMessage(" ");
    while (i != -1) {
      List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
      for(Integer ind = 0; ind<players.size(); ind++){
          UUID playername = players.get(0);
          PlayerSD psd = this.main.playersd.get(playername);
          if (psd.getNbKill() == i && psd.getCamp() != Camp.NULL)
            Bukkit.broadcastMessage( psd.getCamp().getName() + "§7 | §c" + playername + "§7 » §6" + i + "§e kills §7(" + psd.getDamage() + " dégâts donnés)"); 
          players.remove(0);
        } 
        i--;
      }
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
	  int i = 40;
	  this.main.setState(StateSD.FIN);
	  this.main.mjc.setLeaveRestricted(false);
	  while (i != -1) {
	      List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
	      for(Integer ind = 0; ind<players.size(); ind++){
	          UUID playerid = players.get(0);
	          Player playerkill = Bukkit.getPlayer(playerid);
	          PlayerSD psd = this.main.playersd.get(playerid);
	          if (psd.getNbKill() == i && psd.getCamp() != Camp.NULL)
	            Bukkit.broadcastMessage(psd.getCamp() + " §7| §e" +  playerkill.getDisplayName() + "§7 » §6" + i + "§e kills §7(" + psd.getDamage() + " dégâts donnés)"); 
	          players.remove(0);
	        } 
	        i--;
	      } 
	    Bukkit.broadcastMessage(" ");
	    Bukkit.broadcastMessage("§6§lH.Party §7» §c" + player.getName() + " §eremporte cette partie de §6Sky Defender §e!");
	    Bukkit.broadcastMessage(" ");
	  }
  
  public void hasardTP(List<Player> p, World world) {
	  for (Integer ind = 0; ind < p.size(); ind++) {
		  Location p0 = p.get(0).getLocation();		  
		  Player p1 = p.get(ind);
		  Player p2 = null;
		  if(ind == p.size() - 1) {
			  p2 = null;
		  } else {
			  p2 = p.get(ind + 1);
		  }
		  
		  if(p2 != null) {
			  p1.teleport(p2.getLocation());
		  } else {
			  p1.teleport(p0);
		  }
	  }
  }
  }

