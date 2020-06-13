package com.github.jolice.graph;

import com.github.jolice.bean.lookup.StandardLookupParams;
import com.github.jolice.dependency.Dependency;
import com.github.jolice.graph.custom.Graph;
import com.github.jolice.bean.BeanDefinition;
import com.github.jolice.bean.lookup.BeanLookup;


public class DependencyGraph {

    private final Graph<BeanDefinition> graph = new Graph<>();
    private final BeanLookup<BeanDefinition> beanLookup;

    public DependencyGraph(BeanLookup<BeanDefinition> beanLookup) {
        this.beanLookup = beanLookup;
    }

    public void addEdge(BeanDefinition node) {
        for (Dependency dependency : node.getConstructor().getDependencies()) {
            graph.addEdge(node, beanLookup.lookup(dependency.getType(), node.getBeanClass(), new StandardLookupParams(dependency.getAnnotatedElement())));
        }
    }

    public void addNode(BeanDefinition node) {
        graph.addVertex(node);
    }

    public Graph<BeanDefinition> getGraph() {
        return graph;
    }
}
