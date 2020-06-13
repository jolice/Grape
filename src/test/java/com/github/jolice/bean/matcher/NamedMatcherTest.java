package com.github.jolice.bean.matcher;

import com.github.jolice.annotation.Name;
import com.github.jolice.bean.BeanMeta;
import com.github.jolice.provider.InstanceProvider;
import com.github.jolice.bean.BeanDefinition;
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