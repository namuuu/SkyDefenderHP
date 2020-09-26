package fr.namu.sd.command;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostCMD implements TabExecutor {

    private MainSD main;

    public HostCMD(MainSD main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());
        if (args.length == 0)
            return false;
        if (!player.hasPermission("host.use")) {
            sender.sendMessage("§eVous n'avez pas les permissions nécessaires pour réaliser cette commande !");
            return true;
        }
        switch (args[0]) {
            case "start":
                this.main.start.startGame();
                return true;
            case "menu":
                player.getInventory().setItem(7, this.main.item.basicItem(Material.NETHER_STAR, "§eMenu du Host", 1, null));
                return true;
            case "startStuff":
                this.main.item.saveStartStuff(player);
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.teleport(this.main.info.world.getSpawnLocation());
                this.main.PlayerMenu.baseStuff(player);
                player.setGameMode(GameMode.ADVENTURE);
                return true;
            case "say":
                String broadcast = "";
                for (int i = 1; i < args.length; i++) {
                    broadcast = broadcast + args[i] + " ";
                }
                broadcast = broadcast.trim();
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" §f§lHOST §9" + player.getName() + " §7» §f" + broadcast);
                Bukkit.broadcastMessage(" ");
                return true;
            case "title" :
                String title = "";
                for (int i = 1; i < args.length; i++) {
                    title = title + args[i] + " ";
                }
                this.main.score.updateTitle(title.trim());
                return true;
            default:
                player.sendMessage("§cErreur, cette commande ne fait pas parti de celles prévues par ce plugin.");
                return true;
        }
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        String[] tabe = {"start", "menu"};
        List<String> tab = new ArrayList<>(Arrays.asList(tabe));
        if (args.length == 0)
            return tab;
        if (args.length == 1) {
            for (int i = 0; i < tab.size(); i++) {
                for (int j = 0; j < tab.get(i).length() && j < args[0].length(); j++) {
                    if (tab.get(i).charAt(j) != args[0].charAt(j)) {
                        tab.remove(i);
                        i--;
                        break;
                    }
                }
            }
            return tab;
        }
        return null;
    }

}
