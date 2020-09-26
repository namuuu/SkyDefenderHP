package fr.namu.sd.util;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PreyUtil {

    private MainSD main;

    public PreyUtil(MainSD main) {
        this.main = main;
    }

    public void enablePreys() {
        List<UUID> players = new ArrayList<>(this.main.playersd.keySet());
        Collections.shuffle(players);

        if(!ScenarioSD.PREY.getValue()) {
            return;
        }

        for(Player def : TeamSD.DEFENSE.getPlayers()) {
            PlayerSD defsd = this.main.playersd.get(def.getUniqueId());
            for(Integer ind = 0; ind < players.size(); ind++) {
                Player player = Bukkit.getPlayer(players.get(ind));
                PlayerSD psd = this.main.playersd.get(player.getUniqueId());

                if(psd.isTeam(TeamSD.DEFENSE) || this.main.spec.getSpecs().contains(player)) {
                    players.remove(player.getUniqueId());
                    ind--;
                }
            }

            while (defsd.isPreyNull()) {
                Player player = Bukkit.getPlayer(players.get(0));
                defsd.setPrey(player);
                players.remove(0);
                System.out.println("[SkyDefender - PREY] " + def.getName() + " a obtenu pour Prey " + player.getName());
            }
        }
    }

    public void verifyPrey(Player dead) {
        for(Player player : TeamSD.DEFENSE.getPlayers()) {
            PlayerSD psd = this.main.playersd.get(player.getUniqueId());
            if(psd.getPrey() == dead) {
                player.setMaxHealth(20.0D);
                player.setHealth(player.getHealth() + 4.0D);
                player.sendMessage("§7[§5Prey§7] §aVous avez tué votre cible avec Succès ! Vous récupérez donc toute votre vie.");
                System.out.println("[SkyDefender - PREY] " + player.getName() + " a tué sa Prey !");
            }
        }
    }
}
