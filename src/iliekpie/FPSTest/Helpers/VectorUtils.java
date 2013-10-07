package iliekpie.FPSTest.Helpers;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class VectorUtils {
    public static Vector3f breakComponents(float distance, float yaw, float pitch) {
        return new Vector3f(
                distance * (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
                distance * (float) Math.sin(Math.toRadians(pitch)),
                distance * (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))
        );
    }

    public static Vector2f breakComponents(float distance, float yaw) {
        return new Vector2f(
                distance * (float) Math.cos(Math.toRadians(yaw)),
                distance * (float) Math.sin(Math.toRadians(yaw))
        );
    }
}
