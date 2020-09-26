package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.ScenarioSD;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FurnaceEvent implements Listener {

    private MainSD main;

    public FurnaceEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onBurn(FurnaceBurnEvent event) {
        if (ScenarioSD.FASTSMELTING.getValue())
            handleCookingTime((Furnace)event.getBlock().getState());
    }

    private void handleCookingTime(final Furnace block) {
        (new BukkitRunnable() {
            public void run() {
                if (block.getCookTime() > 0 || block.getBurnTime() > 0) {
                    block.setCookTime((short)(block.getCookTime() + 8));
                    block.update();
                } else {
                    cancel();
                }
            }
        }).runTaskTimer((Plugin)this.main, 1L, 1L);
    }

}
