package fr.namu.sd.util;

import fr.namu.sd.MainSD;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TimerUtil {

    private int timer = 0;

    private MainSD main;

    public TimerUtil(MainSD main) {
        this.main = main;
    }

    public void addTimer() {
        this.timer++;
    }

    public int getTimer() {
        return this.timer;
    }

    public String timerConversed() {
        return this.conversion(this.timer);
    }

    public String conversion(int timer) {
        String valeur;
        if (timer % 60 > 9) {
            valeur = String.valueOf(timer % 60) + "s";
        } else {
            valeur = "0" + (timer % 60) + "s";
        }
        if (timer / 3600 > 0) {
            if (timer % 3600 / 60 > 9) {
                valeur = String.valueOf(timer / 3600) + "h" + (timer % 3600 / 60) + "m" + valeur;
            } else {
                valeur = String.valueOf(timer / 3600) + "h0" + (timer % 3600 / 60) + "m" + valeur;
            }
        } else if (timer / 60 > 0) {
            valeur = String.valueOf(timer / 60) + "m" + valeur;
        }
        return valeur;
    }

    public void activatePvP() {
        World world = this.main.info.world;
        if(world.getPVP()) {
            return;
        }
        world.setPVP(true);
        Bukkit.broadcastMessage("§cLe PVP a été Activé.");
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.WOLF_GROWL, 1.0F, 1.0F);
        }
    }

}
