package Tool;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

import Main.GamePanel;
import Object.Allies.Defense;

public class Collider extends JPanel implements MouseListener, MouseMotionListener {
    private ActionListener al;
    public Defense assignedDefense;

    public Collider(){
//        setBorder(new LineBorder(Color.RED));
        setOpaque(false);
        addMouseListener(this);
        addMouseMotionListener(this);
//        setBackground(Color.green);
        setSize(115,80);
    }
    public void setDefense(Defense defense){
        assignedDefense = defense;
    }
    public void removeDefense(){
        assignedDefense.stop();
        assignedDefense = null;
    }
    public boolean isInsideCollider(int tx){
        return (tx > getLocation().x) && (tx < getLocation().x + 100);
    }
    public Point getMousePositionInCollider() {
        Point mousePoint = getMousePosition();
        if (mousePoint != null) {
            return new Point(mousePoint.x - getX(), mousePoint.y - getY());
        } else {
            return null;
        }
    }
    public void setAction(ActionListener al){
        this.al = al;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(al != null){
            al.actionPerformed(new ActionEvent(this,ActionEvent.RESERVED_ID_MAX+1,""));
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {
        repaint();
    }
}
