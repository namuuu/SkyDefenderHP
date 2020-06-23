package fr.namu.sd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.ToolSD;
import fr.namu.sd.listener.PlayerListener;

public class ScoreBoardSD {
  final MainSD main;
  
  private int player = 0;
  
  private int timer = 0;
  
  private int role = 0;
  
  private final List<UUID> killscore = new ArrayList<>();
  
  public ScoreBoardSD(MainSD main) {
    this.main = main;
  }
  
  public void updateScoreBoard1(FastBoard board) {
    String[] score = { 
            "", "§eNombre de joueurs connectés :", "", "", "§eHost : §a", "", "§bVous jouez sur la Map :", "§e", "", "", 
            "§7Évènement par H.Party" };
    score[2] = "§6" + this.getPlayerSize() + " §ejoueurs";
    if(PlayerListener.hostName != null && Bukkit.getPlayer(PlayerListener.hostName) != null) {
    	score[4] = score[4] + Bukkit.getPlayer(PlayerListener.hostName).getName();
    } else {
    	score[4] = score[4] + "§cAucun Host !";
    }
    score[7] = score[7] + this.main.getMapName();
    for (int i = 0; i < score.length; i++) {
      StringBuilder sb = new StringBuilder();
      sb.append(score[i]);
      if (sb.length() > 30)
        sb.delete(29, sb.length() - 1); 
      score[i] = sb.toString();
    } 
    board.updateLines(score);
  }
  
  public void updateScoreBoard2(FastBoard board) {
	Player player = board.getPlayer();
	PlayerSD psd = this.main.playersd.get(player.getUniqueId());
    String[] score = {
    	   "§7Vous êtes ", "§eTimer §7: §b", "", "§cAttaquants §7: §e", "§3Défenseurs §7: §e", "", "§aDistance au Château :", "§e", "", "§bVous avez : §e", 
           "§7Évènement organisé par H.Party"
    };
    score[0] = score[0] + psd.getCamp().getName();
    score[1] = score[1] + this.main.conversion(this.getTimer());
    if(ToolSD.MULTIPLE_TEAM.getValue() == false) {
    	score[3] = score[3] + this.main.ml.NbAttaque;
    } else {
    	int NbAttaquantsSB = this.main.ml.NbRed + this.main.ml.NbOrange + this.main.ml.NbYellow + this.main.ml.NbGreen + this.main.ml.NbAqua + this.main.ml.NbPink + this.main.ml.NbPurple;
    	score[3] = score[3] + NbAttaquantsSB;
    }
    score[4] = score[4] + this.main.ml.NbDefense;
    score[7] = score[7] + updatearrow(player, this.main.getDefUp()) + " blocs";
    score[9] = score[9] + psd.getNbKill() + " kills";
    for (int i = 0; i < score.length; i++) {
        StringBuilder sb = new StringBuilder();
        sb.append(score[i]);
        if (sb.length() > 30)
          sb.delete(29, sb.length() - 1); 
        score[i] = sb.toString();
      } 
    board.updateLines(score);
  }
  
  public void updateScoreBoard3(FastBoard board) {
	  int i = 40;
	  int placement = 0;
    String[] score = { 
        "", "", "", "", "", "", "", "", "", "", 
        "" };
    while (i != -1) {
	      List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
	      for(Integer ind = 0; ind<players.size(); ind++){
	          UUID playerid = players.get(0);
	          String playername = Bukkit.getPlayer(playerid).getName();
	          PlayerSD psd = this.main.playersd.get(playerid);
	          if (psd.getNbKill() == i && psd.getCamp() != Camp.NULL && placement !=10) {
	            score[placement] = playername + " : " + psd.getNbKill();
	            placement = placement + 1;
	          }
	          players.remove(0);
	        } 
	        i = i-1;
	        if(i == -1)
	        	break;
	      } 
    board.updateLines(score);
  }
  
  public void getKillCounter() {
    for (UUID p : this.main.playersd.keySet()) {
      int i = 0;
      while (i < this.killscore.size() && ((PlayerSD)this.main.playersd.get(p)).getNbKill() < ((PlayerSD)this.main.playersd.get(this.killscore.get(i))).getNbKill())
        i++; 
      this.killscore.add(i, p);
    } 
  }
  
  public String middistance(Player player) {
    String retour;
    World world = player.getWorld();
    Location plocation = player.getLocation();
    plocation.setY(world.getSpawnLocation().getY());
    double distance = plocation.distance(world.getSpawnLocation());
    if (distance < 300.0D) {
      retour = "0 300 blocs";
    } else if (distance < 600.0D) {
      retour = "300 600 blocs";
    } else if (distance < 900.0D) {
      retour = "600 900 blocs";
    } else if (distance < 1200.0D) {
      retour = "900 1200 blocs";
    } else {
      retour = "> 1200 blocs";
    } 
    return retour;
  }
  
  
  public void updateBoard() {
    for (FastBoard board : this.main.boards.values()) {
      if (this.main.isState(StateSD.LOBBY)) {
        updateScoreBoard1(board);
        continue;
      } 
      if (!this.main.isState(StateSD.FIN)) {
        updateScoreBoard2(board);
        continue;
      } 
      this.main.score.updateScoreBoard3(board);
    } 
  }
  
  private String updatearrow(Player player, Location cible) {
	    String flech;
	    Location plocation = player.getLocation();
	    plocation.setY(cible.getY());
	    Vector dirToMiddle = cible.toVector().subtract(player.getEyeLocation().toVector());
	    Integer distance = Integer.valueOf((int)Math.round(cible.distance(plocation)));
	    Vector playerDirection = player.getEyeLocation().getDirection();
	    double angle = dirToMiddle.angle(playerDirection);
	    double det = dirToMiddle.getX() * playerDirection.getZ() - dirToMiddle.getZ() * playerDirection.getX();
	    angle *= Math.signum((float)Math.round(det));
	    if (angle > -0.5235987755982988D && angle < 0.5235987755982988D) {
	      flech = "" ;
	    } else if (angle > -1.0471975511965976D && angle < -0.5235987755982988D) {
	      flech = "" ;
	    } else if (angle < 1.0471975511965976D && angle > 0.5235987755982988D) {
	      flech = "" ;
	    } else if (angle > 1.0471975511965976D && angle < 2.0943951023931953D) {
	      flech = "" ;
	    } else if (angle < -1.0471975511965976D && angle > -2.0943951023931953D) {
	      flech = "" ;
	    } else if (angle < -2.0943951023931953D && angle > -2.6179938779914944D) {
	      flech = "" ;
	    } else if (angle > 2.0943951023931953D && angle < 2.6179938779914944D) {
	      flech = "" ;
	    } else {
	      flech = "" ;
	    } 
	    return distance + flech;
	  }
  
  public int getRole() {
    return this.role;
  }
  
  public void setRole(int role) {
    this.role = role;
  }
  
  public int getTimer() {
    return this.timer;
  }
  
  public void addTimer() {
    this.timer++;
  }
  
  public int getPlayerSize() {
    return this.player;
  }
  
  public void removePlayerSize() {
    this.player--;
  }
  
  public void addPlayerSize() {
    this.player++;
  }
  
}
