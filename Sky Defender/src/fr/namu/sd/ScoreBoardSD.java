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
            "", "§eNombre de joueurs connectés :", "", "", "", "", "", "", "", "", 
            "§7Évènement par H.Party" };
    score[3] = "§6" + this.getPlayerSize() + " §ejoueurs";
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
    	   "§7Vous êtes ", "§eTimer §7: §b", "", "§cAttaquants §7: §e", "§3Défenseurs §7: §e", "", "", "", "", "", 
           "§7Évènement organisé par H.Party"
    };
    score[0] = score[0] + psd.getCamp().getName();
    score[2] = score[2] + this.getTimer();    
    score[4] = score[4] + this.main.scoreboard.getTeam(Camp.ATTAQUE.getName()).getSize();
    score[5] = score[5] + this.main.scoreboard.getTeam(Camp.DEFENSE.getName()).getSize();
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
    String[] score = { 
        "", "", "", "", "", "", "", "", "", "", 
        "" };
	int n = 10;
    if (this.main.playersd.size() < 10)
      n = this.main.playersd.size(); 
    for (int i = 0; i < n; i++) {
    	
        score[i + 1] = "" + Bukkit.getPlayer(this.killscore.get(i)).getName() + " :" + ((PlayerSD)this.main.playersd.get(this.killscore.get(i))).getNbKill(); 
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
