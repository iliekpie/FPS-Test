package iliekpie.FPSTest.Helpers;

import iliekpie.FPSTest.Graphics.FPSCamera;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class FPSCameraController {
    private long lastTime = 0L;
    private FPSCamera camera = null;
    private boolean inverted = false;

    private boolean wireframe = false;

    /**
     * Create a controller for a First Person camera
     * Allows input (Keyboard and Mouse)
     */
    public FPSCameraController() {
    }

    /**
     * Binds a camera to the controller to allow movement (Also grabs mouse).
     * @param camera Camera to be bound
     */
    public void bind(FPSCamera camera) {
        this.camera = camera;
        //Mouse.setGrabbed(true);
    }

    /**
     * Handles keyboard and mouse input and applies it to the camera.
     */
    public void handleInput() {
        //Allows ungrabbing of mouse.
        if(Mouse.isGrabbed()) handleMousePosition();
        handleMouseClick();
        handleKeyboard();
    }

    /**
     * Inverts Y Axis response: plane vs FPS
     */
    public void invertMouse() {
        inverted = !inverted;
    }

    /**
     * Returns whether the mouse is inverted
     * @return boolean Inversion state
     */
    public boolean isInverted() {
        return inverted;
    }

    private void handleMousePosition() {
        //Grabs the change in position and inverts the y axis if requested
        final float dx = Mouse.getDX();
        final float dy = Mouse.getDY() * ((inverted) ? 1 : -1);

        //Mouse sensitivity ratios (so it isn't super-sensitive if the display ratio isn't 1;1
        final float mouseSensitivity = 0.5f;
        final float yawSensitivity = (float) Display.getWidth() / Display.getHeight() * mouseSensitivity;
        final float pitchSensitivity = (float) Display.getHeight() / Display.getWidth() * mouseSensitivity;

        camera.yaw(dx * yawSensitivity);
        camera.pitch(dy * pitchSensitivity);
    }

    private void handleMouseClick() {
        //If they click on the window, grab the mouse.
        if(!Mouse.isGrabbed() && Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
        }
    }

    //Used for handling the movement
    private void handleKeyboard() {
        //todo: interpolation - queue movement?
        final float movementSpeed = 1.5f;
        final float runModifier = 2.0f;
        float distance = getDeltaTime() * movementSpeed;

        //unbuffered input
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            //Move faster.
            distance *= runModifier;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.moveForward(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.moveBackward(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camera.moveLeft(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camera.moveRight(distance);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            Mouse.setGrabbed(!Mouse.isGrabbed());
        }

        if(Keyboard.next()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
                wireframe = !wireframe;
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, (wireframe) ? GL11.GL_LINE : GL11.GL_FILL);
            }
        }
    }

    //Timer functions for frame-independent movement
    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private float getDeltaTime() {
        long currentTime = getTime();
        float delta = (float) (currentTime - lastTime);
        lastTime = currentTime;

        return (delta/1000);
    }

}
