package me.riguron.grape.bootstrap;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.BeanLookup;
import me.riguron.grape.bean.registry.Registry;
import me.riguron.grape.exception.dependency.CircularDependencyException;
import me.riguron.grape.exception.GraphCycleException;
import me.riguron.grape.graph.DependencyGraph;
import me.riguron.grape.graph.custom.TopologicalSort;

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
            throw new CircularDependencyException(e.getItem());
        }
    }
}
