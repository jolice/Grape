package com.github.jolice.bean.factory;

import com.github.jolice.bean.lookup.StandardLookupParams;
import com.github.jolice.bean.registry.ManagedBean;
import com.github.jolice.dependency.Dependency;
import com.github.jolice.loader.BeanRegistration;
import com.github.jolice.provider.InstanceProvider;
import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.BeanDefinition;
import com.github.jolice.bean.lookup.BeanLookup;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BeanFactory {

    private final BeanLookup<ManagedBean> beanProvider;
    private final BeanRegistration<ManagedBean> registration;
    private final List<BeanDefinition> definitions;

    public void createBeans() {
        for (BeanDefinition beanDefinition : definitions) {
            Class<?> beanType = beanDefinition.getBeanClass();
            InstanceProvider constructor = beanDefinition.getConstructor();
            List<Dependency> dependencies = constructor.getDependencies();
            List<Object> constructorArguments = new ArrayList<>();
            for (Dependency dependency : dependencies) {
                ManagedBean response = beanProvider.lookup(dependency.getType(), beanType, new StandardLookupParams(dependency.getAnnotatedElement()));
                constructorArguments.add(response.getBeanInstance());
            }
            Object bean = constructor.createBean(constructorArguments);
            registration.register(beanType, new ManagedBean(bean, beanDefinition.getAnnotationData()));
        }
    }

}
