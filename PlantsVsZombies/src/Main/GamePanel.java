package Main;

import Object.Allies.*;
import Object.Enemies.ConeHeadZombie;
import Object.Enemies.NormalZombie;
import Object.Enemies.Zombie;
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
public class GamePanel extends JLayeredPane implements MouseMotionListener {

    Image bgImage;
    Image peashooterImage;
    Image freezePeashooterImage;
    Image sunflowerImage;
    Image peaImage;
    Image freezePeaImage;
    Image wallnutImage;

    Image normalZombieImage;
    Image coneHeadZombieImage;
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
    private int zombiesInCurrentWave;
    private int zombiesSpawnedInWave;


    public GamePanel(JLabel sunScoreboard){
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);

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

    private void startWave() {
        // Reset the count of zombies spawned in the wave
        zombiesSpawnedInWave = 0;

        // Calculate the number of zombies and time delay based on the wave number
        int zombiesToSpawn = calculateZombiesToSpawn(waveNumber);
        int timeDelay = calculateTimeDelay(waveNumber);

        // Set up the zombie producer timer
        zombieProducer = new Timer(timeDelay, (ActionEvent e) -> {
            spawnZombie();
            zombiesSpawnedInWave++;
            if (zombiesSpawnedInWave >= zombiesInCurrentWave) {
                // Stop the timer and prepare for the next wave
                zombieProducer.stop();
                waveNumber++;
                startWave();
            }
        });
        zombieProducer.start();
    }
    private int calculateZombiesToSpawn(int waveNumber) {
        // Define logic to calculate the number of zombies based on the wave number
        // Example: Increase zombies by 5 with each wave
        return 5 * waveNumber;
    }
    private int calculateTimeDelay(int waveNumber) {
        // Define logic to calculate the time delay based on the wave number
        // Example: Decrease delay as the wave number increases
        return Math.max(12000 - (waveNumber * 1000), 2000);
    }
    private void spawnZombie() {
        Random rnd = new Random();
        LevelData lvl = new LevelData();
        String [] Level = lvl.Level[Integer.parseInt(lvl.Lvl)-1];
        int [][] LevelValue = lvl.LevelValue[Integer.parseInt(lvl.Lvl)-1];
        int l = rnd.nextInt(5);
        int t = rnd.nextInt(100);
        Zombie z = null;
        for(int i = 0;i<LevelValue.length;i++) {
            if(t>=LevelValue[i][0]&&t<=LevelValue[i][1]) {
                z = Zombie.getZombie(Level[i],GamePanel.this,l);
            }
        }
        laneZombies.get(l).add(z);
    }


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
            add(a, new Integer(0));
        }
    }
    private void advance(){
        for (int i = 0; i < 5 ; i++) {
            for(Zombie z : laneZombies.get(i)){
                z.advance();
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }

        }

        for (int i = 0; i < activeSuns.size() ; i++) {
            activeSuns.get(i).advance();
        }

    }
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
        if(progress>=400) {
            if(LevelData.Lvl.equals("1")) {
                JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "Starting next Level");
                GameWindow.gw.dispose();
                LevelData.write("2");
                GameWindow.gw = new GameWindow();
            }  else {
                JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
                LevelData.write("1");
                System.exit(0);
            }
            progress = 0;
        }
    }
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

//        if(!"".equals(activePlantingBrush)){
//            System.out.println(activePlantingBrush);
//            if(activePlantingBrush == GameWindow.PlantType.Sunflower) {
//                g.drawImage(sunflowerImage,mouseX,mouseY,null);
//            }
//        }


    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
