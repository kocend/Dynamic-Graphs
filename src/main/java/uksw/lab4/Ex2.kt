package uksw.lab4

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import uksw.Tools
import java.util.stream.Collectors
import kotlin.random.Random

private const val VON_NEUMANN_FILENAME = "vonNeumann.dgs"
private const val MOORE_FILENAME = "moore.dgs"
private const val NO_LINKS = "noLinks.dgs"
private const val RED = "red"
private const val GREEN = "green"
private const val BLUE = "blue"

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")

    val n = 15


    val vonNeumann = SingleGraph("VonNeumannColorConsensus")
    createVonNeumann(vonNeumann, n)
    vonNeumann.nodes().forEach(::paintRandomly)
    Tools.write(vonNeumann, VON_NEUMANN_FILENAME)
    vonNeumann.display(true)

    val vonNeumannIterations = consensus(vonNeumann, "a$VON_NEUMANN_FILENAME")
    println("Von Neumann iterations: $vonNeumannIterations")

    var multiIterationsVonNeumann = 0
    for( i in 1..10){
        multiIterationsVonNeumann += consensus(Tools.read(VON_NEUMANN_FILENAME), "$i$VON_NEUMANN_FILENAME")
    }
    println("Von Neumann avg iterations(10 execution): ${multiIterationsVonNeumann/10}")



    val moore = SingleGraph("MooreColorConsensus")
    createMoore(moore, n)
    moore.nodes().forEach(::paintRandomly)
    Tools.write(moore, MOORE_FILENAME)
    moore.display(true)

    val mooreIterations = consensus(moore, "a$MOORE_FILENAME")
    println("Moore iterations: $mooreIterations")

    var multiIterationsMoore = 0
    for( i in 1..10){
        multiIterationsMoore += consensus(Tools.read(MOORE_FILENAME), "$i$MOORE_FILENAME")
    }
    println("Moore avg iterations(10 execution): ${multiIterationsMoore/10}")



    val withoutSomeLinks = SingleGraph("WithoutSomeLinks")
    createMooreWithoutLinks(withoutSomeLinks, n)
    withoutSomeLinks.nodes().forEach(::paintRandomly)
    Tools.write(withoutSomeLinks, NO_LINKS)
    withoutSomeLinks.display(true)

    val withoutSomeLinksIterations = consensus(moore, "a$NO_LINKS")
    println("Moore iterations: $withoutSomeLinksIterations")

    var multiIterationsWithoutSomeLinks = 0
    for( i in 1..10){
        multiIterationsWithoutSomeLinks += consensus(Tools.read(NO_LINKS), "$i$NO_LINKS")
    }
    println("withoutSomeLinks avg iterations(10 execution): ${multiIterationsWithoutSomeLinks/10}")

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

fun createMoore(moore: SingleGraph, n: Int) {
    for( l in 1..n ){
        for( c in 1..n ){
            moore.addNode<Node>("$l-$c")
        }
    }

    for( l in 1..n ){
        for( c in 1..n ){
            if(c < n)
                moore.addEdge<Edge>("$l-$c<->$l-${c + 1}","$l-$c", "$l-${c + 1}")
            if(l < n)
                moore.addEdge<Edge>("$l-$c<->${l + 1}-$c", "$l-$c", "${l + 1}-$c")
            if(c < n && l < n)
                moore.addEdge<Edge>("$l-$c<->${l + 1}-${c + 1}", "$l-$c", "${l + 1}-${c + 1}")
            if(c > 1 && l < n)
                moore.addEdge<Edge>("$l-$c<->${l + 1}-${c - 1}","$l-$c", "${l + 1}-${c - 1}")
        }
    }
}

fun createMooreWithoutLinks(moore: SingleGraph, n: Int) {
    for( l in 1..n ){
        for( c in 1..n ){
            moore.addNode<Node>("$l-$c")
        }
    }

    for( l in 1..n ){
        for( c in 1..n ){
            if(c < n)
                moore.addEdge<Edge>("$l-$c<->$l-${c + 1}","$l-$c", "$l-${c + 1}")
            if(l < n)
                moore.addEdge<Edge>("$l-$c<->${l + 1}-$c", "$l-$c", "${l + 1}-$c")
            if(c < n && l < n)
                moore.addEdge<Edge>("$l-$c<->${l + 1}-${c + 1}", "$l-$c", "${l + 1}-${c + 1}")
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

fun consensus(graph: SingleGraph, filename: String): Int {
    var iteration = 0
    Tools.write(graph, filename)
    do {
        graph.nodes().forEach { node ->
            val nodes = Tools.read(filename).nodes()
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
        Tools.write(graph, filename)
        val nodes = Tools.read(filename).nodes()
        val testNode = nodes.random()
        val size = nodes.filter {
            it.getIntAttr(RED) == testNode.getIntAttr(RED) &&
                    it.getIntAttr(GREEN) == testNode.getIntAttr(GREEN) &&
                    it.getIntAttr(BLUE) == testNode.getIntAttr(BLUE)
        }.size
        iteration++
    } while (size != nodes.size)
    return iteration
}

fun SingleGraph.nodes() = this.getNodeSet<Node>().stream().collect(Collectors.toList())
fun Node.neighbours() = this.getNeighborNodeIterator<Node>().asSequence().toList()
fun Node.getIntAttr(name: String) = this.getAttribute<Int>(name)
