package me.riguron.grape.loader;

import me.riguron.grape.bean.BeanDefinition;

import java.util.List;

public interface BeanDefinitionLoader {

    List<BeanDefinition> load();
}
