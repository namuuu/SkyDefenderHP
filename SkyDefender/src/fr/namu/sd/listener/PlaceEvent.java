package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {

    private MainSD main;

    public PlaceEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Location loc = event.getBlock().getLocation();

        if(this.main.info.getTeleportUp() != null && loc.distance(this.main.info.getTeleportUp()) <= 3) {
            event.setCancelled(true);
        }
        if(this.main.info.getTeleportDown() != null && loc.distance(this.main.info.getTeleportDown()) <= 3) {
            event.setCancelled(true);
        }
        if(this.main.info.getBanner() != null && loc.distance(this.main.info.getBanner()) <= 5) {
            event.setCancelled(true);
        }
    }
}
