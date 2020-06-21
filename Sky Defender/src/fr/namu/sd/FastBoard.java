package fr.namu.sd;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FastBoard {
  private static final VersionType VERSION_TYPE;
  
  private static final Field PLAYER_CONNECTION;
  
  private static final Method SEND_PACKET;
  
  private static final Method PLAYER_GET_HANDLE;
  
  private static final Class<?> CHAT_COMPONENT_CLASS;
  
  private static final Method MESSAGE_FROM_STRING;
  
  private static final Constructor<?> PACKET_SB_OBJ;
  
  private static final Constructor<?> PACKET_SB_DISPLAY_OBJ;
  
  private static final Constructor<?> PACKET_SB_SCORE;
  
  private static final Constructor<?> PACKET_SB_TEAM;
  
  private static final Class<?> ENUM_SB_HEALTH_DISPLAY;
  
  private static final Class<?> ENUM_SB_ACTION;
  
  private static final Object ENUM_SB_HEALTH_DISPLAY_INTEGER;
  
  private static final Object ENUM_SB_ACTION_CHANGE;
  
  private static final Object ENUM_SB_ACTION_REMOVE;
  
  private final Player player;
  
  private final String id;
  
  static {
    try {
      if (FastReflection.nmsOptionalClass("ScoreboardServer$Action").isPresent()) {
        VERSION_TYPE = VersionType.V1_13;
      } else if (FastReflection.nmsOptionalClass("IScoreboardCriteria$EnumScoreboardHealthDisplay").isPresent()) {
        VERSION_TYPE = VersionType.V1_8;
      } else {
        VERSION_TYPE = VersionType.V1_7;
      } 
      Class<?> craftChatMessageClass = FastReflection.obcClass("util.CraftChatMessage");
      Class<?> entityPlayerClass = FastReflection.nmsClass("EntityPlayer");
      Class<?> playerConnectionClass = FastReflection.nmsClass("PlayerConnection");
      Class<?> craftPlayerClass = FastReflection.obcClass("entity.CraftPlayer");
      MESSAGE_FROM_STRING = craftChatMessageClass.getDeclaredMethod("fromString", new Class[] { String.class });
      CHAT_COMPONENT_CLASS = FastReflection.nmsClass("IChatBaseComponent");
      PLAYER_GET_HANDLE = craftPlayerClass.getDeclaredMethod("getHandle", new Class[0]);
      PLAYER_CONNECTION = entityPlayerClass.getDeclaredField("playerConnection");
      SEND_PACKET = playerConnectionClass.getDeclaredMethod("sendPacket", new Class[] { FastReflection.nmsClass("Packet") });
      PACKET_SB_OBJ = FastReflection.nmsClass("PacketPlayOutScoreboardObjective").getConstructor(new Class[0]);
      PACKET_SB_DISPLAY_OBJ = FastReflection.nmsClass("PacketPlayOutScoreboardDisplayObjective").getConstructor(new Class[0]);
      PACKET_SB_SCORE = FastReflection.nmsClass("PacketPlayOutScoreboardScore").getConstructor(new Class[0]);
      PACKET_SB_TEAM = FastReflection.nmsClass("PacketPlayOutScoreboardTeam").getConstructor(new Class[0]);
      if (VersionType.V1_8.isHigherOrEqual()) {
        ENUM_SB_HEALTH_DISPLAY = FastReflection.nmsClass("IScoreboardCriteria$EnumScoreboardHealthDisplay");
        if (VersionType.V1_13.isHigherOrEqual()) {
          ENUM_SB_ACTION = FastReflection.nmsClass("ScoreboardServer$Action");
        } else {
          ENUM_SB_ACTION = FastReflection.nmsClass("PacketPlayOutScoreboardScore$EnumScoreboardAction");
        } 
        ENUM_SB_HEALTH_DISPLAY_INTEGER = FastReflection.enumValueOf(ENUM_SB_HEALTH_DISPLAY, "INTEGER");
        ENUM_SB_ACTION_CHANGE = FastReflection.enumValueOf(ENUM_SB_ACTION, "CHANGE");
        ENUM_SB_ACTION_REMOVE = FastReflection.enumValueOf(ENUM_SB_ACTION, "REMOVE");
      } else {
        ENUM_SB_HEALTH_DISPLAY = null;
        ENUM_SB_ACTION = null;
        ENUM_SB_HEALTH_DISPLAY_INTEGER = null;
        ENUM_SB_ACTION_CHANGE = null;
        ENUM_SB_ACTION_REMOVE = null;
      } 
    } catch (ReflectiveOperationException e) {
      throw new ExceptionInInitializerError(e);
    } 
  }
  
  private String title = ChatColor.RESET.toString();
  
  private List<String> lines = new ArrayList<>();
  
  private boolean deleted = false;
  
  public FastBoard(Player player) {
    this.player = Objects.<Player>requireNonNull(player, "player");
    this.id = "fb-" + Double.toString(Math.random()).substring(2, 10);
    try {
      sendObjectivePacket(ObjectiveMode.CREATE);
      sendDisplayObjectivePacket();
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void updateTitle(String title) {
    if (this.title.equals(Objects.requireNonNull(title, "title")))
      return; 
    if (!VersionType.V1_13.isHigherOrEqual() && title.length() > 32)
      throw new IllegalArgumentException("Title is longer than 32 chars"); 
    this.title = title;
    try {
      sendObjectivePacket(ObjectiveMode.UPDATE);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public List<String> getLines() {
    return new ArrayList<>(this.lines);
  }
  
  public String getLine(int line) {
    checkLineNumber(line, true);
    return this.lines.get(line);
  }
  
  public void updateLine(int line, String text) {
    checkLineNumber(line, false);
    try {
      if (line < size()) {
        this.lines.set(line, text);
        sendTeamPacket(getScoreByLine(line), TeamMode.UPDATE);
        return;
      } 
      List<String> newLines = new ArrayList<>(this.lines);
      if (line > size())
        for (int i = size(); i < line; i++)
          newLines.add("");  
      newLines.add(text);
      updateLines(newLines);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public void removeLine(int line) {
    checkLineNumber(line, false);
    if (line >= size())
      return; 
    List<String> lines = new ArrayList<>(this.lines);
    lines.remove(line);
    updateLines(lines);
  }
  
  public void updateLines(String... lines) {
    updateLines(Arrays.asList(lines));
  }
  
  public void updateLines(Collection<String> lines) {
    Objects.requireNonNull(lines, "lines");
    if (!VersionType.V1_13.isHigherOrEqual()) {
      int lineCount = 0;
      for (String s : lines) {
        if (s != null && s.length() > 30)
          throw new IllegalArgumentException("Line " + lineCount + " is longer than 30 chars"); 
        lineCount++;
      } 
    } 
    List<String> oldLines = new ArrayList<>(this.lines);
    this.lines.clear();
    this.lines.addAll(lines);
    int linesSize = this.lines.size();
    try {
      if (oldLines.size() != linesSize) {
        List<String> oldLinesCopy = new ArrayList<>(oldLines);
        if (oldLines.size() > linesSize) {
          for (int j = oldLinesCopy.size(); j > linesSize; j--) {
            sendTeamPacket(j - 1, TeamMode.REMOVE);
            sendScorePacket(j - 1, ScoreboardAction.REMOVE);
            oldLines.remove(0);
          } 
        } else {
          for (int j = oldLinesCopy.size(); j < linesSize; j++) {
            sendScorePacket(j, ScoreboardAction.CHANGE);
            sendTeamPacket(j, TeamMode.CREATE);
            oldLines.add(oldLines.size() - j, getLineByScore(j));
          } 
        } 
      } 
      for (int i = 0; i < linesSize; i++) {
        if (!Objects.equals(getLineByScore(oldLines, i), getLineByScore(i)))
          sendTeamPacket(i, TeamMode.UPDATE); 
      } 
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    } 
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public String getId() {
    return this.id;
  }
  
  public boolean isDeleted() {
    return this.deleted;
  }
  
  public int size() {
    return this.lines.size();
  }
  
  public void delete() {
    try {
      for (int i = 0; i < this.lines.size(); i++)
        sendTeamPacket(i, TeamMode.REMOVE); 
      sendObjectivePacket(ObjectiveMode.REMOVE);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    } 
    this.deleted = true;
  }
  
  private void checkLineNumber(int line, boolean checkMax) {
    if (line < 0)
      throw new IllegalArgumentException("Line number must be positive"); 
    if (checkMax && line >= this.lines.size())
      throw new IllegalArgumentException("Line number must be under " + this.lines.size()); 
  }
  
  private int getScoreByLine(int line) {
    return this.lines.size() - line - 1;
  }
  
  private String getLineByScore(int score) {
    return getLineByScore(this.lines, score);
  }
  
  private String getLineByScore(List<String> lines, int score) {
    return lines.get(lines.size() - score - 1);
  }
  
  private void sendObjectivePacket(ObjectiveMode mode) throws ReflectiveOperationException {
    Object packet = PACKET_SB_OBJ.newInstance(new Object[0]);
    setField(packet, String.class, this.id);
    setField(packet, int.class, Integer.valueOf(mode.ordinal()));
    if (mode != ObjectiveMode.REMOVE) {
      setComponentField(packet, this.title, 1);
      if (VersionType.V1_8.isHigherOrEqual())
        setField(packet, ENUM_SB_HEALTH_DISPLAY, ENUM_SB_HEALTH_DISPLAY_INTEGER); 
    } else if (VERSION_TYPE == VersionType.V1_7) {
      setField(packet, String.class, "", 1);
    } 
    sendPacket(packet);
  }
  
  private void sendDisplayObjectivePacket() throws ReflectiveOperationException {
    Object packet = PACKET_SB_DISPLAY_OBJ.newInstance(new Object[0]);
    setField(packet, int.class, Integer.valueOf(1));
    setField(packet, String.class, this.id);
    sendPacket(packet);
  }
  
  private void sendScorePacket(int score, ScoreboardAction action) throws ReflectiveOperationException {
    Object packet = PACKET_SB_SCORE.newInstance(new Object[0]);
    setField(packet, String.class, getColorCode(score), 0);
    if (VersionType.V1_8.isHigherOrEqual()) {
      setField(packet, ENUM_SB_ACTION, (action == ScoreboardAction.REMOVE) ? ENUM_SB_ACTION_REMOVE : ENUM_SB_ACTION_CHANGE);
    } else {
      setField(packet, int.class, Integer.valueOf(action.ordinal()), 1);
    } 
    if (action == ScoreboardAction.CHANGE) {
      setField(packet, String.class, this.id, 1);
      setField(packet, int.class, Integer.valueOf(score));
    } 
    sendPacket(packet);
  }
  
  private void sendTeamPacket(int score, TeamMode mode) throws ReflectiveOperationException {
    if (mode == TeamMode.ADD_PLAYERS || mode == TeamMode.REMOVE_PLAYERS)
      throw new UnsupportedOperationException(); 
    Object packet = PACKET_SB_TEAM.newInstance(new Object[0]);
    setField(packet, String.class, this.id + ':' + score);
    setField(packet, int.class, Integer.valueOf(mode.ordinal()), (VERSION_TYPE == VersionType.V1_8) ? 1 : 0);
    if (mode == TeamMode.CREATE || mode == TeamMode.UPDATE) {
      String prefix, line = getLineByScore(score);
      String suffix = null;
      if (line == null || line.isEmpty()) {
        prefix = getColorCode(score) + ChatColor.RESET;
      } else if (line.length() <= 16 || VersionType.V1_13.isHigherOrEqual()) {
        prefix = line;
      } else {
        int index = (line.charAt(15) == '§') ? 15 : 16;
        prefix = line.substring(0, index);
        String suffixTmp = line.substring(index);
        ChatColor chatColor = null;
        if (suffixTmp.length() >= 2 && suffixTmp.charAt(0) == '§')
          chatColor = ChatColor.getByChar(suffixTmp.charAt(1)); 
        String color = ChatColor.getLastColors(prefix);
        boolean addColor = (chatColor == null || chatColor.isFormat());
        suffix = (addColor ? (color.isEmpty() ? ChatColor.RESET : color) : "") + suffixTmp;
      } 
      if (VERSION_TYPE != VersionType.V1_13 && (
        prefix.length() > 16 || (suffix != null && suffix.length() > 16))) {
        prefix = prefix.substring(0, 16);
        suffix = (suffix != null) ? suffix.substring(0, 16) : null;
      } 
      setComponentField(packet, prefix, 2);
      setComponentField(packet, (suffix == null) ? "" : suffix, 3);
      setField(packet, String.class, "always", 4);
      setField(packet, String.class, "always", 5);
      if (mode == TeamMode.CREATE)
        setField(packet, Collection.class, Collections.singletonList(getColorCode(score))); 
    } 
    sendPacket(packet);
  }
  
  private String getColorCode(int score) {
    return ChatColor.values()[score].toString();
  }
  
  private void sendPacket(Object packet) throws ReflectiveOperationException {
    if (this.deleted)
      throw new IllegalStateException("This FastBoard is deleted"); 
    if (this.player.isOnline()) {
      Object entityPlayer = PLAYER_GET_HANDLE.invoke(this.player, new Object[0]);
      Object playerConnection = PLAYER_CONNECTION.get(entityPlayer);
      SEND_PACKET.invoke(playerConnection, new Object[] { packet });
    } 
  }
  
  private void setField(Object object, Class<?> fieldType, Object value) throws ReflectiveOperationException {
    setField(object, fieldType, value, 0);
  }
  
  private void setField(Object object, Class<?> fieldType, Object value, int count) throws ReflectiveOperationException {
    int i = 0;
    for (Field f : object.getClass().getDeclaredFields()) {
      if (f.getType() == fieldType && i++ == count) {
        f.setAccessible(true);
        f.set(object, value);
      } 
    } 
  }
  
  private void setComponentField(Object object, String value, int count) throws ReflectiveOperationException {
    if (VERSION_TYPE != VersionType.V1_13) {
      setField(object, String.class, value, count);
      return;
    } 
    int i = 0;
    for (Field f : object.getClass().getDeclaredFields()) {
      if ((f.getType() == String.class || f.getType() == CHAT_COMPONENT_CLASS) && i++ == count) {
        f.setAccessible(true);
        f.set(object, Array.get(MESSAGE_FROM_STRING.invoke(null, new Object[] { value }), 0));
      } 
    } 
  }
  
  enum ObjectiveMode {
    CREATE, REMOVE, UPDATE;
  }
  
  enum TeamMode {
    CREATE, REMOVE, UPDATE, ADD_PLAYERS, REMOVE_PLAYERS;
  }
  
  enum ScoreboardAction {
    CHANGE, REMOVE;
  }
  
  enum VersionType {
    V1_7, V1_8, V1_13;
    
    public boolean isHigherOrEqual() {
      return (FastBoard.VERSION_TYPE.ordinal() >= ordinal());
    }
  }
}

