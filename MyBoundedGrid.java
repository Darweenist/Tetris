import java.util.*;
/**
 * MyBoundedGrid offers the ability to place objects into a grid
 * and manipulate the different objects on it.
 *
 * @author Dawson Chen
 * @version 3/6/19
 * @param <E> can be any object stored in the grid
 */
public class MyBoundedGrid<E>
{
    // instance variables - replace the example below with your own
    private int rows;
    private int columns;
    private Object[][] grid;
    /**
     * Constructor for objects of class MyBoundedGrid
     * Sets rows and columns instance fields with rows and cols
     * @param rows is the number of rows in the grid
     * @param cols is the number of columns in the grid
     */
    public MyBoundedGrid(int rows, int cols)
    {
        this.rows = rows;
        this.columns = cols;
        grid = new Object[rows][cols];
    }

    /**
     * Gets how many rows the grid has
     * @return rows
     */
    public int getNumRows()
    {
        return rows;
    }

    /**
     * Gets the number of columns the grid has
     * @return columns
     */
    public int getNumCols()
    {
        return columns;
    }

    /**
     * Gets whether the inputed row and column is in the grid
     * @param loc is the location to be tested
     * @return true if loc is a valid coordinate in the grid
     * otherwise false
     */
    public boolean isValid(Location loc)
    {
        return (loc.getRow() >= 0 && loc.getRow() < rows
            && loc.getCol() >= 0 && loc.getCol() < columns);
    }

    /**
     * Puts an object into the grid at a specified location
     * @param loc is the location to put
     * @param obj is what to put
     * @return the object that used to be at loc before the putting
     */
    public E put(Location loc, E obj)
    {
        if (isValid(loc))
        {
            E temp = get(loc);
            grid[loc.getRow()][loc.getCol()] = obj;
            return temp;
        }
        return null;
    }

    /**
     * Removes the object at a specified location
     * @param loc is the location whose object is to be removed
     * @return the removed obj
     */
    public E remove(Location loc)
    {
        E temp = get(loc);
        put(loc, null);
        return temp;
    }

    /**
     * Gets the object at a specified location and returns it
     * @param loc is the location to look at
     * @return the object at loc
     */
    public E get(Location loc)
    {
        return (E)grid[loc.getRow()][loc.getCol()];
    }

    /**
     * Returns an arraylist of all the locations that are occupied
     * @return an arraylist filled with occupied locations
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> list = new ArrayList<Location>();
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++)
            {
                if (grid[r][c] != null)
                    list.add(new Location(r, c));
            }
        }
        return list;
    }
}
