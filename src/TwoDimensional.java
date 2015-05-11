/**
 * Created by Y1475945.
 */
public abstract class TwoDimensional
{
    private double x, y;


    public TwoDimensional(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public TwoDimensional()
    {
        this.x = 0;
        this.y = 0;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }
}
