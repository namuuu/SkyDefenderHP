package fr.namu.sd.util;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportUtil {

    private MainSD main;

    public TeleportUtil(MainSD main) {
        this.main = main;
    }

    public void teleportPlayers() {
        if(ScenarioSD.MULTIPLE_TEAM.getValue()) {
            teamTeleport();
        } else {
            soloTeleport();
        }
    }

    private void soloTeleport() {
        List<Player> players = TeamSD.ATTAQUE.getPlayers();
        for (Integer ind = 0; ind < players.size(); ind++) {
            Player player = players.get(ind);
            World world = player.getWorld();
            WorldBorder wb = world.getWorldBorder();
            double a = ind * 2.0D * Math.PI / Bukkit.getOnlinePlayers().size();
            int x = (int)Math.round(wb.getSize() / 3.0D * Math.cos(a) + world.getSpawnLocation().getX());
            int z = (int)Math.round(wb.getSize() / 3.0D * Math.sin(a) + world.getSpawnLocation().getZ());
            Location loc = new Location(world, x, (world.getHighestBlockYAt(x, z) + 100), z);
            player.teleport(loc);
            this.main.start.setPlayer(player);

        }

        for(Player player : TeamSD.DEFENSE.getPlayers()) {
            this.main.start.setPlayer(player);
            player.teleport(this.main.info.getSpawnDefense());
        }

    }

    private void teamTeleport() {
        Integer ind = 0;
        for (TeamSD team : TeamSD.values()) {
            if(team != TeamSD.DEFENSE) {
                World world = this.main.info.world;
                WorldBorder wb = world.getWorldBorder();
                double a = ind * 2.0D * Math.PI / Bukkit.getOnlinePlayers().size();
                int x = (int) Math.round(wb.getSize() / 3.0D * Math.cos(a) + world.getSpawnLocation().getX());
                int z = (int) Math.round(wb.getSize() / 3.0D * Math.sin(a) + world.getSpawnLocation().getZ());
                Location loc = new Location(world, x, (world.getHighestBlockYAt(x, z) + 100), z);
                for (Integer i = 0; i < team.getPlayers().size(); i++) {
                    Player player = team.getPlayers().get(i);
                    this.main.start.setPlayer(player);
                    player.teleport(loc);
                }
                ind++;
            } else {
                for (Integer i = 0; i < team.getPlayers().size(); i++) {
                    Player player = team.getPlayers().get(i);
                    this.main.start.setPlayer(player);
                    player.teleport(this.main.info.getSpawnDefense());
                }
            }
        }
    }



}
