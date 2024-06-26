// DefensePanel.java
package Tool;

import Main.GamePanel;
import javax.swing.*;
import Object.Allies.DefenseType;
import Object.Enemies.Zombie;
import Projectiles.Pea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class DefensePanel extends JPanel {
    private DefenseCard sunflower;
    private DefenseCard peashooter;
    private DefenseCard freezepeashooter;
    private DefenseCard wallnut;
    private DefenseCard money;
    private JButton pauseButton;
    private JButton resumeButton;
    private JLabel sun;
    public DefensePanel(GamePanel gp) {
        setLayout(null);
        setOpaque(false);

        sun = new JLabel("");
        sun.setLocation(37,10);
        sun.setSize(60,20);
        sun.setForeground(Color.YELLOW);
        add(sun);

        money = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/sun.png"))).getImage());
        money.setSize(100,90);
        money.setLocation(8,8);
        money.setOpaque(false);
        add(money);

        sunflower = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_sunflower.png"))).getImage());
        sunflower.setLocation(110, 8);
        sunflower.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.Sunflower;
            gp.setActivePlantImage(gp.getSunflowerImage());
        });
        add(sunflower);

        peashooter = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_peashooter.png"))).getImage());
        peashooter.setLocation(175, 8);
        peashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.Peashooter;
            gp.setActivePlantImage(gp.getPeashooterImage());
        });
        add(peashooter);

        freezepeashooter = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_freezepeashooter.png"))).getImage());
        freezepeashooter.setLocation(240, 8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.FreezePeashooter;
            gp.setActivePlantImage(gp.getFreezePeashooterImage());
        });
        add(freezepeashooter);

        wallnut = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_wallnut.png"))).getImage());
        wallnut.setLocation(305, 8);
        wallnut.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.Wallnut;
            gp.setActivePlantImage(gp.getWallnutImage());
        });
        add(wallnut);

//         Add pause button
        pauseButton = new JButton("Pause");
        pauseButton.setLocation(820, 70);
        pauseButton.setSize(80, 30);
        pauseButton.addActionListener((ActionEvent e) -> {
            gp.setPaused(true);
            gp.stopAllTimers();
            gp.stop();
        });
        add(pauseButton);

        resumeButton = new JButton("Resume");
        resumeButton.setLocation(900, 70);
        resumeButton.setSize(80, 30);
        resumeButton.addActionListener((ActionEvent e) -> {
            gp.setPaused(false);
            gp.startAllTimers();
            gp.startMovements();
        });
        add(resumeButton);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
