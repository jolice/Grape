package com.github.jolice.lifecycle;

import com.github.jolice.reflection.MethodInvoker;
import com.github.jolice.lifecycle.type.PostConstructAction;
import org.junit.jupiter.api.Test;

import javax.annotation.PostConstruct;

import static io.riguron.mocks.Mocks.mock;
import static io.riguron.mocks.Mocks.verify;
import static io.riguron.mocks.verify.VerificationModes.times;

class LifecycleCallbackTest {

    @Test
    void trigger() {

        Verification verification = mock(Verification.class);
        Sample sample = new Sample(verification);


        LifecycleCallback lifecycleCallback = new LifecycleCallback(
                new MethodInvoker(), new PostConstructAction()
        );

        lifecycleCallback.trigger(sample);

        verify(verification).first();
        verify(verification).second();
        verify(verification, times(0)).nothing();

    }

    // For some reason, it's not possible to access methods in mockito proxies via reflection, so it's kinda a workaround
    static class Verification {

        void first() {
        }

        void second() {
        }

        void nothing() {
        }

    }

    static class Sample {


        private Verification verification;

        public Sample(Verification verification) {
            this.verification = verification;
        }

        @PostConstruct
        void first() {
            verification.first();
        }

        @PostConstruct
        void second() {
            verification.second();
        }

        void nothing() {
            verification.nothing();

        }

    }
}