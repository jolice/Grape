package com.github.jolice.graph.custom;

import java.util.*;

public class Graph<V> {

    private List<V> vertexes = new ArrayList<>();
    private Map<V, List<V>> adjacency = new HashMap<>();

    public List<V> getVertexes() {
        return Collections.unmodifiableList(vertexes);
    }

    public void addVertex(V v) {
        vertexes.add(v);
    }

    public void addEdge(V from, V to) {
        adjacency.computeIfAbsent(from, v -> new ArrayList<>()).add(to);
    }

    public List<V> getNeighbours(V v) {
        return adjacency.getOrDefault(v, Collections.emptyList());
    }

    public int getVertexCount() {
        return vertexes.size();
    }

}
