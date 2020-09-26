package fr.namu.sd.util;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TeamSD;
import fr.redline.serverclient.enumerator.PartyState;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WinUtil {

    private MainSD main;

    public WinUtil(MainSD main) {
        this.main = main;
    }

    public void verifyWin() {
        if(this.main.team.attackersNumber() == 0) {
            defWin();
        }
    }

    public void teamWin(TeamSD team) {
        displayKills();
        if(team == TeamSD.ATTAQUE) {
            Bukkit.broadcastMessage("§6§lH.Party §7» §eLes §9Attaquants §eremportent cette partie de §6Sky Defender §e!");
        } else {
            Bukkit.broadcastMessage("§6§lH.Party §7» §eL'Équipe " + team.getName() + " §eremportent cette partie de §6Sky Defender §e!");
        }
        Bukkit.broadcastMessage(" ");
        endGame();
    }

    private void defWin() {
        displayKills();
        Bukkit.broadcastMessage("§6§lH.Party §7» §eLes §9Défenseurs §eremportent cette partie de §6Sky Defender §e!");
        Bukkit.broadcastMessage(" ");
        endGame();

    }

    private void endGame() {
        this.main.mjc.setLeaveRestricted(false);
        this.main.info.setState(StateSD.END);
        this.main.score.updateBoard();
        this.main.getServer().getScheduler().scheduleSyncDelayedTask(this.main, () ->
                this.main.mjc.leaveAllPlayer()
                , 20*10L);
        this.main.getServer().getScheduler().scheduleSyncDelayedTask(this.main, () ->
                Bukkit.shutdown()
                , 20*15L);
    }

    private void displayKills() {
        Bukkit.broadcastMessage(" ");
        for (Map.Entry<String, Integer> entry : getTop().entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            PlayerSD psd = this.main.playersd.get(player.getUniqueId());
            Bukkit.broadcastMessage(psd.getTeam().getName() + "§7 | §c" + player.getName() + "§7 » §6" + psd.getKills() + "§e kills");
        }
        Bukkit.broadcastMessage(" ");
    }

    private Map<String, Integer> getTop() {
        Map<String, Integer> stats = new HashMap<>();
        this.main.playersd.keySet().forEach(UUID -> stats.put(Bukkit.getPlayer(UUID).getName(), this.main.playersd.get(UUID).getKills()));

        return stats.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public void firework(Location loc){
        Firework fw = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwmeta = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.withTrail();
        builder.withFlicker();
        builder.withColor(Color.FUCHSIA);
        builder.withColor(Color.GREEN);
        builder.withColor(Color.LIME);
        builder.withColor(Color.BLUE);
        builder.withColor(Color.MAROON);
        builder.withColor(Color.ORANGE);
        builder.withColor(Color.PURPLE);
        builder.withColor(Color.WHITE);
        builder.with(FireworkEffect.Type.BALL_LARGE);
        fwmeta.addEffects(new FireworkEffect[] { builder.build() });
        fwmeta.setPower((int).9);
        fw.setFireworkMeta(fwmeta);
    }
}
