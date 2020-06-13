package com.github.jolice.bean.lookup;

import com.github.jolice.bean.matcher.BeanMatcher;
import com.github.jolice.bean.matcher.policy.BindingPolicy;

public interface LookupParams {

    BeanMatcher beanMatcher();

    BindingPolicy bindingPolicy();


}
