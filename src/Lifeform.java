/**
 * Created by Y1475945.
 */

import javax.xml.stream.events.EndDocument;
import java.util.ArrayList;

public abstract class Lifeform extends Env_Entity
{
    private static ArrayList<Env_Entity> worldInhabitants = new ArrayList<>();
    protected static ArrayList<Lifeform> deadBirds = new ArrayList<>();

    private ArrayList<Env_Entity> myNeighbours = new ArrayList<>();
    private CartesianVector velocity;
    private double repulsion_distance;
    private double view_distance;
    private double view_angle;
    private double speed;

    private static boolean neighbourLines = false;
    private static boolean viewArc = false;

    private double separationFactor;
    private double cohesionFactor;

/************************************************************************************
 * Instantiation Functions
 ************************************************************************************/

    public Lifeform(Canvas canvas)
    {
        super(canvas);
        this.velocity = new CartesianVector((Math.random() * 2) -1, (Math.random() * 2) - 1).normalise(1);
        this.view_angle = Math.toRadians(160);
    }

/************************************************************************************
 * Geometric Calculation Functions
 ************************************************************************************/



    /**
     * Finds all entities that are in sight and adds them to the internal list of neighbours.
     */
    protected void findNeighbours()
    {
        this.myNeighbours.clear();

        if (worldInhabitants.size() > 0)
        {
            for (Env_Entity entity : worldInhabitants)
            {
                if (isInSight(entity) && entity != this)
                {
                    this.myNeighbours.add(entity);
                }
            }
        }
    }

    /**
     * Determines whether the given entity is within sight of the object.
     * @param entity Any entity within the environment.
     * @return boolean
     */
    public boolean isInSight(Env_Entity entity)
    {
        boolean inFront = this.isInFront(entity);
        boolean closeEnough = this.distanceTo(entity) <= this.getView_Distance();

        inFront = true;

        return (inFront && closeEnough);
    }

    public boolean isInFront(Env_Entity entity)
    {
        CartesianVector vecBetween = this.getCurrentPosition().vecTo(entity.getCurrentPosition());

        double theta = this.getVelocity().angleBetween(vecBetween);

        double lowerBearing = this.getMyBearing() - this.getView_angle()/2;
        double upperBearing = this.getMyBearing() + this.getView_angle()/2;

        if(lowerBearing < 0)
        {
            lowerBearing += Math.toRadians(360);
        }

        if(upperBearing > Math.toRadians(360))
        {
            upperBearing -= Math.toRadians(360);
        }

        return (theta >= lowerBearing) || (theta <= upperBearing);
    }

    public boolean isTooClose(Env_Entity entity)
    {
        return this.distanceTo(entity) <= this.getRepulsion_distance();
    }


    /**
     * Updates the position co-ordinates of the object according to its velocity in the given timeframe.
     * @param timePassed Time passed in milliseconds.
     */
    public void updatePos(double timePassed)
    {
        CartesianDouble nextPos;
        double localVelMag = this.getVelocity().magnitude();
        double localBearing = Math.toDegrees(this.getMyBearing());


        nextPos = this.calculateEndPoint(this.getCurrentPosition(), (localVelMag * (timePassed / 1000)), localBearing);

        double nextX = nextPos.getX();
        double nextY = nextPos.getY();

        int width = this.getEnvironment().getWidth();
        int height = this.getEnvironment().getHeight();

        if(nextX >= width)
        {
            nextX -= width;
        }

        if(nextX <= 0)
        {
            nextX += width;
        }

        if(nextY >= height)
        {
            nextY -= height;
        }

        if (nextY <= 0)
        {
            nextY += height;
        }

        this.setPosition(new CartesianDouble(nextX, nextY));
    }

    protected void detectFatalCollision(Env_Entity entity)
    {
        if(entity instanceof Terrain && this.distanceTo(entity) <= entity.getSize())
        {
            deadBirds.add(this);
        }
    }

    protected void draw()
    {
        double localBearing = Math.toDegrees(this.getMyBearing());
        CartesianDouble localPosition = this.getCurrentPosition(), nextPosition;

        // array holding angles and distances to draw a turtle
        // Lower section credited to Chris Harte from Java Lab 3
        int[][] polarArray = new int[][]
                {
                        { 150, 120, 120 },
                        {this.getSize(), this.getSize(),this.getSize()}
                };

        for(int i=0; i<3 ; i++)
        {
            // update local bearing
            localBearing  += polarArray[0][i];
            // calculate new position given local bearing and distance
            nextPosition = this.calculateEndPoint(localPosition, polarArray[1][i], (int)Math.round(localBearing));
            // draw a line between local position and new position
            this.getEnvironment().drawLineBetweenPoints(localPosition, nextPosition, this.getColour());
            // update local position reference to point at new position
            localPosition = nextPosition;
        }

        if(viewArc)
        {
            this.getEnvironment().drawArc
                    (this.getCurrentPosition(),
                            this.getView_Distance(),
                            -Math.toDegrees(this.getMyBearing() + (this.view_angle / 2.0)),
                            Math.toDegrees(this.getView_angle()),
                            "Red"
                    );
        }

        if(neighbourLines)
        {
            for (Env_Entity thing : this.getMyNeighbours())
            {
                if(this.distanceTo(thing) <= this.getView_Distance())
                {
                    this.getEnvironment().drawLineBetweenPoints(localPosition, thing.getCurrentPosition(), "Green");

                }
            }
        }
    }

    public static void cleanUpDead()
    {
        for(Lifeform lifeform : deadBirds)
        {
            worldInhabitants.remove(lifeform);
        }
    }

/************************************************************************************
 * Getter Functions
 ************************************************************************************/

    public double getRepulsion_distance()
    {
        return this.repulsion_distance;
    }

    public double getView_Distance()
    {
        return view_distance;
    }

    public double getView_angle()
    {
        return this.view_angle;
    }

    public CartesianVector getVelocity()
    {
        return this.velocity;
    }

    public ArrayList<Env_Entity> getMyNeighbours()
    {
        return this.myNeighbours;
    }

    public double getMyBearing()
    {
            return Math.atan2(this.getVelocity().getY(),this.getVelocity().getX());
    }

    public double getSeparationFactor()
    {
        return this.separationFactor;
    }

    public double getCohesionFactor()
    {
        return this.cohesionFactor;
    }

    public double getSpeed()
    {
        return speed;
    }

    public static ArrayList<Env_Entity> getWorldInhabitants()
    {
        return worldInhabitants;
    }

 /************************************************************************************
 * Setter Functions
 ************************************************************************************/

    protected void setVelocity(CartesianVector newVel)
    {
        this.velocity = new CartesianVector(newVel.getX(), newVel.getY());
    }

    public static void setWorldInhabitants(ArrayList<Env_Entity> worldInhabitants)
    {
        Lifeform.worldInhabitants = worldInhabitants;
    }

    public void setCohesionFactor(double cohesionFactor)
    {
        this.cohesionFactor = cohesionFactor;
    }

    public void setView_distance(double view_distance)
    {
        this.view_distance = view_distance;
    }

    public void setView_angle(double view_angle)
    {
        this.view_angle = view_angle;
    }

    public void setSeparationFactor(double separationFactor)
    {
        this.separationFactor = separationFactor;
    }

    public void setRepulsion_distance(double repulsion_distance)
    {
        this.repulsion_distance = repulsion_distance;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }
}
