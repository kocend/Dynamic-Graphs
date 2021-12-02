package uksw.lab3

import org.graphstream.graph.Edge
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph

fun main() {
    System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")

    val vonNeumann = SingleGraph("grids")

    val n = 25

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

    vonNeumann.display(true)





    val moore = SingleGraph("grids")

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

    moore.display(true)





    val torus = SingleGraph("grids")

    for( l in 1..n ) {
        for( c in 1..n ) {
            torus.addNode<Node>("$l-$c")
        }
    }

    for( l in 1..n ) {
        for( c in 1..n ) {
            if(c < n)
                torus.addEdge<Edge>("$l-$c<->$l-${c + 1}","$l-$c", "$l-${c + 1}")
            if(l < n)
                torus.addEdge<Edge>("$l-$c<->${l + 1}-$c","$l-$c", "${l + 1}-$c")
        }
    }

    for( i in 1..n ) {
        torus.addEdge<Edge>("$i-$n<->$i-1", "$i-$n", "$i-1")
    }

    for( i in 1..n ) {
        torus.addEdge<Edge>("1-$i<->$n-$i", "1-$i", "$n-$i")
    }

    torus.display(true)
}
