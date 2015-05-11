import java.util.ArrayList;

/**
 * Created by Y1475945.
 */
public class CartesianVector extends TwoDimensional
{

    public CartesianVector(double x, double y)
    {
        super(x, y);
    }

    public CartesianVector()
    {
        super();
    }

    public CartesianVector(double magnitude)
    {
        super((Math.random() * 2 - 1), (Math.random() * 2 - 1));
        this.normalise(magnitude);
    }

    private CartesianVector multiply(double scalar)
    {
        return new CartesianVector
                (
                        this.getX() * scalar,
                        this.getY() * scalar
                );
    }

    private double dotProduct(CartesianVector inVec)
    {
        return (this.getX() * inVec.getX()) + (this.getY() * inVec.getY());
    }

    public double magnitude()
    {
        return Math.hypot(this.getX(), this.getY());
    }

    public double angleBetween(CartesianVector inVec)
    {
        double dot = this.dotProduct(inVec);

        return Math.acos(dot / this.magnitude() * inVec.magnitude());
    }

    public CartesianVector normalise(double newMagnitude)
    {
        if(this.magnitude() == 0)
        {
            return this;
        }

        return new CartesianVector
                (
                        (this.getX() / this.magnitude()) * newMagnitude,
                        (this.getY() / this.magnitude()) * newMagnitude
                );
    }

    public CartesianVector add(CartesianVector inVec)
    {
        return new CartesianVector
                (
                        this.getX() + inVec.getX(),
                        this.getY() + inVec.getY()
                );
    }

    public CartesianVector subtract(CartesianVector inVec)
    {
        return new CartesianVector
                (
                  this.getX() - inVec.getX(),
                  this.getY() - inVec.getY()
                );
    }

    public static CartesianVector avgOfVec(ArrayList<CartesianVector> listOfVectors)
    {

        if(listOfVectors.size() == 0)
        {
            return new CartesianVector();
        }

        CartesianVector total = new CartesianVector();

        for(CartesianVector vector : listOfVectors)
        {
            total = total.add(vector);
        }

        return total.multiply(1.0 / listOfVectors.size());
    }

    public CartesianDouble toCartesianDouble()
    {
        return new CartesianDouble(this.getX(), this.getY());
    }
}