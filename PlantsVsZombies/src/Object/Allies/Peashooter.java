package Object.Allies;

import Main.GamePanel;
import Projectiles.Pea;

import java.awt.event.ActionEvent;
import javax.swing.*;
public class Peashooter extends Defense {
    public Timer shootTimer;

    public Peashooter(GamePanel parent, int x, int y) {
        super(parent,x,y);
        shootTimer = new Timer(1500,(ActionEvent e) -> {
            //System.out.println("SHOOT");
            if(gp.laneZombies.get(y).size() > 0) {
                gp.lanePeas.get(y).add(new Pea(gp, y, 103 + this.x * 100));
            }
        });
        shootTimer.start();
    }
    @Override
    public void stop(){
        shootTimer.stop();
    }

}
