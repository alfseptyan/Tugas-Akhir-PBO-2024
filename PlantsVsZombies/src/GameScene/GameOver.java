package GameScene;

import Main.GamePanel;
import Main.GameWindow;

import javax.swing.*;

public class GameOver {
    public static void trigger(GamePanel gp) {

        // Dialog Pane Saat GameOver
        int option = JOptionPane.showConfirmDialog(gp,
                "ZOMBIES ATE YOUR BRAIN!" + '\n' +
                        "End Score : " + gp.getProgress() + '\n' +
                        "Do you want to restart the level?", "Game Over", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            exitGame();
        }
    }
    private static void restartGame() {
        // Start a new GameWindow instance
        if (GameWindow.gw != null) {
            GameWindow.gw.dispose();
        }

        SwingUtilities.invokeLater(() -> {
            GameWindow.gw = new GameWindow();
            GameWindow.gw.setVisible(true);
        });
    }
    private static void exitGame() {
        // Perform any cleanup or exit actions here if needed
        System.exit(0); // Exit the application
    }
}
