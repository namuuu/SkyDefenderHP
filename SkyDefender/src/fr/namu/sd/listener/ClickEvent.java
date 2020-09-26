package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.StuffSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClickEvent implements Listener {

    private MainSD main;

    public ClickEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onClickInvent(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player)event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if(this.main.info.isState(StateSD.LOBBY)) {
            if(inv.getName() == "container.crafting") {
                event.setCancelled(true);
                return;
            }
        }

        if(inv.getName().contains("container")) {
            return;
        }
        if(current == null || !current.hasItemMeta() || !current.getItemMeta().hasDisplayName()) {
            return;
        }

        String invname = inv.getName();
        Material mat = current.getType();
        String currentName = current.getItemMeta().getDisplayName();

        switch (invname) {

            case "§7Paramètres de la partie":
                event.setCancelled(true);
                if(mat == Material.EMERALD_BLOCK) {
                    this.main.start.startGame();
                    player.closeInventory();
                    return;
                }
                if(mat == Material.BANNER) {
                    this.main.TeamMenu.teamConfig(player);
                    return;
                }
                if(mat == Material.SLIME_BALL) {
                    this.main.HostMenu.locModifMenu(player);
                    player.closeInventory();
                    return;
                }
                if(mat == Material.BARRIER) {
                    this.main.BorderMenu.borderEdit(player);
                }
                if(mat == Material.IRON_SWORD) {
                    this.main.TimerMenu.timerMenu(player);
                }
                if(mat == Material.BOOK) {
                    this.main.StuffMenu.StuffEdit(player);
                }
                if(mat == Material.CHEST) {
                    this.main.HostMenu.stuffModifMenu(player);
                    player.closeInventory();
                }
                if(mat == Material.COMMAND) {
                    this.main.ScenarioMenu.scenarioMenu(player);
                }
                break;
            case "§7Choisis ton Équipe !":
                event.setCancelled(true);
                for(TeamSD team : TeamSD.values()) {
                    if(currentName.contains(team.getName())) {
                        this.main.team.joinTeam(player, team);
                        this.main.PlayerMenu.teamList(player);
                        break;
                    }
                }
                if(mat == Material.BARRIER) {
                    this.main.team.joinTeam(player, TeamSD.NULL);
                }
                if(mat == Material.GLASS) {
                    this.main.spec.setSpec(player);
                }
                break;
            case "§7Configuration des Équipes":
                event.setCancelled(true);
                for(TeamSD team : TeamSD.values()) {
                    if (currentName.contains(team.getName())) {
                        this.main.TeamMenu.teamSizeEdit(event.getClick(), team);
                        break;
                    }
                }
                if(mat == Material.REDSTONE_TORCH_ON || mat == Material.LEVER) {
                    ScenarioSD.MULTIPLE_TEAM.switchValue(event.getClick());
                }
                this.main.TeamMenu.teamConfig(player);
                break;
            case "§7Configuration de la Bordure":
                event.setCancelled(true);
                this.main.BorderMenu.borderModif(event.getSlot());
                this.main.BorderMenu.borderEdit(player);
                break;
            case "§7Configuration des Timers":
                event.setCancelled(true);
                this.main.TimerMenu.timerModif(event.getSlot());
                this.main.TimerMenu.timerMenu(player);
                break;
            case "§7Configuration des Limites":
                event.setCancelled(true);
                for (StuffSD stuff : StuffSD.values()) {
                    if(currentName.contains(stuff.getName())) {
                        this.main.StuffMenu.StuffModif(stuff, event.getClick());
                    }
                }
                this.main.StuffMenu.StuffEdit(player);
                break;
            case "§7Configuration des Scénarios":
                event.setCancelled(true);
                for(ScenarioSD scenario : ScenarioSD.values()) {
                    if(currentName.contains(scenario.getName())) {
                        scenario.switchValue(event.getClick());
                    }
                }
                this.main.ScenarioMenu.scenarioMenu(player);
                break;
        }
    }
}
