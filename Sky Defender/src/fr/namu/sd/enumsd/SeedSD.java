package fr.namu.sd.enumsd;

import java.util.HashMap;

import org.bukkit.Location;;

public class SeedSD {

	public static HashMap<String, SeedSD> seed = new HashMap<>();
	
	private final String name;
	
	private final String WUID;
	
	private final Location banner;
	
	private final Location tpUp;
	
	private final Location tpDown;
	
	private final Location defSpawn;
	
	public SeedSD(String name, String WUID, Location banner, Location tpUp, Location tpDown, Location defSpawn) {
		this.name = name;
		this.WUID = WUID;
		this.banner = banner;
		this.tpUp = tpUp;
		this.tpDown = tpDown;
		this.defSpawn = defSpawn;
		SeedSD.seed.put(WUID, this);
	}
	
	
	
	public String getName() {
		return this.name;
	}
	
	public String getWUID() {
		return this.WUID;
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
