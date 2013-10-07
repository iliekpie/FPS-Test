package iliekpie.FPSTest.Helpers;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class FPSCounter {
    private long lastFPSCheck = 0L;
    private int frames = 0;

    public FPSCounter() {
        lastFPSCheck = Sys.getTime();
    }

    public void updateFPS() {
        if (lastFPSCheck == 0L) {
            lastFPSCheck = Sys.getTime();
        }
        if ((Sys.getTime() - lastFPSCheck) > 1000L) {
            Display.setTitle("FPS: " + frames);
            frames = 0;
            lastFPSCheck += 1000L;
        }
        frames++;
    }

}
