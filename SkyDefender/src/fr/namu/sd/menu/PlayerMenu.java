package fr.namu.sd.menu;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerMenu {

    private MainSD main;

    public PlayerMenu(MainSD main) {
        this.main = main;
    }

    public void teamList(Player player) {
        if(ScenarioSD.MULTIPLE_TEAM.getValue() == false) {
            Inventory inv = Bukkit.createInventory(null, 6*9, "§7Choisis ton Équipe !");
            inv.setItem(21, this.main.item.bannerTeam(DyeColor.RED, "§eRejoindre les " + TeamSD.ATTAQUE.getName(), TeamSD.ATTAQUE));
            inv.setItem(23, this.main.item.bannerTeam(DyeColor.BLUE, "§eRejoindre les " + TeamSD.DEFENSE.getName(), TeamSD.DEFENSE));
            inv.setItem(49, this.main.item.basicItem(Material.BARRIER, "§eNe choisir aucune équipe", 1, new String[] {}));

            int[] SlotWhiteGlass = {
                    3,4,5,11,15,19,25,27,35,36,44,46,47,48,50,51,52 };
            for (int slotGlass : SlotWhiteGlass)
                inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)0));
            int[] SlotGreyGlass = {
                    0, 1, 2, 6, 7, 8, 9, 10, 16, 17, 18, 26, 45, 53 };
            for (int slotGlass : SlotGreyGlass)
                inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)7));

            player.openInventory(inv);
        } else {
            Inventory inv = Bukkit.createInventory(null, 6*9, "§7Choisis ton Équipe !");

            inv.setItem(37, this.main.item.bannerTeam(DyeColor.RED, "§eChoisir l'" + TeamSD.RED.getName(), TeamSD.RED));
            inv.setItem(29, this.main.item.bannerTeam(DyeColor.ORANGE, "§eChoisir l'" + TeamSD.ORANGE.getName(), TeamSD.ORANGE));
            inv.setItem(39, this.main.item.bannerTeam(DyeColor.YELLOW, "§eChoisir l'" + TeamSD.YELLOW.getName(), TeamSD.YELLOW));
            inv.setItem(31, this.main.item.bannerTeam(DyeColor.GREEN, "§eChoisir l'" + TeamSD.GREEN.getName(), TeamSD.GREEN));
            inv.setItem(41, this.main.item.bannerTeam(DyeColor.LIGHT_BLUE, "§eChoisir l'" + TeamSD.AQUA.getName(), TeamSD.AQUA));
            inv.setItem(33, this.main.item.bannerTeam(DyeColor.PINK, "§eChoisir l'" + TeamSD.PINK.getName(), TeamSD.PINK));
            inv.setItem(43, this.main.item.bannerTeam(DyeColor.PURPLE, "§eChoisir l'" + TeamSD.PURPLE.getName(), TeamSD.PURPLE));
            inv.setItem(13, this.main.item.bannerTeam(DyeColor.BLUE, "§eRejoindre les " + TeamSD.DEFENSE.getName(), TeamSD.DEFENSE));
            inv.setItem(49, this.main.item.basicItem(Material.BARRIER, "§eNe choisir aucune équipe", 1, new String[] {}));

            inv.setItem(53, this.main.item.basicItem(Material.GLASS, "§7Rejoindre les Spectateurs", 1, null));

            int[] SlotWhiteGlass = {
                    3,4,5,11,15,19,25,27,35,36,44,46,47,48,50,51,52 };
            for (int slotGlass : SlotWhiteGlass)
                inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)0));
            int[] SlotGreyGlass = {
                    0, 1, 2, 6, 7, 8, 9, 10, 16, 17, 18, 26, 45 };
            for (int slotGlass : SlotGreyGlass)
                inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)7));

            player.openInventory(inv);
        }
    }

    public void baseStuff(Player player) {
        PlayerSD psd = this.main.playersd.get(player.getUniqueId());
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        Inventory inv = player.getInventory();
        inv.setItem(4, this.main.item.bannerItem(psd.getTeam().getColor(), "§eChoisir une équipe"));
        if(player.isOp() || this.main.mjc.isHost(player.getUniqueId())) {
            player.getInventory().setItem(7, this.main.item.basicItem(Material.NETHER_STAR, "§eMenu du Host", 1, null));
        }
    }
}
