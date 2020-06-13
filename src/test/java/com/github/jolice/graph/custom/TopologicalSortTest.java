package com.github.jolice.graph.custom;

import com.github.jolice.exception.GraphCycleException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TopologicalSortTest {

    @Test
    void whenDisconnectedGraphThenSortedProperly() {

        Graph<Integer> graph = new Graph<>();

        IntStream.rangeClosed(0, 6).forEach(graph::addVertex);

        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(0, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);

        TopologicalSort<Integer> sort = new TopologicalSort<>(graph);


        List<Integer> expectedList = Arrays.asList(6, 5, 4, 0, 3, 1, 2);

        assertEquals(expectedList, sort.sort());

    }

    @Test
    void whenLoopDetectedThenExceptionThrown() {

        Graph<Integer> graph = new Graph<>();

        IntStream.rangeClosed(0, 6).forEach(graph::addVertex);

        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(0, 4);
        graph.addEdge(3, 1);

        assertThrows(GraphCycleException.class, () -> {
            TopologicalSort<Integer> sort = new TopologicalSort<>(graph);
            sort.sort();
        });
    }

    @Test
    void whenConnectedGraphThenSorted() {

        Graph<Integer> graph = new Graph<>();

        Stream.of(
                5, 11, 2, 7, 8, 9, 10, 3
        ).forEach(graph::addVertex);

        // 5
        graph.addEdge(5, 11);

        // 11
        graph.addEdge(11, 2);
        graph.addEdge(11, 9);
        graph.addEdge(11, 10);

        // 7
        graph.addEdge(7, 11);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);

        // 3

        graph.addEdge(3, 8);
        graph.addEdge(3, 10);

        List<Integer> expectedList = Arrays.asList(2, 9, 10, 11, 5, 8, 7, 3);

        assertEquals(expectedList, new TopologicalSort<>(graph).sort());

    }


}