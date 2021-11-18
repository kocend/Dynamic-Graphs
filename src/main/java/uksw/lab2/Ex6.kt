package uksw.lab2

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import uksw.Tools

private const val STYLE = "fill-color: black;"

fun main(){
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = Tools.read("src/main/resources/dgs/gridvaluated_30_1_20.dgs")
    val nodes = graph.getNodeSet<Node>()
    nodes.forEach { it.removeAttribute("ui.label") }
    graph.getEdgeSet<Edge>().forEach { it.removeAttribute("ui.label") }
    graph.display(true)

    nodes.parallelStream().forEach(::spanning)
    println("finished!")
}

fun spanning(node: Node){
    node.markAsVisitedAndApplyStyle(STYLE)
    val unvisitedNeighbours = node.getUnvisitedNeighbours()
    unvisitedNeighbours.forEach {
        it.getEdgeLeadingTo(node).applyStyle(STYLE)
        it.markAsVisitedAndApplyStyle(STYLE)
    }
    Thread.sleep(500)
    unvisitedNeighbours.forEach { spanning(it) }
}
