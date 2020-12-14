package com.voopermedia.murderpillars;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Caterpillar {

    public static int LENGTH = 15;
    public ArrayList<Location> segments = new ArrayList<Location>();
    public Player currentTarget;
    public Player lastTarget;

    public Caterpillar(Location start){
        for (int i = 0; i < LENGTH; i++){
            Location location = start.clone();
            segments.add(location);
        }
    }

    public void ShiftCaterpillar(int x, int y, int z){

        Location head = GetHead();

        // Get tail
        Location tail = segments.get(0);
        segments.remove(0);

        // Set to head location
        tail.setX(head.getX());
        tail.setY(head.getY());
        tail.setZ(head.getZ());

        // Move
        tail.add(x, y, z);

        // Make new head
        segments.add(tail);
    }

    public Location GetHead(){
        return segments.get(LENGTH - 1);
    }

    public Location GetTail(){
        return segments.get(0);
    }
}
