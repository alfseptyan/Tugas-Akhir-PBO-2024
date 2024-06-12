package Main;

import javax.swing.*;
import Tool.*;
import GameScene.*;

public class GameWindow extends JFrame {
    public static GameWindow gw;
    public GameWindow(){
        setSize(1012,885);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel();
        sun.setLocation(37,800);
        sun.setSize(60,20);

        GamePanel gp = new GamePanel(sun);
        gp.setLocation(0,0);
        getLayeredPane().add(gp,new Integer(0));

        DefensePanel defensePanel = new DefensePanel(gp);
        defensePanel.setBounds(0, 720, 1012, 120);
        getLayeredPane().add(defensePanel, new Integer(2));

        getLayeredPane().add(sun,new Integer(3));
        setLocationRelativeTo(null);
        setResizable(false);
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

    }
}
