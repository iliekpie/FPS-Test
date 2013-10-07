package iliekpie.FPSTest;

import iliekpie.FPSTest.Entities.Cube;
import iliekpie.FPSTest.Helpers.FPSCamera;
import iliekpie.FPSTest.Helpers.FPSCameraController;
import iliekpie.FPSTest.Helpers.FPSCounter;
import iliekpie.FPSTest.Helpers.LogicManager;
import iliekpie.OpenGLHelpers.MatrixUtils;
import iliekpie.OpenGLHelpers.ShaderProgram;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class FPSTest {
    public static void main(String... args) {
        new FPSTest();
    }
    private final FPSCounter fpsCounter = new FPSCounter();

    //Window constants
    private static final String WINDOW_TITLE = "FPS Test";
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final float fov = 79.0f;

    //Shader-related variables
    private ShaderProgram shaderProgram = null;

    //Entities
    private Cube[] cubeArray = null;

    //Projection-related variables
    private FPSCamera camera = null;
    private Matrix4f projectionMatrix = null;
    private Matrix4f mvpMatrix = null;
    private boolean dirty = true;

    //Input
    private FPSCameraController controller = null;

    public FPSTest() {
        setupOpenGL();
        setupShaders();
        setupProjection();
        setupCubes();
        setupInput();

        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            if (Display.wasResized()) {
                reshapeDisplay(Display.getWidth(), Display.getHeight());
            }

            logicCycle();
            displayCycle();

            fpsCounter.updateFPS();
            //Display.sync(60);
            Display.update();
        }

        destroyOpenGL();
    }

    private void setupOpenGL() {
        //Setup an OpenGL context of version 3.2
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAttribs = new ContextAttribs(3, 1); //temp fix for hardware limitation: need to update drivers
            /*        .withForwardCompatible(true)
                    .withProfileCore(true);*/

            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle(WINDOW_TITLE);
            Display.setVSyncEnabled(true);
            Display.setResizable(true);
            Display.create(pixelFormat, contextAttribs);

            reshapeDisplay(WIDTH, HEIGHT);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        //Set the background color
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0.0f);

        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void setupCubes() {
        /*cubeArray = new Cube[1000];
        for(int x=0; x<10; x++) {
            for(int y=0; y<10; y++) {
                for(int z=0; z<10; z++) {
                    cubeArray[x*100+y*10+z] = new Cube(new Vector3f(
                            (x-5)*2,
                            (y-5)*2,
                            (z-5)*2
                    ));
                    cubeArray[x*100+y*10+z].bind(shaderProgram);
                }
            }
        }*/
        cubeArray = new Cube[2];
        cubeArray[0] = new Cube(new Vector3f(0f, 0f, 0f));
        cubeArray[1] = new Cube(new Vector3f(2f, 0f, 0f));

        for (Cube cube : cubeArray) {
            cube.bind(shaderProgram);
        }
    }

    private void setupShaders(){
        //Create a map of attributes for the shader program.
        Map<Integer, String> attributes = new HashMap<Integer, String>() {
            {
                put(0, "in_Position");
                put(1, "in_Color");
            }
        };

        try {
            shaderProgram = new ShaderProgram(
                    "src/iliekpie/FPSTest/Shaders/BasicProjection.vert",
                    "src/iliekpie/FPSTest/Shaders/BasicProjection.frag",
                    attributes
            );
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void setupProjection() {
        //projectionMatrix = MatrixUtils.getPerspectiveMatrix(fov, (float) (WIDTH / HEIGHT), 0.1f, 100.0f);
        camera = new FPSCamera(new Vector3f(0.5f, 0.5f, 2.0f));

        Mouse.setGrabbed(true);
    }

    private void setupInput() {
        controller = new FPSCameraController();
        controller.bind(camera);
    }

    private void reshapeDisplay(int width, int height) {
        //Sizes the drawing area and projection matrix to the new display size.
        projectionMatrix = MatrixUtils.getPerspectiveMatrix(fov, (float) width / (float) height, 0.1f, 100.0f);
        GL11.glViewport(0, 0, width, height);
    }

    private void displayCycle() {
        //Clear the color and depth buffers.
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //Set the shader program as the currently used one.
        shaderProgram.use();

        for(Cube cube : cubeArray) {
            //Uniforms have to be set after the program is set - maybe use a queue or stack?
            shaderProgram.setUniformMatrix(
                    shaderProgram.getUniformLocation("MVP"),
                    false,
                    MatrixUtils.multiplyMVP(cube.getPositionMatrix(), camera.getViewMatrix(), projectionMatrix)
            );
            cube.draw();
        }

        //Reset everything (deselect)
        shaderProgram.disable();
    }

    private void logicCycle() {
        controller.handleInput();

        for(Cube cube : cubeArray) {
            cube.tick();
        }

        //Create a new MVP matrix in case of movement - check if dirty before changing?
        //mvpMatrix = MatrixUtils.multiplyMVP(, camera.getViewMatrix(), projectionMatrix);
    }

    private void destroyOpenGL(){
        //Delete the shader program and models
        for(Cube cube : cubeArray) {
            cube.destroy();
        }
        shaderProgram.destroy();

        Display.destroy();
    }
}
