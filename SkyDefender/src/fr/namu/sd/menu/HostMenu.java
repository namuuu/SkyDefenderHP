package fr.namu.sd.menu;

import fr.namu.sd.MainSD;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HostMenu {

    private final MainSD main;

    public HostMenu(MainSD main) {
        this.main = main;
    }

    public void configList(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Paramètres de la partie");
        inv.setItem(10, this.main.item.bannerItem(DyeColor.WHITE, "§eModifier les Équipes"));
        inv.setItem(20, this.main.item.basicItem(Material.BARRIER, "§eModifier la Bordure", 1, new String[] {}));
        inv.setItem(12, this.main.item.basicItem(Material.IRON_SWORD, "§eModifier l'activation du PVP", 1, new String[] {}));
        inv.setItem(22, this.main.item.basicItem(Material.SLIME_BALL, "§eModifier les locations de la partie", 1, new String[] {}));
        inv.setItem(14, this.main.item.basicItem(Material.BOOK, "§eModifier les limites d'Équipement", 1, new String[] {}));
        inv.setItem(24, this.main.item.basicItem(Material.CHEST, "§eModifier l'inventaire de départ", 1, new String[] {}));
        inv.setItem(16, this.main.item.basicItem(Material.COMMAND, "§eModifier les Scénarios de la partie", 1, new String[] {}));

        inv.setItem(49, this.main.item.basicItem(Material.EMERALD_BLOCK, "§aLancer la Partie !", 1, new String[] {}));
        inv.setItem(53, this.main.item.skullItem("§aDéveloppeur : §7Nxmu", "Nxmu"));

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,45,48,50 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)0));
        int[] SlotGreyGlass = {
                27,35,36,37,43,44,46,47,51,52 };
        for (int slotGlass : SlotGreyGlass)
            inv.setItem(slotGlass, this.main.item.glassPaneItem(" ", (short)3));

        player.openInventory(inv);
    }

    public void locModifMenu(Player player) {
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        Inventory inv = player.getInventory();

        inv.setItem(1, this.main.item.basicItem(Material.BED, "§9Location : §eSpawn Defenseur", 1, null));
        inv.setItem(2, this.main.item.basicItem(Material.DIRT, "§9Location : §eTéléporteur (Sol)", 1, null));
        inv.setItem(3, this.main.item.basicItem(Material.GLASS, "§9Location : §eTéléporteur (Château)", 1, null));
        inv.setItem(4, this.main.item.basicItem(Material.BANNER, "§9Location : §eBannière", 1, null));
        inv.setItem(8, this.main.item.basicItem(Material.BARRIER, "§cQuitter le mode d'édition", 1, null));
    }

    public void stuffModifMenu(Player player) {
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        Inventory inv = player.getInventory();

        this.main.item.setStartStuff(player);

        TextComponent msg = new TextComponent("§aCliquez ici pour valider l'inventaire de départ !");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/h startStuff"));
        player.sendMessage(" ");
        player.spigot().sendMessage(msg);
        player.sendMessage(" ");

    }
}
