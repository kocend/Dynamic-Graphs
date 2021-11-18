package uksw;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

import java.io.IOException;

public class Main {

    private static String STYLESHEET =
            "graph { fill-color: blue; padding: 40px; }"
                    + "node { size: 10px; shape: box; fill-color: red; }"
                    + "edge { size : 2px; fill-color: black; }";

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        SingleGraph graph = new SingleGraph("graph");

        //Ex 1, 2
//        Node nA = graph.addNode("A");
//        nA.addAttribute("ui.label", "node A");
//        nA.addAttribute("x", 50);
//        nA.addAttribute("y", 50);
//
//        Node nB = graph.addNode("B");
//        nB.addAttribute("ui.label", "node B");
//        nB.addAttribute("x", 50);
//        nB.addAttribute("y", 100);
//
//        Node nC = graph.addNode("C");
//        nC.addAttribute("ui.label", "node C");
//        nC.addAttribute("x", 50);
//        nC.addAttribute("y", 150);
//
//        Node nD = graph.addNode("D");
//        nD.addAttribute("ui.label", "node D");
//        nD.addAttribute("x", 100);
//        nD.addAttribute("y", 150);
//
//        graph.addEdge("A--B", nA.getId(), nB.getId());
//        graph.addEdge("B--C", nB.getId(), nC.getId());
//        graph.addEdge("C--D", nC.getId(), nD.getId());
//        graph.addEdge("D--A", nD.getId(), nA.getId());
//
//        String shortStylesheet = "graph { fill-color: lightblue; "
//                + "padding: 40px; }";
//        graph.addAttribute("ui.stylesheet", shortStylesheet);
//        String nodeStyle = "fill-color:#fafabb; shape:cross;"
//                + "size: 30px;";
//        Node n = Toolkit.randomNode(graph);
//        n.addAttribute("ui.style", nodeStyle);
//        graph.changeAttribute("ui.quality", true);
//        graph.changeAttribute("ui.antialias", true);
//        Tools.write(graph, "src/main/resources/dgs/mygraph.dgs");

        //Ex 3
//        graph = Tools.read("src/main/resources/dgs/dgsfile.dgs");
//        graph.addNode("XYZ1");
//        graph.addNode("XYZ2");
//        graph.addEdge("A--XYZ1", "A", "XYZ1");
//        graph.addEdge("C--XYZ1", "C", "XYZ2");
//        graph.changeAttribute("ui.stylesheet", STYLESHEET);
//        Tools.write(graph, "src/main/resources/dgs/modifiedgraph.dgs");
//        Tools.screenshot(graph, "src/main/resources/pictures/graphbeforemodifications.png");

        //Ex 4
        //src/main/java/uksw/Tools.java

        graph.display(true);
    }
}
