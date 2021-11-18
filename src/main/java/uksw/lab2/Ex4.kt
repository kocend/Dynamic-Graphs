package uksw.lab2

import org.graphstream.graph.Node
import uksw.Tools

private const val FIRST_NODE_STYLE = "fill-color: green;"
private const val END_NODE_STYLE = "fill-color: red;"
private const val VISITED_NODE_STYLE = "fill-color: blue;"
private const val DISTANCE = "distance"
private const val VISITED = "visited"

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")
    val graph = Tools.read("src/main/resources/dgs/gridvaluated_10_1_2.dgs")
    graph.setAttribute("ui.quality");
    graph.setAttribute("ui.antialias");

    val startNodeId = "0-0"

    val nodes = graph.getNodeSet<Node>()

    val startNode = nodes.first { it.id == startNodeId }

    nodes.forEach { it.addAttribute(DISTANCE, Int.MAX_VALUE) }
    startNode.setAttribute(DISTANCE, 0)


    dijkstra(startNode, LinkedHashSet())


    nodes.forEach {
        it.setAttribute("ui.label", "id: ${it.id}, " +
                "dist: ${it.getAttribute<Int>("distance")}")
    }
    startNode.setAttribute("ui.style", FIRST_NODE_STYLE)

    graph.display(true)
}

fun dijkstra(currentNode: Node, queue: LinkedHashSet<Node>){

    if(currentNode.getAttribute<Boolean>(VISITED) ?: false){
        return
    }

    currentNode.addAttribute(VISITED, true)
    currentNode.addAttribute("ui.style", VISITED_NODE_STYLE)
    var neighbours = currentNode.getNeighborNodeIterator<Node>().asSequence().toList()

    neighbours.forEach { node ->
        val edge = currentNode.getEdgeLeadingTo(node)
        val visitedNeighbours = node.getNeighborNodeIterator<Node>().asSequence().toList()
            .filter { it.getAttribute<Boolean>(VISITED) ?: false }
        val nearestVisitedNeighbourDist = visitedNeighbours.minByOrNull {
            node.getEdgeLeadingTo(it)
                .getAttribute<Int>(DISTANCE) +
                    (it.getAttribute<Int>(DISTANCE) ?: 0)
        }?.getAttribute<Int>(DISTANCE) ?: 0
        val distance = edge.getAttribute(DISTANCE) as Int + nearestVisitedNeighbourDist

        if(node.getAttribute(DISTANCE) ?: 0 > distance){
            node.setAttribute(DISTANCE, distance)
        }
    }

    neighbours = currentNode.getNeighborNodeIterator<Node>().asSequence().toList()
    val notVisitedNeighbours = neighbours.filterNot { it.getAttribute(VISITED) ?: false }
    queue.addAll(notVisitedNeighbours)

    if(queue.isNotEmpty()) {
        val next = queue.first()
        queue.remove(next)
        dijkstra(next, queue)
    }
}
