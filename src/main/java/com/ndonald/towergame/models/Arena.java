package com.ndonald.towergame.models;

import java.util.ArrayList;
import java.util.Random;

public class Arena {
    private int time;

    private int numOfColumn;

    private int numOfRow;

    private boolean [][] isGreen;

    public boolean isValidArena;

    public ArrayList<BasicEnemy> enemies;
    public ArrayList <BasicTower> towers;

    public static Random rand = new Random();

    public Arena() {
        this.numOfColumn = numOfColumn;
        this.numOfRow = numOfRow;

        time = 0;

        isValidArena = true;

        enemies = new ArrayList<BasicEnemy>(0);
    }

        public void addEnemy(BasicEnemy newEnemy) {
            enemies.add(newEnemy);
        }

        public void removeEnemy(BasicEnemy enemy) {
            boolean removeSuccess =  enemies.remove(enemy);
            if(!removeSuccess)
                System.out.println("ERROR: Monster Removal");
        }
}
