package com.voopermedia.murderpillars;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Events implements Listener {
    
    public static Main plugin;

    public Events(Main plugin){
        Events.plugin = plugin;
    }

    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent e){

        // Ensure a block is actually placed
        if (e.getBlock() == null){
            return;
        }

        Block placed = e.getBlock();

        if (placed.getType() == Material.BEDROCK){
            Caterpillar c = new Caterpillar(placed.getLocation());
            plugin.caterpillars.add(c);
        }

    }

}
