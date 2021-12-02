package uksw.lab3

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import kotlin.streams.toList

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = SingleGraph("connected graph")

    for(i in 1..10) graph.addNode<Node>("node$i")

    graph.getNodeSet<Node>().stream().toList()
        .forEachIndexed { index, node ->
            graph.getNodeSet<Node>().stream().toList()
                .forEachIndexed { index2, it ->
                    if (!node.hasEdgeBetween(it) && !node.equals(it))
                        graph.addEdge<Edge>("$index-$index2", node.index, it.index)
                }
        }

    graph.display(true)
}
