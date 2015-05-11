/**
 * Created by Y1475945.
 */
public class Obstacle extends Terrain
{
    public Obstacle(Canvas canvas, CartesianDouble position, int size)
    {
        super(canvas, position, size);
    }

    public Obstacle(Canvas canvas, int size)
    {
        super(canvas);
        this.setSize(size);
        CartesianDouble newPosition = new CartesianDouble((Math.random() * ((canvas.getWidth() - (2 * size))) - size), (Math.random() * (canvas.getHeight() - (2 * size))) - size);
        this.setPosition(newPosition);
    }

    public Obstacle(Canvas canvas)
    {
        super(canvas);
        CartesianDouble newPosition = new CartesianDouble(Math.random() * (canvas.getWidth() - this.getSize()), Math.random() * (canvas.getHeight() - this.getSize()));
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
