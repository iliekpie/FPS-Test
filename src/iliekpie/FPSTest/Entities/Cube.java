package iliekpie.FPSTest.Entities;

import iliekpie.FPSTest.Helpers.Drawable;
import iliekpie.OpenGLHelpers.MatrixUtils;
import iliekpie.OpenGLHelpers.Vertex;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Cube extends Drawable {
    //Position & Rotation
    //TODO: use matrix for position and quaternion for orientation?

    private Matrix4f modelMatrix = new Matrix4f();
    private boolean dirty = true;

    /**
     * A default cube for OpenGL testing.
     */
    public Cube(Vector3f initialPosition, Vector3f initialRotation) {
        fillBuffers();
        modelMatrix = MatrixUtils.getLocalTransformationMatrix(initialPosition, initialRotation);
    }

    public Cube(Vector3f initialPosition) {
        this(initialPosition, new Vector3f());
    }

    private void fillBuffers() {
        //Vertices for a box
        Vertex v0 = new Vertex(); v0.setXYZ(-0.5f, -0.5f,  0.5f); v0.setRGB(1.0f, 0.0f, 0.0f);
        Vertex v1 = new Vertex(); v1.setXYZ( 0.5f, -0.5f,  0.5f); v1.setRGB(0.0f, 1.0f, 0.0f);
        Vertex v2 = new Vertex(); v2.setXYZ( 0.5f,  0.5f,  0.5f); v2.setRGB(0.0f, 0.0f, 1.0f);
        Vertex v3 = new Vertex(); v3.setXYZ(-0.5f,  0.5f,  0.5f); v3.setRGB(1.0f, 1.0f, 1.0f);

        Vertex v4 = new Vertex(); v4.setXYZ(-0.5f, -0.5f, -0.5f); v4.setRGB(1.0f, 0.0f, 0.0f);
        Vertex v5 = new Vertex(); v5.setXYZ( 0.5f, -0.5f, -0.5f); v5.setRGB(0.0f, 1.0f, 0.0f);
        Vertex v6 = new Vertex(); v6.setXYZ( 0.5f,  0.5f, -0.5f); v6.setRGB(0.0f, 0.0f, 1.0f);
        Vertex v7 = new Vertex(); v7.setXYZ(-0.5f,  0.5f, -0.5f); v7.setRGB(1.0f, 1.0f, 1.0f);

        Vertex[] vertices = {v0, v1, v2, v3, v4, v5, v6, v7};

        //Indices for a box
        byte[] indices = {
                //Front quad
                0, 1, 2,
                2, 3, 0,
                //Top quad
                3, 2, 6,
                6, 7 ,3,
                //Back quad
                7, 6, 5,
                5, 4, 7,
                //Bottom quad
                4, 5, 1,
                1, 0, 4,
                //Left quad
                4, 0, 3,
                3, 7, 4,
                //Right quad
                1, 5, 6,
                6, 2, 1
        };

        fillVertexBuffer(vertices);
        fillIndexBuffer(indices);
    }

    /**
     * Ticks the cube to allow for logic
     */
    public void tick() {
        dirty = true;
        modelMatrix.rotate(0.05f, new Vector3f(0f, 1f, 0f));

        //System.out.println(position.toString() + "/" + rotation.toString());
    }

    /**
     * Returns the cube's position as a matrix
     * @return Matrix4f The model matrix
     */
    public Matrix4f getPositionMatrix() {
        if (!dirty) return modelMatrix;
        dirty = false;
        //modelMatrix = MatrixUtils.getLocalTransformationMatrix(position, rotation);
        return modelMatrix;
    }
}
