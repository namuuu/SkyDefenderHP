package fr.namu.sd;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import fr.namu.sd.enumsd.BorderSD;
import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.RulesSD;
import fr.namu.sd.enumsd.ScenarioSD;
import fr.namu.sd.enumsd.TimerSD;
import fr.namu.sd.enumsd.ToolSD;

public class MenuSD {
	private final MainSD main;
	
	HashMap<String, ItemStack[]> invMap = new HashMap<String, ItemStack[]>();
	  
	  public MenuSD(MainSD main) {
	    this.main = main;
	  }
	  
	  public void playerInvSee(Player sender, Player select) {
		  Inventory inv = Bukkit.createInventory(null, 54, "§7Visionneur d'inventaire");
		  invMap.put(select.getUniqueId().toString(), select.getInventory().getContents());
		  inv.setContents(invMap.get(select.getUniqueId().toString()));
		  inv.setItem(45, select.getInventory().getHelmet());
		  inv.setItem(46, select.getInventory().getChestplate());
		  inv.setItem(47, select.getInventory().getLeggings());
		  inv.setItem(48, select.getInventory().getBoots());
	  }
	  
	  public void teamList(Player player) {
		  if(ToolSD.MULTIPLE_TEAM.getValue() == false) {
			  Inventory inv = Bukkit.createInventory(null, 9, "§7Choisis ton Équipe !");
			  inv.setItem(1, metaBanner(DyeColor.RED, "§eChoisir l'équipe des attaquants"));
			  inv.setItem(7, metaBanner(DyeColor.BLUE, "§eChoisir l'équipe des défenseurs"));
			  inv.setItem(4, metaBanner(DyeColor.WHITE, "§eNe choisir aucune équipe"));
			  player.openInventory(inv);
		  } else {
			  Inventory inv = Bukkit.createInventory(null, 27, "§7Choisis ton Équipe !");
			  inv.setItem(1, metaBanner(DyeColor.RED, "§eChoisir l'équipe rouge"));
			  inv.setItem(2, metaBanner(DyeColor.ORANGE, "§eChoisir l'équipe orange"));
			  inv.setItem(3, metaBanner(DyeColor.YELLOW, "§eChoisir l'équipe jaune"));
			  inv.setItem(4, metaBanner(DyeColor.GREEN, "§eChoisir l'équipe verte"));
			  inv.setItem(5, metaBanner(DyeColor.LIGHT_BLUE, "§eChoisir l'équipe cyan"));
			  inv.setItem(6, metaBanner(DyeColor.PINK, "§eChoisir l'équipe rose"));
			  inv.setItem(7, metaBanner(DyeColor.PURPLE, "§eChoisir l'équipe violette"));
			  inv.setItem(21, metaBanner(DyeColor.BLUE, "§eChoisir l'équipe des défenseurs"));
			  inv.setItem(23, metaBanner(DyeColor.WHITE, "§eNe choisir aucune équipe"));
			  player.openInventory(inv);
		  }		  
	  }
	  
	  public void rulesList(Player player) {
		  Inventory inv = Bukkit.createInventory(null, 6*9, "§7Les règles de la partie");
		  inv.setItem(19, metaExtra(Material.DIAMOND_SWORD, "§eNiveau du Sharpness", 1, new String[] {"§bÉpée en Diamant :§a" + RulesSD.DIAMOND_SHARPNESS.getValue(), "§bÉpée en Fer :§a" + RulesSD.IRON_SHARPNESS.getValue()}));
		  inv.setItem(20, metaExtra(Material.DIAMOND_CHESTPLATE, "§eNiveau du Protection", 1, new String[] {"§bPlastron en Diamant :§a" + RulesSD.DIAMOND_PROTECTION.getValue(), "§bPlastron en Fer :§a" + RulesSD.IRON_PROTECTION.getValue()}));
		  inv.setItem(28, metaExtra(Material.BOW, "§eNiveau du Power", 1, new String[] {"§bArc :§a" + RulesSD.BOW_POWER.getValue()}));
		  inv.setItem(29, metaExtra(Material.FISHING_ROD, "§eRodLess", 1, infoValue(ScenarioSD.ROD_LESS.getValue())));
		  inv.setItem(37, metaExtra(Material.FISHING_ROD, "§eNombre de pièces en diamant", 1, new String[] {"§bPièces :§a" + RulesSD.DIAMOND_ARMOR_NB.getValue()}));
		  inv.setItem(50, metaExtra(Material.FISHING_ROD, "§eHorseLess", 1, infoValue(ScenarioSD.HORSE_LESS.getValue())));
		  player.openInventory(inv);
	  }
	  
