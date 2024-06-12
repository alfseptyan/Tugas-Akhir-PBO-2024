package Object.Allies;

import Main.GamePanel;
import Projectiles.Sun;

import javax.swing.*;
import java.awt.event.ActionEvent;
public class Sunflower extends Defense {

    Timer sunProduceTimer;

    public Sunflower(GamePanel parent, int x, int y) {
        super(parent, x, y);
        if (health>0){
            sunProduceTimer = new Timer(12000,(ActionEvent e) -> {
                Sun sta = new Sun(gp,60 + x*100,110 + y*120,130 + y*120);
                gp.activeSuns.add(sta);
                gp.add(sta,new Integer(1));
            });
            sunProduceTimer.start();
        }
    }
}
