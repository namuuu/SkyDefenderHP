package fr.namu.sd;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class StuffSD {
	
	private final List<ItemStack> death_loot = new ArrayList<>();
	  
	  private final List<ItemStack> start_loot = new ArrayList<>();
	  
	  public List<ItemStack> getDeathLoot() {
	    return this.death_loot;
	  }
	  
	  public List<ItemStack> getStartLoot() {
	    return this.start_loot;
	  }
	  
	  public void clearDeathLoot() {
	    this.death_loot.clear();
	  }
	  
	  public void clearStartLoot() {
	    this.start_loot.clear();
	  }
	  
	  public void addDeathLoot(ItemStack i) {
	    this.death_loot.add(i);
	  }
	  
	  public void addStartLoot(ItemStack i) {
	    this.start_loot.add(i);
	  }
	  
}
