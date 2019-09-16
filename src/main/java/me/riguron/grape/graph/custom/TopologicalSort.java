package me.riguron.grape.graph.custom;

import me.riguron.grape.exception.GraphCycleException;

import java.util.*;

public class TopologicalSort<V> {

    private List<V> resultBuffer = new ArrayList<>();
    private Set<V> visited = new HashSet<>();
    private Graph<V> graph;

    public TopologicalSort(Graph<V> graph) {
        this.graph = graph;
    }

    public List<V> sort() {
        return graph.getVertexCount() == 0 ? Collections.emptyList() : doSort();
    }

    private List<V> doSort() {

        for (V v : graph.getVertexes()) {
            if (isNotVisited(v)) {
                topologicalSort(v, null);
            }
        }
        return resultBuffer;
    }

    private void topologicalSort(V item, V parent) {
        this.visit(item);
        List<V> neighbours = graph.getNeighbours(item);
        for (V n : neighbours) {
            this.checkLoop(n, parent);
            if (isNotVisited(n)) {
                this.topologicalSort(n, item);
            }
        }
        resultBuffer.add(item);
    }



    private void checkLoop(V item, V parent) {
        if (item == parent) {
            throw new GraphCycleException(item, parent);
        }
    }

    private boolean isNotVisited(V v) {
        return !visited.contains(v);
    }

    private void visit(V v) {
        visited.add(v);
    }





    abstract static class X<V extends X> {

        abstract void x(V x);

    }

    public static void main(String[] args) {
        byte i;
        for(i=0;i<128;i++)
            System.out.print(i);
    }

    class MyClass<X> {
        <T> MyClass(T t) {
            // ...
        }
    }

    static class XX extends X<XX> {

        @Override
        void x(XX x) {

        }
    }

}