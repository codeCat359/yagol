/*

The main routine for a basic java implementation of a
game loop that will draw on a JPanel.

*/
package Game_of_Life_Modified;

import javax.swing.JFrame;
import Game_of_Life_Modified.DisplayPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import Game_of_Life_Modified.LifeArray;
import javax.swing.SwingUtilities;
import java.awt.Point;

//*************************************************************
//*************************************************************
public class main
{
    //*************************************************************
    public static void main(String args[])
    {
        System.out.print("working.\n");

        //this way the x and y are the same length and i know whats going on
        int squareDimension = 200;

        int updateInterval = 1000;

        //LifeArray la = new LifeArray(squareDimension, squareDimension, updateInterval);

        JFrame jf = new JFrame();
        DisplayPanel dp = BuildDisplayPanel(jf, squareDimension, updateInterval);//, la);
        //BuildGameLoop();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();

        jf.setSize(400, 400);

        jf.setVisible(true);

        dp.run();

        return;
    }

    //*************************************************************
    private static DisplayPanel BuildDisplayPanel(JFrame jf, int squareDimension, int updateInterval)//, LifeArray la)
    {
        DisplayPanel dp = new DisplayPanel(squareDimension, squareDimension, updateInterval);
        jf.add(dp);

        //-------
        dp.addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    if (e.getButton() == MouseEvent.BUTTON1)
                    {
                        //la.ToggleSquare(e.getX() / (dp.getWidth() / la.GetX()), e.getY() / (dp.getHeight() / la.GetY()));

                        //convert the point's coordinates from a position on the window to a position on the jframe, and pass that in
                        //not necessary because the mouse listener is on the DisplayPanel itself. I think.
                       /// Point p = SwingUtilities.convertPoint(jf, e.getX(), e.getY(), dp);
                        //dp.HandleClick(p.x, p.y);
                        dp.HandleClick(e.getX(), e.getY());
                    }
                }
            });
        //-------

        //!!!!!WELL THIS WAS IMPORTANT. the keylistener events WILL NOT
        //fire without this.
        dp.setFocusable(true);
        //-------
        dp.addKeyListener(new KeyListener()
            {
                public void keyPressed(KeyEvent ke)
                {
                    System.out.print("keyTyped-> keyCode:" + ke.getKeyCode() + "\n");
                    if (ke.getKeyCode() == KeyEvent.VK_SPACE)
                    {
                        System.out.print("'SPACE' pressed!!!!\n");
                        //la.PrintNeighborhoods();
                        //la.IterateBoard();
                        //dp.Display(0);
                        //la.togglePause();
                        dp.pauseButton();
                    }
                };
                public void keyReleased(KeyEvent ke)
                {
                };
                public void keyTyped(KeyEvent ke)
                {
                };
            });
        //-------
        return dp;
    }

}
