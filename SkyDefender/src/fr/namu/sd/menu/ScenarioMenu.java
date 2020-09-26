package fr.namu.sd.menu;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.ScenarioSD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ScenarioMenu {

    private MainSD main;

    public ScenarioMenu(MainSD main) {
        this.main = main;
    }

    public void scenarioMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration des Scénarios");

        int slot = 1;
        int line = 1;

        for(ScenarioSD scenario : ScenarioSD.values()) {
            inv.setItem(slot + line * 9, scenario.itemDisplay());
            slot = slot + 1;
            if(slot >= 8) {
                line = line + 1;
                slot = 1;
            }
        }

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)1));

        player.openInventory(inv);
    }
}
