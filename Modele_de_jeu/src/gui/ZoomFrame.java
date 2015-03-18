package gui;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import t2s.SIVOXDevint;

public class ZoomFrame extends JFrame implements KeyListener {

    private SIVOXDevint voix = new SIVOXDevint();
    
    public ZoomFrame(Icon icon, String string) {
        Image temp = ((ImageIcon) (icon)).getImage().getScaledInstance(500,
                500, 0);
        Container cont = getContentPane();
        JLabel label = new JLabel(new ImageIcon(temp));
        cont.add(label);
        label.addMouseListener(new MouseAdapter() {

            /**
             * Inherited method.
             * 
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            public void mousePressed(MouseEvent arg0) {
                dispose();
                voix.stop();
            }
        });
        addKeyListener(this);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        voix.playWav(string);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            dispose();
            voix.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }
}
