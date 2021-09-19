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
import java.util.ArrayList;
import java.util.Iterator;

//*************************************************************
//*************************************************************
public class DisplayPanel extends JPanel implements Game_of_Life_Modified.Displayer
{
    GameLoop gameLoop;
    LifeArray la;
    ArrayList<Integer> xSizes;
    ArrayList<Integer> ySizes;
    //test variables that kept debug notes from firing EVERY cycle
    //int test = 0;
    //int testMod = 1000;

    //*************************************************************
    public DisplayPanel(int width, int length, int updateInterval)//LifeArray lifeArray)
    {
        //la = lifeArray;
        la = new LifeArray(width, length, updateInterval);

        gameLoop = new GameLoop();

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

        //hold an array containing the sizes of the squares
        //for the LifeArray in order
        xSizes = new ArrayList<Integer>();
        ySizes = new ArrayList<Integer>();

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

                //store the size of the cell on the Y axis
                ySizes.add(thisHeight / la.GetY() + addPixH);

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

                    //store the widths for the first run-through only
                    if (i == 0)
                    {
                        //store the size of the cell on the X axis
                        xSizes.add(thisWidth / la.GetX() + addPixW);
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

    //*************************************************************
    //find the correct square to toggle based on the coordinates of the mouse click
    public void HandleClick(int xPos, int yPos)
    {
        //start it up
        //System.out.print("xPos=" + xPos + " yPos=" + yPos + "\n");


        Iterator<Integer> it = ySizes.iterator();

        //rollingSum will be the total of all cell sizes prior to this one
        int rollingSum = 0;
        //starts at negative one since we don't want to worry about setting rollingSum to the bounds of the first square
        int ySquare = -1;
        while(it.hasNext())
        {
            if (rollingSum <= yPos)
            {
                rollingSum += it.next();
                ySquare++;
                //System.out.print("rollingSum=" + rollingSum + " and ySquare=" + ySquare + "\n");
            }
            //just consume the .next()
            else
            {
                it.next();
            }
        }

        //another loop to handle the width direction horizontally
        it = xSizes.iterator();
        rollingSum = 0;
        //starts at negative one since we don't want to worry about setting rollingSum to the bounds of the first square
        int xSquare = -1;
        while(it.hasNext())
        {
            if (rollingSum <= xPos)
            {
                rollingSum += it.next();
                xSquare++;
                //System.out.print("rollingSum=" + rollingSum + " and xSquare=" + xSquare + "\n");
            }
            //just consume the .next()
            else
            {
                it.next();
            }
        }

        //flips the bit for the clicked square on the array itself, now that we know where that is
        la.ToggleSquare(xSquare, ySquare);

        //System.out.print(xSizes + "\n");
        //System.out.print(ySizes + "\n");

        return;
    }

    //*************************************************************
    public void pauseButton()
    {
        la.togglePause();
    }

    //*************************************************************
    public void run()
    {
        gameLoop.runLoop(la, this);
    }
}