	  public void configList(Player player) {
		  Inventory inv = Bukkit.createInventory(null, 18, "§7Paramètres de la partie");
		  inv.setItem(0, metaBanner(DyeColor.BLACK, "§eModifier les Équipes"));
		  inv.setItem(1, metaExtra(Material.BARRIER, "§eModifier la Bordure", 1, new String[] {}));
		  inv.setItem(3, metaExtra(Material.IRON_SWORD, "§eModifier l'activation du PVP", 1, new String[] {}));
		  inv.setItem(4, metaExtra(Material.SLIME_BALL, "§eModifier les locations de la partie", 1, new String[] {}));
		  inv.setItem(5, metaExtra(Material.BOOK, "§eModifier les limites d'Équipement", 1, new String[] {}));
		  inv.setItem(7, metaExtra(Material.CHEST, "§eModifier l'inventaire de départ", 1, new String[] {}));
		  inv.setItem(8, metaExtra(Material.COMMAND, "§eModifier les Scénarios de la partie", 1, new String[] {}));	
		  inv.setItem(17, metaExtra(Material.GLASS, "§9Se mettre en tant que Spectateur de la Partie", 1, returnSpec(player)));
		  
		  player.openInventory(inv);
	  }
	  
	  public void borderEdit(Player player) {
		  Inventory inv = Bukkit.createInventory(null, 27, "§7Paramètres de la Bordure");
		  inv.setItem(0, metaExtra(Material.COMPASS, "§eRetour", 1, new String[] {}));
		  
		  inv.setItem(10, metaExtra(Material.STONE_BUTTON, "§b- 50 Blocs", 1, new String[] {}));
		  inv.setItem(11, metaExtra(Material.REDSTONE_BLOCK, "§eBordure finale : §b" + BorderSD.BORDER_MIN.getValue() + " blocs", 1, new String[] {}));
		  inv.setItem(12, metaExtra(Material.STONE_BUTTON, "§b+ 50 Blocs", 1, new String[] {}));
		  
		  inv.setItem(14, metaExtra(Material.STONE_BUTTON, "§b- 50 Blocs", 1, new String[] {}));
		  inv.setItem(15, metaExtra(Material.GLOWSTONE, "§eBordure initiale : §b" + BorderSD.BORDER_MAX.getValue() + " blocs", 1, new String[] {}));
		  inv.setItem(16, metaExtra(Material.STONE_BUTTON, "§b+ 50 Blocs", 1, new String[] {}));
		  
		  inv.setItem(21, metaExtra(Material.STONE_BUTTON, "§b- 30 Secondes", 1, new String[] {}));
		  inv.setItem(22, metaExtra(Material.WATCH, "§eDébut du mouvement de la Bordure : §b" + this.main.conversion(TimerSD.BORDER_BEGIN.getValue()), 1, new String[] {}));
		  inv.setItem(23, metaExtra(Material.STONE_BUTTON, "§b+ 30 Secondes", 1, new String[] {}));
		  
		  player.openInventory(inv);
	  }
	  
	  public void pvpEdit(Player player) {
		  Inventory inv = Bukkit.createInventory(null, 27, "§7Paramètres du PVP");
		  inv.setItem(0, metaExtra(Material.COMPASS, "§eRetour", 1, new String[] {}));
		  
		  inv.setItem(12, metaExtra(Material.STONE_BUTTON, "§b- 30 Secondes", 1, new String[] {}));
		  inv.setItem(13, metaExtra(Material.DIAMOND_SWORD, "§eAction du PVP : §b" + this.main.conversion(TimerSD.PVP.getValue()), 1, new String[] {}));
		  inv.setItem(14, metaExtra(Material.STONE_BUTTON, "§b+ 30 Secondes", 1, new String[] {}));
		  
		  player.openInventory(inv);
	  }
	  
	  public void enhancementEdit(Player player) {
		  Inventory inv = Bukkit.createInventory(null, 18, "§7Paramètres des Enchantements");
		  inv.setItem(0, metaExtra(Material.COMPASS, "§eRetour", 1, new String[] {}));
		  
		  inv.setItem(9, metaExtra(Material.DIAMOND, "§eNombre de pièces en diamant maximal : §b" + RulesSD.DIAMOND_ARMOR_NB.getValue(), 1, new String[] {"§7Clic gauche : §8Réduire", "§7Clic droit : §8Augmenter"}));
		  inv.setItem(11, metaExtra(Material.DIAMOND_SWORD, "§eEnchantment SHARPNESS maximal sur l'épée en diamant : §b" + RulesSD.DIAMOND_SHARPNESS.getValue(), 1, new String[] {"§7Clic gauche : §8Réduire", "§7Clic droit : §8Augmenter"}));
		  inv.setItem(12, metaExtra(Material.DIAMOND_CHESTPLATE, "§eEnchantment PROTECTION maximal sur l'armure en diamant : §b" + RulesSD.DIAMOND_PROTECTION.getValue(), 1, new String[] {"§7Clic gauche : §8Réduire", "§7Clic droit : §8Augmenter"}));
		  inv.setItem(14, metaExtra(Material.IRON_SWORD, "§eEnchantment SHARPNESS maximal sur l'épée en fer : §b" + RulesSD.IRON_SHARPNESS.getValue(), 1, new String[] {"§7Clic gauche : §8Réduire", "§7Clic droit : §8Augmenter"}));
		  inv.setItem(15, metaExtra(Material.IRON_CHESTPLATE, "§eEnchantment PROTECTION maximal sur l'armure en fer : §b" + RulesSD.IRON_PROTECTION.getValue(), 1, new String[] {"§7Clic gauche : §8Réduire", "§7Clic droit : §8Augmenter"}));
		  inv.setItem(17, metaExtra(Material.BOW, "§eEnchantment POWER maximal sur l'arc : §b" + RulesSD.BOW_POWER.getValue(), 1, new String[] {"§7Clic gauche : §8Réduire", "§7Clic droit : §8Augmenter"}));
		  
		  player.openInventory(inv);
	  }
	  
