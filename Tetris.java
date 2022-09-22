import java.util.*;
/**
 * Is the game Tetris.
 *
 * @author Dawson Chen
 * @version 3/6/19
 */
public class Tetris implements ArrowListener
{
    // instance variables - replace the example below with your own
    private BlockDisplay display;
    private MyBoundedGrid<Block> grid;
    private Tetrad activeTetrad;
    private int score;
    private int lvl;
    private int rowsCleared;
    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        lvl = 1;
        score = 0;
        grid = new MyBoundedGrid(20, 10);
        display = new BlockDisplay(grid);
        display.setTitle("Tetris: Level: " + lvl + ", Score: " + score);
        display.showBlocks();
        activeTetrad = new Tetrad(grid, (int)(Math.random() * 7));
        display.setArrowListener(this);
        try 
        {
            play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Rotates tetrad when up arrow is pressed
     */
    public void upPressed()
    {
        activeTetrad.rotate();
        display.showBlocks();
    }

    /**
     * Moves tetrad down one when down arrow is pressed
     */
    public void downPressed()
    {
        activeTetrad.translate(1, 0);
        display.showBlocks();
    }

    /**
     * Moves tetrad left one when down arrow is pressed
     */
    public void leftPressed()
    {
        activeTetrad.translate(0, -1);
        display.showBlocks();
    }

    /**
     * Moves tetrad right one when down arrow is pressed
     */
    public void rightPressed()
    {
        activeTetrad.translate(0, 1);
        display.showBlocks();
    }

    /**
     * Plays tetris
     */
    public void play() throws InterruptedException
    {
        while (!lose())
        {
            try
            {
                //Pause for 1000 milliseconds.
                display.setTitle("Tetris: Level: " + lvl + ", Score: " + score);
                Thread.sleep((int)((11 - lvl) * 50));
                display.showBlocks();
                if (!activeTetrad.translate(1, 0))
                {
                    clearCompletedRows();
                    activeTetrad = new Tetrad(grid, (int)(Math.random() * 7));
                }
            }
            catch (InterruptedException e)
            {
                //ignore
            } 

        }
    }

    /**
     * @return the highest row with block(s)
     */
    private int highestRow()
    {
        int high = 0;
        for (int j = grid.getNumRows() - 1; j >= 0; j--)
        {
            boolean empty = true;
            for (int i = 0; i < grid.getNumCols(); i++)
            {
                if (grid.get(new Location(j, i)) != null)
                    empty = false;
            }
            if (empty)
            {
                return high;
            }
            high++;
        }
        return high;
    }

    /**
     * Calls this method when the player has lost and filled all rows
     * @return whether the player has lost
     */
    public boolean lose() throws InterruptedException
    {
        if (highestRow() >= grid.getNumRows())
        {
            for (int j = 0; j < grid.getNumRows(); j++)
            {
                for (int i = 0; i < grid.getNumCols(); i++)
                {
                    Block b = grid.get(new Location(j, i));
                    if (b != null)
                    {
                        b.removeSelfFromGrid();
                        Thread.sleep(30);
                    }
                    display.showBlocks();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * @precondition 0 <= row < number of rows
     * @postcondition Returns true if every cell in the
     * given row is occupied;
     * @param row is the row to be tested for
     * @return false otherwise.
     */
    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++)
        {
            if (grid.get(new Location(row, i)) == null)
                return false;
        }
        return true;
    }

    /**
     * Hard drops when space is pressed
     */
    public void spacePressed() 
    {
        boolean bottom = false;
        while (!bottom)
        {
            if (!activeTetrad.translate(1, 0))
            {
                bottom = true;
            }
        }
        display.showBlocks();
    }

    /**
     * precondition: 0 <= row < number of rows;
     * given row is full of blocks
     * postcondition: Every block in the given row has been
     * removed, and every block above row
     * has been moved down one row.
     * @param row is the row to be cleared
     */
    private void clearRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++)
        {
            grid.remove(new Location(row, i));
        }

        for (int j = row - 1; j >= 0; j--)
        {
            for (int i = 0; i < grid.getNumCols(); i++)
            {
                Block b = grid.get(new Location(j, i));
                if (b != null)
                {
                    b.moveTo(new Location(j + 1, i));
                }
            }
        }

    }

    /**
     * @postcondition All completed rows have been cleared.
     */
    private void clearCompletedRows() 
    {
        int temp = 0;
        for (int i = grid.getNumRows() - 1; i >= 0; i--)
        {
            if (isCompletedRow(i))
            {
                clearRow(i);
                temp++;
                rowsCleared++;
                i++;
            }
        }
        if (temp == 1)
            temp = 40;
        if (temp == 2)
            temp = 100;
        if (temp == 3)
            temp = 300;
        if (temp == 4)
            temp = 1200;
        score += temp * lvl;
        if (rowsCleared >= 10)
        {
            rowsCleared = 0;
            lvl++;
        }
    }
}
