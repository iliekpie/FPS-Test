package iliekpie.FPSTest.World;

import iliekpie.FPSTest.Entities.BlockType;
import iliekpie.FPSTest.Entities.Block;
import iliekpie.FPSTest.Graphics.Mesh;
import iliekpie.OpenGLHelpers.Color;
import iliekpie.OpenGLHelpers.Vertex;
import org.lwjgl.util.vector.Vector3f;

public class Chunk {
    private static final int CHUNK_SIZE = 8;
    private int[] position = new int[3];
    private Block[][][] blocks;
    private Mesh mesh;

    public Chunk(int x, int y, int z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
        createBlocks();
    }

    public int[] getPosition() {
        return position;
    }

    public Vector3f getPositionVector() {
        return new Vector3f(
                (float)(position[0]*CHUNK_SIZE),
                (float)(position[1]*CHUNK_SIZE),
                (float)(position[2]*CHUNK_SIZE)
        );
    }

    private void createBlocks() {
        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for(int x = 0; x < CHUNK_SIZE; x++) {
            for(int y = 0; y < CHUNK_SIZE; y++) {
                for(int z = 0; z < CHUNK_SIZE; z++) {
                    blocks[x][y][z] = new Block();
                }
            }
        }
    }

    //temp testing function
    public void populate() {
        for(int x = 0; x < CHUNK_SIZE; x++) {
            for(int y = 0; y < CHUNK_SIZE; y++) {
                for(int z = 0; z < CHUNK_SIZE; z++) {
                    /*if (Math.sqrt((double) (x - CHUNK_SIZE / 2) * (x - CHUNK_SIZE / 2) + (y - CHUNK_SIZE / 2) * (y - CHUNK_SIZE / 2) + (z - CHUNK_SIZE / 2) * (z - CHUNK_SIZE / 2)) <= CHUNK_SIZE/2) {
                        blocks[x][y][z].setActive(true);
                        blocks[x][y][z].setBlockType(BlockType.TEST);
                    }*/
                    blocks[x][y][z].setActive(true);
                    blocks[x][y][z].setBlockType(BlockType.TEST);
                }
            }
        }
    }

    public Mesh getMesh() {
        if(mesh == null) createMesh();
        return mesh;
    }

    private void createMesh() {
        mesh = new Mesh();
        final boolean defaultDraw = true;
        for(int x = 0; x < CHUNK_SIZE; x++) {
            for(int y = 0; y < CHUNK_SIZE; y++) {
                for(int z = 0; z < CHUNK_SIZE; z++) {
                    if(blocks[x][y][z].isActive()) {
                        boolean drawFront = defaultDraw;
                        boolean drawBack  = defaultDraw;

                        boolean drawLeft = defaultDraw;
                        boolean drawRight = defaultDraw;

                        boolean drawTop = defaultDraw;
                        boolean drawBottom = defaultDraw;

                        if(x > 0) drawLeft = !blocks[x-1][y][z].isActive();
                        if(x < CHUNK_SIZE - 1) drawRight = !blocks[x+1][y][z].isActive();

                        if(y > 0) drawBottom = !blocks[x][y-1][z].isActive();
                        if(y < CHUNK_SIZE - 1) drawTop = !blocks[x][y+1][z].isActive();

                        if(z > 0) drawBack = !blocks[x][y][z-1].isActive();
                        if(z < CHUNK_SIZE - 1) drawFront = !blocks[x][y][z+1].isActive();

                        createCube(x, y, z, blocks[x][y][z].getBlockType().getColor(),
                                drawFront, drawBack, drawRight, drawLeft, drawTop, drawBottom);
                    }
                }
            }
        }
    }

