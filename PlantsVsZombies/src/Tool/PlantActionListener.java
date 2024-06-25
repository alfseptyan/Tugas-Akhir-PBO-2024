package Tool;

import Main.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Object.Allies.*;

public class PlantActionListener implements ActionListener {
    private GamePanel gp;
    int x,y;

    public PlantActionListener(GamePanel gp, int x, int y){
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    public void actionPerformed(ActionEvent e) {
        if(gp.activePlantingBrush == DefenseType.Sunflower){
            if(gp.getSunScore()>=50) {
                gp.colliders[x + y * 9].setDefense(new Sunflower(gp, x, y));
                gp.setSunScore(gp.getSunScore()-50);
            }
        }
        if(gp.activePlantingBrush == DefenseType.Peashooter){
            if(gp.getSunScore() >= 100) {
                gp.colliders[x + y * 9].setDefense(new Peashooter(gp, x, y));
                gp.setSunScore(gp.getSunScore()-100);
            }
        }

        if(gp.activePlantingBrush == DefenseType.FreezePeashooter){
            if(gp.getSunScore() >= 175) {
                gp.colliders[x + y * 9].setDefense(new FreezePeashooter(gp, x, y));
                gp.setSunScore(gp.getSunScore()-175);
            }
        }
        if(gp.activePlantingBrush == DefenseType.Wallnut){
            if(gp.getSunScore() >= 50) {
                gp.colliders[x + y * 9].setDefense(new Walnut(gp, x, y));
                gp.setSunScore(gp.getSunScore()-50);
            }
        }
        gp.activePlantingBrush = DefenseType.None;
        gp.setActivePlantImage(null);
    }
}
