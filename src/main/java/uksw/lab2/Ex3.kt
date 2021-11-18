package uksw.lab2

import org.graphstream.graph.Node
import uksw.Tools

private const val STYLE = ("fill-color: black;")

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = Tools.read("src/main/resources/dgs/uncompletegrid_50-0.12.dgs")//uncompletegrid_50-0.12.dgs
    graph.display(true)

    val vertex = graph.getNodeSet<Node>().random()
    dfs(vertex)
    println("dfs finished")
}

fun dfs(node: Node){
    node.addAttribute("visited", true)
    node.addAttribute("ui.style", STYLE)
    Thread.sleep(10)
    node.getUnvisitedNeighbours().forEach { dfs(it) }
}
