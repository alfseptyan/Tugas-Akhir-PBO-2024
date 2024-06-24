package Projectiles;

import Main.GamePanel;
import Tool.DefensePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Sun extends JPanel implements MouseListener {
    private GamePanel gp;
    private Image sunImage;

    private int myX;
    private int myY;
    private int endY;

    private int destruct = 200;
    private boolean isMoving = true; // Add this field to manage movement state


    public Sun(GamePanel parent,int startX,int startY,int endY){
        this.gp = parent;
        this.endY = endY;
        setSize(80,80);
        setOpaque(false);
        myX = startX;
        myY = startY;
        setLocation(myX,myY);
        sunImage = new ImageIcon(this.getClass().getResource("/images/sun.png")).getImage();
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sunImage,0,0,null);
    }

    public void advance(){
        if (!isMoving) return; // Add this line to stop movement when isMoving is false

        if(myY < endY) {
            myY+= 4;
        }else{
            destruct--;
            if(destruct<0){
                gp.remove(this);
                gp.activeSuns.remove(this);
            }
        }
        setLocation(myX,myY);
    }
    public void stop() {
        isMoving = false; // Add this line to stop the sun
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.setSunScore(gp.getSunScore()+25);
        gp.remove(this);
        gp.activeSuns.remove(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
