package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.StateSD;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    private MainSD main;

    public DamageEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if(event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }

        if(this.main.timer.getTimer() <= 30 || this.main.info.isState(StateSD.END)) {
            event.setCancelled(true);
        }
    }
}
