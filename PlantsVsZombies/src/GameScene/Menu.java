package GameScene;

import Main.GameWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;
public class Menu extends JPanel {
    Image bgImage;
    private JButton playButton;
    private JButton exitButton;
    public Menu() {
        initComponents();
        setSize(1012, 785);
//        bgImage  = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/menu.JPG"))).getImage();
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage,0,0,null);
        
    }
    private void initComponents() {
        int x = 200;
        int y = 100;
        int width = 1012;
        int height = 785;
//        setBackground(Color.BLACK);
        setPreferredSize(new java.awt.Dimension(width, height));
        setLayout(null);

        playButton = new JButton("PLAY");
        playButton.setBounds((width-x)/2, (height-y)/2, 200, 100); // Set the position and size of the button
        playButton.setFont(new Font("Tahoma", Font.BOLD, 36));
        playButton.setBackground(Color.WHITE);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow.begin(); // Start the game when the button is clicked
            }
        });
        add(playButton);

        exitButton = new JButton("EXIT");
        exitButton.setBounds((width-x)/2,(height-y)/2+110,200,100);
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 36));
        exitButton.setBackground(Color.white);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Define what happens when the button is clicked
                System.exit(0); // For example, this will close the application
            }
        });
        add(exitButton);


    }


}
