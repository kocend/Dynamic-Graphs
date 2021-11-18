package uksw.lab2

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import uksw.Tools

private const val DISTANCE = "distance"
private const val ECCENTRICITY = "eccentricity"

fun main(){
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = Tools.read("src/main/resources/dgs/gridvaluated_30_1_20.dgs")
    val nodes = graph.getNodeSet<Node>()

    nodes.parallelStream().forEach { node ->
        val tempGraph = Tools.read("src/main/resources/dgs/gridvaluated_30_1_20.dgs")
        tempGraph.forEach { it.addAttribute(DISTANCE, Int.MAX_VALUE) }
        val tempNode = tempGraph.find { it.id == node.id }!!
        tempNode.addAttribute(DISTANCE, 0)
        dijkstra(tempNode!!, LinkedHashSet())
        tempGraph.removeNode<Node>(tempNode)
        val sorted = tempGraph.getNodeSet<Node>().sortedByDescending { it.getAttribute<Int>(DISTANCE) }
        node.addAttribute(ECCENTRICITY, sorted.first().getAttribute(DISTANCE))
    }

    val eccentricities = nodes.map { it.getAttribute<Int>(ECCENTRICITY) }
    val diameter = eccentricities.maxOrNull()!!
    val radious = eccentricities.minOrNull()!!

    nodes.forEach { paint(it, radious, diameter) }
    nodes.forEach { it.removeAttribute("ui.label") }
    graph.getEdgeSet<Edge>().forEach { it.removeAttribute("ui.label") }

    graph.display(true)
}

fun paint(node: Node, radious: Int, diameter: Int) {

    val eccentricity = node.getAttribute<Int>(ECCENTRICITY) ?: 0
    val range = (diameter - radious)
    val demiRange = range / 2
    val midpoint = demiRange + radious
    val scale = if(demiRange < 255)
                    255.0 / demiRange
                else
                    demiRange / 255.0

    if(eccentricity < midpoint){
        val excess = eccentricity - radious
        val value = Math.round(excess * scale)
        if(value < 0 || value > 255){
            println("")
        }
        node.addAttribute("ui.style", "size: 20px; fill-color: rgb(0,0,$value);")
    }
    else{
        val excess = eccentricity - midpoint
        val value = Math.round(excess * scale)
        if(value < 0 || value > 255){
            println("")
        }
        node.addAttribute("ui.style", "size: 20px; fill-color: rgb($value,0,0);")
    }
}
