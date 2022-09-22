import java.awt.Color;
/**
 * The Block class encapsulates a Block abstraction which can be 
 * placed into a Gridworld style grid
 * @author Dawson Chen
 * @version 3/6/19
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    /**
     * Constructs a blue block, because blue is the greatest color ever!
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * Gets the color of the block
     * @return color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Changes the color of the block to an inputed color
     * @param newColor is the desired color to be changed to
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Gets the grid that this Block is in
     * @return grid
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Gets the location on the grid that the block is at
     * @return location
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Removes itself(the block) from the grid
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        location = null;
    }

    /**
     * Adds itself(the block) at loc in gr
     * @param loc is the location at which the block is to be placed
     * @param gr is the grid to which the block is to be added
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        grid = gr;
        location = loc;
        Block old = gr.put(loc, this);
        if (old != null)
        {
            old.grid = null;
            old.location = null;
        }
    }

    /**
     * Removes itself from the current location and adds itself
     * to the new location
     * @param newLocation is the location to which the block must be moved
     */
    public void moveTo(Location newLocation)
    {
        grid.remove(location);
        putSelfInGrid(grid, newLocation);
    }

    /**
     * Return a string version representing the block including
     * its location and color
     * @return a string with the location and color of this block
     */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}