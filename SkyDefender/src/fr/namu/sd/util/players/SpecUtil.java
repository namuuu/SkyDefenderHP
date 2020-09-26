package fr.namu.sd.util.players;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpecUtil {

    private MainSD main;

    private List<Player> specs = new ArrayList<>();

    public SpecUtil(MainSD main) {
        this.main = main;
    }

    public void setSpec(Player player) {
        this.main.team.leaveTeam(player);
        this.main.mjc.setLeaveRestrictedPlayer(player, false);
        specs.add(player);
        if(player.getOpenInventory() != null) {
            player.closeInventory();
        }
    }

    public void removeSpec(Player player) {
        specs.remove(player);
        this.main.team.joinTeam(player, TeamSD.NULL);
    }

    public List<Player> getSpecs() {
        return this.specs;
    }
}