	  public void locConfig(Player player) {
		  player.getInventory().clear();
		  Inventory inv = player.getInventory();
		  
		  player.setGameMode(GameMode.CREATIVE);
		  inv.setItem(1, metaExtra(Material.BED, "§cPlacer le spawn des défenseurs", 1, new String[] {}));
		  inv.setItem(2, metaExtra(Material.DIRT, "§cPlacer le téléporteur des défenseurs (sol)", 1, new String[] {}));
		  inv.setItem(3, metaExtra(Material.GLASS, "§cPlacer le téléporteur des défenseurs (chateau)", 1, new String[] {}));
		  inv.setItem(4, metaBanner(DyeColor.WHITE, "§cDéfinir la bannière"));
		  inv.setItem(8, metaExtra(Material.REDSTONE, "§cQuitter le mode d'éditions des locations", 1, new String[] {}));
	  }
	  
	  public void ScenarioConfig(Player player) {
		  Inventory inv = Bukkit.createInventory(null, 54, "§7Gestion des scénarios");
		  
		  inv.setItem(0, metaExtra(Material.COMPASS, "§eRetour", 1, new String[] {}));
		  
		  inv.setItem(10, metaExtra(Material.IRON_ORE, ScenarioSD.CUT_CLEAN.getAppearance(), 1, returnValue(ScenarioSD.CUT_CLEAN.getValue())));
		  inv.setItem(11, metaExtra(Material.FURNACE, ScenarioSD.FAST_SMELTING.getAppearance(), 1, returnValue(ScenarioSD.FAST_SMELTING.getValue())));		  
		  inv.setItem(12, metaExtra(Material.EYE_OF_ENDER, ScenarioSD.CAT_EYES.getAppearance(), 1, returnValue(ScenarioSD.CAT_EYES.getValue())));
		  inv.setItem(13, metaExtra(Material.WOOL, ToolSD.RANDOM_TEAM.getAppearance(), 1, returnValue(ToolSD.RANDOM_TEAM.getValue())));
		  inv.setItem(14, metaExtra(Material.BEACON, RulesSD.FINAL_HEAL.getAppearance(), 1, new String[] {"§aTimer : §b" + this.main.conversion(RulesSD.FINAL_HEAL.getValue()), "§7Clic gauche : §8- 1 minute", "§7Clic droit : §8+ 1 minute"}));
		  inv.setItem(15, metaExtra(Material.GOLDEN_APPLE, ScenarioSD.GOLDEN_HEAD.getAppearance(), 1, returnValue(ScenarioSD.GOLDEN_HEAD.getValue())));
		  inv.setItem(16, metaExtra(Material.GOLD_PICKAXE, ScenarioSD.HASTEY_BOYS.getAppearance(), 1, returnValue(ScenarioSD.HASTEY_BOYS.getValue())));
		  
		  inv.setItem(19, metaExtra(Material.RABBIT_FOOT, ToolSD.PREY.getAppearance(), 1, returnValue(ToolSD.PREY.getValue())));
		  inv.setItem(20, metaExtra(Material.FISHING_ROD, ScenarioSD.ROD_LESS.getAppearance(), 1, returnValue(ScenarioSD.ROD_LESS.getValue())));
		  inv.setItem(21, metaExtra(Material.SADDLE, ScenarioSD.HORSE_LESS.getAppearance(), 1, returnValue(ScenarioSD.HORSE_LESS.getValue())));
		  inv.setItem(22, metaExtra(Material.BLAZE_POWDER, ScenarioSD.FIRE_LESS.getAppearance(), 1, returnValue(ScenarioSD.FIRE_LESS.getValue())));
		  inv.setItem(23, metaExtra(Material.EXP_BOTTLE, RulesSD.XP_BOOST.getAppearance(), 1, new String[] {"§aExperience Boost : §bx" + RulesSD.XP_BOOST.getValue(), "§7Clic gauche : §8- 1", "§7Clic droit : §8+ 1"}));
		  
		  player.openInventory(inv);
	  }
	  
