package fr.namu.sd.util;

import fr.namu.sd.MainSD;
import fr.namu.sd.enumsd.TeamSD;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUtil {

    private MainSD main;

    public ItemUtil(MainSD main) {
        this.main = main;
    }

    public ItemStack basicItem (Material m, String ItemName, int Amount, String[] lore) {
        ItemStack item = new ItemStack(m, Amount);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ItemName);
        if(lore != null)
            im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public ItemStack bannerItem(DyeColor color, String ItemName) {
        ItemStack item = new ItemStack(Material.BANNER, 1);
        BannerMeta bm = (BannerMeta) item.getItemMeta();
        bm.setDisplayName(ItemName);
        bm.setBaseColor(color);
        item.setItemMeta(bm);
        return item;
    }

    public ItemStack bannerItemWithLore(DyeColor color, String ItemName, String[] lore) {
        ItemStack item = new ItemStack(Material.BANNER, 1);
        BannerMeta bm = (BannerMeta) item.getItemMeta();
        bm.setDisplayName(ItemName);
        bm.setBaseColor(color);
        bm.setLore(Arrays.asList(lore));
        item.setItemMeta(bm);
        return item;
    }

    public ItemStack bannerTeam(DyeColor color, String ItemName, TeamSD team) {
        ItemStack item = new ItemStack(Material.BANNER, 1);
        BannerMeta bm = (BannerMeta) item.getItemMeta();
        bm.setDisplayName(ItemName);
        bm.setBaseColor(color);
        List<String> lore = new ArrayList<>();
        for(Player player : team.getPlayers()) {
            lore.add("§8- §7" + player.getName());
        }
        bm.setLore(lore);
        item.setItemMeta(bm);
        return item;
    }

    public ItemStack skullItem(String ItemName, String PlayerName) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
        skullMeta.setOwner(PlayerName);
        skullMeta.setDisplayName(ItemName);
        skull.setItemMeta(skullMeta);
        return skull;
    } //skullMeta("", "")

    public ItemStack glassPaneItem(String ItemName, Short Durability) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ItemName);
        item.setItemMeta(im);
        item.setDurability(Durability);
        return item;
    } // metaGlassPane("", (Short))

    public void saveStartStuff(Player player) {
        Inventory inv = player.getInventory();
        for(ItemStack item : inv.getContents()) {
            if(item != null) {
                this.main.info.itemStart.add(item);
            }
        }
        this.main.info.armorStart = player.getInventory().getArmorContents();
    }

    public void setStartStuff(Player player) {
        Inventory inv = player.getInventory();
        for(ItemStack item : this.main.info.itemStart) {
            inv.addItem(item);
        }
        player.getInventory().setArmorContents(this.main.info.armorStart);
    }

}
