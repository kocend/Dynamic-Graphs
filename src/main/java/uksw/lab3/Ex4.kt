package uksw.lab3

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import kotlin.math.abs
import kotlin.math.floor

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")

    val n = 10000
    val tree = SingleGraph("tree")
    tree.addNode<Node>("initialNode")

    for( i in 0 until n ) {
        val node = tree.addNode<Node>("node$i")
        val value = abs(floor((i.toDouble()/n) * 255).toInt() - 255)
        node.addAttribute("ui.style", "fill-color: rgb($value,0,0);")
        val nodeSet = tree.getNodeSet<Node>().toList().filter { it != node }
        val connectedTo = nodeSet.random()
        tree.addEdge<Edge>("${node.id}<->${connectedTo.id}", node.id, connectedTo.id)
    }

    tree.display(true)
}
