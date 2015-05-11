/**
 * Created by Y1475945.
 */
public class Wall extends Terrain
{
    private double directionAngle = 45;
    public Wall(Canvas canvas, CartesianDouble position)
    {
        super(canvas, position);
    }

    protected void draw()
    {
        this.getEnvironment().drawLineBetweenPoints(this.getCurrentPosition(), this.getCurrentPosition(), "Black");
    }

    @Override
    public void nextFrame()
    {
        this.draw();
    }
}
