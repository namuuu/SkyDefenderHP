package fr.namu.sd.menu;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.TimerSD;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TimerMenu {

    private MainSD main;

    public TimerMenu(MainSD main) {
        this.main = main;
    }

    public void timerMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration des Timers");

        inv.setItem(12, this.main.item.basicItem(Material.STONE_BUTTON, "§b- 30 Secondes", 1, new String[] {}));
        inv.setItem(13, this.main.item.basicItem(Material.IRON_SWORD, "§b" + TimerSD.PVP.getName() + " §7: §e" + this.main.timer.conversion(TimerSD.PVP.getValue()), 1, null));
        inv.setItem(14, this.main.item.basicItem(Material.STONE_BUTTON, "§b+ 30 Secondes", 1, new String[] {}));

        inv.setItem(21, this.main.item.basicItem(Material.STONE_BUTTON, "§b- 30 Secondes", 1, new String[] {}));
        inv.setItem(22, this.main.item.basicItem(Material.EMERALD, "§b" + TimerSD.FINAL_HEAL.getName() + " §7: §e" + this.main.timer.conversion(TimerSD.FINAL_HEAL.getValue()), 1, null));
        inv.setItem(23, this.main.item.basicItem(Material.STONE_BUTTON, "§b+ 30 Secondes", 1, new String[] {}));

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)4));

        player.openInventory(inv);
    }

    public void timerModif(int slot) {
        switch (slot) {
            case 12:
                TimerSD.PVP.addValue(-30);
                break;
            case 14:
                TimerSD.PVP.addValue(30);
                break;
            case 21:
                TimerSD.FINAL_HEAL.addValue(-30);
                break;
            case 23:
                TimerSD.FINAL_HEAL.addValue(30);
                break;
        }
    }
}
