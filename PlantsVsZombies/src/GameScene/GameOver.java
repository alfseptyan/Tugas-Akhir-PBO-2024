package GameScene;

import Main.GamePanel;
import Main.GameWindow;

import javax.swing.*;

public class GameOver {
    public static void trigger(GamePanel gp) {
        JOptionPane.showMessageDialog(gp, "ZOMBIES ATE YOUR BRAIN!" + '\n' +"End Score : "+ GamePanel.getProgress() + '\n' +"Starting the level again");
        GameWindow.gw.dispose();
        GameWindow.gw = new GameWindow();
    }
}
