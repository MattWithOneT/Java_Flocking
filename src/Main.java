import javax.swing.*;


/**
 * Created by Y1475945.
 */
public class Main{
    public static SimulationFrame mySim;

    public static void main(String[] args)
    {
        mySim = new SimulationFrame();
        SwingUtilities.invokeLater(mySim);
    }

}
