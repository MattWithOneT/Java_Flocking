import java.util.ArrayList;

/**
 * Created by Y1475945.
 */
public class Bird extends Lifeform
{
    private static double alignmentFactor;
    private static double separationFactor;
    private static double cohesionFactor;

/************************************************************************************
 * Instantiation Functions
 ************************************************************************************/

    public Bird(Canvas canvas)
    {
        super(canvas);
        this.setSpeed(75);
        this.setView_distance(75);
        this.setRepulsion_distance(25);
        this.setColour("Green");
    }

    public Bird(Canvas canvas, CartesianDouble pos)
    {
        super(canvas, pos);
        this.setSpeed(75);
        this.setView_distance(75);
        this.setRepulsion_distance(25);
        this.setColour("Green");
    }

/************************************************************************************
 * Object-Specific Functions
 ************************************************************************************/

    private CartesianVector calcAlignment()
    {
        ArrayList<Env_Entity> myNeighbours = this.getMyNeighbours();

        if(myNeighbours.size() == 0)
        {
            return new CartesianVector();
        }

        ArrayList<CartesianVector> vecList = new ArrayList<>();

        for(Env_Entity entity : myNeighbours)
        {
            if(entity instanceof Bird)
            {
                vecList.add(((Lifeform) entity).getVelocity());
            }
        }

        return CartesianVector.avgOfVec(vecList).normalise(alignmentFactor);
    }

    protected CartesianVector calcCohesion()
    {
        ArrayList<Env_Entity> myNeighbours = this.getMyNeighbours();

        if(myNeighbours.size() == 0)
        {
            return new CartesianVector();
        }

        ArrayList<CartesianVector> posList = new ArrayList<>();
        CartesianDouble avgPos;

        for(Env_Entity entity : myNeighbours)
        {
            if(entity instanceof Bird  && !this.isTooClose(entity))
            {
                posList.add(entity.getCurrentPosition().toCartesianVector());
            }
        }

        avgPos = CartesianVector.avgOfVec(posList).toCartesianDouble();

        return this.getCurrentPosition().vecTo(avgPos).normalise(cohesionFactor);
    }

    protected CartesianVector calcSeparation()
    {
        ArrayList<Env_Entity> myNeighbours = this.getMyNeighbours();
        CartesianVector totVec;

        if(myNeighbours.size() == 0)
        {
            return new CartesianVector();
        }

        ArrayList<CartesianVector> vecList = new ArrayList<>();

        for(Env_Entity entity : myNeighbours)
        {
            if(entity instanceof Predator)
            {
                vecList.add(this.getCurrentPosition().vecTo(entity.getCurrentPosition()).normalise(-Math.exp((2 * this.getView_Distance()) / this.distanceTo(entity))));
            }

            if(entity instanceof Terrain)
            {
                vecList.add(this.getCurrentPosition().toCartesianVector().subtract(entity.getCurrentPosition().toCartesianVector()).normalise(Math.exp((2 * this.getView_Distance()) / this.distanceTo(entity))));
            }

            if(this.isTooClose(entity))
            {
                if (entity instanceof Bird)
                {
                    vecList.add(this.getCurrentPosition().toCartesianVector().subtract(entity.getCurrentPosition().toCartesianVector()).normalise(Math.exp((2 * this.getRepulsion_distance()) / this.distanceTo(entity))));
                }
                this.detectFatalCollision(entity);
            }
        }

        totVec = CartesianVector.avgOfVec(vecList);

        return totVec.normalise(separationFactor);
    }


    protected CartesianVector calcNewVelocity()
    {
        this.findNeighbours();

        CartesianVector newVelocity = this.getVelocity();

        newVelocity = newVelocity.add(this.calcCohesion());
        newVelocity = newVelocity.add(this.calcAlignment());
        newVelocity = newVelocity.normalise(this.getSpeed());
        newVelocity = newVelocity.add(this.calcSeparation());

        if ((int) Math.round(Math.random() * 100) == 1)
        {
            newVelocity = newVelocity.add(new CartesianVector(200));
        }

        return newVelocity;
    }

    public void draw()
    {
        super.draw();
    }

    public void nextFrame() {
        this.setVelocity(this.calcNewVelocity());
        this.updatePos(50);
        this.draw();
    }

    public static void setAlignmentFactor(double newAlignmentFactor)
    {
        alignmentFactor = newAlignmentFactor;
    }

    public static void setCohesionFactor(double newCohesionFactor)
    {
        cohesionFactor = newCohesionFactor;
    }
    public static void setSeparationFactor(double newSeparationFactor)
    {
        separationFactor = newSeparationFactor;
    }
}
