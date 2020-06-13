package com.github.jolice.provider;

import com.github.jolice.dependency.Dependency;

import java.util.List;

public interface InstanceProvider {

    Object createBean(List<Object> dependencies);

    List<Dependency> getDependencies();

}
