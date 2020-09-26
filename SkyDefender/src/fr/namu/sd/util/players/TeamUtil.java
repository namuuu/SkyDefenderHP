package fr.namu.sd.util.players;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamUtil {

    private MainSD main;

    public TeamUtil(MainSD main) {
        this.main = main;
    }

    public void joinTeam(Player player, TeamSD team) {
        if(team.getSize() <= team.getPlayers().size()) {
            player.sendMessage(ChatColor.RED + "Il y a trop de joueurs dans cette équipe !");
            return;
        }
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());

        if(psd.getTeam() != null) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam(psd.getTeam().getName()).removeEntry(player.getName());
            psd.getTeam().removePlayer(player);
        }

        if(this.main.spec.getSpecs().contains(player)) {
            this.main.spec.removeSpec(player);
        }
        psd.setTeam(team);
        team.addPlayer(player);
        player.setPlayerListName("§7[" + team.getName() + "§7] " + player.getName());
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team.getName()).addEntry(player.getName());

        player.getInventory().setItem(4, this.main.item.bannerItem(team.getColor(), "§eChoisir une équipe"));

        if (team == TeamSD.NULL) {
            return;
        }
        if(team == TeamSD.DEFENSE || team == TeamSD.ATTAQUE) {
            player.sendMessage(ChatColor.GRAY + "Vous avez rejoint le camp des " + team.getName());
            return;
        }
        player.sendMessage(ChatColor.GRAY + "Vous avez rejoint l'" + team.getName());
    }

    public void leaveTeam(Player player) {
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());

        if(psd.getTeam() != null) {
            psd.getTeam().removePlayer(player);
        }
        if(this.main.spec.getSpecs().contains(player)) {
            player.sendMessage(ChatColor.GRAY + "Vous êtes déjà Spectateur !");
            return;
        }

        player.setPlayerListName("§7[SPEC] " + player.getName());

        player.getInventory().setItem(4, this.main.item.basicItem(Material.BARRIER, "§eQuitter le mode Spectateur", 1, null));

        player.sendMessage(ChatColor.GRAY + "Vous avez rejoint le mode Spectateur !");
    }

    public int attackersNumber() {
        if(ScenarioSD.MULTIPLE_TEAM.getValue()) {
            return TeamSD.RED.getPlayers().size()
                    + TeamSD.ORANGE.getPlayers().size()
                    + TeamSD.YELLOW.getPlayers().size()
                    + TeamSD.GREEN.getPlayers().size()
                    + TeamSD.AQUA.getPlayers().size()
                    + TeamSD.PINK.getPlayers().size()
                    + TeamSD.PURPLE.getPlayers().size();
        }
        return TeamSD.ATTAQUE.getPlayers().size();
    }
}
