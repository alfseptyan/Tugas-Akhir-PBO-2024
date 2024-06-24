package Object.Enemies;

import Main.GamePanel;
import Tool.iMovement;

public class NormalZombie extends Zombie implements iMovement {

    public NormalZombie(GamePanel parent, int lane){
        super(parent,lane);
    }
}
