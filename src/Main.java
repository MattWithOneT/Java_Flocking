import java.util.ArrayList;

/**
 * Created by Y1475945.
 */
public class Main {
    public static ArrayList<Env_Entity> myEntities = new ArrayList<>();
    public static Canvas myUniverse;
    public static int maxPop = 75;

    public static void main(String[] args)
    {
        myUniverse = new Canvas(1080, 720);


        for(int i = 0; i < 75; i++)
        {
            myEntities.add(new Bird(myUniverse));
        }

        for(int i = 0; i < 2; i++)
        {
            myEntities.add(new Predator(myUniverse));
        }

//        for(int i = 200; i < 300; i++)
//        {
//            myEntities.add(new Wall(myUniverse, new CartesianDouble(i, 200)));
//        }

        for(int i = 0; i < 3; i++)
        {
            myEntities.add(new Obstacle(myUniverse, 30));
        }

        Lifeform.setWorldInhabitants(myEntities);

        while(true)
        {

            myUniverse.clear();
            Lifeform.cleanUpDead();

            if((int)Math.round(Math.random() * 100) == 1 && myEntities.size() < maxPop)
            {
                myEntities.add(new Bird(myUniverse));
            }

            if((int)Math.round(Math.random() * 2000) == 1 && myEntities.size() < maxPop)
            {
                myEntities.add(new Predator(myUniverse));
            }

            for(Env_Entity entity : myEntities)
            {
                entity.nextFrame();
            }

            myUniverse.repaintCanvas();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
