package Object.Allies;

import Main.GamePanel;
import Object.Allies.Defense;
import Projectiles.FreezePea;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class FreezePeashooter extends Defense {

    public Timer shootTimer;


    public FreezePeashooter(GamePanel parent, int x, int y) {
        super(parent,x,y);
        shootTimer = new Timer(2000,(ActionEvent e) -> {
            //System.out.println("SHOOT");
            if(gp.laneZombies.get(y).size() > 0) {
                gp.lanePeas.get(y).add(new FreezePea(gp, y, 103 + this.x * 100));
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop(){
        shootTimer.stop();
    }

}