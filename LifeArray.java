/*

The 2D array for Conway's Game of Life
Contains the array itself and the transform function
to process for each step

*/
package Game_of_Life_Modified;

import Game_of_Life_Modified.Updater;

//*************************************************************
//*************************************************************
public class LifeArray implements Updater
{
    boolean[][] lifeArray;
    int x, y;
    double lastUpdateTime, updateInterval;
    //the board will not update while pause is true
    boolean pause;

    //*************************************************************
    //parameters:
    //  xSize, ySize- the dimensions in cells of the life array
    //  _updateInterval- frequency in milliseconds which the array will progress one tick
    public LifeArray(int xSize, int ySize, int _updateInterval)
    {
        lifeArray = new boolean[ySize][xSize];
//         for (int i = 0; i < ySize; i++)
//         {
//             lifeArray[i] = new boolean[xSize];
//             for (int j = 0; j < xSize; j++)
//             {
//                 lifeArray[i][j] = false;
//             }
//         }

        x = xSize;
        y = ySize;

        pause = true;
    }

    //*************************************************************
    public double getUpdateInterval()
    {
        return updateInterval;
    }

    //*************************************************************
    public void setUpdateInterval(double newUI)
    {
        updateInterval = newUI;
    }

    //*************************************************************
    public void togglePause()
    {
        pause = !pause;
    }

    //*************************************************************
    public void ToggleSquare(int xCoord, int yCoord)
    {
        lifeArray[yCoord][xCoord] = !lifeArray[yCoord][xCoord];
    }

    //*************************************************************
    public int GetX()
    {
        return x;
    }

    //*************************************************************
    public int GetY()
    {
        return y;
    }

    //*************************************************************
    public boolean GetCellValue(int xCoord, int yCoord)
    {
        return lifeArray[yCoord][xCoord];
    }

    //*************************************************************
    //Function to check the Moore neighborhood on a 2d array of
    //booleans. Will return an integer with the number of
    //"true" in proximity via the Moore neighborhood.
    public int MooreNeighborhood(int xCoord, int yCoord)
    {
        int retVal = 0;
        //to store the coordinates of the cell we wish to evaluate
        int tempX, tempY;

        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                //establish the cell we wish to evaluate
                tempX = xCoord + j;
                tempY = yCoord + i;
                if (InBounds(tempX, tempY))
                {
                    //both the x AND y values to test cannot be zero or we are testing
                    //THIS cell. We and this with the truth value of the cell to evaluate.
                    if (!(i == 0 && j == 0) && lifeArray[tempY][tempX])
                    {
                        retVal++;
                    }
                }
            }
        }

        return retVal;
    }

    //*************************************************************
    //returns a boolean regarding whether the specified coordinates
    //are within the 2d array
    public boolean InBounds(int xCoord, int yCoord)
    {
        //BIG evaluation statement for the return.
        //------------------------------------------------
        return
        (
            xCoord > 0
            &&
            yCoord > 0
            &&
            xCoord < x
            &&
            yCoord < y
        );
        //------------------------------------------------
    }

    //*************************************************************
    //Routine to print the Moore neighborhood for each cell in the
    //2d array.
    public void PrintNeighborhoods()
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                System.out.print("x=" + j + " y=" + i + " moore neighborhood count = " + MooreNeighborhood(j, i) + "\n");
            }
        }
    }

    //*************************************************************
    //Advances the game of life board one tick.
    //This is the actual implementation of "Conway's Game of Life".
    public void IterateBoard()
    {
        int mn;
        boolean[][] newArray = new boolean[y][x];
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                mn = MooreNeighborhood(j, i);
                if (lifeArray[i][j] && (mn == 2 || mn == 3))
                {
                    newArray[i][j] = true;
                }
                else if (!(lifeArray[i][j]) && mn ==3)
                {
                    newArray[i][j] = true;
                }
                else
                {
                    newArray[i][j] = false;
                }
            }
        }

        lifeArray = newArray;
    }

    //*************************************************************
    public void Update(double currentTimeTicks)
    {
        if (!pause && currentTimeTicks - lastUpdateTime > updateInterval)
        {
            IterateBoard();

            lastUpdateTime = currentTimeTicks;
        }
    }
}
