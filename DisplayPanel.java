/*

Implementation of a JPanel which is painted on to display the game

*/
package Game_of_Life_Modified;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Graphics;
import Game_of_Life_Modified.GameLoop;
import Game_of_Life_Modified.LifeArray;

//*************************************************************
//*************************************************************
public class DisplayPanel extends JPanel implements Game_of_Life_Modified.Displayer
{
    LifeArray la;
    //test variables that kept debug notes from firing EVERY cycle
    //int test = 0;
    //int testMod = 1000;

    //*************************************************************
    public DisplayPanel(LifeArray lifeArray)
    {
        la = lifeArray;

        return;
    }

    //*************************************************************
    public void Display(float interpolation)
    {
        this.repaint();

        return;
    }

    //*************************************************************
    //The method to put the pixels on the panel.
    @Override
    protected void paintComponent(Graphics g)
    {
        //grab the values for the jpanel so we don't waste time on procedure calls
        int thisHeight = getHeight();
        int thisWidth = getWidth();

        //calculate how many pixels constitute the dead space in the "margins"
        int xtraPixWidth = thisWidth - ((thisWidth / la.GetX()) * la.GetX());
        int xtraPixHeight = thisHeight - ((thisHeight / la.GetY()) * la.GetY());

        //Divide the number of cells by how many cells we need to create
        //to fill in the margin. This will give the period of the need
        //to add one extra pixel
        float xtraPixFreqH = (float) la.GetY() / (float) xtraPixHeight;
        float xtraPixFreqW = (float) la.GetX() / (float) xtraPixWidth;

        //debug info
//         if (test % testMod == 0)
//         {
//             System.out.print("thisHeight=" + thisHeight + "\n");
//             System.out.print("thisWidth=" + thisWidth + "\n");
//             System.out.print("board height=" + ((thisHeight / la.GetY()) * la.GetY()) + "\n");
//             System.out.print("board width=" + ((thisWidth / la.GetX()) * la.GetX()) + "\n");
//             System.out.print("xtraPixHeight=" + xtraPixHeight + "\n");
//             System.out.print("xtraPixWidth=" + xtraPixWidth + "\n");
//             System.out.print("xtraPixFreqH=" + xtraPixFreqH + "\n");
//             System.out.print("xtraPixFreqW=" + xtraPixFreqW + "\n");
//         }

        //more debug info
        //StringBuilder sb = new StringBuilder("Extra Pixels added at height: ");

        try
        {
            //tally the extra pixels that have been added on the Y axis
            int xtraPixAddedH = 0;
            for (int i = 0; i < la.GetY(); i++)
            {
                //change this to one if we need an extra pixel this round
                int addPixH = 0;
                if (i == (int) (xtraPixFreqH * xtraPixAddedH))
                {
                    addPixH = 1;
                    //sb.append(i + " ");
                }

                //tally the extra pixels that have been added on the X axis
                int xtraPixAddedW = 0;
                for (int j = 0; j < la.GetX(); j++)
                {
                    //change this to one if we need an extra pixel this round
                    int addPixW = 0;
                    //if ((int)xtraPixWidth != 0 && j % (int) xtraPixWidth == 0)
                    if (j == (int) (xtraPixFreqW * xtraPixAddedW))
                    {
                        addPixW = 1;
                    }
                    g.setColor(la.GetCellValue(j, i) ? Color.BLACK : Color.WHITE);
                    g.fillRect(j * (thisWidth / la.GetX()) + xtraPixAddedW, i * (thisHeight / la.GetY()) + xtraPixAddedH, thisWidth / la.GetX() + addPixW, thisHeight / la.GetY() + addPixH);
                    //System.out.print(i + " " + j + "\n");

                    //extra pixel added? increase the tally
                    if (addPixW == 1)
                    {
                        xtraPixAddedW++;
                    }
                }

                //extra pixel added? increase the tally
                if (addPixH == 1)
                {
                    xtraPixAddedH++;
                    //System.out.print((int) (xtraPixFreqH * xtraPixAddedH) + "\n");
                }
            }
        }
        //piss poor exception handling....
        catch (ArithmeticException e)
        {
            //System.out.print(e);
            //System.out.print(e.printStackTrace() + "\n");
            throw e;
        }

//         if (test % testMod == 0)
//         {
//             System.out.print(sb.toString() + "\n");
//         }

//         test++;
    }

    //*************************************************************
    public LifeArray GetLifeArray()
    {
        return la;
    }
}
