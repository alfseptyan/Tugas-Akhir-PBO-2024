package Main;

import javax.swing.*;

import Projectiles.Pea;
import Tool.*;
import GameScene.*;

public class GameWindow extends JFrame {
    public static GameWindow gw;
    public GameWindow(){
        setSize(1012,820);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("Sun");
        sun.setLocation(37,750);
        sun.setSize(60,20);

        JLabel scoreLabel=new JLabel("Score:");
        scoreLabel.setLocation(750, 680);
        scoreLabel.setSize(200, 20);
        getLayeredPane().add(scoreLabel, new Integer(2));

        JLabel zombieDefeatedLabel = new JLabel("Score : ");
        zombieDefeatedLabel.setLocation(800, 680);
        zombieDefeatedLabel.setSize(200, 20);
        getLayeredPane().add(zombieDefeatedLabel, new Integer(2));

        JLabel waveNumberLabel = new JLabel("Wave: 1");
        waveNumberLabel.setLocation(900, 680);
        waveNumberLabel.setSize(200, 20);
        getLayeredPane().add(waveNumberLabel, new Integer(2));

        GamePanel gp = new GamePanel(sun,zombieDefeatedLabel,waveNumberLabel);
        gp.setLocation(0,0);
        getLayeredPane().add(gp,new Integer(0));
        DefensePanel defensePanel = new DefensePanel(gp);
        defensePanel.setBounds(0, 675, 1012, 120);
        getLayeredPane().add(defensePanel, new Integer(2));




        getLayeredPane().add(sun,new Integer(3));
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
    public GameWindow(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0,0);
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu,new Integer(0));
        menu.repaint();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void begin() {
        gw.dispose();
       gw = new GameWindow();

        Backsound player = new Backsound("PlantsVsZombies/src/music/song.wav");
        player.play();

    }


    public static void main(String[] args) {
          gw = new GameWindow(true);
          Backsound menu = new Backsound("PlantsVsZombies/src/music/02. Crazy Dave (Intro Theme).wav");
          menu.play();
    }
}
