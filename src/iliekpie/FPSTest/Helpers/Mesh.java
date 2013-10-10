package iliekpie.FPSTest.Helpers;

import iliekpie.OpenGLHelpers.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private List<Vertex> vertices = new ArrayList<Vertex>();
    private List<byte> indices = new ArrayList<byte>();

    public Mesh() {

    }

    public int addVertex(Vertex vertex) {
        vertices.add(vertex);
        return vertices.size()-1;
    }

    public void addTriangle(byte p3) {
        indices.add()
    }
}
