package me.riguron.grape.bootstrap;

import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.factory.BeanFactory;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.bean.registry.Registry;
import me.riguron.grape.bean.scan.ComponentScan;
import me.riguron.grape.lifecycle.LifecycleCallbackFactory;
import me.riguron.grape.loader.BeanRegistration;
import me.riguron.grape.loader.ClassListBeanDefinitionLoader;
import me.riguron.grape.loader.ConfigurationBeanDefinitionLoader;
import me.riguron.grape.reflection.ConstructorLookup;
import me.riguron.grape.reflection.MethodInvoker;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grape {

    private final MethodInvoker methodInvoker = new MethodInvoker();
    private final ConstructorLookup constructorLookup = new ConstructorLookup();
    private final Registry<BeanDefinition> beanDefinitionRegistry = new Registry<>();
    private final Registry<ManagedBean> beanRegistry = new Registry<>();
    private final GrapeConfiguration grapeConfiguration;
    private Context context;

    public Grape(GrapeConfiguration grapeConfiguration) {
        this.grapeConfiguration = grapeConfiguration;
    }

    public Context createContext() {
        this.create();
        return this.context;
    }

    private void create() {
        this.loadDefinitions();
        BeanLookup<BeanDefinition> lookup = new BeanLookup<>(beanDefinitionRegistry);
        BeanLookup<ManagedBean> beanLookup = new BeanLookup<>(beanRegistry);

        DefinitionSort definitionSort = new DefinitionSort(beanDefinitionRegistry, lookup);
        BeanRegistration<ManagedBean> registration = new BeanRegistration<>(beanRegistry);
        BeanFactory beanFactory = new BeanFactory(
                beanLookup,
                registration,
                definitionSort.getOrderedBeanDefinitions()
        );
        beanFactory.createBeans();
        OptionalInjections optionalInjections = new OptionalInjections(beanLookup, methodInvoker);
        optionalInjections.doInject();
        this.initializeLifecycle();
        this.context = new Context(beanRegistry, beanLookup);
    }

    private void initializeLifecycle() {
        LifecycleManagement lifecycleManagement = new LifecycleManagement(beanRegistry, Runtime.getRuntime(),
                new LifecycleCallbackFactory(methodInvoker));
        lifecycleManagement.triggerBeanInitialization();
        lifecycleManagement.registerShutdownCallbacks();
    }

    private void loadDefinitions() {
        BeanRegistration<BeanDefinition> beanRegistration = new BeanRegistration<>(beanDefinitionRegistry);
        ClassListBeanDefinitionLoader classListBeanLoader = new ClassListBeanDefinitionLoader(constructorLookup, getAllDefiningClasses());
        ConfigurationBeanDefinitionLoader configurationBeanLoader = new ConfigurationBeanDefinitionLoader(methodInvoker, grapeConfiguration.getConfigurations());

        Stream.concat(classListBeanLoader.load().stream(), configurationBeanLoader.load().stream())
                .collect(Collectors.toSet())
                .forEach(beanDefinition -> beanRegistration.register(beanDefinition.getBeanClass(), beanDefinition));
    }

    private Set<Class<?>> getAllDefiningClasses() {
        Set<Class<?>> finalClasses = new HashSet<>();
        ComponentScan componentScan = grapeConfiguration.getComponentScan();
        if (componentScan != null) {
            finalClasses.addAll(componentScan.getAnnotatedBeans());
        }
        finalClasses.addAll(grapeConfiguration.getClasses());
        return finalClasses;
    }


}

