package iliekpie.FPSTest.World;

import iliekpie.FPSTest.Graphics.ChunkRenderer;
import iliekpie.OpenGLHelpers.MatrixUtils;
import iliekpie.OpenGLHelpers.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChunkManager {
    private Map<ChunkRenderer, Chunk> loadedChunks = new HashMap<ChunkRenderer, Chunk>();
    private ShaderProgram program;

    public ChunkManager() {

    }

    public void populateChunkList(int size) {
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                for(int z = 0; z < size; z++) {
                    Chunk chunk = new Chunk(x, y, z);
                    chunk.populate();
                    loadedChunks.put(new ChunkRenderer(chunk.getMesh()), chunk);
                }
            }
        }
    }

    public void bindRenderers(ShaderProgram shaderProgram) {
        program = shaderProgram;

        for(ChunkRenderer renderer : loadedChunks.keySet()) {
            renderer.bind(shaderProgram);
        }
    }

    public void renderChunks(Matrix4f viewMatrix, Matrix4f projectionMatrix) {

        for(ChunkRenderer renderer : loadedChunks.keySet()) {
            program.setUniformMatrix(
                    program.getUniformLocation("MVP"),
                    false,
                    MatrixUtils.multiplyMVP(MatrixUtils.getLocalTransformationMatrix(
                            loadedChunks.get(renderer).getPositionVector(),
                            new Vector3f()
                    ), viewMatrix, projectionMatrix)
            );

            renderer.draw();
        }
    }

    public void destroy() {
        for(ChunkRenderer renderer : loadedChunks.keySet()) {
            renderer.destroy();
        }
    }
}
