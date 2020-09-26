package fr.namu.sd.menu;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeamMenu {

    private MainSD main;

    public TeamMenu(MainSD main) {
        this.main = main;
    }


    public void teamConfig(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration des Équipes");

        if(ScenarioSD.MULTIPLE_TEAM.getValue()) {
            inv.setItem(37, teamInfo(TeamSD.RED));
            inv.setItem(29, teamInfo(TeamSD.ORANGE));
            inv.setItem(39, teamInfo(TeamSD.YELLOW));
            inv.setItem(31, teamInfo(TeamSD.GREEN));
            inv.setItem(41, teamInfo(TeamSD.AQUA));
            inv.setItem(33, teamInfo(TeamSD.PINK));
            inv.setItem(43, teamInfo(TeamSD.PURPLE));

            inv.setItem(13, teamInfo(TeamSD.DEFENSE));
        } else {
            inv.setItem(30, teamInfo(TeamSD.ATTAQUE));
            inv.setItem(32, teamInfo(TeamSD.DEFENSE));
        }

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)10));

        inv.setItem(49, isMultiTeam());

        player.openInventory(inv);
    }

    private ItemStack isMultiTeam() {
        if(ScenarioSD.MULTIPLE_TEAM.getValue()) {
            return this.main.item.basicItem(Material.REDSTONE_TORCH_ON, "§e" + ScenarioSD.MULTIPLE_TEAM.getName(), 1, new String[] {"§aEst activé."});
        } else {
            return this.main.item.basicItem(Material.LEVER, "§e" + ScenarioSD.MULTIPLE_TEAM.getName(), 1, new String[] {"§cEst désactivé."});
        }
    }

    private ItemStack teamInfo(TeamSD team) {
        return this.main.item.bannerItemWithLore(team.getColor(), team.getName(), new String[]
                {"§bTaille : §e" + team.getSize() + " joueurs",
                "§7Clic Gauche : diminuer",
                "§7Clic Droit : augmenter"});
    }

    public void teamSizeEdit(ClickType click, TeamSD team) {
        if(click == ClickType.RIGHT) {
            team.addSize();
            return;
        }
        if(team.getSize() <= 0) {
            return;
        }
        team.decSize();
        verifyTeamSize(team);
    }

    private void verifyTeamSize(TeamSD team) {
        if(team.getSize() < team.getPlayers().size()) {
            Player player = team.getPlayers().get(0);
            player.sendMessage("§cDésolé, en raison de la réduction de la taille de ton Équipe, tu en as été retiré !");
            this.main.team.joinTeam(player, TeamSD.NULL);
        }
    }
}
