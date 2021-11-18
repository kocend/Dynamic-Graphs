package uksw.lab2

import org.graphstream.graph.Node
import uksw.Tools

private const val STYLE = ("size: 30px; fill-color: red;")

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = Tools.read("src/main/resources/dgs/firstgraphlab2.dgs")

    val averageDegree = graph.getNodeSet<Node>().map { it.degree }.average()
    println("Average degree: $averageDegree")

    val parameter = 40
    graph.getNodeSet<Node>().forEach {
        val cost = it.getNeighborNodeIterator<Node>().asSequence().sumOf { it.getAttribute("cost") as Int }

        if(cost > parameter){
            println("if")
            it.addAttribute("ui.style", STYLE)
        }
    }

    graph.display(true)
}
