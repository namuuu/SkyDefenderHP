package fr.namu.sd.enumsd;


import org.bukkit.Material;

public enum StuffSD {

    IRON_SHARP(Material.IRON_SWORD, "§bÉpée en Fer : §eSharpness", 3),
    DIAMOND_SHARP(Material.DIAMOND_SWORD, "§bÉpée en Diamant : §eSharpness", 2),

    IRON_PROT(Material.IRON_CHESTPLATE, "§bArmure en Fer : §eProtection", 3),
    DIAMOND_PROT(Material.DIAMOND_CHESTPLATE, "§bArmure en Diamant : §eProtection", 2),

    BOW_POWER(Material.BOW, "§bArc : §ePower", 3),

    STUFF_LIMIT(Material.DIAMOND, "§bLimite de Pièces", 2),
    ;

    private String name;

    private int value;

    private Material mat;

    StuffSD(Material mat, String name, int value) {
        this.name = name;
        this.value = value;
        this.mat = mat;
    }

    public Material getMaterial() {
        return this.mat;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addValue(int value) {
        this.value = this.value + value;
    }

    public int getValue() {
        return this.value;
    }

    public String[] showValue() {
        return new String[] {
                "§eLimite : §b" + getValue(),
                "§7Clic Gauche : diminuer",
                "§7Clic Droit : augmenter"};
    }
}
