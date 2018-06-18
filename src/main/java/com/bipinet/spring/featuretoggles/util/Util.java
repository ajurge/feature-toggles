package com.bipinet.spring.featuretoggles.util;

public final class Util {

    private Util() {
    }

    public static String getPageRelativePath(String pageName){
        return "/" + pageName;
    }
}