/**
 * Created by Y1475945.
 */
public class Obstacle extends Terrain
{
    public Obstacle(Canvas canvas, CartesianDouble position, double size)
    {
        super(canvas, position, size);
    }

    public Obstacle(Canvas canvas, double size)
    {
        super(canvas);
        this.setSize(size);
        CartesianDouble newPosition = new CartesianDouble
                (
                        Math.random() * ((canvas.getWidth() - (2 * size))) + size,
                        (Math.random() * (canvas.getHeight() - (2 * size))) + size
                );
        this.setPosition(newPosition);
    }


    @Override
    public void draw()
    {
        this.getEnvironment().drawCircle(this.getCurrentPosition(), this.getSize(), "Blue");
    }

    public void nextFrame()
    {
        this.draw();
    }


}
