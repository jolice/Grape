package com.github.jolice.bean.matcher.policy;

import com.github.jolice.exception.ExceptionProvider;

/**
 * Binding policy represents the result of unsuccessful bean search. If
 * policy is mandatory and a bean matching certain criteria is not found,
 * an exception is thrown.
 */
public interface BindingPolicy {

    /**
     * Whether the bean matching a certain criteria must exist. If it does not,
     * IoC engine doesn't proceed and forces user to fix the issue by raising an appropriate
     * exception.
     *
     * @return whether the bean must exist in the container so that it could proceed loading
     */
    boolean isMandatory();

    ExceptionProvider unsatisfiedError();
}
