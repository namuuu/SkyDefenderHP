package fr.namu.sd;

import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public class PlayerSD {

    private State state;
    private TeamSD team;

    private int kills = 0;

    private UUID prey = null;

    Scoreboard board;

    public PlayerSD() {
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public void setScoreBoard(Scoreboard board) {
        this.board = board;
    }
    public Scoreboard getScoreBoard() {
        return this.board;
    }

    public void setState(State state) {
        this.state = state;
    }
    public boolean isState(State state) {
        return this.state == state;
    }

    public void setTeam(TeamSD team) {this.team = team;}
    public Boolean isTeam(TeamSD team) {return this.team == team;}
    public TeamSD getTeam() {return this.team;}

    public void setPrey(Player player) { this.prey = player.getUniqueId(); }
    public Player getPrey() { return Bukkit.getPlayer(prey);}
    public Boolean isPreyNull() { return prey == null; }

    public void addKill() {this.kills++;}
    public int getKills() {return this.kills;}
}
