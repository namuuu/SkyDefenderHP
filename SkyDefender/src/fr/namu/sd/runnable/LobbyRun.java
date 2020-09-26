package fr.namu.sd.runnable;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.util.SetupUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyRun extends BukkitRunnable {

    private MainSD main;

    public LobbyRun(MainSD main) {
        this.main = main;
    }

    public void run() {
        this.main.score.updateBoard();

        if(!this.main.info.isState(StateSD.LOBBY)) {
            cancel();
        }
    }
}