	  public void baseStuff(Player player) {
		  player.getInventory().clear();
		  Inventory inv = player.getInventory();
		  inv.setItem(4, metaBanner(DyeColor.WHITE, "§eChoisir une équipe !"));
		  inv.setItem(7, metaExtra(Material.BOOK, "§eLes règles", 1, new String[] {""}));
	 	    if(player.hasPermission("host.use")) {
	 	    	player.getInventory().setItem(1, metaExtra(Material.NETHER_STAR, "§eParamétrer la partie", 1, new String[] {""}));
	 	    }
	  }
	  
	  public void teamEdit(Player player) {
		  Inventory inv = Bukkit.createInventory(null, 27, "§7Gestion des Équipes");
		  
		  inv.setItem(0, metaExtra(Material.COMPASS, "§eRetour", 1, new String[] {}));
		  inv.setItem(12, metaExtra(Material.REDSTONE_BLOCK, "§cNombre d'attaquants : §b" + Camp.ATTAQUE.getValue(), 1, new String[] {}));
		  inv.setItem(14, metaExtra(Material.LAPIS_BLOCK, "§9Nombre de défenseurs : §b" + Camp.DEFENSE.getValue(), 1, new String[] {}));
		  inv.setItem(19, metaBanner(DyeColor.RED, "§4Taille de l'" + Camp.RED.getName() + " : §b" + Camp.RED.getValue()));
		  inv.setItem(20, metaBanner(DyeColor.ORANGE, "§6Taille de l'" + Camp.ORANGE.getName() + " : §b" + Camp.ORANGE.getValue()));
		  inv.setItem(21, metaBanner(DyeColor.YELLOW, "§eTaille de l'" + Camp.YELLOW.getName() + " : §b" + Camp.YELLOW.getValue()));
		  inv.setItem(22, metaBanner(DyeColor.GREEN, "§aTaille de l'" + Camp.GREEN.getName() + " : §b" + Camp.GREEN.getValue()));
		  inv.setItem(23, metaBanner(DyeColor.LIGHT_BLUE, "§bTaille de l'" + Camp.AQUA.getName() + " : §b" + Camp.AQUA.getValue()));
		  inv.setItem(24, metaBanner(DyeColor.PINK, "§dTaille de l'" + Camp.PINK.getName() + " : §b" + Camp.PINK.getValue()));
		  inv.setItem(25, metaBanner(DyeColor.PURPLE, "§5Taille de l'" + Camp.PURPLE.getName() + " : §b" + Camp.PURPLE.getValue()));
		  
		  player.openInventory(inv);
	  }
	  
	  public String[] returnSpec(Player player) {
		  if(this.main.mjc.isSpectator(player)) {
			  return new String[] {"§7Vous êtes actuellement spectateur"};
		  } else {
			  return new String[] {"§7Vous n'êtes actuellement pas spectateur"};
		  }			  
	  }
	  
	  
	  public ItemStack metaExtra(Material m, String ItemName, int Amount, String[] lore) {
		    ItemStack item = new ItemStack(m, Amount);
		    ItemMeta im = item.getItemMeta();
		    im.setDisplayName(ItemName);
		    im.setLore(Arrays.asList(lore));
		    item.setItemMeta(im);
		    return item;
		  } //metaExtra(Material, "", 1, new String[] {"", ""})
	  
	  public ItemStack metaBanner(DyeColor color, String ItemName) {
		  ItemStack item = new ItemStack(Material.BANNER, 1);
		  BannerMeta bm = (BannerMeta) item.getItemMeta();
		  bm.setDisplayName(ItemName);
		  bm.setBaseColor(color);
		  item.setItemMeta(bm);
		  return item;
	  } //metaBanner(DyeColor, "")
	  
	  
	  
	  public String[] returnValue(Boolean value) {
		  if(value == true) {
			  return new String[] {"§aActivé", "§7Clic gauche : §8Désactiver", "§7Clic droit : §8Activer"};
		  }
		  if(value == false) {
			  return new String[] {"§cDésactivé", "§7Clic gauche : §8Désactiver", "§7Clic droit : §8Activer"};
		  } else {
			  return new String[] {"§9Error"};
		  }
	  }
	  
	  public String[] infoValue(Boolean value) {
		  if(value == true) {
			  return new String[] {"§aActivé"};
		  }
		  if(value == false) {
			  return new String[] {"§cDésactivé"};
		  } else {
			  return new String[] {"§9Error"};
		  }
	  }
}
