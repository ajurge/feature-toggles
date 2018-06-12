package com.bipinet.spring.featuretoggles.togglez.toggles;

import com.bipinet.spring.featuretoggles.togglez.annotation.Sprint_1;
import com.bipinet.spring.featuretoggles.togglez.annotation.Sprint_2;
import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;

public enum FeatureToggles implements Feature {

    @Sprint_1
    HELLO_WORLD,

    @Sprint_1
    REVERSE_GREETING,

    @Sprint_2
    INFO;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}