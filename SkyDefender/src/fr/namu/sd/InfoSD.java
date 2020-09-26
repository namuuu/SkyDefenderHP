package fr.namu.sd;

import fr.namu.sd.enumsd.StateSD;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InfoSD {

    public World world;
    private StateSD state;

    private String MapName;
    private Location spawnDefense;
    private Location teleportUp;
    private Location teleportDown;
    private Location bannner;

    private int playerSize;
    private int playersMax = 20;

    public List<ItemStack> itemStart = new ArrayList<>();
    public ItemStack[] armorStart;

    public UUID hostUID;

    public InfoSD(MainSD main) {
    }

    public void setInfo() {
        world = Bukkit.getWorld("world");
        this.setState(StateSD.LOBBY);
        MapName = "Custom Map";
        playerSize = 0;
    }

    public void setState(StateSD state) {
        this.state = state;
    }
    public boolean isState(StateSD state) {
        return (this.state == state);
    }

    public void setHost(UUID uuid) {
        hostUID = uuid;
    }
    public Player getHost() { return Bukkit.getPlayer(hostUID);}

    public void addPlayerSize() {playerSize++;}
    public void decPlayerSize() {playerSize--;}
    public int getPlayerSize() {return playerSize;}

    public void setPlayersMax(int size) {this.playersMax = size;}
    public int getPlayersMax() {return this.playersMax;}

    public void setMapName(String MapName) {this.MapName = MapName;}
    public String getMapName() {return this.MapName;}

    public void setSpawnDefense(Location loc) { this.spawnDefense = loc; }
    public Location getSpawnDefense() { return this.spawnDefense; }

    public void setTeleportUp(Location loc) { this.teleportUp = loc; }
    public Location getTeleportUp() { return this.teleportUp; }
    public void setTeleportDown(Location loc) { this.teleportDown = loc; }
    public Location getTeleportDown() { return this.teleportDown; }

    public void setBanner(Location loc) { this.bannner = loc; }
    public Location getBanner() { return this.bannner; }
}
