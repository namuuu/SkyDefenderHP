package fr.namu.sd;

import fr.namu.sd.command.HostCMD;
import fr.namu.sd.listener.*;
import fr.namu.sd.menu.*;
import fr.namu.sd.scoreboard.FastBoard;
import fr.namu.sd.scoreboard.ScoreBoard;
import fr.namu.sd.scoreboard.TabList;
import fr.namu.sd.util.*;
import fr.namu.sd.util.players.SpecUtil;
import fr.namu.sd.util.players.TeamUtil;
import fr.redline.psystem.util.TabGestion;
import fr.redline.serverclient.liaison.StartSC;
import fr.redline.serverclient.minijeux.MiniJeux;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainSD extends JavaPlugin {

    public final Map<UUID, PlayerSD> playersd = new HashMap<>();
    public final Map<UUID, FastBoard> boards = new HashMap<>();

    public final WinUtil win = new WinUtil(this);
    public final TimerUtil timer = new TimerUtil(this);
    public final TeamUtil team = new TeamUtil(this);
    public final ItemUtil item = new ItemUtil(this);
    public final ScoreBoard score = new ScoreBoard(this);
    public final TabList tab = new TabList(this);
    public final InfoSD info = new InfoSD(this);
    public final SpecUtil spec = new SpecUtil(this);
    public final SetupUtil setup = new SetupUtil(this);
    public final StartUtil start = new StartUtil(this);
    public final TeleportUtil teleport = new TeleportUtil(this);

    public final PreyUtil prey = new PreyUtil(this);

    public final BorderMenu BorderMenu = new BorderMenu(this);
    public final HostMenu HostMenu = new HostMenu(this);
    public final PlayerMenu PlayerMenu = new PlayerMenu(this);
    public final TeamMenu TeamMenu = new TeamMenu(this);
    public final TimerMenu TimerMenu = new TimerMenu(this);
    public final StuffMenu StuffMenu = new StuffMenu(this);
    public final ScenarioMenu ScenarioMenu = new ScenarioMenu(this);

    public MiniJeux mjc = null;

    @Override
    public void onEnable() {
        this.info.setInfo();
        this.setup.setupWorld();

        StartSC.getInstance().wu.saveWorld(info.world);

        this.mjc = StartSC.createMiniJeux("SkyDefender", "Party", this.info.getMapName(), this.info.getPlayersMax(), Boolean.TRUE, Boolean.FALSE);
        mjc.newGame();
        mjc.registerToServer();
        mjc.setLeaveRestricted(false);

        TabGestion.actualise = false;

        enableListeners();
        enableCommands();
    }

    @Override
    public void onDisable() {
        mjc.leaveAllPlayer();
        mjc.unregisterToServer();
    }

    public void enableListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BreakEvent(this), this);
        pm.registerEvents(new DeathEvent(this), this);
        pm.registerEvents(new InteractEvent(this), this);
        pm.registerEvents(new JoinLeaveEvent(this), this);
        pm.registerEvents(new ClickEvent(this), this);
        pm.registerEvents(new SneakEvent(this), this);
        pm.registerEvents(new DamageEvent(this), this);
        pm.registerEvents(new PlaceEvent(this), this);
        pm.registerEvents(new DropEvent(this), this);
        pm.registerEvents(new ItemSpawnEvent(this), this);
        pm.registerEvents(new ChatEvent(this), this);
        pm.registerEvents(new FurnaceEvent(this), this);
    }

    public void enableCommands() {
        getCommand("host").setExecutor(new HostCMD(this));
    }
}
