package com.bipinet.spring.featuretoggles.matcher;

import org.hamcrest.Matcher;

public class ExtraMatchers {
   public static Matcher<Object> isClass(Class<?> aClass) {
      return new ClassMatchMatcher(aClass);
   }
}