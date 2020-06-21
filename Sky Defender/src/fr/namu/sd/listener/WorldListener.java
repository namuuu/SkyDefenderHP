package fr.namu.sd.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.namu.sd.MainSD;
import fr.namu.sd.PlayerSD;
import fr.namu.sd.enumsd.RulesSD;
import fr.namu.sd.enumsd.ScenarioSD;

public class WorldListener implements Listener{
	final MainSD main;
	  
	  public WorldListener(MainSD main) {
	    this.main = main;
	  }
	  

	  @EventHandler
	  private void onBlockBreak(BlockBreakEvent event) {
	    Player player = event.getPlayer();
	    Block Block = event.getBlock();
	    int xp_rate = RulesSD.XP_BOOST.getValue();
	    Location loc = new Location(Block.getWorld(), Block.getLocation().getBlockX() + 0.5D, Block.getLocation().getBlockY() + 0.5D, Block.getLocation().getBlockZ() + 0.5D);
	    
	    switch (Block.getType()) {
	      case COAL_ORE:
	        if (!event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.IRON_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.STONE_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.GOLD_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.WOOD_PICKAXE))
	          return; 
	        if (ScenarioSD.CUT_CLEAN.getValue() == true) {
	          Block.setType(Material.AIR);
	          Block.getWorld().dropItem(loc, new ItemStack(Material.TORCH, 4)); 
		      ((ExperienceOrb)Block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(event.getExpToDrop() * xp_rate);
	        } 
	        break;
	      case REDSTONE_ORE:
	      case LAPIS_ORE:
	      case EMERALD_ORE:
	    	  ((ExperienceOrb)Block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(event.getExpToDrop() * xp_rate);
	      case DIAMOND_ORE:
	        if (!event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.IRON_PICKAXE))
	          return; 
	        if (ScenarioSD.DIAMOND_LIMIT.getValue() == true)
	          if (((PlayerSD)this.main.playersd.get(player.getUniqueId())).getDiamondLimit() > 0) {
	              ((PlayerSD)this.main.playersd.get(player.getUniqueId())).decDiamondLimit();
	              Block.setType(Material.AIR);
	            } else {
	            Block.getWorld().dropItem(loc, new ItemStack(Material.GOLD_INGOT, 1)); 
	            Block.setType(Material.AIR);
	          }  
	        ((ExperienceOrb)Block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(event.getExpToDrop() * xp_rate);
	        break;
	      case IRON_ORE:
	        if (!event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.IRON_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.STONE_PICKAXE))
	          return; 
	        if (ScenarioSD.CUT_CLEAN.getValue() == true) {
	          Block.setType(Material.AIR);
	          Block.getWorld().dropItem(loc, new ItemStack(Material.IRON_INGOT, 1)); 
	          ((ExperienceOrb)Block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(1 * xp_rate);
	        } 
	        break;
	      case GOLD_ORE:
	        if (!event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && !event.getPlayer().getItemInHand().getType().equals(Material.IRON_PICKAXE))
	          return; 
	        if (ScenarioSD.CUT_CLEAN.getValue() == true) {
	          Block.setType(Material.AIR);
	          Block.getWorld().dropItem(loc, new ItemStack(Material.GOLD_INGOT, 1)); 
	          ((ExperienceOrb)Block.getWorld().spawn(loc, ExperienceOrb.class)).setExperience(1 * xp_rate);
	        } 
	        break;
	      case GRAVEL:
	          Block.setType(Material.AIR);
	          Block.getWorld().dropItem(loc, new ItemStack(Material.FLINT, 1)); 
	        break;
		default:
			break;
	    } 
	  }
	  
	  @EventHandler
	  public void onBlockMelt(BlockFadeEvent event) {
		  if(event.getBlock().getLocation().getBlockY() > 90 && event.getBlock().getType() == Material.ICE) {
			  event.setCancelled(true);
		  }
	  }

	  @EventHandler
	  public void onEntityDeath(EntityDeathEvent event) {
	    if (ScenarioSD.CUT_CLEAN.getValue() == true) {
	      List<ItemStack> loots = event.getDrops();
	      for (int i = loots.size() - 1; i >= 0; i--) {
	        ItemStack is = loots.get(i);
	        if (is == null)
	          return; 
	        switch (is.getType()) {
	          case RAW_BEEF:
	            loots.remove(i);
	            loots.add(new ItemStack(Material.COOKED_BEEF));
	            break;
	          case PORK:
	            loots.remove(i);
	            loots.add(new ItemStack(Material.GRILLED_PORK));
	            break;
	          case RAW_CHICKEN:
	            loots.remove(i);
	            loots.add(new ItemStack(Material.COOKED_CHICKEN));
	            break;
	          case MUTTON:
	            loots.remove(i);
	            loots.add(new ItemStack(Material.COOKED_MUTTON));
	            break;
	          case RABBIT:
	            loots.remove(i);
	            loots.add(new ItemStack(Material.COOKED_RABBIT));
	            break;
			default:
				break;
	        } 
	      } 
	    } 
	  }
	  
	  @EventHandler
	  public void onBurn(FurnaceBurnEvent event) {
	      handleCookingTime((Furnace)event.getBlock().getState()); 
	  }
	  
	  private void handleCookingTime(final Furnace block) {
	    (new BukkitRunnable() {
	        public void run() {
	          if (block.getCookTime() > 0 || block.getBurnTime() > 0) {
	            block.setCookTime((short)(block.getCookTime() + 8));
	            block.update();
	          } else {
	            cancel();
	          } 
	        }
	      }).runTaskTimer((Plugin)this.main, 1L, 1L);
	  }
	  
	  @EventHandler
	  public void onCraft(PrepareItemCraftEvent event) {
	    if (event.getInventory() != null) {
	      CraftingInventory inv = event.getInventory();
	      ItemStack AIR = new ItemStack(Material.AIR);
	      
	      if (inv.getResult().getType() == Material.GOLDEN_APPLE && inv.getResult().getDurability() == 1)
	        inv.setResult(AIR); 
	      if (ScenarioSD.ROD_LESS.getValue() == true && inv.getResult().getType() == Material.FISHING_ROD)
	        inv.setResult(AIR); 
	      if (ScenarioSD.HASTEY_BOYS.getValue() == true) {
	        ItemStack lo1, l1, lj1, lI1, lN1, le1, lep1, leB1, lXe1, leJ1, lHe1, lHej1, lnHej1, t1, Jt1, hJt1, FlnHej1, Qt1, PJt1, VhJt1;
	        ItemMeta lo1M, l1M, lj1M, lI1M, lN1M, leN1M, lepN1M, leBN1M, lXeN1M, leJN1M, leHN1M, leHNj1M, nleHNj1M, t1M, Jt1M, hJt1M, FnleHNj1M, Qt1M, PJt1M, VhJt1M;
	        Material itemType = event.getInventory().getResult().getType();
	        switch (itemType) {
	          case WOOD_PICKAXE:
	            lo1 = new ItemStack(Material.WOOD_PICKAXE);
	            lo1M = lo1.getItemMeta();
	            lo1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            lo1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lo1.setItemMeta(lo1M);
	            event.getInventory().setResult(lo1);
	            break;
	          case WOOD_AXE:
	            l1 = new ItemStack(Material.WOOD_AXE);
	            l1M = l1.getItemMeta();
	            l1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            l1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            l1.setItemMeta(l1M);
	            event.getInventory().setResult(l1);
	            break;
	          case WOOD_SPADE:
	            lj1 = new ItemStack(Material.WOOD_SPADE);
	            lj1M = lj1.getItemMeta();
	            lj1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            lj1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lj1.setItemMeta(lj1M);
	            event.getInventory().setResult(lj1);
	            break;
	          case WOOD_HOE:
	            lI1 = new ItemStack(Material.WOOD_HOE);
	            lI1M = lI1.getItemMeta();
	            lI1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            lI1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lI1.setItemMeta(lI1M);
	            event.getInventory().setResult(lI1);
	            break;
	          case STONE_PICKAXE:
	            lN1 = new ItemStack(Material.STONE_PICKAXE);
	            lN1M = lN1.getItemMeta();
	            lN1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            lN1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lN1.setItemMeta(lN1M);
	            event.getInventory().setResult(lN1);
	            break;
	          case STONE_AXE:
	            le1 = new ItemStack(Material.STONE_AXE);
	            leN1M = le1.getItemMeta();
	            leN1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            leN1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            le1.setItemMeta(leN1M);
	            event.getInventory().setResult(le1);
	            break;
	          case STONE_SPADE:
	            lep1 = new ItemStack(Material.STONE_SPADE);
	            lepN1M = lep1.getItemMeta();
	            lepN1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            lepN1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lep1.setItemMeta(lepN1M);
	            event.getInventory().setResult(lep1);
	            break;
	          case STONE_HOE:
	            leB1 = new ItemStack(Material.STONE_HOE);
	            leBN1M = leB1.getItemMeta();
	            leBN1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            leBN1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            leB1.setItemMeta(leBN1M);
	            event.getInventory().setResult(leB1);
	            break;
	          case IRON_PICKAXE:
	            lXe1 = new ItemStack(Material.IRON_PICKAXE);
	            lXeN1M = lXe1.getItemMeta();
	            lXeN1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            lXeN1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lXe1.setItemMeta(lXeN1M);
	            event.getInventory().setResult(lXe1);
	            break;
	          case IRON_AXE:
	            leJ1 = new ItemStack(Material.IRON_AXE);
	            leJN1M = leJ1.getItemMeta();
	            leJN1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            leJN1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            leJ1.setItemMeta(leJN1M);
	            event.getInventory().setResult(leJ1);
	            break;
	          case IRON_SPADE:
	            lHe1 = new ItemStack(Material.IRON_SPADE);
	            leHN1M = lHe1.getItemMeta();
	            leHN1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            leHN1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lHe1.setItemMeta(leHN1M);
	            event.getInventory().setResult(lHe1);
	            break;
	          case IRON_HOE:
	            lHej1 = new ItemStack(Material.IRON_HOE);
	            leHNj1M = lHej1.getItemMeta();
	            leHNj1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            leHNj1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lHej1.setItemMeta(leHNj1M);
	            event.getInventory().setResult(lHej1);
	            break;
	          case GOLD_PICKAXE:
	            lnHej1 = new ItemStack(Material.GOLD_PICKAXE);
	            nleHNj1M = lnHej1.getItemMeta();
	            nleHNj1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            nleHNj1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            lnHej1.setItemMeta(nleHNj1M);
	            event.getInventory().setResult(lnHej1);
	            break;
	          case GOLD_AXE:
	            t1 = new ItemStack(Material.GOLD_AXE);
	            t1M = t1.getItemMeta();
	            t1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            t1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            t1.setItemMeta(t1M);
	            event.getInventory().setResult(t1);
	            break;
	          case GOLD_SPADE:
	            Jt1 = new ItemStack(Material.GOLD_SPADE);
	            Jt1M = Jt1.getItemMeta();
	            Jt1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            Jt1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            Jt1.setItemMeta(Jt1M);
	            event.getInventory().setResult(Jt1);
	            break;
	          case GOLD_HOE:
	            hJt1 = new ItemStack(Material.GOLD_HOE);
	            hJt1M = hJt1.getItemMeta();
	            hJt1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            hJt1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            hJt1.setItemMeta(hJt1M);
	            event.getInventory().setResult(hJt1);
	            break;
	          case DIAMOND_PICKAXE:
	            FlnHej1 = new ItemStack(Material.DIAMOND_PICKAXE);
	            FnleHNj1M = FlnHej1.getItemMeta();
	            FnleHNj1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            FnleHNj1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            FlnHej1.setItemMeta(FnleHNj1M);
	            event.getInventory().setResult(FlnHej1);
	            break;
	          case DIAMOND_AXE:
	            Qt1 = new ItemStack(Material.DIAMOND_AXE);
	            Qt1M = Qt1.getItemMeta();
	            Qt1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            Qt1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            Qt1.setItemMeta(Qt1M);
	            event.getInventory().setResult(Qt1);
	            break;
	          case DIAMOND_SPADE:
	            PJt1 = new ItemStack(Material.DIAMOND_SPADE);
	            PJt1M = PJt1.getItemMeta();
	            PJt1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            PJt1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            PJt1.setItemMeta(PJt1M);
	            event.getInventory().setResult(PJt1);
	            break;
	          case DIAMOND_HOE:
	            VhJt1 = new ItemStack(Material.DIAMOND_HOE);
	            VhJt1M = VhJt1.getItemMeta();
	            VhJt1M.addEnchant(Enchantment.DIG_SPEED, 3, true);
	            VhJt1M.addEnchant(Enchantment.DURABILITY, 3, true);
	            VhJt1.setItemMeta(VhJt1M);
	            event.getInventory().setResult(VhJt1);
	            break;
			default:
				break;
	        } 
	      } 
	    } 
	  }
	  
	  @EventHandler
	  public void onWeatherChange(WeatherChangeEvent event) {
		  event.setCancelled(true);
	  }
	  
	  public ItemStack metaExtra(Material m, String ItemName, int Amount, String[] lore) {
		    ItemStack item = new ItemStack(m, Amount);
		    ItemMeta im = item.getItemMeta();
		    im.setDisplayName(ItemName);
		    im.setLore(Arrays.asList(lore));
		    item.setItemMeta(im);
		    return item;
		  } //metaExtra(Material, "", 1, new String[] {"", ""})
}
