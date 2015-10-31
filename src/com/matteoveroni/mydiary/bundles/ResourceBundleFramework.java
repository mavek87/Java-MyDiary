package com.matteoveroni.mydiary.bundles;

import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public enum ResourceBundleFramework {

    SUPPORTED_DEFAULT_LOCALE("default", new Locale("en", "EN")),
    SUPPORTED_ENGLISH_LOCALE("english", new Locale("en", "EN")),
    SUPPORTED_ITALIAN_LOCALE("italian", new Locale("it", "IT"));

    private final String localeName;
    private final Locale locale;
    public static final String RESOURCE_BUNDLE_FILE_PATH = "com.matteoveroni.mydiary.bundles.Bundle";

    ResourceBundleFramework(String localeName, Locale locale) {
        this.localeName = localeName;
        this.locale = locale;
    }

    public String getLocaleName() {
        return localeName;
    }

    public Locale getLocale() {
        return locale;
    }
}
