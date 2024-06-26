package Object.Allies;

import Main.GamePanel;
import Projectiles.Sun;
import Tool.iMovement;

import javax.swing.*;
import java.awt.event.ActionEvent;
public class Sunflower extends Defense implements iMovement{
    private static final int SUN_PRODUCTION_INTERVAL = 12000; // 12 seconds
    private long lastSunProductionTime;
    private boolean isPaused;
    private long pauseStartTime;
    public Sunflower(GamePanel parent, int x, int y) {
        super(parent, x, y);
        if (health > 0) {
            lastSunProductionTime = System.currentTimeMillis();
            isPaused = false;
        }
    }
    public void produceSun() {
        if (!isPaused && (System.currentTimeMillis() - lastSunProductionTime >= SUN_PRODUCTION_INTERVAL)) {
            Sun sta = new Sun(gp, 60 + getX() * 100, 110 + getY() * 120, 130 + getY() * 120);
            gp.activeSuns.add(sta);
            gp.add(sta, new Integer(1));
            lastSunProductionTime = System.currentTimeMillis();
        }
    }
    @Override
    public void stop() {
        if (!isPaused) {
            pauseStartTime = System.currentTimeMillis();
            isPaused = true;
        }
    }
    @Override
    public void start() {
        if (isPaused) {
            long pauseEndTime = System.currentTimeMillis();
            long pauseDuration = pauseEndTime - pauseStartTime;
            lastSunProductionTime += pauseDuration; // Adjust the last sun production time
            isPaused = false;
        }
    }
    @Override
    public void advance() {
        produceSun();
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
