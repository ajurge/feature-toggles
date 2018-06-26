package com.bipinet.spring.featuretoggles.helper;

import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.repository.FeatureState;

import java.util.Arrays;

public final class TestHelper {

    private TestHelper() {
    }

    public static void setFeatureTogglesState(boolean enabled){
        Arrays.stream(FeatureToggles.values()).forEach(featureToggle ->
                FeatureContext.getFeatureManager().setFeatureState(new FeatureState(featureToggle, enabled))
        );
    }
}