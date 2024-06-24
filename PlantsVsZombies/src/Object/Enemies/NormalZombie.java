package Object.Enemies;

import Main.GamePanel;

public class NormalZombie extends Zombie implements iProgress{

    public NormalZombie(GamePanel parent, int lane){
        super(parent,lane);
    }

    @Override
    public void changeHealth(){
        if (GamePanel.getProgress() >= 500){
            this.health = 1500;
        } else if (GamePanel.getProgress() >= 1000) {
            this.health = 3000;
        }
    }
}
