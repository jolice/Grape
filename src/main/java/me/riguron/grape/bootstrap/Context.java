package me.riguron.grape.bootstrap;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.lookup.LookupParams;
import me.riguron.grape.bean.lookup.StandardLookupParams;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.bean.registry.Registry;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Context {

    private final Registry<ManagedBean> beanRegistry;
    private final BeanLookup<ManagedBean> lookup;

    public <T> T getBean(Class<T> type) {
        return get(lookup.lookup(type, void.class, new StandardLookupParams(void.class)));
    }

    public <T> T getBean(Class<T> type, LookupParams lookupParams) {
        return get(lookup.lookup(type, void.class, lookupParams));
    }

    public Set getAllBeans() {
        return beanRegistry.getAll().stream().map(ManagedBean::getBeanInstance).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private <T> T get(ManagedBean managedBean) {
        return (T) managedBean.getBeanInstance();
    }


}
