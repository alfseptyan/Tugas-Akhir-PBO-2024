package Object.Enemies;

import Main.GamePanel;
import Tool.iMovement;

public class ConeHeadZombie extends Zombie implements iMovement {

    public ConeHeadZombie(GamePanel parent, int lane){
        super(parent,lane);
        health = 1800;
    }
}
