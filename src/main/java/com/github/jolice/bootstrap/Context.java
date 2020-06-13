package com.github.jolice.bootstrap;

import com.github.jolice.bean.lookup.LookupParams;
import com.github.jolice.bean.lookup.StandardLookupParams;
import lombok.RequiredArgsConstructor;
import com.github.jolice.bean.lookup.BeanLookup;
import com.github.jolice.bean.registry.ManagedBean;
import com.github.jolice.bean.registry.Registry;

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
