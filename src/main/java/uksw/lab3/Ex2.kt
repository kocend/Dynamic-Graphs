package uksw.lab3

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import kotlin.streams.toList

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = SingleGraph("the Erdos-Renyi random graph model")

    val n = 100
    val p = 0.05

    for(i in 1..n) graph.addNode<Node>("node$i")

    graph.getNodeSet<Node>().stream().toList()
    .forEachIndexed { index, node ->
        graph.getNodeSet<Node>().stream().toList()
        .forEachIndexed { index2, node2 ->

            if (!node.hasEdgeBetween(node2) &&
                !node.equals(node2) &&
                Math.random() < p)
                graph.addEdge<Edge>("$index-$index2", node.index, node2.index)

        }
    }

    graph.display(true)
}
