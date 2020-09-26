package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.StateSD;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener {

    private MainSD main;

    public DeathEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());
        event.setDeathMessage(null);

        World world = this.main.info.world;

        for(ItemStack item : player.getInventory().getContents()) {
            if(item != null && item.getType() != Material.AIR) {
                world.dropItemNaturally(player.getLocation(), item);
            }
        }
        for(ItemStack item : player.getInventory().getArmorContents()) {
            if(item != null && item.getType() != Material.AIR) {
                world.dropItemNaturally(player.getLocation(), item);
            }
        }

        player.setHealth(player.getMaxHealth());
        player.setGameMode(GameMode.SPECTATOR);
        psd.setState(State.DEAD);
        psd.getTeam().getPlayers().remove(player);

        this.main.prey.verifyPrey(player);


        if(player.getKiller() == null) {
            Bukkit.broadcastMessage("§7[" + psd.getTeam().getName() + "§7] §6" + player.getName() + "§e est mort.");
        }

        if(player.getKiller() != null) {
            Player killer = player.getKiller();
            PlayerSD psdKiller = this.main.playersd.get(killer.getUniqueId());
            psdKiller.addKill();

            Bukkit.broadcastMessage("§7[" + psd.getTeam().getName() + "§7] §6" + player.getName() + "§e s'est fait tuer par " + "§7[" + psdKiller.getTeam().getName() + "§7] §6" + killer.getName());
        }

        this.main.win.verifyWin();
        if(this.main.info.isState(StateSD.END)) {
            this.main.win.firework(player.getLocation());
        }

    }
}
