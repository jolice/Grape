package me.riguron.grape.graph;

import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.lookup.StandardLookupParams;
import me.riguron.grape.dependency.Dependency;
import me.riguron.grape.graph.custom.Graph;


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
