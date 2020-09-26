package fr.namu.sd.menu;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.TimerSD;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BorderMenu {

    private MainSD main;

    public BorderMenu(MainSD main) {
        this.main = main;
    }

    public void borderEdit(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration de la Bordure");

        inv.setItem(12, this.main.item.basicItem(Material.STONE_BUTTON, "§b- 50 Blocs", 1, new String[] {}));
        inv.setItem(13, this.main.item.basicItem(Material.MAGMA_CREAM, "§b" + BorderSD.MAX_BORDER.getName() + " §7: §e"+ BorderSD.MAX_BORDER.getValue() + " blocs", 1, null));
        inv.setItem(14, this.main.item.basicItem(Material.STONE_BUTTON, "§b+ 50 Blocs", 1, new String[] {}));

        inv.setItem(21, this.main.item.basicItem(Material.STONE_BUTTON, "§b- 50 Blocs", 1, new String[] {}));
        inv.setItem(22, this.main.item.basicItem(Material.SLIME_BALL, "§b" + BorderSD.MIN_BORDER.getName() + " §7: §e"+ BorderSD.MIN_BORDER.getValue() + " blocs", 1, null));
        inv.setItem(23, this.main.item.basicItem(Material.STONE_BUTTON, "§b+ 50 Blocs", 1, new String[] {}));

        inv.setItem(39, this.main.item.basicItem(Material.STONE_BUTTON, "§b- 30 Secondes", 1, new String[] {}));
        inv.setItem(40, this.main.item.basicItem(Material.WATCH, "§b" + TimerSD.BORDER_SHRINK.getName() + " §7: §e"+ this.main.timer.conversion(TimerSD.BORDER_SHRINK.getValue()), 1, null));
        inv.setItem(41, this.main.item.basicItem(Material.STONE_BUTTON, "§b+ 30 Secondes", 1, new String[] {}));

        int[] SlotWhiteGlass = {
               0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)3));

        player.openInventory(inv);
    }

    public void borderModif(int slot) {
        switch (slot) {
            case 12:
                BorderSD.MAX_BORDER.addValue(-50);
                break;
            case 14:
                BorderSD.MAX_BORDER.addValue(50);
                break;
            case 21:
                BorderSD.MIN_BORDER.addValue(-50);
                break;
            case 23:
                BorderSD.MIN_BORDER.addValue(50);
                break;
            case 39:
                TimerSD.BORDER_SHRINK.addValue(-30);
                break;
            case 41:
                TimerSD.BORDER_SHRINK.addValue(30);
                break;
        }

    }
}