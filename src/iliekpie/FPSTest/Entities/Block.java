package iliekpie.FPSTest.Entities;

public class Block {
    public static final float RENDER_SIZE = 0.5f;

    private BlockType blockType = BlockType.AIR;
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
