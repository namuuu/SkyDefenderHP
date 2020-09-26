package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.StateSD;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {

    private MainSD main;

    public DropEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(!this.main.info.isState(StateSD.GAME)) {
            event.setCancelled(true);
        }
    }
}
