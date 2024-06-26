package Main;

import Object.Allies.*;
import Object.Enemies.ConeHeadZombie;
import Object.Enemies.NormalZombie;
import Object.Enemies.Zombie;
import Tool.iMovement;
import Projectiles.FreezePea;
import Projectiles.Pea;
import Projectiles.Sun;
import Tool.Collider;
import Tool.PlantActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
public class GamePanel extends JLayeredPane implements MouseMotionListener, iMovement {

    protected static JLabel waveNumberLabel;
    protected static JLabel zombieDefeatedLabel;
    private Image bgImage;
    private Image peashooterImage;
    private Image freezePeashooterImage;
    private Image sunflowerImage;
    private Image peaImage;
    private Image freezePeaImage;
    private Image wallnutImage;

    private Image normalZombieImage;
    private Image coneHeadZombieImage;
    public Collider[] colliders;

    public ArrayList<ArrayList<Zombie>> laneZombies;
    public ArrayList<ArrayList<Pea>> lanePeas;
    public ArrayList<Sun> activeSuns;

    Timer redrawTimer;
    Timer advancerTimer;
    Timer sunProducer;
    Timer zombieProducer;
    JLabel sunScoreboard;
    public DefenseType activePlantingBrush = DefenseType.None;
    int mouseX , mouseY;
    private int sunScore;
    static int progress = 0;
    private int waveNumber = 1;
    private final int zombiesInCurrentWave = waveNumber * 2;
    private int zombiesSpawnedInWave;
    private boolean isPaused = false;
    private Image activePlantImage;
    public GamePanel(JLabel sunScoreboard, JLabel zombieDefeatedLabel, JLabel waveNumberLabel){
        this.sunScoreboard = sunScoreboard;
        GamePanel.zombieDefeatedLabel = zombieDefeatedLabel;
        GamePanel.waveNumberLabel =waveNumberLabel;
        setSunScore(150);
        setProgress(0);
        addMouseMotionListener(this);

        initUI();
        loadImages();
        initializeLanes();
        initializeColliders();
        startWave();

        activeSuns = new ArrayList<>();
        // Timer
        redrawTimer = new Timer(25,(ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60,(ActionEvent e) -> advance());
        advancerTimer.start();

        //Producer
        sunProducer = new Timer(10000,(ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this,rnd.nextInt(800)+100,0,rnd.nextInt(300)+200);
            activeSuns.add(sta);
            add(sta,new Integer(1));
        });
        sunProducer.start();
    }

