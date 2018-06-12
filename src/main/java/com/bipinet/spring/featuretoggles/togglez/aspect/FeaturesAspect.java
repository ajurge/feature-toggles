package com.bipinet.spring.featuretoggles.togglez.aspect;

import com.bipinet.spring.featuretoggles.togglez.exception.ResourceNotFoundException;
import com.bipinet.spring.featuretoggles.togglez.annotation.FeatureToggle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeaturesAspect {

    private static final Logger LOG = LogManager.getLogger(FeaturesAspect.class);

    @Around(value = "@within(featureToggle) || @annotation(featureToggle)")
    public Object checkAspect(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        if (featureToggle.value().isActive()) {
            return joinPoint.proceed();
        } else {
            LOG.debug("Feature " + featureToggle.value().name() + " is not enabled!");
            throw new ResourceNotFoundException();
        }
    }
}