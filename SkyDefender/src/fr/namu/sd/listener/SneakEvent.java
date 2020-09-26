package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SneakEvent implements Listener {

    private MainSD main;

    public SneakEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());

        if(player.isSneaking()) {
            return;
        }
        if(psd.getTeam() != TeamSD.DEFENSE) {
            return;
        }

        Location ploc = player.getLocation();

        if(isSameLocation(ploc, this.main.info.getTeleportUp())) {
            player.teleport(this.main.info.getTeleportDown());
        }
        if(isSameLocation(ploc, this.main.info.getTeleportDown())) {
            player.teleport(this.main.info.getTeleportUp());
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
