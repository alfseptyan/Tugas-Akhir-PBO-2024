package Object.Enemies;

import Main.GamePanel;

public class ConeHeadZombie extends Zombie implements iProgress {

    public ConeHeadZombie(GamePanel parent, int lane){
        super(parent,lane);
        health = 1800;
    }
    public void changeHealth(){
        if (GamePanel.getProgress() >= 500){
            this.health = 2300;
        } else if (GamePanel.getProgress() >= 1000) {
            this.health = 4000;
        }
    }
}
