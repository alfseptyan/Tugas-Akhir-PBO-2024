package Object.Allies;

import Main.GamePanel;
public abstract class Defense {
    public int health = 200;
    public int x;
    public int y;
    public GamePanel gp;
    public Defense(GamePanel parent, int x, int y){
        this.x = x;
        this.y = y;
        gp = parent;
    }
    public void advance(){}
    public void start(){}
    public void stop(){}

}
