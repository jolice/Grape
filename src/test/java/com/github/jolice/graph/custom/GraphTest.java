package com.github.jolice.graph.custom;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphTest {

    @Test
    void getVertexes() {

        Graph<Integer> graph = new Graph<>();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);


        // 1 -> 2, 1 -> 3
        graph.addEdge(1, 3);
        graph.addEdge(1, 2);

        // 3 -> 1, 3 -> 2
        graph.addEdge(3, 1);
        graph.addEdge(3, 2);

        // 4 -> 1, 4 -> 3
        graph.addEdge(4, 1);
        graph.addEdge(4, 3);

        assertEquals(Arrays.asList(3, 2), graph.getNeighbours(1));
        assertEquals(Arrays.asList(1, 2), graph.getNeighbours(3));
        assertEquals(Arrays.asList(1, 3), graph.getNeighbours(4));

    }




    @Test
    void getVertexCount() {

        Graph<Integer> graph = new Graph<>();

        graph.addVertex(1);
        graph.addVertex(3);
        graph.addVertex(9);

        assertEquals(3, graph.getVertexCount());
    }
}