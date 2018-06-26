package com.bipinet.spring.featuretoggles.togglez.config;

import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.metadata.SimpleFeatureGroup;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.spi.FeatureProvider;

import java.io.File;

import static com.bipinet.spring.featuretoggles.togglez.config.FeatureTogglesConfig.CUSTOM_FEATURE_GROUP;


@Configuration
@Profile("feature-toggles")
public class FeatureTogglesConfig {

    static final String CUSTOM_FEATURE_GROUP = "Custom";


    @SuppressWarnings("unchecked")
    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(FeatureToggles.class);
    }

    @Bean
    public StateRepository getStateRepository(@Value("${togglz.feature-toggles.properties-file-path:feature-toggles.properties}") String pathToFeatureTogglesPropertiesFile) {
        return new FileBasedStateRepository(new File(pathToFeatureTogglesPropertiesFile), Integer.MAX_VALUE);
    }

/*    @Bean
    public UserProvider getUserProvider() {
        return () -> new SimpleFeatureUser("admin", true);
    }*/

    @Bean
    public CustomFeatureGroups frontEndFeatureGroups(FeatureManager featureManager) {
        return new CustomFeatureGroups(featureManager);
    }
}

class CustomFeatureGroups {
    public CustomFeatureGroups(FeatureManager featureManager) {
        featureManager.getFeatures().forEach(ft -> {
            if (!FeatureToggles.HELLO_WORLD.equals(ft)) {
                featureManager.getMetaData(ft).getGroups().add(new SimpleFeatureGroup(CUSTOM_FEATURE_GROUP));
            }
        });
    }
}