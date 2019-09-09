package me.riguron.grape.bean.scan;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.annotation.Component;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ComponentScan {

    private final String packageName;

    public List<Class<?>> getAnnotatedBeans() {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class).stream().filter(x -> x.isAnnotationPresent(Component.class)).collect(Collectors.toList());
    }

}
