package com.github.jolice.bean.matcher;

import com.github.jolice.bean.BeanMeta;

/**
 * Just a 'true' predicate that doesn't imply any search criteria. Used when it doesn't
 * matter which bean will the container provide (for example, when there's only one bean
 * of a certain type).
 */
public class AnyMatcher implements BeanMatcher {

    @Override
    public boolean matches(BeanMeta registeredBean) {
        return true;
    }


}
