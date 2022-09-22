import java.awt.Color;
import java.util.*;
import java.util.concurrent.Semaphore;
/**
 * Represents a group of blocks that move together in 
 * the tetris game.
 *
 * @author Dawson Chen
 * @version 5/18/19
 */
public class Tetrad
{
    private MyBoundedGrid<Block> grid;
    private Block[] blocks;
    private Semaphore lock; 
    /**
     * Constructor for objects of class Tetrad
     * @param grid is the grid in which the tetrad to be instantiated in
     * @param type is the type of tetrad
     */
    public Tetrad(MyBoundedGrid<Block> grid, int type)
    {
        blocks = new Block[4];
        Location[] locations = new Location[9];
        int rand = type;
        Color[] colors = {Color.RED, Color.GRAY, Color.CYAN, Color.YELLOW, 
            Color.MAGENTA, Color.BLUE, Color.GREEN};
        this.grid = grid;
        int mid = grid.getNumCols() / 2;
        int count = 0;
        Location a = new Location(0, mid);
        Location b = new Location(1, mid);
        Location c = new Location(2, mid);
        Location d = new Location(3, mid);
        Location e = new Location(2, mid + 1);
        Location f = new Location(2, mid - 1);
        Location g = new Location(0, mid + 1);
        Location h = new Location(0, mid - 1);
        Location i = new Location(1, mid + 1);
        Location j = new Location(1, mid - 1);

        Location[][] places = 
        {
            {b, a, c, d},
            {a, b, g, h},
            {b, a, i, g},
            {b, a, c, e},
            {b, a, c, f},
            {a, b, j, g},
            {a, b, h, i}
        };
        for (int x = 0; x < blocks.length; x++)
        {
            blocks[x] = new Block();
            blocks[x].setColor(colors[rand]);
        }
        addToLocations(grid, places[rand]);
        lock = new Semaphore(1, true); 
    }

    /**
     * Adds each block from the tetrad to the array of locations
     * in grid
     * @param gr is the grid to which blocks should be added
     * @param locs is the locations in grid at which blocks must
     * be added
     * @precondition blocks are not in any grid;
     * locs.length = 4.
     * @postcondition The locations of blocks match locs,
     * and blocks have been put in the grid.
     */
    private void addToLocations(MyBoundedGrid<Block> gr, Location[] locs) 
    {
        for (int i = 0; i < 4; i++)
        {
            blocks[i].putSelfInGrid(gr, locs[i]);
        }
    }

    /**
     * @precondition Blocks are in the grid.
     * @postcondition Returns old locations of blocks;
     * blocks have been removed from grid.
     */
    private Location[] removeBlocks()
    {
        Location[] locs = new Location[4];
        for (int i = 0; i < blocks.length; i++)
        {
            locs[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return locs;
    }

    /**
     * @postcondition Returns true if each of locs is 
     * valid and empty in grid; false otherwise.
     * @return whether locs is valid and empty in grid
     */
    private boolean areEmpty(MyBoundedGrid<Block> gr, Location[] locs) 
    {
        ArrayList<Location> loos = gr.getOccupiedLocations();
        for (Location loc : locs)
        {
            if (!grid.isValid(loc) || loos.contains(loc))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @postcondition Attempts to move this tetrad deltaRow
     * rows down and deltaCol columns to the
     * right, if those positions are valid
     * and empty; returns true if successful
     * and false otherwise.
     * @param deltaRow the number of rows to translate
     * @param deltaCol the number of columns to translate
     * @return whether translate was successful
     */
    public boolean translate(int deltaRow, int deltaCol) 
    {
        try
        {
            lock.acquire();
            Location[] oldlocs = new Location[4];
            Location[] newlocs = new Location[4];
            for (int i = 0; i < 4; i++)
            {
                if (blocks[i].getLocation() != null)
                    newlocs[i] = new Location(
                        blocks[i].getLocation().getRow() + deltaRow, 
                        blocks[i].getLocation().getCol() + deltaCol);
            }
            oldlocs = removeBlocks();
            if (!areEmpty(grid, newlocs))
            {
                addToLocations(grid, oldlocs);
                return false;
            }
            addToLocations(grid, newlocs);
            return true;
        }
        catch (InterruptedException e)
        {
            // did not modify the tetrad
            return false;
        }
        finally
        {
            lock.release();
        } 
    }

    /**
     * @postcondition Attempts to rotate this tetrad
     * clockwise by 90 degrees about its
     * center, if the necessary positions
     * are empty 
     * @return true if successful
     * and false otherwise.
     */
    public boolean rotate() 
    {
        Location[] oldlocs = new Location[4];
        Location[] newlocs = new Location[4];
        int row = blocks[0].getLocation().getRow();
        int col = blocks[0].getLocation().getCol();
        for (int i = 0; i < 4; i++)
        {
            newlocs[i] = new Location(row - col + 
                blocks[i].getLocation().getCol(),
                row + col - blocks[i].getLocation().getRow());
        }
        oldlocs = removeBlocks();
        if (!areEmpty(grid, newlocs))
        {
            addToLocations(grid, oldlocs);
            return false;
        }
        addToLocations(grid, newlocs);
        return true;
    }
}

