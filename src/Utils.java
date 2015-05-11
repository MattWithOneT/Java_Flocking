public class Utils {
    public static void pause(int time) {
        try{
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // Pass silently
        }
    }
}
