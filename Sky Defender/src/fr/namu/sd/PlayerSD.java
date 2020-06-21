package fr.namu.sd;

import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerSD {
	  private State state = State.VIVANT;
	  
	  private Camp camp = Camp.NULL;
	  
	  private Boolean power = Boolean.valueOf(true);
	  
	  private Boolean kit = Boolean.valueOf(false);
	  
	  Scoreboard board;
	  
	  private final List<ItemStack> itemsdeath = new ArrayList<>();
	  
	  private Location spawn;
	  
	  private int diamondlimit = 17;
	  
	  private int nbkill = 0;
	  
	  private UUID killer = null;
	  
	  private UUID prey = null;
	  
	  private UUID lastDamager = null;
	  
	  public PlayerSD() {
	    this.board = Bukkit.getScoreboardManager().getNewScoreboard();
	  }
	  
	  public Scoreboard getScoreBoard() {
	    return this.board;
	  }
	  
	  public void setScoreBoard(Scoreboard board) {
	    this.board = board;
	  }
	  
	  public void setItemDeath(ItemStack[] itemsdeath) {
	    this.itemsdeath.addAll(Arrays.asList(itemsdeath));
	  }
	  
	  public List<ItemStack> getItemDeath() {
	    return this.itemsdeath;
	  }
	  
	  public void addItemDeath(ItemStack itemsdeath) {
	    this.itemsdeath.add(itemsdeath);
	  }
	  
	  public void clearItemDeath() {
	    this.itemsdeath.clear();
	  }
	  
	  public void setState(State state) {
	    this.state = state;
	  }
	  
	  public boolean isState(State state) {
	    return (this.state == state);
	  }
	  
	  public void addOneKill() {
	    this.nbkill++;
	  }
	  
	  public int getNbKill() {
	    return this.nbkill;
	  }
	  
	  public void setKitStuff(Boolean kit) {
	    this.kit = kit;
	  }
	  
	  public Boolean hasKitStuff() {
	    return this.kit;
	  }
	  
	  public void setCamp(Camp camp) {
	    this.camp = camp;
	  }
	  
	  public Camp getCamp() {
	    return this.camp;
	  }
	  
	  public Boolean isRole(Camp camp) {
	    return Boolean.valueOf(this.kit.equals(camp));
	  }
	  
	  public void setPower(Boolean power) {
	    this.power = power;
	  }
	  
	  public Boolean hasPower() {
	    return this.power;
	  }
	  
	  public void setSpawn(Location spawn) {
	    this.spawn = spawn;
	  }
	  
	  public Location getSpawn() {
	    return this.spawn;
	  }
	  
	  public void setKiller(UUID killer) {
	    this.killer = killer;
	  }
	  
	  public UUID getKiller() {
	    return this.killer;
	  }
	  
	  public void setPrey(UUID prey) {
		  this.prey = prey;
	  }
	  
	  public UUID getPrey() {
		  return this.prey;
	  }
	  
	  public void setLastDamager(UUID lastDamager) {
		  this.lastDamager = lastDamager;
	  }
	  
	  public UUID getLastDamager() {
		  return this.lastDamager;
	  }
	  
	  public void decDiamondLimit() {
	    this.diamondlimit--;
	  }
	  
	  public int getDiamondLimit() {
	    return this.diamondlimit;
	  }
	  
	  public void setDiamondLimit(int diamond) {
	    this.diamondlimit = diamond;
	  }
	}
