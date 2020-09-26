package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.StateSD;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSpawnEvent implements Listener {

    private MainSD main;

    public ItemSpawnEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onItemSpawn(org.bukkit.event.entity.ItemSpawnEvent event) {
        if(ScenarioSD.CUT_CLEAN.getValue()) {
            ItemStack item = event.getEntity().getItemStack();
            Material mat = item.getType();
            switch (mat) {
                case COAL:
                    item.setType(Material.TORCH);
                    break;
                case IRON_ORE:
                    item.setType(Material.IRON_INGOT);
                    break;
                case GOLD_ORE:
                    item.setType(Material.GOLD_INGOT);
                    break;
                case GRAVEL:
                    item.setType(Material.FLINT);
                    break;
                case PORK:
                    item.setType(Material.GRILLED_PORK);
                    break;
                case RAW_BEEF:
                    item.setType(Material.COOKED_BEEF);
                    break;
                case RAW_CHICKEN:
                    item.setType(Material.COOKED_CHICKEN);
                    break;
                case MUTTON:
                    item.setType(Material.COOKED_MUTTON);
                    break;
                case RABBIT:
                    item.setType(Material.COOKED_RABBIT);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(this.main.info.isState(StateSD.LOBBY) && event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            event.setCancelled(true);
        }
    }
}
