package iliekpie.FPSTest.Entities;

import iliekpie.OpenGLHelpers.Color;

public enum BlockType {
    AIR(0, new Color(0.0f, 0.0f, 0.0f, 0.0f)),
    TEST(1, new Color(0, 128, 64));

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
