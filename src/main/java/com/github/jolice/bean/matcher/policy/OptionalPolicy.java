package com.github.jolice.bean.matcher.policy;

import com.github.jolice.exception.ExceptionProvider;

/**
 * Optional policy implies that container doesn't fail when there's no
 * bean matching a certain criteria and returns the only existing bean
 * of this type (however, if there are less or more than one bean, an
 * appropriate exception is fired).
 */
public class OptionalPolicy implements BindingPolicy {

    @Override
    public boolean isMandatory() {
        return false;
    }

    @Override
    public ExceptionProvider unsatisfiedError() {
        throw new UnsupportedOperationException();
    }
}
