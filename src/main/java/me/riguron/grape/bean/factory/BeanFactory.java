package me.riguron.grape.bean.factory;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.BeanLookup;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.bean.registry.Registry;
import me.riguron.grape.dependency.Dependency;
import me.riguron.grape.provider.InstanceProvider;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BeanFactory {

    private final BeanLookup<ManagedBean> beanProvider;
    private final Registry<ManagedBean> registry;
    private final List<BeanDefinition> definitions;

    public void createBeans() {
        for (BeanDefinition beanDefinition : definitions) {
            Class<?> beanType = beanDefinition.getBeanClass();
            InstanceProvider constructor = beanDefinition.getConstructor();
            List<Dependency> dependencies = constructor.getDependencies();
            List<Object> constructorArguments = new ArrayList<>();
            for (Dependency dependency : dependencies) {
                ManagedBean response = beanProvider.lookup(dependency.getType(), beanType, dependency.getAnnotatedElement());
                constructorArguments.add(response.getBeanInstance());
            }
            Object bean = constructor.createBean(constructorArguments);
            registry.put(beanType, new ManagedBean(bean, beanDefinition.getAnnotationData()));
        }
    }

}
