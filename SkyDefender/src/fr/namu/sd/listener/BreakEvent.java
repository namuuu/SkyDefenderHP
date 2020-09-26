package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {

    private MainSD main;

    public BreakEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        Player player = event.getPlayer();
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());

        if(this.main.info.getTeleportUp() != null && loc.distance(this.main.info.getTeleportUp()) <= 3) {
            event.setCancelled(true);
        }
        if(this.main.info.getTeleportDown() != null && loc.distance(this.main.info.getTeleportDown()) <= 3) {
            event.setCancelled(true);
        }
        if(this.main.info.getBanner() != null && loc.distance(this.main.info.getBanner()) <= 5) {
            event.setCancelled(true);

            if(isSameLocation(loc, this.main.info.getBanner())) {
                System.out.println("False");
                if(!psd.isState(State.ALIVE) || psd.isTeam(TeamSD.DEFENSE)) {
                    player.sendMessage("§9S§3D §7» §cTu n'es pas autorisé à casser la bannière !");
                    return;
                }
                if(TeamSD.DEFENSE.getPlayers().size() != 0) {
                    player.sendMessage("§9S§3D §7» §cTout les défenseurs ne sont pas morts, tuez-les d'abord !");
                    return;
                }


                this.main.win.teamWin(psd.getTeam());
                event.getBlock().setType(Material.AIR);
                this.main.win.firework(event.getBlock().getLocation());
                return;
            }
        }
    }



    private Boolean isSameLocation(Location loc1, Location loc2) {
        if(loc1.getBlockX() != loc2.getBlockX()) {
            return false;
        }
        if(loc1.getBlockY() != loc2.getBlockY()) {
            return false;
        }
        if(loc1.getBlockZ() != loc2.getBlockZ()) {
            return false;
        }
        return true;
    }
}
