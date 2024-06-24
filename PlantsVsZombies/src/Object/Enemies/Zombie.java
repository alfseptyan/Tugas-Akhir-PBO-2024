package Object.Enemies;

import GameScene.GameOver;
import Main.GamePanel;
import Tool.*;

public class Zombie implements iMovement{

    public int health = 1000;
    int slowInt = 0;
    private GamePanel gp;
    public int posX = 1000;
    public int myLane;
    public boolean isMoving = true;

    public Zombie(GamePanel parent,int lane){
        this.gp = parent;
        myLane = lane;
    }

    public void advance(){
        if(isMoving) {
            boolean isCollides = false;
            Collider collided = null;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.colliders[i].assignedDefense != null && gp.colliders[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.colliders[i];
                }
            }
            if (!isCollides) {
                if(slowInt>0){
                    if(slowInt % 2 == 0) {
                        posX--;
                    }
                    slowInt--;
                }else {
                    posX -= 1;
                }
            } else {
                collided.assignedDefense.health -= 10;
                if (collided.assignedDefense.health < 0) {
                    collided.removeDefense();
                }
            }
            if (posX < 0) {
                isMoving = false;
                GameOver.trigger(gp);
            }
        }
    }
    public void stop() {
        isMoving = false;
    }

    @Override
    public void start() {
        isMoving = true;
    }

    public void slow(){
        slowInt = 1000;
    }
    public static Zombie getZombie(String type,GamePanel parent, int lane) {
        Zombie z = new Zombie(parent,lane);
       switch(type) {
           case "Object.Enemies.NormalZombie" : z = new NormalZombie(parent,lane);
                                 break;
           case "Object.Enemies.ConeHeadZombie" : z = new ConeHeadZombie(parent,lane);
                                 break;
    }
       return z;
    }

}
