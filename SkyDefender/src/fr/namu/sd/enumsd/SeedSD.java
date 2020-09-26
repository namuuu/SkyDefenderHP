package fr.namu.sd.enumsd;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum SeedSD {

    TEST("Map de Test", "6010626937157214284",
            new Location(Bukkit.getWorld("world"), 28.5, 32, -16.5),
            new Location(Bukkit.getWorld("world"), 28.5, 32, -11.5),
            new Location(Bukkit.getWorld("world"), 28.5, 32, -5.5),
            new Location(Bukkit.getWorld("world"), 28, 32, -3)),
    MEDIEVAL("Medieval", "bc70bd50-6e25-4658-a774-cd4fa4ec861e",
            new Location(Bukkit.getWorld("world"), 0, 189, 0),
            new Location(Bukkit.getWorld("world"), -22, 159, 0),
            new Location(Bukkit.getWorld("world"), 0, 71, 0),
            new Location(Bukkit.getWorld("world"), -3, 209, -19)),
    OBSERVATOIRE("Observatoire", "f8f54319-5aa7-43b6-a48f-4b4de1a41db5",
            new Location(Bukkit.getWorld("world"), 1, 230, 0),
            new Location(Bukkit.getWorld("world"), 65, 198, 31),
            new Location(Bukkit.getWorld("world"), 66, 70, 44),
            new Location(Bukkit.getWorld("world"), 0, 240, 0)),
    ;

    private String MapName;
    private String WUID;

    private Location spawnDef;
    private Location teleportUp;
    private Location teleportDown;
    private Location banner;

    SeedSD(String MapName, String WUID, Location spawnDef, Location teleportUp, Location teleportDown, Location banner){
        this.MapName = MapName;
        this.WUID = WUID;
        this.spawnDef = spawnDef;
        this.teleportUp = teleportUp;
        this.teleportDown = teleportDown;
        this.banner = banner;

    }

    public String getMapName() {
        return this.MapName;
    }

    public String getWUID() {
        return this.WUID;
    }

    public Location getSpawnDefense() { return this.spawnDef; }

    public Location getTeleportUp() { return this.teleportUp; }

    public Location getTeleportDown() { return this.teleportDown; }

    public Location getBanner() { return this.banner;}
}
