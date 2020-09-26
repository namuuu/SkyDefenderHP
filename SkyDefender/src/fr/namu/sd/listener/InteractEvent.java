package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    private MainSD main;

    public InteractEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        ItemStack current = event.getItem();
        Player player = event.getPlayer();

        if(current == null || !current.hasItemMeta() || !current.getItemMeta().hasDisplayName()) {
            return;
        }
        switch (current.getItemMeta().getDisplayName()) {
            case "§eMenu du Host":
                this.main.HostMenu.configList(player);
                break;
            case "§eChoisir une équipe":
                this.main.PlayerMenu.teamList(player);
                break;
            case "§9Location : §eSpawn Defenseur":
                event.setCancelled(true);
                this.main.setup.setSpawnDef(player);
                break;
            case "§9Location : §eTéléporteur (Sol)":
                event.setCancelled(true);
                this.main.setup.setTeleportDown(player);
                break;
            case "§9Location : §eTéléporteur (Château)":
                event.setCancelled(true);
                this.main.setup.setTeleportUp(player);
                break;
            case "§9Location : §eBannière":
                event.setCancelled(true);
                this.main.setup.setBanner(player);
                break;
            case "§cQuitter le mode d'édition":
                event.setCancelled(true);
                this.main.PlayerMenu.baseStuff(player);
                player.teleport(this.main.info.world.getSpawnLocation());
                break;
            case "§eQuitter le mode Spectateur":
                this.main.spec.removeSpec(player);
                break;
        }
    }
}
