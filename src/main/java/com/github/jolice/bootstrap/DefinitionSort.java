package com.github.jolice.bootstrap;

import com.github.jolice.bean.registry.Registry;
import com.github.jolice.exception.dependency.CircularDependencyException;
import com.github.jolice.graph.DependencyGraph;
import com.github.jolice.graph.custom.TopologicalSort;
import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.BeanDefinition;
import com.github.jolice.bean.lookup.BeanLookup;
import com.github.jolice.exception.GraphCycleException;

import java.util.List;

@RequiredArgsConstructor
public class DefinitionSort {

    private final Registry<BeanDefinition> beanDefinitionRegistry;
    private final BeanLookup<BeanDefinition> lookup;

    public List<BeanDefinition> getOrderedBeanDefinitions() {
        DependencyGraph dependencyGraph = new DependencyGraph(lookup);
        beanDefinitionRegistry.getAll().forEach(dependencyGraph::addNode);
        beanDefinitionRegistry.getAll().forEach(dependencyGraph::addEdge);
        TopologicalSort<BeanDefinition> topologicalSort = new TopologicalSort<>(dependencyGraph.getGraph());
        try {
            return topologicalSort.sort();
        } catch (GraphCycleException e) {
            throw new CircularDependencyException(e.getFrom(), e.getTo());
        }
    }
}
