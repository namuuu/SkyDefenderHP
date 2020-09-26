package fr.namu.sd.util;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.SeedSD;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TeamSD;
import fr.namu.sd.runnable.LobbyRun;
import fr.namu.sd.scoreboard.FastBoard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Set;

public class SetupUtil {

    private final MainSD main;

    public SetupUtil(MainSD main) {
        this.main = main;
    }


    public void setupWorld() {
        try {
            World world = this.main.info.world;
            world.setPVP(false);
            world.setWeatherDuration(0);
            world.setThunderDuration(0);
            world.setTime(0L);
            world.setGameRuleValue("keepInventory", "true");
            world.setGameRuleValue("naturalRegeneration", "false");
            world.setGameRuleValue("announceAdvancements", "false");
            world.setGameRuleValue("doDayLightCycle", "false");
            world.getWorldBorder().reset();
            int x = (int)world.getSpawnLocation().getX();
            int z = (int)world.getSpawnLocation().getZ();
            int y = (int)world.getSpawnLocation().getY();
            world.setSpawnLocation(x, y, z); // 184
            for (int i = -16; i <= 16; i++) {
                for (int j = -16; j <= 16; j++) {
                    (new Location(world, (i + x), y-1, (j + z))).getBlock().setType(Material.BARRIER);
                    (new Location(world, (i + x), y+3, (j + z))).getBlock().setType(Material.BARRIER);
                }
                (new Location(world, (i + x), y  , (z - 16))).getBlock().setType(Material.BARRIER);
                (new Location(world, (i + x), y+1, (z - 16))).getBlock().setType(Material.BARRIER);
                (new Location(world, (i + x), y+3, (z - 16))).getBlock().setType(Material.BARRIER);
                (new Location(world, (i + x), y  , (z + 16))).getBlock().setType(Material.BARRIER);
                (new Location(world, (i + x), y+1, (z + 16))).getBlock().setType(Material.BARRIER);
                (new Location(world, (i + x), y+2, (z + 16))).getBlock().setType(Material.BARRIER);
                (new Location(world, (x - 16), y  , (i + z))).getBlock().setType(Material.BARRIER);
                (new Location(world, (x - 16), y+1, (i + z))).getBlock().setType(Material.BARRIER);
                (new Location(world, (x - 16), y+2, (i + z))).getBlock().setType(Material.BARRIER);
                (new Location(world, (x + 16), y  , (i + z))).getBlock().setType(Material.BARRIER);
                (new Location(world, (x + 16), y+1, (i + z))).getBlock().setType(Material.BARRIER);
                (new Location(world, (x + 16), y+2, (i + z))).getBlock().setType(Material.BARRIER);
            }
            enableSeed();
            LobbyRun startLobby = new LobbyRun(this.main);
            startLobby.runTaskTimer(this.main, 0L, 20L);
            setTeamBoard();
            resetPlayers();
        } catch (Exception e) {
            Bukkit.broadcastMessage(ChatColor.RED + "Une erreur est survenue. Le Plugin SkyDefender n'a pas pu charger.");
        }
    }

    public void resetPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
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
            player.setGameMode(GameMode.ADVENTURE);
            psd.setState(State.ALIVE);
            this.main.info.addPlayerSize();
            this.main.team.joinTeam(player, TeamSD.NULL);
            this.main.PlayerMenu.baseStuff(player);
            if(player.isOp() || this.main.mjc.isHost(player.getUniqueId())) {
                player.getInventory().setItem(7, this.main.item.basicItem(Material.NETHER_STAR, "§eMenu du Host", 1, null));
            }
        }
    }

    public void enableSeed() {
        String WUID = this.main.info.world.getUID().toString();
        System.out.println("[SkyDefender - SEEDS] Recherche de Seeds...");

        for(SeedSD seed : SeedSD.values()) {
            if(WUID.equals(seed.getWUID())) {
                this.main.info.setMapName(seed.getMapName());
                this.main.info.setSpawnDefense(seed.getSpawnDefense());
                this.main.info.setTeleportDown(seed.getTeleportDown());
                this.main.info.setTeleportUp(seed.getTeleportUp());
                this.main.info.setBanner(seed.getBanner());
                System.out.println("[SkyDefender - SEEDS] Seed Fonctionnelle ! (" + seed.getMapName() + " | " + seed.getWUID() + ")");
                return;
            }
        }

        System.out.println("[SkyDefender - SEEDS] Aucune Seed n'a été trouvée...");
    }

    public void setSpawnDef(Player player) {
        Location loc = player.getTargetBlock((Set<Material>)null, 10).getLocation();
        if(loc.getBlock().getType() != Material.AIR) {
            player.sendMessage("§7[§9S§bD§7] §aLa localisation a été définie avec succès !");
            System.out.println("[SkyDefender - SETUP] Spawn Defenseur : " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
            loc.setY(loc.getY() + 1);
            this.main.info.setSpawnDefense(loc);
        }
    }

    public void setTeleportUp(Player player) {
        Location loc = player.getTargetBlock((Set<Material>)null, 10).getLocation();
        if(loc.getBlock().getType() != Material.AIR) {
            player.sendMessage("§7[§9S§bD§7] §aLa localisation a été définie avec succès !");
            System.out.println("[SkyDefender - SETUP] Teleporteur (Chateau) : " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
            loc.setY(loc.getY() + 1);
            this.main.info.setTeleportUp(loc);
        }
    }

    public void setTeleportDown(Player player) {
        Location loc = player.getTargetBlock((Set<Material>)null, 10).getLocation();
        if(loc.getBlock().getType() != Material.AIR) {
            player.sendMessage("§7[§9S§bD§7] §aLa localisation a été définie avec succès !");
            System.out.println("[SkyDefender - SETUP] Teleporteur (Sol) : " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
            loc.setY(loc.getY() + 1);
            this.main.info.setTeleportDown(loc);
        }
    }

    public void setBanner(Player player) {
        Location loc = player.getTargetBlock((Set<Material>)null, 10).getLocation();
        if(loc.getBlock().getType() == Material.STANDING_BANNER || loc.getBlock().getType() == Material.WALL_BANNER) {
            player.sendMessage("§7[§9S§bD§7] §aLa localisation a été définie avec succès !");
            System.out.println("[SkyDefender - SETUP] Teleporteur (Sol) : " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
            loc.setY(loc.getY() + 1);
            this.main.info.setBanner(loc);
        }
    }

    private void setTeamBoard() {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();

        for(TeamSD team : TeamSD.values()) {
            sb.registerNewTeam(team.getName());
            sb.getTeam(team.getName()).setPrefix(team.getPrefix());
        }
    }
}
