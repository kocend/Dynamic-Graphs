package uksw;

import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSinkImages;

import java.io.IOException;

public class Tools {

    public final static SingleGraph read(String filename) {
        SingleGraph graph = new SingleGraph("graph");
        try {
            graph.read(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GraphParseException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public final static void write(SingleGraph graph, String filename) {
        try {
            graph.write(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final static void screenshot(SingleGraph graph, String filename) {
        if (graph != null) if (graph.getNodeCount() > 0) {
            FileSinkImages fsi = new FileSinkImages(
                    FileSinkImages.OutputType.PNG,
                    FileSinkImages.Resolutions.SVGA);
            fsi.setLayoutPolicy(
                    FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
            try {
                fsi.writeAll(graph, filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
