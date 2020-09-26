package fr.namu.sd.enumsd;

import fr.namu.sd.MainSD;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum ScenarioSD {
    MULTIPLE_TEAM("§6Team Multiples", Material.LEVER, Boolean.valueOf(true), 0),
    CAT_EYES("§2Cat Eyes", Material.EYE_OF_ENDER, Boolean.valueOf(true), 0),
    CUT_CLEAN("§eCut Clean", Material.GOLD_PICKAXE, Boolean.valueOf(true), 0),
    PREY("§5Prey", Material.RABBIT_FOOT, Boolean.valueOf(false), 0),
    MASTERLEVEL("§aMaster Level", Material.EXP_BOTTLE, null, 1),

    FASTSMELTING("§cFast Smelting", Material.FURNACE, Boolean.valueOf(false), 0),
    FIRELESS("§4FireLess", Material.FIRE, Boolean.valueOf(false), 0),


    ;

    private MainSD main;

    private String name;

    private Boolean value;

    private int nb;

    private Material mat;

    ScenarioSD(String name, Material mat, Boolean value, int nb) {
        this.name = name;
        this.value = value;
        this.nb = nb;
        this.mat = mat;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getValue() {
        return this.value;
    }
    public void setValue(Boolean value) {
        this.value = value;
    }

    public int getNumber() {return this.nb;}

    public void switchValue(ClickType click) {
        if(value == null) {
            if(click == ClickType.LEFT) {
                if(nb == 0) {
                    return;
                }
                nb--;
            } else {
                nb++;
            }
            return;
        }
        if(value) {
            setValue(false);
            return;
        } else if (value == false) {
            setValue(true);
        }
    }

    public ItemStack itemDisplay() {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        if(value == null) {
            im.setLore(Arrays.asList(new String[]{"§bValeur : §e" + this.nb}));
            if(nb == 0) {
                im.setLore(Arrays.asList(new String[]{"§cEst inactif"}));
            }
        } else {
            if(value) {
                im.setLore(Arrays.asList(new String[]{"§aEst actif"}));
            } else {
                im.setLore(Arrays.asList(new String[]{"§cEst inactif"}));
            }
        }
        item.setItemMeta(im);
        return item;
    }
}
