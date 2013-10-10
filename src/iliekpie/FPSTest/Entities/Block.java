package iliekpie.FPSTest.Entities;

import iliekpie.FPSTest.BlockType.BlockType;

public class Block {
    private BlockType blockType;
    private boolean active = false;

    public Block() {

    }

    public BlockType getBlockType() {
        return blockType;
    }

    public boolean isActive() {
        return active;
    }

    public void setBlockType(BlockType type) {
        blockType = type;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
