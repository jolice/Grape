package me.riguron.grape.bootstrap;

import lombok.Getter;
import me.riguron.grape.bean.Configuration;
import me.riguron.grape.bean.scan.ComponentScan;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for configuring Grape container.
 */
@Getter
public class GrapeConfiguration {

    private ComponentScan componentScan;
    private Set<Class<?>> classes = new HashSet<>();
    private Set<Configuration> configurations = new HashSet<>();

    /**
     * Allows for specifying the name of the package that will be
     * scanned for the bean classes.
     *
     * @param packageName package to be scanned
     * @return current configuration instance
     */
    public GrapeConfiguration scan(String packageName) {
        this.componentScan = new ComponentScan(packageName);
        return this;
    }

    /**
     * Allows for adding bean classes manually. These classes
     * should not be annotated with {@link me.riguron.grape.annotation.Component},
     * but support injection annotations (e.g @{@link javax.inject.Named})
     *
     * @param classes bean classes
     * @return current configuration instance
     */
    public GrapeConfiguration classes(Set<Class<?>> classes) {
        this.classes = classes;
        return this;
    }

    /**
     * Allows for specifying programmatic bean configurations.
     *
     * @param configurations bean configurations
     * @return current configuration instance
     */
    public GrapeConfiguration configurations(Set<Configuration> configurations) {
        this.configurations = configurations;
        return this;
    }


}
