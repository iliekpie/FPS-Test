package iliekpie.FPSTest.BlockType;

import org.lwjgl.util.Color;

public enum BlockType {
    TEST(1, new Color(128, 0, 0));


    private int id;
    private Color color;

    private BlockType(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    public int getID() {
        return id;
    }

    public Color getColor() {
        return color;
    }
}
