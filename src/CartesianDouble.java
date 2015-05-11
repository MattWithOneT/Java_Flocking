/***************************************************************************  
 *
 * CARTESIAN DOUBLE
 * Class to store x and y coordinate pair as doubles.
 *
 ***************************************************************************/
public class CartesianDouble extends TwoDimensional
{
    /***************************************************************************
     * CONSTRUCTOR
     ***************************************************************************/
    public CartesianDouble(double x, double y)
    {
        super(x, y);
    }

    public CartesianDouble()
    {
        super();
    }

    /**
     * Returns a vector between the co-ordinate calling the method and the input co-ordinate.
     * @param target The end point of the vector as a CartesianDouble.
     * @return CartesianVector between the point that called and the input target.
     */
    public CartesianVector vecTo(CartesianDouble target)
    {
        double dX = target.getX() - this.getX();
        double dY = target.getY() - this.getY();

        return new CartesianVector(dX, dY);
    }

    public CartesianVector toCartesianVector()
    {
        return new CartesianVector(this.getX(), this.getY());
    }
}