package fr.namu.sd.runnable;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.enumsd.TimerSD;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRun extends BukkitRunnable {

    private MainSD main;

    public GameRun(MainSD main) {
        this.main = main;
    }

    public void run() {
        this.main.timer.addTimer();
        this.main.score.updateBoard();

        int timer = this.main.timer.getTimer();

        if(timer == TimerSD.PVP.getValue()) {
            this.main.timer.activatePvP();
        }
        if(timer == TimerSD.FINAL_HEAL.getValue() && timer != 0) {
            Bukkit.broadcastMessage("§9S§3D §7» §eVous venez de recevoir le §aFinal Heal§e, vous récupérez donc toute votre vie !");
            for(Player player :Bukkit.getOnlinePlayers()) {
                player.setHealth(20D);
                player.setFoodLevel(20);
                player.playSound(player.getLocation(), Sound.DRINK, 1, 1);
            }
        }

        if (timer >= TimerSD.BORDER_SHRINK.getValue()) {
            WorldBorder wb = this.main.info.world.getWorldBorder();
            if (wb.getSize() == BorderSD.MAX_BORDER.getValue()) {
                Bukkit.broadcastMessage("§9S§3D §7» §eLa §6Bordure §ecommence à se rétrécir, dirigez-vous vers le Château !");
            }
            if (wb.getSize() > BorderSD.MIN_BORDER.getValue()) {
                wb.setSize(wb.getSize() - 0.5D);
                wb.setWarningDistance((int)(wb.getSize() / 7.0D));
            }
        }

        if(this.main.info.isState(StateSD.END)) {
            cancel();
        }
    }
}
