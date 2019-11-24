package me.riguron.grape.bean.matcher;

import me.riguron.grape.annotation.Name;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.BeanMeta;
import me.riguron.grape.provider.InstanceProvider;
import org.junit.jupiter.api.Test;

import javax.inject.Named;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.riguron.mocks.Mocks.mock;

class NamedMatcherTest {

    @Test
    void matches() {

        NamedMatcher namedMatcher = new NamedMatcher(
                new Name("theName")
        );

        BeanMeta beanDefinition = new BeanDefinition(
                Target.class,
                mock(InstanceProvider.class),
                Target.class
        );

        assertTrue(namedMatcher.matches(beanDefinition));

    }

    @Named("theName")
    static class Target {

    }

}