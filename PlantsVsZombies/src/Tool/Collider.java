package Tool;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Object.Allies.Defense;

public class Collider extends JPanel implements MouseListener {

    ActionListener al;

    public Collider(){
//        setBorder(new LineBorder(Color.RED));
        setOpaque(false);
        addMouseListener(this);
//        setBackground(Color.green);
        setSize(115,80);
    }

    public Defense assignedDefense;

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
}
