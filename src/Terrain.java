/**
 * Created by Y1475945.
 */
public abstract class Terrain extends Env_Entity
{
    public Terrain(Canvas canvas, CartesianDouble position)
    {
        super(canvas, position);
    }

    public Terrain(Canvas canvas, CartesianDouble position, int size)
    {
        super(canvas, position, size);
    }
    public Terrain(Canvas canvas)
    {
        super(canvas);
    }
}
