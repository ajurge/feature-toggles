package com.bipinet.spring.featuretoggles.togglez.config;

import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

import java.io.File;

@Configuration
@Profile("feature-toggles")
public class FeatureTogglesConfig {

    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(FeatureToggles.class);
    }

    @Bean
    public StateRepository getStateRepository(@Value("${togglz.feature-toggles.properties-file-path:feature-toggles.properties}") String pathToFeatureTogglesPropertiesFile) {
        return new FileBasedStateRepository(new File(pathToFeatureTogglesPropertiesFile), Integer.MAX_VALUE);
    }

    @Bean
    public UserProvider getUserProvider() {
        return () -> new SimpleFeatureUser("admin", true);
    }
}