package fr.namu.sd.enumsd;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum TeamSD {
    ATTAQUE("§cAttaquants", "§7[§cA§7] §c", 9, DyeColor.RED, new ArrayList<>()),
    DEFENSE("§9Défenseurs", "§7[§9D§7] §9", 9, DyeColor.BLUE, new ArrayList<>()),

    RED("§4Équipe Rouge", "§7[§cR§7] §c", 9, DyeColor.RED, new ArrayList<>()),
    ORANGE("§6Équipe Orange","§7[§6O§7] §6", 9, DyeColor.ORANGE, new ArrayList<>()),
    YELLOW("§eÉquipe Jaune","§7[§eJ§7] §e", 9, DyeColor.YELLOW, new ArrayList<>()),
    GREEN("§aÉquipe Verte","§7[§aV§7] §a", 9, DyeColor.GREEN, new ArrayList<>()),
    AQUA("§bÉquipe Cyan","§7[§bC§7] §b", 9, DyeColor.LIGHT_BLUE, new ArrayList<>()),
    PINK("§dÉquipe Rose","§7[§dR§7] §d", 9, DyeColor.PINK, new ArrayList<>()),
    PURPLE("§5Équipe Mauve","§7[§5M§7] §5", 9, DyeColor.PURPLE, new ArrayList<>()),

    NULL("§fAucune Équipe","§7", 99, DyeColor.WHITE, new ArrayList<>());
    ;

    private final String teamName;
    private final String teamPrefix;
    private int size;
    private final DyeColor color;
    private List<Player> list;

    TeamSD(String teamName, String teamPrefix, int size, DyeColor color, List<Player> list) {
        this.teamName = teamName;
        this.teamPrefix = teamPrefix;
        this.size = size;
        this.color = color;
        this.list = list;
    }

    public String getName() {
        return this.teamName;
    }
    public String getPrefix() { return this.teamPrefix; }

    public void addSize() {this.size++;}
    public void decSize() {this.size--;}
    public int getSize() {
        return this.size;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void addPlayer(Player player) {
        this.list.add(player);
    }

    public void removePlayer(Player player) {
        this.list.remove(player);
    }

    public List<Player> getPlayers() {
        return this.list;
    }
}
