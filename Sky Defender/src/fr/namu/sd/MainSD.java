package fr.namu.sd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import fr.RedLine.RankGestion.RankGestion;
import fr.RedLine.Util.TabGestion;
import fr.namu.sd.commandsd.AdminCMD;
import fr.namu.sd.commandsd.DefaultCMD;
import fr.namu.sd.commandsd.DevCMD;
import fr.namu.sd.enumsd.Camp;
import fr.namu.sd.enumsd.SeedSD;
import fr.namu.sd.enumsd.StateSD;
import fr.namu.sd.listener.MenuListener;
import fr.namu.sd.listener.PlayerListener;
import fr.namu.sd.listener.WorldListener;

import fr.redline.liaison.MiniJeux;

public class MainSD extends JavaPlugin {

	  public Scoreboard scoreboard;
	  
	  public final Map<UUID, FastBoard> boards = new HashMap<>();
	  
	  public final Map<UUID, PlayerSD> playersd = new HashMap<>();
	  
	  public final ScoreBoardSD score = new ScoreBoardSD(this);
	  
	  public final MenuSD menusd = new MenuSD(this);
	  
	  public final DeathManagement death_manage = new DeathManagement(this);
	  
	  public final StuffSD stuffsd = new StuffSD();
	  
	  public final EndSD endsd = new EndSD(this);
	  
	  public final PlayerListener PlList = new PlayerListener(this);
	  
	  public MiniJeux mjc = null;
	  
	  public RankGestion rg = RankGestion.getRankg();
	  
	  public final MenuListener ml = new MenuListener(this);
	  
	  public final DevCMD devcmd = new DevCMD(this);
	  
	  private StateSD state;
	  
	  private Location defspawn = null;
	  private Location defup = null;
	  private Location defdown = null;
	  private Location banner = null;
	  private String mapName = "";
	
	@Override
	public void onEnable() {
		System.out.println("Sky Defender is enabled.");
		enableSeeds();
		this.mjc = new MiniJeux(this, "SkyDefender", Bukkit.getServerName(), 24, 25578); 
	    setState(StateSD.LOBBY);
	    this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents((Listener)new PlayerListener(this), (Plugin)this);
	    pm.registerEvents((Listener)ml, (Plugin)this);
	    pm.registerEvents((Listener)new WorldListener(this), (Plugin)this);
	    AutoLaunchSD start = new AutoLaunchSD(this);
	    start.runTaskTimer((Plugin)this, 0L, 20L);
	    AutoLobby startLobby = new AutoLobby(this);
	    startLobby.runTaskTimer((Plugin)this, 0L, 1L);
	    getCommand("sd").setExecutor((CommandExecutor)new DefaultCMD(this));
	    getCommand("skydefender").setExecutor((CommandExecutor)new DefaultCMD(this));
	    getCommand("host").setExecutor((CommandExecutor)new AdminCMD(this));
	    getCommand("h").setExecutor((CommandExecutor)new AdminCMD(this));
	    getCommand("dev").setExecutor((CommandExecutor)new DevCMD(this));
	    setWorld();
	    MiniJeux.worldutils.saveWorld(Bukkit.getWorld("world"));
	    mjc.newGame();
	    mjc.registerToServer();
	    mjc.setLeaveRestricted(false);
	    TabGestion.actualise = false;
	    this.setScoreBoardTeams();
	    }
	
	public void onDisable() {
		System.out.println("Sky Defender is disabled");
		mjc.unregisterToServer();
	}
	
