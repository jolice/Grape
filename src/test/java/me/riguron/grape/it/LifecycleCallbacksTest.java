package me.riguron.grape.it;

import me.riguron.grape.bootstrap.Context;
import me.riguron.grape.bootstrap.Grape;
import me.riguron.grape.bootstrap.GrapeConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LifecycleCallbacksTest {

    @Test
    void lifecycleCallbacks() {

        Grape grape = new Grape(
                new GrapeConfiguration()
                        .classes(new HashSet<>(Collections.singletonList(Component.class)))
        );

        final Context context = grape.createContext();
        Component component = context.getBean(Component.class);
        assertEquals(1, component.counter.get());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                component.runner.join();
                assertEquals(2, context.getBean(Component.class).counter.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }


    public static class Component {

        private AtomicInteger counter = new AtomicInteger();
        private CountDownLatch countDownLatch = new CountDownLatch(1);
        private Thread runner;

        @PostConstruct
        public void doPostConstruct() {
            counter.incrementAndGet();
            runner = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            runner.start();
        }

        @PreDestroy
        public void doPreDestroy() {
            counter.incrementAndGet();
            countDownLatch.countDown();
        }


    }
}
