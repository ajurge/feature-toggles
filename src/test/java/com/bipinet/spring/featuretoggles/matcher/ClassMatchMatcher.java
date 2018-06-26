package com.bipinet.spring.featuretoggles.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ClassMatchMatcher extends BaseMatcher<Object> {
    private final Class<?> expectedClass;

    ClassMatchMatcher(Class<?> expectedClass) {
        this.expectedClass = expectedClass;
    }

    @Override
    public boolean matches(Object item) {
        return expectedClass.equals(item.getClass());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an instance of ")
                .appendText(expectedClass.getName());
    }
}