	private void setWorld() {
	    try {
	      World world = Bukkit.getWorld("world");
	      ItemStack GoldenHead = metaExtra(Material.GOLDEN_APPLE, "§bGolden Head", 1, new String[] {});
	      ShapedRecipe gHead = new ShapedRecipe(GoldenHead);
	      gHead.shape("%%%", "%*%", "%%%");
	      gHead.setIngredient('%', Material.GOLD_INGOT);
	      gHead.setIngredient('*', Material.SKULL_ITEM);
	      this.getServer().addRecipe(gHead);
	      world.setPVP(false);
	      world.setWeatherDuration(0);
	      world.setThunderDuration(0);
	      world.setTime(0L);
	      world.setGameRuleValue("reducedDebugInfo", "false");
	      world.setGameRuleValue("keepInventory", "true");
	      world.setGameRuleValue("naturalRegeneration", "false");
	      world.setGameRuleValue("randomTickSpeed", "20");
	      world.setGameRuleValue("announceAdvancements", "false");
	      world.getWorldBorder().reset();
	      int x = (int)world.getSpawnLocation().getX();
	      int z = (int)world.getSpawnLocation().getZ();
	      int y = (int)world.getSpawnLocation().getY();
	      world.setSpawnLocation(x, y, z); // 184
	      for (int i = -16; i <= 16; i++) {
	        for (int j = -16; j <= 16; j++) {
	          (new Location(world, (i + x), y-1, (j + z))).getBlock().setType(Material.BARRIER);
	          (new Location(world, (i + x), y+3, (j + z))).getBlock().setType(Material.BARRIER);
	        } 
	        (new Location(world, (i + x), y  , (z - 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+1, (z - 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+3, (z - 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y  , (z + 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+1, (z + 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (i + x), y+2, (z + 16))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x - 16), y  , (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x - 16), y+1, (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x - 16), y+2, (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x + 16), y  , (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x + 16), y+1, (i + z))).getBlock().setType(Material.BARRIER);
	        (new Location(world, (x + 16), y+2, (i + z))).getBlock().setType(Material.BARRIER);
	        	} 
	      	String WUID = world.getUID().toString();
	        setSeedLocation(WUID);
	    } catch (Exception e) {
	      Bukkit.broadcastMessage(ChatColor.RED + "Une erreur est survenue. Le Plugin SkyDefender n'a pas pu charger.");
	    } 
	  }
	
	public void joinPlayer(Player player) {
	    FastBoard fastboard = new FastBoard(player);
	    fastboard.updateTitle("§bSky §9Defender");
	    this.boards.put(player.getUniqueId(), fastboard);
	    Title.sendTabTitle(player, "§cH's §6Par§ety", " ", "§eVous jouez actuellement sur §aplay.h-party.fr", "§eNore site: §ahttps://www.h-party.fr/", "§eCe plugin a été créé par §bNamu", "§eDiscord: §ahttps://discord.gg/SHsq8gb");
	    if (isState(StateSD.LOBBY)) {

	      player.setMaxHealth(20.0D);
	      player.setHealth(20.0D);
	      player.setExp(0.0F);
	      player.setLevel(0);
	      player.getInventory().clear();
	      player.getInventory().setHelmet(null);
	      player.getInventory().setChestplate(null);
	      player.getInventory().setLeggings(null);
	      player.getInventory().setBoots(null);
	      player.teleport(player.getWorld().getSpawnLocation());
	      player.setGameMode(GameMode.ADVENTURE);
	      this.playersd.put(player.getUniqueId(), new PlayerSD());
	      this.score.addPlayerSize(); 
	      player.setScoreboard(((PlayerSD)this.playersd.get(player.getUniqueId())).getScoreBoard());
	      for (PotionEffect po : player.getActivePotionEffects())
	        player.removePotionEffect(po.getType()); 
	      player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 2147483647, 0, false, false));
	    } 
	  }
	
	public void setScoreBoardTeams() {
	    this.scoreboard.registerNewTeam(Camp.ATTAQUE.getName());
	    this.scoreboard.registerNewTeam(Camp.DEFENSE.getName());
	    this.scoreboard.registerNewTeam(Camp.RED.getName());
	    this.scoreboard.registerNewTeam(Camp.ORANGE.getName());
	    this.scoreboard.registerNewTeam(Camp.YELLOW.getName());
	    this.scoreboard.registerNewTeam(Camp.GREEN.getName());
	    this.scoreboard.registerNewTeam(Camp.AQUA.getName());
	    this.scoreboard.registerNewTeam(Camp.PINK.getName());
	    this.scoreboard.registerNewTeam(Camp.PURPLE.getName());
	    
	    this.scoreboard.getTeam(Camp.ATTAQUE.getName()).setPrefix("§7[§cAttaquant§7] ");
	    this.scoreboard.getTeam(Camp.DEFENSE.getName()).setPrefix("§7[§3Défenseur§7] ");
	    this.scoreboard.getTeam(Camp.RED.getName()).setPrefix("§7[§4Rouge§7] ");
	    this.scoreboard.getTeam(Camp.ORANGE.getName()).setPrefix("§7[§6Orange§7] ");
	    this.scoreboard.getTeam(Camp.YELLOW.getName()).setPrefix("§7[§eJaune§7] ");
	    this.scoreboard.getTeam(Camp.GREEN.getName()).setPrefix("§7[§aVert§7] ");
	    this.scoreboard.getTeam(Camp.AQUA.getName()).setPrefix("§7[§bCyan§7] ");
	    this.scoreboard.getTeam(Camp.PINK.getName()).setPrefix("§7[§dRose§7] ");
	    this.scoreboard.getTeam(Camp.PURPLE.getName()).setPrefix("§7[§5Violet§7] ");
	    
	}
	
	public void setState(StateSD state) {
		    this.state = state;
		  }
		  
	public boolean isState(StateSD state) {
		    return (this.state == state);
		  }
	
	public void setDefSpawn(Location loc) {
		this.defspawn = loc;
	}
	
	public void setDefUp(Location loc) {
		this.defup = loc;
	}
	
	public void setDefDown(Location loc) {
		this.defdown = loc;
	}
	
	public void setBanner(Location loc) {
		this.banner = loc;
	}
	
	public Location getDefSpawn() {
		return this.defspawn;
	}
	
	public Location getDefUp() {
		return this.defup;
	}
	
	public Location getDefDown() {
		return this.defdown;
	}
	
	public Location getBanner() {
		return this.banner;
	}
	
	public String getMapName() {
		return this.mapName;
	}
	
	public void setCamp(Player player, Camp camp) {
		PlayerSD psd = this.playersd.get(player.getUniqueId());
		psd.setCamp(camp);
 	    player.setCustomName("§7[" + camp.getName() + "§7] " + player.getName());
 	    player.setPlayerListName("§7[" + camp.getName() + "§7] " + player.getName());
	}
	
	public void enableSeeds() {
		new SeedSD("Médiévale", "bc70bd50-6e25-4658-a774-cd4fa4ec861e", new Location(Bukkit.getWorld("world"), -3, 209, -19), new Location(Bukkit.getWorld("world"), -22, 158, 0), new Location(Bukkit.getWorld("world"), 0, 70, 0), new Location(Bukkit.getWorld("world"), 0, 189, 0));
        new SeedSD("Observatoire", "f8f54319-5aa7-43b6-a48f-4b4de1a41db5", new Location(Bukkit.getWorld("world"), 0, 240, 0), new Location(Bukkit.getWorld("world"), 65, 197, 31), new Location(Bukkit.getWorld("world"), 66, 69, 44), new Location(Bukkit.getWorld("world"), 1, 230, 0));
	}
	
	public void setSeedLocation(String WUID) {
		System.out.println("§e[H.PARTY] §fRecherche d'une Seed...");
		SeedSD seedsd = SeedSD.seed.get(WUID);
		if(seedsd != null) {
			this.banner = seedsd.getBanner();
	        this.defdown = seedsd.getTPDown();
	        this.defup = seedsd.getTPUP();
	        this.defspawn = seedsd.getDefSpawn();
	        System.out.println("§e[H.PARTY] §fUne Map a été trouvée ! Son WUID est : " + seedsd.getWUID());
	        this.mapName = seedsd.getName();
		} else {
			System.out.println("§e[H.PARTY] §fAucune Map n'a été trouvée...");
			this.mapName = "Custom Map";
		}
		
	}
	
	  public ItemStack metaExtra(Material m, String ItemName, int Amount, String[] lore) {
		    ItemStack item = new ItemStack(m, Amount);
		    ItemMeta im = item.getItemMeta();
		    im.setDisplayName(ItemName);
		    im.setLore(Arrays.asList(lore));
		    item.setItemMeta(im);
		    return item;
		  } // metaExtra(Material, "", 1, new String[] {"", ""})
	
	public String conversion(int timer) {
	    String valeur;
	    if (timer % 60 > 9) {
	      valeur = String.valueOf(timer % 60) + "s";
	    } else {
	      valeur = "0" + (timer % 60) + "s";
	    } 
	    if (timer / 3600 > 0) {
	      if (timer % 3600 / 60 > 9) {
	        valeur = String.valueOf(timer / 3600) + "h" + (timer % 3600 / 60) + "m" + valeur;
	      } else {
	        valeur = String.valueOf(timer / 3600) + "h0" + (timer % 3600 / 60) + "m" + valeur;
	      } 
	    } else if (timer / 60 > 0) {
	      valeur = String.valueOf(timer / 60) + "m" + valeur;
	    } 
	    return valeur;
	  }
}
