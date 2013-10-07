package iliekpie.FPSTest.Helpers;

import iliekpie.OpenGLHelpers.MatrixUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Stack;

public class FPSCamera {
    private Vector3f position = null;
    private float yaw = 0.0f;       //Yaw is the rotation around the Y axis AKA left/right
    private float pitch = 0.0f;     //Pitch is the rotation around the X axis AKA up/down

    /**
     * Create a new First Person camera at a given location
     * @param position The beginning position
     */
    public FPSCamera(Vector3f position) {
        this.position = position;
    }

    /**
     * Alter the camera's yaw
     * @param deltaYaw Amount to add to yaw
     */
    public void yaw(float deltaYaw) {
        yaw += deltaYaw;
        //Keep yaw within -180 and 180 - reduce precision errors
        if (yaw < -180.0f) {
            yaw += 360.0f;
        }
        if (yaw > 180.0f) {
            yaw -= 360.0f;
        }
    }

    /**
     * Alter the camera'a pitch
     * @param deltaPitch Amount to add to pitch
     */
    public void pitch(float deltaPitch) {
        pitch += deltaPitch;
        //Lock pitch to up and down.
        if (pitch < -90.0f) {
            pitch = -90.0f;
        }
        if (pitch > 90.0f) {
            pitch = 90.0f;
        }
    }

    /**
     * Move forward (based on yaw and pitch)
     * @param distance The distance to move
     */
    public void moveForward(float distance) {
        Vector3f components = VectorUtils.breakComponents(distance, yaw, pitch);
        position.x += components.x;
        position.y += -components.y;
        position.z += -components.z;
    }

    /**
     * Move backward (based on yaw and pitch)
     * @param distance The distance to move
     */
    public void moveBackward(float distance) {
        Vector3f components = VectorUtils.breakComponents(distance, yaw, pitch);
        position.x += -components.x;
        position.y += components.y;
        position.z += components.z;
    }

    /**
     * Move left (based on yaw)
     * @param distance The distance to move
     */
    public void moveLeft(float distance) {
        Vector2f components = VectorUtils.breakComponents(distance, yaw);
        position.x += -components.x;
        position.z += -components.y;
    }

    /**
     * Move right (based on yaw).
     * @param distance The distance to move
     */
    public void moveRight(float distance) {
        Vector2f components = VectorUtils.breakComponents(distance, yaw);
        position.x += components.x;
        position.z += components.y;
    }

    /**
     * Returns a view matrix from the camera's viewpoint
     * @return Matrix4f The view matrix
     */
    public Matrix4f getViewMatrix() {
        return MatrixUtils.applyTranslations(position, new Vector3f(pitch, yaw, 0.0f));
    }
}
