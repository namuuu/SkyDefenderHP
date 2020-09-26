package fr.namu.sd.scoreboard;

import fr.namu.sd.MainSD;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class TabList {


    public TabList(MainSD main) {
    }

    public void setTabList(Player player, ChatComponentText headerText, ChatComponentText footerText) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        Object header = headerText;
        Object footer = footerText;
        try {
            Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);

            a.set(packet, header);
            b.set(packet, footer);

            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
