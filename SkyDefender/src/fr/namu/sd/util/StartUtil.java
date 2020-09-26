package fr.namu.sd.util;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TeamSD;
import fr.namu.sd.runnable.GameRun;
import fr.redline.serverclient.enumerator.PartyState;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class StartUtil {

    private final MainSD main;

    public StartUtil(MainSD main) {
        this.main = main;
    }


    public void startGame() {
        System.out.println("[SkyDefender] Lancement de la partie...");


        if(!verifySettings()) {
            return;
        }

        autoTeam();
        autoSpec();
        setBorder();
        this.main.prey.enablePreys();
        this.main.teleport.teleportPlayers();
        this.main.mjc.startGame();
        this.main.mjc.setLeaveRestricted(true);
        setSpecs();
        this.main.info.setState(StateSD.GAME);
        this.main.info.world.setGameRuleValue("doDayLightCycle", "true");

        GameRun startGame = new GameRun(this.main);
        startGame.runTaskTimer(this.main, 0L, 20L);

        this.main.getServer().getScheduler().scheduleSyncDelayedTask(this.main, () ->
                Bukkit.broadcastMessage("§6S§bD §7» §cVotre période de grâce est dorénavant terminée !"), 20*29L);
    }


    private Boolean verifySettings() {
        if(this.main.info.getSpawnDefense() == null || this.main.info.getTeleportUp() == null || this.main.info.getTeleportDown() == null) {
            Bukkit.broadcastMessage(ChatColor.RED + "L'host a tenté de lancer la partie, mais il n'a pas défini toutes les locations !");
            return false;
        }
        if(TeamSD.DEFENSE.getPlayers().size() == 0) {
            if(TeamSD.DEFENSE.getSize() == 0 || TeamSD.NULL.getPlayers().size() == 0) {
                Bukkit.broadcastMessage(ChatColor.RED + "Il vous faut au moins un joueur capable de devenir Défenseur !");
                return false;
            }
        }
        if(ScenarioSD.PREY.getValue()) {
            if(this.main.playersd.size() - TeamSD.DEFENSE.getPlayers().size() < TeamSD.DEFENSE.getPlayers().size()) {
                Bukkit.broadcastMessage("§7[§5Prey§7] §cIl vous faut plus d'attaquants que de défenseurs !");
                return false;
            }
        }


        return true;
    }

    private void autoTeam() {
        Collections.shuffle(TeamSD.NULL.getPlayers());
        List<Player> nullMembers = TeamSD.NULL.getPlayers();
        System.out.println("[SkyDefender] Remplissage des Equipes...");

        while(!nullMembers.isEmpty()) {
            if(nullMembers.isEmpty()) {
                break;
            }
            Player player = nullMembers.get(0);
            PlayerSD psd = this.main.playersd.get(player.getUniqueId());
            int ind = 0;

            while(psd.getTeam() == TeamSD.NULL) {
                for (TeamSD team : TeamSD.values()) {
                    if(psd.getTeam() == TeamSD.NULL && team.getSize() > ind && team.getPlayers().size() == ind && team != TeamSD.NULL) {
                        if(ScenarioSD.MULTIPLE_TEAM.getValue()) {
                            if(team != TeamSD.ATTAQUE) {
                                this.main.team.joinTeam(player, team);
                            }
                        } else {
                            if(team == TeamSD.ATTAQUE || team == TeamSD.DEFENSE) {
                                this.main.team.joinTeam(player, team);
                            }
                        }
                    }

                }
                if(ind == 30) {
                    return;
                }
                ind++;
            }
            System.out.println(nullMembers.size());
        }
    }

    private void autoSpec() {
        List<Player> nullMembers = TeamSD.NULL.getPlayers();
        if(nullMembers.isEmpty()) {
            return;
        }
        System.out.println("[SkyDefender] Certains joueurs n'ont pas pu recevoir une Équipe ! Attribution de Spectateurs...");
        while(!nullMembers.isEmpty()) {
            Player player = nullMembers.get(0);
            this.main.spec.setSpec(player);
        }
    }

    public void setPlayer(Player player) {
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20.0);
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        player.setLevel(ScenarioSD.MASTERLEVEL.getNumber());
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        player.setGameMode(GameMode.SURVIVAL);

        this.main.item.setStartStuff(player);

        for (PotionEffect po : player.getActivePotionEffects())
            player.removePotionEffect(po.getType());
        if(ScenarioSD.CAT_EYES.getValue())
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
        if(!psd.isPreyNull() && ScenarioSD.PREY.getValue()) {
            player.setMaxHealth(16.0D);
            player.sendMessage("§7[§5Prey§7] §eLe Scénario §5Prey §eest activé ! Par conséquent, votre nombre de  maximal de coeur sera de 8 tant que vous ne tuez pas votre proie, qui est §5" + psd.getPrey().getName() + "§e.");
        }
    }

    private void setBorder() {
        World world = this.main.info.world;
        world.setTime(0L);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(world.getSpawnLocation().getX(), world.getSpawnLocation().getZ());
        worldBorder.setSize(BorderSD.MAX_BORDER.getValue());
        worldBorder.setWarningDistance((int)(worldBorder.getSize() / 7.0D));
    }

    private void setSpecs() {
        for(Player player : this.main.spec.getSpecs()) {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
}
