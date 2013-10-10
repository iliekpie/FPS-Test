package iliekpie.FPSTest.Helpers;

import iliekpie.FPSTest.Entities.Block;

public class Chunk {
    private static final int CHUNK_SIZE = 32;
    private Block[][][] blocks;

    public Chunk() {
        createBlocks();
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

    public

    public void update() {

    }
}