    // Screen and Image Rendering
    private void initUI(){
        setSize(1000,752);
        setLayout(null);
        addMouseMotionListener(this);
    }
    private void loadImages(){
        bgImage  = new ImageIcon(this.getClass().getResource("/images/map.png")).getImage();
        peashooterImage = new ImageIcon(this.getClass().getResource("/images/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getResource("/images/plants/freezepeashooter.gif")).getImage();
        sunflowerImage = new ImageIcon(this.getClass().getResource("/images/plants/sunflower.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getResource("/images/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getResource("/images/freezepea.png")).getImage();
        wallnutImage = new ImageIcon(this.getClass().getResource("/images/plants/wallnut.png")).getImage();
        normalZombieImage = new ImageIcon(this.getClass().getResource("/images/zombies/zombie1.png")).getImage();
        coneHeadZombieImage = new ImageIcon(this.getClass().getResource("/images/zombies/zombie2.png")).getImage();
    }

    // Zombies Spawn Logic and Progression
    public static int getProgress() {
        return progress;
    }
    private void startWave() {
        // Reset the count of zombies spawned in the wave
        zombiesSpawnedInWave = 0;

        // Calculate the base delay and wave delay
        int baseDelay = calculateTimeDelay(waveNumber);
        int waveDelay = calculateTotalDelay(waveNumber);

        // Spawn zombies individually with a base delay
        zombieProducer = new Timer(baseDelay, (ActionEvent e) -> {
            spawnZombie();
            zombiesSpawnedInWave++;
            if (zombiesSpawnedInWave >= zombiesInCurrentWave) {
                // Stop the timer and prepare for the next wave
                zombieProducer.stop();
                waveNumber++;
                waveNumberLabel.setText(String.valueOf(waveNumber));
                startWave();
            }
        });
        zombieProducer.start();

        // Spawn all zombies in a wave after the wave delay
        if (getProgress() % 100 == 0) {
            int extraWaves = getProgress() / 100;
            new Timer(waveDelay, (ActionEvent e) -> {
                spawnAllZombiesInWave(extraWaves);
            }).start();
        }
    }
    private int calculateTotalDelay(int waveNumber) {
        // Define logic to calculate the total delay based on the wave number
        // Example: Increase delay as the wave number increases
        return Math.max(60000 - (waveNumber * 500), 2000); // Adjust delay as needed
    }
    private int calculateTimeDelay(int waveNumber) {
        // Define logic to calculate the time delay based on the wave number
        return Math.max(12000 - (waveNumber * 1000), 1000);
    }
    private void spawnZombie() {
        Random rnd = new Random();
        LevelData lvl = new LevelData();
        int l = rnd.nextInt(5);
        int t = rnd.nextInt(100);

        Zombie z = null;
        for (int i = 0; i < LevelData.SpawnProbability.length; i++) {
            if (t >= LevelData.SpawnProbability[i][0] && t <= LevelData.SpawnProbability[i][1]) {
                z = Zombie.getZombie(LevelData.ZombieTypes[i], GamePanel.this, l);
            }
        }
        if (z != null) {
            laneZombies.get(l).add(z);
        }
    }
    private int calculateZombiesToSpawn(int zombieType, int waveNumber) {
        return waveNumber * LevelData.SpawnProbability[zombieType][0];
    }
    private void spawnAllZombiesInWave(int extraWave) {
        Random rnd = new Random();
        LevelData lvl = new LevelData();

        for (int i = 0; i < LevelData.SpawnProbability.length; i++) {
            for (int wave = 0; wave < extraWave; wave++) {
                int numZombies = calculateZombiesToSpawn(i, waveNumber);
                for (int j = 0; j < numZombies; j++) {
                    int l = rnd.nextInt(5); // Assuming 5 lanes
                    Zombie z = Zombie.getZombie(LevelData.ZombieTypes[i], GamePanel.this, l);
                    if (z != null) {
                        laneZombies.get(l).add(z);
                    }
                }
            }
        }
    }
    private void initializeLanes() {
        laneZombies = new ArrayList<>(5);
        lanePeas = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            laneZombies.add(new ArrayList<>());
            lanePeas.add(new ArrayList<>());
        }
    }
    private void initializeColliders() {
        colliders = new Collider[45];
        for (int i = 0; i < colliders.length; i++) {
            Collider a = new Collider();
            a.setLocation(3 + (i % 9) * 115, 269 + (i / 9) * 80);
            a.setAction(new PlantActionListener(this, (i % 9), (i / 9)));
            colliders[i] = a;
            add(a);
        }
    }

    //    Entities Movement and Time Control
    public void advance() {
        if (isPaused) return;
        // Loop through each lane (0 to 4)
        for (int i = 0; i < 5; i++) {
            // Advance each zombie in the current lane
            for (Zombie z : laneZombies.get(i)) {
                z.advance();
            }

            // Advance each pea in the current lane
            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }
        }
        // Advance each active sun
        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).advance();
        }
        // Advance each sunflower to potentially produce suns
        for (int i = 0; i < 45; i++) {
            if (colliders[i].assignedDefense instanceof Sunflower) {
                ((Sunflower) colliders[i].assignedDefense).advance();
            }
        }
    }
    @Override
    public void stop() {
        // Loop through each lane (0 to 4)
        for (int i = 0; i < 5; i++) {
            // Advance each zombie in the current lane
            for (Zombie z : laneZombies.get(i)) {
                z.stop();
            }

            // Advance each pea in the current lane
            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.stop();
            }
        }
        // Advance each active sun
        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).stop();
        }
        // Sunflower
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if (c.assignedDefense instanceof Sunflower) {
                c.assignedDefense.stop();
            }
        }
    }
    @Override
    public void start() {
    }
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
    public void stopAllTimers() {
        redrawTimer.stop();
        advancerTimer.stop();
        sunProducer.stop();
        if (zombieProducer != null) {
            zombieProducer.stop();
        }
    }
    public void startAllTimers() {
        redrawTimer.start();
        advancerTimer.start();
        sunProducer.start();
        if (zombieProducer != null) {
            zombieProducer.start();
        }
    }
    public void startMovements() {
        // Start each lane (0 to 4)
        for (int i = 0; i < 5; i++) {
            // Start each zombie in the current lane
            for (Zombie z : laneZombies.get(i)) {
                z.start();
            }

            // Start each pea in the current lane
            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.start();
            }
        }

        // Start each active sun
        for (int i = 0; i < activeSuns.size(); i++) {
            activeSuns.get(i).start();
        }
        // Sunflower
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if (c.assignedDefense instanceof Sunflower) {
                ((Sunflower) c.assignedDefense).start();
            }
        }
    }

    //    Getter and Setter part
    public int getSunScore() {
        return sunScore;
    }
    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
    }
    public static void setProgress(int num) {
        progress = progress + num;
        System.out.println(progress);
        if (progress > 200){
            LevelData.changeSpawnProbability();
        } else if (progress > 500) {
            LevelData.changeSpawnProbability();
        }
        zombieDefeatedLabel.setText(String.valueOf(progress));
    }
    public Image getSunflowerImage() {
        return sunflowerImage;
    }
    public void setActivePlantImage(Image image) {
        this.activePlantImage = image;
    }
    public Image getPeashooterImage() {
        return peashooterImage;
    }
    public Image getFreezePeashooterImage() {
        return freezePeashooterImage;
    }
    public Image getWallnutImage() {
        return wallnutImage;
    }

    //    Rendering and Mouse Listening
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage,0,0,1000,752,null);

        //Draw Plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if(c.assignedDefense != null){
                Defense p = c.assignedDefense;
                if(p instanceof Peashooter){
                    g.drawImage(peashooterImage,20 + (i%9)*115,269 + (i/9)*80,null);
                }
                if(p instanceof FreezePeashooter){
                    g.drawImage(freezePeashooterImage,20 + (i%9)*115,269 + (i/9)*80,null);
                }
                if(p instanceof Sunflower){
                    g.drawImage(sunflowerImage,20 + (i%9)*115,269 + (i/9)*80,null);
                }
                if (p instanceof Walnut){
                    g.drawImage(wallnutImage,20 + (i%9)*115,269 + (i/9)*80,null);
                }
            }
        }

        for (int i = 0; i < 5 ; i++) {
            for(Zombie z : laneZombies.get(i)){
                if(z instanceof NormalZombie){
                    g.drawImage(normalZombieImage,z.posX,249+(i*80),null);
                }else if(z instanceof ConeHeadZombie){
                    g.drawImage(coneHeadZombieImage,z.posX,249+(i*80),null);
                }
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                if(p instanceof FreezePea){
                    g.drawImage(freezePeaImage, p.posX, 269 + (i * 80), null);
                }else {
                    g.drawImage(peaImage, p.posX, 269 + (i * 80), null);
                }
            }


        }
        // Top Left Active Plant
        if (activePlantImage != null) {
            int x = 5;
            int y = 10;
            g.drawImage(activePlantImage, x, y, null);
        }

        // Follow Mouse Active Plant
//        if (activePlantImage != null) {
//            int x = mouseX - activePlantImage.getWidth(null) / 2;
//            int y = mouseY - activePlantImage.getHeight(null) / 2;
//            g.drawImage(activePlantImage, x, y, null);
//        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        repaint();
    }
}
