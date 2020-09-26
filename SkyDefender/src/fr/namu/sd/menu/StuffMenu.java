package fr.namu.sd.menu;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.StuffSD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class StuffMenu {

    private MainSD main;

    public StuffMenu(MainSD main) {
        this.main = main;
    }

    public void StuffEdit(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration des Limites");
        int slot = 0;
        int line = 0;

        for(StuffSD item : StuffSD.values()) {
            inv.setItem(10+slot+line * 9, this.main.item.basicItem(item.getMaterial(), item.getName(), 1, item.showValue()));
            slot = slot + 2;
            if(slot > 6) {
                slot = 0;
                line = line + 1;
                if(line%2 == 1) {
                    slot = 1;
                }
            }
        }

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)1));

        player.openInventory(inv);
    }

    public void StuffModif(StuffSD stuff, ClickType click) {
        if(click == ClickType.LEFT) {
            if(stuff.getValue() == 0) {
                return;
            }
            stuff.addValue(-1);
            return;
        }
        stuff.addValue(1);
    }
}
