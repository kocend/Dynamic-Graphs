package uksw.lab2

import org.graphstream.graph.Node
import uksw.Tools

private const val STYLE = "fill-color: black;"

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = Tools.read("src/main/resources/dgs/completegrid_10.dgs")
    graph.display(true)

    val vertex = graph.getNodeSet<Node>().random()
    bfs(vertex)
    println("bfs finished")
}

fun bfs(node: Node){
    node.markAsVisitedAndApplyStyle(STYLE)
    Thread.sleep(500)
    val unvisitedNeighbours = node.getUnvisitedNeighbours()
    unvisitedNeighbours.forEach { it.markAsVisitedAndApplyStyle(STYLE) }
    unvisitedNeighbours.forEach { bfs(it) }
}
