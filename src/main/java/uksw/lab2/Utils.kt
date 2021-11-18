package uksw.lab2

import org.graphstream.graph.Edge
import org.graphstream.graph.Node

@Synchronized
fun Node.getUnvisitedNeighbours() =
    this.getNeighborNodeIterator<Node>().asSequence().toList()
        .filter { it.getAttribute("visited") as Boolean? == null }

@Synchronized
fun Node.markAsVisitedAndApplyStyle(style: String) {
    this.addAttribute("visited", true)
    this.addAttribute("ui.style", style)
}

@Synchronized
fun Edge.applyStyle(style: String) = this.addAttribute("ui.style", style)

@Synchronized
fun Node.getEdgeLeadingTo(node: Node): Edge {
    val thisNodeEdges = this.getEachEdge<Edge>().toList()
    val nodeEdges = node.getEachEdge<Edge>().toList()
    val commonEdge = (thisNodeEdges + nodeEdges)
                        .groupBy { it.id }
                        .filter {
                            it.value.size > 1
                        }
                        .values
                        .first()
                        .first()
    return commonEdge
}
