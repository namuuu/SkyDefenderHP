package fr.namu.sd.listener;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.State;
import fr.namu.sd.enumsd.StateSD;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    private MainSD main;

    public ChatEvent(MainSD main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());
        String playername = player.getName();

        if (psd.isState(State.ALIVE)) {
            event.setFormat("§7[" + psd.getTeam().getName() + "§7] §e" + playername + " §7» §f" + event.getMessage());
            return;
        } else if (psd.isState(State.DEAD)) {
            event.setFormat("§7[" + psd.getTeam().getName() + "§7] " + playername + " » " + event.getMessage());
            return;
        }
        event.setFormat("§7[SPEC] " + playername + " » " + event.getMessage());
    }
}
