package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TeamSD;
import fr.namu.sd.scoreboard.FastBoard;
import fr.redline.serverclient.event.AuthorisePlayerConnectedEvent;
import fr.redline.serverclient.event.AuthorisePlayerDisconnectedEvent;
import fr.redline.serverclient.event.WhiteListEvent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinLeaveEvent implements Listener {

    private MainSD main;

    public JoinLeaveEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(AuthorisePlayerConnectedEvent event) {
        Player player = event.getPlayer();

        this.main.playersd.put(player.getUniqueId(), new PlayerSD());
        this.main.boards.put(player.getUniqueId(), new FastBoard(player));

        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.teleport(this.main.info.world.getSpawnLocation());
        for (PotionEffect po : player.getActivePotionEffects())
            player.removePotionEffect(po.getType());
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());
        this.main.score.updateBoard();

        this.main.tab.setTabList(player,
                new ChatComponentText(
                             "§7• §cH.§6Par§ety §7•\n" +
                                     " \n" +
                                     "§fVous jouez actuellement sur §3play.h-party.fr\n" +
                                     "§fCe plugin a été créé par §bNamu\n" +
                                     " "),

                new ChatComponentText(
                        " \n" +
                                "§fDiscord: §3https://discord.gg/SHsq8gb"
                        ));

        if(this.main.info.isState(StateSD.LOBBY) && !this.main.mjc.isSpectator(player)) {
            event.setJoinMessage("§a+ §7» §e"+ event.getPlayer().getName());
            player.setGameMode(GameMode.ADVENTURE);
            psd.setState(State.ALIVE);
            this.main.info.addPlayerSize();
            this.main.team.joinTeam(player, TeamSD.NULL);
            this.main.PlayerMenu.baseStuff(player);
            return;
        }

        event.setJoinMessage("§7+ » "+ event.getPlayer().getName());
        player.setGameMode(GameMode.SPECTATOR);
        psd.setState(State.DEAD);
        this.main.spec.setSpec(player);
    }

    @EventHandler
    public void onPlayerQuit(AuthorisePlayerDisconnectedEvent event) {
        Player player = event.getPlayer();
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());
        this.main.score.updateBoard();

        if(psd.isState(State.ALIVE)) {
            this.main.info.decPlayerSize();
        }
        if(this.main.info.isState(StateSD.GAME) && psd.getTeam() != null) {
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
            psd.getTeam().getPlayers().remove(player);


            Bukkit.broadcastMessage("§6" + player.getName() + " §ea décidé de quitter la Partie, il en meurt donc.");

            this.main.win.verifyWin();
        }

        event.setQuitMessage("§c- §7» §e"+ event.getPlayer().getName());

        this.main.boards.remove(player.getUniqueId());
        this.main.playersd.remove(player.getUniqueId());
    }

    @EventHandler
    public void onWhiteList(WhiteListEvent event) {
        if(event.isHost()) {
            this.main.info.setHost(event.getPlUUID());
        }
    }
}
