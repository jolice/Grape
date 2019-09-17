package me.riguron.grape.loader;

import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.Configuration;
import me.riguron.grape.reflection.MethodInvoker;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Produces;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigurationBeanDefinitionLoaderTest {

    @Test
    void load() {

        BeanDefinitionLoader beanDefinitionLoader = new ConfigurationBeanDefinitionLoader(
                new MethodInvoker(), Collections.singleton(new TestConfig())
        );

        List<BeanDefinition> result = beanDefinitionLoader.load();

        assertEquals(2,
                result.size());


        BeanDefinition first = result.get(1);

        assertEquals(String.class, first.getBeanClass());

        assertEquals(1, first.getConstructor().getDependencies().size());
        assertEquals(Integer.class, first.getConstructor().getDependencies().get(0).getType());

        BeanDefinition second = result.get(0);

        assertEquals(Double.class, second.getBeanClass());

        assertEquals(1, second.getConstructor().getDependencies().size());
        assertEquals(Boolean.class, second.getConstructor().getDependencies().get(0).getType());


    }

    static class TestConfig implements Configuration {


        @Produces
        public String a(Integer x) {
            return "a";
        }

        @Produces
        public Double b(Boolean b) {
            return 1.5D;
        }
    }
}

