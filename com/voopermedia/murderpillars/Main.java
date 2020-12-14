package com.voopermedia.murderpillars;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import org.bukkit.*;

public class Main extends JavaPlugin {

    public static JavaPlugin plugin;

    public ArrayList<Caterpillar> caterpillars = new ArrayList<Caterpillar>();

    @Override
    public void onEnable() {

        // Show welcome message
        WelcomeMessage();

        RegisterEvents();

        BukkitRunnable runnable = new BukkitRunnable(){
            @Override
            public void run() {
                MoveCaterpillars();
            }
        };
        runnable.runTaskTimer(this, 0L, 5L);
    }

    // Task to be repeated
    public void MoveCaterpillars(){
        for (Caterpillar c : caterpillars){

            Location head = c.GetHead();
            Location tail = c.GetTail();

            tail.getWorld().getBlockAt(tail).setType(Material.AIR);

            Player closest = null;
            double distance = Double.MAX_VALUE;
            Location bpl = null;

            for (Player p : Bukkit.getOnlinePlayers()){
                if (p.getWorld() == head.getWorld() && p.getGameMode() == GameMode.SURVIVAL){
                    Location pl = p.getLocation();
                    
                    double d = pl.distanceSquared(head);

                    if (d < distance){
                        distance = d;
                        closest = p;
                        bpl = pl;
                        c.currentTarget = p;
                    }
                }
            }


            if (c.currentTarget != null && c.currentTarget != c.lastTarget){
                if (c.lastTarget != null){
                    c.lastTarget.sendMessage(ChatColor.GREEN + "You are no longer being hunted :)");
                }

                c.currentTarget.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "YOU ARE BEING HUNTED", "You should run.", 10, 40, 10);
                c.currentTarget.playSound(c.currentTarget.getLocation(), Sound.AMBIENT_CAVE, 10, 1);

                c.lastTarget = c.currentTarget;
            }

            if (bpl != null){
                int xa = 0, ya = 0, za = 0;

                if (bpl.getBlockX() > head.getBlockX()){
                    xa = 1;
                }
                else if (bpl.getBlockX() < head.getBlockX()){
                    xa = -1;
                }

                if (bpl.getBlockY() > head.getBlockY()){
                    ya = 1;
                }
                else if (bpl.getBlockY() < head.getBlockY()){
                    ya = -1;
                }

                if (bpl.getBlockZ() > head.getBlockZ()){
                    za = 1;
                }
                else if (bpl.getBlockZ() < head.getBlockZ()){
                    za = -1;
                }

                c.ShiftCaterpillar(xa, ya, za);

                Location newHead = c.GetHead();

                newHead.getWorld().getBlockAt(newHead).setType(Material.LAVA);
            }
        }
    }

    // Registers event listeners
    public void RegisterEvents() {
        Events events = new Events(this);
        Bukkit.getPluginManager().registerEvents(events, this);
    }

    public static void LogWarning(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[Murderpillars] " + ChatColor.RED + message);
    }

    public static void Log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[Murderpillars] " + ChatColor.AQUA + message);
    }

    public void WelcomeMessage() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Welcome to Murderpillars: Prepare to die!");
    }

    @Override
    public void onDisable() {

    }

    public static String BoolColor(boolean value) {
        if (value)
            return ChatColor.GREEN.toString();
        else
            return ChatColor.RED.toString();
    }

    public static float Round(float n) {
        return (Math.round(n * 100.0f) / 100.0f);
    }

}
