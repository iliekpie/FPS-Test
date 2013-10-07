package iliekpie.FPSTest.Helpers;

import org.lwjgl.Sys;

public class LogicManager {
    //All time-based variables are in ms.
    final private int timestep = 10;
    private long resolution = Sys.getTimerResolution();

    private long lastCheck = 0L;
    private long accumulator = 0L;

    public LogicManager() {

    }

    public void update() {
        long currentTime = Sys.getTime();
        long frameTime = (currentTime - lastCheck)*1000/resolution;

        if (frameTime > 250) {
            frameTime = 250;
        }

        lastCheck = currentTime;
        accumulator += frameTime;

        while (accumulator >= timestep) {
            //todo: implement logic
            accumulator -= timestep;
        }

        //todo: state alpha
    }
}
