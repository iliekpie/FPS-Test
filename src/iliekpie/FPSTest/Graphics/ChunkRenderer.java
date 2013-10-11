package iliekpie.FPSTest.Graphics;

import iliekpie.OpenGLHelpers.ShaderProgram;
import iliekpie.OpenGLHelpers.Vertex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

public class ChunkRenderer {
    private ShaderProgram shaderProgram = null;

    //Vertices and Indices
    private int indexCount = -1;
    private int vertexDataObjectID = -1;
    private int indexObjectID = -1;

    //Buffers
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    public ChunkRenderer(Mesh mesh) {
        fillVertexBuffer(mesh.getVertices());
        fillIndexBuffer(mesh.getIndices());
    }

    private void fillVertexBuffer(List<Vertex> vertices) {

        //Put the vertex data into the float buffer.
        vertexBuffer = BufferUtils.createFloatBuffer(vertices.size() * Vertex.elementCount);
        for (Vertex vertex : vertices) {
            vertexBuffer.put(vertex.getXYZW());
            vertexBuffer.put(vertex.getRGBA());
        }
        vertexBuffer.flip();
    }

    private void fillIndexBuffer(short[] indices) {
        indexCount = indices.length;

        //Put the indices into a buffer for OpenGL.
        indexBuffer = BufferUtils.createShortBuffer(indexCount);
        indexBuffer.put(indices);
        indexBuffer.flip();
    }

    /**
     * Binds the object's data to the shader program.
     * @param program The active shader program
     */
    public void bind(ShaderProgram program) {
        shaderProgram = program;

        //Create and select a VBO.
        vertexDataObjectID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexDataObjectID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);

        //Put the positions in the "in_VertexPosition" attribute location.
        GL20.glVertexAttribPointer(shaderProgram.getAttributeLocation("in_VertexPosition"), 4, GL11.GL_FLOAT, false, Vertex.stride, 0);
        //Put the colors in in the "in_Color" attribute location.
        GL20.glVertexAttribPointer(shaderProgram.getAttributeLocation("in_Color"), 4, GL11.GL_FLOAT, false, Vertex.stride, Vertex.elementBytes * 4);

        //Deselect the VBO.
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        //Create a VBO for indices and select it.
        indexObjectID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexObjectID);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        //Deselect the VBO.
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Draws the object using OpenGL. It will clean itself up.
     */
    public void draw() {
        //Enable attributes (position and color)
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        //Bind to the index VBO (contains the order of the vertices)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexObjectID);

        //Draw the vertices
        GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount, GL11.GL_UNSIGNED_SHORT, 0);

        //Reset attributes and buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
    }

    /**
     * Destroys the object's attributes and buffers.
     */
    public void destroy() {
        //Disable the VBO index
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);

        //Delete the vertex VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(vertexDataObjectID);

        //Delete the index VBO
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(indexObjectID);
    }
}