    private void createCube(int x, int y, int z, Color color, boolean drawFront, boolean drawBack, boolean drawRight, boolean drawLeft, boolean drawTop, boolean drawBottom) {
        /*Vertex v0 = new Vertex(); v0.setXYZ(x-Block.RENDER_SIZE, y-Block.RENDER_SIZE, z+Block.RENDER_SIZE); v0.setColor(color);
        Vertex v1 = new Vertex(); v1.setXYZ(x+Block.RENDER_SIZE, y-Block.RENDER_SIZE, z+Block.RENDER_SIZE); v1.setColor(color);
        Vertex v2 = new Vertex(); v2.setXYZ(x+Block.RENDER_SIZE, y+Block.RENDER_SIZE, z+Block.RENDER_SIZE); v2.setColor(color);
        Vertex v3 = new Vertex(); v3.setXYZ(x-Block.RENDER_SIZE, y+Block.RENDER_SIZE, z+Block.RENDER_SIZE); v3.setColor(color);
        Vertex v4 = new Vertex(); v4.setXYZ(x+Block.RENDER_SIZE, y-Block.RENDER_SIZE, z-Block.RENDER_SIZE); v4.setColor(color);
        Vertex v5 = new Vertex(); v5.setXYZ(x-Block.RENDER_SIZE, y-Block.RENDER_SIZE, z-Block.RENDER_SIZE); v5.setColor(color);
        Vertex v6 = new Vertex(); v6.setXYZ(x-Block.RENDER_SIZE, y+Block.RENDER_SIZE, z-Block.RENDER_SIZE); v6.setColor(color);
        Vertex v7 = new Vertex(); v7.setXYZ(x+Block.RENDER_SIZE, y+Block.RENDER_SIZE, z-Block.RENDER_SIZE); v7.setColor(color);*/

        Vertex v0 = new Vertex(); v0.setXYZ(x-Block.RENDER_SIZE, y-Block.RENDER_SIZE, z+Block.RENDER_SIZE); v0.setRGB(1.0f, 0.0f, 0.0f);
        Vertex v1 = new Vertex(); v1.setXYZ(x+Block.RENDER_SIZE, y-Block.RENDER_SIZE, z+Block.RENDER_SIZE); v1.setRGB(0.0f, 1.0f, 0.0f);
        Vertex v2 = new Vertex(); v2.setXYZ(x+Block.RENDER_SIZE, y+Block.RENDER_SIZE, z+Block.RENDER_SIZE); v2.setRGB(0.0f, 0.0f, 1.0f);
        Vertex v3 = new Vertex(); v3.setXYZ(x-Block.RENDER_SIZE, y+Block.RENDER_SIZE, z+Block.RENDER_SIZE); v3.setRGB(1.0f, 1.0f, 1.0f);

        Vertex v4 = new Vertex(); v4.setXYZ(x+Block.RENDER_SIZE, y-Block.RENDER_SIZE, z-Block.RENDER_SIZE); v4.setRGB(1.0f, 0.0f, 0.0f);
        Vertex v5 = new Vertex(); v5.setXYZ(x-Block.RENDER_SIZE, y-Block.RENDER_SIZE, z-Block.RENDER_SIZE); v5.setRGB(0.0f, 1.0f, 0.0f);
        Vertex v6 = new Vertex(); v6.setXYZ(x-Block.RENDER_SIZE, y+Block.RENDER_SIZE, z-Block.RENDER_SIZE); v6.setRGB(0.0f, 0.0f, 1.0f);
        Vertex v7 = new Vertex(); v7.setXYZ(x+Block.RENDER_SIZE, y+Block.RENDER_SIZE, z-Block.RENDER_SIZE); v7.setRGB(1.0f, 1.0f, 1.0f);

        short[] vertices = {-1, -1, -1, -1, -1, -1, -1, -1};


        //front
        if(drawFront) {
            vertices[0] = mesh.addVertex(v0);
            vertices[1] = mesh.addVertex(v1);
            vertices[2] = mesh.addVertex(v2);
            vertices[3] = mesh.addVertex(v3);
            mesh.addTriangle(vertices[0], vertices[1], vertices[2]);
            mesh.addTriangle(vertices[0], vertices[2], vertices[3]);
        }

        //back
        if(drawBack) {
            vertices[4] = mesh.addVertex(v4);
            vertices[5] = mesh.addVertex(v5);
            vertices[6] = mesh.addVertex(v6);
            vertices[7] = mesh.addVertex(v7);
            mesh.addTriangle(vertices[4], vertices[5], vertices[6]);
            mesh.addTriangle(vertices[4], vertices[6], vertices[7]);
        }

        //right
        if(drawRight) {
            if(vertices[1] == -1) {
                vertices[1] = mesh.addVertex(v1);
                vertices[2] = mesh.addVertex(v2);
            }
            if(vertices[4] == -1) {
                vertices[4] = mesh.addVertex(v4);
                vertices[7] = mesh.addVertex(v7);
            }
            mesh.addTriangle(vertices[1], vertices[4], vertices[7]);
            mesh.addTriangle(vertices[1], vertices[7], vertices[2]);
        }

        //left
        if(drawLeft) {
            if(vertices[0] == -1) {
                vertices[0] = mesh.addVertex(v0);
                vertices[3] = mesh.addVertex(v3);
            }
            if(vertices[5] == -1) {
                vertices[5] = mesh.addVertex(v5);
                vertices[6] = mesh.addVertex(v6);
            }
            mesh.addTriangle(vertices[5], vertices[0], vertices[3]);
            mesh.addTriangle(vertices[5], vertices[3], vertices[6]);
        }

        //top
        if(drawTop) {
            if(vertices[2] == -1) vertices[2] = mesh.addVertex(v2);
            if(vertices[3] == -1) vertices[3] = mesh.addVertex(v3);
            if(vertices[6] == -1) vertices[6] = mesh.addVertex(v6);
            if(vertices[7] == -1) vertices[7] = mesh.addVertex(v7);
            mesh.addTriangle(vertices[3], vertices[2], vertices[7]);
            mesh.addTriangle(vertices[3], vertices[7], vertices[6]);
        }

        //bottom
        if(drawBottom) {
            if(vertices[0] == -1) vertices[0] = mesh.addVertex(v0);
            if(vertices[1] == -1) vertices[1] = mesh.addVertex(v1);
            if(vertices[4] == -1) vertices[4] = mesh.addVertex(v4);
            if(vertices[5] == -1) vertices[5] = mesh.addVertex(v5);
            mesh.addTriangle(vertices[5], vertices[4], vertices[1]);
            mesh.addTriangle(vertices[5], vertices[1], vertices[0]);
        }
    }
}
