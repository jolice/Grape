package me.riguron.grape.inject;

import me.riguron.grape.bean.BeanLookup;
import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.reflection.MethodInvoker;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SetterInjectionTest {

    private final BeanLookup<ManagedBean> lookup = mock(BeanLookup.class);

    @Test
    void inject() throws NoSuchMethodException {

        putBean("x", "x");
        putBean(1, "i");
        putBean(1.5D, "d");

        Sample sample = new Sample();
        SetterInjection setterInjection =  new SetterInjection(lookup, new MethodInvoker());
        setterInjection.inject(sample);


        assertEquals("x", sample.x);
        assertEquals(1, sample.i);
        assertEquals(1.5D, sample.d);
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

    private void putBean(Object o, String name) throws NoSuchMethodException {

        when(lookup.lookup(eq(o.getClass()), any(), any()))
                .thenReturn(
                        createBean(o)
                );

    }

    private ManagedBean createBean(Object value) {
        return new ManagedBean(value, null);
    }

}