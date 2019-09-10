package me.riguron.grape.bootstrap;

import me.riguron.grape.bean.registry.ManagedBean;
import me.riguron.grape.bean.registry.Registry;
import me.riguron.grape.lifecycle.LifecycleCallback;
import me.riguron.grape.lifecycle.LifecycleCallbackFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class LifecycleManagementTest {

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    void runPostConstruct() {

        Registry<ManagedBean> registry = mock(Registry.class);

        ManagedBean a = managedBean();
        ManagedBean b = managedBean();

        when(registry.getAll()).thenReturn(Arrays.asList(a, b));

        LifecycleCallbackFactory factory = mock(LifecycleCallbackFactory.class);

        LifecycleCallback postConstruct = mock(LifecycleCallback.class);

        when(factory.postConstructCallback()).thenReturn(postConstruct);

        LifecycleManagement lifecycleManagement
                = new LifecycleManagement(
                registry,
                mock(Runtime.class),
                factory
        );


        lifecycleManagement.triggerBeanInitialization();

        verify(postConstruct).trigger(eq(a.getBeanInstance()));
        verify(postConstruct).trigger(eq(b.getBeanInstance()));
    }

    @Test
    void runPreDestroy() {

        Registry<ManagedBean> registry = mock(Registry.class);

        ManagedBean a = managedBean();
        ManagedBean b = managedBean();

        when(registry.getAll()).thenReturn(Arrays.asList(a, b));


        LifecycleCallbackFactory factory = mock(LifecycleCallbackFactory.class);

        LifecycleCallback preDestroy = mock(LifecycleCallback.class);

        when(factory.preDestroyCallback()).thenReturn(preDestroy);

        Runtime runtime = mock(Runtime.class);

        doAnswer(invocationOnMock -> {
            Thread thread = invocationOnMock.getArgumentAt(0, Thread.class);
            // intentionally called run instead of start, to trigger execution in the main thread
            thread.run();
            return null;
        }).when(runtime).addShutdownHook(any());

        LifecycleManagement lifecycleManagement
                = new LifecycleManagement(
                registry,
               runtime,
                factory
        );


        lifecycleManagement.registerShutdownCallbacks();

        verify(preDestroy).

                trigger(eq(a.getBeanInstance()));

        verify(preDestroy).

                trigger(eq(b.getBeanInstance()));
    }

    private ManagedBean managedBean() {
        ManagedBean managedBean = mock(ManagedBean.class);
        Object beanInstance = mock(Object.class);
        when(managedBean.getBeanInstance()).thenReturn(beanInstance);
        return managedBean;
    }


}