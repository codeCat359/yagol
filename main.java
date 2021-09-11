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
import Game_of_Life_Modified.GameLoop;
import Game_of_Life_Modified.LifeArray;

//*************************************************************
//*************************************************************
public class main
{
    static GameLoop gameLoop;

    //*************************************************************
    public static void main(String args[])
    {
        System.out.print("working.\n");

        LifeArray la = new LifeArray(50, 50, 0 /* NEEDS CHANGED*/);

        JFrame jf = new JFrame();
        DisplayPanel dp = BuildDisplayPanel(jf, la);
        BuildGameLoop();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();

        jf.setSize(400, 400);

        jf.setVisible(true);

        gameLoop.GameLoop(la, dp);

        return;
    }

    //*************************************************************
    private static DisplayPanel BuildDisplayPanel(JFrame jf, LifeArray la)
    {
        DisplayPanel dp = new DisplayPanel(la);
        jf.add(dp);

        //-------
        dp.addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    if (e.getButton() == MouseEvent.BUTTON1)
                    {
                        la.ToggleSquare(e.getX() / (dp.getWidth() / la.GetX()), e.getY() / (dp.getHeight() / la.GetY()));
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
                        la.IterateBoard();
                        dp.Display(0);
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

    //*************************************************************
    private static void BuildGameLoop()
    {
        gameLoop = new GameLoop();

        return;
    }
}
