package com.bipinet.spring.featuretoggles.togglez.aspect;

import com.bipinet.spring.featuretoggles.togglez.annotation.FeatureToggle;
import com.bipinet.spring.featuretoggles.togglez.config.FeatureTogglesConfig;
import com.bipinet.spring.featuretoggles.togglez.exception.ResourceNotFoundException;
import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.repository.FeatureState;
import org.togglz.spring.boot.autoconfigure.TogglzAutoConfiguration;

import static com.bipinet.spring.featuretoggles.helper.TestHelper.setFeatureTogglesState;
import static com.bipinet.spring.featuretoggles.matcher.ExtraMatchers.isClass;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.bipinet.spring.featuretoggles.togglez.aspect"})
class TestSpringContextAop {
}

@Service
class TestServiceImpl {

    @FeatureToggle(FeatureToggles.HELLO_WORLD)
    public String testMethod() {
        return "Aspect works";
    }
}

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TogglzAutoConfiguration.class, FeatureTogglesConfig.class, TestSpringContextAop.class})
@DirtiesContext(classMode = AFTER_CLASS)
@ActiveProfiles("feature-toggles")
public class FeaturesAspectTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    private TestServiceImpl testServiceImpl;

    @Before
    public void setUp() {
        setFeatureTogglesState(false);
    }

    @Test
    public void aspectProceedsToTestMethodWhenFeatureToggleEnabled() {
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(FeatureToggles.HELLO_WORLD, true));
        assertThat(this.testServiceImpl.testMethod(), is("Aspect works"));
    }

    @Test
    public void aspectThrowsExceptionWhenFeatureToggleDisabled() {
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(FeatureToggles.HELLO_WORLD, false));
        this.expectedException.expect(isClass(ResourceNotFoundException.class));
        this.testServiceImpl.testMethod();
    }
}
