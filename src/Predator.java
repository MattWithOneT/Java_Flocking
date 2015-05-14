import java.util.ArrayList;

/**
 * Created by Y1475945.
 */
public class Predator extends Lifeform
{
    private double cohesionFactor;
    private double separationFactor;
    public Predator(Canvas canvas)
    {
        super(canvas);
        this.setSize(15);
        this.setColour("Red");
        this.setSpeed(115);

        this.cohesionFactor = 5;
        this.separationFactor = 20;

        this.setView_distance(100);
        this.setRepulsion_distance(50);
    }

    public Predator(Canvas canvas, CartesianDouble position)
    {
        super(canvas, position);
        this.setSize(15);
        this.setColour("Red");
        this.setSpeed(115);

        this.cohesionFactor = 5;
        this.separationFactor = 20;

        this.setView_distance(100);
        this.setRepulsion_distance(50);
    }

    @Override
    protected void draw()
    {
        super.draw();
    }

    @Override
    public void nextFrame()
    {
        this.setVelocity(this.calcNewVelocity());
        this.updatePos(50);
        this.draw();
    }

    private void eatBird(Env_Entity entity)
    {
        if(this.distanceTo(entity) <= this.getSize() && entity instanceof Bird)
        {
            deadBirds.add((Lifeform) entity);
        }
    }

    public CartesianVector calcNewVelocity()
    {
        this.findNeighbours();
        CartesianVector newVelocity = this.getVelocity();

        newVelocity = newVelocity.add(this.calcCohesion());
        newVelocity = newVelocity.normalise(this.getSpeed());
        newVelocity = newVelocity.add(this.calcSeparation());

        if ((int) Math.round(Math.random() * 20) == 1)
        {
            newVelocity = newVelocity.add(new CartesianVector(200));
        }

        return newVelocity;
    }

    protected CartesianVector calcCohesion()
    {
        ArrayList<Env_Entity> myNeighbours = this.getMyNeighbours();

        if(myNeighbours.size() == 0)
        {
            return new CartesianVector();
        }

        ArrayList <CartesianVector> posList = new ArrayList<>();
        CartesianDouble avgPos;

        for(Env_Entity entity : myNeighbours)
        {
            if(this.isInSight(entity) && entity instanceof Bird)
            {
                posList.add(entity.getCurrentPosition().toCartesianVector());
                this.eatBird(entity);
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
            if(entity instanceof Terrain)
            {
                vecList.add(this.getCurrentPosition().toCartesianVector().subtract(entity.getCurrentPosition().toCartesianVector()).normalise(Math.exp((2 * this.getView_Distance()) / this.distanceTo(entity))));
            }

            if(this.isTooClose(entity) && entity instanceof Predator)
            {
                vecList.add(this.getCurrentPosition().toCartesianVector().subtract(entity.getCurrentPosition().toCartesianVector()).normalise(Math.exp((2 * this.getRepulsion_distance()) / this.distanceTo(entity))));
            }

            this.detectFatalCollision(entity);
        }

        totVec = CartesianVector.avgOfVec(vecList);

        return totVec.normalise(separationFactor);
    }

}
