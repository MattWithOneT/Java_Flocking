/**
 * Created by Y1475945.
 */
public abstract class Env_Entity
{
    //Private Variables
    private CartesianDouble currentPosition;
    private Canvas environment;
    private String colour;
    private double size;

/************************************************************************************
 * Constructors
 ************************************************************************************/

    public Env_Entity(Canvas canvas)
    {
        this.environment = canvas;
        this.size = 10;
        this.colour = "Black";
        this.currentPosition = new CartesianDouble
                (
                    Math.random() * this.environment.getWidth(),
                    Math.random() * this.environment.getHeight()
                );

    }

    public Env_Entity(Canvas canvas, CartesianDouble position)
    {
        this.environment = canvas;
        this.currentPosition = position;
        this.colour = "Black";
        this.size = 10;
    }

    public Env_Entity(Canvas canvas, CartesianDouble position, double size)
    {
        this.environment = canvas;
        this.currentPosition = position;
        this.size = size;
        this.colour = "Black";
    }

    public Env_Entity(Canvas canvas, CartesianDouble position, double size, String colour)
    {
        this.environment = canvas;
        this.currentPosition = position;
        this.size = size;
        this.colour = colour;
    }

/************************************************************************************
 * Setter Functions
 ************************************************************************************/

    protected void setPosition(CartesianDouble newPos)
    {
        this.currentPosition = newPos;
    }

    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public void setSize(double size)
    {
        this.size = size;
    }

    /************************************************************************************
 * Getter Functions
 ************************************************************************************/

    public Canvas getEnvironment()
    {
        return environment;
    }

    public CartesianDouble getCurrentPosition()
    {
        return this.currentPosition;
    }

    public double getSize()
    {
        return size;
    }

    public String getColour()
    {
        return colour;
    }

    /************************************************************************************
 * Abstract Functions
 ************************************************************************************/

    protected abstract void draw();
    public abstract void nextFrame();

/************************************************************************************
 * Generic Functions
 ************************************************************************************/

    protected double distanceTo(Env_Entity thing) // TODO Return distance perpendicular to line / optimise for having lines that aren't straight.
    {
        if(thing instanceof Terrain)
        {
            if(thing instanceof Wall)
            {
                return this.getCurrentPosition().vecTo(thing.getCurrentPosition()).magnitude() - 15;
            }
            return this.getCurrentPosition().vecTo(thing.getCurrentPosition()).magnitude() - (thing.getSize() / 2.0);
        }

        return this.getCurrentPosition().vecTo(thing.getCurrentPosition()).magnitude();
    }

    /***************************************************************************
     * CALCULATE ENDPOINT METHOD
     * calculates a new coordinate given a start point, distance and bearing
     ***************************************************************************/
    protected CartesianDouble calculateEndPoint(CartesianDouble startPosition,
                                                double distance,
                                                double bearing)
    {
        double deltaX, deltaY; // relative distance moved x,y

        double newX, newY; // new positions after move x,y

        // calculate deltaX and deltaY
        deltaX =  distance * Math.cos(Math.toRadians(bearing));
        deltaY =  distance * Math.sin(Math.toRadians(bearing));

        // calculate new floating point coordinates
        newX = startPosition.getX() + deltaX;
        newY = startPosition.getY() + deltaY;

        // make new position coordinate
        return new CartesianDouble(newX, newY);
    }
}

