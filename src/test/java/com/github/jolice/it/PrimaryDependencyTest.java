package com.github.jolice.it;

import com.github.jolice.annotation.Primary;
import com.github.jolice.bean.Configuration;
import com.github.jolice.bootstrap.Grape;
import com.github.jolice.bootstrap.GrapeConfiguration;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimaryDependencyTest {

    @Test
    void whenPrimaryBeanDefinedInClass() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .classes(Arrays.asList(Client.class, One.class, Two.class))

        );

        assertEquals(Two.class, grape.createContext().getBean(Client.class).instance.getClass());

    }

    @Test
    void whenPrimaryBeanDefinedInProgrammaticConfig() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                .configurations(Collections.singletonList(new Config()))
        );

        assertEquals(Two.class, grape.createContext().getBean(Client.class).instance.getClass());
    }

    public static class Client {

        @Getter
        private Interface instance;

        @Inject
        public Client(Interface instance) {
            this.instance = instance;
        }
    }

    public static class Config implements Configuration {

        @Produces
        public Interface one() {
            return new One();
        }

        @Primary
        @Produces
        public Interface two() {
            return new Two();
        }

        @Produces
        public Client client(Interface instance) {
            return new Client(instance);
        }
    }

    public interface Interface {

    }

    public static class One implements Interface {

    }

    @Primary
    public static class Two implements Interface {

    }

}
