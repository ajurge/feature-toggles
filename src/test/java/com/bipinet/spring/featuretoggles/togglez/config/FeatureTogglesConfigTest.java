package com.bipinet.spring.featuretoggles.togglez.config;

import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.metadata.FeatureGroup;
import org.togglz.spring.boot.autoconfigure.TogglzAutoConfiguration;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bipinet.spring.featuretoggles.togglez.config.FeatureTogglesConfig.CUSTOM_FEATURE_GROUP;
import static com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles.HELLO_WORLD;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TogglzAutoConfiguration.class, FeatureTogglesConfig.class})
@DirtiesContext(classMode = AFTER_CLASS)
@ActiveProfiles("feature-toggles")
public class FeatureTogglesConfigTest {

    @Test
    public void customGroupAdded() {
        FeatureContext.getFeatureManager().getFeatures().forEach(ft -> {
            if (!HELLO_WORLD.equals(ft)) {
                final List<String> featureGroupLabels =
                        FeatureContext.getFeatureManager().getMetaData(ft).getGroups().stream().map(FeatureGroup::getLabel).collect(Collectors.toList());
                assertThat(featureGroupLabels, hasItem(CUSTOM_FEATURE_GROUP));
            }
        });
    }

    @Test
    public void featureTogglesLoaded() {
        final Set<Feature> featureToggles = FeatureContext.getFeatureManager().getFeatures();
        assertThat(featureToggles, containsInAnyOrder(FeatureToggles.values()));
    }
}