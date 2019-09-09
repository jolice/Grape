package me.riguron.grape.provider;

import me.riguron.grape.dependency.Dependency;

import java.util.List;

public interface InstanceProvider {

    Object createBean(List<Object> dependencies);

    List<Dependency> getDependencies();

}
