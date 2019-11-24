package me.riguron.grape.inject;

import me.riguron.grape.bean.lookup.BeanLookup;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.reflection.MethodInvoker;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.when;
import static io.riguron.mocks.matcher.ArgumentMatchers.any;
import static io.riguron.mocks.matcher.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MethodInjectionTest {

    private final BeanLookup<ManagedBean> lookup = mock(BeanLookup.class);

    @Test
    void inject() throws NoSuchMethodException {

        putBean("x");
        putBean(1);
        putBean(1.5D);

        Sample sample = new Sample();
        MethodInjection methodInjection =  new MethodInjection(lookup, new MethodInvoker());
        methodInjection.inject(sample);


        assertEquals("x", sample.x);
        assertEquals(1, sample.i.intValue());
        assertEquals(1.5D, sample.d.doubleValue());
    }

    public static class Sample {

        private String x;
        private Integer i;
        private Double d;

        @Inject
        public void inject(String x, Integer i) {
            this.x = x;
            this.i = i;
        }

        @Inject
        public void injMethod(Double d) {
            this.d = d;
        }

    }

    private void putBean(Object o) {

        when(lookup.lookup(eq(o.getClass()), any(), any()))
                .thenReturn(
                        createBean(o)
                );

    }

    private ManagedBean createBean(Object value) {
        return new ManagedBean(value, null);
    }

}