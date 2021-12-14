package uksw.lab4

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import uksw.Tools
import java.util.stream.Collectors
import kotlin.random.Random

private const val FILENAME = "vonNeumann.dgs"
private const val RED = "red"
private const val GREEN = "green"
private const val BLUE = "blue"

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")

    val vonNeumann = SingleGraph("ColorConsensus")
    val n = 15
    createVonNeumann(vonNeumann, n)
    vonNeumann.nodes().forEach(::paintRandomly)
    vonNeumann.display(true)
    //delay for better visual experience
//    Thread.sleep(10000)
    Tools.write(vonNeumann, FILENAME)
    do {
        vonNeumann.nodes().forEach { node ->
            val nodes = Tools.read(FILENAME).nodes()
            val nodeTemp = nodes.find { it.id == node.id }

            var red = nodeTemp?.getIntAttr(RED) ?: 0
            var green = nodeTemp?.getIntAttr(GREEN) ?: 0
            var blue = nodeTemp?.getIntAttr(BLUE) ?: 0

            nodeTemp!!.neighbours().forEach {
                red += it?.getIntAttr(RED)!!
                green += it?.getIntAttr(GREEN)!!
                blue += it?.getIntAttr(BLUE)!!
            }

            red /= (nodeTemp.neighbours().size + 1)
            green /= (nodeTemp.neighbours().size + 1)
            blue /= (nodeTemp.neighbours().size + 1)

            node.setAttribute("ui.style", "size: 50px; fill-color: rgb($red,$green,$blue);")
            node.setAttribute(RED, red)
            node.setAttribute(GREEN, green)
            node.setAttribute(BLUE, blue)
        }
        Tools.write(vonNeumann, FILENAME)
        val nodes = Tools.read(FILENAME).nodes()
        val testNode = nodes.random()
        val size = nodes.filter {
                        it.getIntAttr(RED) == testNode.getIntAttr(RED) &&
                        it.getIntAttr(GREEN) == testNode.getIntAttr(GREEN) &&
                        it.getIntAttr(BLUE) == testNode.getIntAttr(BLUE)
                     }.size
    } while (size != nodes.size)
    println("finished")
}

fun createVonNeumann(vonNeumann: SingleGraph, n: Int) {
    for( l in 1..n ) {
        for( c in 1..n ) {
            vonNeumann.addNode<Node>("$l-$c")
        }
    }

    for( l in 1..n ) {
        for( c in 1..n ) {
            if(c < n)
                vonNeumann.addEdge<Edge>("$l-$c<->$l-${c + 1}","$l-$c", "$l-${c + 1}")
            if(l < n)
                vonNeumann.addEdge<Edge>("$l-$c<->${l + 1}-$c","$l-$c", "${l + 1}-$c")
        }
    }
}

fun paintRandomly(node: Node) {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    node.addAttribute("ui.style", "size: 20px; fill-color: rgb($red,$green,$blue);")
    node.addAttribute(RED, red)
    node.addAttribute(GREEN, green)
    node.addAttribute(BLUE, blue)
}

fun SingleGraph.nodes() = this.getNodeSet<Node>().stream().collect(Collectors.toList())
fun Node.neighbours() = this.getNeighborNodeIterator<Node>().asSequence().toList()
fun Node.getIntAttr(name: String) = this.getAttribute<Int>(name)
