package fr.namu.sd.enumsd;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum SeedSD {

	BETA("Bêta", -7202540931898112989L, new Location(Bukkit.getWorld("world"), 0, 0, -0), new Location(Bukkit.getWorld("world"), -0, 0, -0), new Location(Bukkit.getWorld("world"), -0, 0, -0), new Location(Bukkit.getWorld("world"), 0, 0, -0)),
	
	;
	
	private final String name;
	
	private final Long seed;
	
	private final Location banner;
	
	private final Location tpUp;
	
	private final Location tpDown;
	
	private final Location defSpawn;
	
	SeedSD(String name, Long seed, Location banner, Location tpUp, Location tpDown, Location defSpawn) {
		this.name = name;
		this.seed = seed;
		this.banner = banner;
		this.tpUp = tpUp;
		this.tpDown = tpDown;
		this.defSpawn = defSpawn;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Long getSeed() {
		return this.seed;
	}
	
	public Location getBanner() {
		return this.banner;
	}
	
	public Location getTPUP() {
		return this.tpUp;
	}
	
	public Location getTPDown() {
		return this.tpDown;
	}
	
	public Location getDefSpawn() {
		return this.defSpawn;
	}
}
