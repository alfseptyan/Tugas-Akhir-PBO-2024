// DefensePanel.java
package Tool;

import Main.GamePanel;
import javax.swing.*;
import Object.Allies.DefenseType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class DefensePanel extends JPanel {
    private DefenseCard sunflower;
    private DefenseCard peashooter;
    private DefenseCard freezepeashooter;
    private DefenseCard wallnut;
    private DefenseCard money;
    private DefenseCard shovel;
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

        shovel = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/shovel.png.png"))).getImage());
        shovel.setSize(10,90);
        shovel.setLocation(10,8);
        shovel.setOpaque(false);
        add(shovel);

        sunflower = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_sunflower.png"))).getImage());
        sunflower.setLocation(110, 8);
        sunflower.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.Sunflower;
        });
        add(sunflower);

        peashooter = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_peashooter.png"))).getImage());
        peashooter.setLocation(175, 8);
        peashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.Peashooter;
        });
        add(peashooter);

        freezepeashooter = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_freezepeashooter.png"))).getImage());
        freezepeashooter.setLocation(240, 8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.FreezePeashooter;
        });
        add(freezepeashooter);

        wallnut = new DefenseCard(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/cards/card_wallnut.png"))).getImage());
        wallnut.setLocation(305, 8);
        wallnut.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = DefenseType.Wallnut;
        });
        add(wallnut);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
