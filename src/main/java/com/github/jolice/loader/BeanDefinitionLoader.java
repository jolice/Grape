package com.github.jolice.loader;

import com.github.jolice.bean.BeanDefinition;

import java.util.List;

public interface BeanDefinitionLoader {

    List<BeanDefinition> load();
}
